package br.com.doacao.view;

import java.awt.EventQueue;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import br.com.doacao.dao.ItemDAO;
import br.com.doacao.model.Item;

public class TelaListagemItem extends JFrame {

   
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    private JTable tabelaItens;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    TelaListagemItem frame = new TelaListagemItem();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public TelaListagemItem() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 400);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 11, 560, 339);
        contentPane.add(scrollPane);

        tabelaItens = new JTable();
        scrollPane.setViewportView(tabelaItens);
        
        // Carrega os dados assim que abre a tela
        carregarTabela();
    }
    
    private void carregarTabela() {
        try {
            ItemDAO dao = new ItemDAO();
            List<Item> lista = dao.listarItens();
            
            DefaultTableModel modelo = new DefaultTableModel();
            modelo.addColumn("ID");
            modelo.addColumn("Item");
            modelo.addColumn("Categoria");
            modelo.addColumn("Estado");
            modelo.addColumn("Doador"); 
            
            for (Item i : lista) {
                modelo.addRow(new Object[] {
                    i.getIdItem(),
                    i.getNomeItem(),
                    i.getCategoria(),
                    i.getEstadoConservacao(),
                    i.getDoador().getNome() // Aqui o nome do doador
                });
            }
            
            tabelaItens.setModel(modelo);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}