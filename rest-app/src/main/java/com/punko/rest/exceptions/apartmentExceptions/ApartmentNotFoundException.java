package com.punko.rest.exceptions.apartmentExceptions;

public class ApartmentNotFoundException extends RuntimeException {

    public ApartmentNotFoundException(Integer id) {
        super("Apartment not found for id: " + id);
    }
}
