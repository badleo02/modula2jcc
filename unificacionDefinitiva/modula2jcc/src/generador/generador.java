package generador;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Stack;

import Main.Interfaz;
import Semantico.Nodo;
import TablaDeSimbolos.Atributos;
import TablaDeSimbolos.TablaSimbolos;



public class generador {

	/**
	 * Referencia de la interfaz
	 */
	public Interfaz intefaz;

	/**
	 * Buffer de código ensamblador
	 */
	private String codigo;
	private PrintStream file;
	private FileOutputStream codObj;
	/**
	 * Contador de etiquetas a generar.
	 */
	private int contadorEtiquetas;
	/**
	 * Pila de contador de variables temporales por ámbito.
	 */
	private Stack<Integer> contadorVariables;

	private Stack<ArrayList<String>> pilaListaVariables;

	private TablaSimbolos tabla;

	private boolean etiquetaUltimaEmision;

	/** Separación de los comentarios desde la columna 0 */
	private int SEPCODIGO = 25;


	/**
	 * Constructor de la clase
	 */
	public Generador (TablaSimbolos arg0) {
		String fichero = new String("a.out");
		codigo = new String();
		contadorEtiquetas = 0;
		contadorVariables = new Stack<Integer>();
		contadorVariables.add(1);
		pilaListaVariables = new Stack<ArrayList<String>>();
		ArrayList<String> temp = new ArrayList<String>();
		pilaListaVariables.add(temp);
		tabla = arg0;
		etiquetaUltimaEmision = false;
		try{
			codObj = new FileOutputStream(fichero);
			file = new PrintStream(codObj);
		}
		catch (Exception e){
			System.err.println("Error en apertura de fichero");
		}
	}

	/**
	 * Emite el código al fichero buffer de código ensamblador.
	 * IMPORTANTE: No escribe en fichero. Sólo amplía el código.
	 * @param arg0 cadena que escribirá en el código
	 * @see escribeSeccion
	 */
	public void emite(String arg0){
		String temp = new String();
		if (arg0.charAt(0) < 'a'){
			temp += '\t';
			etiquetaUltimaEmision = false;
		} else {
			if (etiquetaUltimaEmision)
				temp += "\tNOP \n";
			etiquetaUltimaEmision = true;
		}
		temp += arg0;
		codigo += temp + "\n";
	}

	/**
	 * Emite el código al fichero buffer de código ensamblador.
	 * IMPORTANTE: No escribe en fichero. Sólo amplía el código.
	 * @param arg0 cadena que escribirá en el código
	 * @param com comentario asociado a la cadena
	 * @see escribeSeccion
	 */
	public void emite(String arg0, String com){
		String temp = new String();
		if (arg0.charAt(0) < 'a'){
			temp += '\t';
		}

		temp += arg0;
		String comentario = "";
		for (int i=arg0.length(); i<SEPCODIGO; i++) comentario += " ";
		comentario += "; " + com;
		codigo += temp + comentario + "\n";
	}

	/**
	 * Añade el código al comienzo del buffer actual.
	 * Es necesario para añadir el código del control de pila
	 * al principio de cada código de subprograma.
	 * @param arg0 código a añadadir
	 */
	public void anadeAlComienzo(String arg0){
		String temp = new String();
		if (arg0.charAt(0) < 'a'){
			temp += '\t';
		}
		temp += arg0;
		if (arg0.charAt(0) == ';'){
			temp += '\n';
		}
		codigo = temp + codigo;
	}


	/**
	 * Escribe el código almacenado en el buffer.
	 */
	public void escribeSeccion(){
		file.println(codigo);
		intefaz.actualizarCodigoEns(codigo);
		codigo = new String();
	}

	/**
	 * Retorna una nueva etiqueta generada.
	 * @return Etiqueta temporal generada
	 */
	public String dameNuevaEtiqueta(){
		contadorEtiquetas++;
		return ("temp" + (contadorEtiquetas - 1));
	}

