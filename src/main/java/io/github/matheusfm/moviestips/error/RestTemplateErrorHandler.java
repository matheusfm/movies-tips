package io.github.matheusfm.moviestips.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.DefaultResponseErrorHandler;

public class RestTemplateErrorHandler extends DefaultResponseErrorHandler {
    /**
     * Por default, o RestTemplate lança uma exceção para
     * {@link HttpStatus.Series#CLIENT_ERROR CLIENT_ERROR} ou
     * {@link HttpStatus.Series#SERVER_ERROR SERVER_ERROR}.
     * <p>
     * Método sobrescrito para lançar exceção se não for {@link HttpStatus.Series#SUCCESSFUL SUCCESSFUL}
     *
     * @param statusCode the HTTP status code
     * @return {@code true} if the response has an error; {@code false} otherwise
     */
    @Override
    protected boolean hasError(HttpStatus statusCode) {
        return statusCode.series() != HttpStatus.Series.SUCCESSFUL;
    }
}
