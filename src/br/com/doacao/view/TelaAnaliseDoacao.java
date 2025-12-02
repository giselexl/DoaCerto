package br.com.doacao.view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import br.com.doacao.dao.DoacaoDAO;
import br.com.doacao.model.Doacao;

public class TelaAnaliseDoacao extends JFrame {

  
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    private JTable tabela;
    private DoacaoDAO dao = new DoacaoDAO(); // Instância do DAO

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    TelaAnaliseDoacao frame = new TelaAnaliseDoacao();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public TelaAnaliseDoacao() {
        setTitle("Gestão de Doações Pendentes");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 400);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 11, 560, 250);
        contentPane.add(scrollPane);

        tabela = new JTable();
        scrollPane.setViewportView(tabela);

        // --- BOTÃO APROVAR ---
        JButton btnAprovar = new JButton("Aprovar Selecionada");
        btnAprovar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                acaoAprovar();
            }
        });
        btnAprovar.setBounds(80, 280, 180, 40);
        contentPane.add(btnAprovar);

        // --- BOTÃO REJEITAR ---
        JButton btnRejeitar = new JButton("Rejeitar Selecionada");
        btnRejeitar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                acaoRejeitar();
            }
        });
        btnRejeitar.setBounds(320, 280, 180, 40);
        contentPane.add(btnRejeitar);

        carregarTabela();
    }

    private void carregarTabela() {
        List<Doacao> lista = dao.listarDoacoesEmAnalise();
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Item");
        modelo.addColumn("Beneficiário");
        modelo.addColumn("Data Solicitação");

        for (Doacao d : lista) {
            modelo.addRow(new Object[] {
                d.getIdDoacao(),
                d.getItem().getNomeItem(),
                d.getBeneficiario().getNome(),
                d.getDataSolicitacao()
            });
        }
        tabela.setModel(modelo);
    }

    // Lógica de Aprovação
    private void acaoAprovar() {
        int linhaSelecionada = tabela.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(null, "Selecione uma doação na tabela!");
            return;
        }

        // Pega o ID da coluna 0 (onde pusemos o ID)
        int idDoacao = (int) tabela.getValueAt(linhaSelecionada, 0);

        // Pergunta simples para gerar a data
        String diasStr = JOptionPane.showInputDialog("Aprovar: Entrega prevista para daqui a quantos dias?");
        if (diasStr != null && !diasStr.isEmpty()) {
            try {
                int dias = Integer.parseInt(diasStr);
                
                // Calcula a data atual + dias digitados
                LocalDateTime dataFutura = LocalDateTime.now().plusDays(dias);
                Timestamp dataSQL = Timestamp.valueOf(dataFutura);
                
                dao.aprovarDoacao(idDoacao, dataSQL);
                carregarTabela(); // Atualiza a lista (a doação vai sumir, pois não está mais "Em Analise")
                
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Digite apenas números!");
            }
        }
    }

    // Lógica de Rejeição
    private void acaoRejeitar() {
        int linhaSelecionada = tabela.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(null, "Selecione uma doação na tabela!");
            return;
        }

        int idDoacao = (int) tabela.getValueAt(linhaSelecionada, 0);

        String motivo = JOptionPane.showInputDialog("Qual o motivo da rejeição?");
        if (motivo != null && !motivo.isEmpty()) {
            dao.rejeitarDoacao(idDoacao, motivo);
            carregarTabela(); // Atualiza a lista
        }
    }
}