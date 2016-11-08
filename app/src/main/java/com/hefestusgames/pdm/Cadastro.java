package com.hefestusgames.pdm;


import com.orm.SugarRecord;

import java.util.Date;

public class Cadastro extends SugarRecord{
    String nome, funcao, sexo;
    Date nascimento;
    boolean ingles, espanhol, portugues;
    Double altura;

    public Cadastro(){

    }

    public Cadastro(String nome, String funcao, Date nascimento, String sexo, boolean ingles, boolean espanhol, boolean portugues, Double altura) {
        this.nome = nome;
        this.funcao = funcao;
        this.nascimento = nascimento;
        this.sexo = sexo;
        this.ingles = ingles;
        this.espanhol = espanhol;
        this.portugues = portugues;
        this.altura = altura;
    }

    public Long toId(){
        return this.getId();
    }
    public String toString() {
        return "Nome: " + nome +
                "\nFunção: " + funcao;
    }
}
