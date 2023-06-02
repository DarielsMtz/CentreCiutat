package recursos;

import java.io.*;
import java.sql.*;
import java.util.*;

/**
 * Esta clase proporciona los metoos para llevar acabo la gestion de un alquiler
 * de un plaza de aparcamiento. Se establece una conexion con la base de datos
 * utilizando la URL, nomnre de usuario y contraseña.Primeramente se llevan a
 * cabo las distintas consultas a la base de datos, lo que conlleva realizar
 * manejo de los datos del cliente, vehiculo, plaza de aparcamiento entre las
 * mismas. Si se produce una excepcion de tipo SQLEcecption, se imprime el
 * mensaje de error.
 */
public class Gestion {

	// Variables con los datos de la conexcion a la base datos
	/**
	 * Enlace a la base de datos
	 */
	private static final String DB_URL = "jdbc:mysql://localhost:3306/centreciutat"; // Enlace
	/**
	 * Nombre del usuario para acceder a la base de datos
	 */
	private static final String DB_USER = "root"; // Usuario
	/**
	 * Contraseña para acceder a la base de datos
	 */
	private static final String DB_PASSWORD = ""; // Contraseña

	// Variables para establecer las conexcion con la base de datos
	/**
	 * Variables para establecer las conexcion con la bbdd
	 */
	private static Connection connection;

	// Metodo principal de la clase GESTION
	/**
	 * Este método es el punto de entrada principal del programa. Inicia la
	 * ejecución de los métodos de la base de datos.
	 *
	 * @param args los argumentos de la línea de comandos (no se utilizan en este
	 *             método)
	 */
	public static void main(String[] args) {
		BBDD.ejectutarMetodos();
	}

	/*
	 * CONSULTAS A LA BASE DE DATOS
	 */

	// Metodo para ingresar clientes en su tabla
	/**
	 * Inserta un nuevo cliente en la base de datos.
	 *
	 * @param nombre          el nombre del cliente
	 * @param apellido        el apellido del cliente
	 * @param dni             el DNI del cliente
	 * @param direccion       la dirección del cliente
	 * @param cuentaCorriente el número de cuenta corriente del cliente
	 * @return el identificador generado para el cliente insertado, o -1 si no se
	 *         pudo insertar
	 * @throws SQLException si ocurre algún error al ejecutar la consulta SQL
	 */
	private static int insertarCliente(String nombre, String apellido, String dni, String direccion,
			String cuentaCorriente) throws SQLException {
		String sql = "INSERT INTO clientes (nombre, apellido, dni, direccion, cuenta_corriente) VALUES (?, ?, ?, ?, ?)";
		PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
		statement.setString(1, nombre);
		statement.setString(2, apellido);
		statement.setString(3, dni);
		statement.setString(4, direccion);
		statement.setString(5, cuentaCorriente);
		statement.executeUpdate();

		ResultSet generatedKeys = statement.getGeneratedKeys();
		if (generatedKeys.next()) {
			return generatedKeys.getInt(1);
		} else {
			return -1;
		}
	}

	// Metodo para ingresar vehiculos en su tabla.
	/**
	 * Inserta un vehículo en la base de datos con la información proporcionada por
	 * el teclado.
	 *
	 * @param marca     la marca del vehículo
	 * @param modelo    el modelo del vehículo
	 * @param color     el color del vehículo
	 * @param motor     el tipo de motor del vehículo
	 * @param matricula la matrícula del vehículo
	 * @param tipo      el tipo de vehículo
	 * @return el ID generado para el vehículo insertado, o -1 si no se pudo
	 *         insertar correctamente
	 * @throws SQLException si ocurre algún error al realizar la operación en la
	 *                      base de datos
	 */
	private static int insertarVehiculo(String marca, String modelo, String color, String motor, String matricula,
			String tipo) throws SQLException {
		String sql = "INSERT INTO vehiculo (marca, modelo, color, motor, matricula, tipo) VALUES (?, ?, ?, ?, ?, ?)";
		PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
		statement.setString(1, marca);
		statement.setString(2, modelo);
		statement.setString(3, color);
		statement.setString(4, motor);
		statement.setString(5, matricula);
		statement.setString(6, tipo);
		statement.executeUpdate();

		ResultSet generatedKeys = statement.getGeneratedKeys();
		if (generatedKeys.next()) {
			return generatedKeys.getInt(1);
		} else {
			return -1;
		}
	}

