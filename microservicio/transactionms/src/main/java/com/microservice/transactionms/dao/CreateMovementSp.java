package com.microservice.transactionms.dao;

import com.microservice.transactionms.utils.annotations.StoreProcedure;
import com.microservice.transactionms.utils.annotations.StoreProcedureParam;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.sql.Types;

@Getter
@Setter
@AllArgsConstructor
@StoreProcedure(schema = "public", catalog = "postgres", name = "pa_icrea_movimiento")
public class CreateMovementSp {
    
    @StoreProcedureParam(param = "e_cliente", type = Types.INTEGER,isOut = false)
	private Integer code;
    
    @StoreProcedureParam(param = "e_cuenta", type = Types.VARCHAR,isOut = false)
	private String account;
    
    @StoreProcedureParam(param = "e_valor", type = Types.DECIMAL,isOut = false)
	private Float value;
    
    @StoreProcedureParam(param = "e_tipo", type = Types.VARCHAR,isOut = false)
	private String type;

    @StoreProcedureParam(param = "o_secuencia", type = Types.BIGINT,isOut = true)
	private BigInteger sequence;

}
