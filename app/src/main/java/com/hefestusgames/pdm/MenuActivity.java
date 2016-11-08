package com.hefestusgames.pdm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MenuActivity extends AppCompatActivity {

    public static final int NOTIFICATION_ID = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ImageButton calculadoraButton = (ImageButton) findViewById(R.id.botao_calculadora);
        ImageButton cadastroButton = (ImageButton) findViewById(R.id.botao_cadastro);
        ImageButton consultaButton = (ImageButton) findViewById(R.id.botao_consulta);
        Notificacao n = new Notificacao();

        CriarNotificacao();

        calculadoraButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                    Intent intent = new Intent(MenuActivity.this, CalculadoraActivity.class);
                     startActivity(intent);

            }

        });

        cadastroButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MenuActivity.this, CadastroActivity.class);
                startActivity(intent);

            }

        });

        consultaButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MenuActivity.this, ConsultaActivity.class);
                startActivity(intent);

            }

        });
    }

    public void CriarNotificacao() {
        Intent notificationIntent = new Intent(this, MenuActivity.class);
        Intent tela01Intent = new Intent(this, CadastroActivity.class);
        Intent tela02Intent = new Intent(this, ConsultaActivity.class);

        // usando System.currentTimeMillis() você terá um ID único para a pending intent
        PendingIntent notificationPendingIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), notificationIntent, 0);
        PendingIntent tela01PendingIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), tela01Intent, 0);
        PendingIntent tela02PendingIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), tela02Intent, 0);

        Notification.Action action01 = new Notification.Action.Builder(R.drawable.registra_notificacao, "Cadastro", tela01PendingIntent).build();
        Notification.Action action02 = new Notification.Action.Builder(R.drawable.consulta_notificacao, "Consulta", tela02PendingIntent).build();

        String textoPequeno = "Vamos continuar seus serviços no aplicativo!";
        String textoGrande = "Vamos continuar seus serviços no aplicativo! Selecione uma das opções abaixo e seja direcionado para a opcao de sua escolha!";
        Notification notification = new Notification.Builder(this)
                .setContentTitle("PDM")
                .setContentText(textoPequeno)
                .setStyle(new Notification.BigTextStyle().bigText(textoGrande))
                .setContentIntent(notificationPendingIntent)
                .setAutoCancel(false)
                .addAction(action01)
                .setOngoing(true)
                .setSmallIcon(R.drawable.registrar_icone)
                .addAction(action02)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        //Se colocar o mesmo ID ele substitui o anterior (se tiver uma notificação já)
        //   se colocar um novo ID, ele cria outra notificação...
        notificationManager.notify(NOTIFICATION_ID, notification);
    }
}