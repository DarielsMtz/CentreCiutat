package clases;

public class Vehiculo {

	//Atributos de clase
	protected String modelo;
	protected String color;
	protected String motor;
	protected String matricula;
	protected tipoVehiculo tipo;
	public enum tipoVehiculo {coche, moto, furgoneta} 
	
	// Constructor
	public Vehiculo(String modelo, String color, String motor, String matricula, tipoVehiculo tipo) {
		super();
		this.modelo = modelo;
		this.color = color;
		this.motor = motor;
		this.matricula = matricula;
		this.tipo = tipo;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getMotor() {
		return motor;
	}

	public void setMotor(String motor) {
		this.motor = motor;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public tipoVehiculo getTipo() {
		return tipo;
	}

	public void setTipo(tipoVehiculo tipo) {
		this.tipo = tipo;
	}

	// Métodos extra
	@Override
	public String toString() {
		return "Vehiculo [modelo=" + modelo + ", color=" + color + ", motor=" + motor + ", matricula=" + matricula
				+ ", tipo=" + tipo + "]";
	}
	
	
	
	
	
	
}