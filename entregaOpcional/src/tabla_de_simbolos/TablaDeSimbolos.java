package tabla_de_simbolos;

import tabla_de_simbolos.PalabrasReservadas;
import java.util.ArrayList;
import java.util.Hashtable;

//***************************************************************************//
/**
 * Esta clase implementa la tabla de s�mbolos necesitada por cualquier compilador.
 * Gestiona diferentes tipos de �mbitos y palabras reservadas.
 * 
 * @author Grupo1.
 */
public class TablaDeSimbolos{
	
	// ATRIBUTOS
	private Hashtable<String, ArrayList<Object>> _palabrasReservadas;
	private ArrayList<Hashtable<String, ArrayList<Object>>> _listaAmbitos;
	private Hashtable<Integer, Integer>	_tablaOrganizador;
	private Integer _ambitoActual;
	private boolean	_insercionesPermitidas;

//	***************************************************************************//
	/**
	 * Constructor de la clase TablaDeSimbolos.
	 * 
	 * Initializes all the resources needed later on and opens the main scope.
	 */
	public TablaDeSimbolos(){
		
		// Inicializa el flag de Inserciones Permitidas
		_insercionesPermitidas = true; //TODO ESTO ES FALSE, PERO CUANDO SE ANADA EL SINTACTICO
		
		// Inicializa las tablas
		_palabrasReservadas	= new Hashtable<String, ArrayList<Object>>();
		_listaAmbitos = new ArrayList<Hashtable<String,ArrayList<Object>>>();
		_tablaOrganizador = new Hashtable<Integer, Integer>();
		
		// Actualiza el puntero a dicho �mbito
		_ambitoActual = new Integer(0);
		
		// Abre el primer �mbito
		abreAmbito();
		
		// A�ade las palabras reservadas a las tablas
		generaListaReservadas();
	}
	
//	***************************************************************************//
	/**
	 * Inicializa la tabla de palabras reservadas de la clase PalabrasReservadas.
	 */
	private void generaListaReservadas(){
	
		PalabrasReservadas[] reserved = PalabrasReservadas.values();
		for (PalabrasReservadas word : reserved ) {
			
			ArrayList<Object> atributos = new ArrayList<Object>();
			//atributos.add(new Integer(word.ordinal()));
			atributos.add(new String(word.toString()));
			_palabrasReservadas.put(word.toString(), atributos);
		}
	}
	
//	***************************************************************************//
	/**
	 * Busca la palabra dada en el �mbito actual. Devuelve el rango de �mbitos
	 * donde se encuentra el identificador o -1 si la palabra no es encontrada.
	 * 
	 *  @param identificador Identificador a buscar.
	 *  @return	<li>El n�mero en la <i>tabla de �mbitos</i> del �mbito que 
	 *  contiene la variable.
	 *  		<li>-1 Si el identificador no est� definido.
	 *  
	 *  @see #esPalabraReservada(String)
	 *  @see #insertaIdentificador(String)
	 */
	private int estaInsertada(String identificador){
		
		Hashtable<String, ArrayList<Object>> ambito;
		
		int iAmbito = _ambitoActual;
		ambito = _listaAmbitos.get(_ambitoActual);
		
		// Busca por el �mbito actual y los padres hasta dar con la soluci�n, de existir
		while (ambito != null){
			
			// Retorna cierto y actualiza el valor
			if (ambito.containsKey(identificador)){ 
				iAmbito = _listaAmbitos.indexOf(ambito);
				return iAmbito;
			}
			else { // Vuelve al �mbito padre
				
				if (iAmbito == 0){ // No hay m�s �mbitos padre
					ambito = null;
					iAmbito = -1;
				}
				else { // Actualiza al �mbito padre
					iAmbito = _tablaOrganizador.get(iAmbito);
					ambito = _listaAmbitos.get(_tablaOrganizador.get(_ambitoActual));
				}
			}
		}
		return -1;
	}
	
//	***************************************************************************//
	/**
	 * Busca una palabra en la tabla de palabras reservadas. 
	 * 
	 * @param identificador Identificador a buscar.
	 *  
	 * @return Verdadero si el identificador dado es una palabra reservada y
	 * falso en caso contrario.
	 *  
	 * @see #estaInsertada(String) 
	 */
	public boolean esPalabraReservada(String identificador){
		
		return _palabrasReservadas.containsKey(identificador.toUpperCase());
	}
	
//	***************************************************************************//
	/**
	 * Devuelve la palabra reservada asociada a identificador.
	 * 
	 * @param identificador Identificador asociado.
	 * 
	 * @return La palabra reservada asociada a identificador.
	 */
	public ArrayList<Object> getPalabraReservada(String identificador){
		return _palabrasReservadas.get(identificador.toUpperCase());
	}
	
//	***************************************************************************//
	/**
	 * Trata de insertar un nuevo identificador en la tabla.
	 * 
	 *  @param identificador Identificador a insertar.
	 *  @return	<li>Un puntero a la nueva entrada.
	 *  		<li>Un puntero a la entrada previa, si el identificador 
	 *  		estaba ya previamente definido o era una palabra reservada.
	 *  		<li>Un puntero a <i>null</i> si las inserciones no est�n permitidas 
	 */
	public ArrayList<Object> insertaIdentificador(String identificador){
		
		Integer iAmbitoTemporal;
		iAmbitoTemporal = new Integer(0);
		
		// Si es una palabra reservada retorna el puntero a la tabla interna de la misma.
		if (esPalabraReservada(identificador)){
			return (_palabrasReservadas.get(identificador.toUpperCase()));
		}
		// Si ya existe se devuelve un puntero a la entrada previamente generada.
		if (estaInsertada(identificador)>0){
			return _listaAmbitos.get(iAmbitoTemporal).get(identificador); 
		}
		// Si no se permiten inserciones retorna null
		if (!_insercionesPermitidas) return null;
		
		/* 
		 * Si no es reservada ni existe previamente se inserta y se devuelve 
		 * un puntero a dicha entrada.
		 */
		ArrayList<Object> atributos = new ArrayList<Object>();
		atributos.add(new String(identificador));
		_listaAmbitos.get(_ambitoActual).put(identificador, atributos);
		
		// Retorna el puntero a la entrada insertada
		return _listaAmbitos.get(_ambitoActual).get(identificador);
	}
	
//	***************************************************************************//
	/**
	 * A�ade un nuevo �mbito a la tabla y actualiza todos los recursos relacionados. 
	 * 
	 * @see #CierraAmbito()
	 */
	public void abreAmbito(){
		
		Hashtable<String, ArrayList<Object>> tempTable;
		
		// A�ade la entrada al organizador
		_tablaOrganizador.put(new Integer(_listaAmbitos.size()), new Integer(_ambitoActual));
		
		// Inserta la tabla en la lista dinamica
		tempTable = new Hashtable<String, ArrayList<Object>>();
		_listaAmbitos.add(tempTable);
		
		// Actualiza el puntero de ambito actual
		_ambitoActual = _listaAmbitos.size() - 1;
	}
	
//	***************************************************************************//
	/**
	 * Devuelve la palabra reservada asociada a contenido.
	 * 
	 * @param contenido Palabra reservada asociada a contenido.
	 * 
	 * @return La palabra reservada asociada a contenido. 
	 */
	public Object obtenerPalabraReservada(Object contenido){
		
		return _palabrasReservadas.get(contenido);
	}
	
//	***************************************************************************//
	/**
	 * Cierra el �mbito actual y devuelve el �mbito actual al padre.
	 *
	 * @see #abreAmbito()
	 */
	public void CierraAmbito(){
		
		// Actualiza el puntero a la tabla actual
		_ambitoActual = _tablaOrganizador.get(_ambitoActual);
		
		/* TODO Habría que ver si la tabla de ámbito previa se puede eliminar
		 * o no para ahorrar costes en espacio.
		 */
	}
	
//	***************************************************************************//
	/**
	 * Devuelve el flag privado para permitir las inserciones.
	 * 
	 * @return Verdadero Si las inserciones est�n permitidas y falso en 
	 * caso contrario.
	 * 
	 * @see #getInsercionesPermitidas()
	 */
	public boolean getInsercionesPermitidas(){
		
		return _insercionesPermitidas;
	}
	
//	***************************************************************************//
	/**
	 * Establece el flag privado para permitir las inserciones.
	 * 
	 * @param nuevoValor Nuevo valor del flag.
	 * 
	 * @see #getInsercionesPermitidas()
	 */
	public void setInsercionesPermitidas(boolean nuevoValor){
		
		_insercionesPermitidas = nuevoValor;
	}	
}
