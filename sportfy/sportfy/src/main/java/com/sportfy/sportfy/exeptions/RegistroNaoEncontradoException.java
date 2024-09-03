package com.sportfy.sportfy.exeptions;

public class RegistroNaoEncontradoException extends Exception{
    public RegistroNaoEncontradoException(String mensagem){
        super(mensagem);
    }
}
