package com.sportfy.sportfy.exeptions;

public class CanalNaoExisteException extends Exception{
    public CanalNaoExisteException(String mensagem){
        super(mensagem);
    }
}
