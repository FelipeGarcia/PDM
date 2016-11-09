package com.hefestusgames.pdm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Felipe on 03/11/2016.
 */

public class EditarActivity extends AppCompatActivity {

    public static int NOTIFICATION_ID = 1;

    private TextView altura;
    private EditText campo_data_nascimento, campo_nome;
    private Spinner spinner;

    private RadioGroup radio_sexo;
    private CheckBox ingles, portugues, espanhol;
    private Double progresso;

    private Cadastro c;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);


        SeekBar seekbar;
        Button botao_salvar;

        altura = (TextView) findViewById(R.id.cadastro_textview_altura);
        campo_nome = (EditText) findViewById(R.id.cadastro_campo_nome);

        botao_salvar = (Button) findViewById(R.id.cadastro_button_cadastra);
        radio_sexo = (RadioGroup) findViewById(R.id.cadastro_radiogroup_sexo);
        ingles = (CheckBox) findViewById(R.id.cadastro_checkbox_ingles);
        portugues = (CheckBox) findViewById(R.id.cadastro_checkbox_portugues);
        espanhol = (CheckBox) findViewById(R.id.cadastro_checkbox_espanhol);

        //inserir máscaras nos campos
        campo_data_nascimento = (EditText) findViewById(R.id.cadastro_campo_data_nascimento);
        campo_data_nascimento.addTextChangedListener(Mask.insert("##/##/####", campo_data_nascimento));
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

        //Adiciona valores ao Spinner
        spinner = (Spinner) findViewById(R.id.funcao_spinner);
        ArrayAdapter < CharSequence > adapter = ArrayAdapter.createFromResource(this,
                R.array.cadastro_funcao_array, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

        //Gerencia valor seekbar
        seekbar = (SeekBar) findViewById(R.id.cadastro_seekbar_altura);
        seekbar.setMax(120);

        //Insere valores nos campos
        Long idCadastro = getIntent().getLongExtra("id", 0);
        c = Cadastro.findById(Cadastro.class, idCadastro);
        campo_nome.setText(c.nome);
        altura.setText(c.altura + "M");
        ingles.setChecked(c.ingles);
        portugues.setChecked(c.portugues);
        espanhol.setChecked(c.espanhol);
        campo_data_nascimento.setText(formato.format(c.nascimento));
        seekbar.setProgress((int)(c.altura * 100f) - 120);
        botao_salvar.setText("Salvar");
        radio_sexo.check(c.idSexo);
        progresso = c.altura;
        spinner.setSelection(c.posFuncao);

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //adiciona 120 pois é o menos valor a se tratar
                progresso = (progress + 120) / 100.0d;
                altura.setText(progresso + "M");
            }

            public void onStartTrackingTouch(SeekBar seekBar) { /* não faço nada*/ }

            public void onStopTrackingTouch(SeekBar seekBar) { /*não faço nada*/ }
        });

        class AssyncTask extends AsyncTask < Void, Void, Void > {

            @
                    Override
            protected Void doInBackground(Void...params) {
                try {
                    Salvar();

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                return null;
            }
        }

        botao_salvar.setOnClickListener(new View.OnClickListener() {

            @
                    Override
            public void onClick(View view) {
                if (!validaNome()) {} else if (!validaData()) {} else {
                    final AsyncTask < Void, Void, Void > execute = new AssyncTask().execute();
                }
            }
        });


    }


    public void Salvar() throws ParseException {


        RadioButton sexo_selecionado = (RadioButton) findViewById(radio_sexo.getCheckedRadioButtonId());
        String nascimento = campo_data_nascimento.getText().toString();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date data = formato.parse(nascimento);

        c.nome = campo_nome.getText().toString();
        c.funcao = spinner.getSelectedItem().toString();
        c.nascimento = data;
        c.sexo = sexo_selecionado.getText().toString();
        c.ingles = ingles.isChecked();
        c.portugues = portugues.isChecked();
        c.espanhol = espanhol.isChecked();
        c.altura = progresso;
        c.idSexo = radio_sexo.getCheckedRadioButtonId();
        c.posFuncao = spinner.getSelectedItemPosition();

        c.save();
        CriarNotificacao();
    }

    public Boolean validaNome() {
        if (campo_nome.getText().toString().isEmpty()) {
            shake(campo_nome, "Tenho certeza que você possui um nome, que tal inserir ele?");
            return false;
        }

        return true;
    }

    public Boolean validaData() {

        //Pega valor de data e remove as /
        String data = campo_data_nascimento.getText().toString().replaceAll("[/]", "");

        //Verifica se esta completa e já garante que não está vazia
        if (data.length() != 8) {
            shake(campo_data_nascimento, "Faltam dados em sua data de nascimento...");
            return false;
        }

        //Separa em dia, mes e ano, para poder tratar em seguida;
        int dia = Integer.valueOf(data.substring(1, 2));
        int mes = Integer.valueOf(data.substring(2, 4));
        int ano = Integer.valueOf(data.substring(4, 8));

        //verifica se data esta incorreta
        if (dia <= 0 || dia > 31 ||
                mes <= 0 || mes > 12 ||
                ano < 1899 || ano > 2010) {
            shake(campo_data_nascimento, "Você tem certeza que essa é sua data de nascimento?");
            return false;
        }


        return true;
    }

    public void shake(EditText campo, String erro) {
        Animation shake = AnimationUtils.loadAnimation(EditarActivity.this, R.anim.shake);
        campo.startAnimation(shake);
        campo.setError(erro);
    }

    public void CriarNotificacao() {
        Intent notificationIntent = new Intent(this, CadastroActivity.class);

        // usando System.currentTimeMillis() você terá um ID único para a pending intent
        PendingIntent notificationPendingIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), notificationIntent, 0);

        String textoPequeno = campo_nome.getText().toString() + " foi alterado com sucesso!";

        Notification notification = new Notification.Builder(this)
                .setContentTitle("PDM")
                .setContentText(textoPequeno)
                .setContentIntent(notificationPendingIntent)
                .setAutoCancel(false)
                .setSmallIcon(R.drawable.registrar_icone)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        //Se colocar o mesmo ID ele substitui o anterior (se tiver uma notificação já)
        notificationManager.notify(NOTIFICATION_ID, notification);
    }
}