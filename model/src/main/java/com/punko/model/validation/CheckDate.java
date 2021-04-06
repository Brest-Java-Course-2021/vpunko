package com.punko.model.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.time.LocalDate;
import java.util.Date;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckDateValidator.class)
@Documented
public @interface CheckDate {

    //   public LocalDate value();
    public String message() default "Arrival time should be before than Departure time";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

//    @Target(TYPE)
//    @Retention(RUNTIME)
//    @interface List {
//        CheckDate[] value();
//    }

}
