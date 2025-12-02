package br.com.doacao.view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import br.com.doacao.dao.DoadorDAO;
import br.com.doacao.model.Doador;

public class TelaCadastroDoador extends JFrame {

    
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    private JTextField txtNome;
    private JTextField txtEmail;
    private JTextField txtTelefone;
    private JTextField txtEndereco;

    public static void main(String[] args) {
        // TRUQUE DE MESTRE: Isso faz a janela pegar o visual do seu Windows (Fica muito mais bonito)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    TelaCadastroDoador frame = new TelaCadastroDoador();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public TelaCadastroDoador() {
        setTitle("Novo Doador");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 360); // Tamanho ajustado
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null); // Layout simples (Absolute)

        // --- TÍTULO CENTRALIZADO ---
        JLabel lblTitulo = new JLabel("Cadastro de Doador");
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18)); // Fonte maior e negrito
        lblTitulo.setBounds(10, 11, 414, 30);
        contentPane.add(lblTitulo);
        
        // Linha separadora para organizar
        JSeparator separator = new JSeparator();
        separator.setBounds(10, 45, 414, 2);
        contentPane.add(separator);

        // --- CAMPOS (Bem alinhados) ---
        
        // Nome
        JLabel lblNome = new JLabel("Nome Completo:");
        lblNome.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblNome.setBounds(30, 70, 100, 14);
        contentPane.add(lblNome);

        txtNome = new JTextField();
        txtNome.setBounds(30, 90, 370, 25); // Altura 25 fica melhor que 20
        contentPane.add(txtNome);
        txtNome.setColumns(10);

        // Email
        JLabel lblEmail = new JLabel("E-mail:");
        lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblEmail.setBounds(30, 125, 100, 14);
        contentPane.add(lblEmail);

        txtEmail = new JTextField();
        txtEmail.setBounds(30, 145, 370, 25);
        contentPane.add(txtEmail);

        // Telefone
        JLabel lblTelefone = new JLabel("Telefone:");
        lblTelefone.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblTelefone.setBounds(30, 180, 100, 14);
        contentPane.add(lblTelefone);

        txtTelefone = new JTextField();
        txtTelefone.setBounds(30, 200, 170, 25); // Campo menor
        contentPane.add(txtTelefone);
        
        // Endereço (Ao lado do telefone para economizar espaço ou embaixo)
        JLabel lblEndereco = new JLabel("Endereço:");
        lblEndereco.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblEndereco.setBounds(210, 180, 100, 14);
        contentPane.add(lblEndereco);
        
        txtEndereco = new JTextField();
        txtEndereco.setBounds(210, 200, 190, 25);
        contentPane.add(txtEndereco);

        // --- BOTÃO SALVAR ---
        JButton btnSalvar = new JButton("Salvar Doador");
        btnSalvar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                salvarDoador();
            }
        });
        btnSalvar.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnSalvar.setBounds(130, 260, 160, 35); // Botão maiorzinho
        contentPane.add(btnSalvar);
    }

    private void salvarDoador() {
        try {
            Doador d = new Doador();
            d.setNome(txtNome.getText());
            d.setEmail(txtEmail.getText());
            d.setTelefone(txtTelefone.getText());
            d.setEndereco(txtEndereco.getText());

            DoadorDAO dao = new DoadorDAO();
            dao.cadastrarDoador(d);

            JOptionPane.showMessageDialog(null, "Doador cadastrado com sucesso!");

            // Limpa os campos
            txtNome.setText("");
            txtEmail.setText("");
            txtTelefone.setText("");
            txtEndereco.setText("");
            
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "Erro: " + erro.getMessage());
        }
    }
}