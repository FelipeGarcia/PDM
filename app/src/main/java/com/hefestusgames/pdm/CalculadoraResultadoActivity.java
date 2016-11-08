package com.hefestusgames.pdm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by felipes on 8/31/2016.
 */
public class CalculadoraResultadoActivity extends AppCompatActivity {


    private TextView resultado;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculadora_resultado);

        resultado = (TextView) findViewById(R.id.resultado_operacao);
        float teste = getIntent().getFloatExtra("resultado", 0);
        resultado.setText(String.valueOf(teste));
    }
}
