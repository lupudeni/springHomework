package com.siit.spring.exception;

import org.springframework.core.NestedRuntimeException;

public class BadRequestException extends NestedRuntimeException {

    public BadRequestException(String msg) {
        super(msg);
    }
}
