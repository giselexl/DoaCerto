package br.com.doacao.view;

// importando as ferramentas visuais (botões, painéis, cores)
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

// importando componentes da tela
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

// conectando com meus "mordomos" (DAOs) e "formas" (Models)
import br.com.doacao.dao.AnuncioDAO;
import br.com.doacao.dao.BeneficiarioDAO;
import br.com.doacao.dao.DoadorDAO;
import br.com.doacao.dao.DoacaoDAO;
import br.com.doacao.model.Anuncio;
import br.com.doacao.model.Beneficiario;
import br.com.doacao.model.Doador;
import br.com.doacao.model.Doacao;

// essa é a tela principal do chefe (gestor), herda de JFrame pq é uma janela
public class DashboardGestor extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    private JTabbedPane tabbedPane; // esse é o componente que cria as abas mágicas

    // --- VARIÁVEIS GLOBAIS (pra eu acessar de qualquer lugar da classe) ---

    // Coisas da Aba 1 (Doador)
    private JTextField txtNomeDoador, txtEmailDoador;
    private JTable tabelaDoadores;
    private JTextArea txtComentariosDoador; // aqui vai aparecer o texto quando eu clicar na tabela

    // Coisas da Aba 2 (Beneficiário)
    private JTextField txtNomeBen, txtEmailBen;
    private JTable tabelaBeneficiarios;
    private JTextArea txtComentariosBen;    // mesma coisa, pra ver o que falam do beneficiário

    // Coisas da Aba 3 (Análise)
    private JTable tabelaAnalise;
    
    // Coisas da Aba 4 (Anúncios)
    private JTable tabelaAnuncios;

    // --- O START DO PROGRAMA ---
    public static void main(String[] args) {
        try { 
            // deixando com a cara do windows pra ficar bonitão
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
        } catch (Exception e) {}
        
        // fila de eventos pra não travar a tela
        EventQueue.invokeLater(() -> {
            try { new DashboardGestor().setVisible(true); } catch (Exception e) {}
        });
    }

    // --- CONSTRUTOR: AQUI NASCE A TELA ---
    public DashboardGestor() {
        setTitle("Painel de Controle do Gestor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // fecha tudo se clicar no X
        setBounds(100, 100, 900, 700); // tamanho grande pra caber os relatórios
        
        // configurando o painel principal
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        // Título azul lá em cima
        JLabel lblTitulo = new JLabel("  Administração DoaCerto");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(0, 102, 204));
        contentPane.add(lblTitulo, BorderLayout.NORTH);

        // Criando o gerenciador de abas
        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        contentPane.add(tabbedPane, BorderLayout.CENTER);

        // --- ADICIONANDO AS ABAS (CHAMANDO OS MÉTODOS QUE DESENHAM CADA UMA) ---
        // isso aqui organiza tudo, em vez de 5 janelas, tenho 5 abas
        tabbedPane.addTab("1. Doadores", null, criarPainelDoador(), "Cadastrar e Ver Reputação");
        tabbedPane.addTab("2. Beneficiários", null, criarPainelBeneficiario(), "Cadastrar e Ver Reputação");
        tabbedPane.addTab("3. Análise de Pedidos", null, criarPainelAnalise(), "Aprovar Doações");
        tabbedPane.addTab("4. Mural de Anúncios", null, criarPainelAnuncios(), "Ver Mural");
        
        // Botão de Sair lá embaixo
        JButton btnSair = new JButton("Sair do Sistema");
        btnSair.addActionListener(e -> {
            dispose(); // mata essa tela
            new TelaInicial().setVisible(true); // volta pro início
        });
        contentPane.add(btnSair, BorderLayout.SOUTH);
    }

    // =========================================================================
    // PAINEL 1: DOADORES (Cadastro em cima + Lista no meio + Comentário embaixo)
    // =========================================================================
    private JPanel criarPainelDoador() {
        JPanel panel = new JPanel(null);

        // 1. Parte de Cadastro
        JLabel lblNome = new JLabel("Nome:"); lblNome.setBounds(20, 20, 50, 20); panel.add(lblNome);
        txtNomeDoador = new JTextField(); txtNomeDoador.setBounds(70, 20, 200, 25); panel.add(txtNomeDoador);
        
        JLabel lblEmail = new JLabel("Email:"); lblEmail.setBounds(290, 20, 50, 20); panel.add(lblEmail);
        txtEmailDoador = new JTextField(); txtEmailDoador.setBounds(330, 20, 200, 25); panel.add(txtEmailDoador);
        
        JButton btnSalvar = new JButton("Cadastrar");
        btnSalvar.setBackground(new Color(46, 204, 113)); btnSalvar.setForeground(Color.WHITE);
        btnSalvar.setBounds(550, 20, 100, 25);
        
        // Lógica do botão salvar
        btnSalvar.addActionListener(e -> {
            // crio o objeto, encho de dados e mando pro DAO salvar
            Doador d = new Doador(); d.setNome(txtNomeDoador.getText()); d.setEmail(txtEmailDoador.getText());
            new DoadorDAO().cadastrarDoador(d); 
            atualizarTabelaDoador(); // recarrego a lista na hora pra ver que salvou
            txtNomeDoador.setText(""); txtEmailDoador.setText(""); // limpo os campos
            JOptionPane.showMessageDialog(null, "Doador Salvo!");
        });
        panel.add(btnSalvar);

        // 2. Tabela de Listagem
        tabelaDoadores = new JTable();
        // O PULO DO GATO: Evento de clique do mouse na tabela
        tabelaDoadores.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { 
                carregarComentariosDoador(); // se clicar, busca os comentários dele
            }
        });
        JScrollPane sp = new JScrollPane(tabelaDoadores);
        sp.setBounds(20, 60, 840, 300); 
        panel.add(sp);

        // 3. Área de Texto pros Comentários (Relatório)
        txtComentariosDoador = new JTextArea();
        txtComentariosDoador.setEditable(false); // só leitura
        txtComentariosDoador.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        JScrollPane spTxt = new JScrollPane(txtComentariosDoador);
        spTxt.setBounds(20, 370, 840, 180);
        spTxt.setBorder(new TitledBorder("Avaliações e Comentários (Clique na tabela acima para ver)"));
        panel.add(spTxt);
        
        atualizarTabelaDoador(); // carrega a lista quando abre
        return panel;
    }

    // =========================================================================
    // PAINEL 2: BENEFICIÁRIOS (Igual ao doador, só muda o DAO)
    // =========================================================================
    private JPanel criarPainelBeneficiario() {
        JPanel panel = new JPanel(null);

        // Cadastro
        JLabel lblNome = new JLabel("Nome:"); lblNome.setBounds(20, 20, 50, 20); panel.add(lblNome);
        txtNomeBen = new JTextField(); txtNomeBen.setBounds(70, 20, 200, 25); panel.add(txtNomeBen);
        
        JLabel lblEmail = new JLabel("Email:"); lblEmail.setBounds(290, 20, 50, 20); panel.add(lblEmail);
        txtEmailBen = new JTextField(); txtEmailBen.setBounds(330, 20, 200, 25); panel.add(txtEmailBen);
        
        JButton btnSalvar = new JButton("Cadastrar");
        btnSalvar.setBackground(new Color(230, 126, 34)); btnSalvar.setForeground(Color.WHITE);
        btnSalvar.setBounds(550, 20, 100, 25);
        btnSalvar.addActionListener(e -> {
            Beneficiario b = new Beneficiario(); b.setNome(txtNomeBen.getText()); b.setEmail(txtEmailBen.getText());
            new BeneficiarioDAO().cadastrar(b); 
            atualizarTabelaBeneficiario();
            txtNomeBen.setText(""); txtEmailBen.setText("");
            JOptionPane.showMessageDialog(null, "Beneficiário Salvo!");
        });
        panel.add(btnSalvar);

        // Tabela
        tabelaBeneficiarios = new JTable();
        tabelaBeneficiarios.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { carregarComentariosBeneficiario(); } // clique pra ver detalhes
        });
        JScrollPane sp = new JScrollPane(tabelaBeneficiarios);
        sp.setBounds(20, 60, 840, 300);
        panel.add(sp);

        // Área de Texto
        txtComentariosBen = new JTextArea();
        txtComentariosBen.setEditable(false);
        txtComentariosBen.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        JScrollPane spTxt = new JScrollPane(txtComentariosBen);
        spTxt.setBounds(20, 370, 840, 180);
        spTxt.setBorder(new TitledBorder("Avaliações e Comentários (Clique na tabela acima para ver)"));
        panel.add(spTxt);
        
        atualizarTabelaBeneficiario();
        return panel;
    }

    // =========================================================================
    // PAINEL 3: ANÁLISE (Onde eu aprovo ou reprovo)
    // =========================================================================
    private JPanel criarPainelAnalise() {
        JPanel p = new JPanel(new BorderLayout());
        
        tabelaAnalise = new JTable();
        p.add(new JScrollPane(tabelaAnalise), BorderLayout.CENTER);
        
        // Botões de ação lá embaixo
        JPanel bPanel = new JPanel();
        JButton b1 = new JButton("Aprovar"); b1.setBackground(new Color(0,153,76)); b1.setForeground(Color.WHITE);
        JButton b2 = new JButton("Rejeitar"); b2.setBackground(new Color(204,0,0)); b2.setForeground(Color.WHITE);
        JButton b3 = new JButton("Atualizar Lista");
        bPanel.add(b1); bPanel.add(b2); bPanel.add(b3);
        p.add(bPanel, BorderLayout.SOUTH);
        
        DoacaoDAO dao = new DoacaoDAO();
        
        // Funçãozinha pra recarregar a lista de pendentes
        Runnable att = () -> {
            List<Doacao> l = dao.listarDoacoesEmAnalise();
            DefaultTableModel m = new DefaultTableModel(); 
            m.addColumn("ID"); m.addColumn("Item"); m.addColumn("Beneficiário");
            for(Doacao d:l) m.addRow(new Object[]{d.getIdDoacao(), d.getItem().getNomeItem(), d.getBeneficiario().getNome()});
            tabelaAnalise.setModel(m);
        };
        
        b3.addActionListener(e->att.run());
        
        // Lógica de Aprovar
        b1.addActionListener(e->{
            int r=tabelaAnalise.getSelectedRow(); 
            if(r>-1) { 
                // pega o ID e manda o DAO aprovar com a data de hoje
                dao.aprovarDoacao((int)tabelaAnalise.getValueAt(r,0), new java.sql.Timestamp(System.currentTimeMillis())); 
                att.run(); // atualiza a lista (o item some pq não tá mais "em análise")
            }
        });
        
        // Lógica de Rejeitar
        b2.addActionListener(e->{
            int r=tabelaAnalise.getSelectedRow(); 
            if(r>-1) { 
                // pede o motivo e manda o DAO rejeitar
                dao.rejeitarDoacao((int)tabelaAnalise.getValueAt(r,0), JOptionPane.showInputDialog("Motivo:")); 
                att.run(); 
            }
        });
        
        att.run(); // carrega ao iniciar
        return p;
    }
    
    // =========================================================================
    // PAINEL 4: ANÚNCIOS (Só pra ver o que tá rolando no mural)
    // =========================================================================
    private JPanel criarPainelAnuncios() {
        JPanel p = new JPanel(new BorderLayout());
        tabelaAnuncios = new JTable();
        p.add(new JScrollPane(tabelaAnuncios), BorderLayout.CENTER);
        
        JButton b = new JButton("Recarregar"); 
        p.add(b, BorderLayout.SOUTH);
        
        b.addActionListener(e->atualizarTabelaAnuncios());
        
        atualizarTabelaAnuncios();
        return p;
    }

    // --- MÉTODOS DE APOIO (QUE VÃO NO BANCO BUSCAR DADOS) ---

    private void atualizarTabelaDoador() {
        List<Doador> l = new DoadorDAO().listarDoadores();
        DefaultTableModel m = new DefaultTableModel(); 
        m.addColumn("ID"); m.addColumn("Nome"); m.addColumn("Email"); m.addColumn("Média");
        // Loop pra encher a tabela, formata a nota pra ficar bonitinha (4,50)
        for(Doador d:l) m.addRow(new Object[]{d.getIdDoador(), d.getNome(), d.getEmail(), String.format("%.2f", d.getPontuacaoAvaliacao())});
        tabelaDoadores.setModel(m);
    }
    
    private void atualizarTabelaBeneficiario() {
        List<Beneficiario> l = new BeneficiarioDAO().listarTodos();
        DefaultTableModel m = new DefaultTableModel(); 
        m.addColumn("ID"); m.addColumn("Nome"); m.addColumn("Email"); m.addColumn("Média");
        for(Beneficiario b:l) m.addRow(new Object[]{b.getIdBeneficiario(), b.getNome(), b.getEmail(), String.format("%.2f", b.getPontuacaoAvaliacao())});
        tabelaBeneficiarios.setModel(m);
    }
    
    private void atualizarTabelaAnuncios() {
        List<Anuncio> l = new AnuncioDAO().listarAnuncios();
        DefaultTableModel m = new DefaultTableModel(); m.addColumn("ID"); m.addColumn("Título"); m.addColumn("Local");
        for(Anuncio a:l) m.addRow(new Object[]{a.getIdAnuncio(), a.getTitulo(), a.getLocalizacao()});
        tabelaAnuncios.setModel(m);
    }

    // --- MÉTODOS QUE CARREGAM O RELATÓRIO QUANDO CLICA ---

    private void carregarComentariosDoador() {
        int r = tabelaDoadores.getSelectedRow(); // pega qual linha cliquei
        if(r > -1) {
            int id = (int) tabelaDoadores.getValueAt(r, 0); // pega o ID (coluna 0)
            String nome = (String) tabelaDoadores.getValueAt(r, 1);
            
            // chama o DAO pra buscar a lista de textos
            List<String> lista = new DoadorDAO().listarComentarios(id);
            
            // monta o textão bonito
            StringBuilder sb = new StringBuilder("Histórico de " + nome + ":\n\n");
            if(lista.isEmpty()) sb.append("(Sem comentários)");
            else for(String s : lista) sb.append(s).append("\n---\n");
            
            txtComentariosDoador.setText(sb.toString()); // joga na tela
        }
    }

    private void carregarComentariosBeneficiario() {
        int r = tabelaBeneficiarios.getSelectedRow();
        if(r > -1) {
            int id = (int) tabelaBeneficiarios.getValueAt(r, 0);
            String nome = (String) tabelaBeneficiarios.getValueAt(r, 1);
            
            List<String> lista = new BeneficiarioDAO().listarComentarios(id);
            
            StringBuilder sb = new StringBuilder("Histórico de " + nome + ":\n\n");
            if(lista.isEmpty()) sb.append("(Sem comentários)");
            else for(String s : lista) sb.append(s).append("\n---\n");
            
            txtComentariosBen.setText(sb.toString());
        }
    }
}