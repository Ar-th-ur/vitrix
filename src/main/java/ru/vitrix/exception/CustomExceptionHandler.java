package ru.vitrix.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.util.Locale;

@ControllerAdvice
@RequiredArgsConstructor
public class CustomExceptionHandler {
    private final MessageSource messageSource;

    @ExceptionHandler(FileException.class)
    public String handleFileException(FileException exception, Locale locale, Model model) {
        var errorMessage = messageSource.getMessage(exception.getMessage(), new Object[0], locale);
        var statusCode = HttpStatus.BAD_REQUEST.value();

        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("statusCode", statusCode);
        return "errors/error";
    }

    @ExceptionHandler(Exception.class)
    public String handleException() {
        return "redirect:/posts";
    }
}
