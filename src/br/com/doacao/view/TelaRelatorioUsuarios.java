package br.com.doacao.view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import br.com.doacao.dao.BeneficiarioDAO;
import br.com.doacao.dao.DoadorDAO;
import br.com.doacao.model.Beneficiario;
import br.com.doacao.model.Doador;

public class TelaRelatorioUsuarios extends JFrame {

  
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    private JTable tabelaDoadores;
    private JTable tabelaBeneficiarios;
    private JTextArea txtComentarios; 

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception e) {}
        EventQueue.invokeLater(() -> {
            try { new TelaRelatorioUsuarios().setVisible(true); } catch (Exception e) {}
        });
    }

    public TelaRelatorioUsuarios() {
        setTitle("Relatório de Avaliações");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 700, 600);
        contentPane = new JPanel();
        contentPane.setBackground(Color.WHITE);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblTitulo = new JLabel("Histórico de Avaliações");
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setBounds(10, 10, 664, 30);
        contentPane.add(lblTitulo);

        // --- SISTEMA DE ABAS ---
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBounds(10, 50, 664, 250);
        contentPane.add(tabbedPane);

        // ABA 1: DOADORES
        JPanel panelDoador = new JPanel();
        panelDoador.setLayout(null);
        JScrollPane scrollDoador = new JScrollPane();
        scrollDoador.setBounds(0, 0, 659, 222);
        panelDoador.add(scrollDoador);
        
        tabelaDoadores = new JTable();
        tabelaDoadores.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                carregarDetalhesDoador();
            }
        });
        scrollDoador.setViewportView(tabelaDoadores);
        tabbedPane.addTab("Doadores", null, panelDoador, null);

        // ABA 2: BENEFICIÁRIOS
        JPanel panelBen = new JPanel();
        panelBen.setLayout(null);
        JScrollPane scrollBen = new JScrollPane();
        scrollBen.setBounds(0, 0, 659, 222);
        panelBen.add(scrollBen);
        
        tabelaBeneficiarios = new JTable();
        tabelaBeneficiarios.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                carregarDetalhesBeneficiario();
            }
        });
        scrollBen.setViewportView(tabelaBeneficiarios);
        tabbedPane.addTab("Beneficiários", null, panelBen, null);

        // --- ÁREA DE DETALHES (EMBAIXO) ---
        JPanel panelDetalhes = new JPanel();
        panelDetalhes.setBorder(new TitledBorder(null, "Comentários Recebidos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panelDetalhes.setBackground(Color.WHITE);
        panelDetalhes.setBounds(10, 310, 664, 240);
        contentPane.add(panelDetalhes);
        panelDetalhes.setLayout(null);

        JScrollPane scrollTxt = new JScrollPane();
        scrollTxt.setBounds(10, 20, 644, 210);
        panelDetalhes.add(scrollTxt);

        txtComentarios = new JTextArea();
        txtComentarios.setEditable(false);
        txtComentarios.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        scrollTxt.setViewportView(txtComentarios);

        // Carregar dados iniciais
        listarDoadores();
        listarBeneficiarios();
    }

    // Preenche a tabela de Doadores (SEM A NOTA)
    private void listarDoadores() {
        DoadorDAO dao = new DoadorDAO();
        List<Doador> lista = dao.listarDoadores(); 
        
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nome");
        // Removi a coluna de Nota Média daqui

        for (Doador d : lista) {
            modelo.addRow(new Object[] {
                d.getIdDoador(),
                d.getNome()
                // Removi o valor da nota daqui
            });
        }
        tabelaDoadores.setModel(modelo);
    }

    // Preenche a tabela de Beneficiários (SEM A NOTA)
    private void listarBeneficiarios() {
        BeneficiarioDAO dao = new BeneficiarioDAO();
        List<Beneficiario> lista = dao.listarTodos();
        
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nome");
        // Removi a coluna de Nota Média daqui

        for (Beneficiario b : lista) {
            modelo.addRow(new Object[] {
                b.getIdBeneficiario(),
                b.getNome()
                // Removi o valor da nota daqui
            });
        }
        tabelaBeneficiarios.setModel(modelo);
    }

    private void carregarDetalhesDoador() {
        int linha = tabelaDoadores.getSelectedRow();
        if (linha != -1) {
            int id = (int) tabelaDoadores.getValueAt(linha, 0);
            String nome = (String) tabelaDoadores.getValueAt(linha, 1);
            
            DoadorDAO dao = new DoadorDAO();
            List<String> comentarios = dao.listarComentarios(id);
            
            StringBuilder sb = new StringBuilder();
            sb.append("O que dizem sobre: ").append(nome).append("\n\n");
            
            if(comentarios.isEmpty()) {
                sb.append("(Nenhuma avaliação recebida ainda)");
            } else {
                for(String c : comentarios) {
                    sb.append(c).append("\n-----------------------\n");
                }
            }
            txtComentarios.setText(sb.toString());
        }
    }

    private void carregarDetalhesBeneficiario() {
        int linha = tabelaBeneficiarios.getSelectedRow();
        if (linha != -1) {
            int id = (int) tabelaBeneficiarios.getValueAt(linha, 0);
            String nome = (String) tabelaBeneficiarios.getValueAt(linha, 1);
            
            BeneficiarioDAO dao = new BeneficiarioDAO();
            List<String> comentarios = dao.listarComentarios(id);
            
            StringBuilder sb = new StringBuilder();
            sb.append("O que dizem sobre: ").append(nome).append("\n\n");
            
            if(comentarios.isEmpty()) {
                sb.append("(Nenhuma avaliação recebida ainda)");
            } else {
                for(String c : comentarios) {
                    sb.append(c).append("\n-----------------------\n");
                }
            }
            txtComentarios.setText(sb.toString());
        }
    }
}