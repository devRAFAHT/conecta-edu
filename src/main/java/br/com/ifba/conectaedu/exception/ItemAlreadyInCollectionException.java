package br.com.ifba.conectaedu.exception;

public class ItemAlreadyInCollectionException extends RuntimeException{

    public ItemAlreadyInCollectionException(String msg){
        super(msg);
    }

}
