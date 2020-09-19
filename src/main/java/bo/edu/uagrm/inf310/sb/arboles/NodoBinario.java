package bo.edu.uagrm.inf310.sb.arboles;

public class NodoBinario <K,V> {
	private K clave;
	private V valor;
	private NodoBinario<K,V> hijoIzquierdo;
	private NodoBinario<K,V> hijoDerecho;
	
	public NodoBinario() {
		//constructor por defecto
	}
	
	public NodoBinario (K clave, V valor) {
		//constructo con parametros
		this.clave = clave;
		this.valor = valor;
	}
	
	//generando getter y setter 
	public K getClave() {
		return clave;
	}

	public void setClave(K clave) {
		this.clave = clave;
	}

	public V getValor() {
		return valor;
	}

	public void setValor(V valor) {
		this.valor = valor;
	}

	public NodoBinario<K, V> getHijoIzquierdo() {
		return hijoIzquierdo;
	}

	public void setHijoIzquierdo(NodoBinario<K, V> hijoIzquierdo) {
		this.hijoIzquierdo = hijoIzquierdo;
	}

	public NodoBinario<K, V> getHijoDerecho() {
		return hijoDerecho;
	}

	public void setHijoDerecho(NodoBinario<K, V> hijoDerecho) {
		this.hijoDerecho = hijoDerecho;
	}
	
	//verificando si es vacio el hijo derecho
	public boolean esVacioHijoDerecho() {
		return NodoBinario.esNodoVacio(getHijoDerecho());
	}
	
	//verificando si es vacio el hijo izquierdo
	public boolean esVacioHijoIzquiero() {
		return NodoBinario.esNodoVacio(getHijoIzquierdo());
	}
	
	public boolean esHoja() {
		return this.esVacioHijoDerecho()
				&& this.esVacioHijoIzquiero();
	}
	
	
	//verificando si el nodo es vacio
	public static boolean esNodoVacio(NodoBinario <?,?> nodo) {
		return nodo == null;
	}
	
	//devolviendo un nodo 
	public static NodoBinario<?,?> nodoVacio(){
		return null;
	}


}
