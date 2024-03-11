package ru.vitrix.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.WebUtils;

@Controller
public class ExceptionController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        int statusCode = (int) request.getAttribute(WebUtils.ERROR_STATUS_CODE_ATTRIBUTE);
        String errorMessage = switch (statusCode) {
            case 404 -> "Такой страницы нет.";
            case 403 -> "У вас нет прав доступа к этой странице";
            default -> "Что то пошло нет. Мы уже исправляем";
        };

        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("statusCode", statusCode);
        return "errors/error";
    }
}
