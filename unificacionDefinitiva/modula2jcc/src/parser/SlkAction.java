package parser;

import semantico.PilaNodos;
import gestor_de_errores.GestorErrores;
import gestor_de_errores.TErrorSemantico;
import java.util.ArrayList;
import scanner.TipoToken;
import semantico.Nodo;
import semantico.TipoSemantico;
import tabla_de_simbolos.TablaDeSimbolos;
import tabla_de_simbolos.simbolo.*;

/**
 * Clase que implementa las acciones semanticas.
 * 
 * @author Macrogrupo 1
 */
public class SlkAction {

    /**
     * Gestor de errores del compilador.
     */
    private GestorErrores _gestorDeErrores;
    /**
     * Pila de Nodos para el tratamiento de las acciones semanticas.
     */
    private PilaNodos _pilaNodos;
    /**
     * Tabla activa en cada momento. 
     */
    private TablaDeSimbolos _tablaActual;
    /**
     * Variable que sirve para identifcar la primera vez que se entre en la 
     * regla de definicion de un modulo. Cuando valga true significará
     * que el modulo global ha sido renombrado. 
     */
    private boolean _tablaGlobalNombrada = false;

    /**
     * Constructor de la clase SlkAction.
     * 
     * @param tabla Tabla de simbolos del compilador.
     */
    public SlkAction(TablaDeSimbolos tablaActual, GestorErrores gestorDeErrores, PilaNodos pilaNodos) {

        _gestorDeErrores = gestorDeErrores;
        _pilaNodos = pilaNodos;
        _tablaActual = tablaActual;
    }

    /**
     * Metodo invocado por Slk consistente en la bifurcacion hacia el procedimiento
     * que trata la accion semantica correspondiente.
     * 
     * @param number La accion semantica correspondiente.
     */
    public void execute(int number) {
        switch (number) {
        case 1:  FinDeModulo();  break;
    case 2:  ComienzoDeModulo();  break;
    case 3:  DefinicionDeTipo();  break;
    case 4:  AsociacionConstante();  break;
    case 5:  TipoConjunto();  break;
    case 6:  TipoPuntero();  break;
    case 7:  ponerMarcaListaVariables();  break;
    case 8:  quitarMarcaListaVariables();  break;
    case 9:  DeclaracionVariables();  break;
    case 10:  InicioDeclaraciónProcedure();  break;
    case 11:  CabeceraDeProcedure();  break;
    case 12:  ExpresionIF();  break;
    case 13:  SentenciaIF();  break;
    case 14:  ExpresionELSIF();  break;
    case 15:  RestoSentenciaELSIF();  break;
    case 16:  RestoSentenciaELSE();  break;
    case 17:  ExpresionWHILE();  break;
    case 18:  SentenciaWHILE();  break;
    case 19:  ExpresionREPEAT();  break;
    case 20:  SentenciaREPEAT();  break;
    case 21:  SentenciaLOOP();  break;
    case 22:  Cadena();  break;
    case 23:  Caracter();  break;
    case 24:  NumeroEntero();  break;
    case 25:  NumeroReal();  break;
    case 26:  TipoPredefinidoPorUsuario();  break;
    case 27:  BITSET();  break;
    case 28:  BOOLEAN();  break;
    case 29:  CARDINAL();  break;
    case 30:  CHAR();  break;
    case 31:  INTEGER();  break;
    case 32:  LONGINT();  break;
    case 33:  LONGREAL();  break;
    case 34:  PROC();  break;
    case 35:  REAL();  break;
    case 36:  TRUE();  break;
    case 37:  FALSE();  break;
    case 38:  NIL();  break;
        }
    }

