package br.com.doacao.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import br.com.doacao.dao.BeneficiarioDAO;
import br.com.doacao.dao.DoacaoDAO;
import br.com.doacao.dao.ItemDAO;
import br.com.doacao.model.Beneficiario;
import br.com.doacao.model.Doacao;
import br.com.doacao.model.DoacaoEmAnalise;
import br.com.doacao.model.Item;

public class DashboardBeneficiario extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    private JTabbedPane tabbedPane;

    // Componentes Gerais
    private JComboBox<Beneficiario> cbQuemSouEu; // Simula o login

    // Aba 1: Mural
    private JTable tabelaMural;

    // Aba 2: Avaliação
    private JTable tabelaMinhasDoacoes;
    private JComboBox<String> cbNota;
    private JTextArea txtComentario;

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception e) {}
        EventQueue.invokeLater(() -> {
            try { new DashboardBeneficiario().setVisible(true); } catch (Exception e) {}
        });
    }

    public DashboardBeneficiario() {
        setTitle("Painel do Beneficiário");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 850, 600);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        // Topo Laranja
        JPanel panelTop = new JPanel(new BorderLayout());
        panelTop.setBackground(new Color(230, 126, 34));
        
        JLabel lblTitulo = new JLabel("  Área de Solicitação de Doações  ");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        panelTop.add(lblTitulo, BorderLayout.WEST);
        
        // Seletor de Usuário (Simulação de Login)
        JPanel panelUser = new JPanel();
        panelUser.setOpaque(false);
        JLabel lblSou = new JLabel("Eu sou: ");
        lblSou.setForeground(Color.WHITE);
        lblSou.setFont(new Font("Segoe UI", Font.BOLD, 14));
        cbQuemSouEu = new JComboBox<>();
        panelUser.add(lblSou);
        panelUser.add(cbQuemSouEu);
        panelTop.add(panelUser, BorderLayout.EAST);
        
        contentPane.add(panelTop, BorderLayout.NORTH);

        // Abas
        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        contentPane.add(tabbedPane, BorderLayout.CENTER);

        // --- ABA 1: MURAL ---
        tabbedPane.addTab("1. Mural de Itens", null, criarPainelMural(), "Ver itens disponíveis");

        // --- ABA 2: AVALIAÇÃO ---
        tabbedPane.addTab("2. Avaliar Doadores", null, criarPainelAvaliacao(), "Avaliar doações recebidas");

        // Botão Sair
        JButton btnSair = new JButton("Sair / Voltar");
        btnSair.addActionListener(e -> {
            dispose();
            new TelaInicial().setVisible(true);
        });
        contentPane.add(btnSair, BorderLayout.SOUTH);
        
        carregarBeneficiarios(); // Carrega o combo do topo
    }

    // =================================================================================
    // ABA 1: MURAL DE ITENS (SOLICITAR)
    // =================================================================================
    private JPanel criarPainelMural() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel lblDica = new JLabel("  Selecione um item abaixo e clique em Solicitar:");
        lblDica.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        panel.add(lblDica, BorderLayout.NORTH);

        tabelaMural = new JTable();
        JScrollPane scroll = new JScrollPane(tabelaMural);
        panel.add(scroll, BorderLayout.CENTER);

        JPanel panelBotao = new JPanel();
        JButton btnSolicitar = new JButton("SOLICITAR DOAÇÃO");
        btnSolicitar.setBackground(new Color(230, 126, 34));
        btnSolicitar.setForeground(Color.WHITE);
        btnSolicitar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        JButton btnAtualizar = new JButton("Atualizar Lista");
        
        btnSolicitar.addActionListener(e -> solicitarDoacao());
        btnAtualizar.addActionListener(e -> atualizarMural());
        
        panelBotao.add(btnAtualizar);
        panelBotao.add(btnSolicitar);
        panel.add(panelBotao, BorderLayout.SOUTH);

        atualizarMural();
        return panel;
    }

    // =================================================================================
    // ABA 2: AVALIAR DOADOR
    // =================================================================================
    private JPanel criarPainelAvaliacao() {
        JPanel panel = new JPanel(null);

        JLabel lblInfo = new JLabel("Selecione uma doação que você RECEBEU (Aprovada) para avaliar o Doador:");
        lblInfo.setBounds(20, 20, 600, 20);
        panel.add(lblInfo);

        JScrollPane scroll = new JScrollPane();
        scroll.setBounds(20, 50, 790, 200);
        panel.add(scroll);
        tabelaMinhasDoacoes = new JTable();
        scroll.setViewportView(tabelaMinhasDoacoes);

        JLabel lblNota = new JLabel("Nota (1-5):");
        lblNota.setBounds(20, 270, 100, 20);
        panel.add(lblNota);
        cbNota = new JComboBox<>(new String[]{"5 - Excelente", "4 - Muito Bom", "3 - Bom", "2 - Ruim", "1 - Péssimo"});
        cbNota.setBounds(20, 290, 150, 30);
        panel.add(cbNota);

        JLabel lblCom = new JLabel("Comentário sobre o Doador:");
        lblCom.setBounds(200, 270, 200, 20);
        panel.add(lblCom);
        txtComentario = new JTextArea();
        txtComentario.setBorder(new LineBorder(Color.LIGHT_GRAY));
        txtComentario.setBounds(200, 290, 400, 60);
        panel.add(txtComentario);

        JButton btnEnviar = new JButton("Enviar Avaliação");
        btnEnviar.setBackground(new Color(255, 215, 0)); // Dourado
        btnEnviar.setBounds(620, 290, 150, 60);
        btnEnviar.addActionListener(e -> enviarAvaliacao());
        panel.add(btnEnviar);
        
        JButton btnRecarregar = new JButton("Recarregar Minhas Doações");
        btnRecarregar.setBounds(20, 360, 200, 30);
        btnRecarregar.addActionListener(e -> atualizarMinhasDoacoes());
        panel.add(btnRecarregar);

        atualizarMinhasDoacoes();
        return panel;
    }

    // =================================================================================
    // MÉTODOS DE LÓGICA (CONTROLLERS)
    // =================================================================================

    private void carregarBeneficiarios() {
        List<Beneficiario> lista = new BeneficiarioDAO().listarTodos();
        for (Beneficiario b : lista) cbQuemSouEu.addItem(b);
    }

    // Listar todos os itens (idealmente, só os que tem Anúncio e NÃO tem Doação)
    // Como simplificação, listamos todos os Itens e filtramos visualmente
    private void atualizarMural() {
        List<Item> lista = new ItemDAO().listarItens(); // Traz itens com Doador
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID Item");
        model.addColumn("O que é");
        model.addColumn("Categoria");
        model.addColumn("Estado");
        model.addColumn("Doador");

        for (Item i : lista) {
            // Filtrar: Mostra item se NÃO foi doado ainda (idDoacao é um conceito de banco, aqui checamos lógica)
            // Como o objeto Item Java simples não tem o campo idDoacao mapeado direto (tem o objeto),
            // assumimos que listarItens traz tudo.
            // Para produção, o DAO deveria ter um método listarItensDisponiveisNoMural()
            
            model.addRow(new Object[]{
                i.getIdItem(),
                i.getNomeItem(),
                i.getCategoria(),
                i.getEstadoConservacao(),
                i.getDoador().getNome()
            });
        }
        tabelaMural.setModel(model);
    }

    private void solicitarDoacao() {
        int row = tabelaMural.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(null, "Selecione um item no mural!"); return; }

        // Cria o objeto Item apenas com o ID (suficiente pro DAO)
        int idItem = (int) tabelaMural.getValueAt(row, 0);
        Item itemSelecionado = new Item();
        itemSelecionado.setIdItem(idItem);

        Beneficiario eu = (Beneficiario) cbQuemSouEu.getSelectedItem();
        if (eu == null) { JOptionPane.showMessageDialog(null, "Selecione quem é você lá em cima!"); return; }

        try {
            // Cria a doação com status inicial
            DoacaoEmAnalise novaDoacao = new DoacaoEmAnalise();
            novaDoacao.setBeneficiario(eu);
            novaDoacao.setItem(itemSelecionado);

            new DoacaoDAO().realizarDoacao(novaDoacao);
            
            // Remove o item da lista visualmente ou recarrega
            atualizarMural();
            atualizarMinhasDoacoes(); // Já aparece na outra aba
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
        }
    }

    private void atualizarMinhasDoacoes() {
        // Lista todas as doações do sistema
        // Em um sistema real, filtraria pelo ID do usuário logado "cbQuemSouEu"
        List<Doacao> lista = new DoacaoDAO().listarDoacoes();
        
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID Doação");
        model.addColumn("Item");
        model.addColumn("Doador");
        model.addColumn("Status");
        model.addColumn("Minha Avaliação");

        Beneficiario eu = (Beneficiario) cbQuemSouEu.getSelectedItem();
        int meuId = (eu != null) ? eu.getIdBeneficiario() : -1;

        for (Doacao d : lista) {
            // Filtro visual: Só mostra as doações DESTE beneficiário
            if (d.getBeneficiario().getIdBeneficiario() == meuId) {
                // Filtro visual: Só mostra se estiver APROVADA (para poder avaliar)
                if ("Aprovada".equalsIgnoreCase(d.getStatusAtual())) {
                    model.addRow(new Object[]{
                        d.getIdDoacao(),
                        d.getItem().getNomeItem(),
                        d.getItem().getDoador().getNome(),
                        d.getStatusAtual(),
                        "Pendente" // Simplificação visual
                    });
                }
            }
        }
        tabelaMinhasDoacoes.setModel(model);
    }

    private void enviarAvaliacao() {
        int row = tabelaMinhasDoacoes.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(null, "Selecione uma doação!"); return; }

        int idDoacao = (int) tabelaMinhasDoacoes.getValueAt(row, 0);
        int nota = Integer.parseInt(((String)cbNota.getSelectedItem()).substring(0, 1));
        String comentario = txtComentario.getText();

        new DoacaoDAO().avaliarDoacao(idDoacao, nota, comentario);
        
        txtComentario.setText("");
        atualizarMinhasDoacoes();
    }
}