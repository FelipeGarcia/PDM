package com.hefestusgames.pdm;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Felipe on 09/11/2016.
 */

public class NoticiasActivity extends ListActivity implements Callback<NewsMaterias> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        requestWindowFeature(Window.FEATURE_PROGRESS);
        ArrayAdapter<Materia> arrayAdapter = new ArrayAdapter<Materia>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                new ArrayList<Materia>());
        setListAdapter(arrayAdapter);
        setProgressBarIndeterminateVisibility(true);
        setProgressBarVisibility(true);

        buscarPerguntas();
    }

    public void buscarPerguntas() {
        setProgressBarIndeterminateVisibility(true);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://newsapi.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // Prepara chamada
        NewsAPI stackOverflowAPI = retrofit.create(NewsAPI.class);
        Call<NewsMaterias> call = stackOverflowAPI.carregarmaterias("android");

        ////asynchronous call
        call.enqueue(this);

        //// synchronous call, fazer em outra thread e não na principal!
        // call.execute()

        //// Para cancelar uma chamada
        // call.cancel();

        //// calls só podem ser chamadas uma vez, mas você pode cloná-las...
        //Call<StackOverflowQuestions> c = call.clone();
        //c.enqueue(this);
    }

    @Override
    public void onResponse(Call<NewsMaterias> call, Response<NewsMaterias> response) {
        setProgressBarIndeterminateVisibility(false);
        ArrayAdapter<Materia> adapter = (ArrayAdapter<Materia>) getListAdapter();
        adapter.clear();
        adapter.addAll(response.body().articles);
    }

    @Override
    public void onFailure(Call<NewsMaterias> call, Throwable t) {
        Toast.makeText(NoticiasActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Materia pergunta = (Materia) getListAdapter().getItem(position);
        Uri linkMateria = Uri.parse(pergunta.url);
        Intent a = new Intent(Intent.ACTION_VIEW, linkMateria);
        startActivity(a);
    }

}
