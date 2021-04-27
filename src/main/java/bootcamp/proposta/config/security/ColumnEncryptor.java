package bootcamp.proposta.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.security.InvalidKeyException;
import java.security.Key;
import java.util.Base64;

@Component
@Converter
public class ColumnEncryptor implements AttributeConverter<String, String> {
    private static final String ENCRYPT_TYPE = "AES";

    private final Key key;
    private final Cipher cipher;

    public ColumnEncryptor(@Value("${academy.aes-key}") String SECRET) throws Exception {
        key = new SecretKeySpec(SECRET.getBytes(), ENCRYPT_TYPE);
        cipher = Cipher.getInstance(ENCRYPT_TYPE);
    }

    @Override
    public String convertToDatabaseColumn(String attribute) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return Base64.getEncoder().encodeToString(cipher.doFinal(attribute.getBytes()));
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            throw new IllegalStateException(ex);
        }
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, key);
            return new String(cipher.doFinal(Base64.getDecoder().decode(dbData)));
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            throw new IllegalStateException(ex);
        }
    }
}
