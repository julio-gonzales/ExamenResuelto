package bo.edu.uagrm.inf310.sb.primerParcial;

import java.util.Scanner;

import bo.edu.uagrm.inf310.sb.arboles.*;


public class PrimeraPregunta <K extends Comparable<K>,V> extends ArbolBinarioBusqueda<K,V> {
	public boolean esMonticulo() {
		NodoBinario<K,V> nodoActual = this.raiz;
		
		while (!NodoBinario.esNodoVacio(nodoActual)) {
			if (!nodoActual.esVacioHijoDerecho()) {
				return false;
			}
			nodoActual = nodoActual.getHijoIzquierdo();
		}
		
		return true;
		
	}
	
	
	
	public static void main(String[] args) throws ExcepcionClaveYaExiste, ExcepcionClaveNoExiste {
		
		PrimeraPregunta<Integer, String> prueba = new PrimeraPregunta<>();
		int opcion = 0;
		int clave = 0;
		String valor;
		Scanner teclado = new Scanner(System.in);
		do {
			System.out.println("   ARBOL BINARIO BUSQUEDA  ");
			System.out.println("1.-   insertar");
			System.out.println("2.-   eliminar");
			System.out.println("3.-   Es monticulo");
			System.out.println("4.-   salir");
			opcion = teclado.nextInt();
			switch (opcion) {
			case 1:

				System.out.println("digte la clave a insertar: ");
				clave = teclado.nextInt();
				System.out.println("digite el valor a insertar: ");
				valor = teclado.next();
				prueba.insertar(clave, valor);
				break;
			case 2:
				System.out.println("digte la clave a eliminar: ");
				clave = teclado.nextInt();
				System.out.println("el valor de la clave eliminada es: " + prueba.eliminar(clave));
				
				break;
			case 3:
				System.out.println("el arbol es un monticulo : " + prueba.esMonticulo());
				break;

			default:
				break;
			}
			
		}
		
		while (opcion != 4);
	
		
	}
}

