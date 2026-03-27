package service_booking_api.shared.exceptions;

import service_booking_api.core.domain.error.model.ErrorTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class HandlerException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    @ExceptionHandler(ApplicationException.class)
    public static ResponseEntity<ErrorTemplate> handleAuthenticationException(ApplicationException e) {
        log.error("ApplicationException: {}", e.getMessage());

        Integer code = e.getErrorCode();

        HttpStatus status;
        switch (code) {
            case 400:
                status = HttpStatus.BAD_REQUEST;
                break;
            case 401:
                status = HttpStatus.UNAUTHORIZED;
                break;
            case 403:
                status = HttpStatus.FORBIDDEN;
                break;
            case 404:
                status = HttpStatus.NOT_FOUND;
                break;
            case 409:
                status = HttpStatus.CONFLICT;
                break;
            case 415:
                status = HttpStatus.UNSUPPORTED_MEDIA_TYPE;
                break;
            case 422:
                status = HttpStatus.UNPROCESSABLE_ENTITY;
                break;
            case 503:
                status = HttpStatus.SERVICE_UNAVAILABLE;
                break;
            default:
                status = HttpStatus.INTERNAL_SERVER_ERROR;
                break;
        }

        ErrorTemplate errorBody = ErrorTemplate.builder()
                .errorCode(e.getErrorCode())
                .message(e.getMessage())
                .timestamp(e.getTimestamp())
                .traceId(e.getTraceId())
                .build();

        log.info("Returning error body: {}", errorBody.toString());
        return ResponseEntity.status(status).body(errorBody);
    }
}
