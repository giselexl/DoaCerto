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
                // ... pode pegar os outros campos se quiser
                lista.add(b);
            }
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}