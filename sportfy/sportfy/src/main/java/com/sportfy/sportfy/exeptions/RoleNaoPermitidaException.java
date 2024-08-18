package com.sportfy.sportfy.exeptions;

public class RoleNaoPermitidaException extends Exception{
    public RoleNaoPermitidaException(String mensagem){
        super(mensagem);
    }
}
