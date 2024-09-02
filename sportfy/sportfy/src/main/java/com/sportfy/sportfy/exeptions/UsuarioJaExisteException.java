package com.sportfy.sportfy.exeptions;

public class UsuarioJaExisteException extends Exception{
    public UsuarioJaExisteException(String mensagem){
        super(mensagem);
    }
}
