package main;

import recursos.MenuInicioSesion;

/**
 * Clase principal que inicia la aplicaci�n Centre Ciutat. Esta clase contiene
 * el m�todo principal, el cual se encarga de crear una instancia de la clase
 * MenuInicioSesion y llamar al m�todo mostrarMenu() para mostrar el men� de
 * inicio de sesi�n y permitir al usuario seleccionar una opci�n.
 * 
 * La clase CentreCiutat es el punto de entrada de la aplicaci�n y act�a como un
 * controlador para gestionar el flujo de ejecuci�n del programa.
 * 
 * @param args
 */
public class CentreCiutat {

	/**
	 * M�todo principal que se encarga de iniciar la aplicaci�n. Crea una instancia
	 * de la clase MenuInicioSesion y llama al m�todo mostrarMenu() para mostrar el
	 * men� de inicio de sesi�n y permitir al usuario seleccionar una opci�n.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		// Se intacion un objeto de tipo MunuInicioSesion
		MenuInicioSesion inicio = new MenuInicioSesion();

		// Se hace un llamado al menu complento
		inicio.mostrarMenu();

	}
}
