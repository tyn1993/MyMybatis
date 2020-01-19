package com.tyn.sqlSession;

import com.tyn.pojo.Configuration;
import com.tyn.pojo.MapperdStatement;

import java.util.List;

public interface Executor {
    public <E> List<E> query(Configuration configuration, MapperdStatement mapperdStatement,Object... params) throws Exception;
    public Integer update(Configuration configuration, MapperdStatement mapperdStatement,Object... params) throws Exception;
}
