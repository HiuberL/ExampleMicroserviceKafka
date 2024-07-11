package com.microservice.entidadms.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.microservice.entidadms.utils.LogUtils;

import jakarta.annotation.PostConstruct;

@Component
public class Initializer {
	private static final LogUtils logs = LogUtils.getLogger(Initializer.class);

    @Autowired
    DataSourceManager dataSourceManager;

    @PostConstruct
    public void init(){
        logs.info("INICIALIZANDO BASE DE DATOS");
        dataSourceManager.initDatasource();
        logs.info("BASE DE DATOS INICIALIZADA");

    }
}
