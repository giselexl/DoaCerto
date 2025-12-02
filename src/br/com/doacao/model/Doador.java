package br.com.doacao.model;
import java.time.LocalDateTime; // Import para lidar com datas
public class Doador {
	    
	    // 1. Atributos Privados (Só essa classe mexe neles)
	    private int idDoador;
	    private String nome;
	    private String email;
	    private String telefone;
	    private String endereco;
	    private LocalDateTime dataCadastro;
	    private double pontuacaoAvaliacao;

	    // 2. Construtor Vazio (Obrigatório para o Java criar o objeto depois)
	    public Doador() {
	    }

		public int getIdDoador() {
			return idDoador;
		}

		public void setIdDoador(int idDoador) {
			this.idDoador = idDoador;
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

		public LocalDateTime getDataCadastro() {
			return dataCadastro;
		}

		public void setDataCadastro(LocalDateTime dataCadastro) {
			this.dataCadastro = dataCadastro;
		}

		public double getPontuacaoAvaliacao() {
			return pontuacaoAvaliacao;
		}

		public void setPontuacaoAvaliacao(double pontuacaoAvaliacao) {
			this.pontuacaoAvaliacao = pontuacaoAvaliacao;
		}
		
		@Override
	    public String toString() {
	        return "Doador [nome=" + nome + ", email=" + email + "]";
		}
	    
}
