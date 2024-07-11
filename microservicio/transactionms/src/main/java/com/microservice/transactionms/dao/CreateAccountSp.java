package com.microservice.transactionms.dao;

import com.microservice.transactionms.utils.annotations.StoreProcedure;
import com.microservice.transactionms.utils.annotations.StoreProcedureParam;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.sql.Types;

@Getter
@Setter
@AllArgsConstructor
@StoreProcedure(schema = "public", catalog = "postgres", name = "pa_icrea_cuenta")
public class CreateAccountSp {
    
    @StoreProcedureParam(param = "e_cliente", type = Types.INTEGER,isOut = false)
	private Integer codePerson;

    @StoreProcedureParam(param = "e_tipo", type = Types.VARCHAR,isOut = false)
	private String type;

}
