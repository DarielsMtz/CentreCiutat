package main;
import java.util.*;

public class CentreCiutat {

	public static void main(String[] args) {

Scanner in = new Scanner (System.in);
		
		System.out.println("=================================");
		System.out.println("===== Centre Ciutat Parking =====");
		System.out.println("=================================");
		
		System.out.println("\n*** Inicio de Sesi�n ***");
		System.out.println("Por favor, introduzca los siguientes datos:");
		System.out.print("Usuario: ");
		String usuario = in.next();
		System.out.print("Contrase�a: ");
		String contrase�a = in.next();
		
		System.out.println(usuario+" "+contrase�a);
		
		in.close();
		
	}

}