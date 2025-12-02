package br.com.doacao.view;

import java.awt.Color;
import java.awt.EventQueue; // Importante para rodar
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager; // Importante para o visual Windows
import javax.swing.border.EmptyBorder;

public class MenuDoador extends JFrame {


	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

    // --- AQUI ESTAVA FALTANDO O MÉTODO MAIN ---
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MenuDoador frame = new MenuDoador();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    // ------------------------------------------

    public MenuDoador() {
        setTitle("Área do Doador");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 420);
        contentPane = new JPanel();
        contentPane.setBackground(Color.WHITE);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // --- CABEÇALHO VERDE ---
        JPanel panelHeader = new JPanel();
        panelHeader.setBackground(new Color(46, 204, 113)); // Verde Esperança
        panelHeader.setBounds(0, 0, 484, 70);
        contentPane.add(panelHeader);
        panelHeader.setLayout(null);

        JLabel lblTitulo = new JLabel("Minha Área de Doador");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBounds(10, 15, 464, 40);
        panelHeader.add(lblTitulo);

        // --- CONTEÚDO ---
        JLabel lblAcoes = new JLabel("O que você deseja fazer hoje?");
        lblAcoes.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblAcoes.setBounds(40, 90, 300, 20);
        contentPane.add(lblAcoes);

        // Botão 1: Cadastrar Item
        JButton btnNovoItem = new JButton("1. Cadastrar Novo Item");
        btnNovoItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TelaCadastroItem tela = new TelaCadastroItem();
                tela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                tela.setVisible(true);
            }
        });
        btnNovoItem.setBackground(new Color(240, 240, 240));
        btnNovoItem.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnNovoItem.setBounds(40, 130, 220, 45);
        contentPane.add(btnNovoItem);

        // Botão 2: Criar Anúncio
        JButton btnCriarAnuncio = new JButton("2. Criar Anúncio");
        btnCriarAnuncio.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TelaCriarAnuncio tela = new TelaCriarAnuncio();
                tela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                tela.setVisible(true);
            }
        });
        btnCriarAnuncio.setBackground(new Color(240, 240, 240));
        btnCriarAnuncio.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnCriarAnuncio.setBounds(40, 190, 220, 45);
        contentPane.add(btnCriarAnuncio);
        
        // Botão 3: Ver Anúncios (Mural)
        JButton btnVerAnuncios = new JButton("3. Ver Anúncios");
        btnVerAnuncios.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TelaListagemAnuncio tela = new TelaListagemAnuncio();
                tela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                tela.setVisible(true);
            }
        });
        btnVerAnuncios.setBackground(new Color(240, 240, 240));
        btnVerAnuncios.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnVerAnuncios.setBounds(40, 250, 220, 45);
        contentPane.add(btnVerAnuncios);

        // --- BOTÃO SAIR / VOLTAR ---
        JButton btnSair = new JButton("Voltar ao Início");
        btnSair.setBackground(new Color(231, 76, 60)); // Vermelho
        btnSair.setForeground(Color.WHITE);
        btnSair.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnSair.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Fecha este menu
                new TelaInicial().setVisible(true); // Abre a tela inicial
            }
        });
        btnSair.setBounds(320, 330, 140, 35);
        contentPane.add(btnSair);
    }
}