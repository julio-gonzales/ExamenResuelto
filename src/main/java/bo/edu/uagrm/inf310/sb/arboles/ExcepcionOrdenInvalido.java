package bo.edu.uagrm.inf310.sb.arboles;

public class ExcepcionOrdenInvalido extends Exception {
	public ExcepcionOrdenInvalido() {
		super("la clave ya Existe en el arbol");
	}
	
	public ExcepcionOrdenInvalido (String mensaje) {
		super (mensaje);
	}
}
