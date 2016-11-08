package com.hefestusgames.pdm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText loginEditText,senhaEditText;
    private String loginPadrao, senhaPadrao;
    private Button loginButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginPadrao = "Admin";
        senhaPadrao = "admin";

        loginEditText = (EditText) findViewById(R.id.editLogin);
        senhaEditText = (EditText) findViewById(R.id.editSenha);
        loginButton = (Button) findViewById(R.id.botaoLogin);

        loginButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {




                if(loginEditText.getText().toString().equals(loginPadrao) &&
                        senhaEditText.getText().toString().equals(senhaPadrao)){
                            Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                            startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "Dados de Login inválidos\nTente Admin:admin", Toast.LENGTH_SHORT).show();
                }

            }

        });

    }
}
