package br.com.doacao.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import br.com.doacao.model.Beneficiario;
import br.com.doacao.util.ConnectionFactory;

public class BeneficiarioDAO {
    private Connection con;

    public BeneficiarioDAO() {
        this.con = new ConnectionFactory().getConnection();
    }

    // 1. Cadastrar
    public void cadastrar(Beneficiario b) {
        String sql = "INSERT INTO Beneficiario (nome, email, telefone, endereco) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, b.getNome());
            stmt.setString(2, b.getEmail());
            stmt.setString(3, b.getTelefone());
            stmt.setString(4, b.getEndereco());
            stmt.execute();
            stmt.close();
            JOptionPane.showMessageDialog(null, "Beneficiário cadastrado!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 2. Listar 
    public List<Beneficiario> listarTodos() {
        List<Beneficiario> lista = new ArrayList<>();
        String sql = "SELECT * FROM Beneficiario";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
            	Beneficiario b = new Beneficiario();
                b.setIdBeneficiario(rs.getInt("idBeneficiario"));
                b.setNome(rs.getString("nome"));
                b.setEmail(rs.getString("email"));
                b.setTelefone(rs.getString("telefone"));
                b.setEndereco(rs.getString("endereco"));
                
                // --- LINHA NOVA: PEGAR A NOTA ---
                b.setPontuacaoAvaliacao(rs.getDouble("pontuacaoAvaliacao"));
                
                lista.add(b);
            }
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
    
 // Busca lista de comentários e notas recebidas por um Beneficiário
    public List<String> listarComentarios(int idBeneficiario) {
        List<String> comentarios = new ArrayList<>();
        // Aqui é mais direto, pois o idBeneficiario está na tabela Doacao
        String sql = "SELECT notaBeneficiario, avaliacaoBeneficiario " +
                     "FROM Doacao " +
                     "WHERE idBeneficiario = ? AND notaBeneficiario > 0";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, idBeneficiario);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int nota = rs.getInt("notaBeneficiario");
                String texto = rs.getString("avaliacaoBeneficiario");
                comentarios.add("★ " + nota + " - " + texto);
            }
            stmt.close();
        } catch (Exception e) { e.printStackTrace(); }
        return comentarios;
    }
}