package br.com.ifba.conectaedu.exception;

public class NotAdministratorException extends RuntimeException{
    public NotAdministratorException(String msg){
        super(msg);
    }
}
