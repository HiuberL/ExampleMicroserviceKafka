package com.microservice.entidadms.dao;

import com.microservice.entidadms.utils.annotations.StoreProcedure;
import com.microservice.entidadms.utils.annotations.StoreProcedureParam;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.sql.Types;

@Getter
@Setter
@AllArgsConstructor
@StoreProcedure(schema = "public", catalog = "postgres", name = "cconsulta_cliente")
public class QueryPersonFun {
    
    @StoreProcedureParam(param = "e_identificacion", type = Types.VARCHAR,isOut = false)
	private String dni;
    @StoreProcedureParam(param = "e_tipo", type = Types.VARCHAR,isOut = false)
	private String type;
    @StoreProcedureParam(param = "e_ultimo", type = Types.INTEGER,isOut = false)
	private Integer page;

}
