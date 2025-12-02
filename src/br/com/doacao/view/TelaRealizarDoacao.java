package br.com.doacao.view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import br.com.doacao.dao.BeneficiarioDAO;
import br.com.doacao.dao.DoacaoDAO;
import br.com.doacao.dao.ItemDAO;
import br.com.doacao.model.Beneficiario;
import br.com.doacao.model.DoacaoEmAnalise; // IMPORTANTE: Importar a classe filha
import br.com.doacao.model.Item;

public class TelaRealizarDoacao extends JFrame {

 
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    private JComboBox<Beneficiario> cbBeneficiario;
    private JComboBox<Item> cbItem;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    TelaRealizarDoacao frame = new TelaRealizarDoacao();
                    frame.setVisible(true);
                } catch (Exception e) { e.printStackTrace(); }
            }
        });
    }

    public TelaRealizarDoacao() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblTitulo = new JLabel("Registrar Doação");
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblTitulo.setBounds(150, 11, 200, 30);
        contentPane.add(lblTitulo);

        JLabel lblBen = new JLabel("Quem vai receber?");
        lblBen.setBounds(30, 60, 120, 14);
        contentPane.add(lblBen);

        cbBeneficiario = new JComboBox<Beneficiario>();
        cbBeneficiario.setBounds(160, 57, 250, 22);
        contentPane.add(cbBeneficiario);

        JLabel lblItem = new JLabel("Qual item?");
        lblItem.setBounds(30, 100, 120, 14);
        contentPane.add(lblItem);

        cbItem = new JComboBox<Item>();
        cbItem.setBounds(160, 97, 250, 22);
        contentPane.add(cbItem);

        JButton btnDoar = new JButton("Finalizar Doação");
        btnDoar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                realizarDoacao();
            }
        });
        btnDoar.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnDoar.setBounds(160, 150, 150, 40);
        contentPane.add(btnDoar);
        
        carregarCombos();
    }
    
    private void carregarCombos() {
        try {
            BeneficiarioDAO benDao = new BeneficiarioDAO();
            List<Beneficiario> listaBen = benDao.listarTodos();
            for(Beneficiario b : listaBen) cbBeneficiario.addItem(b);
            
            ItemDAO itemDao = new ItemDAO();
            List<Item> listaItem = itemDao.listarItens();
            for(Item i : listaItem) cbItem.addItem(i);
            
        } catch (Exception e) { e.printStackTrace(); }
    }
    
    private void realizarDoacao() {
        try {
            Beneficiario bSelecionado = (Beneficiario) cbBeneficiario.getSelectedItem();
            Item iSelecionado = (Item) cbItem.getSelectedItem();
            
            if (bSelecionado == null || iSelecionado == null) {
                JOptionPane.showMessageDialog(null, "Selecione um beneficiário e um item!");
                return;
            }
            
            // --- AQUI ESTÁ A MUDANÇA PARA HERANÇA ---
            // Criamos especificamente uma Doação Em Análise
            DoacaoEmAnalise novaDoacao = new DoacaoEmAnalise();
            novaDoacao.setBeneficiario(bSelecionado);
            novaDoacao.setItem(iSelecionado);
            // O status "Em Analise" já é automático dentro dessa classe
            
            DoacaoDAO dao = new DoacaoDAO();
            dao.realizarDoacao(novaDoacao); // O DAO aceita porque DoacaoEmAnalise É UMA Doacao
            
            cbItem.removeItem(iSelecionado);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
        }
    }
}