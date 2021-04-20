package bootcamp.proposta.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = IsBase64Validator.class)
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsBase64 {
    String message() default "Dados enviados não são Base64 válida!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
