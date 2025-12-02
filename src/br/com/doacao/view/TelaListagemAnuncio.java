package br.com.doacao.view;

import java.awt.EventQueue;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import br.com.doacao.dao.AnuncioDAO;
import br.com.doacao.model.Anuncio;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Color;

public class TelaListagemAnuncio extends JFrame {

  
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    private JTable tabelaAnuncios;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) { e.printStackTrace(); }

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    TelaListagemAnuncio frame = new TelaListagemAnuncio();
                    frame.setVisible(true);
                } catch (Exception e) { e.printStackTrace(); }
            }
        });
    }

    public TelaListagemAnuncio() {
        setTitle("Mural de Anúncios");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 400);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        // Título
        JLabel lblTitulo = new JLabel("Anúncios Publicados");
        lblTitulo.setForeground(new Color(0, 102, 204));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setBounds(10, 11, 564, 30);
        contentPane.add(lblTitulo);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 52, 564, 298);
        contentPane.add(scrollPane);

        tabelaAnuncios = new JTable();
        scrollPane.setViewportView(tabelaAnuncios);
        
        carregarTabela();
    }
    
    private void carregarTabela() {
        try {
            AnuncioDAO dao = new AnuncioDAO();
            List<Anuncio> lista = dao.listarAnuncios();
            
            DefaultTableModel modelo = new DefaultTableModel();
            modelo.addColumn("ID");
            modelo.addColumn("Título");
            modelo.addColumn("Status");
            modelo.addColumn("Localização"); // Adicionei localização que é útil
            
            for (Anuncio a : lista) {
                modelo.addRow(new Object[] {
                    a.getIdAnuncio(),
                    a.getTitulo(),
                    a.getStatusAnuncio(),
                    a.getLocalizacao()
                });
            }
            
            tabelaAnuncios.setModel(modelo);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}