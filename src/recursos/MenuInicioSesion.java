package recursos;

import java.sql.*;
import java.util.Scanner;

public class MenuInicioSesion {

	private Connection connection;

	public static void main(String[] args) {
		MenuInicioSesion menu = new MenuInicioSesion();
		menu.mostrarMenu();
	}

	public MenuInicioSesion() {

		// Establecer conexión con la base de datos
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/centreciutat", "root", "");
			System.out.println("Se ha establecido la conexcion a la BBDD correctamente!");

		} catch (SQLException e) {
			System.out.println("Error al conectar con la base de datos: " + e.getMessage());
			System.exit(0);
		}
	}

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
			System.out.println("---     Inicio de Sesion     ---");
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
				// Dos opciones de usuario
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
				// Dos opciones de usuario
				login = iniciarSesion("usuario");
				if (login) {
					System.out.println("Inicio de sesión exitoso como usuario normal.");

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

	 // Metodo para las opciones del admin
	
	// Consola de admin
	// BBDD.ejectutarMetodos();
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
			System.out.println("|        1. Alquilar plazas            |)");
			System.out.println("|        2. Editar plazas              |");
			System.out.println("|        3. Eliminar plazas            |)");
			System.out.println("|        4. Listar plazas              |");
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
		System.out.println("FIN DE PROGRAMA");
		in.close();
	}
}