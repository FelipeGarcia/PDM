package com.hefestusgames.pdm;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.text.ParseException;
import java.util.List;


public class ConsultaActivity extends AppCompatActivity {

    EditText campo_consulta;
    ListView listaPessoas;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);

        listaPessoas = (ListView) findViewById(R.id.lista_consulta);
        campo_consulta = (EditText) findViewById(R.id.campo_consulta);

        campo_consulta.addTextChangedListener(ConsultaWatcher());

        List<Cadastro> tudo = Cadastro.listAll(Cadastro.class);

        GeraLista(tudo);

        listaPessoas.setOnItemClickListener(new ListView.OnItemClickListener(){


            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cadastro c = (Cadastro) listaPessoas.getItemAtPosition((int) adapterView.getItemIdAtPosition(i));
                Toast.makeText(getApplicationContext(), c.nome, Toast.LENGTH_LONG).show();
            }


        });

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void PesquisaLista() {

        List<Cadastro> pesquisa = Select.from(Cadastro.class)
                .where(Condition.prop("nome").like("%" + campo_consulta.getText().toString() + "%"))
                .orderBy("nome")
                .list();

        GeraLista(pesquisa);

    }

    public void GeraLista(List<Cadastro> lista) {

        ArrayAdapter<Cadastro> adapter = new ArrayAdapter<Cadastro>(this,
                android.R.layout.simple_list_item_1, lista);

        listaPessoas.setAdapter(adapter);

        registerForContextMenu(listaPessoas);
    }

    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Consulta Page")
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

        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.setHeaderTitle("Você deseja deletar o cadastro?");
        menu.add(0, v.getId(), 0, "Deletar");
        menu.add(0, v.getId(), 0, "Cancelar");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle() == "Deletar") {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            int listPosition = info.position;
            Cadastro c = (Cadastro) listaPessoas.getItemAtPosition(listPosition);
            c.delete();
            Toast.makeText(getApplicationContext(), "Cadastro deletado", Toast.LENGTH_LONG).show();
        } else if (item.getTitle() == "Cancelar") {
            return false;
        } else {
            return false;
        }
        GeraLista(Cadastro.listAll(Cadastro.class));
        return true;
    }

    public TextWatcher ConsultaWatcher() {
        return new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                PesquisaLista();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void afterTextChanged(Editable s) {

            }
        };
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_consulta, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_delete_all) {
            Cadastro.deleteAll(Cadastro.class);
            Toast.makeText(getApplicationContext(), "Todos os cadastros deletados", Toast.LENGTH_LONG).show();
            GeraLista(Cadastro.listAll(Cadastro.class));
        }

        return super.onOptionsItemSelected(item);
    }
}