	// Metodo para verificar si las plazas estan disponibles
	/**
	 * Verifica si una plaza de estacionamiento está disponibles.
	 * 
	 * @param numeroPlaza El número de la plaza de estacionamiento a verificar.
	 * @return {@code true} si la plaza de estacionamiento está disponible,
	 *         {@code false} si no lo está.
	 * @throws SQLException Si ocurre un error al ejecutar la consulta SQL.
	 */
	private static boolean verificarPlazaDisponible(String numeroPlaza) throws SQLException {
		String sql = "SELECT disponible FROM plazas WHERE numero_plaza = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, numeroPlaza);
		ResultSet resultSet = statement.executeQuery();

		if (resultSet.next()) {
			return resultSet.getBoolean("disponible");
		} else {
			return false;
		}
	}

	// Metodo para listar todaas las plaza disponibles
	/**
	 * Muestra un listado de todas las plazas disponibles.
	 *
	 * @throws SQLException si ocurre algún error al acceder a la base de datos.
	 */
	private static void mostrarPlazasDisponibles() throws SQLException {
		String sql = "SELECT numero_plaza FROM plazas WHERE disponible = true";
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();

		while (resultSet.next()) {
			String numeroPlaza = resultSet.getString("numero_plaza");
			System.out.println(numeroPlaza);
		}
	}

