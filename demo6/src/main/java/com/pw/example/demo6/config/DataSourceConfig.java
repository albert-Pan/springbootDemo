package com.pw.example.demo6.config;
import com.pw.example.demo6.entity.enums.DBTypeEnum;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Classname DataSourceConfig
 * @Description TODO
 * @Date 2019/10/12 0012 下午 5:00
 * @Created by albert
 */
@Configuration
public class DataSourceConfig {
    @Bean
    @ConfigurationProperties(prefix="spring.datasource.master")
    public DataSource masterDataSource() {
        DataSource dataSource= DataSourceBuilder.create().build();
        return dataSource;
    }

    @Bean
    @ConfigurationProperties(prefix="spring.datasource.slave1")
    public DataSource slave1DataSource() {
        DataSource dataSource= DataSourceBuilder.create().build();
        return dataSource;
    }

    @Bean
    @ConfigurationProperties(prefix="spring.datasource.slave2")
    public DataSource slave2DataSource() {
        DataSource dataSource= DataSourceBuilder.create().build();
        return dataSource;
    }



    @Bean
    public DataSource myRoutingDataSource(@Qualifier("masterDataSource") DataSource masterDataSource,
                                          @Qualifier("slave1DataSource") DataSource slave1DataSource,
                                          @Qualifier("slave2DataSource") DataSource slave2DataSource) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DBTypeEnum.MASTER, masterDataSource);
        targetDataSources.put(DBTypeEnum.SLAVE1, slave1DataSource);
        targetDataSources.put(DBTypeEnum.SLAVE2, slave2DataSource);
        MyRoutingDataSource myRoutingDataSource = new MyRoutingDataSource();
        myRoutingDataSource.setDefaultTargetDataSource(masterDataSource);
        myRoutingDataSource.setTargetDataSources(targetDataSources);
        return myRoutingDataSource;
    }

}
