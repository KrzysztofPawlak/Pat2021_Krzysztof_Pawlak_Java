package com.krzysztof.pawlak.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.naming.OperationNotSupportedException;

@ControllerAdvice
@ResponseBody
public class ErrorHandlingController {

    @ExceptionHandler(OperationNotSupportedException.class)
    public ResponseEntity<ExceptionResponse> operationNotSupportedException(OperationNotSupportedException e) {
        var exceptionResponse = new ExceptionResponse();
        exceptionResponse.setDescription(e.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_IMPLEMENTED)
                .body(exceptionResponse);
    }

    @ExceptionHandler(CalculationNotImplementedException.class)
    public ResponseEntity<ExceptionResponse> calculationNotImplementedException(CalculationNotImplementedException e) {
        var exceptionResponse = new ExceptionResponse();
        exceptionResponse.setDescription(e.getDescription());
        return ResponseEntity
                .status(HttpStatus.NOT_IMPLEMENTED)
                .body(exceptionResponse);
    }

    @ExceptionHandler(FileNotExistException.class)
    public ResponseEntity<ExceptionResponse> notFoundException(FileNotExistException e) {
        var exceptionResponse = new ExceptionResponse();
        exceptionResponse.setDescription(e.getDescription());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exceptionResponse);
    }
}