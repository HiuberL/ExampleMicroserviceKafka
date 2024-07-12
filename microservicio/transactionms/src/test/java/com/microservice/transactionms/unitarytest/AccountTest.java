package com.microservice.transactionms.unitarytest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.lang.reflect.InvocationTargetException;
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
import com.microservice.transactionms.controllers.AccountController;
import com.microservice.transactionms.dto.CreateAccountRequest;
import com.microservice.transactionms.dto.GenericResponse;
import com.microservice.transactionms.dto.QueryAccountResponse;
import com.microservice.transactionms.dto.UpdateAccountRequest;
import com.microservice.transactionms.services.AccountService;
import com.microservice.transactionms.utils.LogUtils;
import com.microservice.transactionms.utils.ResponseDictionary;

class AccountTest {
    @InjectMocks
    private AccountController accountController;

    @Mock
    private AccountService accountService;

    @Mock
    private DataSourceManager dataSourceManager;

    @Mock
    private LogUtils logUtils;

    @Mock
    private Initializer initializer;

    @Mock
    private CreateAccountRequest request;


    @Mock
    private UpdateAccountRequest requestUpdate;

    private GenericResponse response;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createAccountTest() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException {
        request = new CreateAccountRequest("AHO");
        response = new GenericResponse<>(ResponseDictionary.OK);
        when(accountService.createAccount(47,request)).thenReturn(response);
        ResponseEntity<GenericResponse<Void>> result = accountController.createMappingAccount(47,request);
        assertEquals(response, result.getBody());
    }

    @Test
    public void queryAccountTest() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException {
        response = new GenericResponse<>(ResponseDictionary.OK);
        when(accountService.queryAccount(47)).thenReturn(response);
        ResponseEntity<GenericResponse<List<QueryAccountResponse>>> result = accountController.queryMappingAccountSpecify(47);
        assertEquals(response, result.getBody());
    }

    @Test
    public void updateAccountTest() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException {
        requestUpdate =new UpdateAccountRequest(0f, "V");
        response = new GenericResponse<>(ResponseDictionary.OK);
        when(accountService.updateAccount(47,"000001225",requestUpdate)).thenReturn(response);
        ResponseEntity<GenericResponse<Void>> result = accountController.updateMappingAccount(47, "000001225",requestUpdate);
        assertEquals(response, result.getBody());
    }

    @Test
    public void deleteAccountTest() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException {
        response = new GenericResponse<>(ResponseDictionary.OK);
        when(accountService.deleteAccount(47,"000001225")).thenReturn(response);
        ResponseEntity<GenericResponse<Void>> result = accountController.deleteMappingAccount(47,"000001225");
        assertEquals(response, result.getBody());
    }

}
