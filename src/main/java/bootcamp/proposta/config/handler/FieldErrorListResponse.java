package bootcamp.proposta.config.handler;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.context.MessageSource;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.stream.Collectors;

public class FieldErrorListResponse {
    @JsonProperty
    private final List<FieldErrorResponse> errors;

    public FieldErrorListResponse(List<FieldError> errors, MessageSource messageSource) {
        this.errors = errors.stream()
                .map(i -> new FieldErrorResponse(i, messageSource))
                .collect(Collectors.toList());
    }
}
