package server.api.emailAuth.exceptionHandler.exception;

import org.springframework.http.HttpStatus;
import server.api.emailAuth.exceptionHandler.exception.abs.AbstractCustomException;

public class EmailSendException extends AbstractCustomException {

    public EmailSendException() {
        super("이메일 전송에 실패하였습니다.");
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.NOT_FOUND;
    }
}
