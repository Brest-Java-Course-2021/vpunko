package com.punko.model.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckDateValidator.class)
@Documented
public @interface CheckDate {

    String message() default "Arrival time should be before than Departure time";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


}
