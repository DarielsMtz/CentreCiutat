package recursos;

import java.sql.*;

/**
 * Esta clase proporciona métodos para configurar y llenar una base de datos. Se
 * establece una conexión con la base de datos utilizando la URL, nombre de
 * usuario y contraseña proporcionados. A continuación, se crean las tablas
 * necesarias en la base de datos y se agregan datos a las tablas
 * correspondientes. Si se produce una excepción de tipo SQLException, se
 * imprime el mensaje de error.
 */
public class BBDD {

	// Variables con los datos de la conexcion a la base datos
	/**
	 * Enlace a la base de datos
	 */
	private static final String URL = "jdbc:mysql://localhost:3306/centreciutat"; // Enlace
	/**
	 * Nombre de la base de datos
	 */
	private static final String DB_NAME = "centreciutat"; // Nombre de la BBDD
	/**
	 * Nombre del usuario para acceder a la base de datos
	 */
	private static final String USERNAME = "root"; // Usuario
	/**
	 * Contraseña para acceder a la base de datos
	 */
	private static final String PASSWORD = ""; // Contraseña

	// Metodo que agrupa todos los metodos y los ejecuta simultaneamente
	/**
	 * Ejecuta una serie de métodos para configurar y llenar una base de datos. Se
	 * establece una conexión con la base de datos utilizando la URL, nombre de
	 * usuario y contraseña proporcionados. A continuación, se crean las tablas
	 * necesarias en la base de datos y se agregan datos a las tablas
	 * correspondientes. Si se produce una excepción de tipo SQLException, se
	 * imprime el mensaje de error.
	 */
	public static void ejectutarMetodos() {
		try {

			crearBaseDeDatos();
			Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			System.out.println("Conexión establecida correctamente!");

			crearTablaUsuarios(con);
			crearTablaVehiculo(con);
			crearTablaPlazas(con);
			crearTablaCliente(con);
			agregarUsuarios(con);
			agregarPlazas(con);
			agregarClientesVehiculos(con);

		} catch (SQLException e) {
			printSQLException(e);
		}
	}

