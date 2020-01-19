package com.tyn.sqlSession;

import com.tyn.config.BoundSql;
import com.tyn.pojo.Configuration;
import com.tyn.pojo.MapperdStatement;
import com.tyn.utils.GenericTokenParser;
import com.tyn.utils.ParameterMapping;
import com.tyn.utils.ParameterMappingTokenHandler;
import com.tyn.utils.TokenHandler;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

public class SimpleExecutor implements Executor {
    @Override
    public <E> List<E> query(Configuration configuration, MapperdStatement mapperdStatement, Object... params) throws Exception{

        PreparedStatement prepareStatement = getPrepareStatement(configuration, mapperdStatement, params);
        //5.执行sql
        ResultSet resultSet = prepareStatement.executeQuery();
        String resultType = mapperdStatement.getResultType();
        Class<?> resultTypeClass = getClassType(resultType);

        ArrayList<Object> objects = new ArrayList<>();
        //6.封装返回结果集
        while (resultSet.next()){
            Object o = resultTypeClass.newInstance();
            //元数据
            ResultSetMetaData metaData = resultSet.getMetaData();
            for (int i =1;i<metaData.getColumnCount();i++){
                //字段名
                String columnName = metaData.getColumnName(i);
                //字段的值
                Object value = resultSet.getObject(columnName);
                //反射，根据数据库表和实体的对应关系，完成封装
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName, resultTypeClass);
                Method writeMethod = propertyDescriptor.getWriteMethod();
                writeMethod.invoke(o,value);
                objects.add(o);
            }
        }
        return (List<E>)objects;
    }

    @Override
    public Integer update(Configuration configuration, MapperdStatement mapperdStatement, Object... params) throws Exception {
        PreparedStatement prepareStatement = getPrepareStatement(configuration, mapperdStatement, params);
        //5.执行sql
        return prepareStatement.executeUpdate();
    }

    /**
     * 完成对#{}的解析工作：1.将#{}使用？代替 2.解析出#{}里面的值进行存储
     * **/
    private BoundSql getBoundSql(String sql){
        //标记处理类：配置标记解析器来完成对占位符的解析处理工作
        ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{","}",parameterMappingTokenHandler);
        //解析出来的sql
        String parseSql = genericTokenParser.parse(sql);
        //解析出来的参数名称
        List<ParameterMapping> parameterMappings = parameterMappingTokenHandler.getParameterMappings();

        BoundSql boundSql  = new BoundSql(parseSql,parameterMappings);

        return boundSql;
    }
    private Class<?> getClassType(String paramterType) throws ClassNotFoundException{
        if(paramterType!=null){
            Class<?> aclass = Class.forName(paramterType);
        }
            return null;
    }

    private PreparedStatement getPrepareStatement(Configuration configuration, MapperdStatement mapperdStatement, Object... params)throws  Exception{
        //1.注册驱动，获取连接
        Connection connection = configuration.getDataSource().getConnection();

        //2.获取sql语句
        //转换sql语句
        String sql = mapperdStatement.getSql();
        BoundSql boundSql =getBoundSql(sql);
        //3.获取预处理对象 prepareStatement
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSqlTest());
        //4。设置参数
        String parameterType = mapperdStatement.getParamterType();
        Class<?> parameterTypeClass = getClassType(parameterType);
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        for(int i = 0;i<parameterMappings.size();i++){
            ParameterMapping parameterMapping = parameterMappings.get(i);
            String content = parameterMapping.getContent();

            //反射
            Field declaredField = parameterTypeClass.getDeclaredField(content);
            //设置暴力访问,可以访问私有对象
            declaredField.setAccessible(true);
            Object o = declaredField.get(params[0]);
            preparedStatement.setObject(i+1,o);
        }
        return preparedStatement;
    }
}
