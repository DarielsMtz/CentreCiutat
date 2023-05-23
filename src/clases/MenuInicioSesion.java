package clases;

import java.sql.*;
import java.util.Scanner;

public class MenuInicioSesion {

	private Connection connection;

	public MenuInicioSesion() {

		// Establecer conexi�n con la base de datos
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

		// peticion de la contrase�a
		System.out.print("Ingrese la contrase�a: \n");
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
			System.out.println("Error al verificar el inicio de sesi�n: " + e.getMessage());
		}
		return false;
	}

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
			System.out.print("- Ingrese una opci�n:-\n");
			int opcion = obtenerNumero();

			switch (opcion) {
			case 1:
				login = iniciarSesion("admin");
				if (login) {
					System.out.println("Inicio de sesi�n exitoso como administrador.");
					// TODO Metodo mostrarMenuAdministrador();
				} else {
					System.out.println("Inicio de sesi�n fallido. Verifique sus credenciales.");
					System.out.println("");
				}
				break;
			case 2:
				login = iniciarSesion("usuario");
				if (login) {
					System.out.println("Inicio de sesi�n exitoso como usuario normal.");
					// TODO Metodo mostrarmenuUsuario();
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

	private int obtenerNumero() {
		Scanner scanner = new Scanner(System.in);
		while (!scanner.hasNextInt()) {
			System.out.print("Ingrese una opci�n v�lida, porfavor: ");
			scanner.next();
		}
		return scanner.nextInt();
	}

	public static void main(String[] args) {
		MenuInicioSesion menu = new MenuInicioSesion();
		menu.mostrarMenu();
	}
}
