package ifrn.tads.ddm.agendasugar.modelo;

import com.orm.SugarRecord;

import java.util.List;

public class Contato extends SugarRecord {
    private String nome;

    public Contato() {
        super();
    }

    public Contato(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Fone> getFones() {
        String[] id = {""+getId()};
        return Fone.find(Fone.class, "contato = ?", id);
    }
}