	// Método para actualizar el estado de disponibilidad de una plaza en la base de
	// datos
	/**
	 * Actualiza el estado de una plaza en la base de datos, dependiendo de los
	 * datos ingresado.
	 * 
	 * @param numeroPlaza El número de la plaza a actualizar.
	 * @param disponible  El estado de disponibilidad de la plaza. True si está
	 *                    disponible, False si está ocupada.
	 * @throws SQLException Si ocurre algún error al ejecutar la consulta SQL.
	 */
	private static void actualizarEstadoPlaza(String numeroPlaza, boolean disponible) throws SQLException {
		String query = "UPDATE plazas SET Disponible = ? WHERE numero_plaza = ?";
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setBoolean(1, disponible);
			pstmt.setString(2, numeroPlaza);
			pstmt.executeUpdate();
		}
	}

	// Método para vincular un cliente, un vehículo y una plaza en la base de datos
	/**
	 * Vincula un cliente, un vehículo y una plaza asignando el ID de la plaza y el
	 * ID del vehículo al cliente en la base de datos.
	 * 
	 * @param clienteId   ID del cliente que se desea vincular.
	 * @param vehiculoId  ID del vehículo que se desea vincular.
	 * @param numeroPlaza Número de la plaza a la que se desea vincular el cliente.
	 * @throws SQLException Si ocurre un error al ejecutar la consulta SQL.
	 */
	private static void vincularClienteVehiculoPlaza(int clienteId, int vehiculoId, String numeroPlaza)
			throws SQLException {
		String query = "UPDATE clientes SET id_plaza = (SELECT id_plaza FROM plazas WHERE numero_plaza = ?), id_vehiculo = ? WHERE id_cliente = ?";
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setString(1, numeroPlaza);
			pstmt.setInt(2, vehiculoId);
			pstmt.setInt(3, clienteId);
			pstmt.executeUpdate();
		}
	}

	// Método para actualizar la información del cliente en la base de datos
	/**
	 * Actualiza los datos de un cliente de la tabla Clietes de la base de datos.
	 *
	 * @param clienteId       el ID del cliente a actualizar
	 * @param nombre          el nuevo nombre del cliente
	 * @param apellidos       los nuevos apellidos del cliente
	 * @param dni             el nuevo DNI del cliente
	 * @param direccion       la nueva dirección del cliente
	 * @param cuentaCorriente la nueva cuenta corriente del cliente
	 * @throws SQLException si ocurre algún error durante la actualización en la
	 *                      base de datos
	 */
	private static void actualizarCliente(int clienteId, String nombre, String apellidos, String dni, String direccion,
			String cuentaCorriente) throws SQLException {
		String query = "UPDATE clientes SET nombre = ?, apellido = ?, dni = ?, direccion = ?, cuenta_corriente = ? WHERE id_cliente = ?";
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setString(1, nombre);
			pstmt.setString(2, apellidos);
			pstmt.setString(3, dni);
			pstmt.setString(4, direccion);
			pstmt.setString(5, cuentaCorriente);
			pstmt.setInt(6, clienteId);
			pstmt.executeUpdate();
		}
	}

	// Método para actualizar la información del vehículo en la base de datos
	/**
	 * Actualiza los datos de un vehículo de la tabla Vehiculo en la base de datos.
	 *
	 * @param vehiculoId el identificador del vehículo a actualizar
	 * @param marca      la nueva marca del vehículo
	 * @param modelo     el nuevo modelo del vehículo
	 * @param color      el nuevo color del vehículo
	 * @param motor      el nuevo motor del vehículo
	 * @param matricula  la nueva matrícula del vehículo
	 * @param tipo       el nuevo tipo del vehículo
	 * @throws SQLException si ocurre algún error durante la actualización en la
	 *                      base de datos
	 */
	private static void actualizarVehiculo(int vehiculoId, String marca, String modelo, String color, String motor,
			String matricula, String tipo) throws SQLException {
		String query = "UPDATE vehiculo SET marca = ?, modelo = ?, color = ?, motor = ?, matricula = ?, tipo = ? WHERE id_vehiculo = ?";
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setString(1, marca);
			pstmt.setString(2, modelo);
			pstmt.setString(3, color);
			pstmt.setString(4, motor);
			pstmt.setString(5, matricula);
			pstmt.setString(6, tipo);
			pstmt.setInt(7, vehiculoId);
			pstmt.executeUpdate();
		}
	}

	// Método para mostrar todas las plazas disponibles
	/**
	 * Muestra un listado con todas las plazas disponibles y alquiladas en el
	 * sistema / base de datos.
	 *
	 * @throws SQLException si se produce un error al ejecutar la consulta SQL.
	 */
	private static void mostrarPlazas() throws SQLException {
		String query = "SELECT * FROM plazas";
		try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
			while (rs.next()) {
				String numeroPlaza = rs.getString("numero_plaza");
				boolean disponible = rs.getBoolean("disponible");
				String estado = disponible ? "Disponible" : "Alquilado";
				System.out.println("Plaza: " + numeroPlaza + " " + estado);
			}
		}
	}

	// Método para verificar si una plaza seleccionada existe y está ocupada
	/**
	 * Realiza una verificación de las plaza y mustras si está disponilbe o esta
	 * ocupada.
	 *
	 * @param numeroPlaza el número de la plaza a verificar
	 * @return true si la plaza está ocupada, false si está disponible
	 * @throws SQLException si ocurre algún error al acceder a la base de datos
	 */
	private static boolean verificarPlazaOcupada(String numeroPlaza) throws SQLException {
		String query = "SELECT disponible FROM plazas WHERE numero_plaza = ?";
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setString(1, numeroPlaza);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					boolean disponible = rs.getBoolean("disponible");
					return !disponible;
				}
			}
		}
		return false;
	}

	// Método para obtener el ID del cliente asociado a una plaza
	/**
	 * Devuelve el ID del cliente asociado a una plaza específica.
	 *
	 * @param numeroPlaza el número de la plaza de la cual se desea obtener el ID
	 *                    del cliente.
	 * @return el ID del cliente asociado a la plaza especificada, o -1 si no se
	 *         encuentra ningún cliente.
	 * @throws SQLException si ocurre un error al ejecutar la consulta SQL.
	 */
	private static int obtenerClienteIdPorPlaza(String numeroPlaza) throws SQLException {
		String query = "SELECT id_cliente FROM clientes WHERE id_plaza = (SELECT id_plaza FROM plazas WHERE numero_plaza = ?)";
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setString(1, numeroPlaza);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt("id_cliente");
				}
			}
		}
		return -1;
	}

	// Método para obtener el ID del vehículo asociado a una plaza
	/**
	 * Devuelve el ID del vehículo asociado a una plaza específica.
	 * 
	 * @param numeroPlaza el número de la plaza a buscar
	 * @return el ID del vehículo asociado a la plaza, o -1 si no se encuentra
	 *         ninguna coincidencia
	 * @throws SQLException si se produce un error al ejecutar la consulta SQL
	 */
	private static int obtenerVehiculoIdPorPlaza(String numeroPlaza) throws SQLException {
		String query = "SELECT id_vehiculo FROM clientes WHERE id_plaza = (SELECT id_plaza FROM plazas WHERE numero_plaza = ?)";
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setString(1, numeroPlaza);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt("id_vehiculo");
				}
			}
		}
		return -1;
	}

	// Método para mostrar la información actual del cliente asociado a una plaza
	/**
	 * Muestra la información actual de un cliente asociado a una plaza según el
	 * número de plaza proporcionado.
	 *
	 * @param numeroPlaza el número de plaza para buscar el cliente
	 * @throws SQLException si ocurre algún error al acceder a la base de datos
	 */
	private static void mostrarCliente(String numeroPlaza) throws SQLException {
		String query = "SELECT * FROM clientes c JOIN plazas p ON c.id_plaza = p.id_plaza WHERE p.numero_plaza = ?";
		System.out.println("Información actual del cliente:");
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setString(1, numeroPlaza);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					System.out.println("Nombre: " + rs.getString("nombre"));
					System.out.println("Apellidos: " + rs.getString("apellido"));
					System.out.println("DNI: " + rs.getString("dni"));
					System.out.println("Dirección: " + rs.getString("direccion"));
					System.out.println("Cuenta corriente: " + rs.getString("cuenta_corriente"));
				} else {
					System.out.println("No se encontró información del cliente asociado a la plaza.");
				}
			}
		}
	}

	// Método para mostrar la información actual del vehículo asociado a una plaza
	/**
	 * 
	 * Muestra la información actual del vehículo asociado a una plaza específica.
	 * 
	 * @param numeroPlaza el número de la plaza para la cual se desea obtener
	 *                    información del vehículo
	 * @throws SQLException si ocurre un error durante la ejecución de la consulta
	 *                      SQL
	 */
	private static void mostrarVehiculo(String numeroPlaza) throws SQLException {
		String query = "SELECT * FROM vehiculo v JOIN clientes c ON v.id_vehiculo = c.id_vehiculo JOIN plazas p ON c.id_plaza = p.id_plaza WHERE p.numero_plaza = ?";
		System.out.println("Información actual del vehículo:");
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setString(1, numeroPlaza);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					System.out.println("Marca: " + rs.getString("v.marca"));
					System.out.println("Modelo: " + rs.getString("v.modelo"));
					System.out.println("Color: " + rs.getString("v.color"));
					System.out.println("Motor: " + rs.getString("v.motor"));
					System.out.println("Matrícula: " + rs.getString("v.matricula"));
					System.out.println("Tipo de vehículo: " + rs.getString("v.tipo"));
				} else {
					System.out.println("No se encontró información del vehículo asociado a la plaza.");
				}
			}
		}
	}

	// Método para eliminar los registros de cliente y vehículo asociados a una
	// plaza
	/**
	 * Elimina los registros asociados a una plaza en la base de datos. Este metodo
	 * elimina tanto el registro de clientes como el registro de vehículo asociados
	 * a la plaza especificada. Si no se encuentra un vehículo asociado a la plaza,
	 * solo se elimina el registro de clientes.
	 *
	 * @param numeroPlaza el número de plaza a la que se le eliminarán los registros
	 * @throws SQLException si ocurre un error al acceder a la base de datos
	 */
	private static void eliminarRegistrosPlaza(String numeroPlaza) {
		try {
			// Obtener el ID del vehículo asociado a la plaza
			int vehiculoId = obtenerVehiculoIdPorPlaza(numeroPlaza);

			// Eliminar registro de la tabla clientes
			String queryClientes = "DELETE FROM clientes WHERE id_plaza = (SELECT id_plaza FROM plazas WHERE numero_plaza = ?)";
			try (PreparedStatement pstmtClientes = connection.prepareStatement(queryClientes)) {
				pstmtClientes.setString(1, numeroPlaza);
				pstmtClientes.executeUpdate();
			}

			if (vehiculoId != -1) {
				// Eliminar registro de la tabla vehiculo
				String queryVehiculo = "DELETE FROM vehiculo WHERE id_vehiculo = ?";
				try (PreparedStatement pstmtVehiculo = connection.prepareStatement(queryVehiculo)) {
					pstmtVehiculo.setInt(1, vehiculoId);
					pstmtVehiculo.executeUpdate();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Metod para generar un informe en formato .txt
	/**
	 * Guarda un informe como archivo de texto en la ubicación especificada. En el
	 * informe se incluye los alquileres actuales y las plazas disponibles.Los datos
	 * se obtienen de la base de datos y se escriben en el archivo. La conexión a la
	 * base de datos se cierra al finalizar.
	 *
	 * @throws SQLException si se produce un error al acceder a la base de datos.
	 * @throws IOException  si se produce un error al escribir en el archivo.
	 */
	private static void guardarInformeComoTXT() {
		try {
			// Crear un archivo de texto en la ubicación especificada
			String rutaArchivo = "C:\\informes\\informe.txt";
			BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo));

			// Escribir el encabezado del informe en el archivo
			writer.write("Informes de alquileres actuales y plazas disponibles:");
			writer.newLine();
			writer.newLine();

			// Obtener los alquileres actuales y escribirlos en el archivo
			writer.write("Alquileres actuales:");
			writer.newLine();
			writer.newLine();

			Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

			String queryAlquiladas = "SELECT p.numero_plaza, p.precio, c.nombre, c.apellido, v.marca, v.modelo "
					+ "FROM plazas p " + "JOIN clientes c ON p.id_plaza = c.id_plaza "
					+ "JOIN vehiculo v ON c.id_vehiculo = v.id_vehiculo";

			try (Statement stmt = connection.createStatement();
					ResultSet rsAlquiladas = stmt.executeQuery(queryAlquiladas)) {
				while (rsAlquiladas.next()) {
					String numeroPlaza = rsAlquiladas.getString("numero_plaza");
					double precio = rsAlquiladas.getDouble("precio");
					String nombreCliente = rsAlquiladas.getString("nombre");
					String apellidoCliente = rsAlquiladas.getString("apellido");
					String marcaVehiculo = rsAlquiladas.getString("marca");
					String modeloVehiculo = rsAlquiladas.getString("modelo");

					String linea = "Plaza: " + numeroPlaza + ", Precio mensual: " + precio + " euros" + ", Cliente: "
							+ nombreCliente + " " + apellidoCliente + ", Vehículo: " + marcaVehiculo + " "
							+ modeloVehiculo;
					writer.write(linea);
					writer.newLine();
				}
			}

			writer.newLine();
			writer.newLine();

			// Obtener las plazas disponibles y escribirlas en el archivo
			writer.write("Plazas disponibles:");
			writer.newLine();
			writer.newLine();

			String queryDisponibles = "SELECT p.numero_plaza " + "FROM plazas p " + "WHERE p.disponible = true";

			try (Statement stmt = connection.createStatement();
					ResultSet rsDisponibles = stmt.executeQuery(queryDisponibles)) {
				while (rsDisponibles.next()) {
					String numeroPlaza = rsDisponibles.getString("numero_plaza");

					String linea = "Plaza: " + numeroPlaza;
					writer.write(linea);
					writer.newLine();
				}
			}

			// Cerrar la conexión a la base de datos
			connection.close();

			// Cerrar el archivo
			writer.close();

			System.out.println("Informe guardado exitosamente en " + rutaArchivo);
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * METODOS PARA MANIPULARA LAS PLAZAS
	 */

	// Método para alquilar plazas
	/**
	 * Método para alquilar una plaza de estacionamiento.
	 * 
	 * Este método permite al usuario alquilar una plaza de estacionamiento,
	 * solicitando y validando la información del cliente y del vehículo, y
	 * verificando la disponibilidad de las plazas. Una vez que se selecciona una
	 * plaza disponible, se actualiza su estado en la base de datos y se vinculan el
	 * cliente, el vehículo y la plaza.
	 * 
	 * @throws SQLException si ocurre un error al establecer la conexión con la base
	 *                      de datos.
	 */
	public static void alquilarPlaza() {
		try {
			connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			System.out.println("Conexión establecida correctamente!");

			Scanner teclado = new Scanner(System.in);
			String nombre, apellidos, dni, direccion, cuentaCorriente, marca, modelo, color, motor, matricula, tipo,
					numeroPlaza;
			int clienteId, vehiculoId;
			boolean plazaDisponible;

			do {
				System.out.println("Información del cliente:");
				System.out.print("Nombre: ");
				nombre = teclado.nextLine();
			} while (nombre.trim().isEmpty()); // Repetir hasta que se proporcione un nombre válido

			do {
				System.out.print("Apellidos: ");
				apellidos = teclado.nextLine();
			} while (apellidos.trim().isEmpty()); // Repetir hasta que se proporcionen apellidos válidos

			do {
				System.out.print("DNI: ");
				dni = teclado.nextLine();
			} while (dni.trim().isEmpty()); // Repetir hasta que se proporcione un DNI válido

			do {
				System.out.print("Dirección: ");
				direccion = teclado.nextLine();
			} while (direccion.trim().isEmpty()); // Repetir hasta que se proporcione una dirección válida

			do {
				System.out.print("Cuenta corriente: ");
				cuentaCorriente = teclado.nextLine();
			} while (cuentaCorriente.trim().isEmpty()); // Repetir hasta que se proporcione una cuenta corriente válida

			clienteId = insertarCliente(nombre, apellidos, dni, direccion, cuentaCorriente);
			if (clienteId == -1) {
				System.out.println("No se pudo insertar el cliente en la base de datos.");
				return;
			}

			do {
				System.out.println("\nInformación del coche:");
				System.out.print("Marca: ");
				marca = teclado.nextLine();
			} while (marca.trim().isEmpty()); // Repetir hasta que se proporcione una marca válida

			do {
				System.out.print("Modelo: ");
				modelo = teclado.nextLine();
			} while (modelo.trim().isEmpty()); // Repetir hasta que se proporcione un modelo válido

			do {
				System.out.print("Color: ");
				color = teclado.nextLine();
			} while (color.trim().isEmpty()); // Repetir hasta que se proporcione un color válido

			do {
				System.out.print("Motor: ");
				motor = teclado.nextLine();
			} while (motor.trim().isEmpty()); // Repetir hasta que se proporcione un motor válido

			do {
				System.out.print("Matrícula: ");
				matricula = teclado.nextLine();
			} while (matricula.trim().isEmpty()); // Repetir hasta que se proporcione una matrícula válida

			do {
				System.out.print("Tipo de vehículo (coche, moto, furgoneta): ");
				tipo = teclado.nextLine();
			} while (tipo.trim().isEmpty()); // Repetir hasta que se proporcione un tipo de vehículo válido
			tipo = tipo.toLowerCase();
			vehiculoId = insertarVehiculo(marca, modelo, color, motor, matricula, tipo.toLowerCase());
			if (vehiculoId == -1) {
				System.out.println("No se pudo insertar el vehículo en la base de datos.");
				return;
			}

			System.out.println("Plazas de estacionamiento disponibles:");
			mostrarPlazasDisponibles();

			do {
				System.out.print("Seleccione el número de la plaza de estacionamiento: ");
				numeroPlaza = teclado.nextLine().toUpperCase();
				plazaDisponible = verificarPlazaDisponible(numeroPlaza);
				if (!plazaDisponible) {
					System.out.println("La plaza seleccionada no está disponible.");
				}
			} while (!plazaDisponible); // Repetir hasta que se seleccione una plaza disponible

			// Actualizar estado de disponibilidad de la plaza en la base de datos
			actualizarEstadoPlaza(numeroPlaza, false);

			// Vincular cliente, vehículo y plaza en la base de datos
			vincularClienteVehiculoPlaza(clienteId, vehiculoId, numeroPlaza);

			System.out.println("\n¡Plaza alquilada con éxito!");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	// Método para editar plazas
	/**
	 * Este método permite editar la información de una plaza de estacionamiento. El
	 * usuario selecciona el número de la plaza a editar, se verifica que exista y
	 * esté ocupada. A continuación, se solicita la nueva información del cliente
	 * asociado a la plaza y se actualiza en la base de datos. Luego, se solicita la
	 * nueva información del vehículo asociado a la plaza y se actualiza en la base
	 * de datos. Finalmente, se muestra un mensaje indicando que la plaza ha sido
	 * editada exitosamente.
	 * 
	 * @throws SQLException si ocurre algún error al acceder a la base de datos.
	 */
	public static void editarPlaza() {
		try {
			connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			System.out.println("Conexión establecida correctamente!");

			Scanner teclado = new Scanner(System.in);

			System.out.println("Plazas de estacionamiento:");
			mostrarPlazas(); // Mostrar todas las plazas disponibles

			String numeroPlaza;
			do {
				System.out.print("Seleccione el número de la plaza que desea editar: ");
				numeroPlaza = teclado.nextLine().toUpperCase();

				// Verificar si la plaza seleccionada existe y está ocupada
				if (!verificarPlazaOcupada(numeroPlaza)) {
					System.out.println("La plaza seleccionada no existe o no está ocupada. Intente nuevamente.");
				}
			} while (!verificarPlazaOcupada(numeroPlaza));

			// Obtener la información actual del cliente y vehículo asociados a la plaza
			int clienteId = obtenerClienteIdPorPlaza(numeroPlaza);
			int vehiculoId = obtenerVehiculoIdPorPlaza(numeroPlaza);

			mostrarCliente(numeroPlaza);

			mostrarVehiculo(numeroPlaza);
			System.out.println("clienteId: " + clienteId);
			System.out.println("vehiculoId: " + vehiculoId);

			System.out.println("\nIngrese la nueva información del cliente:");

			String nombre;
			do {
				System.out.print("Nombre: ");
				nombre = teclado.nextLine();
			} while (nombre.isEmpty());

			String apellidos;
			do {
				System.out.print("Apellidos: ");
				apellidos = teclado.nextLine();
			} while (apellidos.isEmpty());

			String dni;
			do {
				System.out.print("DNI: ");
				dni = teclado.nextLine();
			} while (dni.isEmpty());

			String direccion;
			do {
				System.out.print("Dirección: ");
				direccion = teclado.nextLine();
			} while (direccion.isEmpty());

			String cuentaCorriente;
			do {
				System.out.print("Cuenta corriente: ");
				cuentaCorriente = teclado.nextLine();
			} while (cuentaCorriente.isEmpty());

			// Actualizar la información del cliente en la base de datos
			actualizarCliente(clienteId, nombre, apellidos, dni, direccion, cuentaCorriente);

			System.out.println("\nIngrese la nueva información del vehículo:");

			String marca;
			do {
				System.out.print("Marca: ");
				marca = teclado.nextLine();
			} while (marca.isEmpty());

			String modelo;
			do {
				System.out.print("Modelo: ");
				modelo = teclado.nextLine();
			} while (modelo.isEmpty());

			String color;
			do {
				System.out.print("Color: ");
				color = teclado.nextLine();
			} while (color.isEmpty());

			String motor;
			do {
				System.out.print("Motor: ");
				motor = teclado.nextLine();
			} while (motor.isEmpty());

			String matricula;
			do {
				System.out.print("Matrícula: ");
				matricula = teclado.nextLine();
			} while (matricula.isEmpty());

			String tipo;
			do {
				System.out.print("Tipo de vehículo (coche, moto, furgoneta): ");
				tipo = teclado.nextLine();
			} while (tipo.isEmpty());

			// Actualizar la información del vehículo en la base de datos
			actualizarVehiculo(vehiculoId, marca, modelo, color, motor, matricula, tipo.toLowerCase());

			System.out.println("\n¡Plaza editada con éxito!");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	// Método para eliminar los datos asociados a una plaza alquilada
	/**
	 * Este método elimina datos de la base de datos. Recorre las plazas que no
	 * están disponibles y permite al usuario seleccionar una plaza para eliminar.
	 * Después de eliminar la plaza seleccionada, actualiza el estado de la plaza
	 * disponible. Si no hay plazas alquiladas, muestra un mensaje indicando que no
	 * hay plazas alquiladas.
	 * 
	 * @throws SQLException si ocurre algún error durante la conexión a la base de
	 *                      datos o la ejecución de consultas.
	 */
	public static void eliminarDatos() {
		try {
			// Establecer conexión a la base de datos
			connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

			String query = "SELECT p.numero_plaza FROM plazas p WHERE p.disponible = false";

			try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
				if (rs.next()) {
					System.out.println("Plazas alquiladas:");
					do {
						String numeroPlaza = rs.getString("numero_plaza");
						System.out.println("Plaza: " + numeroPlaza);
					} while (rs.next());

					Scanner teclado = new Scanner(System.in);
					String numeroPlaza;
					do {
						System.out.print("Seleccione el número de la plaza que desea eliminar: ");
						numeroPlaza = teclado.nextLine().toUpperCase();
					} while (numeroPlaza.isEmpty());

					eliminarRegistrosPlaza(numeroPlaza);
					actualizarEstadoPlaza(numeroPlaza, true);

					System.out.println("Plaza eliminada y marcada como disponible correctamente.");
				} else {
					System.out.println("No hay plazas alquiladas.");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Método para listar plazas de aparcamentos alquiladas
	/**
	 * Método para listar las plazas de aparcamiento alquiladas y disponibles. Se
	 * establece una conexión a la base de datos y se ejecutan consultas SQL para
	 * obtener la información de las plazas alquiladas y las plazas disponibles.
	 * Luego se muestra por consola el listado de las plazas alquiladas, incluyendo
	 * el número de plaza, el precio mensual, el nombre y apellido del cliente, así
	 * como la marca y modelo del vehículo. A continuación, se muestra el listado de
	 * las plazas disponibles. Finalmente, se solicita al usuario si desea guardar
	 * el informe como un archivo TXT. En caso afirmativo, se invoca el método
	 * "guardarInformeComoTXT()".
	 * 
	 * @throws SQLException si ocurre un error durante la conexión a la base de
	 *                      datos o la ejecución de las consultas.
	 */
	public static void listarPlazas() {
		try {
			// Establecer la conexión a la base de datos
			Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

			String queryAlquiladas = "SELECT p.numero_plaza, p.precio, c.nombre, c.apellido, v.marca, v.modelo "
					+ "FROM plazas p " + "JOIN clientes c ON p.id_plaza = c.id_plaza "
					+ "JOIN vehiculo v ON c.id_vehiculo = v.id_vehiculo";

			String queryDisponibles = "SELECT p.numero_plaza " + "FROM plazas p " + "WHERE p.disponible = true";

			try (Statement stmt = connection.createStatement()) {
				System.out.println("Listado de alquileres actuales:");
				try (ResultSet rsAlquiladas = stmt.executeQuery(queryAlquiladas)) {
					while (rsAlquiladas.next()) {
						String numeroPlaza = rsAlquiladas.getString("numero_plaza");
						double precio = rsAlquiladas.getDouble("precio");
						String nombreCliente = rsAlquiladas.getString("nombre");
						String apellidoCliente = rsAlquiladas.getString("apellido");
						String marcaVehiculo = rsAlquiladas.getString("marca");
						String modeloVehiculo = rsAlquiladas.getString("modelo");

						System.out.println("Plaza: " + numeroPlaza);
						System.out.println("Precio mensual: " + precio + " euros");
						System.out.println("Cliente: " + nombreCliente + " " + apellidoCliente);
						System.out.println("Vehículo: " + marcaVehiculo + " " + modeloVehiculo);
						System.out.println("-------------------------------------------");
					}
				}

				System.out.println("Listado de plazas disponibles:");
				try (ResultSet rsDisponibles = stmt.executeQuery(queryDisponibles)) {
					while (rsDisponibles.next()) {
						String numeroPlaza = rsDisponibles.getString("numero_plaza");

						System.out.println("Plaza: " + numeroPlaza);
						System.out.println("-------------------------------------------");
					}
				}
			}

			// Cerrar la conexión a la base de datos
			connection.close();

			// Solicitar al usuario si desea guardar el informe como un archivo TXT
			String respuesta;
			do {
				System.out.println("¿Desea guardar el informe como un archivo TXT? (Y/N)");
				Scanner scanner = new Scanner(System.in);
				respuesta = scanner.nextLine().trim().toUpperCase();
			} while (respuesta.isEmpty());

			if (respuesta.equals("Y")) {
				guardarInformeComoTXT();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
