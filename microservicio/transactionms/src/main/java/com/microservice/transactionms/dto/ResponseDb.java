package com.microservice.transactionms.dto;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ResponseDb{
    private List<Map<String,Object>> dataset;
    private List<Object> outs;

    public ResponseDb(){
        outs = new ArrayList<>();
        dataset = new ArrayList<>();
    }
}
