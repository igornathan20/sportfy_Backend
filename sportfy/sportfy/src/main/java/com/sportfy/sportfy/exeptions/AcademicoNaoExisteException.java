package com.sportfy.sportfy.exeptions;

public class AcademicoNaoExisteException extends Exception{
    public AcademicoNaoExisteException(String mensagem){
        super(mensagem);
    }
}
