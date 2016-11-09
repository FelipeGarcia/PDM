package com.hefestusgames.pdm;

import android.animation.Animator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.animation.ValueAnimator;


public class LoginActivity extends AppCompatActivity {

    private EditText loginEditText, senhaEditText;
    private String loginPadrao, senhaPadrao;
    private Button loginButton;
    private ImageView cadeado;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginPadrao = "Admin";
        senhaPadrao = "admin";

        loginEditText = (EditText) findViewById(R.id.editLogin);
        senhaEditText = (EditText) findViewById(R.id.editSenha);
        loginButton = (Button) findViewById(R.id.botaoLogin);
        cadeado = (ImageView) findViewById(R.id.cadeado);

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {




    if(loginEditText.getText().toString().equals(loginPadrao) &&
            senhaEditText.getText().toString().equals(senhaPadrao)){
            setAnimEvents();
                }else{
        Toast.makeText(getApplicationContext(), "Dados de Login inválidos\nTente Admin:admin", Toast.LENGTH_SHORT).show();
    }

            }

        });

    }

    private int getScreenWidth() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;

        return width;
    }

    private void setAnimEvents() {
        //1
        ValueAnimator animator = ValueAnimator.ofFloat(0, getScreenWidth());

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                cadeado.setTranslationX(value);
            }
        });

        // 2
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {/* nada aqui */}

            @Override
            public void onAnimationEnd(Animator animation) {

                Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                startActivity(intent);
            }

            @Override
            public void onAnimationCancel(Animator animation) {/* nada aqui */}

            @Override
            public void onAnimationRepeat(Animator animation) {/* nada aqui */}
        });

        // 5
        animator.setDuration(5000L);
        animator.start();
    }
}