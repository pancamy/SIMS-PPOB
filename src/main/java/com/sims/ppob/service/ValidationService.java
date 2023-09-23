package com.sims.ppob.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
public class ValidationService {

    static void validate(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            FieldError firstError = bindingResult.getFieldErrors().get(0);
            String errorMessage = firstError.getDefaultMessage();

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errorMessage);

//            StringBuilder errorMessage = new StringBuilder();
//
//            for (FieldError error : bindingResult.getFieldErrors()) {
//                errorMessage.append(error.getDefaultMessage()).append("; ");
//            }
//
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errorMessage.toString());
        }
    }
}
