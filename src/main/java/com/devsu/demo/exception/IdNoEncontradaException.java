package com.devsu.demo.exception;

import java.io.Serializable;

public class IdNoEncontradaException extends RuntimeException implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    public IdNoEncontradaException(String message) {
        super(message);
    }
}