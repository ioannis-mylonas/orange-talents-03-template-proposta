package bootcamp.proposta.exceptions;

import org.springframework.http.HttpStatus;

public class ApiError extends RuntimeException {
    private final HttpStatus status;
    private final String reason;

    public ApiError(HttpStatus status, String reason) {
        super(reason);
        this.status = status;
        this.reason = reason;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getReason() {
        return reason;
    }
}
