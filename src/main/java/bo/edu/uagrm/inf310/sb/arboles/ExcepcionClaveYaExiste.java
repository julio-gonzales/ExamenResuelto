package bo.edu.uagrm.inf310.sb.arboles;

public class ExcepcionClaveYaExiste extends Exception {
	//la excepcion devuelve un mensaje cuando el dato que se quiere insertar
	//ya existe 
	public ExcepcionClaveYaExiste() {
		super("la clave ya Existe en el arbol");
	}
	
	public ExcepcionClaveYaExiste (String mensaje) {
		super (mensaje);
	}
}
