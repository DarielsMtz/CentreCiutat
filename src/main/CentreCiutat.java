package main;
import java.util.*;

public class CentreCiutat {

	public static void main(String[] args) {

Scanner in = new Scanner (System.in);
		
		// Login
		/*System.out.println("=================================");
		System.out.println("===== Centre Ciutat Parking =====");
		System.out.println("=================================");
		
		System.out.println("\n*** Inicio de Sesi�n ***");
		System.out.println("Por favor, introduzca los siguientes datos:");
		System.out.print("Usuario: ");
		String usuario = in.next();
		System.out.print("Contrase�a: ");
		String contrase�a = in.next();
		
		System.out.println(usuario+" "+contrase�a);*/
		
		
		
		// Consola de admin
		do {
			System.out.println("=================================");
			System.out.println("===== Centre Ciutat Parking =====");
			System.out.println("=== Consola del Administrador ===");
			System.out.println("=================================");
			System.out.println();
			
			System.out.println("Bienvenido, administrador");
			System.out.println("Por favor, escoja una opci�n");
			
			
			switch (in.nextInt()){
			case 1:
				System.out.println("accion 1");
				break;
			case 2: 
				System.out.println("accion 2");
				break;
				
			}
			
			
			
			
		} while (in.next()!="Exit"||in.next()!="exit"||in.next()!="EXIT");
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		in.close();
	}

}
