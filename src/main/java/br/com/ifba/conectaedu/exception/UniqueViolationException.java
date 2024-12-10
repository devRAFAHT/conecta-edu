package br.com.ifba.conectaedu.exception;

public class UniqueViolationException extends RuntimeException {
    public UniqueViolationException(String msg) {
        super(msg);
    }
}