	/**
	 * Retorna una nueva variable temporal
	 * @param size tamaño del dato temporal
	 * @return Nueva variable temporal
	 */
	public int dameNuevaTemp(String nombre, int size){
		if (nombre == null) nombre = "; variable temp";
		else nombre = "; variable " + nombre;
		Integer numero = contadorVariables.pop();
		//numero += size;
		contadorVariables.push(numero + size);
		for (int i = 0; i < size; i++)
			pilaListaVariables.peek().add(nombre);
		return numero - 1;
	}

	/**
	 * Abre un nuevo ámbito para declaración de variables.
	 */
	public void abreAmbito(){
		Integer temp = new Integer(3);
		contadorVariables.push(temp);
		ArrayList<String> variables = new ArrayList<String>();
		pilaListaVariables.add(variables);
	}

	/**
	 * Cierra el ámbito actual y vuelve al ámbito padre.
	 */
	public void cierraAmbito(){
		contadorVariables.pop();
		pilaListaVariables.pop();
	}

	/**
	 * Retorna el tamaño de la pila necesario para el ámbito actual
	 * @return tamaño de la pila
	 */
	public int getTamanoTotalVariables(boolean esFuncion){
		if (esFuncion) return contadorVariables.peek() - 3;
		return contadorVariables.peek() - 1;
	}

	/*
	 * Métodos de generación
	 */

	// Genera el código de las sumas y restas
	public void generaCodigoSumas(ArrayList<Nodo> sumandos,
			ArrayList<String> operaciones, Nodo resultado) {
		if (sumandos.size() == 1) { // Si hay 1 sumando
			Nodo sumando = sumandos.get(0);
			resultado.setLugar(getPosicionReal(sumando));
			resultado.setTipo(sumando.getTipo());
		} else { // Si hay varios
			Nodo semiresultado = new Nodo();
			for (int i = 0; i < sumandos.size() - 1; i++) {
				Nodo sumando2 = sumandos.get(i);
				Nodo sumando1 = sumandos.get(i + 1);
				semiresultado = new Nodo();
				semiresultado.setLugar(dameNuevaTemp(null,1));
				String operando1 = new String();
				String operando2 = new String();
				String operando1com = new String();
				String operando2com = new String();
				int posicion2 = getPosicionReal(sumando2);
				int posicion1 = getPosicionReal(sumando1);
				if (sumando2.getValor() == null)
					operando2 += "#-";
				else operando2 += "#";
				operando2 += posicion2;
				if (sumando2.getValor() == null) {
					operando2 += "[.IX]";
					if (sumando2.getToken() != null)
						operando2com = (String) ((Atributos) sumando2
							.getToken().getAtributo())
							.obtener("LEXEMA");
					else operando2 = "temp";
				}
				else operando2com = operando2;
				if (sumando1.getValor() == null)
					operando1 += "#-";
				else operando1 += "#";
				operando1 += posicion1;
				if (sumando1.getValor() == null) {
					operando1 += "[.IX]";
					if (sumando1.getToken() != null)
						operando1com = (String) ((Atributos) sumando1
							.getToken().getAtributo())
							.obtener("LEXEMA");
					else operando1com = "temp";
				}
				else operando1com = operando1;

				if (operaciones.get(i).equals("+")){
					emite("ADD " + operando1 + "," + operando2, operando1com + " + " + operando2com);
				} else if (operaciones.get(i).equals("-")){
					emite("SUB " + operando1 + "," + operando2, operando1com + " + " + operando2com);
				} else if (operaciones.get(i).equals("or")){
					emite("OR " + operando1 + "," + operando2, operando1com + " OR " + operando2com);
				}
				emite("MOVE .A, #-" + semiresultado.getLugar() + "[.IX]",
					  "Acum -> temp");
				sumandos.set(i + 1, semiresultado);
			}
			resultado.setLugar((Integer) semiresultado.getLugar());
			resultado.setToken(null);
			resultado.setValor(null);
			resultado.setOffset(0);
		}
	}

