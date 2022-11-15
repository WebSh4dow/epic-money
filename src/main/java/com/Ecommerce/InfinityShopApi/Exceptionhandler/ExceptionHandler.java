package com.Ecommerce.InfinityShopApi.Exceptionhandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {
    @Autowired
    private MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String messageUser = messageSource.getMessage("invalid.message",null, LocaleContextHolder.getLocale());
        String messageDev = ex.getCause() !=null ? ex.getCause().toString():ex.toString();
        List<ExceptionErrorMessage>error = Arrays.asList(new ExceptionErrorMessage(messageUser,messageDev));
        return handleExceptionInternal(ex,new ExceptionErrorMessage(messageUser,messageDev),headers,status.BAD_REQUEST,request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<ExceptionErrorMessage>error = createListError(ex.getBindingResult());
        return handleExceptionInternal(ex,error, headers, status.BAD_REQUEST, request);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({EmptyResultDataAccessException.class })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException exception,WebRequest request){

        String messageUser = messageSource.getMessage("recruso.nao-encontrado",null,LocaleContextHolder.getLocale());
        String messageDev = exception.toString();

        List<ExceptionErrorMessage> error = Arrays.asList(new ExceptionErrorMessage(messageUser,messageDev));
        return handleExceptionInternal(exception,error,new HttpHeaders(),HttpStatus.NOT_FOUND,request);
    }
    private List<ExceptionErrorMessage> createListError(BindingResult bindingResult) {
        List<ExceptionErrorMessage> error = new ArrayList<>();

        for (FieldError fieldError:bindingResult.getFieldErrors()) {
            String messageUser = messageSource.getMessage(fieldError,LocaleContextHolder.getLocale());
            String messageDev = fieldError.toString();
            error.add(new ExceptionErrorMessage(messageUser,messageDev));
        }
        return error;
    }
}