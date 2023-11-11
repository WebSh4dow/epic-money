package com.invest.me.money.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TipoReceitaException extends RuntimeException{

    public TipoReceitaException(String message) {
        super(message);
    }
}
