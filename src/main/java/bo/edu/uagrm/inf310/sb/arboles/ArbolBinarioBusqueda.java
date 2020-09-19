package bo.edu.uagrm.inf310.sb.arboles;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class ArbolBinarioBusqueda <K extends Comparable<K>,V > implements IArbolBusqueda<K,V>  {
	protected  NodoBinario<K,V> raiz ;
	
	protected NodoBinario<K,V> nodoVacioParaElArbol() {
		return (NodoBinario<K,V>) NodoBinario.nodoVacio();
	}
	@Override
	public void insertar(K clave, V valor) throws ExcepcionClaveYaExiste {
		// TODO Auto-generated method stub
		raiz = insertar(this.raiz,clave,valor);
	}

	private NodoBinario<K,V> insertar(NodoBinario<K, V> nodoActual,K clave,V valor) throws ExcepcionClaveYaExiste{
		// TODO Auto-generated method stub
		if (NodoBinario.esNodoVacio(nodoActual)) {
			//insertar
			NodoBinario<K,V> nuevoNodo = new NodoBinario<K,V>(clave,valor);
			return nuevoNodo;
		}
		K claveActual = nodoActual.getClave();
		if (clave.compareTo(claveActual) > 0) {
			
			NodoBinario<K,V> nodoNuevo = insertar(nodoActual.getHijoDerecho(),clave,valor);
			nodoActual.setHijoDerecho(nodoNuevo);
			return nodoActual;
		}
		if (clave.compareTo(claveActual) < 0) {
			NodoBinario<K,V> nodoNuevo = insertar(nodoActual.getHijoIzquierdo(),clave,valor);
			nodoActual.setHijoIzquierdo(nodoNuevo);
			return nodoActual;
		}
		
			throw new ExcepcionClaveYaExiste(); 
		
	}
	@Override
	public V eliminar(K clave) throws ExcepcionClaveNoExiste {

		V valorARetornar = buscar(clave);
		System.out.println("elimnando" + valorARetornar);	
		if (valorARetornar == null) {
			throw new ExcepcionClaveNoExiste();
		}
		this.raiz = eliminar(this.raiz, clave);
		
		return valorARetornar;
	}

	private NodoBinario<K, V> eliminar(NodoBinario<K, V> nodoActual, K clave) throws ExcepcionClaveNoExiste {
		if (NodoBinario.esNodoVacio(nodoActual)) {
			throw new ExcepcionClaveNoExiste();
		}
		
		K claveActual = nodoActual.getClave();
		//prguntamos si la clave que estamos buscando es mayor o menor para avanzar en el arbol
		if (clave.compareTo(claveActual) > 0) {
			NodoBinario<K,V> supuestoHijoDerecho = eliminar(nodoActual.getHijoDerecho(), clave);
			nodoActual.setHijoDerecho(supuestoHijoDerecho);
			return nodoActual;
		}
		
		if (clave.compareTo(claveActual) < 0) {
			NodoBinario<K,V> supuestoHijoIzquierdo = eliminar(nodoActual.getHijoIzquierdo(), clave);
			nodoActual.setHijoIzquierdo(supuestoHijoIzquierdo);
			return nodoActual;
		}
		
		//si llegamos aqui es por que ya pille el nodo a eliminar
		//caso 1
		if (nodoActual.esHoja()) {
			return nodoVacioParaElArbol();
		}
		
		//caso 2
		if (nodoActual.esVacioHijoIzquiero() && !nodoActual.esVacioHijoDerecho() ) {
			return nodoActual.getHijoDerecho();
		}
		
		if (nodoActual.esVacioHijoDerecho() && !nodoActual.esVacioHijoIzquiero()) {
			return nodoActual.getHijoIzquierdo();
		}
		
		//caso 3
		NodoBinario<K,V> nodoRemplazo = buscarNodoSucesor(nodoActual.getHijoDerecho());
		System.out.println(nodoRemplazo);
		NodoBinario<K,V> posibleNuevoHijo = eliminar(nodoActual.getHijoDerecho(),nodoRemplazo.getClave());
		nodoActual.setHijoDerecho(posibleNuevoHijo);
		
		nodoRemplazo.setHijoDerecho(nodoActual.getHijoDerecho());
		nodoRemplazo.setHijoIzquierdo(nodoActual.getHijoIzquierdo());
		
		nodoActual.setHijoDerecho((NodoBinario<K,V>)NodoBinario.nodoVacio());
		nodoActual.setHijoIzquierdo((NodoBinario<K,V>)NodoBinario.nodoVacio());
		return nodoRemplazo;
		
	}
	protected NodoBinario<K, V> buscarNodoSucesor(NodoBinario<K, V> nodoActual) {
		NodoBinario<K,V> nodoARetornar = nodoActual;
		if (!nodoActual.esVacioHijoIzquiero()) {
			nodoARetornar = buscarNodoSucesor(nodoActual.getHijoIzquierdo());
		}
		return nodoARetornar;
		
	}
	@Override
	public V buscar(K clave) {
		V valorARetornar = buscar(this.raiz,clave);
		
		return valorARetornar;
	}

	private V buscar(NodoBinario<K, V> nodoActual, K clave) {
		// preguntamos si el nodo no es vacio
		//si esta vacio el nodo salimosy retornamos nuull
		if (NodoBinario.esNodoVacio(nodoActual)) {
			return null;
		}
		//si es mayor nos vamos por la derecha
		if (clave.compareTo(nodoActual.getClave()) > 0) {
			V valor = buscar(nodoActual.getHijoDerecho(),clave);
			return valor;
		}
		//si es menor nos vamos por la izquierda
		if (clave.compareTo(nodoActual.getClave()) < 0) {
			V valor = buscar(nodoActual.getHijoIzquierdo(),clave);
			return valor;
		}
		//si es que llegamos aqui es que ya pillamos el valor
		//tenemos algo que retornar
		return nodoActual.getValor();
		
	}
	@Override
	public boolean contiene(K clave) {
		// TODO Auto-generated method stub
		return this.buscar(clave) != null;
	}

	@Override
	public List<K> recorridoEnInOrden(){
		
		List<K> recorrido = new LinkedList<>();
		recorridoInOrdenAmigo(this.raiz,recorrido);
		return recorrido;	
	}

	private void recorridoInOrdenAmigo(NodoBinario<K, V> nodoActual, List<K> recorrido) {
		if (NodoBinario.esNodoVacio(nodoActual)) {
			return;
		}
		recorridoInOrdenAmigo(nodoActual.getHijoIzquierdo(),recorrido);
		recorrido.add(nodoActual.getClave());
		recorridoInOrdenAmigo(nodoActual.getHijoDerecho(), recorrido);
	}

	@Override
	public List<K> recorridoEnPreOrden() {
	
		List<K> recorrido = new LinkedList<>();
		recorridoEnPreOrden(this.raiz,recorrido);
		return recorrido;
	}

	private void recorridoEnPreOrden(NodoBinario<K, V> nodoActual, List<K> recorrido) {
		if (NodoBinario.esNodoVacio(nodoActual)) {
			return;
		}
		recorrido.add(nodoActual.getClave());
		recorridoEnPreOrden(nodoActual.getHijoIzquierdo(),recorrido);
		recorridoEnPreOrden(nodoActual.getHijoDerecho(),recorrido);
		
	}
	@Override
	public List<K> recorridoEnPostOrden() {
		List<K> recorrido = new LinkedList<>();
		recorridoEnPostOrden(this.raiz,recorrido);
		return recorrido;
	}

	private void recorridoEnPostOrden(NodoBinario<K, V> nodoActual, List<K> recorrido) {
		if (NodoBinario.esNodoVacio(nodoActual)) {
			return;
		}
		recorridoEnPostOrden(nodoActual.getHijoIzquierdo(), recorrido);
		recorridoEnPostOrden(nodoActual.getHijoDerecho(), recorrido);
		recorrido.add(nodoActual.getClave());
	}
	
	public List<K> recorridoEnPostOrdenIt() {
		List<K> recorrido = new LinkedList<>();
		if (this.esArbolVacio()) {
			return recorrido;
		}
		Stack<NodoBinario<K,V>> pilaDeNodos = new Stack<>();
		NodoBinario<K,V> nodoActual = this.raiz;
		NodoBinario<K,V> nodoAnterior = this.nodoVacioParaElArbol();
		pilaDeNodos.push(this.raiz);
		
		while (!nodoActual.esVacioHijoIzquiero()) {
			pilaDeNodos.push(nodoActual.getHijoIzquierdo());
			nodoActual = nodoActual.getHijoIzquierdo();
		}
		
		while (!nodoActual.esVacioHijoDerecho()) {
			pilaDeNodos.push(nodoActual.getHijoDerecho());
			nodoActual = nodoActual.getHijoDerecho();
			while(!nodoActual.esVacioHijoIzquiero()) {
				pilaDeNodos.push(nodoActual.getHijoIzquierdo());
				nodoActual = nodoActual.getHijoIzquierdo();		
			}
		}
		
		while (!pilaDeNodos.isEmpty()) {
			nodoActual =  pilaDeNodos.peek();
			if (!nodoActual.esVacioHijoDerecho()) {
				if (nodoAnterior.getClave().compareTo(nodoActual.getHijoDerecho().getClave())!=0) {
					while (!nodoActual.esVacioHijoDerecho()) {
						pilaDeNodos.push(nodoActual.getHijoDerecho());
						nodoActual = nodoActual.getHijoDerecho();
						while(!nodoActual.esVacioHijoIzquiero()) {
							pilaDeNodos.push(nodoActual.getHijoIzquierdo());
							nodoActual = nodoActual.getHijoIzquierdo();		
						}
					}
				}
			}
			
			nodoAnterior = nodoActual;
			pilaDeNodos.pop();
			recorrido.add(nodoActual.getClave());
		}
		return recorrido;
	}
	
	@Override
	public List<K> recorridoPorNiveles() {
		List<K> recorrido = new LinkedList<>();
		if (this.esArbolVacio()) {
			return recorrido;
		}
		NodoBinario<K,V> nodoActual = this.raiz;
		Queue<NodoBinario<K,V>> colaDeNodos = new LinkedList<>();
		colaDeNodos.add(nodoActual);
		
		while(!colaDeNodos.isEmpty()) {
			nodoActual = colaDeNodos.poll();
			recorrido.add(nodoActual.getClave());
			if (!nodoActual.esVacioHijoIzquiero()) {
				colaDeNodos.add(nodoActual.getHijoIzquierdo());
			}
			if (!nodoActual.esVacioHijoDerecho()) {
				colaDeNodos.add(nodoActual.getHijoDerecho());
			}
		}

		
		return recorrido;
	}
	

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return cantidadDeNodos(this.raiz);
	}

	private int cantidadDeNodos(NodoBinario<K, V> nodoActual) {
		if (NodoBinario.esNodoVacio(nodoActual)) {
			return 0;
		}
		int cantidadDeNodosIzq = cantidadDeNodos(nodoActual.getHijoIzquierdo());
		int cantidadDeNodosDer = cantidadDeNodos(nodoActual.getHijoDerecho());
		int retorno= cantidadDeNodosIzq + cantidadDeNodosDer + 1;
		
		return retorno;
	}
	@Override
	public int altura() {
		// TODO Auto-generated method stub
		return altura(this.raiz);
	}

	protected int altura(NodoBinario<K, V> nodoActual) {
		if (NodoBinario.esNodoVacio(nodoActual)) {
			return 0;
		}
		
		int alturaIzq = altura(nodoActual.getHijoIzquierdo());
		int alturaDer = altura(nodoActual.getHijoDerecho());
		
		if (alturaIzq > alturaDer) {
			return alturaIzq + 1;
		}
		
		return alturaDer + 1;
	}
	@Override
	public void vaciar() {
		this.raiz = this.nodoVacioParaElArbol();
		
	}

	@Override
	public boolean esArbolVacio() {
		// TODO Auto-generated method stub
		return NodoBinario.esNodoVacio(this.raiz);
	}

	@Override
	public int nivel() {
		// TODO Auto-generated method stub
		return this.altura() - 1;
	}
	
	//1. Implemente un método recursivo que retorne la cantidad nodos hojas que existen en un
	//árbol binario
	
	public int cantidadDeNodosHojasRecursivo() {
		return cantidadDeNodosHojas(this.raiz);
	}
	private int cantidadDeNodosHojas(NodoBinario<K, V> nodoActual) {
		if (NodoBinario.esNodoVacio(nodoActual)) {
			return 0;
		}
		
		int cantidadDeHojasIzq = cantidadDeNodosHojas(nodoActual.getHijoIzquierdo());
		int cantidadDeHojasDer = cantidadDeNodosHojas(nodoActual.getHijoDerecho());
		
		if (nodoActual.esHoja()) {
			return cantidadDeHojasIzq + cantidadDeHojasDer + 1;
		}
		
		return cantidadDeHojasIzq + cantidadDeHojasDer;
	}
	
	
	//2. Implemente un método iterativo que retorne la cantidad nodos hojas que existen en un
	//árbol binario
	public int cantidadDeHojasIterativo() {
		if (this.esArbolVacio()) {
			return 0;
		}
		
		int cantidad = 0;
		NodoBinario<K,V> nodoActual = this.raiz;
		Queue<NodoBinario<K,V>> colaDeNodos = new LinkedList<>();
		colaDeNodos.add(nodoActual);
		
		while(!colaDeNodos.isEmpty()) {
			nodoActual = colaDeNodos.poll();
			if (nodoActual.esHoja()) {
				cantidad = cantidad + 1;
			}
			if (!nodoActual.esVacioHijoIzquiero()) {
				colaDeNodos.add(nodoActual.getHijoIzquierdo());
			}
			if (!nodoActual.esVacioHijoDerecho()) {
				colaDeNodos.add(nodoActual.getHijoDerecho());
			}
		}
		
		return cantidad;
	}
	
	//3. Implemente un método recursivo que retorne la cantidad nodos hojas que existen en un
	//árbol binario, pero solo en el nivel N
	public int cantidadDeNodosHojasEnNivelRecursivo(int nivel) {
		return cantidadDeNodosHojas(this.raiz,nivel,0);
	}
	private int cantidadDeNodosHojas(NodoBinario<K, V> nodoActual, int nivel, int nivelActual) {
		if (NodoBinario.esNodoVacio(nodoActual)) {
			return 0;
		}
		int cantidadIzq = cantidadDeNodosHojas(nodoActual.getHijoIzquierdo(),nivel, nivelActual + 1);
		int cantidadDer = cantidadDeNodosHojas(nodoActual.getHijoDerecho(),nivel, nivelActual + 1);
		
		if (nivel == nivelActual) {
			if (nodoActual.esHoja()) {
				return cantidadIzq + cantidadDer + 1;
			}
		}
		
		return cantidadIzq + cantidadDer ;
	}
	//4. Implemente un método iterativo que retorne la cantidad nodos hojas que existen en un
	//árbol binario, pero solo en el nivel N
	
	public int cantidadDeHojasDelNivelIterativo(int nivel) {
		if (this.esArbolVacio()) {
			return 0;
		}
		NodoBinario<K,V> nodoActual = this.raiz;
		NodoBinario<K,V> nodoAnterior = this.nodoVacioParaElArbol();
		Stack<NodoBinario<K,V>> pilaDeNodos = new Stack<>();
		int nivelActual = 0;
		int retorno = 0;
		pilaDeNodos.push(nodoActual);
		while (!nodoActual.esVacioHijoIzquiero()) {
			pilaDeNodos.push(nodoActual.getHijoIzquierdo());
			nodoActual = nodoActual.getHijoIzquierdo();
			nivelActual=nivelActual+1;
		}
		while(!nodoActual.esVacioHijoDerecho()) {
			pilaDeNodos.push(nodoActual.getHijoDerecho());
			nivelActual=nivelActual+1;
			nodoActual =nodoActual.getHijoDerecho();
			while(!nodoActual.esVacioHijoIzquiero()) {
				pilaDeNodos.push(nodoActual.getHijoIzquierdo());
				nivelActual=nivelActual+1;
				nodoActual = nodoActual.getHijoIzquierdo();
			}
		}
		
		while (!pilaDeNodos.isEmpty()) {
			nodoActual = pilaDeNodos.peek();
			if (!nodoActual.esVacioHijoDerecho()) {
				if (nodoAnterior.getClave().compareTo(nodoActual.getHijoDerecho().getClave()) != 0) {
					while(!nodoActual.esVacioHijoDerecho()) {
						pilaDeNodos.push(nodoActual.getHijoDerecho());
						nivelActual=nivelActual+1;
						nodoActual = nodoActual.getHijoDerecho();
						while(!nodoActual.esVacioHijoIzquiero()) {
							pilaDeNodos.push(nodoActual.getHijoIzquierdo());
							nivelActual= nivelActual +1;
							nodoActual = nodoActual.getHijoIzquierdo();
						}
					}
				}
			}
			if (nivelActual == nivel   && nodoActual.esHoja()) {
				
				retorno= retorno + 1;
			}
			nodoAnterior = nodoActual;
			pilaDeNodos.pop();
			nivelActual= nivelActual-1;
			
		}
		
		return retorno;
	}
	
	
	//5. Implemente un método iterativo que retorne la cantidad nodos hojas que existen en un
	//árbol binario, pero solo antes del nivel N
	public int cantidadDeHojasAntesDelNivelIterativo(int nivel) {
		if (this.esArbolVacio()) {
			return 0;
		}
		NodoBinario<K,V> nodoActual = this.raiz;
		NodoBinario<K,V> nodoAnterior = this.nodoVacioParaElArbol();
		Stack<NodoBinario<K,V>> pilaDeNodos = new Stack<>();
		int nivelActual = 0;
		int retorno = 0;
		pilaDeNodos.push(nodoActual);
		while (!nodoActual.esVacioHijoIzquiero()) {
			pilaDeNodos.push(nodoActual.getHijoIzquierdo());
			nodoActual = nodoActual.getHijoIzquierdo();
			nivelActual=nivelActual+1;
		}
		while(!nodoActual.esVacioHijoDerecho()) {
			pilaDeNodos.push(nodoActual.getHijoDerecho());
			nivelActual=nivelActual+1;
			nodoActual =nodoActual.getHijoDerecho();
			while(!nodoActual.esVacioHijoIzquiero()) {
				pilaDeNodos.push(nodoActual.getHijoIzquierdo());
				nivelActual=nivelActual+1;
				nodoActual = nodoActual.getHijoIzquierdo();
			}
		}
		
		while (!pilaDeNodos.isEmpty()) {
			nodoActual = pilaDeNodos.peek();
			if (!nodoActual.esVacioHijoDerecho()) {
				if (nodoAnterior.getClave().compareTo(nodoActual.getHijoDerecho().getClave()) != 0) {
					while(!nodoActual.esVacioHijoDerecho()) {
						pilaDeNodos.push(nodoActual.getHijoDerecho());
						nivelActual=nivelActual+1;
						nodoActual = nodoActual.getHijoDerecho();
						while(!nodoActual.esVacioHijoIzquiero()) {
							pilaDeNodos.push(nodoActual.getHijoIzquierdo());
							nivelActual= nivelActual +1;
							nodoActual = nodoActual.getHijoIzquierdo();
						}
					}
				}
			}
			if (nivelActual < nivel   && nodoActual.esHoja()) {
				
				retorno= retorno + 1;
			}
			nodoAnterior = nodoActual;
			pilaDeNodos.pop();
			nivelActual= nivelActual-1;
			
		}
		
		return retorno;
	}
	
	
	//6. Implemente un método recursivo que retorne verdadero, si un árbol binario esta
	//balanceado según las reglas que establece un árbol AVL, falso en caso contrario.
	
	public boolean arbolBalanceadoIterativo() {
	
		if (this.esArbolVacio()) {
			return true;
		}
		NodoBinario<K,V> nodoActual = this.raiz;
		NodoBinario<K,V> nodoAnterior = this.nodoVacioParaElArbol();
		boolean sw = true;
		Stack<NodoBinario<K,V>> pilaDeNodos = new Stack<>();
		pilaDeNodos.push(this.raiz);
		while (!nodoActual.esVacioHijoIzquiero()) {
			pilaDeNodos.push(nodoActual.getHijoIzquierdo());
			nodoActual = nodoActual.getHijoIzquierdo();
		}
		
		while (!nodoActual.esVacioHijoDerecho()) {
			pilaDeNodos.push(nodoActual.getHijoDerecho());
			nodoActual = nodoActual.getHijoDerecho();
			while(!nodoActual.esVacioHijoIzquiero()) {
				pilaDeNodos.push(nodoActual.getHijoIzquierdo());
				nodoActual =  nodoActual.getHijoIzquierdo();
			}
		}
		
		while (!pilaDeNodos.isEmpty()) {
			nodoActual = pilaDeNodos.peek();
			if (!nodoActual.esVacioHijoDerecho()) {	
				if (((nodoAnterior.getClave()).compareTo(nodoActual.getHijoDerecho().getClave())) != 0) {
					while (!nodoActual.esVacioHijoDerecho()) {
						pilaDeNodos.push(nodoActual.getHijoDerecho());
						nodoActual = nodoActual.getHijoDerecho();
						while (!nodoActual.esVacioHijoIzquiero()) {
							pilaDeNodos.push(nodoActual.getHijoIzquierdo());
							nodoActual = nodoActual.getHijoIzquierdo();
						}
					}
				}
			}
			int izq = this.altura(nodoActual.getHijoIzquierdo());
			int der = this.altura(nodoActual.getHijoDerecho());
			System.out.println(izq-der);
			if (((izq - der)>= -1) && ((izq - der) <= 1)) {
				sw = true;
			}else {
				sw = false;
				return sw;
			}
			
			pilaDeNodos.pop();
			nodoAnterior = nodoActual;	
		}
		return sw;
	}
	
	public boolean arbolBalanceadoRec() {	
		return balanceo(this.raiz);
		
	}
	private boolean balanceo(NodoBinario<K, V> nodoActual) {
		if (NodoBinario.esNodoVacio(nodoActual)) {
			return true;
		}
		boolean izq = balanceo(nodoActual.getHijoIzquierdo());
		boolean der = balanceo(nodoActual.getHijoDerecho());
		
		if (izq && der) {
			int altIzq = this.altura(nodoActual.getHijoIzquierdo());
			int altDer = this.altura(nodoActual.getHijoDerecho());
			if (((altIzq - altDer) >= -1) && ((altIzq - altDer) <= 1)) {
				return true;
			}
		}
		return false;
	}
	
	//8. Implemente un método que reciba en listas de parámetros las llaves y valores de los
	//recorridos en preorden e inorden respectivamente y que reconstruya el árbol binario
	//original. Su método no debe usar el método insertar.
	public void reconstruir(List<K> claveIn, List<V> valorIn, List<K> clavePre, List<V> valorPre) {
		if (this.esArbolVacio()) {
			this.raiz = new NodoBinario<K,V>(clavePre.get(0), valorPre.get(0));
		}
		List<K> claveInDer = new LinkedList<>();
		List<V> valorInDer = new LinkedList<>();
		List<K> clavePreDer = new LinkedList<>();
		List<V> valorPreDer = new LinkedList<>();
		List<K> claveInIzq = new LinkedList<>();
		List<V> valorInIzq = new LinkedList<>();
		List<K> clavePreIzq = new LinkedList<>();
		List<V> valorPreIzq = new LinkedList<>();
		for (int i = 0; i < claveIn.size(); i++) {
			if (claveIn.get(i).compareTo(clavePre.get(0)) < 0) {
				claveInIzq.add(claveIn.get(i));
				valorInIzq.add(valorIn.get(i));
				clavePreIzq.add(clavePre.get(i+1));
				valorPreIzq.add(valorPre.get(i+1));
			}
				
			if (claveIn.get(i).compareTo(clavePre.get(0)) > 0) {
				claveInDer.add(claveIn.get(i));
				valorInDer.add(valorIn.get(i));
				clavePreDer.add(clavePre.get(i));
				valorPreDer.add(valorPre.get(i));
			}
		}
		reconstruir(claveInIzq, valorInIzq, clavePreIzq, valorPreIzq, this.raiz);
		reconstruir(claveInDer, valorInDer, clavePreDer, valorPreDer, this.raiz);
		
	}
	
	private void reconstruir(List<K> claveIn, List<V> valorIn, List<K> clavePre, List<V> valorPre,
			NodoBinario<K, V> nodoActual) {
		
		if (clavePre.isEmpty()) {
			return;
		}
			
			if (nodoActual.getClave().compareTo(clavePre.get(0)) > 0) {
				NodoBinario<K,V> nuevoNodoIzq = new NodoBinario<>(clavePre.get(0), valorPre.get(0));
				nodoActual.setHijoIzquierdo(nuevoNodoIzq);
				nodoActual = nuevoNodoIzq;
			}else {
				NodoBinario<K,V> nuevoNodoDer = new NodoBinario<>(clavePre.get(0), valorPre.get(0));
				nodoActual.setHijoDerecho(nuevoNodoDer);
				nodoActual = nuevoNodoDer;
			}
			
			List<K> claveInDer = new LinkedList<>();
			List<V> valorInDer = new LinkedList<>();
			List<K> clavePreDer = new LinkedList<>();
			List<V> valorPreDer = new LinkedList<>();
			List<K> claveInIzq = new LinkedList<>();
			List<V> valorInIzq = new LinkedList<>();
			List<K> clavePreIzq = new LinkedList<>();
			List<V> valorPreIzq = new LinkedList<>();
			for (int i = 0; i < claveIn.size() ; i++) {
				if (claveIn.get(i).compareTo(clavePre.get(0)) < 0) {
					claveInIzq.add(claveIn.get(i));
					valorInIzq.add(valorIn.get(i));
					clavePreIzq.add(clavePre.get(i+1));
					valorPreIzq.add(valorPre.get(i+1));
				}
				
				if (claveIn.get(i).compareTo(clavePre.get(0)) > 0) {
					claveInDer.add(claveIn.get(i));
					valorInDer.add(valorIn.get(i));
					clavePreDer.add(clavePre.get(i));
					valorPreDer.add(valorPre.get(i));
				}
			}
			reconstruir(claveInIzq, valorInIzq, clavePreIzq, valorPreIzq, nodoActual);
			reconstruir(claveInDer, valorInDer, clavePreDer, valorPreDer, nodoActual);
			
		
		
	}
	
	//9. Implemente un método privado que reciba un nodo binario de un árbol binario y que
	//retorne cual sería su sucesor inorden de la clave de dicho nodo.
	private NodoBinario<K,V> sucesorInOrden(NodoBinario <K,V> buscar) throws ExcepcionClaveNoExiste {
		NodoBinario<K,V> nodoActual = this.raiz;
		while (!NodoBinario.esNodoVacio(nodoActual) && nodoActual!= buscar) {
			if (nodoActual.getClave().compareTo(buscar.getClave()) < 0) {
				nodoActual = nodoActual.getHijoIzquierdo();
			}else {
				nodoActual = nodoActual.getHijoDerecho();
			}
		}	
		if (NodoBinario.esNodoVacio(nodoActual)) {
			throw new ExcepcionClaveNoExiste();
		}
			nodoActual = nodoActual.getHijoDerecho();
			NodoBinario<K,V> nodoARetornar = nodoActual;
		
		while (!NodoBinario.esNodoVacio(nodoActual)) {
			nodoARetornar = nodoActual;
			nodoActual = nodoActual.getHijoIzquierdo();
		}
		
		return nodoARetornar;
	}
	
	//10. Implemente un método privado que reciba un nodo binario de un árbol binario y que
	//retorne cuál sería su predecesor inorden de la clave de dicho nodo.
	
	private NodoBinario<K,V> predecesorInOrden(NodoBinario <K,V> nodoABuscar) throws ExcepcionClaveNoExiste{
		NodoBinario<K,V> nodoActual = this.raiz;
		while (!NodoBinario.esNodoVacio(nodoActual) && nodoActual!= nodoABuscar) {
			if (nodoActual.getClave().compareTo(nodoABuscar.getClave()) < 0) {
				nodoActual = nodoActual.getHijoIzquierdo();
			}else {
				nodoActual = nodoActual.getHijoDerecho();
			}
		}
		if (NodoBinario.esNodoVacio(nodoActual)) {
			throw new ExcepcionClaveNoExiste();
		}
		nodoActual = nodoActual.getHijoIzquierdo();
		NodoBinario<K,V> nodoARetornar = nodoActual;
	
		while (!NodoBinario.esNodoVacio(nodoActual)) {
			nodoARetornar = nodoActual;
			nodoActual = nodoActual.getHijoDerecho();
		}
		
		return nodoARetornar;
	}
	
	
	//12. Para un árbol binario implemente un método que retorne la cantidad de nodos que tienen
	//ambos hijos luego del nivel N.
	
	public int cantidadDeNodosConDosHijos(int nivel) {
		return cantidadDeNodosConDosHijos(this.raiz,nivel,0);
	}
	private int cantidadDeNodosConDosHijos(NodoBinario<K, V> nodoActual, int nivel, int nivelActual) {
		if (NodoBinario.esNodoVacio(nodoActual)) {
		
			return 0;
		}
		int cantidadIzq = cantidadDeNodosConDosHijos(nodoActual.getHijoIzquierdo(),nivel, nivelActual + 1);
		int cantidadDer = cantidadDeNodosConDosHijos(nodoActual.getHijoDerecho(),nivel, nivelActual + 1);
		
		if (nivelActual > nivel) {
			if (!nodoActual.esVacioHijoDerecho() && !nodoActual.esVacioHijoIzquiero()) {
				return cantidadIzq + cantidadDer + 1;
			}
		}
		
		return cantidadIzq + cantidadDer ;
	}
	
	
}
