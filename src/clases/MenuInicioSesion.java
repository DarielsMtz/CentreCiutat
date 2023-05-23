package clases;

import java.nio.file.*;
import java.sql.*;
import java.util.Scanner;

public class MenuInicioSesion {
	
	public static void main(String[] args) {

		// Generar DB a partir de script si no existe
		 if (!detectarDB()) {
	            crearDB();
	        }
		
		// Conectar con DB
		MenuInicioSesion menu = new MenuInicioSesion();
		menu.mostrarMenu();

	}
	
	// Método para crear/generar DB a partir de un script
	public static void crearDB() {
		// Conexión con el archivo .sql
		String url = "jdbc:mysql://localhost:3306/?user=root&password=";

		// Establecer conexión
		try (Connection con = DriverManager.getConnection(url);
			 Statement stmt = con.createStatement()) {

			// Seleccionar archivo .sql
			String script = new String(Files.readAllBytes(Paths.get("db/centreciutat_1.sql")));

			// Ejecutar script
			executeSqlScript(stmt, script);

			System.out.println("Creación completada!\n");

		} catch (Exception e) {
			System.out.println("Error al generar la base de datos: " + e.getMessage());
		}
		
	}

	// Método necesario para el script que genera la BD
	public static void executeSqlScript(Statement statement, String sqlScript) {
		try (Scanner scanner = new Scanner(sqlScript)) {
			scanner.useDelimiter("(;(\r)?\n)|(--\n)");
			while (scanner.hasNext()) {
				String line = scanner.next();
				if (line.trim().length() > 0) {
					statement.execute(line);
				}
			}

			System.out.println("Generando base de datos...");

		} catch (Exception e) {
			System.out.println("Error al generar la base de datos: " + e.getMessage());
		}
	}

	// Método para detectar si ya existe la BD
    public static boolean detectarDB() {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/?user=root&password=");
             Statement stmt = con.createStatement()) {
            ResultSet res = stmt.executeQuery("SHOW DATABASES LIKE 'centreciutat'");
            return res.next();
        } catch (SQLException e) {
            System.out.println("Error al verificar la existencia de la base de datos: " + e.getMessage());
        }
        return false;
    }
	
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
    		System.out.println(" ");
    		System.out.println("---------------------------------");
    		System.out.println("---     Bienvenido admin      ---");
    		System.out.println("---------------------------------");
    		System.out.println(" ");
    		System.out.println("---------------------------------");
    		System.out.println("|     1. Alquilar plazas        |");
    		System.out.println("|     2. Editar plazas          |");
    		System.out.println("|     3. Eliminar plazas        |");
    		System.out.println("|     4. Listar plazas          |");
    		System.out.println("---------------------------------");
    		System.out.println(" ");
            System.out.print("Ingrese su opción: ");
            opcion = input.nextInt();

            switch (opcion) {
                case 1:
                    alquilarPlazas();
                    break;
                case 2:
                    editarPlazas();
                    break;
                case 3:
                    eliminarPlazas();
                    break;
                case 4:
                    listarPlazas();
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
	public static void alquilarPlazas() {
    	Scanner in = new Scanner(System.in);
    	System.out.println("=================================");
		System.out.println("===== Centre Ciutat Parking =====");
		System.out.println("=================================");
		System.out.println(" ");
		System.out.println("---------------------------------");
		System.out.println("---      Alquilar plazas      ---");
		System.out.println("---------------------------------");
		System.out.println(" ");
		System.out.println("---------------------------------");
		System.out.println("---   Información Cliente     ---");
		System.out.println("---------------------------------");
		System.out.println(" ");
		System.out.print("Nombre: ");
		String nombre = in.next();
		System.out.print("Apellido: ");
		String apellido = in.next();
		System.out.print("DNI: ");
		String dni= in.next();
		System.out.print("Dirección: ");
		String dirección = in.next();
		System.out.print("Cuenta Corriente: ");
		String cuentacorriente = in.next();
		System.out.println(" ");
		System.out.println("=================================");
		System.out.println("===== Centre Ciutat Parking =====");
		System.out.println("=================================");
		System.out.println(" ");
		System.out.println("---------------------------------");
		System.out.println("---      Alquilar plazas      ---");
		System.out.println("---------------------------------");
		System.out.println(" ");
		System.out.println("---------------------------------");
		System.out.println("---     Información Coche     ---");
		System.out.println("---------------------------------");
		System.out.println(" ");
		System.out.print("Marca: ");
		String marca = in.next();
		System.out.print("Modelo: ");
		String modelo = in.next();
		System.out.print("Tipo de vehiculo: ");
		String tipovehiculo= in.next();
		System.out.print("Matricula: ");
		String matricula = in.next();
		System.out.print("Motor: ");
		String motor = in.next();
		System.out.println("Color: ");
		String color = in.next();
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
		String apellido = in.next();
		System.out.print("DNI: ");
		String dni= in.next();
		System.out.print("Dirección: ");
		String dirección = in.next();
		System.out.print("Cuenta Corriente: ");
		String cuentacorriente = in.next();
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
		String tipovehiculo= in.next();
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
	 

}