	// Metodo de SQLException
	/**
	 * Imprime una excepción de tipo SQLException junto con su información
	 * relacionada.
	 * 
	 * @param exception La excepción SQLException a imprimir.
	 */
	protected static void printSQLException(SQLException exception) {

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

	// Metodo para crea la base de datos
	/**
	 * Crea una base de datos utilizando los detalles de conexión proporcionados. Si
	 * la base de datos ya existe, no se realiza ninguna acción.
	 * 
	 * @throws SQLException si ocurre un error durante la conexión o la ejecución de
	 *                      la consulta
	 */
	public static void crearBaseDeDatos() throws SQLException {
		// Establece los detalles de conexión a la base de datos
		String url = "jdbc:mysql://localhost:3306/";
		String username = "root";
		String password = "";

		// Crea la conexión
		Connection con = DriverManager.getConnection(url, username, password);

		// Crea la sentencia para crear la base de datos
		String createDatabaseQuery = "CREATE DATABASE IF NOT EXISTS " + DB_NAME;

		Statement stmt = null;
		try {
			// Crea un Statement
			stmt = con.createStatement();
			// Ejecuta la consulta para crear la base de datos
			stmt.executeUpdate(createDatabaseQuery);
			// System.out.println("La base de datos '" + DB_NAME + "' se ha creado
			// correctamente!");
		} catch (SQLException e) {
			printSQLException(e);
		} finally {
			// Cierra el Statement
			stmt.close();
		}
	}

	// Crea la tabla de plazas de estacionamiento
	/**
	 * Crea una tabla llamada "plazas" en la base de datos especificada. Si la tabla
	 * ya existe, no se realiza ninguna acción. La tabla tiene los siguientes
	 * campos: - id_plaza: identificador de la plaza (clave primaria,
	 * autoincremental) - Disponible: indica si la plaza está disponible o no
	 * (booleano, no nulo) - tamaño: tamaño de la plaza (cadena de caracteres, no
	 * nulo) - numero_plaza: número de la plaza (cadena de caracteres, no nulo) -
	 * precio: precio de la plaza (doble, no nulo)
	 *
	 * @param con la conexión a la base de datos
	 * @throws SQLException si ocurre un error durante la creación de la tabla
	 */
	public static void crearTablaPlazas(Connection con) throws SQLException {

		String createString = "CREATE TABLE IF NOT EXISTS plazas (" + "id_plaza INT PRIMARY KEY AUTO_INCREMENT,"
				+ "Disponible BOOLEAN NOT NULL," + "tamaño VARCHAR(50) NOT NULL," + "numero_plaza VARCHAR(10) NOT NULL,"
				+ " precio DOUBLE NOT NULL" + ")";

		Statement stmt = null;
		try {
			// Creamos un Statement
			stmt = con.createStatement();
			// Ejecutamos las consultas
			stmt.executeUpdate(createString);

			/*
			 * System.out.println("");
			 * System.out.println("Se ha creado la tabla Plaza, correctamente!");
			 * System.out.println("Se añadió la foreign key id_cliente_alquiler");
			 */

		} catch (SQLException e) {
			printSQLException(e);
		} finally {
			stmt.close();
		}
	}

	// Metodo para crear la tabla vehiculo
	/**
	 * Crea una tabla llamada "vehiculo" en la base de datos especificada con las
	 * distintas columnas correspondiente a los atributos de un vehículo. Si la
	 * tabla ya existe, no se realiza ninguna acción.
	 * 
	 * @param con la conexión a la base de datos
	 * @throws SQLException si ocurre algún error al ejecutar la consulta SQL
	 */
	protected static void crearTablaVehiculo(Connection con) throws SQLException {

		String createString = "CREATE TABLE IF NOT EXISTS vehiculo (" + "id_vehiculo INT PRIMARY KEY AUTO_INCREMENT,"
				+ "marca VARCHAR(50) NOT NULL," + "modelo VARCHAR(50) NOT NULL," + "color VARCHAR(50) NOT NULL,"
				+ "motor VARCHAR(50) NOT NULL," + "matricula VARCHAR(50) NOT NULL,"
				+ "tipo ENUM('coche', 'moto', 'furgoneta') NOT NULL" + ")";

		Statement stmt = null;
		try {
			// Creamos un Statement
			stmt = con.createStatement();
			// Ejecuatmos la consulta
			stmt.executeUpdate(createString);

			/*
			 * System.out.println("");
			 * System.out.println("Se ha creado la tabla Vehiculo correctamente!");
			 */

		} catch (SQLException e) {
			printSQLException(e);
		} finally {
			stmt.close();
		}
	}

	// Metodo para crear la tabla Clientes
	/**
	 * 
	 * Crea una tabla "clientes" en la base de campos corespondientes a los datos de
	 * los clientes *
	 * 
	 * @param con la conexión a la base de datos
	 * @throws SQLException si ocurre un error al ejecutar la consulta SQL
	 */
	protected static void crearTablaCliente(Connection con) throws SQLException {
		String createString = "CREATE TABLE IF NOT EXISTS clientes (" + "id_cliente INT PRIMARY KEY AUTO_INCREMENT,"
				+ "nombre VARCHAR(50) NOT NULL," + "apellido VARCHAR(50) NOT NULL," + "dni VARCHAR(50) NOT NULL,"
				+ "direccion VARCHAR(50) NOT NULL," + "cuenta_corriente VARCHAR(50) NOT NULL," + "id_plaza INT,"
				+ "id_vehiculo INT," + "FOREIGN KEY (id_plaza) REFERENCES plazas(id_plaza),"
				+ "FOREIGN KEY (id_vehiculo) REFERENCES vehiculo(id_vehiculo)" + ")";
		Statement stmt = null;

		try {
			// Creamos un Statement
			stmt = con.createStatement();
			// ejecutamos la consulta
			stmt.executeUpdate(createString);
			/*
			 * System.out.println("");
			 * System.out.println("Se ha creado la tabla Clientes correctamente!!");
			 * System.out.println("Se añadió la foreign key id_vehiculo");
			 * System.out.println("Se añadió la foreign key id_plaza");
			 */
		} catch (SQLException e) {
			printSQLException(e);
		} finally {
			stmt.close();
		}
	}

	// Metodo para crear Usuario
	/**
	 * Crea una tabla llamada "usuarios" en la base de datos con las columnas:
	 * id_usuario, tipo, nombre_usuario y contrasena. Si la tabla ya existe, no se
	 * realiza ninguna acción.
	 *
	 * @param con La conexión a la base de datos.
	 * @throws SQLException Si ocurre algún error al ejecutar la consulta SQL.
	 */
	protected static void crearTablaUsuarios(Connection con) throws SQLException {

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
			// System.out.println("Se ha creado la tabla de Usuarios correctamente!");

		} catch (SQLException e) {
			printSQLException(e);
		} finally {
			stmt.close();
		}

	}

