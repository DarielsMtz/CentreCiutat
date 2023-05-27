package recursos;

import java.sql.*;

public class BBDD {
	private static final String URL = "jdbc:mysql://localhost:3306/centreciutat";
	private static final String DB_NAME = "centreciutat";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "";

	public static void ejectutarMetodos() {
		try {
			crearBaseDeDatos();
			Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			System.out.println("Conexión establecida correctamente!");
			crearTablaUsuarios(con);
			crearTablaVehiculo(con);
			crearTablaPlazas(con);
			crearTablaCliente(con);
			agregarPlazas(con);
			agregarClientesVehiculos(con);

		} catch (SQLException e) {
			printSQLException(e);
		}
	}
	
	// Metodo de SQLException
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
			System.out.println("La base de datos '" + DB_NAME + "' se ha creado correctamente!");
		} catch (SQLException e) {
			printSQLException(e);
		} finally {
			// Cierra el Statement
			stmt.close();
		}
	}

	// Crea la tabla de plazas de estacionamiento
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

			System.out.println("");
			System.out.println("Se ha creado la tabla Vehiculo correctamente!");

		} catch (SQLException e) {
			printSQLException(e);
		} finally {
			stmt.close();
		}
	}

	// Metodo para crear la tabla Clientes
	protected static void crearTablaCliente(Connection con) throws SQLException {
		String createString = "CREATE TABLE IF NOT EXISTS clientes (" + "id_cliente INT PRIMARY KEY AUTO_INCREMENT,"
				+ "nombre VARCHAR(50) NOT NULL," + "apellido VARCHAR(50) NOT NULL," + "dni VARCHAR(50) NOT NULL,"
				+ "direccion VARCHAR(50) NOT NULL," + "cuenta_corriente VARCHAR(50) NOT NULL,"
				+ "id_plaza INT," + "id_vehiculo INT,"
				+ "FOREIGN KEY (id_plaza) REFERENCES plazas(id_plaza),"
				+ "FOREIGN KEY (id_vehiculo) REFERENCES vehiculo(id_vehiculo)" + ")";
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

	// Metodo para crear Usuario
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
			System.out.println("Se ha creado la tabla de Usuarios correctamente!");

		} catch (SQLException e) {
			printSQLException(e);
		} finally {
			stmt.close();
		}

	}

	// Metodo para agregar los usuario
	protected static void agregarUsuarios(Connection con) throws SQLException {

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

	                // Campos de la tabla plazas
	                // ID, disponible, tamaño, numero_plaza, precio
	                stmt.executeUpdate("INSERT INTO " + DB_NAME + ".plazas (disponible, tamaño, numero_plaza, precio) VALUES "
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

	                System.out.println("");
	                System.out.println("Se han agregado las plazas correctamente!");
	            } else {
	                System.out.println("La tabla plazas no está vacía. No se agregaron las plazas.");
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
	            System.out.println("Las tablas de clientes y vehículos no están vacías. No se realizó ninguna inserción.");
	        }

	        // Cerrar la conexión a la base de datos
	        con.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	




}