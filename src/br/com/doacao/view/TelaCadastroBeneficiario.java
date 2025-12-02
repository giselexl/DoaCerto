package br.com.doacao.view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import br.com.doacao.dao.BeneficiarioDAO;
import br.com.doacao.model.Beneficiario;

public class TelaCadastroBeneficiario extends JFrame {

 
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    private JTextField txtNome;
    private JTextField txtEmail;
    private JTextField txtTelefone;
    private JTextField txtEndereco;

    // Método principal para rodar a tela
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    TelaCadastroBeneficiario frame = new TelaCadastroBeneficiario();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // Construtor (Desenho da Tela)
    public TelaCadastroBeneficiario() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null); // Layout Absoluto

        // --- NOME ---
        JLabel lblNome = new JLabel("Nome:");
        lblNome.setBounds(20, 20, 80, 14);
        contentPane.add(lblNome);

        txtNome = new JTextField();
        txtNome.setBounds(90, 17, 250, 20);
        contentPane.add(txtNome);
        txtNome.setColumns(10);

        // --- EMAIL ---
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(20, 50, 80, 14);
        contentPane.add(lblEmail);

        txtEmail = new JTextField();
        txtEmail.setBounds(90, 47, 250, 20);
        contentPane.add(txtEmail);

        // --- TELEFONE ---
        JLabel lblTelefone = new JLabel("Telefone:");
        lblTelefone.setBounds(20, 80, 80, 14);
        contentPane.add(lblTelefone);

        txtTelefone = new JTextField();
        txtTelefone.setBounds(90, 77, 150, 20);
        contentPane.add(txtTelefone);

        // --- ENDEREÇO ---
        JLabel lblEndereco = new JLabel("Endereço:");
        lblEndereco.setBounds(20, 110, 80, 14);
        contentPane.add(lblEndereco);

        txtEndereco = new JTextField();
        txtEndereco.setBounds(90, 107, 250, 20);
        contentPane.add(txtEndereco);

        // --- BOTÃO SALVAR ---
        JButton btnSalvar = new JButton("Cadastrar Beneficiário");
        btnSalvar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                salvarBeneficiario();
            }
        });
        btnSalvar.setBounds(90, 150, 180, 30);
        contentPane.add(btnSalvar);
    }

    // Lógica do botão Salvar
    private void salvarBeneficiario() {
        try {
            // 1. Preenche o objeto com os dados da tela
            Beneficiario b = new Beneficiario();
            b.setNome(txtNome.getText());
            b.setEmail(txtEmail.getText());
            b.setTelefone(txtTelefone.getText());
            b.setEndereco(txtEndereco.getText());

            // 2. Chama o DAO para gravar no banco
            BeneficiarioDAO dao = new BeneficiarioDAO();
            dao.cadastrar(b);

            // 3. Feedback e Limpeza
            JOptionPane.showMessageDialog(null, "Beneficiário cadastrado com sucesso!");
            txtNome.setText("");
            txtEmail.setText("");
            txtTelefone.setText("");
            txtEndereco.setText("");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
        }
    }
}