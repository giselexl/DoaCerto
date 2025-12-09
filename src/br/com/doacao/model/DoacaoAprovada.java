package br.com.doacao.model;

import java.time.LocalDateTime;

public class DoacaoAprovada extends Doacao {

    private LocalDateTime dataEntregaPrevista;
    private LocalDateTime dataEntregaReal;

    @Override
    public String getStatusAtual() {
        return "Aprovada";
    }

    public LocalDateTime getDataEntregaPrevista() { return dataEntregaPrevista; }
    public void setDataEntregaPrevista(LocalDateTime dataEntregaPrevista) { this.dataEntregaPrevista = dataEntregaPrevista; }

    public LocalDateTime getDataEntregaReal() { return dataEntregaReal; }
    public void setDataEntregaReal(LocalDateTime dataEntregaReal) { this.dataEntregaReal = dataEntregaReal; }
}
