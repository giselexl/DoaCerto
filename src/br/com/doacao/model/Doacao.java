package br.com.doacao.model;

import java.time.LocalDateTime;

// CLASSE MÃE ABSTRATA
public abstract class Doacao {

    protected int idDoacao;
    protected LocalDateTime dataSolicitacao;
    protected LocalDateTime dataAtualizacaoStatus;
    
    // Relacionamentos
    protected Beneficiario beneficiario;
    protected Item item;

    public Doacao() {
        this.dataSolicitacao = LocalDateTime.now();
    }

 
    // Método que as filhas são obrigadas a responder
    public abstract String getStatusAtual();

    // --- GETTERS E SETTERS COMUNS ---
    public int getIdDoacao() { return idDoacao; }
    public void setIdDoacao(int idDoacao) { this.idDoacao = idDoacao; }

    public LocalDateTime getDataSolicitacao() { return dataSolicitacao; }
    public void setDataSolicitacao(LocalDateTime dataSolicitacao) { this.dataSolicitacao = dataSolicitacao; }

    public LocalDateTime getDataAtualizacaoStatus() { return dataAtualizacaoStatus; }
    public void setDataAtualizacaoStatus(LocalDateTime dataAtualizacaoStatus) { this.dataAtualizacaoStatus = dataAtualizacaoStatus; }

    public Beneficiario getBeneficiario() { return beneficiario; }
    public void setBeneficiario(Beneficiario beneficiario) { this.beneficiario = beneficiario; }

    public Item getItem() { return item; }
    public void setItem(Item item) { this.item = item; }
    
    @Override
    public String toString() {
        return "Doação [ID=" + idDoacao + ", Status=" + getStatusAtual() + "]";
    }
}