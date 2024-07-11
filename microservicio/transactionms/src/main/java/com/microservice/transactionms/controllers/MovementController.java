package com.microservice.transactionms.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.microservice.transactionms.dto.CreateMovementRequest;
import com.microservice.transactionms.dto.GenericResponse;
import com.microservice.transactionms.dto.ReportMovementResponse;
import com.microservice.transactionms.services.MovementService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@Tag(name = "MovementController", description = "APIs CRUD que invoca a los métodos de movimiento de cuentas")
@RequestMapping("/api/v1/movimientos")
public class MovementController {
    
    @Autowired
    private MovementService movementService;

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Crea movimiento a cuenta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa"),
            @ApiResponse(responseCode = "500", description = "Error Inesperado",
                         content = @io.swagger.v3.oas.annotations.media.Content(schema = @Schema(implementation = GenericResponse.class), examples = {
                             @ExampleObject(name = "Error Ejemplo", value = "{\"code\": 500, \"name\": \"CUENTA NO EXISTE\"}")
                         })),
            @ApiResponse(responseCode = "400", description = "Error por validación",
            content = @io.swagger.v3.oas.annotations.media.Content(schema = @Schema(implementation = GenericResponse.class), examples = {
                @ExampleObject(name = "Error Ejemplo", value = "{\"code\": 10, \"name\": \"Tipo de cuenta es Inválido\"}")
            })),
             
    })
    public ResponseEntity<GenericResponse<BigInteger>> createMappingMovement(@RequestBody CreateMovementRequest request) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException  {
        GenericResponse<BigInteger> response = this.movementService.createMovement(request);
        return ResponseEntity.status(response.getResponseCode()).body(response);
    }

    @DeleteMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Elimina movimiento a cuenta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa"),
            @ApiResponse(responseCode = "500", description = "Error Inesperado",
                         content = @io.swagger.v3.oas.annotations.media.Content(schema = @Schema(implementation = GenericResponse.class), examples = {
                             @ExampleObject(name = "Error Ejemplo", value = "{\"code\": 500, \"name\": \"CUENTA NO EXISTE\"}")
                         })),
            @ApiResponse(responseCode = "400", description = "Error por validación",
            content = @io.swagger.v3.oas.annotations.media.Content(schema = @Schema(implementation = GenericResponse.class), examples = {
                @ExampleObject(name = "Error Ejemplo", value = "{\"code\": 10, \"name\": \"Tipo de cuenta es Inválido\"}")
            })),
             
    })
    public ResponseEntity<GenericResponse<Void>> deleteMappingMovement(@RequestParam(required =  true) Long sequence) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException  {
        GenericResponse<Void> response = this.movementService.deleteMovement(sequence);
        return ResponseEntity.status(response.getResponseCode()).body(response);
    }

    @GetMapping(value = "reportes", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Obtiene Reporte de cuentas de cliente por fechas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa"),
            @ApiResponse(responseCode = "500", description = "Error Inesperado",
                         content = @io.swagger.v3.oas.annotations.media.Content(schema = @Schema(implementation = GenericResponse.class), examples = {
                             @ExampleObject(name = "Error Ejemplo", value = "{\"code\": 500, \"name\": \"ERROR EN POSTGRESQL\"}")
                         }))
    })
    public ResponseEntity<GenericResponse<List<ReportMovementResponse>>> queryMappingAccountSpecify(@RequestParam(required = true) Integer code,@RequestParam(required = true) String initDate, @RequestParam(required = true) String endDate ) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException  {
        GenericResponse<List<ReportMovementResponse>> response = this.movementService.generateReport(code,initDate,endDate);
        return ResponseEntity.status(response.getResponseCode()).body(response);
    }
}
