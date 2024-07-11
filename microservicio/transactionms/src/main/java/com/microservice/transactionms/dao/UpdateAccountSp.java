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
@StoreProcedure(schema = "public", catalog = "postgres", name = "pa_uact_cuenta")
public class UpdateAccountSp {
    
    @StoreProcedureParam(param = "e_cliente", type = Types.INTEGER,isOut = false)
	private Integer code;
    
    @StoreProcedureParam(param = "e_cuenta", type = Types.VARCHAR,isOut = false)
	private String account;
    
    @StoreProcedureParam(param = "e_saldo", type = Types.NUMERIC,isOut = false)
	private Float availableBalance;
    
    @StoreProcedureParam(param = "e_estado", type = Types.VARCHAR,isOut = false)
	private String status;
    
}
