package clases;

public class Admin extends Usuario{

	// Constructor de superclase
	public Admin(String usuario, String contrase�a, tipoUsuario tipo) {
		super(usuario, contrase�a, tipo);
	}

	// M�todos extra
	@Override //toString
	public String toString() {
		return "Admin []";
	}

	// M�todo para alquilar plazas
	public static void alquilarPlaza() {
		
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
