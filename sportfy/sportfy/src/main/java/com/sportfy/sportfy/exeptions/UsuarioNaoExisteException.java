package com.sportfy.sportfy.exeptions;

public class UsuarioNaoExisteException extends Exception{
    public UsuarioNaoExisteException(String mensagem){
        super(mensagem);
    }
}
