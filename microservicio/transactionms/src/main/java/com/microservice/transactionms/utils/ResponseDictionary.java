package com.microservice.transactionms.utils;

import lombok.Getter;

@Getter
public enum ResponseDictionary {
    OK(200,0,"Transacción realizada con éxito"),
    CREATED(201,0,"Transacción realizada con éxito"),
    ERRSQL(500,500,"Transacción realizada con error"),
    VALID(400,10,"Transacción realizada con error");

    private int codeHttp;
    private int codeInter;
    private String message;

    ResponseDictionary(int codeHttp, int codeInter, String message){
        this.codeHttp = codeHttp;
        this.codeInter = codeInter;
        this.message = message;
    }
}
