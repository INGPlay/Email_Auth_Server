package server.api.emailAuth.exceptionHandler.exception;

import server.api.emailAuth.exceptionHandler.exception.abs.AbstractCustomException;
import org.springframework.http.HttpStatus;

public class NotFoundCustomException extends AbstractCustomException {

    public NotFoundCustomException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.NOT_FOUND;
    }
}