	// Genera el código de las multiplicacines y divisiones
	public void generaCodigoMultiplicaciones(ArrayList<Nodo> factores,
			ArrayList<String> operaciones, Nodo resultado) {
		if (factores.size() == 1) { // Si hay 1 sumando
			Nodo factor = factores.get(0);
			resultado.setLugar(getPosicionReal(factor));
			resultado.setTipo(factor.getTipo());
		} else { // Si hay varios
			Nodo semiresultado = new Nodo();
			for (int i = 0; i < factores.size() - 1; i++) {
				Nodo factor2 = factores.get(i);
				Nodo factor1 = factores.get(i + 1);
				semiresultado = new Nodo();
				semiresultado.setLugar(dameNuevaTemp(null,1));
				String operando1 = new String();
				String operando2 = new String();
				String operando1com = new String();
				String operando2com = new String();
				int posicion2 = getPosicionReal(factor2);
				int posicion1 = getPosicionReal(factor1);
				if (factor2.getValor() == null)
					operando2 += "#-";
				else operando2 += "#";
				operando2 += posicion2;
				if (factor2.getValor() == null){
					operando2 += "[.IX]";
					if (factor2.getToken() != null)
						operando2com = (String) ((Atributos) factor2
							.getToken().getAtributo())
							.obtener("LEXEMA");
					else operando2com = "temp";
				}else operando2com = operando2;
				if (factor1.getValor() == null)
					operando1 += "#-";
				else operando1 += "#";
				operando1 += posicion1;
				if (factor1.getValor() == null){
					operando1 += "[.IX]";
					if (factor1.getToken() != null)
						operando1com = (String) ((Atributos) factor1
							.getToken().getAtributo())
							.obtener("LEXEMA");
					else operando1com = "temp";
				}else operando1com = operando1;

				if (operaciones.get(i).equals("*")){
					emite("MUL " + operando1 + "," + operando2, operando1com + " * " + operando2com);
				} else if (operaciones.get(i).equals("/")){
					emite("DIV " + operando1 + "," + operando2, operando1com + " / " + operando2com);
				} else if (operaciones.get(i).equals("and")){
					emite("AND " + operando1 + "," + operando2, operando1com + " AND " + operando2com);
				}
				emite("MOVE .A, #-" + semiresultado.getLugar() + "[.IX]" ,
					  "Acum -> temp");
				factores.set(i + 1, semiresultado);
			}
			resultado.setLugar((Integer) semiresultado.getLugar());
			resultado.setToken(null);
			resultado.setValor(null);
			resultado.setOffset(0);
		}
	}

	// Genera el código de la asignación
	public void generaCodigoAsignacion(Nodo destino, Nodo origen) {
		int iTamanoVariables = 1;
		iTamanoVariables = destino.getTipo().getTamanoTipo();
		String operandoDestino = new String();
		String operandoOrigen = new String();
		String operando1com = new String();
		String operando2com = new String();
		int posicionDestino = getPosicionReal(destino);
		int posicionOrigen = getPosicionReal(origen);
		if (destino.getValor() == null)
			//va a haber que restar al indice de la pila posiciones, por eso el menos
			operandoDestino += "#-";
		//en este caso es un imediato, no hace falta el menos
		else operandoDestino += "#";
		operandoDestino += posicionDestino;
		if (destino.getValor() == null){
			//se pone el indice de la pila (que lleva el menos delante)
			operandoDestino += "[.IX]";
			if (destino.getToken() != null)
				operando1com = (String) ((Atributos) destino
					.getToken().getAtributo())
					.obtener("LEXEMA");
			else operando1com = "temp";
		}else operando1com = operandoDestino;
		//lo mismo con el operando origen
		if (origen.getValor() == null)
			operandoOrigen += "#-";
		else operandoOrigen += "#";
		operandoOrigen += posicionOrigen;
		if (origen.getValor() == null){
			operandoOrigen += "[.IX]";
			if (origen.getToken() != null)
				operando2com = (String) ((Atributos) origen
					.getToken().getAtributo())
					.obtener("LEXEMA");
			else operando2com = "temp";
		}else operando2com = operandoOrigen;

		// Copia todas las posiciones de memoria que sean necesarias
		//para el caso de arrays, por ejemplo.

		emite("MOVE " + operandoOrigen + "," + operandoDestino,
			  operando1com + " := " + operando2com);

		for (int i = 1; i < iTamanoVariables; i++){
			posicionDestino++; posicionOrigen++;
			operandoDestino = new String();
			operandoOrigen = new String();
			operando1com = new String();
			operando2com = new String();
			if (destino.getValor() == null)
				operandoDestino += "#-";
			else operandoDestino += "#";
			operandoDestino +=  posicionDestino;
			if (destino.getValor() == null){
				operandoDestino += "[.IX]";
				if (destino.getToken() != null)
					operando1com = (String) ((Atributos) destino
						.getToken().getAtributo())
						.obtener("LEXEMA");
				else operando1com = "temp";
			}else operando1com = operandoDestino;
			if (origen.getValor() == null)
				operandoOrigen += "#-";
			else operandoOrigen += "#";
			operandoOrigen += posicionOrigen;
			if (origen.getValor() == null){
				operandoOrigen += "[.IX]";
				if (origen.getToken() != null)
					operando2com = (String) ((Atributos) origen
						.getToken().getAtributo())
						.obtener("LEXEMA");
				else operando2com = "temp";
			}else operando2com = operandoOrigen;
			emite("MOVE " + operandoOrigen + "," + operandoDestino,
					  operando1com + " := " + operando2com);
		}

	}

