package com.punko.rest.exceptions.apartmentExceptions;

import static com.punko.model.constants.ApartmentClassConst.*;

public class ApartmentNoSuchClassException extends RuntimeException {

    public ApartmentNoSuchClassException() {
        super("Apartment class should be one of these: " + LUXURIOUS + ", " + MEDIUM + ", " + CHEAP);
    }
}
