package recursos;

import java.sql.*;
import java.util.Scanner;

/**
 * La clase MenuInicioSesion representa la clase de inicio de sesi�n para el
 * programa. Permite a los usuarios iniciar sesi�n como administrador o usuario
 * normal. Proporciona un men� de opciones y realiza operaciones relacionadas
 * con la gesti�n del programa y la interacci�n con una base de datos.
 */
public class MenuInicioSesion {

	// Variable para establecer las conexcion con la base de datos
	/**
	 * Variblae para establecer la conexion de la bbdd
	 */
	private Connection connection;

	// Metodo principal de la clase
	/**
	 * Este m�todo es el punto de entrada principal del programa. Inicia con un
	 * llamado a la interfas del menu
	 * 
	 * @param args los argumentos de la l�nea de comandos (no se utilizan en este
	 *             m�todo)
	 */
	public static void main(String[] args) {

		// Se llama al metodo para mostrar por completo el menu
		MenuInicioSesion menu = new MenuInicioSesion();
		menu.mostrarMenu();
	}

	// Metod para verificar la conexcion con la base de datos
	/**
	 * Constructor de la clase MenuInicioSesion. Realiza el llamado a los distintos
	 * m�todos relacionados con la gesti�n del programa. Establece la conexi�n con
	 * la base de datos.
	 * 
	 * @throws SQLException si se produce un error al conectar con la base de datos.
	 */
	public MenuInicioSesion() {

		// Se hace un llamdo a los distintos metodos relacionado con las gestion del
		// programa
		BBDD.ejectutarMetodos();

		// Establecer conexi�n con la base de datos
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/centreciutat", "root", "");
			// System.out.println("Se ha establecido la conexi�n a la BBDD correctamente!");

		} catch (SQLException e) {
			System.out.println("Error al conectar con la base de datos: " + e.getMessage());
			System.exit(0);
		}
	}

	// M�todo para recoger los datos por teclado
	/**
	 * 
	 * Metodo para llevar acabo el inicio de sesi�n de un usuario en el sistema.
	 * 
	 * @param tipoUsuario el tipo de usuario que est� iniciando sesi�n
	 * 
	 * @return true si el inicio de sesi�n es exitoso, false en caso contrario
	 */
	public boolean iniciarSesion(String tipoUsuario) {
		Scanner scanner = new Scanner(System.in);

		// Se hace una petici�n del Usuario
		System.out.print("Ingrese el usuario: \n");
		String nombreUsuario = scanner.nextLine();

		// Se hace una petici�n de la contrase�a
		System.out.print("Ingrese la contrase�a: \n");

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
			System.out.println("Error al verificar el inicio de sesi�n: " + e.getMessage());
		}
		return false;
	}

	// M�todo que muestra las opciones de inicio de sesi�n
	/**
	 * 
	 * Muestra el men� de inicio de sesi�n y permite al usuario seleccionar una
	 * opci�n. Si el inicio de sesi�n es exitoso, redirige al usuario al men�
	 * correspondiente seg�n su rol. Si el inicio de sesi�n falla, muestra un
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
			System.out.println("---     Inicio de Sesi�n     ---");
			System.out.println("--------------------------------");
			System.out.println("|       1. Administrador       |");
			System.out.println("|       2. Usuario             |");
			System.out.println("|       3. Salir               |");
			System.out.println("--------------------------------");
			System.out.println("");
			System.out.print("- Ingrese una opci�n:-\n");

			int opcion = obtenerNumero();

			// Opciones de usuarios para inicias sesi�n
			switch (opcion) {
			case 1:
				login = iniciarSesion("admin");
				if (login) {
					System.out.println("Inicio de sesi�n exitoso como administrador.");
					// Se llama al metodo de las acciones del usuario admin
					menuAdmin();

				} else {
					System.out.println("Inicio de sesi�n fallido. Verifique sus credenciales.");
					System.out.println("");
				}
				break;
			case 2:
				login = iniciarSesion("usuario");
				if (login) {
					System.out.println("Inicio de sesi�n exitoso como usuario normal.");
					// Se llama al metod de las acciones del usuario normal
					menuUsuario();

				} else {
					System.out.println("Inicio de sesi�n fallido. Verifique sus credenciales.");
					System.out.println("");
				}
				break;
			case 3:
				System.out.println("Saliendo...");
				System.exit(0);
				break;
			default:
				System.out.println("Opci�n inv�lida. Intente nuevamente.");
			}
		} while (!login);
	}

	// M�todo para obtener y validar la opciones
	/**
	 * M�todo que obtiene un n�mero ingresado por teclado.
	 *
	 * @return el n�mero ingresado por el usuario.
	 */
	private int obtenerNumero() {
		Scanner scanner = new Scanner(System.in);
		// Lee el numero/opci�n ingresado por teclado
		while (!scanner.hasNextInt()) {
			System.out.print("Ingrese una opci�n v�lida, porfavor: ");
			scanner.next();
		}
		return scanner.nextInt();
	}

	// Menu de acciones del usuario Normal
	/**
	 * Muestra el men� de usuario y permite ingresar un DNI o una matr�cula para
	 * consultar los datos. Luego llama al m�todo 'consultarDatos' para obtener los
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

		// Lee el DNI o la matr�cula ingresados por teclado
		Scanner scanner = new Scanner(System.in);
		System.out.println("- Ingrese un \"DNI\" o una \"Matr�cula\": ");
		String dniMatricula = scanner.nextLine();

		// Llama al m�todo para consultar los datos
		consultarDatos(dniMatricula);
	}

	// M�todo para consultar los datos relacionados a un DNI o una matr�cula
	/**
	 * Metodo que actua cono consulta para los datos de un cliente y su veh�culo en
	 * la base de datos.
	 * 
	 * @param dniMatricula El DNI o la matr�cula del cliente a consultar.
	 */
	public static void consultarDatos(String dniMatricula) {
		// Establecemos una conexion con la base de datos
		String url = "jdbc:mysql://localhost:3306/centreciutat"; // Enlace
		String usuario = "root"; // Usuario
		String contrase�a = ""; // Contrase�a

		try (Connection conn = DriverManager.getConnection(url, usuario, contrase�a)) {

			// Consulta para obtener los datos relacionados al DNI o la matr�cula
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
				System.out.println("---    Informaci�n Cliente     ---");
				System.out.println("----------------------------------");
				System.out.println("ID Cliente: " + idCliente);
				System.out.println("Nombre: " + nombre);
				System.out.println("Apellido: " + apellido);
				System.out.println("DNI: " + dni);
				System.out.println("Direcci�n: " + direccion);
				System.out.println("Cuenta corriente: " + cuentaCorriente);

				// Informacion del vehiculo
				System.out.println("----------------------------------");
				System.out.println("---    Informaci�n Veh�culo    ---");
				System.out.println("----------------------------------");
				System.out.println("Marca: " + marca);
				System.out.println("Modelo: " + modelo);
				System.out.println("Color: " + color);
				System.out.println("Motor: " + motor);
				System.out.println("Matr�cula: " + matricula);
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
	 * M�todo que muestra el men� de administrador y permite realizar distintas
	 * acciones. Las opciones disponibles son: alquilar plazas, editar plazas,
	 * eliminar plazas y listar plazas. El administrador puede seleccionar una
	 * opci�n ingresando el n�mero por teclado. El bucle del men� se repite hasta
	 * que el administrador ingrese "exit" para salir. Al finalizar, se cierran los
	 * recursos utilizados.
	 */
	static void menuAdmin() {
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
			System.out.println("--- Por favor, seleccione una opci�n ---");
			System.out.println("----------------------------------------");
			System.out.println();
			System.out.println("----------------------------------------");
			System.out.println("|          1. Alquilar plazas          |");
			System.out.println("|          2. Editar plazas            |");
			System.out.println("|          3. Eliminar plazas          |");
			System.out.println("|          4. Listar plazas            |");
			System.out.println("----------------------------------------");
			System.out.println("");
			System.out.println("  - �Escribe \"exit\" para salir! -     ");

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
		System.out.println("Finalizando programa, �hasta la p�xima!");
		in.close();
	}
}