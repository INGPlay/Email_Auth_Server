package server.api.emailAuth.exceptionHandler.exception;

import org.springframework.http.HttpStatus;
import server.api.emailAuth.exceptionHandler.exception.abs.AbstractCustomException;

public class EmailDuplicateException extends AbstractCustomException {

    public EmailDuplicateException() {
        super("이미 가입된 이메일입니다.");
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.BAD_REQUEST;
    }
}
