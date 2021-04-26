package bootcamp.proposta.exceptions;

import org.springframework.http.HttpStatus;

public class NotFoundApiError extends ApiError {
    public NotFoundApiError() {
        super(HttpStatus.NOT_FOUND, "Não encontrado.");
    }
}
