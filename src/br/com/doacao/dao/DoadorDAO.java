package br.com.doacao.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.doacao.model.Doador;
import br.com.doacao.util.ConnectionFactory;

public class DoadorDAO {

    private Connection con;

    // Construtor: Abre a conexão assim que chama o DAO
    public DoadorDAO() {
        this.con = new ConnectionFactory().getConnection();
    }

    // Método para cadastrar (INSERT)
    public void cadastrarDoador(Doador d) {
        
        // 1. O SQL aumentou. Agora temos 5 interrogações (?)
        String sql = "INSERT INTO Doador (nome, email, telefone, endereco, pontuacaoAvaliacao) VALUES (?, ?, ?, ?, ?)";

        try {
            // 2. Preparar a conexão
            PreparedStatement stmt = con.prepareStatement(sql);

            // 3. Substituir os (?) pelos dados do objeto Doador
            stmt.setString(1, d.getNome());
            stmt.setString(2, d.getEmail());
            stmt.setString(3, d.getTelefone());   // Novo!
            stmt.setString(4, d.getEndereco());   // Novo!
            stmt.setDouble(5, 5.0);               // Novo! (Começamos com nota 5.0 padrão)

            // 4. Executar e fechar
            stmt.execute();
            stmt.close();
            
            // Feedback visual (opcional, pode tirar se preferir só no console)
            JOptionPane.showMessageDialog(null, "Cadastrado com sucesso!");

        } catch (SQLException e) {
            // Se der erro (ex: email duplicado), avisa aqui
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar: " + e.getMessage());
            e.printStackTrace();
        }
    }
 // Método para listar todos os doadores do banco
    public List<Doador> listarDoadores() {
        List<Doador> lista = new ArrayList<>();
        String sql = "SELECT * FROM doador";
        
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery(); // O ResultSet é a "tabela" que voltou do banco
            
            while (rs.next()) {
                Doador d = new Doador();
                // Pegamos os dados das colunas do banco e jogamos no objeto
                d.setIdDoador(rs.getInt("idDoador"));
                d.setNome(rs.getString("nome"));
                d.setEmail(rs.getString("email"));
                d.setTelefone(rs.getString("telefone"));
                d.setEndereco(rs.getString("endereco"));
                
                lista.add(d); // Adiciona na lista de retorno
            }
            stmt.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return lista;
    }
 // Busca lista de comentários e notas recebidas por um Doador
    public List<String> listarComentarios(int idDoador) {
        List<String> comentarios = new ArrayList<>();

        String sql = "SELECT d.nota, d.avaliacao " +
                     "FROM Doacao d " +
                     "INNER JOIN Item i ON i.idDoacao = d.idDoacao " +
                     "WHERE i.idDoador = ? AND d.nota > 0";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, idDoador);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int nota = rs.getInt("nota");
                String texto = rs.getString("avaliacao");
                comentarios.add("★ " + nota + " - " + texto);
            }
            stmt.close();
        } catch (Exception e) { e.printStackTrace(); }
        return comentarios;
    }
    
}