package ifrn.tads.ddm.agendasugar.modelo;

import com.orm.SugarRecord;


public class Fone extends SugarRecord {
    private String prefixo;
    private String numero;
    private Contato contato;

    public Fone() {
        super();
    }

    public Fone(String prefixo, String numero, Contato contato) {
        this.prefixo = prefixo;
        this.numero = numero;
        this.contato = contato;
    }

    public String getPrefixo() {
        return prefixo;
    }

    public void setPrefixo(String prefixo) {
        this.prefixo = prefixo;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Contato getContato() {
        return contato;
    }

    public void setContato(Contato contato) {
        this.contato = contato;
    }
}
