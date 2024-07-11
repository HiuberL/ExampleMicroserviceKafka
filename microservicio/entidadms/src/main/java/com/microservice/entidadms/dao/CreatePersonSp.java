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
@StoreProcedure(schema = "public", catalog = "postgres", name = "pa_icrea_persona")
public class CreatePersonSp {
    
    @StoreProcedureParam(param = "e_identificacion", type = Types.VARCHAR,isOut = false)
	private String dni;
    
    @StoreProcedureParam(param = "e_nombre", type = Types.VARCHAR,isOut = false)
	private String name;
    
    @StoreProcedureParam(param = "e_apellido_p", type = Types.VARCHAR,isOut = false)
	private String lastName;
    
    @StoreProcedureParam(param = "e_apellido_m", type = Types.VARCHAR,isOut = false)
	private String sLastName;
    
    @StoreProcedureParam(param = "e_genero", type = Types.VARCHAR,isOut = false)
	private String gender;
    
    @StoreProcedureParam(param = "e_edad", type = Types.INTEGER,isOut = false)
	private Integer age;
    
    @StoreProcedureParam(param = "e_direccion", type = Types.VARCHAR,isOut = false)
	private String address;
    
    @StoreProcedureParam(param = "e_telefono", type = Types.VARCHAR,isOut = false)
	private String phone;

    @StoreProcedureParam(param = "e_contrasena", type = Types.VARCHAR,isOut = false)
	private String pass;

    @StoreProcedureParam(param = "o_codigo", type = Types.INTEGER,isOut = true)
	private Integer code;

}
