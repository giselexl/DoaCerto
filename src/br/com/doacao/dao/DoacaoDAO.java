package br.com.doacao.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import br.com.doacao.model.Beneficiario;
import br.com.doacao.model.Doacao;
import br.com.doacao.model.DoacaoAprovada;
import br.com.doacao.model.DoacaoEmAnalise;
import br.com.doacao.model.DoacaoRejeitada;
import br.com.doacao.model.Item;
import br.com.doacao.util.ConnectionFactory;

public class DoacaoDAO {

    private Connection con;

    public DoacaoDAO() {
        this.con = new ConnectionFactory().getConnection();
    }

    // 1. REALIZAR DOAÇÃO (Polimórfico: Aceita qualquer filha de Doacao)
    public void realizarDoacao(Doacao doacao) {
        
        String sqlDoacao = "INSERT INTO Doacao (idBeneficiario, dataSolicitacao, statusDoacao) VALUES (?, ?, ?)";
        String sqlItem = "UPDATE Item SET idDoacao = ? WHERE idItem = ?";
        
        try {
            PreparedStatement stmt = con.prepareStatement(sqlDoacao, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, doacao.getBeneficiario().getIdBeneficiario());
            stmt.setTimestamp(2, Timestamp.valueOf(doacao.getDataSolicitacao())); 
            
            // Pega o status dinamicamente da classe filha (Em Analise, Aprovada, etc)
            stmt.setString(3, doacao.getStatusAtual());
            
            stmt.execute();
            
            int idDoacaoGerado = 0;
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                idDoacaoGerado = rs.getInt(1);
            }
            stmt.close();
            
            if (idDoacaoGerado > 0) {
                PreparedStatement stmtItem = con.prepareStatement(sqlItem);
                stmtItem.setInt(1, idDoacaoGerado);
                stmtItem.setInt(2, doacao.getItem().getIdItem());
                stmtItem.execute();
                stmtItem.close();
                JOptionPane.showMessageDialog(null, "Pedido de doação enviado para análise! ID: " + idDoacaoGerado);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
        }
    }
    
