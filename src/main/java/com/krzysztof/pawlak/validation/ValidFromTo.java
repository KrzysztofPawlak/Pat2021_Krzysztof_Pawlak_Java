package com.krzysztof.pawlak.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = { FromToValidator.class })
public @interface ValidFromTo {
    String message() default "Parameter: 'to' can't be lower than parameter: 'from'";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}