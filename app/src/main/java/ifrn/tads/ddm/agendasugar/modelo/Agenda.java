package ifrn.tads.ddm.agendasugar.modelo;

import java.util.List;

public class Agenda {
    private List<Contato> contatos;
    private static Agenda instancia = null;

    private Agenda() {
        carregaContatos();
    }

    public static Agenda getInstancia() {
        if (instancia == null) {
            instancia = new Agenda();
        }
        return instancia;
    }

    private void carregaContatos() {
        contatos = Contato.listAll(Contato.class);
    }

    public List<Contato> getContatos() {
        return contatos;
    }

    public long adicionaContato(String nome, String[] fones) {
        Contato contato = new Contato(nome);
        long id = contato.save();
        for (int i = 0; i < fones.length-1; i++) {
            String prefixo = fones[i];
            String numero = fones[i+1];
            Fone fone = new Fone(prefixo, numero, contato);
            fone.save();
        }
        carregaContatos();
        return id;
    }public Contato getContato(long idContato) {
        return Contato.findById(Contato.class, idContato);
    }

    public void removeContato(long id) {
        Contato c = Contato.findById(Contato.class, id);
        List<Fone> telefones = c.getFones();
        for (Fone fone: telefones) {
            fone.delete();
        }
        c.delete();
        carregaContatos();
    }


}