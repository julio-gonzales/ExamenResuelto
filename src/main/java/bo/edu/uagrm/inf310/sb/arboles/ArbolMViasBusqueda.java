package bo.edu.uagrm.inf310.sb.arboles;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ArbolMViasBusqueda  <K extends Comparable<K>,V > implements IArbolBusqueda<K,V>{

	protected NodoMVias<K,V> raiz;
	protected int orden;
	
	public ArbolMViasBusqueda() {
		this.orden = 3;
	}
	
	public ArbolMViasBusqueda(int orden) throws ExcepcionOrdenInvalido {
		if (orden < 3) {
			throw new ExcepcionOrdenInvalido();
		}
		this.orden = orden;
	}
	
	
	protected NodoMVias<K,V> nodoVacioParaElArbol(){
		return (NodoMVias <K, V>) NodoMVias.nodoVacio();
	}
	
	protected K datoVacioParaElArbol() {
		return (K) NodoMVias.datoVacio();
	}
	
	@Override
	public void insertar(K clave, V valor) throws ExcepcionClaveYaExiste {
		if (this.esArbolVacio()) {
			this.raiz = new NodoMVias<>(orden, clave, valor);
			return;
		}
		
		NodoMVias<K,V> nodoActual = this.raiz;
		while (!NodoMVias.esNodoVacio(nodoActual)) {
			if (this.existeClaveEnNodo(nodoActual, clave)) {
				throw new ExcepcionClaveYaExiste();
			}
			//si llegamos a este punto la clave no esta en el nodoActual
			if (nodoActual.esHoja()) {
				if (nodoActual.estanDatosLlenos()) {
					//no hay campo ´para la clave-valor
					int posicionPorDondeBajar = this.porDondeBajar(nodoActual, clave);
					NodoMVias<K,V> nuevoHijo = new NodoMVias<>(orden, clave, valor);
					nodoActual.setHijo(posicionPorDondeBajar, nuevoHijo);
				}else {
					//si hay campo para insertar la clave - valor
					this.insertarEnOrden(nodoActual, clave, valor);
				}
				nodoActual = NodoMVias.nodoVacio();
			}else {
				int posicionPorDondeBajar = this.porDondeBajar(nodoActual, clave);
				
				if (nodoActual.esHijoVacio(posicionPorDondeBajar)) {	
					NodoMVias<K,V> nuevoHijo = new NodoMVias<>(orden, clave, valor);
					nodoActual.setHijo(posicionPorDondeBajar, nuevoHijo);
					nodoActual = NodoMVias.nodoVacio();
				} else {
					nodoActual = nodoActual.getHijo(posicionPorDondeBajar);
		
				}
			}
			
		}
		
	}

	protected int porDondeBajar(NodoMVias<K, V> nodoActual, K clave) {

		for (int i = 0; i < nodoActual.cantidadDeDatosNoVacios() ; i++) {
			if (clave.compareTo(nodoActual.getClave(i)) < 0) {
				return i;
			}
		}
		return nodoActual.cantidadDeDatosNoVacios();
	}

	protected void insertarEnOrden(NodoMVias<K, V> nodoActual, K clave, V valor) {
		
		for (int i = 0; i < nodoActual.cantidadDeDatosNoVacios(); i ++) {
			if (clave.compareTo(nodoActual.getClave(i)) < 0) {
				for (int j = nodoActual.cantidadDeDatosNoVacios(); j > i ; j-- ) {
					nodoActual.setClave(j, nodoActual.getClave(j - 1));
					nodoActual.setValor(j, nodoActual.getValor(j - 1));
					//un supuesto de si quiero insertar algo mas
					nodoActual.setHijo(j+1, nodoActual.getHijo(j));
				}
				nodoActual.setClave(i, clave);
				nodoActual.setValor(i, valor);
				return;
			}
		}
		nodoActual.setClave(nodoActual.cantidadDeDatosNoVacios(), clave);
		nodoActual.setValor(nodoActual.cantidadDeDatosNoVacios() - 1, valor);
		
	}

	protected boolean existeClaveEnNodo(NodoMVias<K, V> nodoActual, K clave) {
		for ( int i = 0; i < orden - 1; i++) {
			if (!nodoActual.esDatoVacio(i)) {
				//preguntando la clave en la posicion i no es vacia
				K claveEnTurno = nodoActual.getClave(i);
				if (claveEnTurno.compareTo(clave) == 0) {
					return true;
				}
			}
		}
		
		return false;
	}

	@Override
	public V eliminar(K clave) throws ExcepcionClaveNoExiste {
		V valorARetornar = buscar(clave);
		if (valorARetornar == null) {
			throw new ExcepcionClaveNoExiste();
		}
		this.raiz = eliminar(this.raiz, clave);
		return valorARetornar;
	}

	private NodoMVias<K, V> eliminar(NodoMVias<K, V> nodoActual, K claveAEliminar) {
		
		for (int i = 0; i < nodoActual.cantidadDeDatosNoVacios(); i++) {
			K claveActual = nodoActual.getClave(i);
			if (claveAEliminar.compareTo(claveActual) == 0) {
				if (nodoActual.esHoja()) {
					this.eliminarDatoDeNodo(nodoActual, i);
					if (nodoActual.estanDatosVacios()) {
						return NodoMVias.nodoVacio();
					}
					return nodoActual; 
				}
				K claveDeReemplazo;
				if (this.hayHijosMasAdelante(nodoActual, i)) {
					claveDeReemplazo = this.buscarSucesoInOrden(nodoActual, claveAEliminar);
				} else {
					claveDeReemplazo = this.buscarPredecesorInOrden(nodoActual, claveAEliminar);
				}
				
				V valorDeRemplazo = this.buscar(claveDeReemplazo);
				nodoActual = eliminar(nodoActual, claveDeReemplazo);
				nodoActual.setClave(i, claveDeReemplazo);
				nodoActual.setValor(i, valorDeRemplazo);
				return nodoActual;
			}
			
			if (claveAEliminar.compareTo(claveActual) < 0) {
				NodoMVias<K,V> supuestoNuevoHijo = this.eliminar(nodoActual.getHijo(i), claveAEliminar);
				nodoActual.setHijo(i, supuestoNuevoHijo);
				return nodoActual;
			}
		}
		
		NodoMVias<K,V> supuestoNuevoHijo = this.eliminar(nodoActual.getHijo(orden - 1), claveAEliminar);
		nodoActual.setHijo(orden - 1, supuestoNuevoHijo);
		return nodoActual;
	}

	private K buscarPredecesorInOrden(NodoMVias<K, V> nodoActual, K claveAEliminar) {
		for (int i = 0; i < nodoActual.cantidadDeDatosNoVacios(); i++) {
			K claveActual = nodoActual.getClave(i);
			if (claveAEliminar.compareTo(claveActual) == 0) {
	
					if (nodoActual.esHijoVacio(i)) {
						return nodoActual.getClave(i - 1);
					}else {
						nodoActual = nodoActual.getHijo(i);
						while (!NodoMVias.esNodoVacio(nodoActual.getHijo(nodoActual.cantidadDeDatosNoVacios()))) {
							nodoActual = nodoActual.getHijo(nodoActual.cantidadDeDatosNoVacios());
						}
						return nodoActual.getClave(nodoActual.cantidadDeDatosNoVacios() - 1);
					}
				
			}
		}
		return null;
	}

	private K buscarSucesoInOrden(NodoMVias<K, V> nodoActual, K claveAEliminar) {
		
		for (int i = 0; i < nodoActual.cantidadDeDatosNoVacios(); i++) {
			K claveActual = nodoActual.getClave(i);
			if (claveAEliminar.compareTo(claveActual) == 0) {
				if (nodoActual.esHijoVacio(i + 1)) {
					return nodoActual.getClave(i + 1);
				}else {
					nodoActual = nodoActual.getHijo(i + 1);
					while (!NodoMVias.esNodoVacio(nodoActual.getHijo(0))) {
						nodoActual = nodoActual.getHijo(0);
					}
					return nodoActual.getClave(0);
				}
			}
		}
		return null;
	}

	private boolean hayHijosMasAdelante(NodoMVias<K, V> nodoActual, int i) {
		for (int j = i + 1; j <= nodoActual.cantidadDeDatosNoVacios(); j++) {
			if (!nodoActual.esHijoVacio(j)) {
				return true;
			}
		}
		return false;
	}

	protected void eliminarDatoDeNodo(NodoMVias<K, V> nodoActual, int i) {
		for (int j = i; j < nodoActual.cantidadDeDatosNoVacios() - 1; j++) {
			nodoActual.setClave(j, nodoActual.getClave(j + 1));
			nodoActual.setValor(j, nodoActual.getValor(j + 1));
		}
		nodoActual.setClave(nodoActual.cantidadDeDatosNoVacios() - 1, (K)NodoMVias.datoVacio());
		nodoActual.setValor(nodoActual.cantidadDeDatosNoVacios(), (V)NodoMVias.datoVacio());
		
	}

	@Override
	public V buscar(K clave) {
		NodoMVias<K,V> nodoActual = this.raiz;
		
		while (!NodoMVias.esNodoVacio(nodoActual)) {
			
			NodoMVias<K,V> nodoAnterior = nodoActual;
			for (int i = 0; i< nodoActual.cantidadDeDatosNoVacios() && 
					nodoActual == nodoAnterior; i++) {
				
				K claveActual = nodoActual.getClave(i);
				if (claveActual.compareTo(clave) == 0) {
					return nodoActual.getValor(i);
				}
				if (clave.compareTo(claveActual) < 0) {
					if (nodoActual.esHijoVacio(i)) {
						return (V) NodoMVias.datoVacio();
					}
					nodoActual = nodoActual.getHijo(i);
				}
			}
			//fin del for
			if (nodoActual == nodoAnterior) {
				nodoActual = nodoActual.getHijo(orden - 1);
			}
		}
		
		return (V) NodoMVias.datoVacio();
		
		/*
		V valorARetornar = buscar(this.raiz, clave);
		return valorARetornar;
		*/	
	}

	//metodo amigo de buscar 
	private V buscar(NodoMVias<K, V> nodoActual, K clave) {
		
		if ( !NodoMVias.esNodoVacio(nodoActual) ) {
			return null;
		}
		
		for (int i = 0; i < nodoActual.cantidadDeDatosNoVacios() - 1 ; i++) {
			
			if (clave.compareTo(nodoActual.getClave(i)) == 0) {
				return nodoActual.getValor(i);
			}
			
			if (clave.compareTo(nodoActual.getClave(i)) <  0) {
				buscar(nodoActual.getHijo(i), clave);
			}
			
		}
		
		//hay que cambiar el indice por que tiene que revisar el ultimo dato
		
		buscar(nodoActual.getHijo(1), clave);
		return null;
	}

	@Override
	public boolean contiene(K clave) {

		return this.buscar(clave) != NodoMVias.datoVacio();
	}

	@Override
	public List<K> recorridoEnInOrden() {
		List<K> recorrido = new LinkedList<>();
		recorridoEnInOrden(this.raiz, recorrido);
		return recorrido;
	}

	private void recorridoEnInOrden(NodoMVias<K, V> nodoActual, List<K> recorrido) {
		if (NodoMVias.esNodoVacio(nodoActual)) {
			return;
		}
		
		for (int i = 0; i < nodoActual.cantidadDeDatosNoVacios(); i++) {
			recorridoEnInOrden(nodoActual.getHijo(i), recorrido);
			recorrido.add(nodoActual.getClave(i));
		}
		recorridoEnInOrden(nodoActual.getHijo(nodoActual.cantidadDeDatosNoVacios()), recorrido);
	}

	@Override
	public List<K> recorridoEnPreOrden() {
		List<K> recorrido = new LinkedList<>();
		recorridoEnPreOrden(this.raiz, recorrido);
		return recorrido;
	}
	
	private void recorridoEnPreOrden(NodoMVias<K, V> nodoActual, List<K> recorrido) {
		
		if (NodoMVias.esNodoVacio(nodoActual)) {
			return;
		}
		
		for (int i = 0; i< nodoActual.cantidadDeDatosNoVacios() ; i++) {
			recorrido.add(nodoActual.getClave(i));
			recorridoEnPreOrden(nodoActual.getHijo(i) , recorrido);
		}	
		recorridoEnPreOrden(nodoActual.getHijo(nodoActual.cantidadDeDatosNoVacios()), recorrido);
	}

	@Override
	public List<K> recorridoEnPostOrden() {
		List<K> recorrido = new LinkedList<>();
		recorridoEnPostOrden(this.raiz, recorrido);
		return recorrido;
	}

	private void recorridoEnPostOrden(NodoMVias<K, V> nodoActual, List<K> recorrido) {
		if (NodoMVias.esNodoVacio(nodoActual)) {
			return;
		}
		recorridoEnPostOrden(nodoActual.getHijo(0), recorrido);
		for (int i = 0; i < nodoActual.cantidadDeDatosNoVacios(); i++) {
			recorridoEnPostOrden(nodoActual.getHijo(i + 1), recorrido);
			recorrido.add(nodoActual.getClave(i));
		}
		
	}

	@Override
	public List<K> recorridoPorNiveles() {
		List<K> recorrido = new LinkedList<>();
		if (this.esArbolVacio()) {
			System.out.println("entro aqui");
			return recorrido;
			
		}
		
		Queue<NodoMVias<K,V>> colaDeNodos = new LinkedList<>();
		colaDeNodos.offer(this.raiz);
		while ( !colaDeNodos.isEmpty()) {
			NodoMVias<K,V> nodoActual = colaDeNodos.poll();
			for (int i = 0; i < nodoActual.cantidadDeDatosNoVacios(); i++ ) {
				recorrido.add(nodoActual.getClave(i));
				if (!nodoActual.esHijoVacio(i)) {
					colaDeNodos.offer(nodoActual.getHijo(i));
				}
				
			}
			
			if (!nodoActual.esHijoVacio(nodoActual.cantidadDeDatosNoVacios())) {
				colaDeNodos.offer(nodoActual.getHijo(nodoActual.cantidadDeDatosNoVacios()));
			}
		}
		return recorrido;
	}

	@Override
	public int size() {
		
		return size(this.raiz);
	}

	private int size(NodoMVias<K, V> nodoActual) {
		if (NodoMVias.esNodoVacio(nodoActual)) {
			return 0;
		}
		
		int cantidad = 0;
		for (int i = 0; i < orden; i++) {
			cantidad = cantidad + size(nodoActual.getHijo(i));
		}
		cantidad = cantidad + 1;
		return cantidad;
	}

	@Override
	public int altura() {
		
		return altura(this.raiz);
	}

	private int altura(NodoMVias<K, V> nodoActual) {
		if (NodoMVias.esNodoVacio(nodoActual)) {
			return 0;
		}
		 int alturaMayor = 0;
		 for (int i = 0; i < orden; i++) {
			 int alturaDeHijo = altura(nodoActual.getHijo(i));
			 if (alturaDeHijo > alturaMayor) {
				 alturaMayor = alturaDeHijo;
			 }
		 }
		
		return alturaMayor + 1;
	}

	@Override
	public void vaciar() {
		this.raiz = this.nodoVacioParaElArbol();
	}


	@Override
	public boolean esArbolVacio() {

		return NodoMVias.esNodoVacio(this.raiz);
	}

	@Override
	public int nivel() {
		
		return nivel(this.raiz);
	}

	private int nivel(NodoMVias<K, V> nodoActual) {
		if (NodoMVias.esNodoVacio(nodoActual)) {
			return -1;
		}
		int nivelMayor = -1;
		for (int i=0; i < orden; i++) {
			int nivelActual = nivel(nodoActual.getHijo(i));
			if (nivelActual > nivelMayor) {
				nivelMayor = nivelActual;
			}
		}
		nivelMayor = nivelMayor + 1;
		return nivelMayor;
	}
	
	public int hojasAPartirDelNivel (int nivelObjetivo) {
		return hojasAPartirDelNivel(this.raiz, nivelObjetivo, 0);
	}

	private int hojasAPartirDelNivel(NodoMVias<K, V> nodoActual, int nivelObjetivo, int nivelActual) {
		if (NodoMVias.esNodoVacio(nodoActual)) {
			return 0;
		}
		
		if (nivelActual >= nivelObjetivo) {
			return 1;
		}
		int cantidadDeHojas = 0;
		for (int i = 0; i < orden; i++) {
			cantidadDeHojas = cantidadDeHojas +
					this.hojasAPartirDelNivel(nodoActual.getHijo(i), nivelObjetivo, nivelActual + 1);
		}
		
		return cantidadDeHojas;
	}
	
	//6. Implemente un método recursivo que retorne la cantidad nodos con datos vacíos en un árbol m-vias de bússqueda
	
	public int cantidadDeNodosConDatosVacios() {
		return cantidadDeNodosConDatosVacios(this.raiz);
	}
	
	private int cantidadDeNodosConDatosVacios (NodoMVias<K,V> nodoActual) {
		if (NodoMVias.esNodoVacio(nodoActual)) {
			return 0;
		}
		
		if (nodoActual.cantidadDeDatosNoVacios() < orden - 1) {
			return 1;
		}
		
		int cantidadDeNodos = 0;
		for (int i = 0; i < orden; i++) {
			cantidadDeNodos = cantidadDeNodos + cantidadDeNodosConDatosVacios(nodoActual.getHijo(i)); 
		}
		
		return cantidadDeNodos;
	}
	
	//9. Implemente un método que retorne verdadero si solo hay hojas
	//en el último nivel de un árbol m-vias de búsqueda. Falso en caso contrario.
	
	public boolean soloHayHojasEnUltimoNivel() {
		if (this.esArbolVacio()) {
			return false;
		}
		return soloHayHojasEnUltimoNivel(this.raiz, 0);
	}

	private boolean soloHayHojasEnUltimoNivel(NodoMVias<K, V> nodoActual, int nivelActual) {
		if (NodoMVias.esNodoVacio(nodoActual)) {
			return true;
		}
		
		if (nodoActual.esHoja() && nivelActual != nivel()) {
			return false;
		}
		
		boolean sw = true;
		for (int i = 0; i < orden ; i ++) {
			
			sw = soloHayHojasEnUltimoNivel(nodoActual.getHijo(i), nivelActual + 1);
			if (!sw) {
				i = orden;
			}
			
		}
		return sw;
	}
	
	//10. Implemente un método que retorne verdadero si un árbol m-vias esta balanceado 
	//según las reglas de un árbol B. Falso en caso contrario.
	
	public boolean estaBalanceado () {
		if (!this.soloHayHojasEnUltimoNivel()) {
			return false;
		}
		Queue<NodoMVias<K,V>> colaDeNodos = new LinkedList<>();
		colaDeNodos.offer(this.raiz);
		while ( !colaDeNodos.isEmpty()) {
			NodoMVias<K,V> nodoActual = colaDeNodos.poll();
			for (int i = 0; i < nodoActual.cantidadDeDatosNoVacios(); i++ ) {
				if (nodoActual.cantidadDeHijosNoVacios() < nodoActual.cantidadDeDatosNoVacios() &&
						!nodoActual.esHoja()) {
					return false;
				}
				if (nodoActual.esHoja()) {
					if (nodoActual.cantidadDeDatosNoVacios() < ((orden - 1)/ 2)) {
						return false;
					}
				}
				if (!nodoActual.esHijoVacio(i)) {
					colaDeNodos.offer(nodoActual.getHijo(i));
				}
				
			}
			
			if (!nodoActual.esHijoVacio(orden - 1)) {
				colaDeNodos.offer(nodoActual.getHijo(orden - 1));
			}
		}
		
		return true;
	}
	

	//11. Implemente un método privado que reciba un dato como parámetro y que retorne cual seria el 
	//predecesor inorden de dicho dato, sin realizar el recorrido en inOrden.
	
	private K predecesorInOrden(K datoABuscar) throws ExcepcionClaveNoExiste {
		
		NodoMVias<K,V> nodoActual = this.raiz;
		while (!NodoMVias.esNodoVacio(nodoActual)) {
			NodoMVias<K,V> nodoAnterior = nodoActual;
			for (int i= 0; i < orden - 1 && nodoAnterior == nodoActual ; i++) {
				if (datoABuscar.compareTo(nodoActual.getClave(i)) == 0) {
					if (!nodoActual.esHijoVacio(i)) {
						nodoActual = nodoActual.getHijo(i);
						while (!NodoMVias.esNodoVacio(nodoActual.getHijo(orden - 1))) {
							nodoActual = nodoActual.getHijo(orden - 1);
						}
						return nodoActual.getClave(nodoActual.cantidadDeDatosNoVacios() - 1);
					}
					if (i > 0) {
						return nodoActual.getClave(i - 1);
					}
					
				}
				if (datoABuscar.compareTo(nodoActual.getClave(i)) < 0) {
					nodoActual = nodoActual.getHijo(i);
				}
			}
			if (nodoActual == nodoAnterior) {
				nodoActual = nodoActual.getHijo(orden - 1);
			}
		}
		
		return null;
	}

	//12. Para un árbol m-vias implemente un método que retorne la cantidad de nodos que tienen todos sus hijos 
	//distintos de vacío luego del nivel N (La excepción a la regla son las hojas).
	
	public int nodosConHijosDistintosDeVacios(int nivel) {
		return hijosDistintosDeVacios(this.raiz, nivel, 0);
	}
	
	private int hijosDistintosDeVacios(NodoMVias<K,V> nodoActual, int nivelObjetivo, int nivelActual ) {
		if (NodoMVias.esNodoVacio(nodoActual)) {
			return 0;
		}
		
		if (nodoActual.cantidadDeHijosNoVacios() == orden && !nodoActual.esHoja() && nivelActual > nivelObjetivo) {
			return 1;
		}
		int cantidad = 0;
		for (int i = 0; i < orden; i++) {
			cantidad = cantidad + hijosDistintosDeVacios(nodoActual.getHijo(i), nivelObjetivo, nivelActual +1);
		}
		
		return cantidad;
	}
	
}

