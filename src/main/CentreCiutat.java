package main;
import java.util.*;

import recursos.BBDD;
import recursos.Gestion;

public class CentreCiutat {

	public static void main(String[] args) {

		Scanner in = new Scanner (System.in);
		
		// Login
		/*System.out.println("=================================");
		System.out.println("===== Centre Ciutat Parking =====");
		System.out.println("=================================");
		
		System.out.println("\n*** Inicio de Sesión ***");
		System.out.println("Por favor, introduzca los siguientes datos:");
		System.out.print("Usuario: ");
		String usuario = in.next();
		System.out.print("Contraseña: ");
		String contraseña = in.next();
		
		System.out.println(usuario+" "+contraseña);*/
		
		
		
		// Consola de admin
		BBDD.ejectutarMetodos();
		String admin_action;
		do {
			System.out.println();
			System.out.println("=================================");
			System.out.println("===== Centre Ciutat Parking =====");
			System.out.println("=== Consola del Administrador ===");
			System.out.println("=================================");
			System.out.println();
			
			System.out.println("Bienvenido, administrador");
			System.out.println("Por favor, seleccione una opción");
			System.out.println();
			
			System.out.println("== Alquilar plazas == (1)");
			System.out.println();
			System.out.println("=== Editar plazas === (2)");
			System.out.println();
			System.out.println("== Eliminar plazas == (3)");
			System.out.println();
			System.out.println("=== Listar plazas === (4)");
			System.out.println();
			System.out.println(" Escribe exit para salir ");
			
			admin_action = in.next();
			    
			    switch (admin_action) {
			        case "1":
			            Gestion.alquilarPlaza();
			            break;
			        case "2":
			            Gestion.editarPlaza();
			            break;
			        case "3":
			            Gestion.eliminarDatos();
			            break;
			        case "4":
			            Gestion.listarPlazas();
			            break;
			        default:
			            // Handle invalid input
			            break;
			    }     
			    
			} while (!admin_action.equalsIgnoreCase("exit"));

			System.out.println("FIN DE PROGRAMA");
			
			in.close();
	}
}
		
		
		
		
		
		
		
		
		
		
		
		
		
	
		
		
	


