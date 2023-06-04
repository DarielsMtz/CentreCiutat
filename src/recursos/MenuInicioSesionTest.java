package recursos;

import org.junit.jupiter.api.*;
import org.mockito.*;
import org.mockito.stubbing.Answer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.*;

class MenuInicioSesionTest {

	private static final String URL = "jdbc:mysql://localhost:3306/centreciutat", USERNAME = "root", 
			PASSWORD = "";
	
    private Connection con;
    private MenuInicioSesion menu;
    
    @Mock
    private Connection conMock;
    private ResultSet resultSet;
    private PreparedStatement stmt;

    @BeforeEach
    public void setUp() throws SQLException {
        con = mock(DriverManager.getConnection(URL, USERNAME, PASSWORD));
        menu = new MenuInicioSesion();
    }

    @AfterEach
    void tearDown() throws SQLException {
    	if (con != null) {
    		con.close();
    		menu = null;
    	}
    }

    @Test
    void validarCredenciales() throws SQLException {
        
        String tipoUsuario = "admin";
        String nombreUsuario = "admin1";
        String contrasena = "admin1";
        
        PreparedStatement stmt = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);

        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getObject(nombreUsuario));
        when(resultSet.getObject(contrasena));

        boolean result = menu.iniciarSesion(tipoUsuario);

        assertTrue(result);
    }
    
    @Test
    void iniciarSesion() throws SQLException {
        assertTrue(menu.iniciarSesion("admin"));
        Mockito.verify(conMock).prepareStatement(Mockito.anyString());
        Mockito.verify(stmt).executeQuery();
        Mockito.verify(resultSet).next();
        Mockito.verify(resultSet).getInt(1);
    }

    @Test
    void iniciarSesionFallo() throws SQLException {
        Mockito.when(resultSet.getInt(1)).thenReturn(0);

        assertFalse(menu.iniciarSesion("admin"));
        Mockito.verify(conMock).prepareStatement(Mockito.anyString());
        Mockito.verify(stmt).executeQuery();
        Mockito.verify(resultSet).next();
        Mockito.verify(resultSet).getInt(1);
    }
    
}
