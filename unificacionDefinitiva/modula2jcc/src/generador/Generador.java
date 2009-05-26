package generador;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Stack;

import semantico.Nodo;
import semantico.TipoSemantico;
import tabla_de_simbolos.TablaDeSimbolos;
import tabla_de_simbolos.simbolo.*;



public class Generador {

	/**
	 * Referencia de la interfaz
	 */
	public gui.Ventana _intefaz;

	/**
	 * Buffer de c�digo ensamblador
	 */
	private String _codigo;
	private PrintStream _file;
	private FileOutputStream _codObj;
	/**
	 * Contador de etiquetas a generar.
	 */
	private int _contadorEtiquetas;
        
	/**  Pila de contador de variables temporales por ambito */
	private Stack<Integer> _contadorVariables;
        /** cada ambito, es en realidad un array con sus variables dentro */
	private Stack<ArrayList<String>> _pilaListaVariables;

	private TablaDeSimbolos _tabla;

	private boolean _etiquetaUltimaEmision;

	/** Separaci�n de los comentarios desde la columna 0 */
	private int _SEPCODIGO = 25;

    private long _contadorTemporales =0;


	/**
	 * Constructor de la clase
	 */
	public Generador (TablaDeSimbolos ts, String fout) {
		String fichero = new String(fout);
		_codigo = new String();
		_contadorEtiquetas = 0;
                
                // estas dos se refieren al tema de los ambitos.
		_contadorVariables = new Stack<Integer>();  // inicialmente no hay hambitos, hay que crearlos
		_pilaListaVariables = new Stack<ArrayList<String>>();
                
		_tabla = ts;
		_etiquetaUltimaEmision = false;
		try{
			_codObj = new FileOutputStream(fichero);
			_file = new PrintStream(_codObj);
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
			_etiquetaUltimaEmision = false;
		} else {
			if (_etiquetaUltimaEmision)
				temp += "\tNOP \n";
			_etiquetaUltimaEmision = true;
		}
		temp += arg0;
		_codigo += temp + "\n";
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
		for (int i=arg0.length(); i<_SEPCODIGO; i++) comentario += " ";
		comentario += "; " + com;
		_codigo += temp + comentario + "\n";
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
		_codigo = temp + _codigo;
	}


	/**
	 * Escribe el c�digo almacenado en el buffer.
	 */
	public void escribeSeccion(){
		_file.println(_codigo);
		// IMPLEMENTAR ESTO intefaz.actualizarCodigoEns(codigo);
		_codigo = new String();
	}

	/**
	 * Retorna una nueva etiqueta generada.
	 * @return Etiqueta temporal generada
	 */
	public String dameNuevaEtiqueta(){
		_contadorEtiquetas++;
		return ("temp" + (_contadorEtiquetas - 1));
	}

	/**
	 * Retorna una nueva variable temporal
	 * @param size tama�o del dato temporal
	 * @return Nueva variable temporal
	 */
	public String dameNuevaTemp(String nombre, int size){
		if (nombre == null) nombre = "; variable temp";
		else nombre = "; variable " + nombre;
		Integer numero = _contadorVariables.pop();
		_contadorVariables.push(numero + size);
		for (int i = 0; i < size; i++)
			_pilaListaVariables.peek().add(nombre);

                InfoSimbolo s = _tabla.busca(nombre);
                if (s != null && s.getTipoSimbolo() == TipoSimbolo.VARIABLE){
                    InfoSimboloVar v = (InfoSimboloVar)s;
                    v.setLugar("#"+numero+"[.IX]");
                    v.setTama(size);
                }      
                
		return "#"+numero+"[.IX]";
	}

	/**
	 * Abre un nuevo �mbito para declaraci�n de variables.
         * Obligatorio llamarlo para abrir lo que sea, funciones o modulos
         * como si es el programa principal
	 */
	public void abreAmbito(){
            // cuando abres un ambito no hay nada dentro, ninguna variable
		Integer temp = new Integer(0);  
		_contadorVariables.push(temp);
		ArrayList<String> variables = new ArrayList<String>();
		_pilaListaVariables.add(variables);
	}

	/**
	 * Cierra el �mbito actual y vuelve al �mbito padre.
	 */
	public void cierraAmbito() throws Exception{
            if (_pilaListaVariables.size() == 0)
                throw  new Exception("no hay ambito del Generador que cerrar");
            
		_contadorVariables.pop();
		_pilaListaVariables.pop();
	}

	/**
	 * Retorna el tama�o de la pila necesario para el �mbito actual
	 * @return tama�o de la pila
	 */
	public int getTamanoTotalVariables(boolean esFuncion){
		return _contadorVariables.peek();
	}

	/*
	 * M�todos de generaci�n
	 */

