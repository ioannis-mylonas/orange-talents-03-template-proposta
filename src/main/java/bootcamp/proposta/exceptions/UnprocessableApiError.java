package bootcamp.proposta.exceptions;

import org.springframework.http.HttpStatus;

public class UnprocessableApiError extends ApiError {
    public UnprocessableApiError() {
        super(HttpStatus.UNPROCESSABLE_ENTITY, "Não foi possível processar o pedido.");
    }
}
