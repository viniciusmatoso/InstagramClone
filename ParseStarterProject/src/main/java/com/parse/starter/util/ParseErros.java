package com.parse.starter.util;

import java.util.HashMap;

public class ParseErros {

    private HashMap<Integer, String> erros;

    public ParseErros() {
        this.erros = new HashMap<>();
        this.erros.put(202, "este nome de usuário já existe, escolha outro!");
        this.erros.put(201, "senha não foi preenchida!");
        this.erros.put(101, "nome de usuário ou senha inválidos!");
    }

    public String getErro(int codErro){
        return this.erros.get( codErro );
    }

}
