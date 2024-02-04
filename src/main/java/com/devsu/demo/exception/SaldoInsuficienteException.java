package com.devsu.demo.exception;

import java.io.Serializable;

public class SaldoInsuficienteException extends RuntimeException implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
    public SaldoInsuficienteException(String message) {
        super(message);
    }
}