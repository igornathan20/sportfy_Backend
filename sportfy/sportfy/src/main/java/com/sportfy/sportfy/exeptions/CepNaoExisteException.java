package com.sportfy.sportfy.exeptions;

public class CepNaoExisteException extends Exception{
    public CepNaoExisteException(String mensagem){
        super(mensagem);
    }
}
