package clases;

public class Plaza {
	
	// Atributos de clase
	protected int idPlaza;
	protected double m2;
	protected double precio;
	protected estadoPlaza estado;
	public enum estadoPlaza {ocupado, libre}
	
	// Constructor
	public Plaza(int idPlaza, double m2, double precio, estadoPlaza estado) {
		super();
		this.idPlaza = idPlaza;
		this.m2 = m2;
		this.precio = precio;
		this.estado = estado;
	}

	// Getters y Setters
	public int getIdPlaza() {
		return idPlaza;
	}

	public void setIdPlaza(int idPlaza) {
		this.idPlaza = idPlaza;
	}

	public double getM2() {
		return m2;
	}

	public void setM2(double m2) {
		this.m2 = m2;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public estadoPlaza getEstado() {
		return estado;
	}

	public void setEstado(estadoPlaza estado) {
		this.estado = estado;
	};
	
	
	
	
	
	
	
	
	
	
	

}