package com.tyn.pojo;

import javax.sql.DataSource;
import java.nio.MappedByteBuffer;
import java.util.HashMap;
import java.util.Map;

public class Configuration {
    private DataSource dataSource;

    /*
    * key:statementId = namespace.id
    * */
    Map<String, MapperdStatement> mappedStatementMap = new HashMap();

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Map<String, MapperdStatement> getMappedStatementMap() {
        return mappedStatementMap;
    }

    public void setMappedStatementMap(Map<String, MapperdStatement> mappedStatementMap) {
        this.mappedStatementMap = mappedStatementMap;
    }
}
