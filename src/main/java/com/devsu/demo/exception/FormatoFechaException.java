package com.devsu.demo.exception;

import java.io.Serializable;

public class FormatoFechaException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 1L;

    public FormatoFechaException(String message) {
        super(message);
    }
}
