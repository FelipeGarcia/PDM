package com.hefestusgames.pdm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by felipes on 8/31/2016.
 */
public class CalculadoraActivity extends AppCompatActivity {


    private TextView visor;
    private Button zero, um, dois, tres, quatro, cinco, seis, sete, oito, nove, ponto, divide, multiplica, subtrai, soma, igual;
    private String operacao;
    private float numero1,numero2;
    private boolean isPonto;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculadora);

        visor = (TextView) findViewById(R.id.calculadora_visor);

        zero = (Button) findViewById(R.id.botao_zero);
        um = (Button) findViewById(R.id.botao_um);
        dois = (Button) findViewById(R.id.botao_dois);
        tres = (Button) findViewById(R.id.botao_tres);
        quatro = (Button) findViewById(R.id.botao_quatro);
        cinco = (Button) findViewById(R.id.botao_cinco);
        seis = (Button) findViewById(R.id.botao_seis);
        sete = (Button) findViewById(R.id.botao_sete);
        oito = (Button) findViewById(R.id.botao_oito);
        nove = (Button) findViewById(R.id.botao_nove);
        ponto = (Button) findViewById(R.id.botao_ponto);
        divide = (Button) findViewById(R.id.botao_divide);
        multiplica = (Button) findViewById(R.id.botao_multiplica);
        subtrai = (Button) findViewById(R.id.botao_subtrai);
        soma = (Button) findViewById(R.id.botao_soma);
        igual = (Button) findViewById(R.id.botao_igual);

        zero.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                adicionaVisor(0);
            }

        });

        um.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                adicionaVisor(1);
            }

        });

        dois.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                adicionaVisor(2);
            }

        });

        tres.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                adicionaVisor(3);
            }

        });

        quatro.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                adicionaVisor(4);
            }

        });

        cinco.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                adicionaVisor(5);
            }

        });

        seis.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                adicionaVisor(6);
            }

        });

        sete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                adicionaVisor(7);
            }

        });

        oito.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                adicionaVisor(8);
            }

        });

        nove.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                adicionaVisor(9);
            }

        });

        ponto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                adicionaVisor(".");
            }

        });

        divide.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                operacao = "divide";
                numero1 = Float.valueOf(visor.getText().toString());
                limpaVisor();
            }

        });

        multiplica.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                operacao = "multiplica";
                numero1 = Float.valueOf(visor.getText().toString());
                limpaVisor();
            }

        });

        subtrai.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                operacao = "subtrai";
                numero1 = Float.valueOf(visor.getText().toString());
                limpaVisor();
            }

        });

        soma.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                operacao = "soma";
                numero1 = Float.valueOf(visor.getText().toString());
                limpaVisor();
            }

        });

        igual.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                numero2 = Float.valueOf(visor.getText().toString());
               calcula();
            }

        });

    }

    private void adicionaVisor(int numero){
        if(visor.getText().toString().equals("0")){
            visor.setText("");
        }
        visor.setText(visor.getText().toString() + numero);
    }

    private void adicionaVisor(String ponto){
        if(!isPonto) {
            visor.setText(visor.getText().toString() + ponto);
            isPonto = true;
        }
    }

    //Seta visor para zero, isso permite que sempre tenha um valor para usar, mesmo que seja 0
    private void limpaVisor(){
        visor.setText("0");
        isPonto = false;
    }

    private void calcula(){
        float resultado;

        //impede que o usuário abra a calculadora e clique no = sem selecionar uma operação

        if(operacao == null){
            Toast.makeText(getApplicationContext(), "Escolha uma operacao", Toast.LENGTH_SHORT).show();
            return;
        }
        switch (operacao){
            case "divide":
                resultado = numero1/numero2;
                break;
            case "multiplica":
                resultado = numero1*numero2;
                break;
            case "subtrai":
                resultado = numero1-numero2;
                break;
            case "soma":
                resultado = numero1+numero2;
                break;
            default:
                resultado = numero2;
        }
        limpaVisor();
        Intent intent = new Intent(CalculadoraActivity.this, CalculadoraResultadoActivity.class);
        intent.putExtra("resultado", resultado);
        startActivity(intent);
    }
}