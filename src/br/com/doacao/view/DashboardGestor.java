package br.com.doacao.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import br.com.doacao.dao.AnuncioDAO;
import br.com.doacao.dao.BeneficiarioDAO; // Import Novo
import br.com.doacao.dao.DoadorDAO;
import br.com.doacao.dao.DoacaoDAO;
import br.com.doacao.model.Anuncio;
import br.com.doacao.model.Beneficiario; // Import Novo
import br.com.doacao.model.Doador;
import br.com.doacao.model.Doacao;

public class DashboardGestor extends JFrame {


	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    private JTabbedPane tabbedPane;

    // Aba 1: Doador
    private JTextField txtNomeDoador, txtEmailDoador;
    private JTable tabelaDoadores;

    // Aba 2: Beneficiário (NOVO)
    private JTextField txtNomeBen, txtEmailBen;
    private JTable tabelaBeneficiarios;

    // Aba 3: Análise
    private JTable tabelaAnalise;
    
    // Aba 4: Anúncios
    private JTable tabelaAnuncios;

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception e) {}
        EventQueue.invokeLater(() -> {
            try { new DashboardGestor().setVisible(true); } catch (Exception e) {}
        });
    }

    public DashboardGestor() {
        setTitle("Painel de Controle do Gestor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 900, 650);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        // Título
        JLabel lblTitulo = new JLabel("  Administração DoaCerto");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(0, 102, 204));
        contentPane.add(lblTitulo, BorderLayout.NORTH);

        // Abas
        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        contentPane.add(tabbedPane, BorderLayout.CENTER);

        // --- ABA 1: DOADORES ---
        tabbedPane.addTab("Doadores", null, criarPainelDoador(), "Cadastrar Doadores");

        // --- ABA 2: BENEFICIÁRIOS (NOVA) ---
        tabbedPane.addTab("Beneficiários", null, criarPainelBeneficiario(), "Cadastrar Beneficiários");

        // --- ABA 3: ANÁLISE ---
        tabbedPane.addTab("Análise de Pedidos", null, criarPainelAnalise(), "Aprovar Doações");
        
        // --- ABA 4: ANÚNCIOS ---
        tabbedPane.addTab("Mural de Anúncios", null, criarPainelAnuncios(), "Ver Mural");
        
        // Botão Sair
        JButton btnSair = new JButton("Sair do Sistema");
        btnSair.addActionListener(e -> {
            dispose();
            new TelaInicial().setVisible(true);
        });
        contentPane.add(btnSair, BorderLayout.SOUTH);
    }

    // =========================================================================
    // PAINEL 1: DOADORES
    // =========================================================================
    private JPanel criarPainelDoador() {
        JPanel panel = new JPanel(null);

        JLabel lblNome = new JLabel("Nome:"); lblNome.setBounds(20, 20, 100, 20); panel.add(lblNome);
        txtNomeDoador = new JTextField(); txtNomeDoador.setBounds(70, 20, 200, 25); panel.add(txtNomeDoador);

        JLabel lblEmail = new JLabel("Email:"); lblEmail.setBounds(290, 20, 100, 20); panel.add(lblEmail);
        txtEmailDoador = new JTextField(); txtEmailDoador.setBounds(340, 20, 200, 25); panel.add(txtEmailDoador);

        JButton btnSalvar = new JButton("Cadastrar Doador");
        btnSalvar.setBackground(new Color(46, 204, 113));
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.setBounds(560, 20, 150, 25);
        btnSalvar.addActionListener(e -> {
            Doador d = new Doador();
            d.setNome(txtNomeDoador.getText());
            d.setEmail(txtEmailDoador.getText());
            new DoadorDAO().cadastrarDoador(d);
            atualizarTabelaDoador();
            JOptionPane.showMessageDialog(null, "Doador Salvo!");
            txtNomeDoador.setText(""); txtEmailDoador.setText("");
        });
        panel.add(btnSalvar);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(20, 70, 840, 400);
        panel.add(scrollPane);
        tabelaDoadores = new JTable();
        scrollPane.setViewportView(tabelaDoadores);
        
        atualizarTabelaDoador();
        return panel;
    }

    // =========================================================================
    // PAINEL 2: BENEFICIÁRIOS (NOVO)
    // =========================================================================
    private JPanel criarPainelBeneficiario() {
        JPanel panel = new JPanel(null);

        JLabel lblNome = new JLabel("Nome:"); lblNome.setBounds(20, 20, 100, 20); panel.add(lblNome);
        txtNomeBen = new JTextField(); txtNomeBen.setBounds(70, 20, 200, 25); panel.add(txtNomeBen);

        JLabel lblEmail = new JLabel("Email:"); lblEmail.setBounds(290, 20, 100, 20); panel.add(lblEmail);
        txtEmailBen = new JTextField(); txtEmailBen.setBounds(340, 20, 200, 25); panel.add(txtEmailBen);

        JButton btnSalvar = new JButton("Cadastrar Beneficiário");
        btnSalvar.setBackground(new Color(230, 126, 34)); // Laranja
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.setBounds(560, 20, 180, 25);
        btnSalvar.addActionListener(e -> {
            Beneficiario b = new Beneficiario();
            b.setNome(txtNomeBen.getText());
            b.setEmail(txtEmailBen.getText());
            new BeneficiarioDAO().cadastrar(b);
            atualizarTabelaBeneficiario();
            JOptionPane.showMessageDialog(null, "Beneficiário Salvo!");
            txtNomeBen.setText(""); txtEmailBen.setText("");
        });
        panel.add(btnSalvar);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(20, 70, 840, 400);
        panel.add(scrollPane);
        tabelaBeneficiarios = new JTable();
        scrollPane.setViewportView(tabelaBeneficiarios);
        
        atualizarTabelaBeneficiario();
        return panel;
    }

    // =========================================================================
    // PAINEL 3: ANÁLISE
    // =========================================================================
    private JPanel criarPainelAnalise() {
        JPanel panel = new JPanel(new BorderLayout());
        tabelaAnalise = new JTable();
        panel.add(new JScrollPane(tabelaAnalise), BorderLayout.CENTER);

        JPanel panelBotoes = new JPanel();
        JButton btnAprovar = new JButton("Aprovar");
        btnAprovar.setBackground(new Color(0, 153, 76)); btnAprovar.setForeground(Color.WHITE);
        
        JButton btnRejeitar = new JButton("Rejeitar");
        btnRejeitar.setBackground(new Color(204, 0, 0)); btnRejeitar.setForeground(Color.WHITE);
        
        JButton btnAtualizar = new JButton("Atualizar");

        panelBotoes.add(btnAprovar); panelBotoes.add(btnRejeitar); panelBotoes.add(btnAtualizar);
        panel.add(panelBotoes, BorderLayout.SOUTH);
        
        DoacaoDAO dao = new DoacaoDAO();
        Runnable carregar = () -> {
            List<Doacao> lista = dao.listarDoacoesEmAnalise();
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("ID"); model.addColumn("Item"); model.addColumn("Beneficiário");
            for(Doacao d : lista) model.addRow(new Object[]{d.getIdDoacao(), d.getItem().getNomeItem(), d.getBeneficiario().getNome()});
            tabelaAnalise.setModel(model);
        };
        
        btnAtualizar.addActionListener(e -> carregar.run());
        btnAprovar.addActionListener(e -> {
            int row = tabelaAnalise.getSelectedRow();
            if(row > -1) {
                int id = (int) tabelaAnalise.getValueAt(row, 0);
                dao.aprovarDoacao(id, new java.sql.Timestamp(System.currentTimeMillis()));
                carregar.run();
            }
        });
        btnRejeitar.addActionListener(e -> {
            int row = tabelaAnalise.getSelectedRow();
            if(row > -1) {
                int id = (int) tabelaAnalise.getValueAt(row, 0);
                dao.rejeitarDoacao(id, JOptionPane.showInputDialog("Motivo:"));
                carregar.run();
            }
        });
        carregar.run();
        return panel;
    }
    
    // =========================================================================
    // PAINEL 4: ANÚNCIOS
    // =========================================================================
    private JPanel criarPainelAnuncios() {
        JPanel panel = new JPanel(new BorderLayout());
        tabelaAnuncios = new JTable();
        panel.add(new JScrollPane(tabelaAnuncios), BorderLayout.CENTER);
        
        JPanel panelSul = new JPanel();
        JButton btnAt = new JButton("Recarregar");
        btnAt.addActionListener(e -> atualizarTabelaAnuncios());
        panelSul.add(btnAt);
        panel.add(panelSul, BorderLayout.SOUTH);
        
        atualizarTabelaAnuncios();
        return panel;
    }

    // --- LISTAGENS ---
    private void atualizarTabelaDoador() {
        List<Doador> lista = new DoadorDAO().listarDoadores();
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID"); model.addColumn("Nome"); model.addColumn("Email");
        for (Doador d : lista) model.addRow(new Object[] { d.getIdDoador(), d.getNome(), d.getEmail() });
        tabelaDoadores.setModel(model);
    }
    
    private void atualizarTabelaBeneficiario() {
        List<Beneficiario> lista = new BeneficiarioDAO().listarTodos();
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID"); model.addColumn("Nome"); model.addColumn("Email");
        for (Beneficiario b : lista) model.addRow(new Object[] { b.getIdBeneficiario(), b.getNome(), b.getEmail() });
        tabelaBeneficiarios.setModel(model);
    }
    
    private void atualizarTabelaAnuncios() {
        List<Anuncio> lista = new AnuncioDAO().listarAnuncios();
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID"); model.addColumn("Título"); model.addColumn("Local");
        for(Anuncio a : lista) model.addRow(new Object[] { a.getIdAnuncio(), a.getTitulo(), a.getLocalizacao() });
        tabelaAnuncios.setModel(model);
    }
}