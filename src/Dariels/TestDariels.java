package Dariels;

import java.sql.*;

public class TestDariels {

	// Método main para probar la conexión y los métodos
	public static void main(String[] args) throws SQLException {

		// Creamos la conexion a la base de datos
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/centreciutat", "root", "");
		System.out.println("Conexcion establecida correctamente!");

		// Metodo para la creacion de la tabla plaza
		// crearTablaPlazas(con, "centreciutat");

		// Metodo para la creacion de la tabla clientes
		// crearTablaCliente();

		// dao.agregarPlaza(1, 1, true);
		// dao.agregarPlaza(2, 2, false);
	}

	// Crea la tabla de plazas de estacionamiento
	public static void crearTablaPlazas(Connection con, String centreciutat) throws SQLException {

		String createString = "CREATE TABLE " + centreciutat + ".plazas_estacionamiento (" + "id_plaza INT PRIMARY KEY,"
				+ "estado BOOLEAN," + "tamaño VARCAHR(50)," + "numero_plaza INT," + " precio DOUBLE" + ")";

		Statement stmt = null;
		
		try {
			// Creamos un Statement
			stmt = con.createStatement();
			// Ejecutamos la consulta
			stmt.executeUpdate(createString);
			
			System.out.println("");
			System.out.println("Se ha creado la tabla Plaza Estacionamientos, correctamente!");
			
		} catch (SQLException e) {
			printSQLException(e);
		} finally {
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
