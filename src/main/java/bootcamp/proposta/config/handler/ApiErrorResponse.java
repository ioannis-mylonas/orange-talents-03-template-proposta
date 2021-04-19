package bootcamp.proposta.config.handler;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApiErrorResponse {
    @JsonProperty
    private final String error;

    public ApiErrorResponse(String error) {
        this.error = error;
    }
}
