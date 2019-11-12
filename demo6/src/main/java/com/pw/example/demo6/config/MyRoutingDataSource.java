package com.pw.example.demo6.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @Classname MyRoutingDataSource
 * @Description TODO
 * @Date 2019/10/12 0012 下午 5:02
 * @Created by albert
 */
public class MyRoutingDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return DBContextHolder.get();
    }
}
