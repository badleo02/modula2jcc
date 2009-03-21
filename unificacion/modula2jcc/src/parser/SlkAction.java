package parser;

import gestor_de_errores.ErroresSemanticos;
import gestor_de_errores.GestorErrores;
import gestor_de_errores.TErrorSemantico;
import scanner.TipoToken;
import tabla_de_simbolos.TablaSimbolos;
import java.util.ArrayList;
import org.apache.log4j.Logger;

/**
 * Clase que implementa las acciones semanticas.
 * 
 * @author Macrogrupo 1
 */
public class SlkAction {

    // CONSTANTES
    private static final Logger logger = Logger.getLogger(SlkAction.class);

    // ATRIBUTOS
    private TablaSimbolos _tablaSimbolos;
    private GestorErrores _gestorDeErrores;
    private PilaNodos _pilaNodos;

    /**
     * Constructor de la clase SlkAction.
     * 
     * @param tabla Tabla de simbolos del compilador.
     */
    public SlkAction(TablaSimbolos tabla, GestorErrores gestorDeErrores, PilaNodos pilaNodos) {

        _tablaSimbolos = tabla;
        _gestorDeErrores = gestorDeErrores;
        _pilaNodos = pilaNodos;
    }

    /**
     * Metodo invocado por Slk consistente en la bifurcacion hacia el procedimiento
     * que trata la accion semantica correspondiente.
     * 
     * @param number La accion semantica correspondiente.
     */
    public void execute(int number) {

        switch (number) {
            case 1:
                unidadDeCompilacion1();
                break;
            case 2:
                unidadDeCompilacion2();
                break;
            case 3:
                unidadDeCompilacion3();
                break;
            case 4:
                moduloPrograma();
                break;
            case 5:
                activarDeclarativa();
                break;
            case 6:
                cabeceraModulo();
                break;
            case 7:
                CierraAmbito();
                break;
            case 8:
                Declaracion();
                break;
            case 9:
                tipoSimple();
                break;
            case 10:
                tipoSimple2();
                break;
            case 11:
                subrango();
                break;
            case 12:
                listaIdentificadores();
                break;
            case 13:
                vacio();
                break;
            case 14:
                variable();
                break;
            case 15:
                cierravariable();
                break;
            case 16:
                Marca();
                break;
            case 17:
                listaVariables();
                break;
            case 18:
                Begin();
                break;
            case 19:
                SentenciaAsignacion();
                break;
            case 20:
                Identificador();
                break;
            case 21:
                RestoSentenciaAsignacion();
                break;
            case 22:
                RestoSentenciaRestoAsignacion();
                break;
            case 23:
                Expresion();
                break;
            case 24:
                expresionIf();
                break;
            case 25:
                expresionElsIf();
                break;
            case 26:
                SentenciaWHILE();
                break;
            case 27:
                SentenciaREPEAT();
                break;
            case 28:
                SentenciaLOOP();
                break;
            case 29:
                exprFor();
                break;
            case 30:
                exprTo();
                break;
            case 31:
                SentenciaFOR();
                break;
            case 32:
                SentenciaWITH();
                break;
            case 33:
                SentenciaRETURN();
                break;
            case 34:
                ListaDeValores();
                break;
            case 35:
                RestoValores();
                break;
            case 36:
                RestoExpresion();
                break;
            case 37:
                ExpresionSimple();
                break;
            case 38:
                RestoExpresionSimple();
                break;
            case 39:
                termino();
                break;
            case 40:
                unitSuma();
                break;
            case 41:
                unitResta();
                break;
            case 42:
                factor();
                break;
            case 43:
                numero();
                break;
            case 44:
                igual();
                break;
            case 45:
                distinto();
                break;
            case 46:
                distinto2();
                break;
            case 47:
                menor();
                break;
            case 48:
                menorIgual();
                break;
            case 49:
                mayor();
                break;
            case 50:
                mayorIgual();
                break;
            case 51:
                operadorIn();
                break;
            case 52:
                suma();
                break;
            case 53:
                resta();
                break;
            case 54:
                operadorOr();
                break;
            case 55:
                mult();
                break;
            case 56:
                division();
                break;
            case 57:
                divisionEntera();
                break;
            case 58:
                operadorModulo();
                break;
            case 59:
                ampersand();
                break;
            case 60:
                operadorAnd();
                break;
            case 61:
                operadorNot();
                break;
            case 62:
                operadorComplemento();
                break;
            case 63:
                numEntero();
                break;
            case 64:
                numReal();
                break;
            case 65:
                tipoPredefinido();
                break;
            case 66:
                bitset();
                break;
            case 67:
                booleano();
                break;
            case 68:
                cardinal();
                break;
            case 69:
                caracter();
                break;
            case 70:
                entero();
                break;
            case 71:
                longint();
                break;
            case 72:
                longreal();
                break;
            case 73:
                proc();
                break;
            case 74:
                real();
                break;
            case 75:
                verdadero();
                break;
            case 76:
                falso();
                break;
            case 77:
                nil();
                break;
            case 78:
                identificador();
                break;
        }
    }

    private void unidadDeCompilacion1() {
        if (logger.isDebugEnabled()) {
            logger.debug("Entrando en unidadDeCompilacion1");
        }
    }

    private void unidadDeCompilacion2() {
        if (logger.isDebugEnabled()) {
            logger.debug("Entrando en moduloDefinicion");
        }
    }

    private void unidadDeCompilacion3() {
        if (logger.isDebugEnabled()) {
            logger.debug("Entrando en unidadDeCompilacion3");
        }
    }

