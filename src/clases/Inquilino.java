package clases;

public class Inquilino extends Usuario {

	// Constructor de superclase
	public Inquilino(String usuario, String contraseña, tipoUsuario tipo) {
		super(usuario, contraseña, tipo);
	}
	
	// Atributos de clase
	protected String nombre;
	protected String apellidos;
	protected String dni;
	protected String direccion;
	protected String cuentaCorriente;
	
	// Constructor de clase
	public Inquilino(String usuario, String contraseña, tipoUsuario tipo, String nombre, String apellidos, String dni,
			String direccion, String cuentaCorriente) {
		super(usuario, contraseña, tipo);
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.dni = dni;
		this.direccion = direccion;
		this.cuentaCorriente = cuentaCorriente;
	}

	// Getters y Setters
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getCuentaCorriente() {
		return cuentaCorriente;
	}

	public void setCuentaCorriente(String cuentaCorriente) {
		this.cuentaCorriente = cuentaCorriente;
	}

	// Métodos extra
	@Override
	public String toString() {
		return "Inquilino [nombre=" + nombre + ", apellidos=" + apellidos + ", dni=" + dni + ", direccion=" + direccion
				+ ", cuentaCorriente=" + cuentaCorriente + "]";
	}

	

}
