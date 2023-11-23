package server.api.emailAuth.exceptionHandler.exception;

import org.springframework.http.HttpStatus;
import server.api.emailAuth.exceptionHandler.exception.abs.AbstractCustomException;

public class CacheTimeoutException extends AbstractCustomException {

    public CacheTimeoutException() {
        super("시간이 초과하였습니다.");
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.BAD_REQUEST;
    }
}
