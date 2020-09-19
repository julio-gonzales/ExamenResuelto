package bo.edu.uagrm.inf310.sb.arboles;

public class AVL<K extends Comparable<K>, V> extends ArbolBinarioBusqueda<K, V> {
	
	private static final byte RANGO = 1;
	
	@Override
	public void insertar(K clave, V valor) throws ExcepcionClaveYaExiste {
		raiz = insertar(raiz, clave, valor); 
	}
	
	private NodoBinario<K,V> insertar(NodoBinario<K,V> nodoActual, K clave, V valor) throws ExcepcionClaveYaExiste{
		
		if (NodoBinario.esNodoVacio(nodoActual)) {
			NodoBinario<K,V> nuevoNodo = new NodoBinario<K,V> (clave, valor);
			return nuevoNodo; 
		}
		
		K claveActual = nodoActual.getClave();
		if (clave.compareTo(claveActual) > 0) {
			//nos vamos por derecha
			NodoBinario<K,V> posibleHijoDerecho = insertar(nodoActual.getHijoDerecho(), clave, valor);
			nodoActual.setHijoDerecho(posibleHijoDerecho);
			return balancear(nodoActual);
			
		}
		if (clave.compareTo(claveActual) < 0) {
			//nos vamos por izquierda
			NodoBinario<K,V> posibleHijoIzquierdo = insertar(nodoActual.getHijoIzquierdo(), clave, valor);
			nodoActual.setHijoIzquierdo(posibleHijoIzquierdo);
			return balancear(nodoActual);
		}
		
		//si llegamos aqui es que ya pillamos la clave en el arbol
		//y devolvemos la excepcion de clave ya existe
		throw new ExcepcionClaveYaExiste();
		
	}
	
	protected NodoBinario<K,V> balancear(NodoBinario<K,V> nodoActual){
		int alturaDerecha = altura(nodoActual.getHijoDerecho());
		int alturaIzquierda = altura(nodoActual.getHijoIzquierdo());
		int diferenciaDeAltura = alturaIzquierda - alturaDerecha;
		
		if (diferenciaDeAltura < -RANGO) {
			//problema en la derecha
			NodoBinario<K,V> nodoDerecho = nodoActual.getHijoDerecho();
			alturaDerecha = altura(nodoDerecho.getHijoDerecho());
			alturaIzquierda = altura(nodoDerecho.getHijoIzquierdo());
			if (alturaDerecha >= alturaIzquierda) {
				return rotacionSimpleAIzquierda(nodoActual);
			}else {
				return rotacionDobleAIzquierda(nodoActual);
			}
		}else if(diferenciaDeAltura > RANGO) {
			//problema en izquierda
			NodoBinario<K,V> nodoIzquierdo = nodoActual.getHijoIzquierdo();
			alturaDerecha = altura(nodoIzquierdo.getHijoDerecho());
			alturaIzquierda = altura(nodoIzquierdo.getHijoIzquierdo());
			if (alturaDerecha > alturaIzquierda) {
				return rotacionDobleADerecha(nodoActual);
			}else {
				return rotacionSimpleADerecha(nodoActual);
			}
		}
		//si llego por aqui no hay probelmas
		//por que la diferencia en alturas esta dentro del rango 
		//no se hace nada
		return nodoActual;
	}

	private NodoBinario<K, V> rotacionSimpleADerecha(NodoBinario<K, V> nodoActual) {
		NodoBinario<K,V> nodoARetornar = nodoActual.getHijoIzquierdo();
		nodoActual.setHijoIzquierdo(nodoARetornar.getHijoDerecho());
		nodoARetornar.setHijoDerecho(nodoActual);
		return nodoARetornar;
	}

	private NodoBinario<K, V> rotacionDobleADerecha(NodoBinario<K, V> nodoActual) {
		NodoBinario<K,V> nodoARetornar = rotacionSimpleAIzquierda(nodoActual.getHijoIzquierdo());
		nodoActual.setHijoIzquierdo(nodoARetornar);
		nodoARetornar = rotacionSimpleADerecha(nodoActual);
		return nodoARetornar;
	}

	private NodoBinario<K, V> rotacionDobleAIzquierda(NodoBinario<K, V> nodoActual) {
		NodoBinario<K,V> nodoARetornar = rotacionSimpleADerecha(nodoActual.getHijoDerecho());
		nodoActual.setHijoDerecho(nodoARetornar);
		nodoARetornar = rotacionSimpleAIzquierda(nodoActual);
		return nodoARetornar;
	}

	private NodoBinario<K, V> rotacionSimpleAIzquierda(NodoBinario<K, V> nodoActual) {
		NodoBinario<K,V> nodoARetornar = nodoActual.getHijoDerecho();
		nodoActual.setHijoDerecho(nodoARetornar.getHijoIzquierdo());
		nodoARetornar.setHijoIzquierdo(nodoActual);
		return nodoARetornar;
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
			return balancear(nodoActual);
		}
		
		if (clave.compareTo(claveActual) < 0) {
			NodoBinario<K,V> supuestoHijoIzquierdo = eliminar(nodoActual.getHijoIzquierdo(), clave);
			nodoActual.setHijoIzquierdo(supuestoHijoIzquierdo);
			return balancear(nodoActual);
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
		return balancear(nodoRemplazo);
		
	}
}
