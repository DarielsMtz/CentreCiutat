package clases;

import java.sql.*;
import java.util.Scanner;

public class MenuInicioSesion {

	private Connection connection;

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
				login = iniciarSesion("admin");
				if (login) {
					System.out.println("Inicio de sesión exitoso como administrador.");
					 BienAdmin();
					 
				} else {
					System.out.println("Inicio de sesión fallido. Verifique sus credenciales.");
					System.out.println("");
				}
				break;
			case 2:
				login = iniciarSesion("usuario");
				if (login) {
					System.out.println("Inicio de sesión exitoso como usuario normal.");
					// TODO Metodo mostrarmenuUsuario();
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
	public static void BienAdmin() {
        Scanner input = new Scanner(System.in);
        int opcion;

        do {
        	System.out.println("=================================");
    		System.out.println("===== Centre Ciutat Parking =====");
    		System.out.println("=================================");
    		System.out.println("");
    		System.out.println("Bienvenido admin (elige una opción)");
    		System.out.println(" ");
            System.out.println("MENU DE OPCIONES");
            System.out.println("1. Alquilar plazas");
            System.out.println("2. Editar plazas");
            System.out.println("3. Eliminar plazas");
            System.out.println("4. Listado de plazas");
            System.out.println("0. Salir");
            System.out.print("Ingrese su opción: ");
            opcion = input.nextInt();

            switch (opcion) {
                case 1:
                    alquilarPlazas();
                    break;
                case 2:
                    //editarPlazas();
                    break;
                case 3:
                    //eliminarPlazas();
                    break;
                case 4:
                    //listarPlazas();
                    break;
                case 0:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción inválida. Por favor, seleccione una opción correcta.");
                    break;
            }
        } while (opcion != 0);

        input.close();
    }
	//Metodo de interfaz de alquiler de plazas
	public static void alquilarPlazas() {
		
    	Scanner scanner = new Scanner(System.in);
    	System.out.println("=================================");
		System.out.println("===== Centre Ciutat Parking =====");
		System.out.println("=================================");
		System.out.println("");
		System.out.println("---------------------------------");
		System.out.println("---     Alquilar plazas       ---");
		System.out.println("---     Información Cliente   ---");
		System.out.println("---------------------------------");
		System.out.println("");
		
		System.out.print("Nombre: ");
		String nombre = scanner.next();
		
		System.out.print("Apellido: ");
		String Apellido = scanner.next();
		
		System.out.print("DNI: ");
		String DNI= scanner.next();
		
		System.out.print("Dirección: ");
		String Dirección = scanner.next();
		
		System.out.print("Cuenta Corriente: ");
		String CuentaCorriente = scanner.next();
		System.out.println("");
		
		System.out.println("=================================");
		System.out.println("===== Centre Ciutat Parking =====");
		System.out.println("=================================");
		System.out.println(" ");
		System.out.println("Alquilar plazas");
		System.out.println("Información Coche");
		System.out.println(" ");
		
		System.out.print("Marca: ");
		String marca = scanner.next();
		
		System.out.print("Modelo: ");
		String modelo = scanner.next();
		
		System.out.print("Tipo de vehiculo: ");
		String Tvehiculo= scanner.next();
		
		System.out.print("Matricula: ");
		String matricula = scanner.next();
		
		System.out.print("Motor: ");
		String motor = scanner.next();
		
		System.out.println("Color: ");
		String color = scanner.next();
		System.out.println(" ");
    }
	public static void editarPlazas() {
        System.out.println("Opción: Editar plazas");
        Scanner in = new Scanner(System.in);
    	System.out.println("=================================");
		System.out.println("===== Centre Ciutat Parking =====");
		System.out.println("=================================");
		System.out.println(" ");
		System.out.println("Editar plazas");
		System.out.println("Información Cliente");
		System.out.println(" ");
		System.out.print("Nombre: ");
		String nombre = in.next();
		System.out.print("Apellido: ");
		String Apellido = in.next();
		System.out.print("DNI: ");
		String DNI= in.next();
		System.out.print("Dirección: ");
		String Dirección = in.next();
		System.out.print("Cuenta Corriente: ");
		String CuentaCorriente = in.next();
		System.out.println(" ");
		System.out.println(" ");
		System.out.println("=================================");
		System.out.println("===== Centre Ciutat Parking =====");
		System.out.println("=================================");
		System.out.println(" ");
		System.out.println("Editar plazas");
		System.out.println("Información Coche");
		System.out.println(" ");
		System.out.print("Marca: ");
		String marca = in.next();
		System.out.print("Modelo: ");
		String modelo = in.next();
		System.out.print("Tipo de vehiculo: ");
		String Tvehiculo= in.next();
		System.out.print("Matricula: ");
		String matricula = in.next();
		System.out.print("Motor: ");
		String motor = in.next();
		System.out.println("Color: ");
		String color = in.next();
		System.out.println(" ");
    }
	public static void BienUser() {
		System.out.println("=================================");
		System.out.println("===== Centre Ciutat Parking =====");
		System.out.println("=================================");
		System.out.println(" ");
		System.out.println("Bienvenido  Usuario");
		System.out.println(" ");
		System.out.println("Porfavor, introduzca su matricula o DNI");
		System.out.println(" ");
		System.out.println("=================================");
		System.out.println("===== Centre Ciutat Parking =====");
		System.out.println("=================================");
		System.out.println(" ");
		System.out.println("Bienvenido  Usuario");
		System.out.println(" ");
		System.out.println("Plaza");
		System.out.println(" ");
		System.out.println("m2");
		System.out.println(" ");
		System.out.println("Precio mensual");
		System.out.println(" ");
	}
	public static void eliminarPlazas() {
        System.out.println("Opción: Eliminar plazas");
        // Código para eliminar plazas
    }
	 public static void listarPlazas() {
	        System.out.println("Opción: Listado de plazas");
	        // Código para listar plazas
	    }
	 
	public static void main(String[] args) {
		MenuInicioSesion menu = new MenuInicioSesion();
		menu.mostrarMenu();
	}
}
