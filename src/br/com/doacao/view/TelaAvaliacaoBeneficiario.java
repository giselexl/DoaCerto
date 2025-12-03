package br.com.doacao.view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import br.com.doacao.dao.DoacaoDAO;
import br.com.doacao.model.Doacao;

public class TelaAvaliacaoBeneficiario extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    private JTable tabela;
    private JComboBox<String> cbNota;
    private JTextArea txtComentario;
    private DoacaoDAO dao = new DoacaoDAO();

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception e) {}
        EventQueue.invokeLater(() -> {
            try { new TelaAvaliacaoBeneficiario().setVisible(true); } catch (Exception e) {}
        });
    }

    public TelaAvaliacaoBeneficiario() {
        setTitle("Avaliar Beneficiário");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 500);
        contentPane = new JPanel();
        contentPane.setBackground(Color.WHITE);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblTitulo = new JLabel("Avaliar Beneficiário");
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setBounds(10, 11, 564, 30);
        contentPane.add(lblTitulo);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 50, 564, 150);
        contentPane.add(scrollPane);
        
        tabela = new JTable();
        scrollPane.setViewportView(tabela);

        JLabel lblNota = new JLabel("Nota para o Beneficiário:");
        lblNota.setBounds(20, 220, 150, 14);
        contentPane.add(lblNota);

        cbNota = new JComboBox<>();
        cbNota.addItem("5 - Excelente");
        cbNota.addItem("4 - Muito Bom");
        cbNota.addItem("3 - Bom");
        cbNota.addItem("2 - Ruim");
        cbNota.addItem("1 - Péssimo");
        cbNota.setBounds(20, 240, 150, 30);
        contentPane.add(cbNota);

        JLabel lblComentario = new JLabel("Comentário:");
        lblComentario.setBounds(200, 220, 100, 14);
        contentPane.add(lblComentario);

        txtComentario = new JTextArea();
        txtComentario.setBorder(javax.swing.BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        txtComentario.setBounds(200, 240, 370, 80);
        contentPane.add(txtComentario);

        JButton btnEnviar = new JButton("ENVIAR AVALIAÇÃO");
        btnEnviar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                enviarAvaliacao();
            }
        });
        btnEnviar.setBackground(new Color(0, 102, 204));
        btnEnviar.setForeground(Color.WHITE);
        btnEnviar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnEnviar.setBounds(200, 340, 200, 40);
        contentPane.add(btnEnviar);

        carregarTabela();
    }

    private void carregarTabela() {
        List<Doacao> lista = dao.listarDoacoes();
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Item Doado");
        modelo.addColumn("Quem Recebeu (Beneficiário)"); // Mostramos quem recebeu
        modelo.addColumn("Status");

        for (Doacao d : lista) {
            if ("Aprovada".equalsIgnoreCase(d.getStatusAtual())) {
                modelo.addRow(new Object[] {
                    d.getIdDoacao(),
                    d.getItem().getNomeItem(),
                    d.getBeneficiario().getNome(),
                    d.getStatusAtual()
                });
            }
        }
        tabela.setModel(modelo);
    }

    private void enviarAvaliacao() {
        int linha = tabela.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(null, "Selecione uma linha!");
            return;
        }
        int idDoacao = (int) tabela.getValueAt(linha, 0);
        String textoNota = (String) cbNota.getSelectedItem();
        int nota = Integer.parseInt(textoNota.substring(0, 1));
        
        dao.avaliarBeneficiario(idDoacao, nota, txtComentario.getText());
        txtComentario.setText("");
    }
}