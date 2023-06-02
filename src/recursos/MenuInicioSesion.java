package recursos;

import java.sql.*;
import java.util.Scanner;

/**
 * La clase MenuInicioSesion representa la clase de inicio de sesión para el
 * programa. Permite a los usuarios iniciar sesión como administrador o usuario
 * normal. Proporciona un menú de opciones y realiza operaciones relacionadas
 * con la gestión del programa y la interacción con una base de datos.
 */
public class MenuInicioSesion {

	// Variable para establecer las conexcion con la base de datos
	/**
	 * Variblae para establecer la conexion de la bbdd
	 */
	private Connection connection;

	// Metodo principal de la clase
	/**
	 * Este método es el punto de entrada principal del programa. Inicia con un
	 * llamado a la interfas del menu
	 * 
	 * @param args los argumentos de la línea de comandos (no se utilizan en este
	 *             método)
	 */
	public static void main(String[] args) {

		// Se llama al metodo para mostrar por completo el menu
		MenuInicioSesion menu = new MenuInicioSesion();
		menu.mostrarMenu();
	}

	// Metod para verificar la conexcion con la base de datos
	/**
	 * Constructor de la clase MenuInicioSesion. Realiza el llamado a los distintos
	 * métodos relacionados con la gestión del programa. Establece la conexión con
	 * la base de datos.
	 * 
	 * @throws SQLException si se produce un error al conectar con la base de datos.
	 */
	public MenuInicioSesion() {

		// Se hace un llamdo a los distintos metodos relacionado con las gestion del
		// programa
		BBDD.ejectutarMetodos();

		// Establecer conexión con la base de datos
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/centreciutat", "root", "");
			// System.out.println("Se ha establecido la conexión a la BBDD correctamente!");

		} catch (SQLException e) {
			System.out.println("Error al conectar con la base de datos: " + e.getMessage());
			System.exit(0);
		}
	}

	// Método para recoger los datos por teclado
	/**
	 * 
	 * Metodo para llevar acabo el inicio de sesión de un usuario en el sistema.
	 * 
	 * @param tipoUsuario el tipo de usuario que está iniciando sesión
	 * 
	 * @return true si el inicio de sesión es exitoso, false en caso contrario
	 */
	public boolean iniciarSesion(String tipoUsuario) {
		Scanner scanner = new Scanner(System.in);

		// Se hace una petición del Usuario
		System.out.print("Ingrese el usuario: \n");
		String nombreUsuario = scanner.nextLine();

		// Se hace una petición de la contraseña
		System.out.print("Ingrese la contraseña: \n");

		String contrasena = scanner.nextLine();
		try {
			String query = "SELECT COUNT(*) FROM usuarios WHERE tipo = ? AND nombre_usuario = ? AND contrasena = ?";

			// Ejecutamos la consulta
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, tipoUsuario);
			statement.setString(2, nombreUsuario);
			statement.setString(3, contrasena);

			ResultSet resultSet = statement.executeQuery();
			resultSet.next();

			int count = resultSet.getInt(1);

			return count == 1;

		} catch (SQLException e) {
			System.out.println("Error al verificar el inicio de sesión: " + e.getMessage());
		}
		return false;
	}

	// Método que muestra las opciones de inicio de sesión
	/**
	 * 
	 * Muestra el menú de inicio de sesión y permite al usuario seleccionar una
	 * opción. Si el inicio de sesión es exitoso, redirige al usuario al menú
	 * correspondiente según su rol. Si el inicio de sesión falla, muestra un
	 * mensaje de error.
	 */
	public void mostrarMenu() {

		boolean login = false;

		do {
			// Interfaz del menu de inicio de sesion
			System.out.println("");
			System.out.println("================================");
			System.out.println("== Centre Ciutat Parking v0.1 ==");
			System.out.println("================================");
			System.out.println("");
			System.out.println("--------------------------------");
			System.out.println("---     Inicio de Sesión     ---");
			System.out.println("--------------------------------");
			System.out.println("|       1. Administrador       |");
			System.out.println("|       2. Usuario             |");
			System.out.println("|       3. Salir               |");
			System.out.println("--------------------------------");
			System.out.println("");
			System.out.print("- Ingrese una opción:-\n");

			int opcion = obtenerNumero();

			// Opciones de usuarios para inicias sesión
			switch (opcion) {
			case 1:
				login = iniciarSesion("admin");
				if (login) {
					System.out.println("Inicio de sesión exitoso como administrador.");
					// Se llama al metodo de las acciones del usuario admin
					menuAdmin();

				} else {
					System.out.println("Inicio de sesión fallido. Verifique sus credenciales.");
					System.out.println("");
				}
				break;
			case 2:
				login = iniciarSesion("usuario");
				if (login) {
					System.out.println("Inicio de sesión exitoso como usuario normal.");
					// Se llama al metod de las acciones del usuario normal
					menuUsuario();

				} else {
					System.out.println("Inicio de sesión fallido. Verifique sus credenciales.");
					System.out.println("");
				}
				break;
			case 3:
				System.out.println("Saliendo...");
				System.exit(0);
				break;
			default:
				System.out.println("Opción inválida. Intente nuevamente.");
			}
		} while (!login);
	}

	// Método para obtener y validar la opciones
	/**
	 * Método que obtiene un número ingresado por teclado.
	 *
	 * @return el número ingresado por el usuario.
	 */
	private int obtenerNumero() {
		Scanner scanner = new Scanner(System.in);
		// Lee el numero/opción ingresado por teclado
		while (!scanner.hasNextInt()) {
			System.out.print("Ingrese una opción válida, porfavor: ");
			scanner.next();
		}
		return scanner.nextInt();
	}

	// Menu de acciones del usuario Normal
	/**
	 * Muestra el menú de usuario y permite ingresar un DNI o una matrícula para
	 * consultar los datos. Luego llama al método 'consultarDatos' para obtener los
	 * datos correspondientes a su DNI.
	 */
	private static void menuUsuario() {
		// Interfaz del menu
		System.out.println("=================================");
		System.out.println("===== Centre Ciutat Parking =====");
		System.out.println("=================================");
		System.out.println("---------------------------------");
		System.out.println("---    Bienvenido Usuario     ---");
		System.out.println("---------------------------------");
		System.out.println("");

		// Lee el DNI o la matrícula ingresados por teclado
		Scanner scanner = new Scanner(System.in);
		System.out.println("- Ingrese un \"DNI\" o una \"Matrícula\": ");
		String dniMatricula = scanner.nextLine();

		// Llama al método para consultar los datos
		consultarDatos(dniMatricula);
	}

	// Método para consultar los datos relacionados a un DNI o una matrícula
	/**
	 * Metodo que actua cono consulta para los datos de un cliente y su vehículo en
	 * la base de datos.
	 * 
	 * @param dniMatricula El DNI o la matrícula del cliente a consultar.
	 */
	public static void consultarDatos(String dniMatricula) {
		// Establecemos una conexion con la base de datos
		String url = "jdbc:mysql://localhost:3306/centreciutat"; // Enlace
		String usuario = "root"; // Usuario
		String contraseña = ""; // Contraseña

		try (Connection conn = DriverManager.getConnection(url, usuario, contraseña)) {

			// Consulta para obtener los datos relacionados al DNI o la matrícula
			String consulta = "SELECT * FROM clientes "
					+ "JOIN vehiculo ON clientes.id_vehiculo = vehiculo.id_vehiculo "
					+ "JOIN plazas ON clientes.id_plaza = plazas.id_plaza " + "WHERE dni = ? OR matricula = ?";

			PreparedStatement statement = conn.prepareStatement(consulta);
			statement.setString(1, dniMatricula);
			statement.setString(2, dniMatricula);

			ResultSet resultado = statement.executeQuery();

			// Recorre los resultados y muestra los datos
			while (resultado.next()) {
				int idCliente = resultado.getInt("id_cliente");
				String nombre = resultado.getString("nombre");
				String apellido = resultado.getString("apellido");
				String dni = resultado.getString("dni");
				String direccion = resultado.getString("direccion");
				String cuentaCorriente = resultado.getString("cuenta_corriente");
				String marca = resultado.getString("marca");
				String modelo = resultado.getString("modelo");
				String color = resultado.getString("color");
				String motor = resultado.getString("motor");
				String matricula = resultado.getString("matricula");
				String tipo = resultado.getString("tipo");
				String plaza = resultado.getString("id_plaza");

				// Informacion del Cleinte
				System.out.println("");
				System.out.println("----------------------------------");
				System.out.println("---    Información Cliente     ---");
				System.out.println("----------------------------------");
				System.out.println("ID Cliente: " + idCliente);
				System.out.println("Nombre: " + nombre);
				System.out.println("Apellido: " + apellido);
				System.out.println("DNI: " + dni);
				System.out.println("Dirección: " + direccion);
				System.out.println("Cuenta corriente: " + cuentaCorriente);

				// Informacion del vehiculo
				System.out.println("----------------------------------");
				System.out.println("---    Información Vehículo    ---");
				System.out.println("----------------------------------");
				System.out.println("Marca: " + marca);
				System.out.println("Modelo: " + modelo);
				System.out.println("Color: " + color);
				System.out.println("Motor: " + motor);
				System.out.println("Matrícula: " + matricula);
				System.out.println("Tipo: " + tipo);
				System.out.println("Plaza: " + plaza);
				System.out.println("----------------------------------");
				System.out.println("");
			}

			// Cierra los recursos
			resultado.close();
			statement.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Menu de acciones del usuario Admin
	/**
	 * Método que muestra el menú de administrador y permite realizar distintas
	 * acciones. Las opciones disponibles son: alquilar plazas, editar plazas,
	 * eliminar plazas y listar plazas. El administrador puede seleccionar una
	 * opción ingresando el número por teclado. El bucle del menú se repite hasta
	 * que el administrador ingrese "exit" para salir. Al finalizar, se cierran los
	 * recursos utilizados.
	 */
	private static void menuAdmin() {
		Scanner in = new Scanner(System.in);

		String admin_action;

		do {
			// Interfaz del menu
			System.out.println();
			System.out.println("========================================");
			System.out.println("=====  Centre Ciutat Parking v0.1  =====");
			System.out.println("=====  Consola del Administrador   =====");
			System.out.println("========================================");
			System.out.println("----------------------------------------");
			System.out.println("---    Bienvenido, administrador     ---");
			System.out.println("--- Por favor, seleccione una opción ---");
			System.out.println("----------------------------------------");
			System.out.println();
			System.out.println("----------------------------------------");
			System.out.println("|          1. Alquilar plazas          |");
			System.out.println("|          2. Editar plazas            |");
			System.out.println("|          3. Eliminar plazas          |");
			System.out.println("|          4. Listar plazas            |");
			System.out.println("----------------------------------------");
			System.out.println("");
			System.out.println("  - ¡Escribe \"exit\" para salir! -     ");

			admin_action = in.next();

			// Opciones a selecionar
			switch (admin_action) {
			case "1":
				Gestion.alquilarPlaza();
				break;
			case "2":
				Gestion.editarPlaza();
				break;
			case "3":
				Gestion.eliminarDatos();
				break;
			case "4":
				Gestion.listarPlazas();
				break;
			default:
				break;
			}

		} while (!admin_action.equalsIgnoreCase("exit"));

		// Cerramso los recurso
		System.out.println("Finalizando programa, ¡hasta la póxima!");
		in.close();
	}
}