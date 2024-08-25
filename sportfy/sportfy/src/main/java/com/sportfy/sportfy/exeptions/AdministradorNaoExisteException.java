package com.sportfy.sportfy.exeptions;

public class AdministradorNaoExisteException extends Exception{
    public AdministradorNaoExisteException(String mensagem){
        super(mensagem);
    }
}
