package com.hefestusgames.pdm;


import com.orm.SugarRecord;

import java.util.Date;

public class Cadastro extends SugarRecord {
    String nome, funcao, sexo;
    Date nascimento;
    boolean ingles, espanhol, portugues;
    Double altura;
    int idSexo, posFuncao;

    public Cadastro() {

    }

    public Cadastro(String nome, String funcao, Date nascimento, String sexo, boolean ingles, boolean espanhol, boolean portugues, Double altura, int idSexo, int posFuncao) {
        this.nome = nome;
        this.funcao = funcao;
        this.nascimento = nascimento;
        this.sexo = sexo;
        this.ingles = ingles;
        this.espanhol = espanhol;
        this.portugues = portugues;
        this.altura = altura;
        this.idSexo = idSexo;
        this.posFuncao = posFuncao;
    }

    public Long toId() {
        return this.getId();
    }
    public String toString() {

        String retorno = "Nome: " + nome +
                "\nFunção: " + funcao +
                "\nAltura: " + altura + "M" +
                "\nSexo: " + sexo +
                "\nIdiomas: ";
        if(ingles)retorno += "Inglês ";
        if(espanhol)retorno += "Espanhol ";
        if(portugues)retorno += "Portugês";


        return retorno;
    }
}