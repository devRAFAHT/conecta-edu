package br.com.ifba.conectaedu.exception;

public class PasswordInvalidException extends RuntimeException{
    public PasswordInvalidException(String msg){
        super(msg);
    }
}
