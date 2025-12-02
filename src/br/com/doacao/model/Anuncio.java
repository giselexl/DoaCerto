package br.com.doacao.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Anuncio {

    private int idAnuncio;
    private String titulo;
    private String descricao;
    private LocalDateTime dataCriacao;
    private String statusAnuncio; // Ex: Ativo, Finalizado
    private String localizacao;
    private String opcoesFrete; // Ex: "Retirada", "Entrega"
    
    // RELACIONAMENTO: Um anúncio tem vários itens
    private List<Item> itens = new ArrayList<>();

    public Anuncio() {
        this.dataCriacao = LocalDateTime.now();
        this.statusAnuncio = "Ativo"; // Padrão ao criar
    }

    // Método auxiliar para adicionar itens na lista
    public void adicionarItem(Item item) {
        this.itens.add(item);
    }

    // --- GETTERS E SETTERS ---
    public int getIdAnuncio() { return idAnuncio; }
    public void setIdAnuncio(int idAnuncio) { this.idAnuncio = idAnuncio; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }

    public String getStatusAnuncio() { return statusAnuncio; }
    public void setStatusAnuncio(String statusAnuncio) { this.statusAnuncio = statusAnuncio; }

    public String getLocalizacao() { return localizacao; }
    public void setLocalizacao(String localizacao) { this.localizacao = localizacao; }

    public String getOpcoesFrete() { return opcoesFrete; }
    public void setOpcoesFrete(String opcoesFrete) { this.opcoesFrete = opcoesFrete; }

    public List<Item> getItens() { return itens; }
    public void setItens(List<Item> itens) { this.itens = itens; }
    
    @Override
    public String toString() {
        return titulo + " (" + statusAnuncio + ")";
    }
}