	// Genera el código de comparaciones
	public void generaCodigoComparacion(ArrayList<Nodo> comparables,
			ArrayList<Nodo> operaciones, Nodo resultado){
		if (resultado.getSiguiente() == null) // Si no tiene etiqueta se la damos
			resultado.setSiguiente(dameNuevaEtiqueta());
		Nodo comp2 = comparables.get(0);
		Nodo comp1 = comparables.get(1);
		String operando1 = new String();
		String operando2 = new String();
		String operando1com = new String();
		String operando2com = new String();
		int posicion2 = getPosicionReal(comp2);
		int posicion1 = getPosicionReal(comp1);
		if (comp2.getValor() == null)
			operando2 += "#-";
		else operando2 += "#";
		operando2 += posicion2;
		if (comp2.getValor() == null){
			operando2 += "[.IX]";
			if (comp2.getToken() != null)
				operando2com = (String) ((Atributos) comp2
					.getToken().getAtributo())
					.obtener("LEXEMA");
			else operando2com = "temp";
		}else operando2com = operando2;
		if (comp1.getValor() == null)
			operando1 += "#-";
		else operando1 += "#";
		operando1 += posicion1;
		if (comp1.getValor() == null){
			operando1 += "[.IX]";
			if (comp1.getToken() != null)
				operando1com = (String) ((Atributos) comp1
					.getToken().getAtributo())
					.obtener("LEXEMA");
			else operando1com = "temp";
		}else operando1com = operando1;

		emite("CMP " + operando1 + "," + operando2, operando1com + " <=> " + operando2com);
		if (operaciones.get(0).getValor().equals("<")) {
			emite("BZ /" + resultado.getSiguiente());
			emite("BP /" + resultado.getSiguiente());
		} else if (operaciones.get(0).getValor().equals("<=")) {
			emite("BP /" + resultado.getSiguiente());
		} else if (operaciones.get(0).getValor().equals(">")) {
			emite("BZ /" + resultado.getSiguiente());
			emite("BN /" + resultado.getSiguiente());
		} else if (operaciones.get(0).getValor().equals(">=")) {
			emite("BN /" + resultado.getSiguiente());
		} else if (operaciones.get(0).getValor().equals("<>")) {
			emite("BZ /" + resultado.getSiguiente());
		} else if (operaciones.get(0).getValor().equals("=")) {
			emite("BNZ /" + resultado.getSiguiente());
		}
		resultado.setLugar(dameNuevaTemp(null, 1));
		emite("MOVE #1,#-" + resultado.getLugar() + "[.IX]", "temp := 1");
	}

