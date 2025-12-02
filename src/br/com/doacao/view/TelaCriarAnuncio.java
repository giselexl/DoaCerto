package br.com.doacao.view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import br.com.doacao.dao.AnuncioDAO;
import br.com.doacao.dao.ItemDAO;
import br.com.doacao.model.Anuncio;
import br.com.doacao.model.Item;

public class TelaCriarAnuncio extends JFrame {


	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtTitulo;
	private JTextField txtDescricao;
	private JTextField txtLocalizacao;
	private JComboBox<String> cbFrete;
	
	// Componentes para selecionar Itens
	private JComboBox<Item> cbItensDisponiveis;
	private JList<Item> listaVisualItens;
	private DefaultListModel<Item> modeloLista; // O "motor" da lista visual
	
	// Lista real que vai para o banco
	private List<Item> itensParaAnunciar = new ArrayList<>();

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) { e.printStackTrace(); }

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaCriarAnuncio frame = new TelaCriarAnuncio();
					frame.setVisible(true);
				} catch (Exception e) { e.printStackTrace(); }
			}
		});
	}

	public TelaCriarAnuncio() {
		setTitle("Novo Anúncio");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 450); // Tela mais larga
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// --- TÍTULO ---
		JLabel lblHeader = new JLabel("Criar Novo Anúncio");
		lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 18));
		lblHeader.setHorizontalAlignment(SwingConstants.CENTER);
		lblHeader.setBounds(10, 11, 614, 30);
		contentPane.add(lblHeader);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 45, 614, 2);
		contentPane.add(separator);

		// === LADO ESQUERDO: DADOS DO ANÚNCIO ===
		
		JLabel lblTitulo = new JLabel("Título do Anúncio:");
		lblTitulo.setBounds(30, 60, 150, 14);
		contentPane.add(lblTitulo);

		txtTitulo = new JTextField();
		txtTitulo.setBounds(30, 80, 250, 25);
		contentPane.add(txtTitulo);

		JLabel lblDesc = new JLabel("Descrição:");
		lblDesc.setBounds(30, 115, 150, 14);
		contentPane.add(lblDesc);

		txtDescricao = new JTextField();
		txtDescricao.setBounds(30, 135, 250, 25);
		contentPane.add(txtDescricao);
		
		JLabel lblLocal = new JLabel("Localização (Cidade/Bairro):");
		lblLocal.setBounds(30, 170, 200, 14);
		contentPane.add(lblLocal);
		
		txtLocalizacao = new JTextField();
		txtLocalizacao.setBounds(30, 190, 250, 25);
		contentPane.add(txtLocalizacao);
		
		JLabel lblFrete = new JLabel("Opções de Frete:");
		lblFrete.setBounds(30, 225, 150, 14);
		contentPane.add(lblFrete);
		
		cbFrete = new JComboBox<>();
		cbFrete.addItem("Retirada no Local");
		cbFrete.addItem("Entrega a Combinar");
		cbFrete.addItem("Envio por Correio");
		cbFrete.setBounds(30, 245, 250, 25);
		contentPane.add(cbFrete);

		// === LADO DIREITO: SELEÇÃO DE ITENS ===
		
		JLabel lblSelItem = new JLabel("Selecionar Itens para o Anúncio:");
		lblSelItem.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblSelItem.setBounds(320, 60, 250, 14);
		contentPane.add(lblSelItem);
		
		cbItensDisponiveis = new JComboBox<>();
		cbItensDisponiveis.setBounds(320, 80, 200, 25);
		contentPane.add(cbItensDisponiveis);
		
		JButton btnAdicionar = new JButton("+");
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionarItemNaLista();
			}
		});
		btnAdicionar.setBounds(530, 80, 50, 25);
		contentPane.add(btnAdicionar);
		
		// Lista Visual (Onde aparecem os itens adicionados)
		modeloLista = new DefaultListModel<>();
		listaVisualItens = new JList<>(modeloLista);
		listaVisualItens.setBorder(new LineBorder(Color.LIGHT_GRAY));
		
		JScrollPane scrollLista = new JScrollPane(listaVisualItens);
		scrollLista.setBounds(320, 115, 260, 155);
		contentPane.add(scrollLista);
		
		JLabel lblInfo = new JLabel("* Adicione um ou mais itens");
		lblInfo.setFont(new Font("Tahoma", Font.ITALIC, 10));
		lblInfo.setForeground(Color.GRAY);
		lblInfo.setBounds(320, 275, 200, 14);
		contentPane.add(lblInfo);

		// === BOTÃO PUBLICAR ===
		JButton btnPublicar = new JButton("PUBLICAR ANÚNCIO");
		btnPublicar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				publicarAnuncio();
			}
		});
		btnPublicar.setFont(new Font("Segoe UI", Font.BOLD, 14));
		btnPublicar.setBackground(new Color(0, 102, 204));
		btnPublicar.setForeground(Color.WHITE);
		btnPublicar.setBounds(200, 330, 250, 40);
		contentPane.add(btnPublicar);
		
		// Carrega os itens do banco
		carregarItens();
	}
	
	// Busca itens sem anúncio no banco
	private void carregarItens() {
		ItemDAO dao = new ItemDAO();
		List<Item> lista = dao.listarItensDisponiveis();
		for (Item i : lista) {
			cbItensDisponiveis.addItem(i);
		}
	}
	
	// Passa do ComboBox para a Lista Visual
	private void adicionarItemNaLista() {
		Item itemSelecionado = (Item) cbItensDisponiveis.getSelectedItem();
		
		if (itemSelecionado == null) return;
		
		// Adiciona na lista visual (modelo)
		modeloLista.addElement(itemSelecionado);
		
		// Adiciona na lista real (lógica)
		itensParaAnunciar.add(itemSelecionado);
		
		// Remove do combobox para não adicionar duas vezes o mesmo
		cbItensDisponiveis.removeItem(itemSelecionado);
	}
	
	// Grava tudo no banco
	private void publicarAnuncio() {
		if (itensParaAnunciar.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Adicione pelo menos um item ao anúncio!");
			return;
		}
		
		try {
			// 1. Cria o Anuncio
			Anuncio a = new Anuncio();
			a.setTitulo(txtTitulo.getText());
			a.setDescricao(txtDescricao.getText());
			a.setLocalizacao(txtLocalizacao.getText());
			a.setOpcoesFrete((String) cbFrete.getSelectedItem());
			
			// 2. Coloca os itens dentro dele
			a.setItens(itensParaAnunciar);
			
			// 3. DAO salva o anúncio e atualiza os itens
			AnuncioDAO dao = new AnuncioDAO();
			dao.cadastrarAnuncio(a);
			
			// Limpa a tela
			txtTitulo.setText("");
			txtDescricao.setText("");
			modeloLista.clear();
			itensParaAnunciar.clear();
			carregarItens(); // Recarrega o combo
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
		}
	}
}