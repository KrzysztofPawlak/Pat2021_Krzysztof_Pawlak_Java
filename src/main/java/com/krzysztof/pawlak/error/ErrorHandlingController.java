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

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponse> notFoundException(ResourceNotFoundException e) {
        var exceptionResponse = new ExceptionResponse();
        exceptionResponse.setDescription(e.getDescription());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exceptionResponse);
    }

    @ExceptionHandler(CalculationConstrainException.class)
    public ResponseEntity<ExceptionResponse> inputSizeExceedException(CalculationConstrainException e) {
        var exceptionResponse = new ExceptionResponse();
        exceptionResponse.setDescription(e.getDescription());
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(exceptionResponse);
    }

    @ExceptionHandler(InputParseException.class)
    public ResponseEntity<ExceptionResponse> parseException(InputParseException e) {
        var exceptionResponse = new ExceptionResponse();
        exceptionResponse.setDescription(e.getDescription());
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(exceptionResponse);
    }

    @ExceptionHandler(UnprocessableException.class)
    public ResponseEntity<ExceptionResponse> parseLogsException(UnprocessableException e) {
        var exceptionResponse = new ExceptionResponse();
        exceptionResponse.setDescription(e.getDescription());
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(exceptionResponse);
    }
}