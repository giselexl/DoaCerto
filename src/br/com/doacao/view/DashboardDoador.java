package br.com.doacao.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import br.com.doacao.dao.AnuncioDAO;
import br.com.doacao.dao.DoadorDAO;
import br.com.doacao.dao.DoacaoDAO;
import br.com.doacao.dao.ItemDAO;
import br.com.doacao.model.Anuncio;
import br.com.doacao.model.Doador;
import br.com.doacao.model.Doacao;
import br.com.doacao.model.Item;

public class DashboardDoador extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    private JTabbedPane tabbedPane;

    // Componentes Aba 1 (Itens)
    private JTextField txtNomeItem, txtDescItem, txtCatItem, txtEstadoItem;
    private JComboBox<Doador> cbDoadorOwner;
    private JTable tabelaItens;

    // Componentes Aba 2 (Anúncios)
    private JTextField txtTituloAnuncio, txtLocalAnuncio, txtDescAnuncio;
    private JComboBox<String> cbFrete;
    private JComboBox<Item> cbItensDisponiveis;
    private JList<Item> listaVisualItens;
    private DefaultListModel<Item> modeloListaItens;
    private List<Item> itensParaAnunciar = new ArrayList<>();
    private JTable tabelaAnuncios;

    // Componentes Aba 3 (Avaliação)
    private JTable tabelaAvaliacao;
    private JComboBox<String> cbNotaAvaliacao;
    private JTextArea txtComentarioAvaliacao;

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception e) {}
        EventQueue.invokeLater(() -> {
            try { new DashboardDoador().setVisible(true); } catch (Exception e) {}
        });
    }

    public DashboardDoador() {
        setTitle("Painel do Doador");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 900, 650);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        // Título Verde
        JLabel lblTitulo = new JLabel("  Minha Área de Doações");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(46, 204, 113));
        contentPane.add(lblTitulo, BorderLayout.NORTH);

        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        contentPane.add(tabbedPane, BorderLayout.CENTER);

        // --- ABA 1: MEUS ITENS ---
        tabbedPane.addTab("1. Meus Itens", null, criarPainelItens(), "Cadastrar e Listar Itens");

        // --- ABA 2: MEUS ANÚNCIOS ---
        tabbedPane.addTab("2. Criar Anúncios", null, criarPainelAnuncios(), "Publicar Doações");

        // --- ABA 3: AVALIAR BENEFICIÁRIOS ---
        tabbedPane.addTab("3. Avaliações Pendentes", null, criarPainelAvaliacao(), "Avaliar quem recebeu");

        // Botão Sair
        JButton btnSair = new JButton("Sair / Voltar");
        btnSair.addActionListener(e -> {
            dispose();
            new TelaInicial().setVisible(true);
        });
        contentPane.add(btnSair, BorderLayout.SOUTH);
    }

    // =================================================================================
    // ABA 1: GERENCIAR ITENS
    // =================================================================================
    private JPanel criarPainelItens() {
        JPanel panel = new JPanel(null);

        // Formulário
        JLabel lblDono = new JLabel("Quem é você?");
        lblDono.setBounds(20, 10, 100, 20);
        panel.add(lblDono);

        cbDoadorOwner = new JComboBox<>(); // Carregar doadores aqui
        cbDoadorOwner.setBounds(20, 30, 200, 25);
        panel.add(cbDoadorOwner);

        JLabel lblNome = new JLabel("Nome do Item:");
        lblNome.setBounds(240, 10, 100, 20);
        panel.add(lblNome);
        txtNomeItem = new JTextField();
        txtNomeItem.setBounds(240, 30, 200, 25);
        panel.add(txtNomeItem);

        JLabel lblCat = new JLabel("Categoria:");
        lblCat.setBounds(460, 10, 80, 20);
        panel.add(lblCat);
        txtCatItem = new JTextField();
        txtCatItem.setBounds(460, 30, 150, 25);
        panel.add(txtCatItem);

        JLabel lblEstado = new JLabel("Estado:");
        lblEstado.setBounds(630, 10, 80, 20);
        panel.add(lblEstado);
        txtEstadoItem = new JTextField();
        txtEstadoItem.setBounds(630, 30, 150, 25);
        panel.add(txtEstadoItem);
        
        JLabel lblDesc = new JLabel("Descrição:");
        lblDesc.setBounds(20, 60, 100, 20);
        panel.add(lblDesc);
        txtDescItem = new JTextField();
        txtDescItem.setBounds(20, 80, 600, 25);
        panel.add(txtDescItem);

        JButton btnSalvar = new JButton("Salvar Item");
        btnSalvar.setBackground(new Color(46, 204, 113));
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.setBounds(640, 80, 140, 25);
        btnSalvar.addActionListener(e -> salvarItem());
        panel.add(btnSalvar);

        // Tabela
        JScrollPane scroll = new JScrollPane();
        scroll.setBounds(20, 120, 840, 380);
        panel.add(scroll);
        tabelaItens = new JTable();
        scroll.setViewportView(tabelaItens);

        // Carregar dados iniciais
        carregarComboDoador();
        atualizarTabelaItens();

        return panel;
    }

    // =================================================================================
    // ABA 2: CRIAR ANÚNCIOS
    // =================================================================================
    private JPanel criarPainelAnuncios() {
        JPanel panel = new JPanel(null);

        // Lado Esquerdo: Dados do Anúncio
        JLabel lblTitulo = new JLabel("Título do Anúncio:");
        lblTitulo.setBounds(20, 20, 150, 20);
        panel.add(lblTitulo);
        txtTituloAnuncio = new JTextField();
        txtTituloAnuncio.setBounds(20, 40, 250, 25);
        panel.add(txtTituloAnuncio);

        JLabel lblLocal = new JLabel("Localização:");
        lblLocal.setBounds(20, 70, 150, 20);
        panel.add(lblLocal);
        txtLocalAnuncio = new JTextField();
        txtLocalAnuncio.setBounds(20, 90, 250, 25);
        panel.add(txtLocalAnuncio);
        
        JLabel lblDesc = new JLabel("Descrição Geral:");
        lblDesc.setBounds(20, 120, 150, 20);
        panel.add(lblDesc);
        txtDescAnuncio = new JTextField();
        txtDescAnuncio.setBounds(20, 140, 250, 25);
        panel.add(txtDescAnuncio);

        JLabel lblFrete = new JLabel("Frete:");
        lblFrete.setBounds(20, 170, 100, 20);
        panel.add(lblFrete);
        cbFrete = new JComboBox<>(new String[]{"Retirada", "Entrega", "Correios"});
        cbFrete.setBounds(20, 190, 250, 25);
        panel.add(cbFrete);

        // Lado Direito: Seleção de Itens
        JLabel lblSel = new JLabel("Adicionar Itens ao Anúncio:");
        lblSel.setBounds(300, 20, 200, 20);
        panel.add(lblSel);
        
        cbItensDisponiveis = new JComboBox<>();
        cbItensDisponiveis.setBounds(300, 40, 200, 25);
        panel.add(cbItensDisponiveis);
        
        JButton btnAdd = new JButton("+");
        btnAdd.addActionListener(e -> adicionarItemNaLista());
        btnAdd.setBounds(510, 40, 50, 25);
        panel.add(btnAdd);
        
        modeloListaItens = new DefaultListModel<>();
        listaVisualItens = new JList<>(modeloListaItens);
        listaVisualItens.setBorder(new LineBorder(Color.LIGHT_GRAY));
        JScrollPane scrollLista = new JScrollPane(listaVisualItens);
        scrollLista.setBounds(300, 70, 260, 145);
        panel.add(scrollLista);

        // Botão Publicar
        JButton btnPublicar = new JButton("PUBLICAR ANÚNCIO");
        btnPublicar.setBackground(new Color(0, 102, 204));
        btnPublicar.setForeground(Color.WHITE);
        btnPublicar.setBounds(300, 220, 260, 40);
        btnPublicar.addActionListener(e -> publicarAnuncio());
        panel.add(btnPublicar);
        
        // Tabela de Anúncios Existentes (Abaixo)
        JLabel lblMeusAnuncios = new JLabel("Anúncios Publicados:");
        lblMeusAnuncios.setBounds(20, 270, 200, 20);
        panel.add(lblMeusAnuncios);
        
        JScrollPane scrollTable = new JScrollPane();
        scrollTable.setBounds(20, 290, 840, 200);
        panel.add(scrollTable);
        tabelaAnuncios = new JTable();
        scrollTable.setViewportView(tabelaAnuncios);

        // Carregar dados
        carregarComboItensDisponiveis();
        atualizarTabelaAnuncios();

        return panel;
    }

    // =================================================================================
    // ABA 3: AVALIAR BENEFICIÁRIOS
    // =================================================================================
    private JPanel criarPainelAvaliacao() {
        JPanel panel = new JPanel(null);

        JLabel lblInfo = new JLabel("Selecione uma doação CONCLUÍDA (Aprovada) para avaliar o Beneficiário:");
        lblInfo.setBounds(20, 20, 600, 20);
        panel.add(lblInfo);

        JScrollPane scroll = new JScrollPane();
        scroll.setBounds(20, 50, 840, 200);
        panel.add(scroll);
        tabelaAvaliacao = new JTable();
        scroll.setViewportView(tabelaAvaliacao);

        // Área de inputs
        JLabel lblNota = new JLabel("Nota (1-5):");
        lblNota.setBounds(20, 270, 100, 20);
        panel.add(lblNota);
        cbNotaAvaliacao = new JComboBox<>(new String[]{"5 - Excelente", "4 - Bom", "3 - Regular", "2 - Ruim", "1 - Péssimo"});
        cbNotaAvaliacao.setBounds(20, 290, 150, 30);
        panel.add(cbNotaAvaliacao);

        JLabel lblCom = new JLabel("Comentário:");
        lblCom.setBounds(200, 270, 100, 20);
        panel.add(lblCom);
        txtComentarioAvaliacao = new JTextArea();
        txtComentarioAvaliacao.setBorder(new LineBorder(Color.LIGHT_GRAY));
        txtComentarioAvaliacao.setBounds(200, 290, 400, 60);
        panel.add(txtComentarioAvaliacao);

        JButton btnAvaliar = new JButton("Enviar Avaliação");
        btnAvaliar.setBackground(new Color(255, 215, 0));
        btnAvaliar.setBounds(620, 290, 150, 60);
        btnAvaliar.addActionListener(e -> enviarAvaliacao());
        panel.add(btnAvaliar);

        atualizarTabelaAvaliacao();
        return panel;
    }

    // =================================================================================
    // MÉTODOS DE LÓGICA (CONTROLADORES)
    // =================================================================================

    // ABA 1
    private void carregarComboDoador() {
        List<Doador> lista = new DoadorDAO().listarDoadores();
        for (Doador d : lista) cbDoadorOwner.addItem(d);
    }

    private void salvarItem() {
        try {
            Item item = new Item();
            item.setNomeItem(txtNomeItem.getText());
            item.setDescricao(txtDescItem.getText());
            item.setCategoria(txtCatItem.getText());
            item.setEstadoConservacao(txtEstadoItem.getText());
            item.setDoador((Doador) cbDoadorOwner.getSelectedItem());
            
            new ItemDAO().cadastrarItem(item);
            
            txtNomeItem.setText(""); txtDescItem.setText("");
            atualizarTabelaItens();
            carregarComboItensDisponiveis(); // Atualiza aba 2
            JOptionPane.showMessageDialog(null, "Item salvo!");
        } catch(Exception e) { e.printStackTrace(); }
    }

    private void atualizarTabelaItens() {
        List<Item> lista = new ItemDAO().listarItens();
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID"); model.addColumn("Nome"); model.addColumn("Categoria"); model.addColumn("Doador");
        for (Item i : lista) model.addRow(new Object[]{i.getIdItem(), i.getNomeItem(), i.getCategoria(), i.getDoador().getNome()});
        tabelaItens.setModel(model);
    }

    // ABA 2
    private void carregarComboItensDisponiveis() {
        cbItensDisponiveis.removeAllItems();
        List<Item> lista = new ItemDAO().listarItensDisponiveis();
        for (Item i : lista) cbItensDisponiveis.addItem(i);
    }

    private void adicionarItemNaLista() {
        Item i = (Item) cbItensDisponiveis.getSelectedItem();
        if (i != null) {
            itensParaAnunciar.add(i);
            modeloListaItens.addElement(i);
            cbItensDisponiveis.removeItem(i);
        }
    }

    private void publicarAnuncio() {
        if(itensParaAnunciar.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Adicione itens!"); return;
        }
        Anuncio a = new Anuncio();
        a.setTitulo(txtTituloAnuncio.getText());
        a.setDescricao(txtDescAnuncio.getText());
        a.setLocalizacao(txtLocalAnuncio.getText());
        a.setOpcoesFrete((String) cbFrete.getSelectedItem());
        a.setItens(itensParaAnunciar);
        
        new AnuncioDAO().cadastrarAnuncio(a);
        
        itensParaAnunciar.clear(); modeloListaItens.clear();
        txtTituloAnuncio.setText(""); txtDescAnuncio.setText("");
        atualizarTabelaAnuncios();
        JOptionPane.showMessageDialog(null, "Anúncio publicado!");
    }
    
    private void atualizarTabelaAnuncios() {
        List<Anuncio> lista = new AnuncioDAO().listarAnuncios();
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID"); model.addColumn("Título"); model.addColumn("Local");
        for (Anuncio a : lista) model.addRow(new Object[]{a.getIdAnuncio(), a.getTitulo(), a.getLocalizacao()});
        tabelaAnuncios.setModel(model);
    }

    // ABA 3
    private void atualizarTabelaAvaliacao() {
        List<Doacao> lista = new DoacaoDAO().listarDoacoes();
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID"); model.addColumn("Item"); model.addColumn("Beneficiário"); model.addColumn("Status");
        
        for (Doacao d : lista) {
            if ("Aprovada".equalsIgnoreCase(d.getStatusAtual())) {
                model.addRow(new Object[]{d.getIdDoacao(), d.getItem().getNomeItem(), d.getBeneficiario().getNome(), d.getStatusAtual()});
            }
        }
        tabelaAvaliacao.setModel(model);
    }

    private void enviarAvaliacao() {
        int row = tabelaAvaliacao.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(null, "Selecione uma doação!"); return; }
        
        int id = (int) tabelaAvaliacao.getValueAt(row, 0);
        int nota = Integer.parseInt(((String)cbNotaAvaliacao.getSelectedItem()).substring(0, 1));
        
        new DoacaoDAO().avaliarBeneficiario(id, nota, txtComentarioAvaliacao.getText());
        txtComentarioAvaliacao.setText("");
    }
}