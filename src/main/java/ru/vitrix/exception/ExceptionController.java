package ru.vitrix.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

import java.util.Locale;

@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionController {
    private final MessageSource messageSource;

    @ExceptionHandler(FileException.class)
    public ModelAndView handleFileException(FileException exception, Locale locale) {
        return getErrorPage(exception.getMessage(), HttpStatus.BAD_REQUEST, locale);
    }

    @ExceptionHandler(HttpClientErrorException.NotFound.class)
    public ModelAndView handleBadRequest(HttpClientErrorException.NotFound exception, Locale locale) {
        return getErrorPage("http.error.not_found", exception.getStatusCode(), locale);
    }

    @ExceptionHandler(HttpServerErrorException.InternalServerError.class)
    public ModelAndView handleBadRequest(HttpServerErrorException.InternalServerError exception, Locale locale) {
        return getErrorPage("server.error", exception.getStatusCode(), locale);
    }

    private ModelAndView getErrorPage(String message, HttpStatusCode statusCode, Locale locale) {
        var errorMessage = messageSource.getMessage(message, new Object[0], locale);

        var model = new ModelAndView("errors/error");
        model.addObject("errorMessage", errorMessage);
        model.addObject("statusCode", statusCode.value());
        return model;
    }
}
