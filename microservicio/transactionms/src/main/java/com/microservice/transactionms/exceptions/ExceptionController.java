package com.microservice.transactionms.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.microservice.transactionms.dto.GenericResponse;
import com.microservice.transactionms.utils.LogUtils;
import com.microservice.transactionms.utils.ResponseDictionary;

import jakarta.validation.ConstraintViolationException;

import java.sql.SQLException;

@ControllerAdvice
public class ExceptionController {
 
  private static final LogUtils logs = LogUtils.getLogger(ExceptionController.class);

    
  @ExceptionHandler(SQLException.class)
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public ResponseEntity<GenericResponse<Void>> handeSqlException(SQLException exception) {
    GenericResponse<Void> response = new GenericResponse(ResponseDictionary.ERRSQL);
    String messsage = exception.getMessage();
    try{
      messsage = messsage.substring(messsage.indexOf("/")+1,messsage.indexOf("\n"));
      response.setResponseCode(400);
    }catch (Exception e){
      messsage = exception.getMessage();
    }
    logs.error("ERROR AL EJECUTAR ACCION: ",exception);
    response.setMessage(messsage);
    return ResponseEntity.status(response.getResponseCode()).body(response);
  }

  @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<GenericResponse<Void> > handleValidationErrors(ConstraintViolationException ex) {
      GenericResponse<Void> response = new GenericResponse(ResponseDictionary.VALID);
      response.setMessage(ex.getConstraintViolations().iterator().next().getMessage());
      logs.error("ERROR AL EJECUTAR ACCION: ",ex);
      return ResponseEntity.status(response.getResponseCode()).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GenericResponse<Void> > handleExceptionErrors(Exception ex) {
      GenericResponse<Void> response = new GenericResponse(ResponseDictionary.VALID);
      response.setMessage(ex.getMessage());
      logs.error("ERROR AL EJECUTAR ACCION: ",ex);
      return ResponseEntity.status(response.getResponseCode()).body(response);
    }


}
