package br.com.doacao.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import br.com.doacao.model.Doador;
import br.com.doacao.model.Item;
import br.com.doacao.util.ConnectionFactory;

public class ItemDAO {
    
    private Connection con;

    public ItemDAO() {
        this.con = new ConnectionFactory().getConnection();
    }

    public void cadastrarItem(Item item) {
        // Note que no SQL nós pedimos o idDoador (a chave estrangeira)
        String sql = "INSERT INTO Item (nomeItem, descricao, categoria, estadoConservacao, idDoador) VALUES (?, ?, ?, ?, ?)";
        
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            
            stmt.setString(1, item.getNomeItem());
            stmt.setString(2, item.getDescricao());
            stmt.setString(3, item.getCategoria());
            stmt.setString(4, item.getEstadoConservacao());
            
            // Para salvar no banco, precisamos extrair apenas o número (ID) do objeto Doador
            stmt.setInt(5, item.getDoador().getIdDoador());
            
            stmt.execute();
            stmt.close();
            
            JOptionPane.showMessageDialog(null, "Item cadastrado com sucesso!");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar item: " + e.getMessage());
            e.printStackTrace();
        }
    }
 // Método para listar Itens trazendo junto o nome do Doador
    public List<Item> listarItens() {
        List<Item> lista = new ArrayList<>();
        
        // SQL avançado: Une a tabela Item com a Doador para pegar o nome dele
        String sql = "SELECT i.*, d.nome as nomeDoador " +
                     "FROM Item i " +
                     "INNER JOIN Doador d ON i.idDoador = d.idDoador";
        
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Item item = new Item();
                item.setIdItem(rs.getInt("idItem"));
                item.setNomeItem(rs.getString("nomeItem"));
                item.setDescricao(rs.getString("descricao"));
                item.setCategoria(rs.getString("categoria"));
                item.setEstadoConservacao(rs.getString("estadoConservacao"));
                
                // Montar o objeto Doador só com o que precisamos (Nome)
                Doador d = new Doador();
                d.setIdDoador(rs.getInt("idDoador"));
                d.setNome(rs.getString("nomeDoador")); // Pegamos o nome graças ao JOIN
                
                item.setDoador(d); // Vincula o doador ao item
                
                lista.add(item);
            }
            stmt.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return lista;
    }
    
 // Método para buscar itens que AINDA NÃO FORAM ANUNCIADOS
    // (Usado na tela de criar anúncio)
    public java.util.List<Item> listarItensDisponiveis() {
        java.util.List<Item> lista = new java.util.ArrayList<>();
        
        // O segredo: Busca itens onde idAnuncio é NULO (ou seja, estão livres)
        String sql = "SELECT * FROM Item WHERE idAnuncio IS NULL";
        
        try {
            java.sql.PreparedStatement stmt = con.prepareStatement(sql);
            java.sql.ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Item item = new Item();
                item.setIdItem(rs.getInt("idItem"));
                item.setNomeItem(rs.getString("nomeItem"));
                item.setDescricao(rs.getString("descricao"));
                item.setCategoria(rs.getString("categoria"));
                item.setEstadoConservacao(rs.getString("estadoConservacao"));
                
                lista.add(item);
            }
            stmt.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return lista;
    }
}