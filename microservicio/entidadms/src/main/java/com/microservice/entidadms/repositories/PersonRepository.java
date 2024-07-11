package com.microservice.entidadms.repositories;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.microservice.entidadms.dao.CreatePersonSp;
import com.microservice.entidadms.dao.DeletePersonSp;
import com.microservice.entidadms.dao.QueryPersonFun;
import com.microservice.entidadms.dao.UpdatePersonSp;
import com.microservice.entidadms.dto.CreatePersonRequest;
import com.microservice.entidadms.dto.ResponseDb;
import com.microservice.entidadms.dto.UpdatePersonRequest;
import com.microservice.entidadms.utils.abtracts.OrmSpAbstract;

@Repository
public class PersonRepository extends OrmSpAbstract {
    
    /**
     * @param request
     * @apiNote Llama al procedimiento pa_icrea_persona para crear a la persona e indica las validaciones correspondientes
     * @return
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws SQLException
     */
    public ResponseDb createPerson(CreatePersonRequest request) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException{
		CreatePersonSp createPersonSp = new CreatePersonSp(request.getDni(),
                                                            request.getName(),
                                                            request.getLastName(),
                                                            request.getSLastName(),
                                                            request.getGender(),
                                                            request.getAge(),
                                                            request.getAddress(),
                                                            request.getPhone(),
                                                            request.getPassword(),
                                                            0);
		return this.executeProcedure(createPersonSp, false,1);
	}

  /**
   * @param dni
   * @param page
   * @apiNote Llama al procedimiento cconsulta_cliente para consultar información de personas
   * @return
   * @throws NoSuchMethodException
   * @throws SecurityException
   * @throws IllegalAccessException
   * @throws IllegalArgumentException
   * @throws InvocationTargetException
   * @throws SQLException
   */
  public ResponseDb queryPerson(String dni, Integer page) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException{
    String mode = "S";
    dni = dni.trim();
    if (dni.equals("") ){
      mode = "T";
    }
    QueryPersonFun queryPersonFun = new QueryPersonFun(dni,mode,page);
		return this.executeProcedure(queryPersonFun, true,2);
	}


  /**
   * @param dni
   * @param request
   * @apiNote Llama al procedimiento pa_uact_persona para actualizar información de personas
   * @return
   * @throws NoSuchMethodException
   * @throws SecurityException
   * @throws IllegalAccessException
   * @throws IllegalArgumentException
   * @throws InvocationTargetException
   * @throws SQLException
   */
  public ResponseDb updatePerson(String dni,UpdatePersonRequest request) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException{
		UpdatePersonSp updatePersonSp = new UpdatePersonSp(dni,
                                                            request.getName(),
                                                            request.getLastName(),
                                                            request.getSLastName(),
                                                            request.getGender(),
                                                            request.getAge(),
                                                            request.getAddress(),
                                                            request.getPhone(),
                                                            request.getPassword(),
                                                            request.getState());
		return this.executeProcedure(updatePersonSp, false,1);
	}

  /**
   * @param dni
   * @apiNote Llama al procedimiento pa_delim_persona para eliminar información de personas de forma física
   * @return
   * @throws NoSuchMethodException
   * @throws SecurityException
   * @throws IllegalAccessException
   * @throws IllegalArgumentException
   * @throws InvocationTargetException
   * @throws SQLException
   */
  public ResponseDb deletePerson(String dni) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException{
		DeletePersonSp deletePersonSp = new DeletePersonSp(dni);
		return this.executeProcedure(deletePersonSp, false,1);
	}


}
