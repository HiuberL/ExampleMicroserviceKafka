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
@StoreProcedure(schema = "public", catalog = "postgres", name = "cconsulta_movimiento")
public class QueryMovementSP {
    
    @StoreProcedureParam(param = "e_codigo_cuenta", type = Types.INTEGER,isOut = false)
	private Integer codeAccount;
    @StoreProcedureParam(param = "e_fecha_inicial", type = Types.VARCHAR,isOut = false)
	private String initDate;
    @StoreProcedureParam(param = "e_fecha_final", type = Types.VARCHAR,isOut = false)
	private String endDate;


}
