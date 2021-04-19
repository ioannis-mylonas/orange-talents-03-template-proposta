package bootcamp.proposta.config.handler;

import bootcamp.proposta.exceptions.ApiError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiErrorHandler {
    @ExceptionHandler(ApiError.class)
    public ResponseEntity<ApiErrorResponse> handle(ApiError err) {
        ApiErrorResponse response = new ApiErrorResponse(err.getReason());

        return ResponseEntity.status(err.getStatus()).body(response);
    }
}
