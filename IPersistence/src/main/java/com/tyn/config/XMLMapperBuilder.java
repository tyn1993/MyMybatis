package com.tyn.config;

import com.tyn.pojo.Configuration;
import com.tyn.pojo.MapperdStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class XMLMapperBuilder {

    private Configuration configuration;

    public XMLMapperBuilder(Configuration configuration){
        this.configuration = configuration;
    }

    public void parse(InputStream inputStream) throws DocumentException {
        Document document = new SAXReader().read(inputStream);
        Element rootElement = document.getRootElement();
        String namespace = rootElement.attributeValue("namespace");
        List<Element> list = rootElement.selectNodes("//select");
        for(Element element : list){
            String id = element.attributeValue("id");
            String resultType = element.attributeValue("resultType");
            String paramterTyper = element.attributeValue("paramterTyper");
            String sqlText = element.getTextTrim();
            MapperdStatement mapperdStatement = new MapperdStatement();
            mapperdStatement.setId(id);
            mapperdStatement.setResultType(resultType);
            mapperdStatement.setParamterType(paramterTyper);
            mapperdStatement.setSql(sqlText);
            configuration.getMappedStatementMap().put(namespace+"."+id,mapperdStatement);
        }
    }

}
