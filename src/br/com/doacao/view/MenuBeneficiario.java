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
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class MenuBeneficiario extends JFrame {

    
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MenuBeneficiario frame = new MenuBeneficiario();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public MenuBeneficiario() {
        setTitle("Área do Beneficiário");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 450); // Aumentei a altura
        contentPane = new JPanel();
        contentPane.setBackground(Color.WHITE);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // --- CABEÇALHO LARANJA ---
        JPanel panelHeader = new JPanel();
        panelHeader.setBackground(new Color(230, 126, 34)); 
        panelHeader.setBounds(0, 0, 484, 70);
        contentPane.add(panelHeader);
        panelHeader.setLayout(null);

        JLabel lblTitulo = new JLabel("Área do Beneficiário");
        lblTitulo.setForeground(Color.BLACK); // FONTE PRETA
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBounds(10, 15, 464, 40);
        panelHeader.add(lblTitulo);

        // --- CONTEÚDO ---
        JLabel lblAcoes = new JLabel("Encontre o que você precisa:");
        lblAcoes.setForeground(Color.BLACK); // FONTE PRETA
        lblAcoes.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblAcoes.setBounds(40, 90, 300, 20);
        contentPane.add(lblAcoes);

        // Botão 1: Ver Mural
        JButton btnMural = new JButton("1. Ver Mural de Doações");
        btnMural.setForeground(Color.BLACK); // FONTE PRETA
        btnMural.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TelaListagemAnuncio tela = new TelaListagemAnuncio();
                tela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                tela.setVisible(true);
            }
        });
        btnMural.setBackground(new Color(240, 240, 240));
        btnMural.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnMural.setBounds(40, 130, 250, 45);
        contentPane.add(btnMural);

        // Botão 2: Pedir Doação
        JButton btnPedir = new JButton("2. Solicitar Doação");
        btnPedir.setForeground(Color.BLACK); // FONTE PRETA
        btnPedir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TelaRealizarDoacao tela = new TelaRealizarDoacao();
                tela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                tela.setVisible(true);
            }
        });
        btnPedir.setBackground(new Color(240, 240, 240));
        btnPedir.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnPedir.setBounds(40, 190, 250, 45);
        contentPane.add(btnPedir);
        
        // --- NOVO BOTÃO: AVALIAR ---
        JButton btnAvaliar = new JButton("3. Avaliar Doação Recebida");
        btnAvaliar.setForeground(Color.BLACK); // FONTE PRETA
        btnAvaliar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TelaAvaliacao tela = new TelaAvaliacao();
                tela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                tela.setVisible(true);
            }
        });
        btnAvaliar.setBackground(new Color(255, 255, 153)); // Amarelo claro
        btnAvaliar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnAvaliar.setBounds(40, 250, 250, 45);
        contentPane.add(btnAvaliar);

        // --- BOTÃO SAIR ---
        JButton btnSair = new JButton("Voltar ao Início");
        btnSair.setBackground(new Color(231, 76, 60)); 
        btnSair.setForeground(Color.BLACK); // FONTE PRETA
        btnSair.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnSair.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new TelaInicial().setVisible(true);
            }
        });
        btnSair.setBounds(320, 340, 140, 35); // Ajustei a posição Y
        contentPane.add(btnSair);
    }
}