package bootcamp.proposta.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Base64;

public class IsBase64Validator implements ConstraintValidator<IsBase64, byte[]> {
    private final Base64.Decoder decoder = Base64.getDecoder();

    @Override
    public boolean isValid(byte[] value, ConstraintValidatorContext context) {
        try {
            decoder.decode(value);
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }
}
