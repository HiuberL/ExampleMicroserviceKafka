package com.microservice.transactionms.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.microservice.transactionms.dto.CreateAccountRequest;
import com.microservice.transactionms.dto.GenericResponse;
import com.microservice.transactionms.dto.QueryAccountResponse;
import com.microservice.transactionms.dto.UpdateAccountRequest;
import com.microservice.transactionms.services.AccountService;

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
@Tag(name = "AccountController", description = "APIs CRUD que invoca a los métodos de cuentas")
@RequestMapping("/api/v1/cuentas")
public class AccountController {
    
    @Autowired
    private AccountService accountService;


    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Obtiene el listado de cuentas por cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa"),
            @ApiResponse(responseCode = "500", description = "Error Inesperado",
                         content = @io.swagger.v3.oas.annotations.media.Content(schema = @Schema(implementation = GenericResponse.class), examples = {
                             @ExampleObject(name = "Error Ejemplo", value = "{\"code\": 500, \"name\": \"ERROR EN POSTGRESQL\"}")
                         }))
    })
    public ResponseEntity<GenericResponse<List<QueryAccountResponse>>> queryMappingAccountSpecify(@RequestParam(required = true) Integer code ) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException  {
        GenericResponse<List<QueryAccountResponse>> response = this.accountService.queryAccount(code);
        return ResponseEntity.status(response.getResponseCode()).body(response);
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Crea una cuenta a partir del cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa"),
            @ApiResponse(responseCode = "500", description = "Error Inesperado",
                         content = @io.swagger.v3.oas.annotations.media.Content(schema = @Schema(implementation = GenericResponse.class), examples = {
                             @ExampleObject(name = "Error Ejemplo", value = "{\"code\": 500, \"name\": \"CLIENTE NO EXISTE\"}")
                         })),
            @ApiResponse(responseCode = "400", description = "Error por validación",
            content = @io.swagger.v3.oas.annotations.media.Content(schema = @Schema(implementation = GenericResponse.class), examples = {
                @ExampleObject(name = "Error Ejemplo", value = "{\"code\": 10, \"name\": \"Tipo de cuenta es Inválido\"}")
            })),
             
    })
    public ResponseEntity<GenericResponse<Void>> createMappingAccount(@RequestParam(required = true) Integer code, @RequestBody CreateAccountRequest request) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException  {
        GenericResponse<Void> response = this.accountService.createAccount(code,request);
        return ResponseEntity.status(response.getResponseCode()).body(response);
    }

    @PutMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Actualiza el estado de una cuenta en específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa"),
            @ApiResponse(responseCode = "500", description = "Error Inesperado",
                         content = @io.swagger.v3.oas.annotations.media.Content(schema = @Schema(implementation = GenericResponse.class), examples = {
                             @ExampleObject(name = "Error Ejemplo", value = "{\"code\": 500, \"name\": \"CUENTA NO EXISTE\"}")
                         })),
            @ApiResponse(responseCode = "400", description = "Error por validación",
            content = @io.swagger.v3.oas.annotations.media.Content(schema = @Schema(implementation = GenericResponse.class), examples = {
                @ExampleObject(name = "Error Ejemplo", value = "{\"code\": 10, \"name\": \"Cuenta es  Inválida\"}")
            })),
             
    })
    public ResponseEntity<GenericResponse<Void>> updateMappingAccount(@RequestParam(required = true) Integer code,@RequestParam(required = true) String account , @RequestBody UpdateAccountRequest request) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException  {
        GenericResponse<Void> response = this.accountService.updateAccount(code,account,request);
        return ResponseEntity.status(response.getResponseCode()).body(response);
    }


    @DeleteMapping(value = "",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Elimina la cuenta de forma lógica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa"),
            @ApiResponse(responseCode = "500", description = "Error Inesperado",
                         content = @io.swagger.v3.oas.annotations.media.Content(schema = @Schema(implementation = GenericResponse.class), examples = {
                             @ExampleObject(name = "Error Ejemplo", value = "{\"code\": 500, \"name\": \"CUENTA NO EXISTE\"}")
                         })),
            @ApiResponse(responseCode = "400", description = "Error por validación",
            content = @io.swagger.v3.oas.annotations.media.Content(schema = @Schema(implementation = GenericResponse.class), examples = {
                @ExampleObject(name = "Error Ejemplo", value = "{\"code\": 10, \"name\": \"Cuenta es Inválida\"}")
            })),
             
    })
    public ResponseEntity<GenericResponse<Void>> deleteMappingAccount(@RequestParam(required = true) Integer code,@RequestParam(required = true) String account) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException  {
        GenericResponse<Void> response = this.accountService.deleteAccount(code,account);
        return ResponseEntity.status(response.getResponseCode()).body(response);
    }


}
