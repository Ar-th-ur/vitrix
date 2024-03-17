package ru.vitrix.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Locale;

@ControllerAdvice
@RequiredArgsConstructor
public class CustomExceptionHandler {
    private final MessageSource messageSource;

    @ExceptionHandler(FileException.class)
    public String handleFileException(FileException exception,
                                      HttpServletRequest request,
                                      Locale locale,
                                      Model model) {
        var errorMessage = messageSource.getMessage(exception.getMessage(), new Object[0], locale);
        var statusCode = HttpStatus.BAD_REQUEST.value();
        var referer = request.getHeader("Referer");

        model.addAttribute("referer", referer);
        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("statusCode", statusCode);
        return "errors/error";
    }

    @ExceptionHandler(Exception.class)
    public String handleException() {
        return "redirect:/posts";
    }
}
