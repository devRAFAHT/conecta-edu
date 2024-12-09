package br.com.ifba.conectaedu.exception;

public class UserIsAlreadyAnAdministrator extends RuntimeException{
    public UserIsAlreadyAnAdministrator(String msg){
        super(msg);
    }
}
