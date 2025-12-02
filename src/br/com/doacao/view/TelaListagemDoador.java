package br.com.doacao.view;

import java.awt.EventQueue;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import br.com.doacao.dao.DoadorDAO;
import br.com.doacao.model.Doador;

public class TelaListagemDoador extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tabelaDoadores;

	// Método principal para rodar a tela
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaListagemDoador frame = new TelaListagemDoador();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// Construtor da Tela
	public TelaListagemDoador() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 350); // Tamanho da janela
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null); // Layout Absoluto

		// 1. Cria o ScrollPane (A área com barra de rolagem)
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 460, 240); // Posição na tela
		contentPane.add(scrollPane);

		// 2. Cria a Tabela
		tabelaDoadores = new JTable();
		
		// 3. IMPORTANTE: Coloca a tabela DENTRO do ScrollPane
		scrollPane.setViewportView(tabelaDoadores);
		
		// 4. Carrega os dados do banco
		carregarTabela();
	}

	// Método que busca no banco e preenche a tabela
	private void carregarTabela() {
		try {
			// Busca a lista no banco
			DoadorDAO dao = new DoadorDAO();
			List<Doador> lista = dao.listarDoadores();
			
			// Cria o modelo da tabela (Cabeçalhos)
			DefaultTableModel modelo = new DefaultTableModel();
			modelo.addColumn("ID");
			modelo.addColumn("Nome");
			modelo.addColumn("Email");
			modelo.addColumn("Telefone");

			// Adiciona as linhas
			for (Doador d : lista) {
				modelo.addRow(new Object[] {
					d.getIdDoador(),
					d.getNome(),
					d.getEmail(),
					d.getTelefone()
				});
			}
			
			// Aplica o modelo visual na tabela
			tabelaDoadores.setModel(modelo);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
