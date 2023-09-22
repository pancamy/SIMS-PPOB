package com.sims.ppob.service;

import com.sims.ppob.exception.ValidationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

public class ValidationService {

    static void validate(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> fieldErrors = new HashMap<>();

            for (FieldError error : bindingResult.getFieldErrors()) {
                String attributeName = error.getField();
                fieldErrors.put(attributeName, error.getDefaultMessage());
            }

            throw new ValidationException("Validation error", fieldErrors);
        }
    }
}
