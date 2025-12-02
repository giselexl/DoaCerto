package br.com.doacao.model;

import java.time.LocalDateTime;

public class Item {

    private int idItem;
    private String nomeItem;
    private String descricao;
    private String categoria; // Ex: Móveis, Eletro, Roupa
    private String estadoConservacao; // Ex: Novo, Bom, Ruim
    private LocalDateTime dataCadastro;
    
    // RELACIONAMENTO: Um Item pertence a um Doador
    // No banco isso vira "idDoador", mas no Java usamos o Objeto inteiro
    private Doador doador; 

   

    // --- GETTERS E SETTERS ---
    // (Dica: Use o botão direito -> Source -> Generate Getters and Setters para criar rápido)
    
   
    
    @Override
    public String toString() {
        return nomeItem; // Isso vai ajudar na hora de mostrar na tela
    }



	public int getIdItem() {
		return idItem;
	}



	public void setIdItem(int idItem) {
		this.idItem = idItem;
	}



	public String getNomeItem() {
		return nomeItem;
	}



	public void setNomeItem(String nomeItem) {
		this.nomeItem = nomeItem;
	}



	public String getDescricao() {
		return descricao;
	}



	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}



	public String getCategoria() {
		return categoria;
	}



	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}



	public String getEstadoConservacao() {
		return estadoConservacao;
	}



	public void setEstadoConservacao(String estadoConservacao) {
		this.estadoConservacao = estadoConservacao;
	}



	public LocalDateTime getDataCadastro() {
		return dataCadastro;
	}



	public void setDataCadastro(LocalDateTime dataCadastro) {
		this.dataCadastro = dataCadastro;
	}



	public Doador getDoador() {
		return doador;
	}



	public void setDoador(Doador doador) {
		this.doador = doador;
	}
}