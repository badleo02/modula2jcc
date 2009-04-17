package parser;

import semantico.PilaNodos;
import gestor_de_errores.GestorErrores;
import gestor_de_errores.TErrorSemantico;
import java.util.ArrayList;
import org.apache.log4j.Logger;
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

    /** Objeto logger para mostrar trazas */
    private static final Logger logger = Logger.getLogger(SlkAction.class);


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
    switch ( number ) {
    case 1:  FinDeModulo();  break;
    case 2:  ComienzoDeModulo();  break;
    case 3:  finDeAmbito();  break;
    case 4:  DefinicionDeTipo();  break;
    case 5:  AsociacionConstante();  break;
    case 6:  TipoConjunto();  break;
    case 7:  TipoPuntero();  break;
    case 8:  ponerMarcaListaVariables();  break;
    case 9:  quitarMarcaListaVariables();  break;
    case 10:  DeclaracionVariables();  break;
    case 11:  InicioDeclaracionProcedure();  break;
    case 12:  CabeceraDeProcedure();  break;
    case 13:  SecuenciaDeSentencias2();  break;
    case 14:  SecuenciaDeSentencias();  break;
    case 15:  SentenciaAsignacion();  break;
    case 16:  RestoSentenciaAsignacion();  break;
    case 17:  IdentificadorOProcPredef_Ident();  break;
    case 18:  IdentificadorOProcPredef_ProcPredef();  break;
    case 19:  ExpresionIF();  break;
    case 20:  SentenciaIF();  break;
    case 21:  ExpresionELSIF();  break;
    case 22:  RestoSentenciaELSIF();  break;
    case 23:  RestoSentenciaELSE();  break;
    case 24:  RestoSentenciaIF();  break;
    case 25:  ExpresionWHILE();  break;
    case 26:  SentenciaWHILE();  break;
    case 27:  ExpresionREPEAT();  break;
    case 28:  SentenciaREPEAT();  break;
    case 29:  SentenciaLOOP();  break;
    case 30:  IdentificadorFOR();  break;
    case 31:  ExpresionTO();  break;
    case 32:  ExpresionConstanteFOR();  break;
    case 33:  SentenciaFOR();  break;
    case 34:  Cadena();  break;
    case 35:  Caracter();  break;
    case 36:  expresionSinParentesisDeSuma();  break;
    case 37:  operadorUnario();  break;
    case 38:  expresionSinParentesisDeMultiplicacion();  break;
    case 39:  OperadorBooleano();  break;
    case 40:  NumeroEntero();  break;
    case 41:  NumeroReal();  break;
    case 42:  TipoPredefinidoPorUsuario();  break;
    case 43:  BITSET();  break;
    case 44:  BOOLEAN();  break;
    case 45:  CARDINAL();  break;
    case 46:  CHAR();  break;
    case 47:  INTEGER();  break;
    case 48:  LONGINT();  break;
    case 49:  LONGREAL();  break;
    case 50:  PROC();  break;
    case 51:  REAL();  break;
    case 52:  TRUE();  break;
    case 53:  FALSE();  break;
    case 54:  NIL();  break;
  
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

            boolean error = false;

            // Si el modulo no está declarado
            if (!_tablaActual.estaModuloDeclarado(id.getLexema())) {

                // Insertamos el identificador del modulo en la tabla de simbolos actuales
                if (_tablaActual.declaraSimbolo(id.getLexema())) {

                    // Completamos el tipo semantico del identificador
                    if (_tablaActual.completaModulo(id.getLexema())) {

                        // Se abre un ambito
                        _tablaActual = _tablaActual.abrirAmbito();
                        _tablaActual.setNombre(id.getLexema());
                    } else {
                        error = true;
                    }
                } else {
                    error = true;
                }
            } else {
                error = true;
            }
            if (error) {
                _gestorDeErrores.insertaErrorSemantico(
                        new TErrorSemantico("El simbolo \"" + id.getLexema() + "\" ya esta definido.",
                        id.getLinea(),
                        id.getColumna()));

            } else { // NO SE INSERTA EL IDENTIFICADOR EN LA TS GLOBAL!!

                // Completamos el nombre de la tabla GLOBAL
                _tablaActual.setNombre(id.getLexema());

                // Marcamos que ya esta nombrada por lo que ya siempre entrará
                // por el IF de arriba y creará los ambitos de los módulos 
                // correspondientes.
                _tablaGlobalNombrada = true;
            }
        }
        _tablaActual.setNombre(id.getLexema());
        _tablaGlobalNombrada = true;
    }

    private void ExpresionConstanteFOR() {

        Nodo nodo1 = _pilaNodos.pop(); //ExpresionConstante

        //no comprobamos que ExpresionConstante está  bien porque ya se hace en SentenciaFOR
        //solo añadimos un nodo Marca a la pila

        _pilaNodos.push(nodo1);

        Nodo nodoMarca = new Nodo();
        nodoMarca.crearMarca();
        _pilaNodos.push(nodoMarca);
    }

    private void ExpresionELSIF() {
        //RestoSentenciaIF:
        //{ ELSIF Expresion _action_ExpresionELSIF THEN SecuenciaDeSentencias _action_RestoSentenciaELSIF } [ ELSE SecuenciaDeSentencias _action_RestoSentenciaELSE ]
             
        /*para hacer pruebas*
        System.out.println("ExpresionELSIF");
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
        
        /*para hacer pruebas*
        System.out.println("ExpresionIF");
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
        Nodo SecuenciaDeSentencias = new Nodo();
        SecuenciaDeSentencias.addTipo(TipoSemantico.VOID);
        System.out.println("ExpresionREPEAT");
        Nodo expresion = new Nodo();
        expresion.addTipo(TipoSemantico.BOOLEANO);
        _pilaNodos.push(SecuenciaDeSentencias);
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


    private void ExpresionTO() {
        //SentenciaFOR:
        //FOR Identificador := Expresion _action_IdentificadorFOR TO Expresion _action_ExpresionTO [ BY ExpresionConstante _action_ExpresionConstanteFOR ] DO SecuenciaDeSentencias END _action_SentenciaFOR

        Nodo nodo1 = _pilaNodos.pop(); //sacamos expresion
        Nodo nodo3 = _pilaNodos.pop(); // sacamos la expresion de la asignacion para comprobar tipos
        // y ver que nodo1 sea mayor que nodo3

        if ((nodo1.getTipoBasico().equals(nodo3.getTipoBasico())) && (nodo1.getLexema().compareTo(nodo3.getLexema()) > 0)) {
            _pilaNodos.push(nodo1);
        } else {
            Nodo n = new Nodo();
            n.addTipo(TipoSemantico.ERROR);
            _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico("Sentencia ExpresionTO mal tipada",
                    nodo1.getLinea(),
                    nodo1.getColumna()));
            _pilaNodos.push(n);
        }
    }

    private void ExpresionWHILE() {
        //SentenciaWHILE:
        //WHILE Expresion _action_ExpresionWHILE DO SecuenciaDeSentencias END _action_SentenciaWHILE

        /*para hacer pruebas*
        System.out.println("ExpresionWHILE");
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
        
        
        //esto ya lo ha hecho finDeAmbito; no preguntes por que
        
//
//        Nodo id = _pilaNodos.pop();
//
//        if (_tablaActual.getNombre().equals(id.getLexema())) {
//            _tablaActual = _tablaActual.cerrarAmbito();
//        } else {
//
//            _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico("Simbolo \"" + id.getLexema() + "\" incorrecto, se esperaba \"" + _tablaActual.getNombre() + "\"",
//                    id.getLinea(),
//                    id.getColumna()));
//        }
    }

    private void IdentificadorFOR() {
        //SentenciaFOR:
        //FOR Identificador := Expresion _action_IdentificadorFOR TO Expresion _action_ExpresionTO [ BY ExpresionConstante _action_ExpresionConstanteFOR ] DO SecuenciaDeSentencias END _action_SentenciaFOR

        //para probar porque no estan echas las expresiones
        // pero reconoce un solo numero, un solo caracter......
        Nodo nodo1 = _pilaNodos.pop();//saca expresion
        Nodo nodo2 = _pilaNodos.pop(); // saca identificador

        // buscamos el tipo del identificador en la tabla
        TipoSemantico tipo = _tablaActual.busca(nodo2.getLexema()).getTipoBasico();

        // miramos si la expresion es del mismo tipo del identificador para asignarselo
        if (tipo.equals(nodo1.getTipoBasico())) {
            _pilaNodos.push(nodo1);
        } else {
            Nodo n = new Nodo();
            n.addTipo(TipoSemantico.ERROR);
            _pilaNodos.add(n);
            _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico("Sentencia IdentificadorFOR mal tipada",
                    nodo1.getLinea(),
                    nodo1.getColumna()));
        }
    }

    private void IdentificadorOProcPredef_Ident() {
        //IdentificadorOProcPredef:
        //Identificador _action_IdentificadorOProcPredef_Ident

        Nodo nodo1 = _pilaNodos.pop(); //Identificador
        Nodo nuevo = new Nodo();

        if (nodo1.getTipoBasico().equals(TipoSemantico.VOID)) {
            nuevo.addTipo(TipoSemantico.VOID);
            _pilaNodos.push(nuevo);
        } else {
            nuevo.addTipo(TipoSemantico.ERROR);
            _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico("Identificador NO Válido",
                    nodo1.getLinea(),
                    nodo1.getColumna()));
            _pilaNodos.push(nuevo);
        }

    }

    private void IdentificadorOProcPredef_ProcPredef() {
        //IdentificadorOProcPredef:
        //LlamadaProcedimientoPredefinido _action_IdentificadorOProcPredef_ProcPredef

        Nodo nodo1 = _pilaNodos.pop(); //LlamadaProcedimientoPredefinido
        Nodo nuevo = new Nodo();

        if (nodo1.getTipoBasico().equals(TipoSemantico.VOID)) {
            nuevo.addTipo(TipoSemantico.VOID);
            _pilaNodos.push(nuevo);
        } else {
            nuevo.addTipo(TipoSemantico.ERROR);
            _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico("Llamada a Procedimiento Predefinido NO Válida",
                    nodo1.getLinea(),
                    nodo1.getColumna()));
            _pilaNodos.push(nuevo);
        }

    }

    private void OperadorBooleano() {
        if(logger.isDebugEnabled()){
            logger.debug("Entrando en el metodo operadorBooleano");
        }
        if(logger.isDebugEnabled()){
            logger.debug("Salida con exito de OperadorBooleano");
        }
    }

    private void RestoSentenciaAsignacion() {
        //RestoSentenciaAsignacion:
        //ParteIzquierda RestoSentenciaRestoAsignacion _action_RestoSentenciaAsignacion

        Nodo nodo1 = _pilaNodos.pop(); //RestoSentenciaRestoAsignacion
        Nodo nodo2 = _pilaNodos.pop(); //ParteIzquierda

        Nodo nuevo = new Nodo();
        if (nodo1.getTipoBasico().equals(TipoSemantico.VOID) && nodo2.getTipoBasico().equals(TipoSemantico.VOID)) {
            nuevo.addTipo(TipoSemantico.VOID);
            _pilaNodos.push(nuevo);
        } else {
            nuevo.addTipo(TipoSemantico.ERROR);
            _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico("Sentencia de Asignación NO Válida",
                    nodo1.getLinea(),
                    nodo1.getColumna()));
            _pilaNodos.push(nuevo);
        }

    }

    private void RestoSentenciaIF() {
        //RestoSentenciaIF:
        //{ ELSIF Expresion _action_ExpresionELSIF THEN SecuenciaDeSentencias _action_RestoSentenciaELSIF } [ ELSE SecuenciaDeSentencias _action_RestoSentenciaELSE ] _action_RestoSentenciaIF
        
        //sacamos el primerNodo de la pila, si es una marca entonces es que no es vacio y comprobamos lo que hay
        //si no es una marca es el nodo de SecuenciaDeSentencias, le volvemos a poner y colocamos otro nuevo tipo VOID
        //para indicar que RestoSentenciaIF aunque es vacio es correcto
        
        /***pruebas***
        System.out.println("Entrando en RestoSentenciaIF");
        /***fin pruebas***/
        
        Nodo nodo1 = _pilaNodos.pop(); //puede ser MARCA o SencuenciaDeSentencias
        
        if (nodo1.esMarca()){
            Nodo nodo2 = _pilaNodos.pop(); //SecuenciaDeSentencias
            Nodo nodo3 = _pilaNodos.pop(); //puede ser o MARCA o Expresion
            if (nodo3.esMarca()){
                //sacamos SecuenciaDeSentencias y Expresion y comprobamos nodo2, nodo4 y nodo5
                Nodo nodo4 = _pilaNodos.pop(); //SecuenciaDeSentencias
                Nodo nodo5 = _pilaNodos.pop(); //Expresion
                if (nodo2.getTipoBasico().equals(TipoSemantico.VOID) && nodo4.getTipoBasico().equals(TipoSemantico.VOID)
                        && nodo5.getTipoBasico().equals(TipoSemantico.VOID)) {
                    Nodo n = new Nodo();
                    n.addTipo(TipoSemantico.VOID);
                    _pilaNodos.push(n);
                } else {
                    Nodo n = new Nodo();
                    n.addTipo(TipoSemantico.ERROR);
                    _pilaNodos.push(n);
                    _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico("Sentencia RestoSentenciaIF mal tipada",
                        nodo1.getLinea(),
                        nodo1.getColumna()));
                }

            } else { //es Expresion
                //vemos si Expresion y SecuenciaDeSentencias son correctas
                if (nodo2.getTipoBasico().equals(TipoSemantico.VOID) && nodo3.getTipoBasico().equals(TipoSemantico.VOID)) {
                    Nodo n = new Nodo();
                    n.addTipo(TipoSemantico.VOID);
                    _pilaNodos.push(n);
                } else {
                    Nodo n = new Nodo();
                    n.addTipo(TipoSemantico.ERROR);
                    _pilaNodos.push(n);
                    _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico("Sentencia RestoSentenciaIF mal tipada",
                        nodo1.getLinea(),
                        nodo1.getColumna()));
                }
            }
        } else {//es SecuenciaDeSentencias            
            _pilaNodos.push(nodo1); //se vuelve a meter sin tocarlo
            //se añade un nodo void nuevo
            Nodo n = new Nodo();
            n.addTipo(TipoSemantico.VOID);
            _pilaNodos.push(n);
        }

    }

    private void SecuenciaDeSentencias() {
        //SecuenciaDeSentencias:
        //Sentencia { ; Sentencia _action_SecuenciaDeSentencias2 } _action_SecuenciaDeSentencias

        boolean SSError = false;
        int SSLinea = 0;
        int SSColumna = 0;

        //saca de la pila un nodo y comprueba si es una marca o si ya es directamente una Sentencia
        Nodo nodo1 = _pilaNodos.pop(); //Sentencia o Marca
        Nodo nuevo = new Nodo();

        if (nodo1.esMarca()){

            while (nodo1.esMarca()){
                Nodo nodo2 = _pilaNodos.pop(); //sacamos Sentencia
                //comprobamos que está bien tipada
                if (nodo2.getTipoBasico().equals(TipoSemantico.ERROR)) {
                    SSError = true;
                    SSLinea = nodo2.getLinea();
                    SSColumna = nodo2.getColumna();
                }
                nodo1 = _pilaNodos.pop();
            }

            //al salir de while, en nodo1 esta la primera sentencia
            //comprobamos si es VOID y si no ha producido ningún error la secuencia anterior
            if (nodo1.getTipoBasico().equals(TipoSemantico.VOID)&& (!SSError)) {
                nuevo.addTipo(TipoSemantico.VOID);
                _pilaNodos.push(nuevo);
            } else if (nodo1.getTipoBasico().equals(TipoSemantico.ERROR)){
                nuevo.addTipo(TipoSemantico.ERROR);
                _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico("Secuencia de Sentencias NO Válida",
                    nodo1.getLinea(),
                    nodo1.getColumna()));
                _pilaNodos.push(nuevo);
            } else {
                nuevo.addTipo(TipoSemantico.ERROR);
                _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico("Secuencia de Sentencias NO Válida",
                    SSLinea,
                    SSColumna));
                _pilaNodos.push(nuevo);
            }

        } else{ //solo hay una Sentencia
            if (nodo1.getTipoBasico().equals(TipoSemantico.VOID)) {
            nuevo.addTipo(TipoSemantico.VOID);
            _pilaNodos.push(nuevo);
            } else {
                nuevo.addTipo(TipoSemantico.ERROR);
                _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico("Secuencia de Sentencias NO Válida",
                    nodo1.getLinea(),
                    nodo1.getColumna()));
                _pilaNodos.push(nuevo);
            }
        }


    }

    private void SecuenciaDeSentencias2() {
        //SecuenciaDeSentencias:
        //Sentencia { ; Sentencia _action_SecuenciaDeSentencias2 } _action_SecuenciaDeSentencias

        //comprueba que Sentencia está bien tipada y añade un nodo Marca
        Nodo nodo1 = _pilaNodos.pop(); //Sentencia
        Nodo nuevo = new Nodo();

        if (nodo1.getTipoBasico().equals(TipoSemantico.VOID)) {
            nuevo.addTipo(TipoSemantico.VOID);
            _pilaNodos.push(nuevo);
        } else {
            nuevo.addTipo(TipoSemantico.ERROR);
            _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico("Sentencia NO Válida",
                    nodo1.getLinea(),
                    nodo1.getColumna()));
            _pilaNodos.push(nuevo);
        }

         //insertamos el nodo marca en la pila
        Nodo nodoMarca = new Nodo();
        nodoMarca.crearMarca();
        _pilaNodos.push(nodoMarca);

    }

    private void SentenciaAsignacion() {
        //SentenciaAsignacion:
        //Identificador RestoSentenciaAsignacion _action_SentenciaAsignacion

        Nodo nodo1 = _pilaNodos.pop(); //RestoSentenciaAsignacion
        Nodo nodo2 = _pilaNodos.pop(); //Identificador

        Nodo nuevo = new Nodo();
        if (nodo1.getTipoBasico().equals(TipoSemantico.VOID) &&
                (nodo2.getTipoBasico().equals(TipoSemantico.VOID))) {
            nuevo.addTipo(TipoSemantico.VOID);
            _pilaNodos.push(nuevo);
        } else {
            nuevo.addTipo(TipoSemantico.ERROR);
            _pilaNodos.push(nuevo);
            _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico("Sentencia de Asignación mal tipada",
                    nuevo.getLinea(),
                    nuevo.getColumna()));
        }

    }

    private void SentenciaFOR() {
        //SentenciaFOR:
        //FOR Identificador := Expresion _action_IdentificadorFOR TO Expresion _action_ExpresionTO [ BY ExpresionConstante _action_ExpresionConstanteFOR ] DO SecuenciaDeSentencias END _action_SentenciaFOR
        
        /***pruebas***
        Nodo SecuenciaDeSentencias = new Nodo();
        SecuenciaDeSentencias.addTipo(TipoSemantico.VOID);     
        _pilaNodos.push(nodo);
        /***fin pruebas***/

        Nodo nodo1 = _pilaNodos.pop();// sacamos secuencia de sentencias        
        Nodo nodo4 = _pilaNodos.pop(); //hay que ver si es una marca (hay BY) o no
        Nodo nodo2 = new Nodo();

        if (nodo4.esMarca()) {
            // al ser solo un numero, un caracter,...
            nodo2 = _pilaNodos.pop(); // sacamos expresionConstante
        }

        Nodo nodo3 = _pilaNodos.pop(); // sacamos Expresion

        if (nodo4.esMarca()) {
            // miramos si las expresiones son del mismo tipo y que secuencia de sentencias sea de tipo void
            if (nodo2.getTipoBasico().equals(nodo3.getTipoBasico()) && nodo1.getTipoBasico().equals(TipoSemantico.VOID)) {
                Nodo n = new Nodo();
                n.addTipo(TipoSemantico.VOID);
                _pilaNodos.push(n);
            } else {
                Nodo n = new Nodo();
                n.addTipo(TipoSemantico.ERROR);
                _pilaNodos.push(n);
                _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico("Sentencia SentenciaFOR mal tipada",
                        nodo1.getLinea(),
                        nodo1.getColumna()));
            }

        } else {// miramos que la expresion no sea de tipo error y que secuencia de sentencias sea de tipo void
            if (nodo1.getTipoBasico().equals(TipoSemantico.VOID) && !nodo3.getTipoBasico().equals(TipoSemantico.ERROR)) {
                Nodo n = new Nodo();
                n.addTipo(TipoSemantico.VOID);
                _pilaNodos.push(n);
            } else {
                Nodo n = new Nodo();
                n.addTipo(TipoSemantico.ERROR);
                _pilaNodos.push(n);
                _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico("Sentencia SentenciaFOR mal tipada",
                        nodo1.getLinea(),
                        nodo1.getColumna()));
            }
        }

    }

    private void expresionSinParentesisDeMultiplicacion() {
        if(logger.isDebugEnabled()){
            logger.debug("Entrando en el metodo expresionSinParentesisDeMultiplicacion");
        }
        Nodo nodo = _pilaNodos.pop();
        nodo = _pilaNodos.pop();
        nodo = _pilaNodos.pop();
        Nodo newNodo = new Nodo();
        // TODO: Método por hacer
        //newNodo.setLexema("resultadoExpresionParentizada");
        _pilaNodos.push(newNodo);
        if(logger.isDebugEnabled()){
            logger.debug("Salida con exito de expresionSinParentesisDeMultiplicacion");
        }
    }

    private void expresionSinParentesisDeSuma() {
        if(logger.isDebugEnabled()){
            logger.debug("Entrando en el metodo expresionSinParentesisDeSuma");
        }
        Nodo nodoOp = _pilaNodos.pop();
        Nodo nodo = null;
         if(nodoOp.getLexema().equals("SUMA")){
            nodo = _pilaNodos.pop();
         }
        nodo = _pilaNodos.pop();
        nodo = _pilaNodos.pop();
        Nodo newNodo = new Nodo();
        // TODO: Método por hacer
        //newNodo.setValor("resultadoExpresionParentizada");
        _pilaNodos.push(newNodo);
        if(nodoOp.getLexema().equals("SUMA")){
            _pilaNodos.push(nodoOp);
        }
        if(logger.isDebugEnabled()){
            logger.debug("Salida con exito de expresionSinParentesisDeSuma");
        }
    }

    /**
     * el fin de un ambito, cierra este como es debido
     * 
     */
    private void finDeAmbito() {
        //TODO: aki hay un problema con el cierre de los modulos, colisionan aki
        // esta ñapa lo protege por ah
        if (!_pilaNodos.isEmpty()) {
            Nodo id = _pilaNodos.pop();
            if (_tablaActual.getNombre().equals(id.getLexema())) {
                _tablaActual = _tablaActual.cerrarAmbito();
            } else {

                _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico("Simbolo \"" + id.getLexema() + "\" incorrecto, se esperaba \"" + _tablaActual.getNombre() + "\"",
                        id.getLinea(),
                        id.getColumna()));
            }
        }

    }

    
    /**
     * Se completa el tipo del simbolo identificado por el lexema del identificador 
     * a valor Tipo CONSTANTE y el valor de la constante.                
     */
    private void AsociacionConstante() {
        boolean error = false;
        try {

            Nodo valor = _pilaNodos.pop();
            _pilaNodos.pop();
            Nodo id = _pilaNodos.pop();

            if (!valor.esError()) {
                // Insertamos el identificador de la constante en la tabla de simbolos actuales
                if (_tablaActual.declaraSimbolo(id.getLexema())) {
                    // Completamos el tipo semantico y el valor
                    if (!_tablaActual.completaConstate(id.getLexema(), valor.getTipoSemantico(), valor.getLexema())) 
                        error = true;
                } else {
                    error = true;
                }
            } else {
                error = true;
            }
            if (error) {
                _gestorDeErrores.insertaErrorSemantico(
                        new TErrorSemantico("El simbolo \"" + id.getLexema() + "\" ya esta definido.",
                        id.getLinea(),
                        id.getColumna()));
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
                        
                        if (_tablaActual.declaraSimbolo(id.getLexema())){

                            if (!_tablaActual.completaVariable(id.getLexema(), tipo.getTipoSemantico())) 
                                _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico("El simbolo \"" + id.getLexema() + "\" ya esta definido.",
                                        id.getLinea(),
                                        id.getColumna()));
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

        boolean error = false;

        try {

            Nodo tipo = _pilaNodos.pop();
            _pilaNodos.pop(); // operador
            Nodo id = _pilaNodos.pop();

            if (_tablaActual.declaraSimbolo(id.getLexema())){
                if (!_tablaActual.completaTipo(id.getLexema(),tipo.getTipoSemantico()))
                    error = true;
            }
            else
                error = true;

            if (error)
                _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico("El simbolo \"" + id.getLexema() + "\" ya esta definido.",
                                id.getLinea(),
                                id.getColumna()));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * se comineza con un procedimineto
     */
    private void InicioDeclaracionProcedure() {
    // CabeceraSubprograma:
    // PROCEDURE Identificador _action_InicioDeclaraciónProcedure [ ParametrosFormales ]
      
        // sacamos el identificador para el procedimiento
        Nodo nodo = _pilaNodos.peek(); 
        
        // comprobamos unicidad
        if (!_tablaActual.declaraSimbolo(nodo.getLexema()))
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
         
         ArrayList<TipoSemantico> retorno = new ArrayList<TipoSemantico>();
         
         // un array para los nombres de los paramtros
         ArrayList<String> nombres = new ArrayList<String>();
         
         // mientras que no sea el nombre de la tabla
         while (!nodo.getLexema().equals(lexema)){
             
             // si no es error:
             if (nodo.getTipoBasico() == TipoSemantico.ERROR){
                 _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico("error " +nodo.getLexema() +
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
  
            // comprueba la unicidad a nivel de cabecera
            if (nombres.contains(nodo.getLexema())){
                _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico("el parametro \""+nodo.getLexema() +"\"ya esta definido" +
                                                    nodo.getLexema(),
                                                    nodo.getLinea(),
                                                    nodo.getColumna()));
                 return;
             }
            nombres.add(nodo.getLexema());
            
            // declara la variable.
            _tablaActual.declaraSimbolo(nodo.getLexema());
            _tablaActual.completaVariable(nodo.getLexema(), tipoArgumentos.get(tipoArgumentos.size()-1));
 
             nodo = _pilaNodos.pop(); 
             numArgs++;
         }
         
         // cierra el ambito
         TablaDeSimbolos ambitoProc = _tablaActual;         
         _tablaActual = _tablaActual.cerrarAmbito();
         
         // completa el simbolo
         if (!_tablaActual.completaSubprograma(lexema,numArgs,pasoArgumentos,tipoArgumentos,ambitoProc,retorno)){
            _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico("El identificador \"" + lexema + "\" esta siendo redefinido.",
                    nodo.getLinea(),
                    nodo.getColumna()));
         }
         
         // devolvemos el ambito al del procedimineto hasta que este acabe
         _tablaActual = ambitoProc;
    }

    private void RestoSentenciaELSE() {
        //RestoSentenciaIF:
        //{ ELSIF Expresion _action_ExpresionELSIF THEN SecuenciaDeSentencias _action_RestoSentenciaELSIF } [ ELSE SecuenciaDeSentencias _action_RestoSentenciaELSE ] _action_RestoSentenciaIF
        /*pruebas*
        System.out.println("RestoSEntenciasELSE");
        Nodo SecuenciaDeSentencias = new Nodo();
        SecuenciaDeSentencias.addTipo(TipoSemantico.VOID);
        //Metemos esto para probarlo
        _pilaNodos.push(SecuenciaDeSentencias);
        /*FIN pruebas*/

        Nodo nodo1 = _pilaNodos.pop(); //SecuenciaDeSentencias

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

        //insertamos el nodo marca en la pila para saber que hay un ELSE
        Nodo nodoMarca = new Nodo();
        nodoMarca.crearMarca();
        _pilaNodos.push(nodoMarca);

    }

    private void RestoSentenciaELSIF() {
        //RestoSentenciaIF:
        //{ ELSIF Expresion _action_ExpresionELSIF THEN SecuenciaDeSentencias _action_RestoSentenciaELSIF } [ ELSE SecuenciaDeSentencias _action_RestoSentenciaELSE ] _action_RestoSentenciaIF
        /*pruebas
        System.out.println("RestoSentenciaELSIF");
        /*Nodo SecuenciaDeSentencias = new Nodo();
        SecuenciaDeSentencias.addTipo(TipoSemantico.VOID);
        //Metemos esto para probarlo
        _pilaNodos.push(SecuenciaDeSentencias);        
        /*FIN pruebas*/

        Nodo nodo1 = _pilaNodos.pop(); //SecuenciaDeSentencias
        Nodo nodo2 = _pilaNodos.pop(); //Expresion

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

        //insertamos el nodo marca en la pila para saber que hay un ELSIF
        Nodo nodoMarca = new Nodo();
        nodoMarca.crearMarca();
        _pilaNodos.push(nodoMarca);
        
    }

    private void SentenciaIF() {
        //SentenciaIF:
        //IF Expresion _action_ExpresionIF THEN SecuenciaDeSentencias RestoSentenciaIF END _action_SentenciaIF

        /*pruebas*
        System.out.println("SentenciaIF");
        Nodo RestoSentenciaIF = new Nodo();
        RestoSentenciaIF.addTipo(TipoSemantico.VOID);
        Nodo SecuenciaDeSentencias = new Nodo();
        SecuenciaDeSentencias.addTipo(TipoSemantico.VOID);
        //Metemos esto para probarlo
        _pilaNodos.push(RestoSentenciaIF);
        _pilaNodos.push(SecuenciaDeSentencias);    
        /*FIN pruebas*/
        
        Nodo nodo1 = _pilaNodos.pop(); //RestoSentenciaIF
        Nodo nodo2 = _pilaNodos.pop(); //SecuenciaDeSentencias   
        Nodo nodo3 = _pilaNodos.pop(); //Expresion

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
        //Metemos esto para probarlo
        _pilaNodos.push(SecuenciaDeSentencias);
        /*FIN pruebas*/

        Nodo nodo1 = _pilaNodos.pop(); //SecuenciaDeSentencias

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
        Nodo nodo2 = _pilaNodos.pop(); //SecuenciaDeSentencias

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

        Nodo nodo1 = _pilaNodos.pop(); //SecuenciaDeSentencias
        Nodo nodo2 = _pilaNodos.pop(); //Expresion

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

            InfoSimbolo info = (InfoSimbolo) _tablaActual.busca(tipoPredefinido.getLexema());

            if (info.getTipoSimbolo() == TipoSimbolo.TIPO) {

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

    private void operadorUnario() {
        if(logger.isDebugEnabled()){
            logger.debug("Entrando en el metodo OperadorUnario");
        }

        if(!_pilaNodos.peek().esError()){
            Nodo numero = _pilaNodos.pop();
            Nodo signo = _pilaNodos.pop();
            if(signo.getTipoToken() == TipoToken.OPERADOR_SUMADOR){
              /*  if(esEntero(numero) || esReal(numero)){
                    _pilaNodos.push(numero);
                }*/
            }
            else{
                Nodo nodoError = new Nodo();
                ArrayList<TipoSemantico> tipos = new ArrayList();
                tipos.add(TipoSemantico.ERROR);
                _pilaNodos.push(nodoError);
            }
        }
       if(logger.isDebugEnabled()){
            logger.debug("Salida con exito de operadorUnario");
        }
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
