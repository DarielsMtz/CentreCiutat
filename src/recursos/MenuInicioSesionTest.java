package recursos;

import org.junit.jupiter.api.*;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.*;

class MenuInicioSesionTest {

	private static final String URL = "jdbc:mysql://localhost:3306/centreciutat";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "";
	
    private Connection con;
    private MenuInicioSesion menu;
    
    @Mock
    private Connection conMock;
    
    @Mock
    private PreparedStatement stmt;
    
    @Mock
    private ResultSet resultSet;

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
    void iniciarSesion_validCredentials_returnsFalse() throws SQLException {
        
        String tipoUsuario = "admin";

        PreparedStatement stmt = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);

        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(1);

        // Act
        boolean result = menu.iniciarSesion(tipoUsuario);

        // Assert
        assertFalse(result);
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
        Mockito.when(resultSet.getInt(1)).thenReturn(1);

        assertFalse(menu.iniciarSesion("admin"));
        Mockito.verify(conMock).prepareStatement(Mockito.anyString());
        Mockito.verify(stmt).executeQuery();
        Mockito.verify(resultSet).next();
        Mockito.verify(resultSet).getInt(1);
    }
    
}
