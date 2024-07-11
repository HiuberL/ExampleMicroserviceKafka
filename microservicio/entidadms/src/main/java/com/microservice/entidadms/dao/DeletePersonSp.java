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
@StoreProcedure(schema = "public", catalog = "postgres", name = "pa_delim_persona")
public class DeletePersonSp {
    
    @StoreProcedureParam(param = "e_identificacion", type = Types.VARCHAR,isOut = false)
	private String dni;
    

}