    // 2. LISTAR TUDO (Factory Pattern - Cria a classe certa dependendo do status)
    public List<Doacao> listarDoacoes() {
        List<Doacao> lista = new ArrayList<>();
        String sql = "SELECT d.*, b.nome as nomeBen, i.nomeItem, doa.nome as nomeDoa " +
                     "FROM Doacao d " +
                     "INNER JOIN Beneficiario b ON d.idBeneficiario = b.idBeneficiario " +
                     "INNER JOIN Item i ON i.idDoacao = d.idDoacao " +
                     "INNER JOIN Doador doa ON i.idDoador = doa.idDoador";
        
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()) {
                String status = rs.getString("statusDoacao");
                Doacao d = null;
                
                // DECISÃO POLIMÓRFICA
                if("Aprovada".equalsIgnoreCase(status)) {
                    d = new DoacaoAprovada();
                    // Aqui você poderia pegar dataEntregaPrevista do banco se quisesse
                } else if ("Rejeitada".equalsIgnoreCase(status)) {
                    d = new DoacaoRejeitada();
                } else {
                    d = new DoacaoEmAnalise();
                }
                
                d.setIdDoacao(rs.getInt("idDoacao"));
                d.setDataSolicitacao(rs.getTimestamp("dataSolicitacao").toLocalDateTime());
                
                Beneficiario b = new Beneficiario();
                b.setNome(rs.getString("nomeBen"));
                d.setBeneficiario(b);
                
                Item i = new Item();
                i.setNomeItem(rs.getString("nomeItem"));
                br.com.doacao.model.Doador doador = new br.com.doacao.model.Doador();
                doador.setNome(rs.getString("nomeDoa"));
                i.setDoador(doador);
                
                d.setItem(i);
                lista.add(d);
            }
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    // 3. LISTAR APENAS 'EM ANÁLISE'
    public List<Doacao> listarDoacoesEmAnalise() {
        List<Doacao> lista = new ArrayList<>();
        String sql = "SELECT d.*, b.nome as nomeBen, i.nomeItem " +
                     "FROM Doacao d " +
                     "INNER JOIN Beneficiario b ON d.idBeneficiario = b.idBeneficiario " +
                     "INNER JOIN Item i ON i.idDoacao = d.idDoacao " +
                     "WHERE d.statusDoacao = 'Em Analise'";
        
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                // Aqui sabemos que é Em Analise
                DoacaoEmAnalise d = new DoacaoEmAnalise();
                d.setIdDoacao(rs.getInt("idDoacao"));
                d.setDataSolicitacao(rs.getTimestamp("dataSolicitacao").toLocalDateTime());
                
                Beneficiario b = new Beneficiario();
                b.setNome(rs.getString("nomeBen"));
                d.setBeneficiario(b);
                
                Item i = new Item();
                i.setNomeItem(rs.getString("nomeItem"));
                d.setItem(i);
                
                lista.add(d);
            }
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    // 4. APROVAR
    public void aprovarDoacao(int idDoacao, Timestamp dataEntrega) {
        String sql = "UPDATE Doacao SET statusDoacao = 'Aprovada', dataAprovacao = NOW(), dataEntregaPrevista = ? WHERE idDoacao = ?";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setTimestamp(1, dataEntrega);
            stmt.setInt(2, idDoacao);
            stmt.execute();
            stmt.close();
            JOptionPane.showMessageDialog(null, "Doação APROVADA!");
        } catch (Exception e) { e.printStackTrace(); }
    }

    // 5. REJEITAR
    public void rejeitarDoacao(int idDoacao, String motivo) {
        String sql = "UPDATE Doacao SET statusDoacao = 'Rejeitada', dataRejeicao = NOW(), motivoRejeicao = ? WHERE idDoacao = ?";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, motivo);
            stmt.setInt(2, idDoacao);
            stmt.execute();
            stmt.close();
            JOptionPane.showMessageDialog(null, "Doação REJEITADA.");
        } catch (Exception e) { e.printStackTrace(); }
    }
    
 // --- MÉTODO DE AVALIAÇÃO ---
    public void avaliarDoacao(int idDoacao, int nota, String comentario) {
        // 1. Atualiza a doação com a nota e o texto
        String sqlAvaliar = "UPDATE Doacao SET nota = ?, avaliacao = ? WHERE idDoacao = ?";
        
        // 2. Descobre quem é o doador dessa doação (via Item)
        // Precisamos disso para saber de quem vamos recalcular a média
        String sqlDescobreDoador = "SELECT i.idDoador FROM Item i WHERE i.idDoacao = ?";
        
        // 3. Calcula a nova média desse doador e atualiza a tabela Doador
        // (Média de todas as doações que ele já fez e que têm nota > 0)
        String sqlAtualizaMedia = "UPDATE Doador SET pontuacaoAvaliacao = (" +
                                  "    SELECT AVG(d.nota) " +
                                  "    FROM Doacao d " +
                                  "    INNER JOIN Item i ON i.idDoacao = d.idDoacao " +
                                  "    WHERE i.idDoador = ? AND d.nota > 0" +
                                  ") WHERE idDoador = ?";

        try {
            // Passo A: Gravar a avaliação
            PreparedStatement stmt = con.prepareStatement(sqlAvaliar);
            stmt.setInt(1, nota);
            stmt.setString(2, comentario);
            stmt.setInt(3, idDoacao);
            stmt.execute();
            stmt.close();

            // Passo B: Descobrir o ID do Doador
            PreparedStatement stmtDescobre = con.prepareStatement(sqlDescobreDoador);
            stmtDescobre.setInt(1, idDoacao);
            ResultSet rs = stmtDescobre.executeQuery();
            
            int idDoador = 0;
            if (rs.next()) {
                idDoador = rs.getInt("idDoador");
            }
            stmtDescobre.close();

            // Passo C: Recalcular a média dele
            if (idDoador > 0) {
                PreparedStatement stmtMedia = con.prepareStatement(sqlAtualizaMedia);
                stmtMedia.setInt(1, idDoador); // Para o cálculo da média
                stmtMedia.setInt(2, idDoador); // Para o WHERE do Update
                stmtMedia.execute();
                stmtMedia.close();
            }

            JOptionPane.showMessageDialog(null, "Avaliação enviada e pontuação do doador atualizada!");

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao avaliar: " + e.getMessage());
        }
    }
    
 // --- NOVO: AVALIAR O BENEFICIÁRIO (Feito pelo Doador) ---
    public void avaliarBeneficiario(int idDoacao, int nota, String comentario) {
        // 1. Grava a nota na doação (nos campos novos)
        String sqlAvaliar = "UPDATE Doacao SET notaBeneficiario = ?, avaliacaoBeneficiario = ? WHERE idDoacao = ?";
        
        // 2. Descobre quem é o beneficiário dessa doação
        String sqlDescobreBen = "SELECT idBeneficiario FROM Doacao WHERE idDoacao = ?";
        
        // 3. Recalcula a média do Beneficiário
        String sqlAtualizaMedia = "UPDATE Beneficiario SET pontuacaoAvaliacao = (" +
                                  "    SELECT AVG(notaBeneficiario) " +
                                  "    FROM Doacao " +
                                  "    WHERE idBeneficiario = ? AND notaBeneficiario > 0" +
                                  ") WHERE idBeneficiario = ?";

        try {
            // A. Grava avaliação
            PreparedStatement stmt = con.prepareStatement(sqlAvaliar);
            stmt.setInt(1, nota);
            stmt.setString(2, comentario);
            stmt.setInt(3, idDoacao);
            stmt.execute();
            stmt.close();

            // B. Pega ID do Beneficiário
            PreparedStatement stmtDescobre = con.prepareStatement(sqlDescobreBen);
            stmtDescobre.setInt(1, idDoacao);
            ResultSet rs = stmtDescobre.executeQuery();
            int idBen = 0;
            if (rs.next()) idBen = rs.getInt("idBeneficiario");
            stmtDescobre.close();

            // C. Atualiza média dele
            if (idBen > 0) {
                PreparedStatement stmtMedia = con.prepareStatement(sqlAtualizaMedia);
                stmtMedia.setInt(1, idBen);
                stmtMedia.setInt(2, idBen);
                stmtMedia.execute();
                stmtMedia.close();
            }
            JOptionPane.showMessageDialog(null, "Beneficiário avaliado com sucesso!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}