package com.sportfy.sportfy.exeptions;

public class PermissaoNaoExisteException extends Exception{
    public PermissaoNaoExisteException(String mensagem){
        super(mensagem);
    }
}
