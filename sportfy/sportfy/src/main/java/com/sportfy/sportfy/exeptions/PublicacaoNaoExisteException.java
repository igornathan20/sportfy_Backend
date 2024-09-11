package com.sportfy.sportfy.exeptions;

public class PublicacaoNaoExisteException extends Exception{
    public PublicacaoNaoExisteException(String mensagem){
        super(mensagem);
    }
}
