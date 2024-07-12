package com.microservice.transactionms.unitarytest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.microservice.transactionms.configurations.DataSourceManager;
import com.microservice.transactionms.configurations.Initializer;
import com.microservice.transactionms.controllers.MovementController;
import com.microservice.transactionms.dto.CreateMovementRequest;
import com.microservice.transactionms.dto.GenericResponse;
import com.microservice.transactionms.dto.ReportMovementResponse;
import com.microservice.transactionms.services.MovementService;
import com.microservice.transactionms.utils.LogUtils;
import com.microservice.transactionms.utils.ResponseDictionary;

class MovementTest {
    @InjectMocks
    private MovementController movementController;

    @Mock
    private MovementService movementService;

    @Mock
    private DataSourceManager dataSourceManager;

    @Mock
    private LogUtils logUtils;

    @Mock
    private Initializer initializer;

    @Mock
    private CreateMovementRequest request;

    private GenericResponse response;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createMovementTest() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException {
        request = new CreateMovementRequest(47,"000001256",126.22f,"DEPOS");
        response = new GenericResponse<>(ResponseDictionary.OK);
        when(movementService.createMovement(request)).thenReturn(response);
        ResponseEntity<GenericResponse<BigInteger>> result = movementController.createMappingMovement(request);
        assertEquals(response, result.getBody());
    }

    @Test
    public void queryMovementTest() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException {
        response = new GenericResponse<>(ResponseDictionary.OK);
        when(movementService.generateReport(47,"06/06/2024","06/06/2024")).thenReturn(response);
        ResponseEntity<GenericResponse<List<ReportMovementResponse>>> result = movementController.queryMappingAccountSpecify(47,"06/06/2024","06/06/2024");
        assertEquals(response, result.getBody());
    }


    @Test
    public void deleteMovementTest() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException {
        response = new GenericResponse<>(ResponseDictionary.OK);
        when(movementService.deleteMovement(47L)).thenReturn(response);
        ResponseEntity<GenericResponse<Void>> result = movementController.deleteMappingMovement(47L);
        assertEquals(response, result.getBody());
    }

}
