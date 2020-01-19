package com.tyn.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.tyn.io.Resources;
import com.tyn.pojo.Configuration;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.lang.annotation.Documented;
import java.util.List;
import java.util.Properties;

public class XMLConfigBuilder {

    private Configuration configuration;

    public XMLConfigBuilder(){
        this.configuration = new Configuration();
    }
    /***
     * 该方法就是使用dom4j将配置文件进行解析，封装configuration
     * */
    public Configuration parseConfig(InputStream in) throws DocumentException, PropertyVetoException {

        Document document = new SAXReader().read(in);
        //获取到根对象<configuration>
        Element element =document.getRootElement();
        List<Element> list =element.selectNodes("//property");
        Properties properties = new Properties();
        for(Element element1 : list){
            String name = element.attributeValue("name");
            String value = element.attributeValue("value");
            properties.setProperty(name,value);
        }

        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setDriverClass(properties.getProperty("driverClass"));
        comboPooledDataSource.setJdbcUrl(properties.getProperty("jdbcUrl"));
        comboPooledDataSource.setUser(properties.getProperty("username"));
        comboPooledDataSource.setPassword(properties.getProperty("password"));

        configuration.setDataSource(comboPooledDataSource);

        //mapper.xml 解析:拿到路径 -- 字节输入流 --dom4j进行解析
        List<Element> mapperList = element.selectNodes("//mapper");

        for(Element element1 :mapperList){
            String mapperPath = element.attributeValue("resource");
            InputStream resourceAsStream = Resources.getResourceAsSteam(mapperPath);
            XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(configuration);
            xmlMapperBuilder.parse(resourceAsStream);
        }


        return  configuration;
    }

}
