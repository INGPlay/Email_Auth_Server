package server.api.emailAuth.exceptionHandler.exception;

import org.springframework.http.HttpStatus;
import server.api.emailAuth.exceptionHandler.exception.abs.AbstractCustomException;

public class FailedValidationException extends AbstractCustomException {

    public FailedValidationException() {
        super("형식에 맞는 데이터를 입력해주세요.");
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.BAD_REQUEST;
    }
}
