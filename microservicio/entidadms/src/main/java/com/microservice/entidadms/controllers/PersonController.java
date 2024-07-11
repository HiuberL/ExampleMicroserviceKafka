package com.microservice.entidadms.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.microservice.entidadms.dto.CreatePersonRequest;
import com.microservice.entidadms.dto.GenericResponse;
import com.microservice.entidadms.dto.QueryPersonResponse;
import com.microservice.entidadms.dto.UpdatePersonRequest;
import com.microservice.entidadms.services.PersonService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;


@RestController
@Tag(name = "PersonController", description = "APIs CRUD que invoca a los métodos de persona y clientes")
@RequestMapping("/api/v1/clientes")
public class PersonController {
    
    @Autowired
    private PersonService personService;

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Crea una nueva persona - Cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Operación exitosa"),
            @ApiResponse(responseCode = "500", description = "Error Inesperado",
                         content = @io.swagger.v3.oas.annotations.media.Content(schema = @Schema(implementation = GenericResponse.class), examples = {
                             @ExampleObject(name = "Error Ejemplo", value = "{\"code\": 500, \"name\": \"CLIENTE YA REGISTRADO\"}")
                         })),
            @ApiResponse(responseCode = "400", description = "Error por validación",
            content = @io.swagger.v3.oas.annotations.media.Content(schema = @Schema(implementation = GenericResponse.class), examples = {
                @ExampleObject(name = "Error Ejemplo", value = "{\"code\": 10, \"name\": \"Cédula Inválida\"}")
            })),

    })
    public ResponseEntity<GenericResponse<Integer>> postMappngPersonCreate(@RequestBody CreatePersonRequest request ) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException  {
        GenericResponse<Integer> response = this.personService.createPerson(request);
        return ResponseEntity.status(response.getResponseCode()).body(response);
    }



    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Obtiene el listado de persona de maximo 10 clientes, en caso que se determine la cédula en el query param, se obtiene la información del cliente en específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa"),
            @ApiResponse(responseCode = "500", description = "Error Inesperado",
                         content = @io.swagger.v3.oas.annotations.media.Content(schema = @Schema(implementation = GenericResponse.class), examples = {
                             @ExampleObject(name = "Error Ejemplo", value = "{\"code\": 500, \"name\": \"ERROR EN POSTGRESQL\"}")
                         }))
    })
    public ResponseEntity<GenericResponse<List<QueryPersonResponse>>> queryMappingPersonSpecify(@RequestParam(required = false) String dni,@RequestParam(required = false,defaultValue = "0") Integer lastPosition ) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException  {
        GenericResponse<List<QueryPersonResponse>> response = this.personService.queryPerson(dni,lastPosition);
        return ResponseEntity.status(response.getResponseCode()).body(response);
    }

    @PutMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Actualiza un cliente en específico, no se puede actualizar la cédula")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa"),
            @ApiResponse(responseCode = "500", description = "Error Inesperado",
                         content = @io.swagger.v3.oas.annotations.media.Content(schema = @Schema(implementation = GenericResponse.class), examples = {
                             @ExampleObject(name = "Error Ejemplo", value = "{\"code\": 500, \"name\": \"CLIENTE NO HA SIDO REGISTRADO\"}")
                         })),
            @ApiResponse(responseCode = "400", description = "Error por validación",
            content = @io.swagger.v3.oas.annotations.media.Content(schema = @Schema(implementation = GenericResponse.class), examples = {
                @ExampleObject(name = "Error Ejemplo", value = "{\"code\": 10, \"name\": \"Cédula Inválida\"}")
            })),
             
    })
    public ResponseEntity<GenericResponse<Void>> updateMappingPerson(@RequestParam(required = true) String dni, @RequestBody UpdatePersonRequest request) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException  {
        GenericResponse<Void> response = this.personService.updatePerson(dni,request);
        return ResponseEntity.status(response.getResponseCode()).body(response);
    }


    @DeleteMapping(value = "",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Elimina un cliente en específico de forma lógica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa"),
            @ApiResponse(responseCode = "500", description = "Error Inesperado",
                         content = @io.swagger.v3.oas.annotations.media.Content(schema = @Schema(implementation = GenericResponse.class), examples = {
                             @ExampleObject(name = "Error Ejemplo", value = "{\"code\": 500, \"name\": \"CLIENTE NO HA SIDO REGISTRADO\"}")
                         })),
            @ApiResponse(responseCode = "400", description = "Error por validación",
            content = @io.swagger.v3.oas.annotations.media.Content(schema = @Schema(implementation = GenericResponse.class), examples = {
                @ExampleObject(name = "Error Ejemplo", value = "{\"code\": 10, \"name\": \"Cédula Inválida\"}")
            })),
             
    })
    public ResponseEntity<GenericResponse<Void>> deleteMappingPerson(@RequestParam(required = true) String dni) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException  {
        GenericResponse<Void> response = this.personService.deletePerson(dni);
        return ResponseEntity.status(response.getResponseCode()).body(response);
    }


}