	// Metodo para agregar los usuario
	/**
	 * 
	 * Agrega usuarios a la base de datos, estableciendo por defecto el tipo, el
	 * usuario y la contraseña
	 * 
	 * @param con la conexión a la base de datos
	 * 
	 * @throws SQLException si ocurre un error al acceder a la base de datos
	 */
	protected static void agregarUsuarios(Connection con) throws SQLException {

		String insertString = "INSERT IGNORE INTO usuarios (tipo, nombre_usuario, contrasena) VALUES (?, ?, ?)";

		// Crear usuarios admin
		agregarUsuarioValidado(con, insertString, "admin", "admin1", "admin1");
		agregarUsuarioValidado(con, insertString, "admin", "admin2", "admin2");
		agregarUsuarioValidado(con, insertString, "admin", "admin3", "admin3");

		// Crear usuarios normales
		agregarUsuarioValidado(con, insertString, "usuario", "usuario1", "usuario1");
		agregarUsuarioValidado(con, insertString, "usuario", "usuario2", "usuario2");
		agregarUsuarioValidado(con, insertString, "usuario", "usuario3", "usuario3");

		// System.out.println("Usuarios agregados correctamente!");
	}

	// Metodo para la validad de exitenca de los usuarios
	/**
	 * Agrega un usuario validado a la tabla usuarios de la base de datos.
	 * 
	 * @param con           la conexión a la base de datos
	 * @param insertString  la cadena SQL para insertar el usuario en la base de
	 *                      datos
	 * @param tipo          el tipo de usuario
	 * @param nombreUsuario el nombre de usuario a agregar
	 * @param contrasena    la contraseña del usuario
	 * @throws SQLException si ocurre un error al ejecutar la consulta SQL
	 */
	private static void agregarUsuarioValidado(Connection con, String insertString, String tipo, String nombreUsuario,
			String contrasena) throws SQLException {
		// Verificar si el usuario ya existe en la base de datos
		String selectString = "SELECT COUNT(*) FROM usuarios WHERE nombre_usuario = ?";
		try (PreparedStatement selectStmt = con.prepareStatement(selectString)) {
			selectStmt.setString(1, nombreUsuario);
			try (ResultSet resultSet = selectStmt.executeQuery()) {
				resultSet.next();
				int count = resultSet.getInt(1);
				if (count == 0) {
					// El usuario no existe, realizar la inserción
					try (PreparedStatement insertStmt = con.prepareStatement(insertString)) {
						insertStmt.setString(1, tipo);
						insertStmt.setString(2, nombreUsuario);
						insertStmt.setString(3, contrasena);
						insertStmt.executeUpdate();
					}
				} else {
					// System.out.println("El usuario " + nombreUsuario + " ya existe en la base de
					// datos. No se agregará de nuevo.");
				}
			}
		}
	}

