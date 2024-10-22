package com.krzysztof.pawlak.validation;

import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@ResponseBody
public class ValidationHandlingController {

    @ExceptionHandler(BindException.class)
    public Map<String, String> handleValidationExceptions(
            BindException ex) {
        Map<String, String> errors = new HashMap<>();
        if (ex.getBindingResult().hasErrors()) {
            ex.getBindingResult().getAllErrors().forEach(error -> {
                final var errorMessage = error.getDefaultMessage();
                String fieldName;
                if (error instanceof FieldError) {
                    fieldName = ((FieldError) error).getField();
                } else {
                    fieldName = error.getObjectName();
                }
                errors.put(fieldName, errorMessage);
            });
            return errors;
        }
        final var globalError = ex.getGlobalError();
        if (globalError != null) {
            errors.put("description", globalError.getDefaultMessage());
        }
        return errors;
    }
}