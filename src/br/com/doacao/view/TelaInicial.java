package br.com.doacao.view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class TelaInicial extends JFrame {

 
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) { e.printStackTrace(); }

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    TelaInicial frame = new TelaInicial();
                    frame.setVisible(true);
                } catch (Exception e) { e.printStackTrace(); }
            }
        });
    }

    public TelaInicial() {
        setTitle("Bem-vindo ao DoaCerto");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 400);
        contentPane = new JPanel();
        contentPane.setBackground(Color.WHITE);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // --- TÍTULO ---
        JLabel lblTitulo = new JLabel("Quem é você?");
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(50, 50, 50));
        lblTitulo.setBounds(10, 20, 414, 40);
        contentPane.add(lblTitulo);

        // --- BOTÃO DOADOR ---
        JButton btnDoador = new JButton("SOU DOADOR");
        btnDoador.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Abre direto a área do doador
                DashboardDoador menu = new DashboardDoador();
                menu.setVisible(true);
                dispose(); // Fecha essa tela inicial
            }
        });
        btnDoador.setBackground(new Color(46, 204, 113)); // Verde
        btnDoador.setForeground(Color.BLACK);
        btnDoador.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnDoador.setBounds(70, 80, 300, 50);
        contentPane.add(btnDoador);
        
        JLabel lblDica1 = new JLabel("Quero doar objetos");
        lblDica1.setHorizontalAlignment(SwingConstants.CENTER);
        lblDica1.setBounds(70, 135, 300, 14);
        contentPane.add(lblDica1);

        // --- BOTÃO BENEFICIÁRIO ---
        JButton btnBeneficiario = new JButton("SOU BENEFICIÁRIO");
        btnBeneficiario.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Abre direto a área do beneficiário
                DashboardBeneficiario menu = new DashboardBeneficiario();
                menu.setVisible(true);
                dispose();
            }
        });
        btnBeneficiario.setBackground(new Color(230, 126, 34)); // Laranja
        btnBeneficiario.setForeground(Color.BLACK);
        btnBeneficiario.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnBeneficiario.setBounds(70, 170, 300, 50);
        contentPane.add(btnBeneficiario);
        
        JLabel lblDica2 = new JLabel("Estou buscando doações");
        lblDica2.setHorizontalAlignment(SwingConstants.CENTER);
        lblDica2.setBounds(70, 225, 300, 14);
        contentPane.add(lblDica2);

        // --- BOTÃO GESTOR (ADMIN) ---
        JButton btnGestor = new JButton("SOU GESTOR");
        btnGestor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                autenticarGestor();
            }
        });
        btnGestor.setBackground(new Color(52, 152, 219)); // Azul
        btnGestor.setForeground(Color.BLACK);
        btnGestor.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnGestor.setBounds(70, 260, 300, 50);
        contentPane.add(btnGestor);
        
        JLabel lblDica3 = new JLabel("Administração do Sistema");
        lblDica3.setHorizontalAlignment(SwingConstants.CENTER);
        lblDica3.setBounds(70, 315, 300, 14);
        contentPane.add(lblDica3);
    }
    
    // Lógica da senha "secreta"
    private void autenticarGestor() {
        // Abre uma caixinha simples pedindo a senha
        String senha = JOptionPane.showInputDialog(null, "Digite a senha de acesso:");
        
        // Verifica se cancelou ou se a senha está errada
        if (senha == null) {
            return; // Clicou em cancelar
        }
        
        if (senha.equals("batata")) {
            // Senha correta!
            DashboardGestor admin = new DashboardGestor();
            admin.setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Senha incorreta! Acesso negado.");
        }
    }
}