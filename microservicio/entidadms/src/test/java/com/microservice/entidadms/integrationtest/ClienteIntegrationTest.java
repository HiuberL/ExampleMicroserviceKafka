package com.microservice.entidadms.integrationtest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import com.microservice.entidadms.EntidadMsApplication;
import com.microservice.entidadms.dto.CreatePersonRequest;
import com.microservice.entidadms.dto.GenericResponse;
import com.microservice.entidadms.dto.QueryPersonResponse;
import com.microservice.entidadms.dto.UpdatePersonRequest;

import java.util.List;
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = EntidadMsApplication.class)
@ActiveProfiles("test")
public class ClienteIntegrationTest {
    
    @Autowired
    private WebApplicationContext webApplicationContext;
    
    private MockMvc mockMvc;
    
    private ObjectMapper mapper = new ObjectMapper();
    
    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    
    @Test
    public void createPerson() throws Exception {
        CreatePersonRequest request = new CreatePersonRequest("0931850358", "KEVIN IVÁN", "LIZANO", "FLORES", "M", 28,"ECUADOR", "0999485951", "klizanof140");
        
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andReturn();
        
        String jsonResponse = result.getResponse().getContentAsString();
        GenericResponse<Integer> genericResponse = mapper.readValue(jsonResponse, new TypeReference<GenericResponse<Integer>>() {});
        assertNotNull(genericResponse);
    }
    
    @Test
    public void queryPerson() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/clientes"))
                .andExpect(status().isOk())
                .andReturn();
        
                String jsonResponse = result.getResponse().getContentAsString();

                GenericResponse<List<QueryPersonResponse>> response = mapper.readValue(jsonResponse, new TypeReference<GenericResponse<List<QueryPersonResponse>>>() {});        
        
        assertNotNull(response);
        assertEquals(0, response.getCode());
    }
    
    @Test
    public void updatePerson() throws Exception {
        UpdatePersonRequest request = new UpdatePersonRequest("KEVIN IVÁN", "LIZANO", "FLORES", "M", 28,"ECUADOR", "0999485951", "klizanof140","V");
        
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/clientes?dni=0931850358")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn();
        
                String jsonResponse = result.getResponse().getContentAsString();

                GenericResponse<Void> response = mapper.readValue(jsonResponse, new TypeReference<GenericResponse<Void>>() {});        
        
        assertNotNull(response);
        assertEquals(0, response.getCode());
    }
    
    @Test
    public void deletePerson() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/clientes?dni=0931850358"))
                .andExpect(status().isOk())
                .andReturn();

                String jsonResponse = result.getResponse().getContentAsString();
                GenericResponse<Void> response = mapper.readValue(jsonResponse, new TypeReference<GenericResponse<Void>>() {});        
        
        assertNotNull(response);
        assertEquals(0, response.getCode());
    }
}
