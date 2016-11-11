package ifrn.tads.ddm.agendasugar;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.net.Inet4Address;

import ifrn.tads.ddm.agendasugar.modelo.Agenda;
import ifrn.tads.ddm.agendasugar.modelo.Contato;

public class NovoContato extends AppCompatActivity {
    private EditText editTextNome;
    private EditText editTextPrefixo;
    private EditText editTextNumero;

    private Agenda agenda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_contato);

        editTextNome = (EditText) findViewById(R.id.editTextNome);
        editTextPrefixo = (EditText) findViewById(R.id.editTextPrefixo);
        editTextNumero = (EditText) findViewById(R.id.editTextNumero);

        agenda = Agenda.getInstancia();
    }

    public void cadastrar(View view) {
        String nome = editTextNome.getText().toString();
        String prefixo = editTextPrefixo.getText().toString();
        String numero = editTextNumero.getText().toString();
        if (!nome.isEmpty() && !numero.isEmpty()) {
            String[] fones = {prefixo, numero};
            long id = agenda.adicionaContato(nome, fones);
            Intent data = new Intent();
            data.putExtra("idContato",id);
            setResult(RESULT_OK, data);
            finish();
        } else {
            Toast.makeText(this,"Nome e número são obrigatórios", Toast.LENGTH_LONG);
        }
    }

    public void voltar(View view) {
        finish();
    }
}
