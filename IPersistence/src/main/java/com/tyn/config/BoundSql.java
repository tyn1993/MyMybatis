package com.tyn.config;

import com.tyn.utils.ParameterMapping;

import java.util.ArrayList;
import java.util.List;

public class BoundSql {

    public BoundSql(String sqlTest, List<ParameterMapping> parameterMappings) {
        this.sqlTest = sqlTest;
        this.parameterMappings = parameterMappings;
    }

    private String sqlTest;
    private List<ParameterMapping> parameterMappings = new ArrayList<>();

    public String getSqlTest() {
        return sqlTest;
    }

    public void setSqlTest(String sqlTest) {
        this.sqlTest = sqlTest;
    }

    public List<ParameterMapping> getParameterMappings() {
        return parameterMappings;
    }

    public void setParameterMappings(List<ParameterMapping> parameterMappings) {
        this.parameterMappings = parameterMappings;
    }
}