	// Genera el código de evaluación de booleano
	public void generaCodigoBooleano(Nodo resultado){
		if (resultado.getSiguiente() == null)
			resultado.setSiguiente(dameNuevaEtiqueta());
		String operando2 = new String();
		String operando2com = new String();
		int posicion2 = getPosicionReal(resultado);
		if (resultado.getValor() == null)
			operando2 += "#-";
		else operando2 += "#";
		operando2 += posicion2;
		if (resultado.getValor() == null){
			operando2 += "[.IX]";
			if (resultado.getToken() != null)
				operando2com = (String) ((Atributos) resultado
					.getToken().getAtributo())
					.obtener("LEXEMA");
			else operando2com = "temp";
		}else operando2com = operando2;

		emite("CMP #0," + operando2, operando2com + "== true?");
		emite("BZ /" + resultado.getSiguiente());
	}

	// Genera el código de una llamada a subrutina
	public void GeneraCodigoLlamadaAFuncion(String nombre, Nodo resultado){
		emite("CALL /"+ nombre);
		resultado.setLugar(dameNuevaTemp(null, 1));
		emite("MOVE .IY,#-" + resultado.getLugar() + "[.IX]", "Receive Data");
		resultado.setToken(null);
		resultado.setValor(null);
	}

	// Genera el código de gestión de la pila de cada subprograma
	public void generaCodigoSubprograma(String nombre, boolean esFuncion) {
		String previo = "PUSH #0";
		int espacio = getTamanoTotalVariables(esFuncion);
		for (int i = 0; i < espacio; i++) {
			anadeAlComienzo(pilaListaVariables.peek().get(pilaListaVariables.peek().size() - 1 - i));
			anadeAlComienzo(previo);
		}
		if (esFuncion){ // Valor de retorno
			anadeAlComienzo("PUSH #0 ; Return value\n");
		}
		// Situa en nuevo RA
		anadeAlComienzo("MOVE .SP, .IX  ; Set new RA\n");
		// Apila el Registro de Activación en IX
		anadeAlComienzo("PUSH .IX  ; RA backup\n");
		anadeAlComienzo(nombre + ":");
		for (int i = 0; i < espacio; i++) {
			emite("POP .R9");
		}
		if (esFuncion){ // Guarda el resultado en IY
			emite("POP .IY  ; Save return value in IY");
		}
		emite("POP .IX  ; Restore RA");
		emite("RET");
		escribeSeccion();
	}

	// Pide la inserción de una variable y la inserta en su posición
	public void generaCodigoEntrada(Nodo identificador){
		String operando = new String();
		String operandorcom = (String) ((Atributos) identificador.getToken()
							.getAtributo()).obtener("LEXEMA");
		int posicion = getPosicionReal(identificador);
		operando += "#-" + posicion + "[.IX]";
		emite("ININT " + operando, operandorcom + " <- read");
	}

	// Escribe el valor de una variable por terminal
	public void generaCodigoSalida(Nodo identificador){
		String operando = new String();
		String operandorcom = (String) ((Atributos) identificador.getToken()
				.getAtributo()).obtener("LEXEMA");
		int posicion = getPosicionReal(identificador);
		operando += "#-" + posicion + "[.IX]";
		emite("WRINT " + operando, "write -> " + operandorcom);
	}

	/**
	 * Retorna la posición real en memoria del nodo deseado.
	 * @param arg0 valor sobre la pila en la que se encuentra el valor del nodo.
	 * @return
	 */
	private int getPosicionReal(Nodo arg0){
		int retorno = -1;
		if (arg0.getValor() == null) {
			if (arg0.getToken() != null) {
					Atributos at = tabla
						.buscar((String) ((Atributos) arg0
								.getToken().getAtributo())
								.obtener("LEXEMA"));
					retorno = ((Integer)at.obtener("POSICION") + arg0.getOffset());
			}else
				retorno = ((Integer)arg0.getLugar() + arg0.getOffset());
		} else {
			retorno = (Integer)arg0.getValor();
		}
		return retorno;
	}

}
