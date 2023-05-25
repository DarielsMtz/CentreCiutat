package recursos;
import java.sql.*;


public class BBDD {


	public static void main(String[] args) {

		// Creamos la conexion a la base de datos
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ordenadores", "edib", "edib");
			System.out.println("Conexión establecida correctamente!");
		} catch (SQLException e) {
			printSQLException(e);
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