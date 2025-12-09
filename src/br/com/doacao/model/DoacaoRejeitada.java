package br.com.doacao.model;

import java.time.LocalDateTime;

public class DoacaoRejeitada extends Doacao {

    private String motivoRejeicao;
    private LocalDateTime dataRejeicao;

    @Override
    public String getStatusAtual() {
        return "Rejeitada";
    }

    public String getMotivoRejeicao() { return motivoRejeicao; }
    public void setMotivoRejeicao(String motivoRejeicao) { this.motivoRejeicao = motivoRejeicao; }

    public LocalDateTime getDataRejeicao() { return dataRejeicao; }
    public void setDataRejeicao(LocalDateTime dataRejeicao) { this.dataRejeicao = dataRejeicao; }
}
