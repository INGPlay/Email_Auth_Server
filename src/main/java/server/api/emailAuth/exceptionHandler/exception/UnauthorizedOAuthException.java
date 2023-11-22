package server.api.emailAuth.exceptionHandler.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

public class UnauthorizedOAuthException extends AuthenticationException {

    public UnauthorizedOAuthException(String message) {
        super(message);
    }

    public HttpStatus getStatusCode() {
        return HttpStatus.UNAUTHORIZED;
    }
}
