package com.siit.spring.exception;

import org.springframework.core.NestedRuntimeException;

public class AlbumNotFoundException extends NestedRuntimeException {
    public AlbumNotFoundException(String msg) {
        super(msg);
    }
}
