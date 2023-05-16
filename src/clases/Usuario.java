package clases;

public abstract class Usuario {

	// Atributos generales
	protected String usuario;
	protected String contraseña;
	protected tipoUsuario tipo;
	public enum tipoUsuario {admin, inquilino}
	
	// Constructor
	public Usuario(String usuario, String contraseña, tipoUsuario tipo) {
		super();
		this.usuario = usuario;
		this.contraseña = contraseña;
		this.tipo = tipo;
	}

	// Getters y Setters
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	public tipoUsuario getTipo() {
		return tipo;
	}

	public void setTipo(tipoUsuario tipo) {
		this.tipo = tipo;
	}

	// Métodos extra
	@Override
	public String toString() {
		return "Usuario [usuario=" + usuario + ", contraseña=" + contraseña + ", tipo=" + tipo + "]";
	};
	
	
	
	
	
}