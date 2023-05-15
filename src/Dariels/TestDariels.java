package Dariels;

import java.sql.*;

public class TestDariels {
	
    // Método main para probar la conexión y los métodos
    public static void main(String[] args) {
    	TestDariels dao = new TestDariels();
        dao.setupConnection();
        dao.crearTablaPlazas();
       // dao.agregarPlaza(1, 1, true);
       // dao.agregarPlaza(2, 2, false);
    }
    private Connection connection;
   

    // Configura la conexión con la base de datos
    public void setupConnection() {
        String url = "jdbc:mysql://localhost:3306/centre-ciutat";
        String username = "root";
        String password = "";

        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Conexión exitosa");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Crea la tabla de plazas de estacionamiento
    public void crearTablaPlazas() {
        String sql = "CREATE TABLE plazas_estacionamiento (id INT PRIMARY KEY, numero_plaza INT, disponible BOOLEAN)";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            System.out.println("Tabla de plazas de estacionamiento creada correctamente");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Agrega una plaza de estacionamiento a la base de datos
    public void agregarPlaza(int id, int numeroPlaza, boolean disponible) {
        String sql = "INSERT INTO plazas_estacionamiento (id, numero_plaza, disponible) VALUES (?, ?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.setInt(2, numeroPlaza);
            statement.setBoolean(3, disponible);
            statement.executeUpdate();
            System.out.println("Plaza de estacionamiento agregada correctamente");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}

