package server.api.emailAuth.exceptionHandler;

import server.api.emailAuth.common.util.response.ResponseForm;
import server.api.emailAuth.exceptionHandler.exception.NotFoundCustomException;
import server.api.emailAuth.exceptionHandler.exception.abs.AbstractCustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class CustomRestExceptionHandler {

    @ExceptionHandler(NotFoundCustomException.class)
    protected ResponseEntity<ResponseForm> NotFoundException(AbstractCustomException abstractCustomException){

        Map<String, Object> data = new HashMap<>();
        data.put("contents", null);
        data.put("errorMessage", abstractCustomException.getMessage());

        return new ResponseEntity<>(new ResponseForm(false, data), abstractCustomException.getStatusCode());
    }
}
