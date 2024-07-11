package com.microservice.entidadms.services;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.microservice.entidadms.dto.GenericResponse;
import com.microservice.entidadms.dto.QueryPersonResponse;
import com.microservice.entidadms.dto.ResponseDb;
import com.microservice.entidadms.dto.UpdatePersonRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.entidadms.configurations.Initializer;
import com.microservice.entidadms.dto.CreatePersonRequest;
import com.microservice.entidadms.repositories.PersonRepository;
import com.microservice.entidadms.utils.LogUtils;
import com.microservice.entidadms.utils.ResponseDictionary;
import com.microservice.entidadms.utils.annotations.Cedula;

import jakarta.validation.Valid;

import java.util.List;




@Service
@Validated
public class PersonService {
 
	private static final LogUtils logs = LogUtils.getLogger(PersonService.class);

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public GenericResponse<Integer> createPerson(@Valid CreatePersonRequest request) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException{
        GenericResponse<Integer> response = new GenericResponse(ResponseDictionary.CREATED);
        logs.info("INICIANDO LLAMADA A CREACION DE PERSONA");
        ResponseDb responseDb = this.personRepository.createPerson(request);
        logs.info("CREACION DE PERSONA REALIZADA");
        response.setData(Integer.parseInt(String.valueOf(responseDb.getOuts().get(0))));
        logs.info("ENVIANDO MENSAJE A COLA CLIENTE-CREADO");
        kafkaTemplate.send("cliente-creado", response);
        logs.info("MENSAJE ENVIADO A COLA CLIENTE-CREADO");
        return response;
    }
    
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public GenericResponse<List<QueryPersonResponse>> queryPerson(String dni,Integer page) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException{
        logs.info("INICIANDO LLAMADA A CONSULTA DE PERSONA");
        ObjectMapper objectMapper = new ObjectMapper();
        if (dni == null){
            dni = "";
        }
        GenericResponse<List<QueryPersonResponse>> response = new GenericResponse(ResponseDictionary.OK);
        ResponseDb responseDb = this.personRepository.queryPerson(dni,page);
        List<QueryPersonResponse> responseList = objectMapper.convertValue(responseDb.getDataset(), new TypeReference<List<QueryPersonResponse>>() {});
        logs.info("CONSULTA DE PERSONA REALIZADA CON ÉXITO");
        response.setData(responseList);
        return response;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public GenericResponse<Void> updatePerson(@Valid @Cedula(message = "Cédula es inválida") String dni, UpdatePersonRequest request) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException{
        logs.info("INICIANDO LLAMADA A ACTUALIZACION DE PERSONA");
        GenericResponse<Void> response = new GenericResponse(ResponseDictionary.OK);
        this.personRepository.updatePerson(dni,request);
        logs.info("ACTUALIZACION A PERSONA REALIZADO");
        return response;
    }    
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public GenericResponse<Void> deletePerson(@Valid @Cedula(message = "Cédula es inválida") String dni) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException{
        logs.info("INICIANDO LLAMADA A BORRADO DE PERSONA");
        GenericResponse<Void> response = new GenericResponse(ResponseDictionary.OK);
        this.personRepository.deletePerson(dni);
        logs.info("BORRADO LÓGICO A PERSONA REALIZADO");
        return response;
    }    
}
