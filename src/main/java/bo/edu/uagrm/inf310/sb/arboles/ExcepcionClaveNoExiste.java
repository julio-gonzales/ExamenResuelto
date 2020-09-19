package bo.edu.uagrm.inf310.sb.arboles;

public class ExcepcionClaveNoExiste extends Exception {
	//construccion de la excepcion clave no existe 
	//cuando se busque en el arbol un valor que no esta
	public ExcepcionClaveNoExiste() {
		super ("la clave no existe en el arbol");
	}
	
	public ExcepcionClaveNoExiste(String mensaje) {
		super(mensaje);
	}
}
