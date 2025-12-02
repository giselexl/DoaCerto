package br.com.doacao.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import br.com.doacao.model.Anuncio;
import br.com.doacao.model.Item;
import br.com.doacao.util.ConnectionFactory;

public class AnuncioDAO {

    private Connection con;

    public AnuncioDAO() {
        this.con = new ConnectionFactory().getConnection();
    }

    // 1. CADASTRAR ANÚNCIO
    public void cadastrarAnuncio(Anuncio anuncio) {
        String sqlAnuncio = "INSERT INTO Anuncio (titulo, descricao, dataCriacao, statusAnuncio, localizacao, opcoesFrete) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlUpdateItem = "UPDATE Item SET idAnuncio = ? WHERE idItem = ?";
        
        try {
            // Grava o Anúncio
            PreparedStatement stmt = con.prepareStatement(sqlAnuncio, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, anuncio.getTitulo());
            stmt.setString(2, anuncio.getDescricao());
            stmt.setTimestamp(3, Timestamp.valueOf(anuncio.getDataCriacao()));
            stmt.setString(4, anuncio.getStatusAnuncio());
            stmt.setString(5, anuncio.getLocalizacao());
            stmt.setString(6, anuncio.getOpcoesFrete());
            
            stmt.execute();
            
            // Pega o ID gerado
            int idAnuncioGerado = 0;
            ResultSet rs = stmt.getGeneratedKeys();
            if(rs.next()) {
                idAnuncioGerado = rs.getInt(1);
            }
            stmt.close();
            
            // Atualiza os Itens da lista (Vincular ao Anuncio)
            if (idAnuncioGerado > 0 && !anuncio.getItens().isEmpty()) {
                PreparedStatement stmtItem = con.prepareStatement(sqlUpdateItem);
                
                for (Item item : anuncio.getItens()) {
                    stmtItem.setInt(1, idAnuncioGerado); // ID do Anuncio
                    stmtItem.setInt(2, item.getIdItem()); // ID do Item
                    stmtItem.execute();
                }
                stmtItem.close();
            }
            
            JOptionPane.showMessageDialog(null, "Anúncio publicado com sucesso!");
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao anunciar: " + e.getMessage());
        }
    }
    
    // 2. LISTAR ANÚNCIOS (CORRIGIDO)
    public List<Anuncio> listarAnuncios() {
        List<Anuncio> lista = new ArrayList<>();
        String sql = "SELECT * FROM Anuncio";
        
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()) {
                Anuncio a = new Anuncio();
                a.setIdAnuncio(rs.getInt("idAnuncio"));
                a.setTitulo(rs.getString("titulo"));
                a.setDescricao(rs.getString("descricao"));
                a.setStatusAnuncio(rs.getString("statusAnuncio"));
                
                // --- AQUI ESTAVA FALTANDO: ---
                a.setLocalizacao(rs.getString("localizacao")); 
                a.setOpcoesFrete(rs.getString("opcoesFrete"));
                
                lista.add(a);
            }
            stmt.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}