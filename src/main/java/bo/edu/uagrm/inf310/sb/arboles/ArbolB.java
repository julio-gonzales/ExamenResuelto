package bo.edu.uagrm.inf310.sb.arboles;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class ArbolB <K extends Comparable<K>, V> extends ArbolMViasBusqueda<K,V> {
	
	private int nroMaximoDeDatos;
	private int nroMinimoDeDatos;
	private int nroMinimoDeHijos;
	
	public ArbolB() {
		super();
		this.nroMaximoDeDatos = 2;
		this.nroMinimoDeDatos = 1;
		this.nroMinimoDeHijos = 2;
	}
	
	public ArbolB(int orden) throws ExcepcionOrdenInvalido {
		super(orden);
		this.nroMaximoDeDatos = super.orden - 1;
		this.nroMinimoDeDatos = this.nroMaximoDeDatos / 2;
		this.nroMinimoDeHijos = this.nroMinimoDeDatos + 1;
	}
	
	@Override
	public void insertar(K clave, V valor) throws ExcepcionClaveYaExiste {
		if (this.esArbolVacio()) {
			this.raiz = new NodoMVias<> (orden + 1, clave, valor);
			return;
		}
		
		Stack<NodoMVias<K,V>> pilaDeAncestros = new Stack<>();
		NodoMVias<K,V> nodoActual = this.raiz;
		while (!NodoMVias.esNodoVacio(nodoActual)) {
			if (this.existeClaveEnNodo(nodoActual, clave)) {
				throw new ExcepcionClaveYaExiste();
			}
			
			if (nodoActual.esHoja()) {
				super.insertarEnOrden(nodoActual, clave, valor);
				if (nodoActual.cantidadDeDatosNoVacios() > this.nroMaximoDeDatos) {
					this.dividir(nodoActual, pilaDeAncestros);
				}
				nodoActual = NodoMVias.nodoVacio();
			} else {
				int posicionPorDondeBajar = this.porDondeBajar(nodoActual, clave);
				pilaDeAncestros.push(nodoActual);
				nodoActual = nodoActual.getHijo(posicionPorDondeBajar);
			}
			
		}//fin del while
		
	}

	private void dividir(NodoMVias<K, V> nodoActual, Stack<NodoMVias<K, V>> pilaDeAncestros) {
		int pivote = nroMaximoDeDatos / 2;
		if (pilaDeAncestros.isEmpty()) {
			NodoMVias<K,V> padre = new NodoMVias<>(orden +1, nodoActual.getClave(pivote), nodoActual.getValor(pivote));
			NodoMVias<K,V> hijoDer = new NodoMVias<>(orden + 1);
			int j = 0;
			for (int i = pivote + 1; i < orden; i++) {
				hijoDer.setClave(j, nodoActual.getClave(i));
				hijoDer.setValor(j, nodoActual.getValor(i));
				hijoDer.setHijo(j, nodoActual.getHijo(i));
				nodoActual.setClave(i, null);
				nodoActual.setValor(i, null);
				nodoActual.setHijo(i, null);
				j++;
			}
			hijoDer.setHijo(j, nodoActual.getHijo(orden));
			this.raiz = padre;
			nodoActual.setClave(pivote, null);
			nodoActual.setValor(pivote, null);
			this.raiz.setHijo(0, nodoActual);
			this.raiz.setHijo(raiz.cantidadDeDatosNoVacios(), hijoDer);
			
		} else {
			
			NodoMVias<K,V> nodoAncestro = pilaDeAncestros.pop();
			NodoMVias<K,V> hijoDer = new NodoMVias<>(orden + 1);
			//pasamos los valores derechos del pivote  al nuevo nodo
			int j = 0;
			for (int i = pivote + 1; i < orden; i++) {
				hijoDer.setClave(j, nodoActual.getClave(i));
				hijoDer.setValor(j, nodoActual.getValor(i));
				hijoDer.setHijo(j, nodoActual.getHijo(i));
				nodoActual.setClave(i, null);
				nodoActual.setValor(i, null);
				nodoActual.setHijo(i, null);
				j++;
			}
			hijoDer.setHijo(j, nodoActual.getHijo(orden));
			nodoActual.setHijo(orden, null);
			
			
			this.insertarEnOrden(nodoAncestro, nodoActual.getClave(pivote), nodoActual.getValor(pivote));
			K clavePivote = nodoActual.getClave(pivote);
			V valorPivote = nodoActual.getValor(pivote);
			int posicionDonSetear = this.porDondeBajar(nodoAncestro, clavePivote);
			nodoAncestro.setHijo(posicionDonSetear - 1, nodoActual);
			nodoAncestro.setHijo(posicionDonSetear, hijoDer);
			nodoActual.setClave(pivote, null);
			nodoActual.setValor(pivote, null);
			if (nodoAncestro.cantidadDeDatosNoVacios() > nroMaximoDeDatos) {
				dividir(nodoAncestro, pilaDeAncestros);
			}else {
				return;
			}
			
		}
		
	}
	
	@Override
	public V eliminar(K claveAEliminar) throws ExcepcionClaveNoExiste {
		Stack<NodoMVias<K,V>> pilaDeAncestros = new Stack<>();
		NodoMVias<K,V> nodoActual = this.buscarNodoDeLaClave(claveAEliminar, pilaDeAncestros);
		
		if (NodoMVias.esNodoVacio(nodoActual)) {
			throw new ExcepcionClaveNoExiste();
		}
		
		int posicionDeClaveEnElNodo = this.porDondeBajar(nodoActual, claveAEliminar) - 1;
		V valorARetornar = nodoActual.getValor(posicionDeClaveEnElNodo);
		
		if (nodoActual.esHoja()) {
			super.eliminarDatoDeNodo(nodoActual, posicionDeClaveEnElNodo);
			if (nodoActual.cantidadDeDatosNoVacios() < nroMinimoDeDatos) {
				//problemas
				if (pilaDeAncestros.isEmpty()) {
					if (nodoActual.estanDatosVacios()) {
						super.vaciar();
					}else {
						this.prestarOFusionar(nodoActual, pilaDeAncestros);
					}
				}
			}
		} else {
			//nodoActual no es hoja
			pilaDeAncestros.push(nodoActual);
			NodoMVias<K,V> nodoDelPredecesor = this.buscarNodoDelPredecesor(pilaDeAncestros, 
					nodoActual.getHijo(posicionDeClaveEnElNodo));
			int posicionDelPredecesor = nodoActual.cantidadDeDatosNoVacios() - 1;
			K clavePredecesora = nodoDelPredecesor.getClave(posicionDelPredecesor);
			V valorPredecesor = nodoDelPredecesor.getValor(posicionDelPredecesor);
			super.eliminarDatoDeNodo(nodoDelPredecesor, posicionDelPredecesor);
			nodoActual.setClave(posicionDeClaveEnElNodo, clavePredecesora);
			nodoActual.setValor(posicionDelPredecesor,valorPredecesor);
			if(nodoDelPredecesor.cantidadDeDatosNoVacios() < nroMinimoDeDatos) {
				this.prestarOFusionar(nodoDelPredecesor, pilaDeAncestros);
			}
			
		}
		
		return null;
	}
	
	private NodoMVias<K, V> buscarNodoDelPredecesor(Stack<NodoMVias<K, V>> pilaDeAncestros, NodoMVias<K, V> hijo) {
		
		return null;
	}

	private void prestarOFusionar(NodoMVias<K, V> nodoActual, Stack<NodoMVias<K, V>> pilaDeAncestros) {
		
		
	}

	private NodoMVias<K, V> buscarNodoDeLaClave(K claveABuscar, Stack<NodoMVias<K, V>> pilaDeAncestros) {
		NodoMVias<K,V> nodoActual = this.raiz;
		while (!NodoMVias.esNodoVacio(nodoActual)) {
			int tamanhioDeNodoActual = nodoActual.cantidadDeDatosNoVacios();
			NodoMVias<K,V> nodoAnterior = nodoActual;
			for (int i = 0; i <tamanhioDeNodoActual && nodoAnterior == nodoActual; i++) {
				K claveActual = nodoActual.getClave(i);
				if (claveABuscar.compareTo(claveActual) == 0) {
					return nodoActual;
				}
				
				if (claveABuscar.compareTo(claveActual) < 0) {
					if (!nodoActual.esHoja()) {
						pilaDeAncestros.push(nodoActual);
						nodoActual = nodoActual.getHijo(i);
					} else {
						nodoActual = NodoMVias.nodoVacio();
					}
				}
			}//fin del for
			if (nodoActual == nodoAnterior) {
				if (!nodoActual.esHoja()) {
					pilaDeAncestros.push(nodoActual);
					nodoActual = nodoActual.getHijo(tamanhioDeNodoActual);
				}else {
					nodoActual = NodoMVias.nodoVacio();
				}
			}
			
		}
		
		
		return NodoMVias.nodoVacio();
	}

	//5. Implemente un método recursivo que retorne la cantidad nodos con datos vacíos en un árbol B

	public int cantidadDeNodosConDatosVaciosB() {
		return cantidadDeNodosConDatosVaciosB(this.raiz);
	}
	
	private int cantidadDeNodosConDatosVaciosB(NodoMVias<K, V> nodoActual) {
		if (NodoMVias.esNodoVacio(nodoActual)) {
			return 0;
		}
		int cantidad =0;
		for (int i = 0 ; i < orden ; i++) {
			cantidad = cantidad + cantidadDeNodosConDatosVaciosB(nodoActual.getHijo(i));
			
		}
		if (nodoActual.cantidadDeDatosNoVacios() < orden -1 ) {
			return  cantidad + 1;
		}
		return cantidad;
	}
	
	//7. Implemente un método recursivo que retorne la cantidad nodos con datos vacíos 
	//en un árbol B, pero solo en el nivel N
	
	public int nodoConDatosVaciosRec(int nivel) {
		return nodoConDatosVacios(this.raiz, nivel, 0) ;
		
	}

	private int nodoConDatosVacios(NodoMVias<K, V> nodoActual, int nivel, int nivelActual) {
		if (NodoMVias.esNodoVacio(nodoActual)) {
			return 0;
		}
		
		if (nivelActual == nivel && nodoActual.cantidadDeDatosNoVacios() < orden - 1) {
			return 1;
		}
		
		int cantidad = 0;
		for (int i = 0; i < orden; i++ ) {
			cantidad = cantidad + nodoConDatosVacios(nodoActual.getHijo(i), nivel, nivelActual + 1);
		}
		
		return cantidad;
	}
	//8. Implemente un método iterativo que retorne la cantidad nodos con datos vacíos en un árbol b, 
	//pero solo en el nivel N

	public int nodoConDatosVaciosIte(int nivel) {
		if (this.esArbolVacio()) {
			return 0;
		}
		
		NodoMVias<K,V> nodoActual = this.raiz;
		int cantidad =0;
		int nivelActual = -1;
		Stack<NodoMVias<K,V>> pilaDeNodos = new Stack<>();
		while (!NodoMVias.esNodoVacio(nodoActual)) {
			pilaDeNodos.push(nodoActual);
			nodoActual = nodoActual.getHijo(0);
			nivelActual++;
		}
		
		while (!pilaDeNodos.isEmpty()) {
			nodoActual = pilaDeNodos.pop();
			if (nivel == nivelActual && nodoActual.cantidadDeDatosNoVacios() < orden -1) {
				cantidad++;
			}
			for (int i= 1; i < nodoActual.cantidadDeDatosNoVacios(); i++) {
				if (!nodoActual.esHijoVacio(i)) {
					nodoActual = nodoActual.getHijo(i);
					while (!NodoMVias.esNodoVacio(nodoActual)) {
						pilaDeNodos.push(nodoActual);
						nodoActual = nodoActual.getHijo(0);
						nivelActual ++;
					}
				}
				
			}
			nivelActual --;
		}
	
		
		return cantidad;
			
	}
	


	
	
	/*
	private NodoMVias<K, V> dividirHijosDer(NodoMVias<K, V> nodoActual, int puntoDeDivision) {
		NodoMVias<K,V> nodoARetornar = new NodoMVias<>(orden + 1);
		int j = 0;
		for (int i = puntoDeDivision + 1; i < nodoActual.cantidadDeDatosNoVacios(); i++) {
			nodoARetornar.setClave(j, nodoActual.getClave(i));
			nodoARetornar.setValor(j, nodoActual.getValor(i));
			nodoARetornar.setHijo(j, nodoActual.getHijo(i));
			j++;
		}
		nodoARetornar.setHijo(j , nodoActual.getHijo(nodoActual.cantidadDeDatosNoVacios()));
		return nodoARetornar;
		
	}

	private NodoMVias<K, V> dividirHijosIzq(NodoMVias<K, V> nodoActual, int puntoDeDivision) {
		NodoMVias<K,V> nodoARetornar = new NodoMVias<>(orden + 1);
		for (int i = 0; i < puntoDeDivision; i++) {
			nodoARetornar.setClave(i, nodoActual.getClave(i));
			nodoARetornar.setValor(i, nodoActual.getValor(i));
			nodoARetornar.setHijo(i, nodoActual.getHijo(i));
		}
		nodoARetornar.setHijo(puntoDeDivision, nodoActual.getHijo(puntoDeDivision));
		return nodoARetornar;
	}
	*/
	
	
	
	
	/*
	int n = nodoAncestro.cantidadDeDatosNoVacios();
	for (int i = 0; i < n; i++) {
		if (nodoActual.getClave(pivote).compareTo(nodoAncestro.getClave(i)) < 0) {
			System.out.println(nodoAncestro.cantidadDeDatosNoVacios());
			for (int k = nodoAncestro.cantidadDeDatosNoVacios(); k > i; k--) {
				nodoAncestro.setClave(k , nodoAncestro.getClave(k - 1));
				nodoAncestro.setValor(k , nodoAncestro.getValor(k - 1));
				nodoAncestro.setHijo(k + 1 , nodoAncestro.getHijo(k));
			}
			nodoAncestro.setClave(i, nodoActual.getClave(pivote));
			nodoAncestro.setValor(i, nodoActual.getValor(pivote));
			nodoAncestro.setHijo(i + 1, hijoDer);
		}
		
	}
	
	if (n == nodoAncestro.cantidadDeDatosNoVacios()) {
		nodoAncestro.setClave(n, nodoActual.getClave(pivote));
		nodoAncestro.setValor(n, nodoActual.getValor(pivote));
		nodoAncestro.setHijo(n + 1, hijoDer);
	}*/

	
}
