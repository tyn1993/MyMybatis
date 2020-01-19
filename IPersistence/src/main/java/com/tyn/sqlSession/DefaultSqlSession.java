package com.tyn.sqlSession;

import com.tyn.pojo.Configuration;

import java.lang.reflect.*;
import java.util.List;

public class DefaultSqlSession implements  SqlSession {
    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration){
        this.configuration = configuration;
    }

    @Override
    public <E> List<E> selectList(String statementId, Object... params) throws Exception{
        //将要完成对simpleExecutor里的query方法的调用
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        List<Object> list =simpleExecutor.query(configuration,configuration.getMappedStatementMap().get(statementId),params);
        return (List<E>)list;
    }

    @Override
    public <T> T selectOne(String statementId, Object... params) throws Exception {
        List<Object> objects = selectList(statementId,params);
        if (objects.size() == 1){
            return (T)objects.get(0);
        }else {
            throw new RuntimeException("查询结果为空或者返回结果过多");
        }
    }

    @Override
    public Integer insert(String statementId, Object... params) throws Exception {
        return  this.update(statementId,params);
    }

    @Override
    public Integer update(String statementId, Object... params) throws Exception {
        //将要完成对simpleExecutor里的query方法的调用
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        return   simpleExecutor.update(configuration,configuration.getMappedStatementMap().get(statementId),statementId,params);
    }

    @Override
    public Integer delete(String statementId, Object... params) throws Exception {
        return  this.update(statementId,params);
    }

    @Override
    public <T> T getMapper(Class<?> mapperClass) {
        //使用jdk动态代理，来为dao接口生成代理对象并返回
        Object proxyInstance = Proxy.newProxyInstance(DefaultSqlSession.class.getClassLoader(), new Class[]{mapperClass}, new InvocationHandler() {
            @Override
            /**
             * proxy:当前代理对象的引用
             * method :当前被调用方法的引用
             * args：传递的参数
             * **/
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //底层还是执行jdbc代码  根据不同情况，来调用selectList 或者selectOne
                //准备参数 1：statmentId sql语句的唯一表示：namespace.id
                //方法名
                String methodName = method.getName();
                String className = method.getDeclaringClass().getName();
                String statementId = className+","+methodName;
                //准备参数2 ：params
                //获取被调用方法的返回值类型
                String sql =configuration.getMappedStatementMap().get(statementId).getSql();
                if(sql.contains("select")){
                    Type genericReturnType = method.getGenericReturnType();
                    //判断是否进行了 泛型类型参数化
                    if(genericReturnType instanceof ParameterizedType){
                        List<Object> list = selectList(statementId, args);
                        return list;
                    }
                    return selectOne(statementId,args);
                }else if(sql.contains("delete")){
                    return delete(statementId,args);
                }else if(sql.contains("update")){
                    return update(statementId,args);
                }else if(sql.contains("insert")){
                    return insert(statementId,args);
                }
                return null;
            }
        });
        return (T)proxyInstance;
    }
}
