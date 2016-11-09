package com.hefestusgames.pdm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MenuActivity extends AppCompatActivity {

    public static final int NOTIFICATION_ID = 0;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    public TextView clima;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ImageButton calculadoraButton = (ImageButton) findViewById(R.id.botao_calculadora);
        ImageButton cadastroButton = (ImageButton) findViewById(R.id.botao_cadastro);
        ImageButton consultaButton = (ImageButton) findViewById(R.id.botao_consulta);
        ImageButton noticiasButton = (ImageButton) findViewById(R.id.botao_em_breve);

        clima = (TextView) findViewById(R.id.menu_clima);

        CriarNotificacao();

        calculadoraButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MenuActivity.this, CalculadoraActivity.class);
                startActivity(intent);

            }

        });

        cadastroButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MenuActivity.this, CadastroActivity.class);
                startActivity(intent);

            }

        });

        consultaButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MenuActivity.this, ConsultaActivity.class);
                startActivity(intent);

            }

        });

        noticiasButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MenuActivity.this, NoticiasActivity.class);
                startActivity(intent);

            }

        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
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

        String textoPequeno = "Vamos continuar seus serviços!";
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

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Menu Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}