package ru.vitrix.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.WebUtils;

@Controller
@Slf4j
public class ExceptionController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Integer statusCode = (Integer) request.getAttribute(WebUtils.ERROR_STATUS_CODE_ATTRIBUTE);
        String errorMessage = switch (statusCode) {
            case 404 -> "Такой страницы нет.";
            case 403 -> "У вас нет прав доступа к этой странице";
            default -> {
                Exception exception = (Exception) request.getAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE);
                log.error("Exception: ", exception);
                yield "Что то пошло нет. Мы уже исправляем";
            }
        };

        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("statusCode", statusCode);
        return "errors/error";
    }
}
