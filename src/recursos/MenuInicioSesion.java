package recursos;

import java.sql.*;
import java.util.Scanner;

public class MenuInicioSesion {

	private Connection connection;

	// Metodo principal de la clase
	public static void main(String[] args) {

		MenuInicioSesion menu = new MenuInicioSesion();
		menu.mostrarMenu();
	}

	// Metod para verificar la conexcion con la base de datos
	public MenuInicioSesion() {

		BBDD.ejectutarMetodos();
		// Establecer conexión con la base de datos
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/centreciutat", "root", "");
			System.out.println("Se ha establecido la conexión a la BBDD correctamente!");

		} catch (SQLException e) {
			System.out.println("Error al conectar con la base de datos: " + e.getMessage());
			System.exit(0);
		}
	}

	// Metodo para recoger la opcion por teclado
	public boolean iniciarSesion(String tipoUsuario) {
		Scanner scanner = new Scanner(System.in);

		// Peticion del Usuario
		System.out.print("Ingrese el usuario: \n");
		String nombreUsuario = scanner.nextLine();

		// peticion de la contraseña
		System.out.print("Ingrese la contraseña: \n");
		String contrasena = scanner.nextLine();
		try {
			String query = "SELECT COUNT(*) FROM usuarios WHERE tipo = ? AND nombre_usuario = ? AND contrasena = ?";

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

	// Metodo que muestra las opciones de inicio de sesion
	public void mostrarMenu() {

		boolean login = false;

		do {
			System.out.println("");
			System.out.println("================================");
			System.out.println("== Centre Ciutat Parking v0.1 ==");
			System.out.println("================================");
			System.out.println("");
			System.out.println("--------------------------------");
			System.out.println("---     Inicio de Sesión     ---");
			System.out.println("--------------------------------");
			System.out.println("|     1. Administrador         |");
			System.out.println("|     2. Usuario               |");
			System.out.println("|     3. Salir                 |");
			System.out.println("--------------------------------");
			System.out.println("");
			System.out.print("- Ingrese una opción:-\n");
			int opcion = obtenerNumero();

			switch (opcion) {
			case 1:
				login = iniciarSesion("admin");
				if (login) {
					System.out.println("Inicio de sesión exitoso como administrador.");
					// Se llama a las acciones del administrador
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
					// Se llama a las acciones del usuario
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

	
	private int obtenerNumero() {
		Scanner scanner = new Scanner(System.in);
		while (!scanner.hasNextInt()) {
			System.out.print("Ingrese una opción válida, porfavor: ");
			scanner.next();
		}
		return scanner.nextInt();
	}

	// Menu de acciones del usuario
	private static void menuUsuario() {
		System.out.println("=================================");
		System.out.println("===== Centre Ciutat Parking =====");
		System.out.println("=================================");
		System.out.println("---------------------------------");
		System.out.println("---    Bienvenido  Usuario    ---");
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
	public static void consultarDatos(String dniMatricula) {
		String url = "jdbc:mysql://localhost:3306/centreciutat";
		String usuario = "root";
		String contraseña = "";

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

				System.out.println("");
				System.out.println("----------------------------------");
				System.out.println("---      Información Cliente   ---");
				System.out.println("----------------------------------");
				System.out.println("ID Cliente: " + idCliente);
				System.out.println("Nombre: " + nombre);
				System.out.println("Apellido: " + apellido);
				System.out.println("DNI: " + dni);
				System.out.println("Dirección: " + direccion);
				System.out.println("Cuenta corriente: " + cuentaCorriente);
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

	// Menu de acciones del admin
	private static void menuAdmin() {
		Scanner in = new Scanner(System.in);

		String admin_action;

		do {
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
			System.out.println("  - ¡Escribe \"exit\" para salir! -      ");

			admin_action = in.next();

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
		
		System.out.println("Finalizando programa, ¡hasta la póxima!");
		in.close();
	}
}