	// Metodo para agregar las plazas
	/**
	 * 
	 * Este método agrega plazas a la base de datos. Se agrega por defecto la
	 * disponibilida de las plazas, su tamaño, su numero de indentificación y su
	 * precio.
	 * 
	 * @param con la conexión a la base de datos
	 * @throws SQLException si ocurre un error al interactuar con la base de datos
	 */
	protected static void agregarPlazas(Connection con) throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			// Consulta para contar los registros en la tabla plazas
			String countQuery = "SELECT COUNT(*) AS count FROM " + DB_NAME + ".plazas";
			rs = stmt.executeQuery(countQuery);
			if (rs.next()) {
				int count = rs.getInt("count");
				if (count == 0) {
					// La tabla plazas está vacía, proceder a insertar los registros
					stmt.executeUpdate(
							"INSERT INTO " + DB_NAME + ".plazas (disponible, tamaño, numero_plaza, precio) VALUES "
									+ "(true, '10m2', '1A21', 80)," // 1 plaza
									+ "(true, '10m2', '1A22', 80)," // 2 plaza
									+ "(true, '10m2', '1A23', 80)," // 3 plaza
									+ "(true, '10m2', '1A24', 80)," // 4 plaza
									+ "(true, '10m2', '1A25', 80)," // 5 plaza
									+ "(true, '10m2', '1A26', 80)," // 6 plaza
									+ "(true, '10m2', '1A27', 80)," // 7 plaza
									+ "(true, '10m2', '1A28', 80)," // 8 plaza
									+ "(true, '10m2', '1A29', 80)," // 9 plaza
									+ "(true, '10m2', '1A30', 80)" // 10 plaza
					);
					// System.out.println("");
					// System.out.println("Se han agregado las plazas correctamente!");
				} else {
					// System.out.println("La tabla plazas no está vacía. No se agregaron las
					// plazas.");
				}
			}
		} catch (SQLException e) {
			printSQLException(e);
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}
	}

	// Metodo para agregar vehiculos a los clientes
	/**
	 * Este método agrega clientes y vehículos a la base de datos. Para ello,
	 * verifica si las tablas de clientes y vehículos están vacías. Si ambas están
	 * vacías, se insertan registros de vehículos y clientes preestablecidos, y se
	 * actualiza el estado de las plazas correspondientes. Si alguna de las tablas
	 * no está vacía, no se realiza ninguna inserción. Al finalizar, se cierra la
	 * conexión a la base de datos.
	 * 
	 * @param con la conexión a la base de datos
	 * @throws SQLException si ocurre algún error al interactuar con la base de
	 *                      datos
	 */
	public static void agregarClientesVehiculos(Connection con) throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			// Verificar si las tablas están vacías
			String queryClientes = "SELECT COUNT(*) FROM clientes";
			ResultSet rsClientes = stmt.executeQuery(queryClientes);
			rsClientes.next();
			int countClientes = rsClientes.getInt(1);
			String queryVehiculos = "SELECT COUNT(*) FROM vehiculo";
			ResultSet rsVehiculos = stmt.executeQuery(queryVehiculos);
			rsVehiculos.next();
			int countVehiculos = rsVehiculos.getInt(1);
			if (countClientes == 0 && countVehiculos == 0) {
				// Insertar vehículos
				String insertVehiculos = "INSERT INTO vehiculo (id_vehiculo, marca, modelo, color, motor, matricula, tipo) VALUES "
						+ "(1, 'Ford', 'Focus', 'Azul', 'Gasolina', '1234ABC', 'coche'), "
						+ "(2, 'Honda', 'CBR600', 'Rojo', 'Gasolina', '5678DEF', 'moto'), "
						+ "(3, 'Renault', 'Kangoo', 'Blanco', 'Diésel', '9012GHI', 'furgoneta')";
				stmt.executeUpdate(insertVehiculos);
				// Insertar clientes
				String insertClientes = "INSERT INTO clientes (id_cliente, id_plaza, id_vehiculo, nombre, apellido, dni, direccion, cuenta_corriente) VALUES "
						+ "(1, (SELECT id_plaza FROM plazas WHERE numero_plaza = '1A25'), 1, 'Juan', 'Pérez', '11111111A', 'Calle Mayor 1', 'ES1234567890123456789012'), "
						+ "(2, (SELECT id_plaza FROM plazas WHERE numero_plaza = '1A27'), 2, 'María', 'García', '22222222B', 'Avenida Libertad 10', 'ES2345678901234567890123'), "
						+ "(3, (SELECT id_plaza FROM plazas WHERE numero_plaza = '1A30'), 3, 'Pedro', 'López', '33333333C', 'Plaza España 5', 'ES3456789012345678901234')";
				stmt.executeUpdate(insertClientes);
				// Actualizar estado de las plazas
				String updatePlazas = "UPDATE plazas SET disponible = false WHERE numero_plaza IN ('1A25', '1A27', '1A30')";
				stmt.executeUpdate(updatePlazas);
				System.out.println("Clientes y vehículos agregados exitosamente.");
			} else {
				// System.out.println("Las tablas de clientes y vehículos no están vacías. No se
				// realizó ninguna inserción.");
			}
			// Cerrar la conexión a la base de datos
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}