package bo.edu.uagrm.inf310.sb.primerParcial;

import java.util.Scanner;
import java.util.Stack;

import bo.edu.uagrm.inf310.sb.arboles.*;


public class SegundaPregunta <K extends Comparable<K>,V> extends AVL<K,V>{

	public void insertar(K clave, V valor) throws  ExcepcionClaveYaExiste {
		if (this.esArbolVacio()) {
			NodoBinario<K,V> nuevoNodo = new NodoBinario<> (clave, valor);
			this.raiz = nuevoNodo; 
			return;
		}
		NodoBinario<K,V> nodoActual = this.raiz;
		Stack<NodoBinario<K,V>> pilaDeNodos  = new Stack<> ();
		while (!NodoBinario.esNodoVacio(nodoActual)) {
			pilaDeNodos.push(nodoActual);
			if (clave.compareTo(nodoActual.getClave()) == 0) {
				throw new ExcepcionClaveYaExiste();
			}
			if (clave.compareTo(nodoActual.getClave()) < 0) {
				nodoActual = nodoActual.getHijoIzquierdo();
			}else {
				if (clave.compareTo(nodoActual.getClave()) > 0) {
				nodoActual = nodoActual.getHijoDerecho();
				}
			}
			
		
		}
		
		NodoBinario<K,V> nuevoNodo = new NodoBinario<>(clave, valor);
		nodoActual = pilaDeNodos.pop();
		if (clave.compareTo(nodoActual.getClave()) < 0) {
			nodoActual.setHijoIzquierdo(nuevoNodo);
		}else {
			nodoActual.setHijoDerecho(nuevoNodo);
		}
		
		while (!pilaDeNodos.isEmpty()) {
			NodoBinario<K,V> nodoPadre = pilaDeNodos.pop();
			NodoBinario<K,V> posibleHijo = this.balancear(nodoActual);
			if (posibleHijo.getClave().compareTo(nodoPadre.getClave()) < 0) {
				
				nodoPadre.setHijoIzquierdo(posibleHijo);
			}else {
				nodoPadre.setHijoDerecho(posibleHijo);
			}
			nodoActual = nodoPadre;
		}	
		
		this.raiz = this.balancear(nodoActual);
	
	}
	
	public static void main(String[] args) throws ExcepcionClaveYaExiste, ExcepcionClaveNoExiste {
		
		SegundaPregunta<Integer, String> prueba = new SegundaPregunta<>();
		int opcion = 0;
		int clave = 0;
		String valor;
		Scanner teclado = new Scanner(System.in);
		do {
			System.out.println("   AVL  ");
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
	


