package com.punko.model.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckDateValidator.class)
@Documented
public @interface CheckDate {

    public String message() default "Arrival time should be before than Departure time";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};


}
