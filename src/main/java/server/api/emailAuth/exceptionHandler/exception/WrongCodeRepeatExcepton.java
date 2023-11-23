package server.api.emailAuth.exceptionHandler.exception;

import org.springframework.http.HttpStatus;
import server.api.emailAuth.exceptionHandler.exception.abs.AbstractCustomException;

public class WrongCodeRepeatExcepton extends AbstractCustomException {


    private String referer;

    public WrongCodeRepeatExcepton(String referer) {
        super("반복하여 잘못된 코드를 입력하였습니다.");
        this.referer = referer;
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.BAD_REQUEST;
    }

    public String getReferer(){
        return this.referer;
    }
}
