package com.sportfy.sportfy.exeptions;

public class ComentarioNaoExisteException extends Exception{
    public ComentarioNaoExisteException(String mensagem){
        super(mensagem);
    }
}
