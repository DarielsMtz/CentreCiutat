package recursos;

import java.sql.*;
import java.util.Scanner;

public class Gestion {
	private static final String DB_URL = "jdbc:mysql://localhost:3306/centreciutat";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "";

	private static Connection connection;
	
	// CONSULTAS
	
	private static int insertarCliente(String nombre, String apellido, String dni, String direccion, String cuentaCorriente) throws SQLException {
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

    private static int insertarVehiculo(String marca, String modelo, String color, String motor, String matricula, String  tipo) throws SQLException {
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

    private static void mostrarPlazasDisponibles() throws SQLException {
        String sql = "SELECT numero_plaza FROM plazas_estacionamiento WHERE disponible = true";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            int numeroPlaza = resultSet.getInt("numero_plaza");
            System.out.println(numeroPlaza);
        }
    }

    private static boolean verificarPlazaDisponible(int numeroPlaza) throws SQLException {
        String sql = "SELECT disponible FROM plazas_estacionamiento WHERE numero_plaza = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, numeroPlaza);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getBoolean("disponible");
        } else {
            return false;
        }
    }
    
    // METODOS
    
	// M�todo para alquilar plazas
	public static void alquilarPlaza() {
		try {
			connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        System.out.println("Conexi�n establecida correctamente!");
		 // Implementamos la clase Scanner
		
		// Solicitamos los datos del cliente
        Scanner teclado = new Scanner(System.in);
        System.out.println("Informaci�n del cliente:");
        System.out.print("Nombre: ");
        String nombre = teclado.nextLine();
        System.out.print("Apellidos: ");
        String apellidos = teclado.nextLine();
        System.out.print("DNI: ");
        String dni = teclado.nextLine();
        System.out.print("Direcci�n: ");
        String direccion = teclado.nextLine();
        System.out.print("Cuenta corriente: ");
        String cuentaCorriente = teclado.nextLine();

        // Creamos un objeto del tipo Inquilino para guardar su informaci�n en la base de datos (a�n no implementado la generaci�n de usuario y contrase�a)
        int clienteId = 0;
		try {
			clienteId = insertarCliente(nombre, apellidos, dni, direccion, cuentaCorriente);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (clienteId == -1) {
            System.out.println("No se pudo insertar el cliente en la base de datos.");
            return;
        }
        // Solicitamos los datos del coche
        System.out.println("\nInformaci�n del coche:");
        System.out.println("Marca: ");
        String marca = teclado.nextLine();
        System.out.print("Modelo: ");
        String modelo = teclado.nextLine();
        System.out.print("Color: ");
        String color = teclado.nextLine();
        System.out.print("Motor: ");
        String motor = teclado.nextLine();
        System.out.print("Matr�cula: ");
        String matricula = teclado.nextLine();
        System.out.print("Tipo de veh�culo (coche, moto, furgoneta): ");
        String tipo = teclado.nextLine();

        // Creamos un objeto del tipo vehiculo para guardar su informaci�n
        int vehiculoId = 0;
		try {
			vehiculoId = insertarVehiculo(marca, modelo, color, motor, matricula, tipo);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (vehiculoId == -1) {
            System.out.println("No se pudo insertar el veh�culo en la base de datos.");
            return;
        }
        
        System.out.println("Plazas de estacionamiento disponibles:");
        try {
			mostrarPlazasDisponibles();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        // Solicitar n�mero de plaza
        System.out.print("Seleccione el n�mero de la plaza de estacionamiento: ");
        int numeroPlaza = teclado.nextInt();

        // Verificar disponibilidad de la plaza seleccionada
        boolean plazaDisponible = false;
		try {
			plazaDisponible = verificarPlazaDisponible(numeroPlaza);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (!plazaDisponible) {
            System.out.println("La plaza seleccionada no est� disponible.");
            return;
        }
        System.out.println("\n�Plaza alquilada con �xito!");
    }
	
	
	// M�todo para editar plazas
	public static void editarPlaza() {
		
	}
	
	// M�todo para eliminar una plaza
	public static void eliminarPlaza() {
		
	}
	
	// M�todo para listar plazas
	public static void listarPlazas() {
		
	}
	
	
}


