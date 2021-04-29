package com.punko.rest.exceptions;

import com.punko.rest.exceptions.apartmentExceptions.ApartmentNoSuchClassException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ApartmentIncorrectData> handlerApartmentWrongClassException(
            ApartmentNoSuchClassException exception) {
        ApartmentIncorrectData incorrectData = new ApartmentIncorrectData();
        incorrectData.setInfo(exception.getMessage());
        return new ResponseEntity<>(incorrectData, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ApartmentIncorrectData> handlerIllegalArgumentException(
            IllegalArgumentException exception) {
        ApartmentIncorrectData incorrectData = new ApartmentIncorrectData();
        incorrectData.setInfo(exception.getMessage() + " - custom exception in DAO");
        return new ResponseEntity<>(incorrectData, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}


