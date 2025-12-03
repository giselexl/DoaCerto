package br.com.doacao.view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class MenuPrincipal extends JFrame {


	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

    public static void main(String[] args) {
        // Visual estilo Windows
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MenuPrincipal frame = new MenuPrincipal();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public MenuPrincipal() {
        setTitle("Sistema de Doações - Menu do Gestor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 700, 580); // Aumentei a altura para caber o relatório
        contentPane = new JPanel();
        contentPane.setBackground(Color.WHITE);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // --- CABEÇALHO ---
        JPanel panelHeader = new JPanel();
        panelHeader.setBackground(new Color(0, 102, 204));
        panelHeader.setBounds(0, 0, 684, 80);
        contentPane.add(panelHeader);
        panelHeader.setLayout(null);

        JLabel lblTitulo = new JLabel("Sistema de Gestão de Doações");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBounds(10, 20, 664, 40);
        panelHeader.add(lblTitulo);

        // ==========================================================
        // GRUPO 1: CADASTROS BÁSICOS (Lado Esquerdo)
        // ==========================================================
        JLabel lblCadastros = new JLabel("Cadastros");
        lblCadastros.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblCadastros.setBounds(40, 100, 200, 30);
        contentPane.add(lblCadastros);

        JButton btnNovoDoador = new JButton("Novo Doador");
        btnNovoDoador.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TelaCadastroDoador tela = new TelaCadastroDoador();
                tela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                tela.setVisible(true);
            }
        });
        btnNovoDoador.setBounds(40, 140, 180, 40);
        contentPane.add(btnNovoDoador);

        JButton btnNovoBeneficiario = new JButton("Novo Beneficiário");
        btnNovoBeneficiario.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TelaCadastroBeneficiario tela = new TelaCadastroBeneficiario();
                tela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                tela.setVisible(true);
            }
        });
        btnNovoBeneficiario.setBounds(40, 190, 180, 40);
        contentPane.add(btnNovoBeneficiario);

        JButton btnNovoItem = new JButton("Novo Item");
        btnNovoItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TelaCadastroItem tela = new TelaCadastroItem();
                tela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                tela.setVisible(true);
            }
        });
        btnNovoItem.setBounds(40, 240, 180, 40);
        contentPane.add(btnNovoItem);
        
        // Botões de Listagem (Menores)
        JButton btnListarDoador = new JButton("Ver Doadores");
        btnListarDoador.setFont(new Font("Tahoma", Font.PLAIN, 10));
        btnListarDoador.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TelaListagemDoador tela = new TelaListagemDoador();
                tela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                tela.setVisible(true);
            }
        });
        btnListarDoador.setBounds(230, 140, 100, 40);
        contentPane.add(btnListarDoador);
        
        JButton btnListarItens = new JButton("Ver Itens");
        btnListarItens.setFont(new Font("Tahoma", Font.PLAIN, 10));
        btnListarItens.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TelaListagemItem tela = new TelaListagemItem();
                tela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                tela.setVisible(true);
            }
        });
        btnListarItens.setBounds(230, 240, 100, 40);
        contentPane.add(btnListarItens);

        // Separador Vertical
        JSeparator separator = new JSeparator();
        separator.setOrientation(SwingConstants.VERTICAL);
        separator.setBounds(350, 100, 2, 380);
        contentPane.add(separator);

        // ==========================================================
        // GRUPO 2: OPERAÇÕES (Lado Direito)
        // ==========================================================
        JLabel lblOperacoes = new JLabel("Operações & Doações");
        lblOperacoes.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblOperacoes.setBounds(380, 100, 200, 30);
        contentPane.add(lblOperacoes);

        // --- ANÚNCIOS ---
        JButton btnCriarAnuncio = new JButton("Criar Anúncio");
        btnCriarAnuncio.setBackground(new Color(240, 240, 240));
        btnCriarAnuncio.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TelaCriarAnuncio tela = new TelaCriarAnuncio();
                tela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                tela.setVisible(true);
            }
        });
        btnCriarAnuncio.setBounds(380, 140, 130, 40);
        contentPane.add(btnCriarAnuncio);

        JButton btnMural = new JButton("Mural de Anúncios");
        btnMural.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TelaListagemAnuncio tela = new TelaListagemAnuncio();
                tela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                tela.setVisible(true);
            }
        });
        btnMural.setBounds(520, 140, 140, 40);
        contentPane.add(btnMural);

        // --- PAINEL DE DOAÇÃO ---
        JPanel panelDoacao = new JPanel();
        panelDoacao.setBorder(new LineBorder(new Color(0, 102, 204), 1, true));
        panelDoacao.setBackground(new Color(245, 250, 255));
        panelDoacao.setBounds(380, 200, 280, 140);
        contentPane.add(panelDoacao);
        panelDoacao.setLayout(null);
        
        JLabel lblAreaDoacao = new JLabel("Área de Doação");
        lblAreaDoacao.setHorizontalAlignment(SwingConstants.CENTER);
        lblAreaDoacao.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblAreaDoacao.setForeground(new Color(0, 102, 204));
        lblAreaDoacao.setBounds(10, 5, 260, 20);
        panelDoacao.add(lblAreaDoacao);

        JButton btnFazerPedido = new JButton("Realizar Pedido (Admin)");
        btnFazerPedido.setBackground(new Color(50, 205, 50)); // Verde
        btnFazerPedido.setForeground(Color.BLACK);
        btnFazerPedido.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnFazerPedido.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TelaRealizarDoacao tela = new TelaRealizarDoacao();
                tela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                tela.setVisible(true);
            }
        });
        btnFazerPedido.setBounds(40, 35, 200, 40);
        panelDoacao.add(btnFazerPedido);

        JButton btnAdministracao = new JButton("Administração (Análise)");
        btnAdministracao.setBackground(new Color(255, 69, 0)); // Vermelho
        btnAdministracao.setForeground(Color.BLACK);
        btnAdministracao.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnAdministracao.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TelaAnaliseDoacao tela = new TelaAnaliseDoacao();
                tela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                tela.setVisible(true);
            }
        });
        btnAdministracao.setBounds(40, 85, 200, 40);
        panelDoacao.add(btnAdministracao);
        
        // --- ÁREA DE RELATÓRIOS (NOVO) ---
        JLabel lblRelatorios = new JLabel("Relatórios & Qualidade");
        lblRelatorios.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblRelatorios.setBounds(380, 360, 250, 30);
        contentPane.add(lblRelatorios);
        
        // BOTÃO NOVO AQUI:
        JButton btnRelAvaliacoes = new JButton("Relatório de Usuários");
        btnRelAvaliacoes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TelaRelatorioUsuarios tela = new TelaRelatorioUsuarios();
                tela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                tela.setVisible(true);
            }
        });
        btnRelAvaliacoes.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnRelAvaliacoes.setBounds(380, 395, 280, 40);
        contentPane.add(btnRelAvaliacoes);
        
        // --- BOTÃO SAIR ---
        JButton btnSair = new JButton("Logout / Sair");
        btnSair.setBackground(new Color(100, 100, 100));
        btnSair.setForeground(Color.WHITE);
        btnSair.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new TelaInicial().setVisible(true);
            }
        });
        btnSair.setBounds(10, 490, 120, 30);
        contentPane.add(btnSair);
        
        // --- RODAPÉ ---
        JLabel lblFooter = new JLabel("Sistema DoaCerto v1.0 - Modo Gestor");
        lblFooter.setForeground(Color.GRAY);
        lblFooter.setHorizontalAlignment(SwingConstants.CENTER);
        lblFooter.setBounds(0, 510, 684, 20);
        contentPane.add(lblFooter);
    }
}