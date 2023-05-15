package Dariels;

import java.sql.*;

public class TestDariels {
	
    // M�todo main para probar la conexi�n y los m�todos
    public static void main(String[] args) {
    	TestDariels dao = new TestDariels();
        dao.setupConnection();
        dao.crearTablaPlazas();
       // dao.agregarPlaza(1, 1, true);
       // dao.agregarPlaza(2, 2, false);
    }
    private Connection connection;
   

    // Configura la conexi�n con la base de datos
    public void setupConnection() {
        String url = "jdbc:mysql://localhost:3306/centre-ciutat";
        String username = "root";
        String password = "";

        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Conexi�n exitosa");
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

}

