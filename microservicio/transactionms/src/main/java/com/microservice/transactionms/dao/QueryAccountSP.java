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
@StoreProcedure(schema = "public", catalog = "postgres", name = "cconsulta_cuenta")
public class QueryAccountSP {
    
    @StoreProcedureParam(param = "e_code", type = Types.INTEGER,isOut = false)
	private Integer dni;

}
