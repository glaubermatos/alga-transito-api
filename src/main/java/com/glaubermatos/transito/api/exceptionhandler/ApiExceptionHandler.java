package com.glaubermatos.transito.api.exceptionhandler;

import java.net.URI;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.glaubermatos.transito.domain.exception.NegocioException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                HttpHeaders headers, HttpStatusCode status, 
                                                                WebRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(status);
        problemDetail.setTitle("Um ou mais campos estão inválidos");
        problemDetail.setType(URI.create("http://algatransito.com/errors/campos-invalidos"));

        Map<String, String> fields = ex.getBindingResult().getAllErrors()
                .stream()
                .collect(Collectors.toMap(objectError -> ((FieldError)objectError).getField(),
                                        objectError -> messageSource.getMessage((FieldError)objectError, LocaleContextHolder.getLocale())));

        problemDetail.setProperty("fields", fields);

        return handleExceptionInternal(ex, problemDetail, headers, status, request);
    }
    
    @ExceptionHandler(NegocioException.class)
    public ProblemDetail handleNegocioException(NegocioException ex) {

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle(ex.getMessage());
        problemDetail.setType(URI.create("http://algatransito.com/errors/regra-de-negocio-violada"));

        return problemDetail;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail handleDataIntegrity(DataIntegrityViolationException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        problemDetail.setTitle("Recurso está em uso");
        problemDetail.setType(URI.create("http://algatransito.com/errors/recurso-em-uso"));

        return problemDetail;
    }
}