    /**
     * Comprueba que el identificador del modulo y el de despues del END del
     * modulo concuerden. 
     * En caso de concordar se cerrara ese ambito en la tabla de simbolos.
     */
    private void moduloPrograma() {
        
       Nodo nodo = _pilaNodos.pop(); // Guardamos el identificador
        
        // Si el identificador del END del modulo se llama igual que el nombre del Modulo 
        if (_tablaSimbolos.getNombre().equals(nodo.getValor()))            
            _tablaSimbolos.cerrarAmbitoModulo();
        else {
        
            _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico(
                                ErroresSemanticos.ERROR_SEMANTICO_MODULO_NO_DECLARADO,
                                "Identificador de Modulo incorrecto, se esperaba \"" + _tablaSimbolos.getNombre() + "\" pero se encontro \"" + nodo.getValor() + "\"",
                                nodo.getLinea(),
                                nodo.getColumna()));            
            insertarNodoError();
        }
    }
    
    /**
     * Activa la parte declarativa del modulo actual.
     */
    private void activarDeclarativa() {
        
        _pilaNodos.activarParteDeclarativa();
    }
    
    /**
     * Comprueba que el nombre del modulo que se estaContenida declarando no esté en 
     * la tabla de símbolos en un ambito superior. Si estaContenida se genera el error
     * correspondiente y si no estaContenida se actualiza.
     */
    private void cabeceraModulo() {
        
        Nodo nodo = _pilaNodos.pop(); // Obtenemos el identificador del nombre del modulo
        
        // Si estamos en el modulo principal
        if (_tablaSimbolos.esModuloPrincipal())
            
            // Le asignamos el nombre (Ya la hemos creado en el Main)
            _tablaSimbolos.setNombre(nodo.getValor()); 
        else {
            
            // Si es un nuevo modulo lo insertamos 
            if (!_tablaSimbolos.estaContenida(nodo.getValor()))
                _tablaSimbolos.insertarModulo(nodo.getValor());
            else {
                
                _gestorDeErrores.insertaErrorSemantico(
                        new TErrorSemantico(
                                ErroresSemanticos.ERROR_SEMANTICO_MODULO_YA_DECLARADO,
                                "El modulo " + nodo.getValor() + "ya esta declarado",
                                nodo.getLinea(),
                                nodo.getColumna()));

                insertarNodoError();                
            }
        }
    }
    
    /**
     * Cierra el modo declaracion.
     */
    private void Begin() {
        
        _pilaNodos.desactivarParteDeclarativa();
    }

    private void CierraAmbito() {
        if (logger.isDebugEnabled()) {
            logger.debug("Entrando en cierra Ambito. ");
        }
    }

    private void Declaracion() {

        if (logger.isDebugEnabled()) {
            logger.debug("Entrando en declaracion");
        }
        Nodo nodo = new Nodo();
        nodo.setValor("#");
        _pilaNodos.push(nodo);
    }

    private void Expresion() {
        if (logger.isDebugEnabled()) {
            logger.debug("Entrando en Expresion");
        }
    }

    private void ExpresionSimple() {
        if (logger.isDebugEnabled()) {
            logger.debug("Entrando en ExpresionSimple");
        }
    }

    private void Identificador() {
        if (logger.isDebugEnabled()) {
            logger.debug("Entrando en identificador; \n Suponiendo que esta en la tabla de simbolos");
        }
    }

    private void ListaDeValores() {
        if (logger.isDebugEnabled()) {
            logger.debug("Entrando en listaDeValores");
        }
    }

    private void Marca() {
        if (logger.isDebugEnabled()) {
            logger.debug("Entrando en Marca. Introduciendo marca en la pila para saber el numero de variables que hay");
        }
        Nodo nodo = new Nodo();
        nodo.setValor("#");
        _pilaNodos.push(nodo);
    }

    private void OperadorSumador() {
        if (logger.isDebugEnabled()) {
            logger.debug("Entrando en operadorSumador");
        }
    }

    private void RestoExpresion() {
        if (logger.isDebugEnabled()) {
            logger.debug("Entrando en restoExpresion");
        }
    }

    private void RestoExpresionSimple() {
        if (logger.isDebugEnabled()) {
            logger.debug("Entrando en restoExpresionSimple");
        }

    }

    private void RestoSentenciaAsignacion() {
        if (logger.isDebugEnabled()) {
            logger.debug("Entrando en restoSentenciaAsignacion");
        }
    }

    private void RestoSentenciaRestoAsignacion() {
        if (logger.isDebugEnabled()) {
            logger.debug("Entrando en restoSentenciRestoaAsignacion");
        }
    }

    private void RestoValores() {
        if (logger.isDebugEnabled()) {
            logger.debug("Entrando en RestoValores");
        }
    }

    private void SentenciaAsignacion() {
        
        if (logger.isDebugEnabled()) {
            logger.debug("Entrando en sentencia asignacion");
        }

        Nodo type = _pilaNodos.pop();

        if (!type.getValor().equals(PLGConstants.ERROR)) {

            Nodo var = _pilaNodos.pop();

            if (!var.getValor().equals(PLGConstants.ERROR)) {
                if (_tablaSimbolos.estaContenida(var.getValor())) {
                    //COMPROBAR CON LOS OTROS TIPOS QUE HAY
                    //comprobamos si es identificador
                    if (((TipoToken) type.getTipos().get(0)).compareTo(TipoToken.valueOf("IDENTIFICADOR")) == 0) {
                        if (_tablaSimbolos.estaContenida(type.getValor())) {
                            if (!_tablaSimbolos.sonConpatiblesIdentificadores(var.getValor(), type.getValor())) {

                                _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico(
                                ErroresSemanticos.ERROR_SEMANTICO_TIPOS_NO_COMPATIBLES,
                                "Tipos no compatibles",
                                var.getLinea(),
                                var.getColumna()));
                                //logger.error("Tipos no compatibles");
                            }
                        } else {

                            _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico(
                                ErroresSemanticos.ERROR_SEMANTICO_VARIABLE_NO_DECLARADA,
                                "variable " + type.getValor() + " no declarada",
                                type.getLinea(),
                                type.getColumna()));
                            //logger.error("variable " + type.getValor() + " no declarada");
                        }
                    } else {
                        if (((TipoToken) type.getTipos().get(0)).compareTo(TipoToken.valueOf("NUMERO_ENTERO")) == 0) {
                            if (!_tablaSimbolos.sonConpatibles(var.getValor(), ((TipoToken) type.getTipos().get(0)))) {
                                
                                _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico(
                                ErroresSemanticos.ERROR_SEMANTICO_TIPOS_NO_COMPATIBLES,
                                "Tipos no compatibles",
                                type.getLinea(),
                                type.getColumna()));
                                //logger.error("Tipos no compatibles");
                            }
                        } else if (((TipoToken) type.getTipos().get(0)).compareTo(TipoToken.valueOf("NUMERO_REAL")) == 0) {
                            if (!_tablaSimbolos.sonConpatibles(var.getValor(), ((TipoToken) type.getTipos().get(0)))) {

                                _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico(
                                ErroresSemanticos.ERROR_SEMANTICO_TIPOS_NO_COMPATIBLES,
                                "Tipos no compatibles",
                                type.getLinea(),
                                type.getColumna()));
                                //logger.error("Tipos no compatibles");
                            }
                        } else if (((TipoToken) type.getTipos().get(0)).compareTo(TipoToken.valueOf("PALABRA_RESERVADA")) == 0) {
                            if (!_tablaSimbolos.sonConpatibles(var.getValor(), ((TipoToken) type.getTipos().get(0)))) {

                                _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico(
                                ErroresSemanticos.ERROR_SEMANTICO_TIPOS_NO_COMPATIBLES,
                                "Tipos no compatibles",
                                type.getLinea(),
                                type.getColumna()));
                                //logger.error("Tipos no compatibles");
                            }
                        } else {

                            _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico(
                                ErroresSemanticos.ERROR_SEMANTICO_TIPO_INDEFINIDO,
                                "Tipo indefinido",
                                type.getLinea(),
                                type.getColumna()));
                            //logger.error("Tipo indefinido");
                        }
                    }
                } else {

                    _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico(
                                ErroresSemanticos.ERROR_SEMANTICO_VARIABLE_NO_DECLARADA,
                                "variable " + var.getValor() + " no declarada",
                                var.getLinea(),
                                var.getColumna()));
                    //logger.error("variable " + var.getValor() + " no declarada");
                }

            } else {
                insertarNodoError();
                logger.error("Introduciendo el tipo ERROR en sentencia asignacion");
            }
        } else {
            insertarNodoError();
            logger.error("Introduciendo el tipo ERROR en sentencia asignacion");
        }
    }

    private void SentenciaFOR() {
        if (logger.isDebugEnabled()) {
            logger.debug("Entrando en SentenciaFor");
        }
    }

    private void SentenciaLOOP() {
        if (logger.isDebugEnabled()) {
            logger.debug("Entrando en SentenciaLOOP");
        }
    }

    private void SentenciaREPEAT() {
        if (logger.isDebugEnabled()) {
            logger.debug("Entrando en SentenciaRepeat");
        }
    }

    private void SentenciaRETURN() {
        if (logger.isDebugEnabled()) {
            logger.debug("Entrando en SentenciaRETURN");
        }
    }

    private void SentenciaWHILE() {

        //Creo el nodo error previamente
        Nodo error = new Nodo();
        error.setTipo(null);
        error.setValor(PLGConstants.ERROR);

        System.out.println("---SentenciaWHILE---");
        if (logger.isDebugEnabled()) {
            logger.debug("Entrando en SentenciaWHILE");
        }
        
        Nodo nodo = _pilaNodos.pop();
        
        //Miramos si el nodo que acabamos de sacar de la pila es un error
        if (nodo.getValor().equals(PLGConstants.ERROR) == false) {
            //Comprobamos si el tipo del nodo es una palabra reservada
            if (((TipoToken) nodo.getTipos().get(0)).compareTo(TipoToken.valueOf("PALABRA_RESERVADA")) == 0) {
                //Comprobado en las acciones semanticas de expresiones
                logger.debug("Tipo booleano encontrado correctamente1");
            } else if (((TipoToken) nodo.getTipos().get(0)).compareTo(TipoToken.valueOf("IDENTIFICADOR")) == 0) {
                if (_tablaSimbolos.estaContenida(nodo.getValor())) {

                    TipoToken aux = (TipoToken) _tablaSimbolos.dameTipoIdentificador(nodo.getValor()).get(0);

                    if (aux.compareTo(TipoToken.valueOf("PALABRA_RESERVADA")) == 0) {
                        // El identificador es booleano
                        logger.debug("Tipo booleano encontrado correctamente2");
                    } 
                    else {

                        _pilaNodos.push(error);
                        //logger.error("Identificador no booleano");

                        _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico(
                                ErroresSemanticos.ERROR_SEMANTICO_TIPO_NO_BOOLEANO,
                                "Tipo no booleano",
                                nodo.getLinea(),
                                nodo.getColumna()));
                    }
                } else {
                    _pilaNodos.push(error);
                    _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico(
                                ErroresSemanticos.ERROR_SEMANTICO_VARIABLE_NO_DECLARADA,
                                "Variable no declarada",
                                nodo.getLinea(),
                                nodo.getColumna()));
                    //logger.error("Variable no declarada");
                }
            } else {
                _pilaNodos.push(error);
                _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico(
                                ErroresSemanticos.ERROR_SEMANTICO_TIPO_NO_COMPATIBLE,
                                "Tipo no compatible",
                                nodo.getLinea(),
                                nodo.getColumna()));
               // logger.error("Tipo no compatible");
            }
        } else {//Como el nodo sacado era un error, propagamos el error
            _pilaNodos.push(error);
            logger.error("Se encontro un nodo error en la pila. Se propagara el error.");
        }
    }

    private void SentenciaWITH() {
        System.out.println("---SentenciaWITH---");
    }

    private void Signo() {
        if (logger.isDebugEnabled()) {
            logger.debug("Entrando en signo");
        }
    }



    private void ampersand() {
        if (logger.isDebugEnabled()) {
            logger.debug("Entrando en ampersand. Redirigiendo a operadorAnd()");
        }
        operadorAnd();
    }

    private void bitset() {
        if (logger.isDebugEnabled()) {
            logger.debug("Entrando en bitset");
        }
    // Modificar tabla de símbolos
    }

    private void booleano() {
        if (logger.isDebugEnabled()) {
            logger.debug("Entrando en booleano");
        }
    // Modificar tabla de símbolos
    }

    private void caracter() {
        if (logger.isDebugEnabled()) {
            logger.debug("Entrando en caracter");
        }
    // Modificar tabla de símbolos
    }

    private void cardinal() {
        if (logger.isDebugEnabled()) {
            logger.debug("Entrando en cardinal");
        }
    // Modificar tabla de símbolos
    }

    private void cierravariable() {
        if (logger.isDebugEnabled()) {
            logger.debug("Entrando en cierravariable");
        }
        _pilaNodos.pop();
    }

    private void distinto() {
        if (logger.isDebugEnabled()) {
            logger.debug("Entrando en distinto");
        }
    }

    private void distinto2() {
        if (logger.isDebugEnabled()) {
            logger.debug("Entrando en distinto2");
        }
    }

    private void division() {
        if (logger.isDebugEnabled()) {
            logger.debug("Entrando en division");
        }
    }

    private void exprFor() {
        if (logger.isDebugEnabled()) {
            logger.debug("Entrando en for");
        }
        Nodo nodo1 = _pilaNodos.pop();
        //_pilaNodos.pop();
        Nodo nodo2 = _pilaNodos.pop();
        if (((TipoToken) nodo1.getTipos().get(0)).compareTo(TipoToken.valueOf("NUMERO_ENTERO")) == 0) {
            if (_tablaSimbolos.estaContenida(nodo2.getValor())) {
                TipoToken aux = (TipoToken) _tablaSimbolos.dameTipoIdentificador(nodo2.getValor()).get(0);

                if (aux.compareTo(TipoToken.valueOf("NUMERO_ENTERO")) == 0) {                    // El identificador es booleano
                } else {
                    // Identificador no booleano
                    insertarNodoError();
                    logger.error("No entero en FOR");
                }
            } else {
                
                insertarNodoError();
                logger.error("La expresión no es entera");
            }

        } else if (((TipoToken) nodo1.getTipos().get(0)).compareTo(TipoToken.valueOf("IDENTIFICADOR")) == 0) {
            if (_tablaSimbolos.estaContenida(nodo2.getValor())) {
                ArrayList tiposVar = _tablaSimbolos.dameTipoIdentificador(nodo2.getValor());
                TipoToken tipoVar = (TipoToken) tiposVar.get(0);
                if (tipoVar.compareTo(TipoToken.valueOf("NUMERO_ENTERO")) != 0) {

                    _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico(
                                ErroresSemanticos.ERROR_SEMANTICO_TIPO_NO_COMPATIBLE,
                                "El tipo de la variable para el limite inferior del for no es del tipo esperado",
                                nodo2.getLinea(),
                                nodo2.getColumna()));
                    //logger.error("El tipo de la variable para el limite inferior del for no es del tipo esperado");
                    insertarNodoError();
                    logger.error("La expresión no es entera");
                }
            } else {

                _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico(
                                ErroresSemanticos.ERROR_SEMANTICO_VARIABLE_NO_DECLARADA,
                                "La variable " + nodo2.getValor() + " no ha sido declarada",
                                nodo2.getLinea(),
                                nodo2.getColumna()));
                logger.error("La variable " + nodo2.getValor() + " no ha sido declarada");
                insertarNodoError();
                logger.error("La expresión no es entera");
            }
        } else {
            _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico(
                                ErroresSemanticos.ERROR_SEMANTICO_TIPO_NO_COMPATIBLE,
                                "El tipo de la variable para el limite inferior del for no es del tipo esperado",
                                nodo2.getLinea(),
                                nodo2.getColumna()));
            //logger.error("El tipo de la variable para el limite inferior del for no es del tipo esperado");
            insertarNodoError();
            logger.error("La expresión no es entera");
        }
    }

    private void exprTo() {
        if (logger.isDebugEnabled()) {
            logger.debug("Entrando en expresionTO");
        }
        Nodo nodo = _pilaNodos.pop();
        if (((TipoToken) nodo.getTipos().get(0)).compareTo(TipoToken.valueOf("NUMERO_ENTERO")) == 0) {
            //El límite del FOR es entero
        } else if (((TipoToken) nodo.getTipos().get(0)).compareTo(TipoToken.valueOf("IDENTIFICADOR")) == 0) {
            ArrayList tipos = _tablaSimbolos.dameTipoIdentificador(nodo.getValor());
            TipoToken tipoVar = (TipoToken) tipos.get(0);
            if (tipoVar.compareTo(TipoToken.valueOf("NUMERO_ENTERO")) != 0) {

                _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico(
                                ErroresSemanticos.ERROR_SEMANTICO_TIPO_NO_COMPATIBLE,
                                "Tipo no compatible como limite del for",
                                nodo.getLinea(),
                                nodo.getColumna()));
                //logger.error("Tipo no compatible como limite del for");
                
                insertarNodoError();
                logger.error("La expresión no es entera");
            }

        } else {

            insertarNodoError();
            logger.error("El límite del FOR no es entero");
        }
    }

    private void expresionElsIf() {
        if (logger.isDebugEnabled()) {
            logger.debug("Entrando en expresionElsif");
        }
        // TODO: Generación de código intermedio
        expresionIf();
    }

    private void expresionIf() {

        if (logger.isDebugEnabled()) {
            logger.debug("Entrando en expresionIf");
        }

        // Caso 1: Identificador
        Nodo nodo = _pilaNodos.pop();

        if (!nodo.getValor().equals(PLGConstants.ERROR)) {
            if (((TipoToken) nodo.getTipos().get(0)).compareTo(TipoToken.valueOf("IDENTIFICADOR")) == 0) {
                if (_tablaSimbolos.estaContenida(nodo.getValor())) {
                    TipoToken aux = (TipoToken) _tablaSimbolos.dameTipoIdentificador(nodo.getValor()).get(0);

                    if (aux.compareTo(TipoToken.valueOf("PALABRA_RESERVADA")) == 0) {
                        // El identificador es booleano
                        logger.debug("Tipo booleano encontrado correctamente");
                    } else {

                        // Identificador no booleano
                        insertarNodoError();
                        
                        // logger.error("Identificador no booleano");
                        _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico(
                                ErroresSemanticos.ERROR_SEMANTICO_TIPO_NO_BOOLEANO,
                                "Tipo no booleano",
                                nodo.getLinea(),
                                nodo.getColumna()));
                    }
                } else {
                    
                    insertarNodoError();

                    _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico(
                                ErroresSemanticos.ERROR_SEMANTICO_VARIABLE_NO_DECLARADA,
                                "Variable no declarada",
                                nodo.getLinea(),
                                nodo.getColumna()));

                    //logger.error("Variable no declarada");
                }

            } else if (((TipoToken) nodo.getTipos().get(0)).compareTo(TipoToken.valueOf("PALABRA_RESERVADA")) == 0) {
                {
                    //se ha quitado la comprobacion con la tabla de simbolso que no hacia falta
                    //fya que sabemos que es un booleano
                    logger.debug("Tipo booleano encontrado correctamente");
                }
            } else {
                
                insertarNodoError();
                logger.error("Tipo no compatible");
            }
        } else {
            
            insertarNodoError();
            logger.error("Se encontro un nodo error en la pila. Se propagara el error.");
        }
    }

    private void insertarNodoError() {
        Nodo error = new Nodo();
        error.setTipo(null);
        error.setValor(PLGConstants.ERROR);
        _pilaNodos.push(error);
    }

    

    private void sonComparables(Nodo nodo1, Nodo nodo2) {
        TipoToken tipo1 = (TipoToken) nodo1.getTipos().get(0);
        TipoToken tipo2 = (TipoToken) nodo2.getTipos().get(0);

        if (tipo1.compareTo(TipoToken.valueOf("IDENTIFICADOR")) == 0) {
            if (_tablaSimbolos.estaContenida(nodo1.getValor())) {
                ArrayList tiposVar = _tablaSimbolos.dameTipoIdentificador(nodo1.getValor());
                TipoToken tipoVar = (TipoToken) tiposVar.get(0);
                if (tipoVar.compareTo(tipo2) == 0) {
                    Nodo resultado = new Nodo();
                    resultado.concatRight(TipoToken.valueOf("PALABRA_RESERVADA"));
                    resultado.setValor("");
                    _pilaNodos.push(resultado);
                } else {

                    _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico(
                                ErroresSemanticos.ERROR_SEMANTICO_TIPOS_NO_COMPATIBLES,
                                "No se pueden comparar el tipo " + tipoVar + " con el tipo " + tipo2,
                                nodo2.getLinea(),
                                nodo2.getColumna()));

                    //logger.error("No se pueden comparar el tipo " + tipoVar + " con el tipo " + tipo2);
                    insertarNodoError();
                }
            } else {
                
                _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico(
                                ErroresSemanticos.ERROR_SEMANTICO_VARIABLE_NO_DECLARADA,
                                "La variable " + nodo1.getValor() + " no esta declarada",
                                nodo1.getLinea(),
                                nodo1.getColumna()));
                //logger.error("La variable " + nodo1.getValor() + " no estaContenida declarada");
                insertarNodoError();
            }

        } else if (tipo2.compareTo(TipoToken.valueOf("IDENTIFICADOR")) == 0) {
            if (_tablaSimbolos.estaContenida(nodo2.getValor())) {
                ArrayList tiposVar = _tablaSimbolos.dameTipoIdentificador(nodo2.getValor());
                TipoToken tipoVar = (TipoToken) tiposVar.get(0);
                if (tipoVar.compareTo(tipo1) == 0) {

                    Nodo resultado = new Nodo();
                    resultado.concatRight(TipoToken.valueOf("PALABRA_RESERVADA"));
                    resultado.setValor("");
                    _pilaNodos.push(resultado);
                }
                else {

                    _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico(
                                ErroresSemanticos.ERROR_SEMANTICO_TIPOS_NO_COMPATIBLES,
                                "No se pueden comparar el tipo " + tipoVar + " con el tipo " + tipo1,
                                nodo1.getLinea(),
                                nodo1.getColumna()));

                    //logger.error("No se pueden comparar el tipo " + tipoVar + " con el tipo " + tipo1);
                    insertarNodoError();
                }
            }
            else {

                _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico(
                                ErroresSemanticos.ERROR_SEMANTICO_VARIABLE_NO_DECLARADA,
                                "La variable " + nodo2.getValor() + " no esta declarada",
                                nodo2.getLinea(),
                                nodo2.getColumna()));

                //logger.error("La variable " + nodo2.getValor() + " no estaContenida declarada");
                insertarNodoError();
            }
        }
        else {

            _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico(
                                ErroresSemanticos.ERROR_SEMANTICO_TIPO_INESPERADO,
                                "Tipos no permitidos.",
                                nodo2.getLinea(),
                                nodo2.getColumna()));
            //logger.error("Tipos no permitidos.");
            insertarNodoError();
        }
    }

    /**
     * Comprueba si los dos tipos son enteros
     * @param tipo1 ArrayList con el tipo
     * @param tipo2 ArrayList con el tipo
     * @return true si los dos tipos son enteros, false si alguno no lo es
     */
    private boolean sonEnteros(ArrayList tipo1, ArrayList tipo2) {
        if (logger.isDebugEnabled()) {
            logger.debug("Comprobando si los tipos " + tipo1 + " o " + tipo2 + " son enteros");
        }
        boolean enteros = true;
        for (int i = 0; i < tipo1.size(); i++) {
            TipoToken tipoToken1 = (TipoToken) tipo1.get(i);
            TipoToken tipoToken2 = (TipoToken) tipo2.get(i);
            if (tipoToken1.compareTo(TipoToken.valueOf("NUMERO_ENTERO")) != 0) {
                enteros = true;
            }
            if (tipoToken2.compareTo(TipoToken.valueOf("NUMERO_ENTERO")) != 0) {
                enteros = true;
            }
        }
        return enteros;
    }

    /**
     * Comprueba si los dos tipos son reales
     * @param tipo1 ArrayList con el tipo
     * @param tipo2 ArrayList con el tipo
     * @return true si los dos tipos son reales, false si alguno no lo es
     */
    private boolean sonReales(ArrayList tipo1, ArrayList tipo2) {
        if (logger.isDebugEnabled()) {
            logger.debug("Comprobando si los tipos " + tipo1 + " o " + tipo2 + " son reales");
        }
        boolean enteros = true;
        for (int i = 0; i < tipo1.size(); i++) {
            TipoToken tipoToken1 = (TipoToken) tipo1.get(i);
            TipoToken tipoToken2 = (TipoToken) tipo2.get(i);
            if (tipoToken1.compareTo(TipoToken.valueOf("NUMERO_REAL")) != 0) {
                enteros = true;
            }
            if (tipoToken2.compareTo(TipoToken.valueOf("NUMERO_REAL")) != 0) {
                enteros = true;
            }
        }
        return enteros;
    }

    private void divisionEntera() {
        if (logger.isDebugEnabled()) {
            logger.debug("Entrando en divisionEntera");
        }
        Nodo primerNodo = _pilaNodos.pop();
        if (!(primerNodo.getValor().equals(PLGConstants.ERROR))) {
            Nodo segundoNodo = _pilaNodos.pop();
            if (!(segundoNodo.getValor().equals(PLGConstants.ERROR))) {
                ArrayList arrayTipo1 = primerNodo.getTipos();
                ArrayList arrayTipo2 = segundoNodo.getTipos();
                TipoToken tipo1 = (TipoToken) arrayTipo1.get(0);
                TipoToken tipo2 = (TipoToken) arrayTipo2.get(0);

                if (tipo1.compareTo(TipoToken.valueOf("IDENTIFICADOR")) == 0) {
                    if (tipo2.compareTo(TipoToken.valueOf("IDENTIFICADOR")) == 0) {
                        if (_tablaSimbolos.sonEnteros(primerNodo.getValor(), segundoNodo.getValor())) {
                            Nodo resultado = new Nodo();
                            ArrayList tiposResultado = _tablaSimbolos.dameTipoIdentificador(primerNodo.getValor());
                            resultado.setTipo(tiposResultado);
                            resultado.setValor("");
                            _pilaNodos.push(resultado);

                        } else {
                            insertarNodoError();
                            logger.error("Introducido nodo ERROR. Tipos no permitidos");
                        }
                    } else {
                        if (_tablaSimbolos.sonEnteros((TipoToken) segundoNodo.getTipos().get(0), primerNodo.getValor())) {
                            Nodo resultado = new Nodo();
                            resultado.concatRight(tipo2);
                            resultado.setValor(segundoNodo.getValor());
                            _pilaNodos.push(resultado);
                        } else {
                            insertarNodoError();
                            logger.error("Introducido nodo ERROR. Tipos no permitidos");
                        }
                    }

                } else {
                    if (tipo2.compareTo(TipoToken.valueOf("IDENTIFICADOR")) == 0) {
                        if (_tablaSimbolos.sonEnteros((TipoToken) primerNodo.getTipos().get(0), segundoNodo.getValor())) {
                            Nodo resultado = new Nodo();
                            resultado.concatRight(tipo1);
                            resultado.setValor(primerNodo.getValor());
                            _pilaNodos.push(resultado);
                        } else {
                            insertarNodoError();
                            _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico(
                                ErroresSemanticos.ERROR_SEMANTICO_ENTEROS_NO_PERMITIDOS,
                                "Tipos enteros no permitidos.",
                                primerNodo.getLinea(),
                                primerNodo.getColumna()));
                            logger.error("Introducido nodo ERROR. Tipos enteros no permitidos");
                        }
                    } else {
                        if (sonEnteros(primerNodo.getTipos(), segundoNodo.getTipos())) {
                            Nodo resultado = new Nodo();
                            resultado.setTipo(primerNodo.getTipos());
                            resultado.setValor(primerNodo.getValor());
                            _pilaNodos.push(resultado);
                        } else {
                            insertarNodoError();
                            _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico(
                                ErroresSemanticos.ERROR_SEMANTICO_BOOLEANOS_NO_PERMITIDOS,
                                "Tipos booleanos no permitidos.",
                                primerNodo.getLinea(),
                                primerNodo.getColumna()));
                            logger.error("Introducido nodo ERROR. Tipos booleanos no permitidos");
                        }
                    }
                }

            } else {
                insertarNodoError();
                logger.error("Introducido nodo ERROR");
            }
        } else {
            insertarNodoError();
            logger.error("Introducido nodo ERROR");
        }

    }

    private void entero() {
        if (logger.isDebugEnabled()) {
            logger.debug("Entrando en entero");
        }
    // Modificar tabla de símbolos

    }

    private void factor() {
        if (logger.isDebugEnabled()) {
            logger.debug("Entrando en factor");
        }
    }

    private void falso() {
        if (logger.isDebugEnabled()) {
            logger.debug("Entrando en falso");
        }
    }

    private void identificador() {
        if (logger.isDebugEnabled()) {
            logger.debug("Entrando en identificador");
        }

    }

    private void igual() {

        if (logger.isDebugEnabled()) {
            logger.debug("Entrando en igual");
        }
        Nodo primerNodo = _pilaNodos.pop();
        if (!(primerNodo.getValor().equals(PLGConstants.ERROR))) {
            Nodo segundoNodo = _pilaNodos.pop();
            if (!(segundoNodo.getValor().equals(PLGConstants.ERROR))) {
                ArrayList arrayTipo1 = primerNodo.getTipos();
                ArrayList arrayTipo2 = segundoNodo.getTipos();
                TipoToken tipo1 = (TipoToken) arrayTipo1.get(0);
                TipoToken tipo2 = (TipoToken) arrayTipo2.get(0);
                if (tipo1.compareTo(tipo2) == 0) {
                    if (tipo1.compareTo(TipoToken.valueOf("IDENTIFICADOR")) == 0) {
                        if (tipo2.compareTo(TipoToken.valueOf("IDENTIFICADOR")) == 0) {
                            if (_tablaSimbolos.sonBooleanos(primerNodo.getValor(), segundoNodo.getValor())) {

                                Nodo resultado = new Nodo();
                                ArrayList tiposResultado = _tablaSimbolos.dameTipoIdentificador(primerNodo.getValor());
                                //un concatright es la funcion que utilizo para meter el tipo simple
                                resultado.concatRight(TipoToken.valueOf("PALABRA_RESERVADA"));
                                resultado.setValor("");
                                _pilaNodos.push(resultado);

                            } else if (_tablaSimbolos.sonEnteros(primerNodo.getValor(), segundoNodo.getValor())) {
                                Nodo resultado = new Nodo();
                                resultado.concatRight(TipoToken.valueOf("PALABRA_RESERVADA"));
                                resultado.setValor("");
                                _pilaNodos.push(resultado);
                            }///FALTA COMPROBAR TODO Y REALES
                            else if (_tablaSimbolos.sonReales(primerNodo.getValor(), segundoNodo.getValor())) {
                                Nodo resultado = new Nodo();
                                resultado.concatRight(TipoToken.valueOf("PALABRA_RESERVADA"));
                                resultado.setValor("");
                                _pilaNodos.push(resultado);
                            } else {
                                insertarNodoError();
                                _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico(
                                ErroresSemanticos.ERROR_SEMANTICO_BOOLEANOS_NO_PERMITIDOS,
                                "Tipos booleanos no permitidos.",
                                primerNodo.getLinea(),
                                primerNodo.getColumna()));
                                logger.error("Introducido nodo ERROR");
                            }
                        } else {
                            if (!_tablaSimbolos.sonBooleanos((TipoToken) segundoNodo.getTipos().get(0), primerNodo.getValor())) {

                                Nodo resultado = new Nodo();
                                resultado.concatRight(tipo2);
                                resultado.setValor(segundoNodo.getValor());
                                _pilaNodos.push(resultado);

                            } //falta identificado con tipo directo
                            else {
                                insertarNodoError();
                                _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico(
                                ErroresSemanticos.ERROR_SEMANTICO_BOOLEANOS_NO_PERMITIDOS,
                                "Tipos booleanos no permitidos.",
                                primerNodo.getLinea(),
                                primerNodo.getColumna()));
                                logger.error("Introducido nodo ERROR.");
                            }
                        }

                    } else {
                        if (tipo2.compareTo(TipoToken.valueOf("IDENTIFICADOR")) == 0) {
                            if (!_tablaSimbolos.sonBooleanos((TipoToken) primerNodo.getTipos().get(0), segundoNodo.getValor())) {
                                if (_tablaSimbolos.sonConpatibles(segundoNodo.getValor(), tipo1)) {
                                    Nodo resultado = new Nodo();
                                    resultado.concatRight(TipoToken.valueOf("PALABRA_RESERVADA"));
                                    resultado.setValor(primerNodo.getValor());
                                    _pilaNodos.push(resultado);
                                } else {
                                    _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico(
                                        ErroresSemanticos.ERROR_SEMANTICO_TIPOS_NO_COMPATIBLES,
                                        "Tipos no compatibles.",
                                        primerNodo.getLinea(),
                                        primerNodo.getColumna()));

                                    //logger.error("Tipos no compatibles. Error en suma()");
                                    insertarNodoError();
                                    logger.error("Introducido nodo ERROR");
                                }
                            } else {
                                insertarNodoError();

                                _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico(
                                ErroresSemanticos.ERROR_SEMANTICO_BOOLEANOS_NO_PERMITIDOS,
                                "Tipos booleanos no permitidos.",
                                primerNodo.getLinea(),
                                primerNodo.getColumna()));
                                logger.error("Introducido nodo ERROR.");
                            }
                        } else {
                            if (!sonBoolean(primerNodo.getTipos(), segundoNodo.getTipos())) {
                                if (tiposCompatibles(primerNodo.getTipos(), segundoNodo.getTipos())) {
                                    Nodo resultado = new Nodo();
                                    resultado.concatRight(TipoToken.valueOf("PALABRA_RESERVADA"));
                                    resultado.setValor(primerNodo.getValor());
                                    _pilaNodos.push(resultado);
                                } else {
                                    insertarNodoError();
                                    logger.error("Introducido nodo ERROR");
                                }
                            } else {
                                insertarNodoError();
                                _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico(
                                ErroresSemanticos.ERROR_SEMANTICO_BOOLEANOS_NO_PERMITIDOS,
                                "Tipos booleanos no permitidos.",
                                primerNodo.getLinea(),
                                primerNodo.getColumna()));
                                logger.error("Introducido nodo ERROR.");
                            }
                        }
                    }
                } else {
                    sonComparables(primerNodo, segundoNodo);
                }
            }
        }

    }

    private void listaIdentificadores() {
        if (logger.isDebugEnabled()) {
            logger.debug("Entrando en listaIdentificadores");
        }
    }

    private void listaVariables() {
        if (logger.isDebugEnabled()) {
            logger.debug("Entrando en listaVariables");
        }
        Nodo tipo = _pilaNodos.pop();
        //quito la marca
        _pilaNodos.pop();
        Nodo var = _pilaNodos.pop();


        while (!var.getValor().equals("#")) {
            _tablaSimbolos.insertarIdentificador(var.getValor());
            //comprobar que estaContenida en la tabla de simbolos
            //si existe en la tabla rellenarle el tipo
            if (_tablaSimbolos.estaContenida(var.getValor())) {
                //Mirar si utilizamos array o string en la tabla para el tipo
                //ahora puesto esto para el paso
                TipoToken type = (TipoToken) tipo.getTipos().get(0);

                _tablaSimbolos.setTypeIdent(var.getValor(), tipo.getValor());
            } else {
                logger.error("Variable " + var.getValor() + " no insertada en la tabla de simbolos");
            }
            var = _pilaNodos.pop();
        }
        _pilaNodos.push(var);

    }

    private void longint() {
        if (logger.isDebugEnabled()) {
            logger.debug("Entrando en logint");
        }
    }

    private void longreal() {
        if (logger.isDebugEnabled()) {
            logger.debug("Entrando en longreal");
        }
    }

    // TODO: Todas estas funciones redirigen a igual(), pero con vistas
    // a la generación de código intermedio, ¿no sería conveniente separarlas
    // desde ya?
    private void mayor() {
        if (logger.isDebugEnabled()) {
            logger.debug("Entrando en mayor. Redirigiendo a igual().");
        }

        igual();
    }

    private void mayorIgual() {
        if (logger.isDebugEnabled()) {
            logger.debug("Entrando en mayorIgual. Redirigiendo a igual().");
        }
        igual();
    }

    private void menor() {
        if (logger.isDebugEnabled()) {
            logger.debug("Entrando en menor. Redirigiendo a menor().");
        }
        igual();
    }

    private void menorIgual() {
        if (logger.isDebugEnabled()) {
            logger.debug("Entrando en menorIgual");
        }
        igual();
    }

    private void mult() {
        if (logger.isDebugEnabled()) {
            logger.debug("Entrando en mult. Redirigiendo a suma()");
        }
        suma();
    }

    private void nil() {
        if (logger.isDebugEnabled()) {
            logger.debug("Entrando en nil");
        }
    }

    private void numEntero() {
        if (logger.isDebugEnabled()) {
            logger.debug("Introduciendo el tipo Entero");
        }
    /* Nodo nodo = new Nodo();
    nodo.concatRight("INTEGER");
    stack.push(nodo);*/
    }

    private void numReal() {
        if (logger.isDebugEnabled()) {
            logger.debug("Introduciendo el tipo Real");
        }
    /*Nodo nodo = new Nodo();
    nodo.concatRight("REAL");
    stack.push(nodo);*/
    }

    private void numero() {
        if (logger.isDebugEnabled()) {
            logger.debug("Entrando en numero");
        }

    }

    private void operadorAnd() {
        if (logger.isDebugEnabled()) {
            logger.debug("Entrando en operadorAnd. Se redirige a operadorOr()");
        }

        // TODO: Generación de código intermedio

        Nodo primerNodo = _pilaNodos.pop();
        if (!(primerNodo.getValor().equals(PLGConstants.ERROR))) {
            Nodo segundoNodo = _pilaNodos.pop();
            if (!(segundoNodo.getValor().equals(PLGConstants.ERROR))) {
                ArrayList tipo1 = primerNodo.getTipos();
                ArrayList tipo2 = segundoNodo.getTipos();
                if (sonBoolean(tipo1, tipo2)) {
                    Nodo resultado = new Nodo();
                    resultado.setTipo(tipo1);
                    resultado.setValor("");
                    _pilaNodos.push(resultado);
                    logger.debug("Introducido nodo BOOLEAN");
                } else {
                    insertarNodoError();
                    logger.error("Introducido nodo ERROR");
                }
            } else {

                insertarNodoError();
                logger.error("Introducido nodo ERROR");
            }
        } else {

            insertarNodoError();
            logger.error("Introducido nodo ERROR");
        }
    }

    private void operadorComplemento() {
        if (logger.isDebugEnabled()) {
            logger.debug("Entrando en operadorComplemento. Redirigiendo a operadorNot()");
        }

        // TODO: Generación de código intermedio

        Nodo var = _pilaNodos.pop();
        if (!var.getValor().equals(PLGConstants.ERROR)) {
            ArrayList tipos = var.getTipos();
            TipoToken tipoToken = (TipoToken) tipos.get(0);
            if ((tipoToken.compareTo(TipoToken.valueOf("PALABRA_RESERVADA")) == 0)) {

                Nodo resultado = new Nodo();
                resultado.concatRight(tipoToken);
                _pilaNodos.push(resultado);
            } else if ((tipoToken.compareTo(TipoToken.valueOf("IDENTIFICADOR")) == 0)) {
                ArrayList tiposVar = _tablaSimbolos.dameTipoIdentificador(var.getValor());
                if (tiposVar != null) {
                    TipoToken aux = (TipoToken) tiposVar.get(0);
                    if ((aux.compareTo(TipoToken.valueOf("PALABRA_RESERVADA")) == 0)) {

                        Nodo resultado = new Nodo();
                        resultado.concatRight(aux);
                        _pilaNodos.push(resultado);
                    } else {
                        if (logger.isDebugEnabled()) {
                            logger.error("Tipos no compatibles. Error en operadorNot()");
                        }
                        insertarNodoError();
                        logger.error("Introducido nodo ERROR");
                    }
                } else {
                    if (logger.isDebugEnabled()) {
                        logger.error("Tipos no compatibles. Error en operadorNot()");
                    }
                    insertarNodoError();
                    logger.error("Introducido nodo ERROR");
                }
            } else {
                if (logger.isDebugEnabled()) {
                    logger.error("Tipos no compatibles. Error en operadorNot()");
                }
                insertarNodoError();
                logger.error("Introducido nodo ERROR");
            }

        } else {
            if (logger.isDebugEnabled()) {
                logger.error("Tipos no compatibles. Error en operadorNot()");
            }
            insertarNodoError();
            logger.error("Introducido nodo ERROR");
        }
    }

    private void operadorIn() {
        if (logger.isDebugEnabled()) {
            logger.debug("Entrando en operadorIn");
        }
    }

    private void operadorModulo() {
        if (logger.isDebugEnabled()) {
            logger.debug("Entrando en operadorModulo. Se redirige a divisionEntera()");
        }
        divisionEntera();
    }

    private void operadorNot() {
        if (logger.isDebugEnabled()) {
            logger.debug("Entrando en operadorNot");
        }
        Nodo var = _pilaNodos.pop();
        if (!var.getValor().equals(PLGConstants.ERROR)) {
            ArrayList tipos = var.getTipos();
            TipoToken tipoToken = (TipoToken) tipos.get(0);
            if ((tipoToken.compareTo(TipoToken.valueOf("PALABRA_RESERVADA")) == 0)) {

                Nodo resultado = new Nodo();
                resultado.concatRight(tipoToken);
                _pilaNodos.push(resultado);
            } else if ((tipoToken.compareTo(TipoToken.valueOf("IDENTIFICADOR")) == 0)) {
                ArrayList tiposVar = _tablaSimbolos.dameTipoIdentificador(var.getValor());
                if (tiposVar != null) {
                    TipoToken aux = (TipoToken) tiposVar.get(0);
                    if ((aux.compareTo(TipoToken.valueOf("PALABRA_RESERVADA")) == 0)) {

                        Nodo resultado = new Nodo();
                        resultado.concatRight(aux);
                        _pilaNodos.push(resultado);
                    } else {
                        if (logger.isDebugEnabled()) {
                            _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico(
                                ErroresSemanticos.ERROR_SEMANTICO_TIPOS_NO_COMPATIBLES,
                                "Tipos no compatibles.",
                                var.getLinea(),
                                var.getColumna()));
                            //logger.error("Tipos no compatibles. Error en operadorNot()");
                        }
                        insertarNodoError();
                        logger.error("Introducido nodo ERROR");
                    }
                } else {

                    _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico(
                                ErroresSemanticos.ERROR_SEMANTICO_TIPOS_NO_COMPATIBLES,
                                "Tipos no compatibles.",
                                var.getLinea(),
                                var.getColumna()));
                    
                    if (logger.isDebugEnabled()) {
                        logger.error("Tipos no compatibles. Error en operadorNot()");
                    }
                    _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico(
                                ErroresSemanticos.ERROR_SEMANTICO_TIPOS_NO_COMPATIBLES,
                                "Tipos no compatibles.",
                                var.getLinea(),
                                var.getColumna()));
                    insertarNodoError();
                    logger.error("Introducido nodo ERROR");
                }
            } else {
                if (logger.isDebugEnabled()) {
                    logger.error("Tipos no compatibles. Error en operadorNot()");
                }
                _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico(
                                ErroresSemanticos.ERROR_SEMANTICO_TIPOS_NO_COMPATIBLES,
                                "Tipos no compatibles.",
                                var.getLinea(),
                                var.getColumna()));
                insertarNodoError();
                logger.error("Introducido nodo ERROR");
            }

        } else {
            if (logger.isDebugEnabled()) {
                logger.error("Tipos no compatibles. Error en operadorNot()");
            }
            _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico(
                                ErroresSemanticos.ERROR_SEMANTICO_TIPOS_NO_COMPATIBLES,
                                "Tipos no compatibles.",
                                var.getLinea(),
                                var.getColumna()));
            insertarNodoError();
            logger.error("Introducido nodo ERROR");
        }
    }

    private void operadorOr() {
        if (logger.isDebugEnabled()) {
            logger.debug("Entrando en operadorOr");
        }
        Nodo primerNodo = _pilaNodos.pop();
        if (!(primerNodo.getValor().equals(PLGConstants.ERROR))) {
            Nodo segundoNodo = _pilaNodos.pop();
            if (!(segundoNodo.getValor().equals(PLGConstants.ERROR))) {
                ArrayList tipo1 = primerNodo.getTipos();
                ArrayList tipo2 = segundoNodo.getTipos();
                if (sonBoolean(tipo1, tipo2)) {
                    Nodo resultado = new Nodo();
                    resultado.setTipo(tipo1);
                    resultado.setValor("");
                    _pilaNodos.push(resultado);
                    logger.debug("Introducido nodo BOOLEAN");
                } else {
                    insertarNodoError();
                    logger.error("Introducido nodo ERROR");
                }
            } else {
                insertarNodoError();
                logger.error("Introducido nodo ERROR");
            }
        } else {
            insertarNodoError();
            logger.error("Introducido nodo ERROR");
        }
    }

    private void proc() {
        if (logger.isDebugEnabled()) {
            logger.debug("Entrando en proc");
        }
    }

    private void real() {
        if (logger.isDebugEnabled()) {
            logger.debug("Entrando en real");
        }
    }

    private void resta() {
        if (logger.isDebugEnabled()) {
            logger.debug("Entrando en resta. LLama a suma que hace lo mismo");
        }
        suma();
    }

    private void subrango() {
        if (logger.isDebugEnabled()) {
            logger.debug("Entrando en subrango");
        }
    }

    private void suma() {
        if (logger.isDebugEnabled()) {
            logger.debug("Entrando en suma");
        }
        Nodo primerNodo = _pilaNodos.pop();
        if (!(primerNodo.getValor().equals(PLGConstants.ERROR))) {
            Nodo segundoNodo = _pilaNodos.pop();
            if (!(segundoNodo.getValor().equals(PLGConstants.ERROR))) {
                ArrayList arrayTipo1 = primerNodo.getTipos();
                ArrayList arrayTipo2 = segundoNodo.getTipos();
                TipoToken tipo1 = (TipoToken) arrayTipo1.get(0);
                TipoToken tipo2 = (TipoToken) arrayTipo2.get(0);

                if (tipo1.compareTo(TipoToken.valueOf("IDENTIFICADOR")) == 0) {
                    if (tipo2.compareTo(TipoToken.valueOf("IDENTIFICADOR")) == 0) {
                        if (!_tablaSimbolos.sonBooleanos(primerNodo.getValor(), segundoNodo.getValor())) {
                            if (_tablaSimbolos.sonConpatiblesIdentificadores(primerNodo.getValor(), segundoNodo.getValor())) {
                                Nodo resultado = new Nodo();
                                ArrayList tiposResultado = _tablaSimbolos.dameTipoIdentificador(primerNodo.getValor());
                                resultado.setTipo(tiposResultado);
                                resultado.setValor("");
                                _pilaNodos.push(resultado);
                            } else {
                                _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico(
                                ErroresSemanticos.ERROR_SEMANTICO_TIPOS_NO_COMPATIBLES,
                                "Tipos no compatibles.",
                                primerNodo.getLinea(),
                                primerNodo.getColumna()));

                                //logger.error("Tipos no compatibles. Error en suma()");
                                insertarNodoError();
                                logger.error("Introducido nodo ERROR");
                            }
                        } else {
                            insertarNodoError();
                            _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico(
                                ErroresSemanticos.ERROR_SEMANTICO_BOOLEANOS_NO_PERMITIDOS,
                                "Tipos booleanos no permitidos.",
                                primerNodo.getLinea(),
                                primerNodo.getColumna()));
                            logger.error("Introducido nodo ERROR. Tipos booleanos no permitidos");
                        }
                    } else {
                        if (!_tablaSimbolos.sonBooleanos((TipoToken) segundoNodo.getTipos().get(0), primerNodo.getValor())) {
                            if (_tablaSimbolos.sonConpatibles(primerNodo.getValor(), tipo2)) {
                                Nodo resultado = new Nodo();
                                resultado.concatRight(tipo2);
                                resultado.setValor(segundoNodo.getValor());
                                _pilaNodos.push(resultado);
                            } else {
                                _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico(
                                ErroresSemanticos.ERROR_SEMANTICO_TIPOS_NO_COMPATIBLES,
                                "Tipos no compatibles.",
                                primerNodo.getLinea(),
                                primerNodo.getColumna()));
                                //logger.error("Tipos no compatibles. Error en suma()");
                                insertarNodoError();
                                logger.error("Introducido nodo ERROR");
                            }
                        } else {
                            insertarNodoError();
                            _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico(
                                ErroresSemanticos.ERROR_SEMANTICO_BOOLEANOS_NO_PERMITIDOS,
                                "Tipos booleanos no permitidos.",
                                primerNodo.getLinea(),
                                primerNodo.getColumna()));
                            logger.error("Introducido nodo ERROR. Tipos booleanos no permitidos");
                        }
                    }

                } else {
                    if (tipo2.compareTo(TipoToken.valueOf("IDENTIFICADOR")) == 0) {
                        if (!_tablaSimbolos.sonBooleanos((TipoToken) primerNodo.getTipos().get(0), segundoNodo.getValor())) {
                            if (_tablaSimbolos.sonConpatibles(segundoNodo.getValor(), tipo1)) {
                                Nodo resultado = new Nodo();
                                resultado.concatRight(tipo1);
                                resultado.setValor(primerNodo.getValor());
                                _pilaNodos.push(resultado);
                            } else {
                                _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico(
                                ErroresSemanticos.ERROR_SEMANTICO_TIPOS_NO_COMPATIBLES,
                                "Tipos no compatibles.",
                                primerNodo.getLinea(),
                                primerNodo.getColumna()));
                                logger.error("Tipos no compatibles. Error en suma()");
                                insertarNodoError();
                                logger.error("Introducido nodo ERROR");
                            }
                        } else {
                            insertarNodoError();
                                _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico(
                                ErroresSemanticos.ERROR_SEMANTICO_BOOLEANOS_NO_PERMITIDOS,
                                "Tipos booleanos no permitidos.",
                                primerNodo.getLinea(),
                                primerNodo.getColumna()));
                            logger.error("Introducido nodo ERROR. Tipos booleanos no permitidos");
                        }
                    } else {
                        if (!sonBoolean(primerNodo.getTipos(), segundoNodo.getTipos())) {
                            if (tiposCompatibles(primerNodo.getTipos(), segundoNodo.getTipos())) {
                                Nodo resultado = new Nodo();
                                resultado.setTipo(primerNodo.getTipos());
                                resultado.setValor(primerNodo.getValor());
                                _pilaNodos.push(resultado);
                            } else {
                                _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico(
                                ErroresSemanticos.ERROR_SEMANTICO_TIPOS_NO_COMPATIBLES,
                                "Tipos no compatibles.",
                                primerNodo.getLinea(),
                                primerNodo.getColumna()));
                                logger.error("Tipos no compatibles. Error en suma()");
                                insertarNodoError();
                                logger.error("Introducido nodo ERROR");
                            }
                        } else {
                            insertarNodoError();
                                _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico(
                                ErroresSemanticos.ERROR_SEMANTICO_BOOLEANOS_NO_PERMITIDOS,
                                "Tipos booleanos no permitidos.",
                                primerNodo.getLinea(),
                                primerNodo.getColumna()));
                            logger.error("Introducido nodo ERROR. Tipos booleanos no permitidos");
                        }
                    }
                }
            } else {
                insertarNodoError();
                logger.error("Introducido nodo ERROR");
            }
        } else {
            insertarNodoError();
            logger.error("Introducido nodo ERROR");
        }
    }

    private void termino() {
        if (logger.isDebugEnabled()) {
            logger.debug("Entrando en termino");
        }
    }

    private void tipoPredefinido() {
        if (logger.isDebugEnabled()) {
            logger.debug("Entrando en tipoPredefinido");
        }
    }

    private void tipoSimple() {
        if (logger.isDebugEnabled()) {
            logger.debug("Entrando en tipoSimple");
        }
    }

    private void tipoSimple2() {
        if (logger.isDebugEnabled()) {
            logger.debug("Entrando en tipoSimple2");
        }
    }

    /**
     * Comprueba si alguno de los tipos introducidos es booleano(booleano se mete en la pila como
     * TipoToken PALABRA_RESERVADA
     * @param tipo1 tipo a comprobar
     * @param tipo2 segundo tipo a comprobar
     * @return true si alguno de los dos es booleano y false en otro caso
     */
    private boolean sonBoolean(ArrayList tipo1, ArrayList tipo2) {
        if (logger.isDebugEnabled()) {
            logger.debug("Comprobando si los tipos " + tipo1 + " o " + tipo2 + " son booleanos");
        }
        boolean booleanos = false;
        for (int i = 0; i < tipo1.size(); i++) {
            TipoToken tipoToken1 = (TipoToken) tipo1.get(i);
            TipoToken tipoToken2 = (TipoToken) tipo2.get(i);
            if (tipoToken1.compareTo(TipoToken.valueOf("PALABRA_RESERVADA")) == 0) {
                booleanos = true;
            }
            if (tipoToken2.compareTo(TipoToken.valueOf("PALABRA_RESERVADA")) == 0) {
                booleanos = true;
            }
        }
        return booleanos;
    }

    private boolean tiposCompatibles(ArrayList tipo1, ArrayList tipo2) {
        if (logger.isDebugEnabled()) {
            logger.debug("Comprobando los tipos " + tipo1 + " y " + tipo2);
        }
        boolean iguales = true;
        for (int i = 0; i < tipo1.size(); i++) {
            TipoToken tipoToken1 = (TipoToken) tipo1.get(i);
            TipoToken tipoToken2 = (TipoToken) tipo2.get(i);
            if (tipoToken1.compareTo(tipoToken2) != 0) {
                iguales = false;
            }
        }
        return iguales;
    }

    private void unitResta() {
        if (logger.isDebugEnabled()) {
            logger.debug("Entrando en unitSuma");
        }
        Nodo var = _pilaNodos.pop();
        if (!var.getValor().equals(PLGConstants.ERROR)) {
            ArrayList tipos = var.getTipos();
            TipoToken tipoToken = (TipoToken) tipos.get(0);
            if ((tipoToken.compareTo(TipoToken.valueOf("NUMERO_REAL")) == 0) || (tipoToken.compareTo(TipoToken.valueOf("NUMERO_ENTERO")) == 0)) {

                Nodo resultado = new Nodo();
                resultado.concatRight(tipoToken);
                _pilaNodos.push(resultado);
            } else if ((tipoToken.compareTo(TipoToken.valueOf("IDENTIFICADOR")) == 0)) {
                ArrayList tiposVar = _tablaSimbolos.dameTipoIdentificador(var.getValor());
                if (tiposVar != null) {
                    TipoToken aux = (TipoToken) tiposVar.get(0);
                    if ((aux.compareTo(TipoToken.valueOf("NUMERO_REAL")) == 0) || (aux.compareTo(TipoToken.valueOf("NUMERO_ENTERO")) == 0)) {

                        Nodo resultado = new Nodo();
                        resultado.concatRight(aux);
                        _pilaNodos.push(resultado);
                    } else {

                        _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico(
                                ErroresSemanticos.ERROR_SEMANTICO_TIPOS_NO_COMPATIBLES,
                                "Tipos no compatibles.",
                                var.getLinea(),
                                var.getColumna()));
                        if (logger.isDebugEnabled()) {
                            logger.error("Tipos no compatibles. Error en unitSuma()");
                        }
                        insertarNodoError();
                        logger.error("Introducido nodo ERROR");
                    }
                } else {
                    _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico(
                                ErroresSemanticos.ERROR_SEMANTICO_TIPOS_NO_COMPATIBLES,
                                "Tipos no compatibles.",
                                var.getLinea(),
                                var.getColumna()));
                    if (logger.isDebugEnabled()) {
                        logger.error("Tipos no compatibles. Error en unitSuma()");
                    }
                    insertarNodoError();
                    logger.error("Introducido nodo ERROR");
                }
            } else {
                _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico(
                                ErroresSemanticos.ERROR_SEMANTICO_TIPOS_NO_COMPATIBLES,
                                "Tipos no compatibles.",
                                var.getLinea(),
                                var.getColumna()));
                if (logger.isDebugEnabled()) {
                    logger.error("Tipos no compatibles. Error en unitSuma()");
                }
                insertarNodoError();
                logger.error("Introducido nodo ERROR");
            }

        } else {
            _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico(
                                ErroresSemanticos.ERROR_SEMANTICO_TIPOS_NO_COMPATIBLES,
                                "Tipos no compatibles.",
                                var.getLinea(),
                                var.getColumna()));
            if (logger.isDebugEnabled()) {
                logger.error("Tipos no compatibles. Error en unitSuma()");
            }
            insertarNodoError();
            logger.error("Introducido nodo ERROR");
        }
    }

    private void unitSuma() {
        if (logger.isDebugEnabled()) {
            logger.debug("Entrando en unitSuma");
        }
        Nodo var = _pilaNodos.pop();
        if (!var.getValor().equals(PLGConstants.ERROR)) {
            ArrayList tipos = var.getTipos();
            TipoToken tipoToken = (TipoToken) tipos.get(0);
            if ((tipoToken.compareTo(TipoToken.valueOf("NUMERO_REAL")) == 0) || (tipoToken.compareTo(TipoToken.valueOf("NUMERO_ENTERO")) == 0)) {

                Nodo resultado = new Nodo();
                resultado.concatRight(tipoToken);
                _pilaNodos.push(resultado);
            } else if ((tipoToken.compareTo(TipoToken.valueOf("IDENTIFICADOR")) == 0)) {
                ArrayList tiposVar = _tablaSimbolos.dameTipoIdentificador(var.getValor());
                if (tiposVar != null) {
                    TipoToken aux = (TipoToken) tiposVar.get(0);
                    if ((aux.compareTo(TipoToken.valueOf("NUMERO_REAL")) == 0) || (aux.compareTo(TipoToken.valueOf("NUMERO_ENTERO")) == 0)) {

                        Nodo resultado = new Nodo();
                        resultado.concatRight(aux);
                        _pilaNodos.push(resultado);
                    } else {
                        _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico(
                                ErroresSemanticos.ERROR_SEMANTICO_TIPOS_NO_COMPATIBLES,
                                "Tipos no compatibles.",
                                var.getLinea(),
                                var.getColumna()));
                        if (logger.isDebugEnabled()) {
                            logger.error("Tipos no compatibles. Error en unitSuma()");
                        }
                        insertarNodoError();
                        logger.error("Introducido nodo ERROR");
                    }
                }
            } else {

                _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico(
                                ErroresSemanticos.ERROR_SEMANTICO_TIPOS_NO_COMPATIBLES,
                                "Tipos no compatibles.",
                                var.getLinea(),
                                var.getColumna()));
                if (logger.isDebugEnabled()) {
                    logger.error("Tipos no compatibles. Error en unitSuma()");
                }
                insertarNodoError();
                logger.error("Introducido nodo ERROR");
            }

        } else {
            if (logger.isDebugEnabled()) {
                logger.error("Tipos no compatibles. Error en unitSuma()");
            }

            insertarNodoError();
            logger.error("Introducido nodo ERROR");
        }


    }

    private void vacio() {
        if (logger.isDebugEnabled()) {
            logger.debug("Entrando en vacio");
        }
    }

    private void variable() {
        if (logger.isDebugEnabled()) {
            logger.debug("Entrando en variable");
        }

    }

    private void verdadero() {
        if (logger.isDebugEnabled()) {
            logger.debug("Entrando en verdadero");
        }
    }
}

