package bootcamp.proposta.config.handler;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.FieldError;

public class FieldErrorResponse {
    @JsonProperty
    String message;

    @JsonProperty
    String field;

    public FieldErrorResponse(FieldError error, MessageSource messageSource) {
        this.field = error.getField();
        this.message = messageSource.getMessage(error, LocaleContextHolder.getLocale());
    }
}
