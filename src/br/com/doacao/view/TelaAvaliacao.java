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

public class TelaAvaliacao extends JFrame {

   
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    private JTable tabela;
    private JComboBox<String> cbNota;
    private JTextArea txtComentario;
    private DoacaoDAO dao = new DoacaoDAO();

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) { e.printStackTrace(); }

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    TelaAvaliacao frame = new TelaAvaliacao();
                    frame.setVisible(true);
                } catch (Exception e) { e.printStackTrace(); }
            }
        });
    }

    public TelaAvaliacao() {
        setTitle("Avaliar Doações Concluídas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 500);
        contentPane = new JPanel();
        contentPane.setBackground(Color.WHITE);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblTitulo = new JLabel("Avaliar Experiência");
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setBounds(10, 11, 564, 30);
        contentPane.add(lblTitulo);

        // Tabela para selecionar qual doação avaliar
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 50, 564, 150);
        contentPane.add(scrollPane);
        
        tabela = new JTable();
        scrollPane.setViewportView(tabela);

        // --- ÁREA DE AVALIAÇÃO ---
        JLabel lblNota = new JLabel("Nota (1 a 5):");
        lblNota.setBounds(20, 220, 100, 14);
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
        btnEnviar.setBackground(new Color(255, 215, 0)); // Dourado (Estrela)
        btnEnviar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnEnviar.setBounds(200, 340, 200, 40);
        contentPane.add(btnEnviar);

        carregarTabela();
    }

    private void carregarTabela() {
        // Usa o listarDoacoes do DAO que já traz tudo
        // O ideal seria filtrar só as "Aprovada", vamos fazer isso visualmente aqui
        List<Doacao> lista = dao.listarDoacoes();
        
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Item");
        modelo.addColumn("Doador");
        modelo.addColumn("Status");

        for (Doacao d : lista) {
            // Só mostra na tabela se estiver APROVADA (Concluída)
            if ("Aprovada".equalsIgnoreCase(d.getStatusAtual())) {
                modelo.addRow(new Object[] {
                    d.getIdDoacao(),
                    d.getItem().getNomeItem(),
                    d.getItem().getDoador().getNome(),
                    d.getStatusAtual()
                });
            }
        }
        tabela.setModel(modelo);
    }

    private void enviarAvaliacao() {
        int linha = tabela.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(null, "Selecione uma doação na tabela acima!");
            return;
        }

        int idDoacao = (int) tabela.getValueAt(linha, 0);
        
        // Pega o número da nota (O char na posição 0 da string "5 - Excelente")
        String textoNota = (String) cbNota.getSelectedItem();
        int nota = Integer.parseInt(textoNota.substring(0, 1));
        
        String comentario = txtComentario.getText();

        dao.avaliarDoacao(idDoacao, nota, comentario);
        
        // Limpa campos
        txtComentario.setText("");
    }
}