package Dariels;

import java.sql.*;

public class TestDariels {

	// Método main para probar la conexión y los métodos
	public static void main(String[] args) throws SQLException {
		
		TestDariels dao = new TestDariels();
		// Metodo para establecer la conexion con la bases de datos
		dao.setupConnection();
		
		// Metodo para la creacion de la tabla plaza
		dao.crearTablaPlazas();
		
		// Metodo para la creacion de la tabla clientes
		// dao.crearTablaClientes();
		// dao.agregarPlaza(1, 1, true);
		// dao.agregarPlaza(2, 2, false);
	}

	private Connection con;

	// Configura la conexión con la base de datos
	public void setupConnection() {
		String url = "jdbc:mysql://localhost:3306/centre-ciutat";
		String username = "root";
		String password = "";

		try {
			con = DriverManager.getConnection(url, username, password);
			System.out.println("Conexión exitosa");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Crea la tabla de plazas de estacionamiento
	public void crearTablaPlazas() throws SQLException {
		
		Statement stmt = null;
		String sql = "CREATE TABLE plazas_estacionamiento (id INT PRIMARY KEY, numero_plaza INT, disponible BOOLEAN)";
		
		try {
			
			stmt = con.createStatement();
			
			stmt.executeUpdate(sql);
			
			System.out.println("Tabla de plazas de estacionamiento creada correctamente");
			
		} catch (SQLException e) {
			printSQLException(e);
			
		} finally {
			stmt.close();
		}
	}
	
	// Metodo para crear la tabla de clientes
	public void crearTablaCliente() throws SQLException{
		Statement stmt = null;
		String sql = "CREATE TABLE cliente ()";
		
		try {
			
			stmt = con.createStatement();
			stmt.executeUpdate(sql);
			System.out.println("Tabla de clientes crear correctamente!");
			
		} catch (SQLException e) {
			printSQLException(e);
		}finally {
			stmt.close();
		}
	}
	
	
	
	// Metodo de SQLException
	private static void printSQLException(SQLException exception) {

		exception.printStackTrace(System.err);
		System.err.println("SQLState: " + exception.getSQLState()); // getSQLState()
		System.err.println("Error Code: " + exception.getErrorCode()); // getErrorCode()
		System.err.println("Message: " + exception.getMessage()); // getMessage()

		Throwable th = exception.getCause(); // getCause() - Leemos la primera causa

		while (th != null) {
			System.out.println("Cause: " + th); // Imprimimos una causa
			th = th.getCause(); // Leemos otra causa
		}

	}

}
