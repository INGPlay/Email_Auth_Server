package server.api.emailAuth.exceptionHandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import server.api.emailAuth.exceptionHandler.exception.FailedValidationException;
import server.api.emailAuth.exceptionHandler.exception.WrongCodeRepeatExcepton;
import server.api.emailAuth.exceptionHandler.exception.abs.AbstractCustomException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    protected String exception(RuntimeException exception,
                               HttpServletRequest httpServletRequest,
                               RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("referer", getReferer(httpServletRequest));
        redirectAttributes.addFlashAttribute("errorMessage", exception.getMessage());
        return "redirect:/error";
    }

    @ExceptionHandler(WrongCodeRepeatExcepton.class)
    protected String exception(WrongCodeRepeatExcepton wrongCodeRepeatExcepton,
                               RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("referer", wrongCodeRepeatExcepton.getReferer());
        redirectAttributes.addFlashAttribute("errormessage", wrongCodeRepeatExcepton.getMessage());
        return "redirect:/error";
    }

    @ExceptionHandler(FailedValidationException.class)
    protected String exception(FailedValidationException failedValidationException,
                               HttpServletRequest httpServletRequest,
                               RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("referer", getReferer(httpServletRequest));
        redirectAttributes.addFlashAttribute("errorMessage", failedValidationException.getMessage());
        return "redirect:/error";
    }

    private static String getReferer(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getHeader("Referer");
    }
}
