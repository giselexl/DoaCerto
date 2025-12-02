package br.com.doacao.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class ConnectionFactory {

	public Connection getConnection() {
		try {
			// O "jdbc:mysql" é o protocolo. "localhost" é seu PC. "SistemaDoacoes" é o nome do banco que criamos no SQL.
			return DriverManager.getConnection("jdbc:mysql://localhost:3306/SistemaDoacoes", "root", "Gi29092003");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Erro de conexão: " + e.getMessage());
			throw new RuntimeException(e);
		}
	}


//Cole isso DENTRO da classe ConnectionFactory, antes do último }
public static void main(String[] args) {
 ConnectionFactory factory = new ConnectionFactory();
 factory.getConnection();
 System.out.println("Conexão aberta com sucesso!");
}
}