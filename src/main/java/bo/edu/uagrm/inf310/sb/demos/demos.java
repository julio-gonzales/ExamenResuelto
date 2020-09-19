package bo.edu.uagrm.inf310.sb.demos;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import bo.edu.uagrm.inf310.sb.arboles.AVL;
import bo.edu.uagrm.inf310.sb.arboles.ArbolBinarioBusqueda;
import bo.edu.uagrm.inf310.sb.arboles.ExcepcionClaveNoExiste;
import bo.edu.uagrm.inf310.sb.arboles.ExcepcionClaveYaExiste;
import bo.edu.uagrm.inf310.sb.arboles.IArbolBusqueda;



public class demos {
	public static void main(String[] argumentos) throws ExcepcionClaveYaExiste ,ExcepcionClaveNoExiste {
		IArbolBusqueda<Integer,String> arbolDePrueba = new ArbolBinarioBusqueda<>();
		
		int opcion = 0;
		int subOpcion = 0;
		int nivel = 0;
		Integer clave = 0;
		String valor = "";
		Scanner teclado = new Scanner(System.in);
		
		System.out.println("ARBOLES DE BUSQUEDA");
		System.out.println("1.  ARBOL BINARIO DE BUSQUEDA");
		System.out.println("2.  AVL");
		System.out.println("Seleccione con que arbol desea trabajar");
		opcion = teclado.nextInt();
		
		switch (opcion) {
		case 1:
			arbolDePrueba = new ArbolBinarioBusqueda<>();
			break;

		case 2:
			arbolDePrueba = new AVL<>();
			break;
		}
		
		
		do {
			System.out.println("MENU");
			System.out.println("1.  INSERTAR");
			System.out.println("2.  BUSCAR");
			System.out.println("3.  ELIMINAR");
			System.out.println("4.  RECORRIDO IN ORDEN");
			System.out.println("5.  RECORRIDO PRE ORDEN");
			System.out.println("6.  RECORRIDO POST ORDEN");
			System.out.println("7.  RECORRIDO POR NIVELES");
			System.out.println("8.  NIVEL");
			System.out.println("9.  ALTURA");
			System.out.println("10.  VACIAR");
			System.out.println("11.  CANTIDAD DE NODOS HOJAS EN EL ARBOL");
			System.out.println("12.  CANTIDAD DE NODOS HOJAS SOLO EN EL NIVEL N");
			System.out.println("13.  CANTIDAD DE NODOS HOJAS ANTES DEL NIVEL N");
			System.out.println("14. EL ARBOL ESTA BALANCEADO SEGUN LAS REGLAS AVL");
			System.out.println("15. RECOSTRUIR EL ARBOL");
			System.out.println("16. CANTIDAD DE NODOS QUE TIENE AMBOS HIJOS EN EL NIVEL N");
			System.out.println("17. SALIR");
			opcion = teclado.nextInt();
			
			switch (opcion) {
			case 1:
				System.out.println("DIGITE LA CLAVE: ");
				clave = teclado.nextInt();
				System.out.println("DIGITE EL VALOR: ");
				valor = teclado.next();
				arbolDePrueba.insertar(clave, valor);
				break;

			case 2:
				System.out.println("DIGITE LA CLAVE A BUSCAR");
				clave = teclado.nextInt();
				System.out.println("el valor de la clave es: " + arbolDePrueba.buscar(clave));
				break;

			case 3:
				System.out.println("DIGITE LA CLAVE A ELIMINAR: ");
				clave = teclado.nextInt();
				valor = arbolDePrueba.eliminar(clave);
				System.out.println("el valor de la clave eliminada es: " + valor);
				break;
			case 4:
				System.out.println("RECORRIDO INORDEN: " + arbolDePrueba.recorridoEnInOrden());
				break;
			case 5:
				System.out.println("RECORRIDO PRE-ORDEN: " + arbolDePrueba.recorridoEnPreOrden());
				break;
			case 6:
				System.out.println("RECORRIDO POST-ORDEN: " + arbolDePrueba.recorridoEnPostOrden());
				break;
			case 7:
				System.out.println("RECORRIDO POR NIVELES: " + arbolDePrueba.recorridoPorNiveles());
				break;
			case 8:
				System.out.println("EL NIVEL DEL ARBOL ES: " + arbolDePrueba.nivel());
				break;
			case 9:
				System.out.println("LA ALTURA DEL ARBOL ES: " + arbolDePrueba.altura());
				break;
			case 10:
				arbolDePrueba.vaciar();
				System.out.println("EL ARBOL SE VACIO");
				break;
			case 11:
				System.out.println("SELECCIONE CON QUE METODO RESOLVER");
				System.out.println("1.  ITERATIVO");
				System.out.println("2.  RECURSIVO");
				subOpcion = teclado.nextInt();
				switch (subOpcion) {
				case 1:
						System.out.println("la cantidad de nodos hojas en el arbol es: " + 
								((ArbolBinarioBusqueda)arbolDePrueba).cantidadDeHojasIterativo());
					break;
				case 2:
						System.out.println("la cantidad de nodos hojas en el arbol es: " + 
							((ArbolBinarioBusqueda)arbolDePrueba).cantidadDeNodosHojasRecursivo());
				}
				break;
			case 12:
				System.out.println("digite el nivel en que desea buscar");
				nivel = teclado.nextInt();
				System.out.println("SELECCIONE EL METODO");
				System.out.println("1.  ITERATIVO");
				System.out.println("2.  RECURSIVO");
				subOpcion = teclado.nextInt();
				switch (subOpcion) {
				case 1:
					System.out.println("la cantidad de nodos hojas en el niel: " + nivel + " es " +
							((ArbolBinarioBusqueda)arbolDePrueba).cantidadDeHojasDelNivelIterativo(nivel));
					break;
				case 2:
					System.out.println("la cantidad de nodos hojas en el nivel: " + nivel + " es " +
							((ArbolBinarioBusqueda)arbolDePrueba).cantidadDeNodosHojasEnNivelRecursivo(nivel));
				}
				break;
			case 13:
				System.out.println("digite el nivel ");
				nivel = teclado.nextInt();
				System.out.println("la cantidad de nodos hojas antes del nivel " + nivel + " es " + 
						((ArbolBinarioBusqueda)arbolDePrueba).cantidadDeHojasAntesDelNivelIterativo(nivel));
				break;
			case 14:
				System.out.println("selecione el metodo: ");
				System.out.println("1.  ITERATIVO");
				System.out.println("2.  RECURSIVO");
				subOpcion = teclado.nextInt();
				switch (subOpcion) {
				case 1:
					boolean sw = ((ArbolBinarioBusqueda)arbolDePrueba).arbolBalanceadoIterativo();
					if (sw) {
						System.out.println("el arbol esta balanceado");
					}else {
						System.out.println("el arbol no esta balanceado");
					}
					break;

				case 2:
					sw = ((ArbolBinarioBusqueda)arbolDePrueba).arbolBalanceadoRec();
					if (sw) {
						System.out.println("el arbol esta balanceado");
					}else {
						System.out.println("el arbol no esta balanceado");
					}
				}
				break;
			case 15:
				List<Integer> listaIn = arbolDePrueba.recorridoEnInOrden();
				List<Integer> listaPre = arbolDePrueba.recorridoEnPreOrden();
				List<String> valorIn = new LinkedList<>();
				List<String> valorPre = new LinkedList<>();
				for (int i = 0; i < listaIn.size(); i++) {
					valorIn.add(arbolDePrueba.buscar(listaIn.get(i)));
					valorPre.add(arbolDePrueba.buscar(listaPre.get(i)));
				}
				arbolDePrueba.vaciar();
				((ArbolBinarioBusqueda)arbolDePrueba).reconstruir(listaIn, valorIn, listaPre, valorPre);
				break;
			case 16:
				System.out.println("digite el nivel");
				nivel = teclado.nextInt();
				System.out.println("la cantidad de nodos con dos hijos a partir del nivel "+ nivel + " es " +
						((ArbolBinarioBusqueda)arbolDePrueba).cantidadDeNodosConDosHijos(nivel));
				break;

			}
			
		}
		
		while(opcion != 17);
		
	
	}
}
