package io.github.matheusfm.moviestips.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ErrorHandler {
    private static final String DEFAULT_ERROR_MESSAGE = "Ocorreu um erro inesperado. Por favor, tente novamente mais tarde.";

    private final Logger logger = LoggerFactory.getLogger(ErrorHandler.class);

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Map<String, String> unknownError(Exception e) {
        return buildErrorResponse(e);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Map<String, String> missingParameter(MissingServletRequestParameterException e) {
        String message = String.format("O parâmetro %s é obrigatório.", e.getParameterName());
        return buildErrorResponse(e, message);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Map<String, String> typeParameter(MethodArgumentTypeMismatchException e) {
        String message = String.format("O parâmetro %s deve ser do tipo %s.", e.getName(), e.getRequiredType().getSimpleName());
        return buildErrorResponse(e, message);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(HttpClientErrorException.class)
    public Map<String, String> restTemplateException(HttpClientErrorException e) {
        String message = "Erro ao consumir API de terceiros";
        return buildErrorResponse(e, message);
    }

    private Map<String, String> buildErrorResponse(Exception e) {
        return buildErrorResponse(e, DEFAULT_ERROR_MESSAGE);
    }

    private Map<String, String> buildErrorResponse(Exception e, String message) {
        logger.error(message, e);
        Map<String, String> error = new HashMap<>();
        error.put("message", message);
        return error;
    }
}