	// Genera el c�digo de las sumas y restas
	public String generaCodigoAritmetica(Nodo sumando1, Nodo sumando2, Nodo operador){

         String direcOp1, direcOp2;
         /*tres casos para cada uno de los operandos*/

            if(sumando1.getLexema()!= null){
                // *     1º sumando es un identificador
                InfoSimbolo operador1;
                if((operador1=_tabla.busca(sumando1.getLexema()))!=null){
                    direcOp1 = ((InfoSimboloVar)operador1).getLugar();
                }
                else{
                    //*     2º sumando es un literal
                    direcOp1 = generaNuevoLiteral(sumando1.getTipoSemantico(),sumando1.getLexema());
                }
            }
            else{
            //*     3º sumando es el resultado de una operacion anterior
             direcOp1 = sumando1.getLugar();
            }

         // lo mismo para el operando 2
            if(sumando2.getLexema()!= null){
                // *     1º sumando es un identificador
                InfoSimbolo operador2;
                if((operador2=_tabla.busca(sumando2.getLexema()))!=null){
                    direcOp2 = ((InfoSimboloVar)operador2).getLugar();
                }
                else{
                    //*     2º sumando es un literal
                    direcOp2 = generaNuevoLiteral(sumando2.getTipoSemantico(),sumando2.getLexema());
                }
            }
            else{
            //*     3º sumando es el resultado de una operacion anterior
             direcOp2 = sumando2.getLugar();
            }
        // nuevo temporal
        String dirRes = dameNuevaTemp(null, 1);
        emite("MOVE " + dirRes + " " + direcOp1);

        // un switch para la operacion:
        String op ="pero que es estoooooo";
        switch(operador.getTipoToken()){
            case OPERADOR_MULTIPLICADOR:
                if (operador.getLexema().equals("DIVISION"))
                    op = "DIV";
                else if (operador.getLexema().equals("MULTIPLICACION"))
                    op = "MUL";
                break;
            case OPERADOR_SUMADOR:
                if (operador.getLexema().equals("SUMA"))
                    op = "SUM";
                else if (operador.getLexema().equals("RESTA"))
                    op = "SUB";
                break;
        }
        emite(op + " " + dirRes + " " + direcOp2 + "; suma ");

		return dirRes;
	}

	// Genera el c�digo de las multiplicacines y divisiones
	public void generaCodigoMultiplicaciones(ArrayList<Nodo> factores){
		
	}

	// Genera el c�digo de la asignaci�n
	public void generaCodigoAsignacion(Nodo destino, Nodo origen) {
		emite("MOVE "+ destino.getLugar() + " " + origen.getLugar() + " ; Asignacion");
	}
// Genera el c digo de comparaciones
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
		
	}

	// Genera el c�digo de una llamada a subrutina
	public void GeneraCodigoLlamadaAFuncion(String nombre, Nodo resultado){
		emite("CALL /"+ nombre);
		resultado.setLugar(dameNuevaTemp(null, 1));
	//	emite("MOVE .IY,#-" + resultado.getLugar() + "[.IX]", "Receive Data");
		resultado.setToken(null);
	}

	// Genera el c�digo de gesti�n de la pila de cada subprograma
	public void generaCodigoSubprograma(String nombre, boolean esFuncion) throws Exception {
            if (_pilaListaVariables.size() == 0)
                throw new Exception("no podemos generar el codigo de <" + nombre +
                                    "> nunca se creo su ambito");
            
		String previo = "PUSH #0";
		int espacio = getTamanoTotalVariables(esFuncion);
		for (int i = 0; i < espacio; i++) {
			anadeAlComienzo(_pilaListaVariables.peek().get(_pilaListaVariables.peek().size() - 1 - i));
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
	
	}

	// Escribe el valor de una variable por terminal
	public void generaCodigoSalida(Nodo identificador){

	}

    private String generaNuevoLiteral(ArrayList<TipoSemantico> tipoSemantico, String lexema) {

        String nmb = "; Temporal" + _contadorTemporales;
        _contadorTemporales++;

		Integer numero = _contadorVariables.pop();
        // TODO: el tamaño debe ser en función del tipo semantico.
		_contadorVariables.push(numero + 1);
		_pilaListaVariables.peek().add(nmb);

        String lugar = "#" + numero + "[.IX]";
        emite("MOVE " + "#" + lexema + " " + lugar + "; inicializacion de " + nmb);
                
		return lugar ;
    }

      /**
         * Retorna la posici n real en memoria del nodo deseado.
         * @param arg0 valor sobre la pila en la que se encuentra el valor del nodo.
         * @return
         */
        private int getPosicionReal(Nodo arg0){
                int retorno = -1;
                if (arg0.getValor() == null) {
                        if (arg0.getToken() != null) {
                                        InfoSimbolo at = _tabla.busca(arg0.getLexema());

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
                        }else{
                                //retorno = ((Integer)arg0.getLugar() + arg0.getOffset());


                  /**
                   * Comentado para mejora de lugar como un String y no como un object
                   * Para que funcionen las expresiones y lo demas
                   */
                  //retorno = (Integer)arg0.getLugar();

                        }
                } else {
                        retorno = (Integer)arg0.getValor();
                }
                return retorno;
        }


}
