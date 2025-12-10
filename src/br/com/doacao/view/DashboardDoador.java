package br.com.doacao.view;

// aqui eu importo as ferramentas visuais do java (botões, cores, painéis)
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

// importo os componentes específicos da tela (tabelas, caixas de texto, abas)
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

// aqui eu chamo os meus (DAOs) e as minhas (Models)
import br.com.doacao.dao.AnuncioDAO;
import br.com.doacao.dao.DoadorDAO;
import br.com.doacao.dao.DoacaoDAO;
import br.com.doacao.dao.ItemDAO;
import br.com.doacao.model.Anuncio;
import br.com.doacao.model.Doador;
import br.com.doacao.model.Doacao;
import br.com.doacao.model.Item;

// essa classe é a tela principal do doador, ela herda de JFrame pq é uma janela
public class DashboardDoador extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane; // o painelzão que segura tudo
    private JTabbedPane tabbedPane; // o componente que cria as abas de navegação

    // --- VARIÁVEIS GLOBAIS (pra eu poder usar em qualquer método da classe) ---
    
    // Componentes da Aba 1 (Onde cadastro os itens)
    private JTextField txtNomeItem, txtDescItem, txtCatItem, txtEstadoItem;
    private JComboBox<Doador> cbDoadorOwner; // caixinha pra escolher quem é o dono (simulação de login)
    private JTable tabelaItens; // tabela pra mostrar o que já cadastrei

    // Componentes da Aba 2 (Onde crio anúncios)
    private JTextField txtTituloAnuncio, txtLocalAnuncio, txtDescAnuncio;
    private JComboBox<String> cbFrete; // opções de entrega
    private JComboBox<Item> cbItensDisponiveis; // lista de itens que tão "soltos" (sem anúncio)
    
    // essa parte é importante: listas pra controlar os itens que vou colocar no anúncio antes de salvar
    private JList<Item> listaVisualItens; // lista que aparece na tela pro usuário ver
    private DefaultListModel<Item> modeloListaItens; // o "motor" da lista visual
    private List<Item> itensParaAnunciar = new ArrayList<>(); // a lista real que o java vai mandar pro banco
    
    private JTable tabelaAnuncios; // tabela pra ver meus anúncios prontos

    // Componentes da Aba 3 (Onde avalio quem recebeu)
    private JTable tabelaAvaliacao;
    private JComboBox<String> cbNotaAvaliacao; // notas de 1 a 5
    private JTextArea txtComentarioAvaliacao; // textão pro feedback

    // --- O PONTO DE PARTIDA (MAIN) ---
    public static void main(String[] args) {
        try { 
            // truque pro java ficar com a cara do windows (botões bonitos)
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
        } catch (Exception e) {}
        
        // coloca a criação da tela na fila do processador pra não travar nada
        EventQueue.invokeLater(() -> {
            try { new DashboardDoador().setVisible(true); } catch (Exception e) {}
        });
    }

    // --- O CONSTRUTOR (ONDE A TELA NASCE) ---
    public DashboardDoador() {
        setTitle("Painel do Doador");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // mata o programa se fechar
        setBounds(100, 100, 900, 650); // tamanho da janela
        
        // configura o painel base
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0)); // layout norte, sul, centro...

        // Título Verde lá no topo pra saber que é área do doador
        JLabel lblTitulo = new JLabel("  Minha Área de Doações");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(46, 204, 113)); // verde
        contentPane.add(lblTitulo, BorderLayout.NORTH);

        // Cria o sistema de abas
        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        contentPane.add(tabbedPane, BorderLayout.CENTER);

        // --- AQUI EU MONTO AS 3 ABAS CHAMANDO OUTROS MÉTODOS ---
        // faço isso pra não deixar esse construtor gigante e bagunçado
        tabbedPane.addTab("1. Meus Itens", null, criarPainelItens(), "Cadastrar e Listar Itens");
        tabbedPane.addTab("2. Criar Anúncios", null, criarPainelAnuncios(), "Publicar Doações");
        tabbedPane.addTab("3. Avaliações Pendentes", null, criarPainelAvaliacao(), "Avaliar quem recebeu");

        // Botão de Sair lá embaixo
        JButton btnSair = new JButton("Sair / Voltar");
        btnSair.addActionListener(e -> {
            dispose(); // fecha essa janela
            new TelaInicial().setVisible(true); // volta pra tela de escolha
        });
        contentPane.add(btnSair, BorderLayout.SOUTH);
    }

    // =================================================================================
    // MÉTODOS DE DESENHO (VIEW) - AQUI EU MONTO O QUE O USUÁRIO VÊ
    // =================================================================================

    // desenha a aba de itens
    private JPanel criarPainelItens() {
        JPanel panel = new JPanel(null); // layout null pra eu posicionar com x,y

        // campo pra "fingir" login (escolher qual doador tá usando)
        JLabel lblDono = new JLabel("Quem é você?");
        lblDono.setBounds(20, 10, 100, 20);
        panel.add(lblDono);

        cbDoadorOwner = new JComboBox<>(); 
        cbDoadorOwner.setBounds(20, 30, 200, 25);
        panel.add(cbDoadorOwner);

        // campos de texto pro item (nome, categoria, estado...)
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

        // botão de salvar que chama a lógica
        JButton btnSalvar = new JButton("Salvar Item");
        btnSalvar.setBackground(new Color(46, 204, 113));
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.setBounds(640, 80, 140, 25);
        
        // lambda expression: quando clicar, executa salvarItem()
        btnSalvar.addActionListener(e -> salvarItem()); 
        panel.add(btnSalvar);

        // tabela com barra de rolagem (scroll)
        JScrollPane scroll = new JScrollPane();
        scroll.setBounds(20, 120, 840, 380);
        panel.add(scroll);
        tabelaItens = new JTable();
        scroll.setViewportView(tabelaItens);

        // carrega os dados do banco assim que abre a aba
        carregarComboDoador();
        atualizarTabelaItens();

        return panel;
    }

    // desenha a aba de anúncios
    private JPanel criarPainelAnuncios() {
        JPanel panel = new JPanel(null);

        // Lado Esquerdo: Form do Anúncio
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

        // Lado Direito: "Cesta de Compras" de itens pra por no anúncio
        JLabel lblSel = new JLabel("Adicionar Itens ao Anúncio:");
        lblSel.setBounds(300, 20, 200, 20);
        panel.add(lblSel);
        
        cbItensDisponiveis = new JComboBox<>();
        cbItensDisponiveis.setBounds(300, 40, 200, 25);
        panel.add(cbItensDisponiveis);
        
        JButton btnAdd = new JButton("+");
        btnAdd.addActionListener(e -> adicionarItemNaLista()); // joga o item na lista temporária
        btnAdd.setBounds(510, 40, 50, 25);
        panel.add(btnAdd);
        
        // lista visual pra mostrar o que eu já adicionei no "carrinho"
        modeloListaItens = new DefaultListModel<>();
        listaVisualItens = new JList<>(modeloListaItens);
        listaVisualItens.setBorder(new LineBorder(Color.LIGHT_GRAY));
        JScrollPane scrollLista = new JScrollPane(listaVisualItens);
        scrollLista.setBounds(300, 70, 260, 145);
        panel.add(scrollLista);

        // Botão pra gravar tudo
        JButton btnPublicar = new JButton("PUBLICAR ANÚNCIO");
        btnPublicar.setBackground(new Color(0, 102, 204));
        btnPublicar.setForeground(Color.WHITE);
        btnPublicar.setBounds(300, 220, 260, 40);
        btnPublicar.addActionListener(e -> publicarAnuncio());
        panel.add(btnPublicar);
        
        // Tabela histórica lá embaixo
        JLabel lblMeusAnuncios = new JLabel("Anúncios Publicados:");
        lblMeusAnuncios.setBounds(20, 270, 200, 20);
        panel.add(lblMeusAnuncios);
        
        JScrollPane scrollTable = new JScrollPane();
        scrollTable.setBounds(20, 290, 840, 200);
        panel.add(scrollTable);
        tabelaAnuncios = new JTable();
        scrollTable.setViewportView(tabelaAnuncios);

        // carrega listas iniciais
        carregarComboItensDisponiveis();
        atualizarTabelaAnuncios();

        return panel;
    }

    // desenha a aba de avaliação
    private JPanel criarPainelAvaliacao() {
        JPanel panel = new JPanel(null);

        JLabel lblInfo = new JLabel("Selecione uma doação CONCLUÍDA (Aprovada) para avaliar o Beneficiário:");
        lblInfo.setBounds(20, 20, 600, 20);
        panel.add(lblInfo);

        // tabela pra escolher quem avaliar
        JScrollPane scroll = new JScrollPane();
        scroll.setBounds(20, 50, 840, 200);
        panel.add(scroll);
        tabelaAvaliacao = new JTable();
        scroll.setViewportView(tabelaAvaliacao);

        // inputs da avaliação
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
    // MÉTODOS DE LÓGICA (CONTROLLERS) - AQUI O CÓDIGO PENSA E VAI NO BANCO
    // =================================================================================

    // --- LÓGICA DA ABA 1 (ITENS) ---
    
    // busca doadores no banco pra encher a caixinha de "login"
    private void carregarComboDoador() {
        List<Doador> lista = new DoadorDAO().listarDoadores();
        for (Doador d : lista) cbDoadorOwner.addItem(d);
    }

    // pega o texto da tela, cria um objeto Item e manda o DAO salvar
    private void salvarItem() {
        try {
            Item item = new Item();
            item.setNomeItem(txtNomeItem.getText());
            item.setDescricao(txtDescItem.getText());
            item.setCategoria(txtCatItem.getText());
            item.setEstadoConservacao(txtEstadoItem.getText());
            // pega o doador selecionado na caixa e vincula ao item
            item.setDoador((Doador) cbDoadorOwner.getSelectedItem());
            
            new ItemDAO().cadastrarItem(item);
            
            // limpa os campos e atualiza as listas
            txtNomeItem.setText(""); txtDescItem.setText("");
            atualizarTabelaItens();
            carregarComboItensDisponiveis(); // atualiza a aba 2 também (pq tem item novo)
            JOptionPane.showMessageDialog(null, "Item salvo!");
        } catch(Exception e) { e.printStackTrace(); }
    }

    // vai no banco pegar a lista de itens pra mostrar na tabela
    private void atualizarTabelaItens() {
        List<Item> lista = new ItemDAO().listarItens();
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID"); model.addColumn("Nome"); model.addColumn("Categoria"); model.addColumn("Doador");
        for (Item i : lista) model.addRow(new Object[]{i.getIdItem(), i.getNomeItem(), i.getCategoria(), i.getDoador().getNome()});
        tabelaItens.setModel(model);
    }

    // --- LÓGICA DA ABA 2 (ANÚNCIOS) ---
    
    // carrega só itens que ainda NÃO tem anúncio (pra evitar duplicidade)
    private void carregarComboItensDisponiveis() {
        cbItensDisponiveis.removeAllItems();
        List<Item> lista = new ItemDAO().listarItensDisponiveis();
        for (Item i : lista) cbItensDisponiveis.addItem(i);
    }

    // joga o item do combobox pra lista temporária (carrinho) antes de salvar
    private void adicionarItemNaLista() {
        Item i = (Item) cbItensDisponiveis.getSelectedItem();
        if (i != null) {
            itensParaAnunciar.add(i); // guarda na lista lógica
            modeloListaItens.addElement(i); // mostra na lista visual
            cbItensDisponiveis.removeItem(i); // tira do combo pra não por 2x
        }
    }

    // pega o "pacote" todo e salva o anúncio + atualiza os itens
    private void publicarAnuncio() {
        if(itensParaAnunciar.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Adicione itens!"); return;
        }
        Anuncio a = new Anuncio();
        a.setTitulo(txtTituloAnuncio.getText());
        a.setDescricao(txtDescAnuncio.getText());
        a.setLocalizacao(txtLocalAnuncio.getText());
        a.setOpcoesFrete((String) cbFrete.getSelectedItem());
        a.setItens(itensParaAnunciar); // aqui eu vinculo a lista de itens ao anúncio
        
        // o DAO é esperto: salva o anúncio e depois vai em cada item e põe o ID do anúncio neles
        new AnuncioDAO().cadastrarAnuncio(a);
        
        // reseta tudo pra começar de novo
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

    // --- LÓGICA DA ABA 3 (AVALIAÇÃO) ---
    
    // lista doações que já foram finalizadas (aprovadas)
    private void atualizarTabelaAvaliacao() {
        List<Doacao> lista = new DoacaoDAO().listarDoacoes();
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID"); model.addColumn("Item"); model.addColumn("Beneficiário"); model.addColumn("Status");
        
        for (Doacao d : lista) {
            // filtro: só quero ver o que tá aprovado
            if ("Aprovada".equalsIgnoreCase(d.getStatusAtual())) {
                model.addRow(new Object[]{d.getIdDoacao(), d.getItem().getNomeItem(), d.getBeneficiario().getNome(), d.getStatusAtual()});
            }
        }
        tabelaAvaliacao.setModel(model);
    }

    // pega a nota e comentário e grava no banco
    private void enviarAvaliacao() {
        int row = tabelaAvaliacao.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(null, "Selecione uma doação!"); return; }
        
        int id = (int) tabelaAvaliacao.getValueAt(row, 0);
        // pega só o número da nota ("5 - Excelente" vira 5)
        int nota = Integer.parseInt(((String)cbNotaAvaliacao.getSelectedItem()).substring(0, 1));
        
        // chama o método específico que avalia o BENEFICIÁRIO (recalcula a média dele)
        new DoacaoDAO().avaliarBeneficiario(id, nota, txtComentarioAvaliacao.getText());
        txtComentarioAvaliacao.setText("");
    }
}