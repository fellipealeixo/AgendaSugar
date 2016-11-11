package ifrn.tads.ddm.agendasugar;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ifrn.tads.ddm.agendasugar.modelo.Agenda;
import ifrn.tads.ddm.agendasugar.modelo.Contato;
import ifrn.tads.ddm.agendasugar.modelo.Fone;

public class MainActivity extends AppCompatActivity {

    private Agenda agenda;
    private TableLayout tableLayout;
    private View.OnLongClickListener deleteListener  = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View view) {
            final TableRow tr = (TableRow) view;
            TextView nome = (TextView) tr.findViewById(R.id.trTextViewNome);
            TextView idContato = (TextView) tr.findViewById(R.id.trTextViewId);
            final long contato = Long.parseLong(idContato.getText().toString());

            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setMessage("Confirma remoção do contato: "+nome.getText().toString())
                    .setTitle(R.string.alertTitulo);
            builder.setPositiveButton(R.string.alertOk, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    agenda.removeContato(contato);
                    ((TableLayout) tr.getParent()).removeView(tr);
                }
            });
            builder.setNegativeButton(R.string.alertCancel, null);
            AlertDialog dialog = builder.create();
            dialog.show();
            return true;
        }
    };
    private View.OnClickListener ligarListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            TableRow tableRow = (TableRow) view.getParent();
            TextView prefixo = (TextView) tableRow.findViewById(R.id.trTextViewPrefixo);
            TextView numero = (TextView) tableRow.findViewById(R.id.trTextViewNumero);
            String fone = "tel:"+prefixo.getText().toString()+numero.getText().toString();
            Uri uri = Uri.parse(fone);
            Intent ligar = new Intent(Intent.ACTION_CALL, uri);
            try {
                startActivity(ligar);
            } catch (SecurityException sex) {
                Log.e("Security",sex.getMessage());
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        agenda = Agenda.getInstancia();
        tableLayout = (TableLayout) findViewById(R.id.tableLayout);
        montaContatos();
    }


    private void montaContatos() {
        List<Contato> contatos = agenda.getContatos();
        for (Contato c: contatos) {
            mostraContato(c.getId());
        }
    }

    public void novoContato(View view) {
        Intent intent = new Intent(this, NovoContato.class);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == RESULT_OK) {
            long idContato = data.getLongExtra("idContato", 0);
            mostraContato(idContato);
        }
    }

    private void mostraContato(long idContato) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        Contato c = agenda.getContato(idContato);
        View row = inflater.inflate(R.layout.tablerow_novo_contato, null);
        TextView id = (TextView) row.findViewById(R.id.trTextViewId);
        TextView nome = (TextView) row.findViewById(R.id.trTextViewNome);
        TextView prefixo = (TextView) row.findViewById(R.id.trTextViewPrefixo);
        TextView numero = (TextView) row.findViewById(R.id.trTextViewNumero);
        Button btnLigar = (Button) row.findViewById(R.id.btnLigar);
        id.setText(""+c.getId());
        nome.setText(c.getNome());
        List<Fone> fones = c.getFones();
        for (Fone f : fones) {
            prefixo.setText("("+f.getPrefixo()+")");
            numero.setText(f.getNumero());
            break;
        }
        row.setOnLongClickListener(deleteListener);
        btnLigar.setOnClickListener(ligarListener);
        tableLayout.addView(row);
    }

    public void fechar(View view) {
        finish();
    }
}

