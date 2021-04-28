package com.punko.model.validation;

import com.punko.model.Resident;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CheckDateValidator implements ConstraintValidator<CheckDate, Resident> {

    @Override
    public void initialize(CheckDate constraintAnnotation) {
    }

    @Override
    public boolean isValid(Resident resident, ConstraintValidatorContext context) {
        if (resident == null) {
            return true;
        } else if (resident.getArrivalTime() == null && resident.getDepartureTime() == null) {
            return false;
        }
        return resident.getArrivalTime().isBefore(resident.getDepartureTime());
    }

}
