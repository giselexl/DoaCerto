package br.com.doacao.model;

public class DoacaoEmAnalise extends Doacao {

    @Override
    public String getStatusAtual() {
        return "Em Analise";
    }
}
