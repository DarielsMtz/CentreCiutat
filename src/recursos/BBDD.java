package recursos;
import java.sql.*;

public class BBDD {
	private static final String URL = "jdbc:mysql://localhost:3306/centreciutat";
    private static final String DB_NAME = "centreciutat";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    
	public static void main(String[] args) {
		try {
			Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			System.out.println("Conexión establecida correctamente!");
			
			crearTablaUsuarios(con, "centreciutat");
			crearTablaVehiculo(con, "centreciutat");
			crearTablaPlazas(con, "centreciutat");
			crearTablaCliente(con, "centreciutat");
			agregarPlazas(con, "centreciutat");
			
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
	// Crea la tabla de plazas de estacionamiento
		public static void crearTablaPlazas(Connection con, String centreciutat) throws SQLException {

			String createString = "CREATE TABLE IF NOT EXISTS plazas (" + "id_plaza INT PRIMARY KEY AUTO_INCREMENT," 
									+ "Disponible BOOLEAN NOT NULL," + "tamaño VARCHAR(50) NOT NULL," + "numero_plaza VARCHAR(10) NOT NULL," + " precio DOUBLE NOT NULL" + ")";
			
			
			Statement stmt = null;
			try {
				// Creamos un Statement
				stmt = con.createStatement();
				// Ejecutamos las consultas
				stmt.executeUpdate(createString);
				
				System.out.println("");
				System.out.println("Se ha creado la tabla Plaza, correctamente!");
				System.out.println("Se añadió la foreign key id_cliente_alquiler");
				

			} catch (SQLException e) {
				printSQLException(e);
			} finally {
				stmt.close();
			}
		}
		
		// Metodo para crear la tabla vehiculo
				private static void crearTablaVehiculo(Connection con, String centreciutat) throws SQLException {
					String createString = "CREATE TABLE IF NOT EXISTS vehiculo (" + "id_vehiculo INT PRIMARY KEY AUTO_INCREMENT," + 
											"marca VARCHAR(50) NOT NULL," + "modelo VARCHAR(50) NOT NULL," + "color VARCHAR(50) NOT NULL," + "motor VARCHAR(50) NOT NULL," + "matricula VARCHAR(50) NOT NULL," + "tipo ENUM('coche', 'moto', 'furgoneta') NOT NULL" + ")";

					Statement stmt = null;
					try {
						// Creamos un Statement
						stmt = con.createStatement();
						// Ejecuatmos la consulta
						stmt.executeUpdate(createString);

						System.out.println("");
						System.out.println("Se ha creado la tabla Vehiculo correctamente!");

					} catch (SQLException e) {
						printSQLException(e);
					} finally {
						stmt.close();
					}
				}

		// Metodo para crear la tabla Clientes
		private static void crearTablaCliente(Connection con, String centreciutat) throws SQLException {
			String createString = "CREATE TABLE IF NOT EXISTS clientes ("
		            + "id_cliente INT PRIMARY KEY AUTO_INCREMENT,"
		            + "nombre VARCHAR(50) NOT NULL,"
		            + "apellido VARCHAR(50) NOT NULL,"
		            + "dni VARCHAR(50) NOT NULL,"
		            + "direccion VARCHAR(50) NOT NULL,"
		            + "cuenta_corriente VARCHAR(50) NOT NULL,"
		            + "id_plaza INT,"
		            + "id_vehiculo INT,"
		            + "FOREIGN KEY (id_plaza) REFERENCES plazas(id_plaza),"
		            + "FOREIGN KEY (id_vehiculo) REFERENCES vehiculo(id_vehiculo)"
		            + ")";
			Statement stmt = null;

			try {
				// Creamos un Statement
				stmt = con.createStatement();
				// ejecutamos la consulta
				stmt.executeUpdate(createString);

				System.out.println("");
				System.out.println("Se ha creado la tabla Clientes correctamente!!");
				System.out.println("Se añadió la foreign key id_vehiculo");
				System.out.println("Se añadió la foreign key id_plaza");
			} catch (SQLException e) {
				printSQLException(e);
			} finally {
				stmt.close();
			}
		}

		

		// Método para obtener todas las plazas de estacionamiento
		private static void obtenerPlazas(Connection con, String centreciutat) throws SQLException {

			String query = "SELECT * FROM " + centreciutat + ".plazas_estacionamiento";

			Statement stmt = null;
			ResultSet rs = null;

			try {
				// Creamos un Statement
				stmt = con.createStatement();

				// Ejecutamos la consulta
				rs = stmt.executeQuery(query);

				// Iteramos sobre los resultados
				while (rs.next()) {
					int idPlaza = rs.getInt("id_plaza");
					boolean estado = rs.getBoolean("estado");	
					String tamaño = rs.getString("tamaño");
					int numeroPlaza = rs.getInt("numero_plaza");
					double precio = rs.getDouble("precio");

					// Imprimimos los datos de cada plaza
					System.out.println("ID Plaza: " + idPlaza);
					System.out.println("Estado: " + estado);
					System.out.println("Tamaño: " + tamaño);
					System.out.println("Número de plaza: " + numeroPlaza);
					System.out.println("Precio: " + precio);
					System.out.println("---------------------------");
				}

				System.out.println("");
				System.out.println("Consulta de plazas finalizada!");

			} catch (SQLException e) {
				printSQLException(e);
			} finally {

				// Cerramos los recursos
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
			}
		}

		// Metodo para crear Usuario
		private static void crearTablaUsuarios(Connection con, String centreciutat) throws SQLException {

			String createString = "CREATE TABLE IF NOT EXISTS usuarios (" + "id_usuario INT PRIMARY KEY AUTO_INCREMENT,"
					+ "tipo VARCHAR(20) NOT NULL," + "nombre_usuario VARCHAR(50) NOT NULL,"
					+ "contrasena VARCHAR(50) NOT NULL" + ")";

			Statement stmt = null;
			try {
				// Creamos un Statement
				stmt = con.createStatement();

				// ejecutamos la consulta
				stmt.executeUpdate(createString);
				System.out.println("");
				System.out.println("Se ha creado la tabla de Usuarios correctamente!");

			} catch (SQLException e) {
				printSQLException(e);
			} finally {
				stmt.close();
			}

		}

		// Metodo para agregar los usuario
		private static void agregarUsuarios(Connection con, String centreciutat) throws SQLException {

			Statement stmt = null;

			try {
				stmt = con.createStatement();
				// Campos de la tabla usuarios
				// ID, Tipo, Nombre_Usuario, Contraseña
				stmt.executeUpdate("INSERT INTO usuarios (tipo, nombre_usuario, contrasena) VALUES "
						+ "('admin', 'admin123', 'admin123')," // Usuario para administrador
						+ "('usuario', 'usuario123', 'usuario123')," // Usuario para un primer usuario normal
						+ "('usuario', 'usuario456', 'usuario456')"); // Usuario para un segundo usuario normal

				System.out.println("");
				System.out.println("Se han agregado los usuarios correctamente!");

			} catch (SQLException e) {
				printSQLException(e);
			} finally {
				stmt.close();
			}
		}
		
		// Metodo para agregar las plazas
				private static void agregarPlazas(Connection con, String centreciutat) throws SQLException {

					Statement stmt = null;

					try {
						stmt = con.createStatement();
						// Campos de la tabla plazas
						// ID, disponible, tamaño, numero_plaza, precio
						stmt.executeUpdate("INSERT INTO plazas (disponible, tamaño, numero_plaza, precio) VALUES "
								+ "(true, '10m2', 'A20', 71)," // 1 plaza
								+ "(true, '20m2', 'A21', 90)," // 2 plaza
								+ "(true, '30m2', 'A22', 50)," // 3 plaza
								+ "(true, '40m2', 'A23', 67)," // 4 plaza
								+ "(true, '50m2', 'A24', 75)," // 5 plaza
								+ "(false, '60m2', 'A25', 72)," // 6 plaza
								+ "(true, '70m2', 'A26', 62)," // 7 plaza
								+ "(false, '80m2', 'A27', 87)," // 8 plaza
								+ "(true, '90m2', 'A28', 84)," // 9 plaza
								+ "(true, '100m2', 'A29', 91)," // 10 plaza
								+ "(false, '110m2', 'A230', 100)" );// 11 plaza

						System.out.println("");
						System.out.println("Se han agregado las plazas correctamente!");

					} catch (SQLException e) {
						printSQLException(e);
					} finally {
						stmt.close();
					}
				}
		
		
}