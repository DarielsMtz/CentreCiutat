package main;

import recursos.MenuInicioSesion;

/**
 * Clase principal que inicia la aplicacion Centre Ciutat. Esta clase contiene
 * el método principal, el cual se encarga de crear una instancia de la clase
 * MenuInicioSesion y llamar al método mostrarMenu() para mostrar el menú de
 * inicio de sesión y permitir al usuario seleccionar una opción.
 * 
 * La clase CentreCiutat es el punto de entrada de la aplicación y actúa como un
 * controlador para gestionar el flujo de ejecución del programa.
 * 
 * @param args
 */
public class CentreCiutat {

	/**
	 * Método principal que se encarga de iniciar la aplicación. Crea una instancia
	 * de la clase MenuInicioSesion y llama al método mostrarMenu() para mostrar el
	 * menú de inicio de sesión y permitir al usuario seleccionar una opción.
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

