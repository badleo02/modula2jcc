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
    case 12:  Cadena();  break;
    case 13:  Caracter();  break;
    case 14:  NumeroEntero();  break;
    case 15:  NumeroReal();  break;
    case 16:  TipoPredefinidoPorUsuario();  break;
    case 17:  BITSET();  break;
    case 18:  BOOLEAN();  break;
    case 19:  CARDINAL();  break;
    case 20:  CHAR();  break;
    case 21:  INTEGER();  break;
    case 22:  LONGINT();  break;
    case 23:  LONGREAL();  break;
    case 24:  PROC();  break;
    case 25:  REAL();  break;
    case 26:  TRUE();  break;
    case 27:  FALSE();  break;
    case 28:  NIL();  break;
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
                _tablaActual = _tablaActual.abrirAmbito(_tablaActual);
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


    /**
     * Comprueba que el nombre de la tabla y el del identificador despues del
     * END sean el mismo. Ademas cierra el ambito actual.
     */
    private void FinDeModulo() {

        Nodo id = _pilaNodos.pop();

        if (_tablaActual.getNombre().equals(id.getLexema())) {
            _tablaActual = _tablaActual.cerrarAmbito(_tablaActual);
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
      
        // si en la cabecera de la pila hay un error, continua ahi
        if (_pilaNodos.peek().getTipoBasico() == TipoSemantico.ERROR)
            return;
      
        // sacamos el identificador para el procedimiento
        Nodo nodo = _pilaNodos.peek(); 
        
        // comprobamos unicidad
        if (_tablaActual.estaModuloDeclarado(nodo.getLexema()))
             _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico("El identificador \"" + nodo.getLexema() + "\" esta siendo redefinido.",
                        nodo.getLinea(),
                        nodo.getColumna()));
        
        // abrimos ambito.       
        _tablaActual.abrirAmbito(_tablaActual);
        _tablaActual.setNombre(nodo.getLexema());
    }

    
    /** 
     * 
     * completa la definicion de un modulo
     */
    private void CabeceraDeProcedure() {
        
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
