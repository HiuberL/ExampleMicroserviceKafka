package com.microservice.entidadms.unitarytest;

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

import com.microservice.entidadms.configurations.DataSourceManager;
import com.microservice.entidadms.configurations.Initializer;
import com.microservice.entidadms.controllers.PersonController;
import com.microservice.entidadms.dto.CreatePersonRequest;
import com.microservice.entidadms.dto.GenericResponse;
import com.microservice.entidadms.dto.QueryPersonResponse;
import com.microservice.entidadms.dto.UpdatePersonRequest;
import com.microservice.entidadms.services.PersonService;
import com.microservice.entidadms.utils.LogUtils;
import com.microservice.entidadms.utils.ResponseDictionary;

class ClienteTest {

    @InjectMocks
    private PersonController personController;

    @Mock
    private PersonService personService;

    @Mock
    private DataSourceManager dataSourceManager;

    @Mock
    private LogUtils logUtils;

    @Mock
    private Initializer initializer;

    @Mock
    private CreatePersonRequest request;


    @Mock
    private UpdatePersonRequest requestUpdate;

    private GenericResponse response;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createPersonTest() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException {
        request = new CreatePersonRequest("0931850358", "KEVIN IVÁN", "LIZANO", "FLORES", "M", 28,"ECUADOR", "0999485951", "klizanof140");
        response = new GenericResponse<>(ResponseDictionary.OK);
        when(personService.createPerson(request)).thenReturn(response);
        ResponseEntity<GenericResponse<Integer>> result = personController.postMappngPersonCreate(request);
        assertEquals(response, result.getBody());
    }

    @Test
    public void queryPersonTest() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException {
        response = new GenericResponse<>(ResponseDictionary.OK);
        when(personService.queryPerson("dni",1)).thenReturn(response);
        ResponseEntity<GenericResponse<List<QueryPersonResponse>>> result = personController.queryMappingPersonSpecify("dni", 1);
        assertEquals(response, result.getBody());
    }

    @Test
    public void updatePersonTest() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException {
        response = new GenericResponse<>(ResponseDictionary.OK);
        when(personService.updatePerson("dni", requestUpdate)).thenReturn(response);
        ResponseEntity<GenericResponse<Void>> result = personController.updateMappingPerson("dni", requestUpdate);
        assertEquals(response, result.getBody());
    }

    @Test
    public void deletePersonTest() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException {
        response = new GenericResponse<>(ResponseDictionary.OK);
        when(personService.deletePerson("dni")).thenReturn(response);
        ResponseEntity<GenericResponse<Void>> result = personController.deleteMappingPerson("dni");
        assertEquals(response, result.getBody());
    }

}