package bootcamp.proposta.exceptions;

import org.springframework.http.HttpStatus;

public class InternalApiError extends ApiError {
    public InternalApiError() {
        super(HttpStatus.INTERNAL_SERVER_ERROR,
                "Erro no processamento do pedido, por favor tente novamente mais tarde.");
    }
}
