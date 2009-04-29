package generador;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Stack;

import gui.Ventana;
import semantico.Nodo;
import tabla_de_simbolos.TablaDeSimbolos;
import tabla_de_simbolos.simbolo.*;



public class generador {

	/**
	 * Referencia de la interfaz
	 */
	public gui.Ventana intefaz;

	/**
	 * Buffer de c�digo ensamblador
	 */
	private String codigo;
	private PrintStream file;
	private FileOutputStream codObj;
	/**
	 * Contador de etiquetas a generar.
	 */
	private int contadorEtiquetas;
	/**
	 * Pila de contador de variables temporales por �mbito.
	 */
	private Stack<Integer> contadorVariables;

	private Stack<ArrayList<String>> pilaListaVariables;

	private TablaDeSimbolos tabla;

	private boolean etiquetaUltimaEmision;

	/** Separaci�n de los comentarios desde la columna 0 */
	private int SEPCODIGO = 25;


	/**
	 * Constructor de la clase
	 */
	public generador (TablaDeSimbolos arg0) {
		String fichero = new String("COMPILACION.out");
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
	 * Emite el c�digo al fichero buffer de c�digo ensamblador.
	 * IMPORTANTE: No escribe en fichero. S�lo ampl�a el c�digo.
	 * @param arg0 cadena que escribir� en el c�digo
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
	 * Emite el c�digo al fichero buffer de c�digo ensamblador.
	 * IMPORTANTE: No escribe en fichero. S�lo ampl�a el c�digo.
	 * @param arg0 cadena que escribir� en el c�digo
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
	 * A�ade el c�digo al comienzo del buffer actual.
	 * Es necesario para a�adir el c�digo del control de pila
	 * al principio de cada c�digo de subprograma.
	 * @param arg0 c�digo a a�adadir
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
	 * Escribe el c�digo almacenado en el buffer.
	 */
	public void escribeSeccion(){
		file.println(codigo);
		// IMPLEMENTAR ESTO intefaz.actualizarCodigoEns(codigo);
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
	 * @param size tama�o del dato temporal
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
	 * Abre un nuevo �mbito para declaraci�n de variables.
	 */
	public void abreAmbito(){
		Integer temp = new Integer(3);
		contadorVariables.push(temp);
		ArrayList<String> variables = new ArrayList<String>();
		pilaListaVariables.add(variables);
	}

	/**
	 * Cierra el �mbito actual y vuelve al �mbito padre.
	 */
	public void cierraAmbito(){
		contadorVariables.pop();
		pilaListaVariables.pop();
	}

	/**
	 * Retorna el tama�o de la pila necesario para el �mbito actual
	 * @return tama�o de la pila
	 */
	public int getTamanoTotalVariables(boolean esFuncion){
		if (esFuncion) return contadorVariables.peek() - 3;
		return contadorVariables.peek() - 1;
	}

	/*
	 * M�todos de generaci�n
	 */

	// Genera el c�digo de las sumas y restas
	public void generaCodigoSumas(ArrayList<Nodo> sumandos,
			ArrayList<String> operaciones, Nodo resultado) {
		if (sumandos.size() == 1) { // Si hay 1 sumando
			Nodo sumando = sumandos.get(0);
			resultado.setLugar(getPosicionReal(sumando));
			resultado.setTipo(sumando.getTipoSemantico());
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
					if (sumando2.getToken() == null)
                        operando2 = "temp";
				}
				else operando2com = operando2;
				if (sumando1.getValor() == null)
					operando1 += "#-";
				else operando1 += "#";
				operando1 += posicion1;
				if (sumando1.getValor() == null) {
					operando1 += "[.IX]";
					if (sumando1.getToken() == null)
                        operando1com = "temp";
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

	// Genera el c�digo de las multiplicacines y divisiones
	public void generaCodigoMultiplicaciones(ArrayList<Nodo> factores,
			ArrayList<String> operaciones, Nodo resultado) {
		if (factores.size() == 1) { // Si hay 1 sumando
			Nodo factor = factores.get(0);
			resultado.setLugar(getPosicionReal(factor));
		//	resultado.setTipo(factor.getTipo());
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
                    operando2com = "";
				}else operando2com = operando2;
				if (factor1.getValor() == null)
					operando1 += "#-";
				else operando1 += "#";
				operando1 += posicion1;
				if (factor1.getValor() == null){
					operando1 += "[.IX]";
					operando1com = "";
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

	// Genera el c�digo de la asignaci�n
	public void generaCodigoAsignacion(Nodo destino, Nodo origen) {
		int iTamanoVariables = 1;
//		iTamanoVariables = destino.getTipo().getTamanoTipo();
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
			operando1com = "";
		}else operando1com = operandoDestino;
		//lo mismo con el operando origen
		if (origen.getValor() == null)
			operandoOrigen += "#-";
		else operandoOrigen += "#";
		operandoOrigen += posicionOrigen;
		if (origen.getValor() == null){
			operandoOrigen += "[.IX]";
			operando2com = "";
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
				operando1com = "";
			}else operando1com = operandoDestino;
			if (origen.getValor() == null)
				operandoOrigen += "#-";
			else operandoOrigen += "#";
			operandoOrigen += posicionOrigen;
			if (origen.getValor() == null){
				operandoOrigen += "[.IX]";
				operando2com = "temp";
			}else operando2com = operandoOrigen;
			emite("MOVE " + operandoOrigen + "," + operandoDestino,
					  operando1com + " := " + operando2com);
		}

	}

	// Genera el c�digo de comparaciones
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
			operando2com = "";
		}else operando2com = operando2;
		if (comp1.getValor() == null)
			operando1 += "#-";
		else operando1 += "#";
		operando1 += posicion1;
		if (comp1.getValor() == null){
			operando1 += "[.IX]";
			operando1com = "";
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

	// Genera el c�digo de evaluaci�n de booleano
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
			operando2com = "";
		}else operando2com = operando2;

		emite("CMP #0," + operando2, operando2com + "== true?");
		emite("BZ /" + resultado.getSiguiente());
	}

	// Genera el c�digo de una llamada a subrutina
	public void GeneraCodigoLlamadaAFuncion(String nombre, Nodo resultado){
		emite("CALL /"+ nombre);
		resultado.setLugar(dameNuevaTemp(null, 1));
		emite("MOVE .IY,#-" + resultado.getLugar() + "[.IX]", "Receive Data");
		resultado.setToken(null);
		resultado.setValor(null);
	}

	// Genera el c�digo de gesti�n de la pila de cada subprograma
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
		// Apila el Registro de Activaci�n en IX
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

	// Pide la inserci�n de una variable y la inserta en su posici�n
	public void generaCodigoEntrada(Nodo identificador){
		String operando = new String();
		String operandorcom = "";
		int posicion = getPosicionReal(identificador);
		operando += "#-" + posicion + "[.IX]";
		emite("ININT " + operando, operandorcom + " <- read");
	}

	// Escribe el valor de una variable por terminal
	public void generaCodigoSalida(Nodo identificador){
		String operando = new String();
		String operandorcom = "";
		int posicion = getPosicionReal(identificador);
		operando += "#-" + posicion + "[.IX]";
		emite("WRINT " + operando, "write -> " + operandorcom);
	}

	/**
	 * Retorna la posici�n real en memoria del nodo deseado.
	 * @param arg0 valor sobre la pila en la que se encuentra el valor del nodo.
	 * @return
	 */
	private int getPosicionReal(Nodo arg0){
		int retorno = -1;
		if (arg0.getValor() == null) {
			if (arg0.getToken() != null) {
					InfoSimbolo at = tabla.busca(arg0.getLexema());

                    /****************
                     * HAY QUE ACCEDER A LA POSICION DEL ID,
                     * ESO NO ESTA ALMACENADO AUN, SE HACE AL GENERAR CODIGO
                     * DESDE LOS SLK ACTION
                     * HABRA QUE IR METIENDOLO
                     *
                     * en retorno meteremos la posicion cuando la tengamos,
                     * por ahora dejo un 0
                     */
                    retorno = 0;
                    //ARREGLAR ESTO CUANDO SE META POSICION EN SIMBOO
					//retorno = ((Integer)at.obtener("POSICION") + arg0.getOffset());
			}else
				//retorno = ((Integer)arg0.getLugar() + arg0.getOffset());
                  retorno = (Integer)arg0.getLugar();
		} else {
			retorno = (Integer)arg0.getValor();
		}
		return retorno;
	}
}
