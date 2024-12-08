package br.com.ifba.conectaedu.exception;

public class UsernameUniqueViolationException extends RuntimeException {
    public UsernameUniqueViolationException(String msg) {
        super(msg);
    }
}
