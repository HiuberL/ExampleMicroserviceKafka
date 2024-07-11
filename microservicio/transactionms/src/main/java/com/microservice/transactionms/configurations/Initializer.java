package com.microservice.transactionms.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class Initializer {
    @Autowired
    DataSourceManager dataSourceManager;

    @PostConstruct
    public void init(){
        dataSourceManager.initDatasource();
    }
}
