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
@StoreProcedure(schema = "public", catalog = "postgres", name = "pa_delim_movimiento")
public class DeleteMovementSp {
    
    @StoreProcedureParam(param = "o_secuencia", type = Types.BIGINT,isOut = false)
	private BigInteger sequence;

}
