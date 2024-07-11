package com.microservice.entidadms.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class LogUtils {
    private Logger logger; 
    

    public static LogUtils getLogger(Class<?> className){
        var ul = new LogUtils();
        ul.setLogger(LoggerFactory.getLogger(className));
        return ul;
    }

    public void setLogger(Logger logger){
        this.logger = logger;
    }
    
    public void info(String message){
        logger.info(message);
    }
    public void debug(String message){
        logger.debug(message);
    }
    public void error(String message){
        logger.error(message);
    }

    public void error(String message, Object e){
        logger.error(message,e);
    }
}

