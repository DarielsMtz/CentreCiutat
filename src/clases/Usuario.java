package clases;

public abstract class Usuario {

	// Atributos generales
	protected String usuario;
	protected String contrase�a;
	protected tipoUsuario tipo;
	public enum tipoUsuario {admin, inquilino}
	
	// Constructor
	public Usuario(String usuario, String contrase�a, tipoUsuario tipo) {
		super();
		this.usuario = usuario;
		this.contrase�a = contrase�a;
		this.tipo = tipo;
	}

	// Getters y Setters
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getContrase�a() {
		return contrase�a;
	}

	public void setContrase�a(String contrase�a) {
		this.contrase�a = contrase�a;
	}

	public tipoUsuario getTipo() {
		return tipo;
	}

	public void setTipo(tipoUsuario tipo) {
		this.tipo = tipo;
	}

	// M�todos extra
	@Override
	public String toString() {
		return "Usuario [usuario=" + usuario + ", contrase�a=" + contrase�a + ", tipo=" + tipo + "]";
	};
	
	
	
	
	
}