    /**
     * Abre un nuevo ambito y actualiza el nombre de la nueva tabla 
     * correspondiente a dicho ambito. Se Comprueba que el nombre del 
     * módulo no esta declarado en la tabla de símbolos.
     */
    private void ComienzoDeModulo() {

        Nodo id = _pilaNodos.pop();

        // Si la tabla GLOBAL ha sido nombrada
        if (_tablaGlobalNombrada) {

            // Si el modulo no está declarado
            if (!_tablaActual.estaModuloDeclarado(id.getLexema())) {

                // Insertamos el identificador del modulo en la tabla de simbolos actuales
                _tablaActual.inserta(id.getLexema(), TipoSimbolo.MODULO);
                
                // Completamos el tipo semantico del identificador
                InfoSimboloModulo info = (InfoSimboloModulo)_tablaActual.busca(id.getLexema());
                ArrayList<TipoSemantico> tipo = new ArrayList<TipoSemantico>();
                tipo.add(TipoSemantico.MODULO);
                info.setTipoSemantico(tipo);
                
                // Se abre un ambito
                _tablaActual = _tablaActual.abrirAmbito();
                _tablaActual.setNombre(id.getLexema());

            } else {

                _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico("El simbolo \"" + id.getLexema() + "\" ya esta definido.",
                        id.getLinea(),
                        id.getColumna()));
            }
        } else { // NO SE INSERTA EL IDENTIFICADOR EN LA TS GLOBAL!!
            
            // Completamos el nombre de la tabla GLOBAL
            _tablaActual.setNombre(id.getLexema());

            // Marcamos que ya esta nombrada por lo que ya siempre entrará
            // por el IF de arriba y creará los ambitos de los módulos 
            // correspondientes.
            _tablaGlobalNombrada = true;
        }
    }

    private void ExpresionELSIF() {
        //RestoSentenciaIF:
        //{ ELSIF Expresion _action_ExpresionELSIF THEN SecuenciaDeSentencias _action_RestoSentenciaELSIF } [ ELSE SecuenciaDeSentencias _action_RestoSentenciaELSE ]
             
        /*para hacer pruebas
        System.out.println("ExpresionELSIF");
        Nodo nodoElsIf = new Nodo();
        _pilaNodos.push(nodoElsIf);
        Nodo expresion = new Nodo();
        expresion.addTipo(TipoSemantico.BOOLEANO);
        _pilaNodos.push(expresion);
        /*para hacer pruebas*/

        Nodo nodo1 = _pilaNodos.pop(); //Expresion
        Nodo nuevo = new Nodo();

        if (nodo1.getTipoBasico().equals(TipoSemantico.BOOLEANO)) {
            nuevo.addTipo(TipoSemantico.VOID);
            _pilaNodos.push(nuevo);
        } else {
            nuevo.addTipo(TipoSemantico.ERROR);
            _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico("Tipo no booleano en la expresion ELSIF",
                    nodo1.getLinea(),
                    nodo1.getColumna()));
            _pilaNodos.push(nuevo);
        }
        
    }

    private void ExpresionIF() {
        //SentenciaIF:
        //IF Expresion _action_ExpresionIF THEN SecuenciaDeSentencias RestoSentenciaIF END _action_SentenciaIF
        
        /*para hacer pruebas
        System.out.println("ExpresionIF");
        Nodo nodoIf = new Nodo();
        _pilaNodos.push(nodoIf);
        Nodo expresion = new Nodo();
        expresion.addTipo(TipoSemantico.BOOLEANO);
        _pilaNodos.push(expresion);
        /*para hacer pruebas*/

        Nodo nodo1 = _pilaNodos.pop();
        Nodo nuevo = new Nodo();

        if (nodo1.getTipoBasico().equals(TipoSemantico.BOOLEANO)) {
            nuevo.addTipo(TipoSemantico.VOID);
            _pilaNodos.push(nuevo);
        } else {
            nuevo.addTipo(TipoSemantico.ERROR);
            _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico("Tipo no booleano en la expresion if",
                    nodo1.getLinea(),
                    nodo1.getColumna()));
            _pilaNodos.push(nuevo);
        }

    }

    private void ExpresionREPEAT() {
        //SentenciaREPEAT:
        //REPEAT SecuenciaDeSentencias UNTIL Expresion _action_ExpresionREPEAT _action_SentenciaREPEAT

        /*para hacer pruebas*
        Nodo nodoRepeat = new Nodo();
        Nodo nodoUntil = new Nodo();
        Nodo SecuenciaDeSentencias = new Nodo();
        SecuenciaDeSentencias.addTipo(TipoSemantico.VOID);
        System.out.println("ExpresionREPEAT");
        Nodo expresion = new Nodo();
        expresion.addTipo(TipoSemantico.BOOLEANO);
        _pilaNodos.push(nodoRepeat);
        _pilaNodos.push(SecuenciaDeSentencias);
        _pilaNodos.push(nodoUntil);
        _pilaNodos.push(expresion);
        /*para hacer pruebas*/

        Nodo nodo1 = _pilaNodos.pop(); //Expresion
        Nodo nuevo = new Nodo();

        if (nodo1.getTipoBasico().equals(TipoSemantico.BOOLEANO)) {
            nuevo.addTipo(TipoSemantico.VOID);
            _pilaNodos.push(nuevo);
        } else {
            nuevo.addTipo(TipoSemantico.ERROR);
            _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico("Tipo no booleano en la expresion REPEAT",
                    nodo1.getLinea(),
                    nodo1.getColumna()));
            _pilaNodos.push(nuevo);
        }

    }

    private void ExpresionWHILE() {
        //SentenciaWHILE:
        //WHILE Expresion _action_ExpresionWHILE DO SecuenciaDeSentencias END _action_SentenciaWHILE

        /*para hacer pruebas*/
        System.out.println("ExpresionWHILE");
        Nodo nodoWHILE = new Nodo();
        _pilaNodos.push(nodoWHILE);
        Nodo expresion = new Nodo();
        expresion.addTipo(TipoSemantico.BOOLEANO);
        _pilaNodos.push(expresion);
        /*para hacer pruebas*/

        Nodo nodo1 = _pilaNodos.pop(); //Expresion
        Nodo nuevo = new Nodo();

        if (nodo1.getTipoBasico().equals(TipoSemantico.BOOLEANO)) {
            nuevo.addTipo(TipoSemantico.VOID);
            _pilaNodos.push(nuevo);
        } else {
            nuevo.addTipo(TipoSemantico.ERROR);
            _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico("Tipo no booleano en la expresion WHILE",
                    nodo1.getLinea(),
                    nodo1.getColumna()));
            _pilaNodos.push(nuevo);
        }

    }


    /**
     * Comprueba que el nombre de la tabla y el del identificador despues del
     * END sean el mismo. Ademas cierra el ambito actual.
     */
    private void FinDeModulo() {

        Nodo id = _pilaNodos.pop();

        if (_tablaActual.getNombre().equals(id.getLexema())) {
            _tablaActual = _tablaActual.cerrarAmbito();
        } else {

            _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico("Simbolo \"" + id.getLexema() + "\" incorrecto, se esperaba \"" + _tablaActual.getNombre() + "\"",
                    id.getLinea(),
                    id.getColumna()));
        }
    }

    /**
     * Se completa el tipo del simbolo identificado por el lexema del identificador 
     * a valor Tipo CONSTANTE y el valor de la constante.                
     */
    private void AsociacionConstante() {

        try {

            Nodo valor = _pilaNodos.pop();
            _pilaNodos.pop();
            Nodo id = _pilaNodos.pop();

            if (!valor.esError()) {

                // Insertamos y completamos su informacion
                if (_tablaActual.inserta(id.getLexema(), TipoSimbolo.GENERAL)) {

                    // Obtenemos la informacion y la completamos
                    InfoSimboloGeneral info = (InfoSimboloGeneral) _tablaActual.busca(id.getLexema());
                    info.setTipoSemantico(valor.getTipoSemantico());
                    info.setEsConstante(true);

                } else {

                    _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico("El simbolo \"" + id.getLexema() + "\" ya esta definido.",
                            valor.getLinea(),
                            valor.getColumna()));
                }

            } else {

                _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico("El tipo \"" + valor.getLexema() + "\" no esta definido.",
                        valor.getLinea(),
                        valor.getColumna()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Se completa el tipo semantico de los identificadores y completando el
     * simbolo con el tipo VARIABLE.
     */
    private void DeclaracionVariables() {
        //REGLA: DeclaracionVariables: ListaIdentificadores : EsquemaDeTipo
        try {

            Nodo tipo = _pilaNodos.pop();

            if (!tipo.esError()) {

                Nodo id = null;

                do {
                    id = _pilaNodos.pop();

                    if (!id.esMarcaListaIdentificadores()) {

                        // Insertamos y completamos su informacion
                        if (_tablaActual.inserta(id.getLexema(), TipoSimbolo.GENERAL)) {

                            // Obtenemos la informacion y la completamos
                            _tablaActual.busca(id.getLexema()).setTipoSemantico(tipo.getTipoSemantico());
                        } else {

                            _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico("El simbolo \"" + id.getLexema() + "\" ya esta definido.",
                                    id.getLinea(),
                                    id.getColumna()));
                        }
                    }
                } while (!id.esMarcaListaIdentificadores());

                _pilaNodos.push(id); // Apilamos de nuevo la marca
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Se completa el tipo semantico del identificador y completando el
     * simbolo con el tipo TIPO.
     * Comprobación de errores:
     * Si se da un marca llamamos al gestor de errores y no apilamos el marca.
     * Si la parte valor es un identificador debemos comprobar que sea 
     * una definicion de valor.            
     */
    private void DefinicionDeTipo() {
        //REGLA: DefinicionDeTipo: Identificador = EsquemaDeTipo

        try {

            Nodo tipo = _pilaNodos.pop();
            _pilaNodos.pop(); // operador
            Nodo id = _pilaNodos.pop();

            InfoSimboloGeneral infoTipo = (InfoSimboloGeneral) _tablaActual.busca(tipo.getLexema());

            // Si es IDENTIFICADOR y no es de Tipo TIPO
            if (tipo.getTipoToken().equals(TipoToken.IDENTIFICADOR) && infoTipo != null && !infoTipo.getEsTipo()) {

                _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico("El tipo \"" + tipo.getLexema() + "\" no está definido.",
                        tipo.getLinea(),
                        tipo.getColumna()));
            } else {

                // Insertamos y completamos su informacion
                if (_tablaActual.inserta(id.getLexema(), TipoSimbolo.GENERAL)) {

                    // Completamos su informacion
                    InfoSimboloGeneral infoId = (InfoSimboloGeneral) _tablaActual.busca(id.getLexema());
                    infoId.setTipoSemantico(tipo.getTipoSemantico());
                    infoId.setEsTipo(true);
                } else {

                    _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico("El simbolo \"" + id.getLexema() + "\" ya esta definido.",
                            id.getLinea(),
                            id.getColumna()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * se comineza con un procedimineto
     */
    private void InicioDeclaraciónProcedure() {
    // CabeceraSubprograma:
    // PROCEDURE Identificador _action_InicioDeclaraciónProcedure [ ParametrosFormales ]
      
        // sacamos el identificador para el procedimiento
        Nodo nodo = _pilaNodos.peek(); 
        
        // comprobamos unicidad
        if (_tablaActual.estaModuloDeclarado(nodo.getLexema()))
             _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico("El identificador \"" + nodo.getLexema() + "\" esta siendo redefinido.",
                        nodo.getLinea(),
                        nodo.getColumna()));
        
        // abrimos ambito.       
        _tablaActual = _tablaActual.abrirAmbito();
        _tablaActual.setNombre(nodo.getLexema());
    }

    
    /** 
     * 
     * completa la definicion de un modulo
     */
    private void CabeceraDeProcedure() {
        // extrae elementos hasta que extraigo el nombre de la tabla de simbolos.
        // en ese momento puedo parar y definir el procedure.
         Nodo nodo = _pilaNodos.pop(); 
         String lexema = _tablaActual.getNombre();
         
         // inicializo los parametros
         int numArgs = 0;
         ArrayList<TipoPasoParametro> pasoArgumentos = new ArrayList<TipoPasoParametro>();
         ArrayList<ArrayList<TipoSemantico>> tipoArgumentos = new ArrayList<ArrayList<TipoSemantico>>();
         
         InfoSimboloGeneral param;
         
         // mientras que no sea el nombre de la tabla
         while (!nodo.getLexema().equals(lexema)){
             
             // si no es error:
             if (nodo.getTipoBasico() == TipoSemantico.ERROR){
                 _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico("error aqui " +
                                                    nodo.getLexema(),
                                                    nodo.getLinea(),
                                                    nodo.getColumna()));
                 return;
             }
             
             //un tipo, y un identificador.
             // primero el tipo:
            tipoArgumentos.add(nodo.getTipoSemantico());
              // TODO: necesito las palabras reservadas en la pila para saber cuando es paso por referencia
            pasoArgumentos.add(TipoPasoParametro.VALOR);
                       
            // despues el identificador:
            nodo = _pilaNodos.pop();
  
            _tablaActual.inserta(nodo.getLexema(), TipoSimbolo.GENERAL);
            param = (InfoSimboloGeneral) _tablaActual.busca(nodo.getLexema());
            
             numArgs ++;
             nodo = _pilaNodos.pop(); 
         }
         
         // cierra el ambito
         TablaDeSimbolos ambitoProc = _tablaActual;         
         _tablaActual = _tablaActual.cerrarAmbito();
         
         // completa el simbolo
         _tablaActual.inserta(lexema, TipoSimbolo.SUBPROGRAMA);
         InfoSimboloSubprograma info = (InfoSimboloSubprograma) _tablaActual.busca(lexema);
         
         info.setNumArgs(numArgs);
        
         info.setPasoArgumentos(pasoArgumentos);
         info.setTipoArgumentos(tipoArgumentos);
         info.setAmbito (ambitoProc);
    }

    private void RestoSentenciaELSE() {
        //RestoSentenciaIF:
        //{ ELSIF Expresion _action_ExpresionELSIF THEN SecuenciaDeSentencias _action_RestoSentenciaELSIF } [ ELSE SecuenciaDeSentencias _action_RestoSentenciaELSE ]

        /*pruebas
        System.out.println("RestoSEntenciasELSE");
        Nodo SecuenciaDeSentencias = new Nodo();
        SecuenciaDeSentencias.addTipo(TipoSemantico.VOID);
        Nodo nodoElse = new Nodo();
        //Metemos esto para probarlo
        _pilaNodos.push(nodoElse);
        _pilaNodos.push(SecuenciaDeSentencias);
        /*FIN pruebas*/

        Nodo nodo1 = _pilaNodos.pop(); //SecuenciaDeSentencias
        _pilaNodos.pop(); //ELSE

        Nodo nuevo = new Nodo();
        if (nodo1.getTipoBasico().equals(TipoSemantico.VOID)) {
            nuevo.addTipo(TipoSemantico.VOID);
            _pilaNodos.push(nuevo);
        } else {
            nuevo.addTipo(TipoSemantico.ERROR);
            _pilaNodos.push(nuevo);
            _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico("Sentencia ELSE mal tipada",
                    nuevo.getLinea(),
                    nuevo.getColumna()));
        }

    }

    private void RestoSentenciaELSIF() {
        //RestoSentenciaIF:
        //{ ELSIF Expresion _action_ExpresionELSIF THEN SecuenciaDeSentencias _action_RestoSentenciaELSIF } [ ELSE SecuenciaDeSentencias _action_RestoSentenciaELSE ]

        /*pruebas
        System.out.println("RestoSentenciaELSIF");
        Nodo SecuenciaDeSentencias = new Nodo();
        SecuenciaDeSentencias.addTipo(TipoSemantico.VOID);
        Nodo nodoThen = new Nodo();
        //Metemos esto para probarlo
        _pilaNodos.push(nodoThen);
        _pilaNodos.push(SecuenciaDeSentencias);        
        /*FIN pruebas*/

        Nodo nodo1 = _pilaNodos.pop(); //SecuenciaDeSentencias
        _pilaNodos.pop(); //THEN
        Nodo nodo2 = _pilaNodos.pop(); //Expresion
        _pilaNodos.pop(); //ELSIF

        Nodo nuevo = new Nodo();
        if (nodo1.getTipoBasico().equals(TipoSemantico.VOID) &&
                nodo2.getTipoBasico().equals(TipoSemantico.VOID)) {
            nuevo.addTipo(TipoSemantico.VOID);
            _pilaNodos.push(nuevo);
        } else {
            nuevo.addTipo(TipoSemantico.ERROR);
            _pilaNodos.push(nuevo);
            _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico("Sentencia ELSIF mal tipada",
                    nuevo.getLinea(),
                    nuevo.getColumna()));
        }
        
    }

    private void SentenciaIF() {
        //SentenciaIF:
        //IF Expresion _action_ExpresionIF THEN SecuenciaDeSentencias RestoSentenciaIF END _action_SentenciaIF

        /*pruebas
        System.out.println("SentenciaIF");
        Nodo RestoSentenciaIF = new Nodo();
        RestoSentenciaIF.addTipo(TipoSemantico.VOID);
        Nodo SecuenciaDeSentencias = new Nodo();
        SecuenciaDeSentencias.addTipo(TipoSemantico.VOID);
        Nodo end = new Nodo();
        Nodo nodoThen = new Nodo();
        //Metemos esto para probarlo
        _pilaNodos.push(nodoThen);
        _pilaNodos.push(RestoSentenciaIF);
        _pilaNodos.push(SecuenciaDeSentencias);
        _pilaNodos.push(end);        
        /*FIN pruebas*/
        
        _pilaNodos.pop(); //END
        Nodo nodo1 = _pilaNodos.pop(); //RestoSentenciaIF
        Nodo nodo2 = _pilaNodos.pop(); //SecuenciaDeSentencias
        _pilaNodos.pop(); //THEN       
        Nodo nodo3 = _pilaNodos.pop(); //Expresion
        _pilaNodos.pop(); //IF

        Nodo nuevo = new Nodo();
        if (nodo1.getTipoBasico().equals(TipoSemantico.VOID) &&
                nodo2.getTipoBasico().equals(TipoSemantico.VOID) &&
                nodo3.getTipoBasico().equals(TipoSemantico.VOID)) {
            nuevo.addTipo(TipoSemantico.VOID);
            _pilaNodos.push(nuevo);
        } else {
            nuevo.addTipo(TipoSemantico.ERROR);
            _pilaNodos.push(nuevo);
            _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico("Sentencia IF mal tipada",
                    nuevo.getLinea(),
                    nuevo.getColumna()));
        }

    }

    private void SentenciaLOOP() {
        //SentenciaLOOP:
        //LOOP SecuenciaDeSentencias END _action_SentenciaLOOP

        /*pruebas
        System.out.println("SentenciaLOOP");
        Nodo SecuenciaDeSentencias = new Nodo();
        SecuenciaDeSentencias.addTipo(TipoSemantico.VOID);
        Nodo nodoEnd = new Nodo();
        Nodo nodoLoop = new Nodo();
        //Metemos esto para probarlo
        _pilaNodos.push(nodoLoop);
        _pilaNodos.push(SecuenciaDeSentencias);
        _pilaNodos.push(nodoEnd);
        /*FIN pruebas*/

        _pilaNodos.pop(); //END
        Nodo nodo1 = _pilaNodos.pop(); //SecuenciaDeSentencias
        _pilaNodos.pop(); //LOOP

        Nodo nuevo = new Nodo();
        if (nodo1.getTipoBasico().equals(TipoSemantico.VOID)) {
            nuevo.addTipo(TipoSemantico.VOID);
            _pilaNodos.push(nuevo);
        } else {
            nuevo.addTipo(TipoSemantico.ERROR);
            _pilaNodos.push(nuevo);
            _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico("Sentencia LOOP mal tipada",
                    nuevo.getLinea(),
                    nuevo.getColumna()));
        }

    }

    private void SentenciaREPEAT() {
        //SentenciaREPEAT:
        //REPEAT SecuenciaDeSentencias UNTIL Expresion _action_ExpresionREPEAT _action_SentenciaREPEAT

        /*pruebas*
        System.out.println("SentenciaREPEAT");
        /*FIN pruebas*/

        Nodo nodo1 = _pilaNodos.pop(); //Expresion
        _pilaNodos.pop(); //UNTIL
        Nodo nodo2 = _pilaNodos.pop(); //SecuenciaDeSentencias
        _pilaNodos.pop(); //REPEAT

        Nodo nuevo = new Nodo();
        if (nodo1.getTipoBasico().equals(TipoSemantico.VOID) &&
                nodo2.getTipoBasico().equals(TipoSemantico.VOID)) {
            nuevo.addTipo(TipoSemantico.VOID);
            _pilaNodos.push(nuevo);
        } else {
            nuevo.addTipo(TipoSemantico.ERROR);
            _pilaNodos.push(nuevo);
            _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico("Sentencia REPEAT mal tipada",
                    nuevo.getLinea(),
                    nuevo.getColumna()));
        }

    }

    private void SentenciaWHILE() {
        //SentenciaWHILE:
        //WHILE Expresion _action_ExpresionWHILE DO SecuenciaDeSentencias END _action_SentenciaWHILE

        /*pruebas
        System.out.println("SentenciaWHILE");
        Nodo SecuenciaDeSentencias = new Nodo();
        SecuenciaDeSentencias.addTipo(TipoSemantico.VOID);
        //Nodo nodoDO = new Nodo();
        Nodo nodoEND = new Nodo();
        //Metemos esto para probarlo
        //_pilaNodos.push(nodoDO);
        _pilaNodos.push(SecuenciaDeSentencias);
        _pilaNodos.push(nodoEND);  
        /*FIN pruebas*/

        _pilaNodos.pop(); //END
        Nodo nodo1 = _pilaNodos.pop(); //SecuenciaDeSentencias
        _pilaNodos.pop(); //DO
        Nodo nodo2 = _pilaNodos.pop(); //Expresion
        _pilaNodos.pop(); //WHILE

        Nodo nuevo = new Nodo();
        if (nodo1.getTipoBasico().equals(TipoSemantico.VOID) &&
                nodo2.getTipoBasico().equals(TipoSemantico.VOID)) {
            nuevo.addTipo(TipoSemantico.VOID);
            _pilaNodos.push(nuevo);
        } else {
            nuevo.addTipo(TipoSemantico.ERROR);
            _pilaNodos.push(nuevo);
            _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico("Sentencia WHILE mal tipada",
                    nuevo.getLinea(),
                    nuevo.getColumna()));
        }

    }
    
    /**
     * Se completa el tipo semantico del id con tipo CONJUNTO.
     * Si viene un marca lo propagamos volviendolo a apilar.
     */
    private void TipoConjunto() {
        //REGLA TipoConjunto: SET OF TipoSimple

        try {

            Nodo tipo = _pilaNodos.pop();

            // si no es marca completamos diciendole que es de valor conjunto
            if (!tipo.esError()) {
                tipo.addTipo(TipoSemantico.CONJUNTO);
            }
            // Si hay un marca lo propagamos
            _pilaNodos.push(tipo);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Comprobamos que el identificador que representa el valor
     * predefinido haya sido como marcado en la tabla de simbolos
     * como valor. Si no ha sido marcado como valor significa que no
     * se ha declarado en la parte del TYPE y por lo tanto es un
     * valor incorrecto.
     */
    private void TipoPredefinidoPorUsuario() {

        try {

            Nodo tipoPredefinido = _pilaNodos.pop(); // TIPO PREDEFINIDO

            InfoSimboloGeneral info = (InfoSimboloGeneral) _tablaActual.busca(tipoPredefinido.getLexema());

            if (info.getEsTipo()) {

                // Obtenemos el valor semantico del identificador predefinido
                tipoPredefinido.setTipo(info.getTipoSemantico());

                // Lo apilamos
                _pilaNodos.push(tipoPredefinido);
                
            } else {

                crearNodoError(tipoPredefinido);

                _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico("El tipo \"" + tipoPredefinido.getLexema() + "\" no está definido.",
                        tipoPredefinido.getLinea(),
                        tipoPredefinido.getColumna()));
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    

    /**
     * Se completa el tipo semantico del id con tipo PUNTERO.
     * Si viene un marca lo propagamos volviendolo a apilar.
     */
    private void TipoPuntero() {
        //REGLA: TipoPuntero:POINTER TO EsquemaDeTipo
        try {

            Nodo tipo = _pilaNodos.pop();

            // si no es marca completamos diciendole que es de valor puntero
            if (!tipo.esError()) {
                tipo.addTipo(TipoSemantico.PUNTERO);
            }
            // Si hay un marca lo propagamos
            _pilaNodos.push(tipo);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void TRUE() {

        _pilaNodos.peek().addTipo(TipoSemantico.BOOLEANO);
    }

    private void FALSE() {

        _pilaNodos.peek().addTipo(TipoSemantico.BOOLEANO);
    }

    private void NIL() {

        _pilaNodos.peek().addTipo(TipoSemantico.PUNTERO);
    }

    private void BITSET() {

        _pilaNodos.peek().addTipo(TipoSemantico.BITSET);
    }

    private void BOOLEAN() {

        _pilaNodos.peek().addTipo(TipoSemantico.BOOLEANO);
    }

    private void CARDINAL() {

        _pilaNodos.peek().addTipo(TipoSemantico.CARDINAL);
    }

    private void CHAR() {

        _pilaNodos.peek().addTipo(TipoSemantico.CARACTER);
    }

    private void INTEGER() {

        _pilaNodos.peek().addTipo(TipoSemantico.ENTERO);
    }

    private void LONGINT() {

        _pilaNodos.peek().addTipo(TipoSemantico.ENTERO_LARGO);
    }

    private void LONGREAL() {

        _pilaNodos.peek().addTipo(TipoSemantico.REAL_LARGO);
    }

    private void PROC() {

        _pilaNodos.peek().addTipo(TipoSemantico.PROCEDIMIENTO);
    }

    private void REAL() {

        _pilaNodos.peek().addTipo(TipoSemantico.REAL);
    }

    private void Cadena() {

        _pilaNodos.peek().addTipo(TipoSemantico.CADENA);
    }

    private void Caracter() {

        _pilaNodos.peek().addTipo(TipoSemantico.CARACTER);
    }

    private void NumeroEntero() {

        _pilaNodos.peek().addTipo(TipoSemantico.ENTERO);
    }

    private void NumeroReal() {

        _pilaNodos.peek().addTipo(TipoSemantico.REAL);
    }

    /**
     * Crea un id marca y lo apila en la pila. 
     */
    private void crearNodoError(Nodo tipoPredefinido) {

        Nodo error = new Nodo(tipoPredefinido.getLexema(), 
                                tipoPredefinido.getLinea(),
                                tipoPredefinido.getColumna(),
                                tipoPredefinido.getTipoToken());
        
        error.addTipo(TipoSemantico.ERROR);
        _pilaNodos.add(error);
    }

    /**
     * Añade una marca en la pila para poder desapilar la lista hasta este 
     * elemento.
     */
    private void ponerMarcaListaVariables() {

        // Creamos y apilamos el id de marca de inicio de la lista
        Nodo primerIdent = _pilaNodos.pop();

        Nodo marca = new Nodo();
        marca.crearMarcaListaIdentificadores();
        _pilaNodos.push(marca);

        _pilaNodos.push(primerIdent);
    }

    /**
     * Quita la marca de la cima de la pila.
     */
    private void quitarMarcaListaVariables() {

        _pilaNodos.pop();
    }
    
    
}
