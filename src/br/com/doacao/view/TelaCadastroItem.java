package br.com.doacao.view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import br.com.doacao.dao.DoadorDAO;
import br.com.doacao.dao.ItemDAO;
import br.com.doacao.model.Doador;
import br.com.doacao.model.Item;

public class TelaCadastroItem extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    private JTextField txtNome;
    private JTextField txtDescricao;
    private JTextField txtCategoria;
    private JTextField txtEstado;
    
    // O JComboBox vai guardar objetos inteiros do tipo Doador
    private JComboBox<Doador> cbDoador;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    TelaCadastroItem frame = new TelaCadastroItem();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public TelaCadastroItem() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 350);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // --- LABELS E TEXTFIELDS ---
        JLabel lblNome = new JLabel("Nome Item:");
        lblNome.setBounds(20, 20, 80, 14);
        contentPane.add(lblNome);

        txtNome = new JTextField();
        txtNome.setBounds(100, 17, 200, 20);
        contentPane.add(txtNome);
        txtNome.setColumns(10);

        JLabel lblDesc = new JLabel("Descrição:");
        lblDesc.setBounds(20, 50, 80, 14);
        contentPane.add(lblDesc);

        txtDescricao = new JTextField();
        txtDescricao.setBounds(100, 47, 200, 20);
        contentPane.add(txtDescricao);

        JLabel lblCat = new JLabel("Categoria:");
        lblCat.setBounds(20, 80, 80, 14);
        contentPane.add(lblCat);

        txtCategoria = new JTextField();
        txtCategoria.setBounds(100, 77, 200, 20);
        contentPane.add(txtCategoria);

        JLabel lblEstado = new JLabel("Estado:");
        lblEstado.setBounds(20, 110, 80, 14);
        contentPane.add(lblEstado);

        txtEstado = new JTextField();
        txtEstado.setBounds(100, 107, 200, 20);
        contentPane.add(txtEstado);

        // --- COMBOBOX DE DOADOR ---
        JLabel lblDoador = new JLabel("Doador:");
        lblDoador.setBounds(20, 140, 80, 14);
        contentPane.add(lblDoador);

        cbDoador = new JComboBox<Doador>();
        cbDoador.setBounds(100, 137, 200, 22);
        contentPane.add(cbDoador);

        // --- BOTÃO SALVAR ---
        JButton btnSalvar = new JButton("Salvar Item");
        btnSalvar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                salvarItem(); // Chama o método de salvar
            }
        });
        btnSalvar.setBounds(100, 180, 120, 30);
        contentPane.add(btnSalvar);

        // --- CARREGAR OS DADOS ---
        carregarComboDoadores();
    }

    // 1. Método que busca os doadores no banco e enche a caixinha
    private void carregarComboDoadores() {
        DoadorDAO dao = new DoadorDAO();
        List<Doador> lista = dao.listarDoadores();
        
        for (Doador d : lista) {
            cbDoador.addItem(d); // Adiciona o objeto inteiro na lista
        }
    }

    // 2. Método que salva tudo
    private void salvarItem() {
        try {
            Item item = new Item();
            item.setNomeItem(txtNome.getText());
            item.setDescricao(txtDescricao.getText());
            item.setCategoria(txtCategoria.getText());
            item.setEstadoConservacao(txtEstado.getText());
            
            // O GRANDE TRUQUE: Pegamos o objeto selecionado na caixinha
            Doador doadorSelecionado = (Doador) cbDoador.getSelectedItem();
            item.setDoador(doadorSelecionado);
            
            // Salva no banco
            ItemDAO dao = new ItemDAO();
            dao.cadastrarItem(item);
            
            // Limpa campos
            txtNome.setText("");
            txtDescricao.setText("");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
        }
    }
}