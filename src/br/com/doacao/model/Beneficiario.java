package br.com.doacao.model;

public class Beneficiario {
    private int idBeneficiario;
    private String nome;
    private String email;
    private String telefone;
    private String endereco;
	private double pontuacaoAvaliacao;
    
	
	
     public double getPontuacaoAvaliacao() {
		return pontuacaoAvaliacao;
	}



	public void setPontuacaoAvaliacao(double pontuacaoAvaliacao) {
		this.pontuacaoAvaliacao = pontuacaoAvaliacao;
	}


    public int getIdBeneficiario() {
		return idBeneficiario;
	}



	public void setIdBeneficiario(int idBeneficiario) {
		this.idBeneficiario = idBeneficiario;
	}



	public String getNome() {
		return nome;
	}



	public void setNome(String nome) {
		this.nome = nome;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getTelefone() {
		return telefone;
	}



	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}



	public String getEndereco() {
		return endereco;
	}



	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}



	@Override
    public String toString() {
        return nome; // Para aparecer bonito no ComboBox depois
    }
}
