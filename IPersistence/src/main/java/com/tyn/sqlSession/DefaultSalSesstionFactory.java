package com.tyn.sqlSession;

import com.tyn.pojo.Configuration;

public class DefaultSalSesstionFactory implements SqlSessionFactory {
    private Configuration configuration;

    public DefaultSalSesstionFactory (Configuration configuration){
        this.configuration = configuration;
    }
    @Override
    public SqlSession openSession(){
        return new DefaultSqlSession(configuration);
    }
}
