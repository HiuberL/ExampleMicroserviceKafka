package com.microservice.transactionms.services;

import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.transactionms.dto.CreateMovementRequest;
import com.microservice.transactionms.dto.GenericResponse;
import com.microservice.transactionms.dto.MovementsQueryResponse;
import com.microservice.transactionms.dto.QueryAccountResponse;
import com.microservice.transactionms.dto.ReportMovementResponse;
import com.microservice.transactionms.dto.ResponseDb;
import com.microservice.transactionms.repositories.MovementeRepository;
import com.microservice.transactionms.utils.LogUtils;
import com.microservice.transactionms.utils.ResponseDictionary;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;




@Service
@Validated
public class MovementService {
 

	private static final LogUtils logs = LogUtils.getLogger(MovementService.class);
    
    @Autowired
    private MovementeRepository movementeRepository;

    @Autowired
    private AccountService accountService;


    @SuppressWarnings({ "rawtypes", "unchecked" })
    public GenericResponse<BigInteger> createMovement(@Valid CreateMovementRequest request ) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException{
        logs.info(String.format("CREANDO MOVIMIENTO A CUENTA %s", request.getAccount()));
        GenericResponse<BigInteger> response = new GenericResponse(ResponseDictionary.OK);
        ResponseDb responseDb = this.movementeRepository.createMovement(request);
        logs.info(String.format("MOVIMIENTO A CUENTA %s CREADO", request.getAccount()));
        response.setData(BigInteger.valueOf(Long.valueOf(String.valueOf(responseDb.getOuts().get(0)))));
        return response;
    }        
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public GenericResponse<Void> deleteMovement(@Valid @NotNull(message = "Secuencia No puede ser nula") Long sequence ) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException{
        logs.info(String.format("ELIMINANDO MOVIMIENTO %d", sequence));
        GenericResponse<Void> response = new GenericResponse(ResponseDictionary.OK);
        this.movementeRepository.deleteMovement(sequence);
        logs.info(String.format("MOVIMIENTO %s ELIMINADO", sequence));
        return response;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public GenericResponse<List<ReportMovementResponse> > generateReport(int code, String iniDate, String endDate ) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException{
        ObjectMapper objectMapper = new ObjectMapper();
        logs.info(String.format("CONSULTANDO MOVIMIENTOS A CUENTA DE CLIENTE %s",String.valueOf(code)));
        GenericResponse<List<ReportMovementResponse>> response = new GenericResponse(ResponseDictionary.OK);
        GenericResponse<List<QueryAccountResponse>> queryAccount = this.accountService.queryAccount(code);
        Iterator<QueryAccountResponse> query = queryAccount.getData().iterator();
        List<ReportMovementResponse> reportMovementResponse = new ArrayList<>();
        while (query.hasNext()) {
            QueryAccountResponse queryAccountTemp = query.next();
            ReportMovementResponse movementTemp = new ReportMovementResponse(new ArrayList<>());
            ResponseDb responseDbTemp = this.movementeRepository.queryMovement(queryAccountTemp.getAccountCode(), iniDate, endDate);
            List<MovementsQueryResponse> responseList = objectMapper.convertValue(responseDbTemp.getDataset(), new TypeReference<List<MovementsQueryResponse>>() {});
            movementTemp.setAccountCode(queryAccountTemp.getAccountCode());
            movementTemp.setAccountNumber(queryAccountTemp.getAccountNumber());
            movementTemp.setAccountType(queryAccountTemp.getAccountType());
            movementTemp.setAvailableBalance(queryAccountTemp.getAvailableBalance());
            movementTemp.setStatus(queryAccountTemp.getStatus());
            movementTemp.setMovements(responseList);
            reportMovementResponse.add(movementTemp);
        }
        logs.info(String.format("MOVIMIENTOS CONSULTADOS A CLIENTES %s",String.valueOf(code)));
        response.setData(reportMovementResponse);
        return response;
    }        

}
