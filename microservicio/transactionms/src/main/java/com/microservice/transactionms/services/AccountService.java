package com.microservice.transactionms.services;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.microservice.transactionms.dto.CreateAccountRequest;
import com.microservice.transactionms.dto.GenericResponse;
import com.microservice.transactionms.dto.QueryAccountResponse;
import com.microservice.transactionms.dto.ResponseDb;
import com.microservice.transactionms.dto.UpdateAccountRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.transactionms.repositories.AccountRepository;
import com.microservice.transactionms.utils.LogUtils;
import com.microservice.transactionms.utils.ResponseDictionary;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;

import java.util.List;




@Service
@Validated
public class AccountService {
 

	private static final LogUtils logs = LogUtils.getLogger(AccountService.class);
    
    @Autowired
    private AccountRepository accountRepository;


    @SuppressWarnings({ "rawtypes", "unchecked" })
    @KafkaListener(topics = "cliente-creado", groupId = "ms-clientes-mq")
    public void createAccountAsync(ConsumerRecord message) {
        ObjectMapper mapper = new ObjectMapper();
        try{
            GenericResponse<Integer> response =  mapper.readValue(message.value().toString(), GenericResponse.class);
            logs.info(String.format("Creando cuenta a cliente %s",response.getData()));
            accountRepository.createAccount(response.getData(), "AHO");   
            logs.info(String.format("Cuenta a cliente %s Creada",response.getData()));
        }catch(Exception e){
            logs.error("Error al crear cuenta: ", e);
        }
    }
    
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public GenericResponse<List<QueryAccountResponse>> queryAccount(Integer code) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException{
        ObjectMapper objectMapper = new ObjectMapper();
        logs.info("CONSULTANDO CUENTAS DE CLIENTES");
        GenericResponse<List<QueryAccountResponse>> response = new GenericResponse(ResponseDictionary.OK);
        ResponseDb responseDb = this.accountRepository.queryAccount(code);
        List<QueryAccountResponse> responseList = objectMapper.convertValue(responseDb.getDataset(), new TypeReference<List<QueryAccountResponse>>() {});
        logs.info("CUENTAS CONSULTADAS CON EXITO");
        response.setData(responseList);
        return response;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public GenericResponse<Void> updateAccount(Integer code,@Valid @Pattern(regexp="^[0-9]*$",message = "Cuenta Inválida") String account, UpdateAccountRequest request) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException{
        logs.info("ACTUALIZANDO CUENTAS DE CLIENTES");
        GenericResponse<Void> response = new GenericResponse(ResponseDictionary.OK);
        this.accountRepository.updateAccount(code,account,request);
        logs.info("ACTUALIZADO DE CUENTAS REALIZADO CON EXITO");
        return response;
    }    

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public GenericResponse<Void> createAccount(int code, @Valid CreateAccountRequest request ) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException{
        logs.info("CREANDO CUENTAS DE CLIENTES");
        GenericResponse<Void> response = new GenericResponse(ResponseDictionary.OK);
        this.accountRepository.createAccount(code, request.getType());
        logs.info("CUENTA DE CLIENTE CREADA");
        return response;
    }        
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public GenericResponse<Void> deleteAccount(Integer code,@Valid @Pattern(regexp="^[0-9]*$",message = "Cuenta Inválida") String account) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException{
        logs.info("ELIMINANDO CUENTAS DE CLIENTES");
        GenericResponse<Void> response = new GenericResponse(ResponseDictionary.OK);
        this.accountRepository.deleteAccount(code,account);
        logs.info("CUENTA DE CLIENTE ELIMINADA");
        return response;
    }    
}
