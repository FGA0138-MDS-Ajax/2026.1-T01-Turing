package br.com.seuespacounb.turing.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = HorarioValidoValidator.class)
public @interface HorarioValido {
    String message() default "O horário de início deve ser anterior ao horário de fim";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}