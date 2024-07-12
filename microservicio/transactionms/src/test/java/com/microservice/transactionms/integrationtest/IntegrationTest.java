package com.microservice.transactionms.integrationtest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.transactionms.TransactionMsApplication;
import com.microservice.transactionms.dto.CreateAccountRequest;
import com.microservice.transactionms.dto.CreateMovementRequest;
import com.microservice.transactionms.dto.GenericResponse;
import com.microservice.transactionms.dto.QueryAccountResponse;
import com.microservice.transactionms.dto.UpdateAccountRequest;
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TransactionMsApplication.class)
@ActiveProfiles("test")
public class IntegrationTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    
    private MockMvc mockMvc;
    
    private ObjectMapper mapper = new ObjectMapper();
    
    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    
    @Test
    public void createAccount() throws Exception {
        CreateAccountRequest request = new CreateAccountRequest("CTE");
        
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/cuentas?code=1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andReturn();
        
        String jsonResponse = result.getResponse().getContentAsString();
        GenericResponse<Integer> genericResponse = mapper.readValue(jsonResponse, new TypeReference<GenericResponse<Integer>>() {});
        assertNotNull(genericResponse);
    }
    
    @Test
    public void queryAccount() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/cuentas?code=80"))
                .andReturn();
        
                String jsonResponse = result.getResponse().getContentAsString();

                GenericResponse<List<QueryAccountResponse>> response = mapper.readValue(jsonResponse, new TypeReference<GenericResponse<List<QueryAccountResponse>>>() {});        
        
        assertNotNull(response);
        assertEquals(500, response.getCode());
    }
    
    @Test
    public void updateAccount() throws Exception {
        UpdateAccountRequest request = new UpdateAccountRequest(0f,"V");
        
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/cuentas?code=80&account=000001225")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andReturn();
        
                String jsonResponse = result.getResponse().getContentAsString();

                GenericResponse<Void> response = mapper.readValue(jsonResponse, new TypeReference<GenericResponse<Void>>() {});        
        
        assertNotNull(response);
        assertEquals(500, response.getCode());
    }
    
    @Test
    public void deleteAccount() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/cuentas?code=80&account=000001225"))
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        GenericResponse<Void> response = mapper.readValue(jsonResponse, new TypeReference<GenericResponse<Void>>() {});        
        
        assertNotNull(response);
        assertEquals(500, response.getCode());
    }

    @Test
    public void createMovement() throws Exception {
        CreateMovementRequest request = new CreateMovementRequest(1,"000001256",126.22f,"DEPOS");
        
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/movimientos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andReturn();
        
        String jsonResponse = result.getResponse().getContentAsString();
        GenericResponse<Integer> genericResponse = mapper.readValue(jsonResponse, new TypeReference<GenericResponse<Integer>>() {});
        assertNotNull(genericResponse);
    }

    @Test
    public void reverseMovement() throws Exception {
        CreateMovementRequest request = new CreateMovementRequest(1,"000001256",126.22f,"DEPOS");
        
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/movimientos?sequence=1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andReturn();
        
        String jsonResponse = result.getResponse().getContentAsString();
        GenericResponse<Integer> genericResponse = mapper.readValue(jsonResponse, new TypeReference<GenericResponse<Integer>>() {});
        assertNotNull(genericResponse);
    }

    @Test
    public void queryMovementReport() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/movimientos?code=1&initDate=06/06/2024&endDate=06/06/2024"))
                .andReturn();        
                String jsonResponse = result.getResponse().getContentAsString();
                GenericResponse<List<QueryAccountResponse>> response = mapper.readValue(jsonResponse, new TypeReference<GenericResponse<List<QueryAccountResponse>>>() {});        
        
        assertNotNull(response);
        assertEquals(10, response.getCode());
    }

}


