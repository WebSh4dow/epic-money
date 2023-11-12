package com.invest.me.money.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EntidadeEmUsoException extends RuntimeException{

    public EntidadeEmUsoException(String message) {
        super(message);
    }
}
