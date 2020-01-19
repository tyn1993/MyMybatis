package com.tyn.sqlSession;

import java.util.List;

public interface SqlSession {

    //查询所有
    public <E> List<E> selectList(String statementId,Object... params) throws Exception;

    //根据条件查询单个
    public <T> T selectOne(String statementId,Object... params) throws Exception;
    //插入
    public Integer insert(String statementId,Object...params) throws Exception;
    //修改
    public Integer update(String statementId,Object...params) throws  Exception;
    //删除
    public Integer delete(String statementId,Object...params) throws Exception;
    //为Dao借口生成代理实现类
    public <T> T getMapper(Class<?> mapperClass);
}
