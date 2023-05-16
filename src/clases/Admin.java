package clases;

public class Admin extends Usuario{

	// Constructor de superclase
	public Admin(String usuario, String contraseña, tipoUsuario tipo) {
		super(usuario, contraseña, tipo);
	}

	// Métodos extra
	@Override //toString
	public String toString() {
		return "Admin []";
	}

	// Método para alquilar plazas
	public static void alquilarPlaza() {
		
	}
	
	// Método para editar plazas
	public static void editarPlaza() {
		
	}
	
	// Método para eliminar una plaza
	public static void eliminarPlaza() {
		
	}
	
	// Método para listar plazas
	public static void listarPlazas() {
		
	}
	
	
}
