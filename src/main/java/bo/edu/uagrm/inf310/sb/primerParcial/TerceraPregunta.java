package bo.edu.uagrm.inf310.sb.primerParcial;


import java.util.Scanner;

import bo.edu.uagrm.inf310.sb.arboles.*;

public class TerceraPregunta <K extends Comparable<K>,V> extends ArbolMViasBusqueda<K,V> {
	
	public int cantidadDeNodosPadres(int nivelObjetivo) {
		return cantidadDeNodosPadres(this.raiz, nivelObjetivo, 0);
	}

	private int cantidadDeNodosPadres(NodoMVias<K, V> nodoActual, int nivelObjetivo, int nivelActual) {
		
		if (NodoMVias.esNodoVacio(nodoActual)) {
			return 0;
		}
		
		
		
		int cantidad = 0;
		for(int i = 0; i < orden; i++) {
			cantidad = cantidad + cantidadDeNodosPadres(nodoActual.getHijo(i), nivelObjetivo, nivelActual + 1);
		}
		
		if (nodoActual.cantidadDeHijosNoVacios() > 0  && nivelActual != nivelObjetivo) {
			return cantidad + 1;
		}
		
		return cantidad;
	}
	
	public static void main(String[] args) throws ExcepcionClaveYaExiste, ExcepcionClaveNoExiste {
		
		TerceraPregunta<Integer, String> prueba = new TerceraPregunta<>();
		int opcion = 0;
		int clave = 0;
		int orden =0;
		String valor;
		Scanner teclado = new Scanner(System.in);
		do {
			System.out.println("   ARBOL M-VIAS BUSQUEDA  ");
			System.out.println("1.-   insertar");
			System.out.println("2.-   eliminar");
			System.out.println("3.-   Recorrido en InOrden");
			System.out.println("4.-   Recorrido por niveles");
			System.out.println("5.-   salir");
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
				System.out.println("Recorrido InOrden : " + prueba.recorridoEnInOrden());
				break;
			
			case 4:
				System.out.println("recorrido Por Niveles : " + prueba.recorridoPorNiveles());

			default:
				break;
			}
			
		}
		
		while (opcion != 5);
	
	}	
	
	
}
