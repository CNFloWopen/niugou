package com.isagod.split;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
//        不同的需求返回不同的key
        return DynamicDataSourceHolder.getDbType();
    }
}
