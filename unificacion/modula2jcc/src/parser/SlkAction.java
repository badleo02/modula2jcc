package parser;

import semantico.PilaNodos;
import gestor_de_errores.GestorErrores;
import gestor_de_errores.TErrorSemantico;
import java.util.ArrayList;
import scanner.TipoToken;
import semantico.Nodo;
import semantico.TipoSemantico;
import tabla_de_simbolos.TablaSimbolos;
//import org.apache.log4j.Logger;
import tabla_de_simbolos.TipoSimbolo;

/**
 * Clase que implementa las acciones semanticas.
 * 
 * @author Macrogrupo 1
 */
public class SlkAction {

    // CONSTANTES
    //private static final Logger logger = Logger.getLogger(SlkAction.class);    // ATRIBUTOS
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
                FinDeModulo();
                break;
            case 2:
                ComienzoDeModulo();
                break;
            case 3:
                AsociacionConstante();
                break;
            case 4:
                DefinicionDeTipo();
                break;
            case 5:
                TipoConjunto();
                break;
            case 6:
                TipoPuntero();
                break;
            case 7:
                DeclaracionVariables();
                break;
            case 8:
                TipoPredefinidoPorUsuario();
                break;
            case 9:
                BITSET();
                break;
            case 10:
                BOOLEAN();
                break;
            case 11:
                CARDINAL();
                break;
            case 12:
                CHAR();
                break;
            case 13:
                INTEGER();
                break;
            case 14:
                LONGINT();
                break;
            case 15:
                LONGREAL();
                break;
            case 16:
                PROC();
                break;
            case 17:
                REAL();
                break;
            case 18:
                TRUE();
                break;
            case 19:
                FALSE();
                break;
            case 20:
                NIL();
                break;
            case 21:
                marcaInicioLista();
                break;
        }
    }

    /**
     * Abre un nuevo ambito y actualiza el nombre de la nueva tabla 
     * correspondiente a dicho ambito.
     */
    private void ComienzoDeModulo() {

        Nodo nodo = _pilaNodos.pop();
        _tablaSimbolos.crearAmbito(nodo.getLexema());
    }

    /**
     * Comprueba que el nombre de la tabla y el del identificador despues del
     * END sean el mismo. Ademas cierra el ambito actual.
     */
    private void FinDeModulo() {

        Nodo nodo = _pilaNodos.pop();

        // Si se llaman igual el de arriba que el de abajo
        if (nodo.getLexema().equals(_tablaSimbolos.getNombre())) {
            _tablaSimbolos.cerrarAmbitoModulo(nodo.getLexema());
        } else {

            Nodo n = new Nodo();
            n.addTipo(TipoSemantico.ERROR);
            _pilaNodos.add(n);
            _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico("Identificador de modulo <" + nodo.getLexema() + "> incorrecto, se esperaba <" + _tablaSimbolos.getNombre() + ">",
                    nodo.getLinea(),
                    nodo.getColumna()));
        }
    }

    private void AsociacionConstante() {
        try {
            Nodo derecha = _pilaNodos.pop(); // parte derecha, el valor

            _pilaNodos.pop();// operador

            Nodo izquierda = _pilaNodos.pop(); // parte izq, en nm de la funcion

            // comprobación de errores:
            if (derecha.esError()) {
                Nodo n = new Nodo();
                n.addTipo(TipoSemantico.ERROR);
                _pilaNodos.add(n);
                _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico("El simbolo <" + derecha.getLexema() + "> no es una definicion de tipo",
                        derecha.getLinea(),
                        derecha.getColumna()));
            } else {
                // hay que completar el simbolo idetificado por el lexema de la parte izq
                // con tipo simbolo Constante. Tipo de la parte derecha y valor
                _tablaSimbolos.completaConstante(izquierda.getLexema(), derecha.getTipos(), derecha.getLexema());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void DeclaracionVariables() {
        try {
            // desapilamos la parte derecha que nos dira el tipo de las variables.
            Nodo derecha = _pilaNodos.pop();

            // comprueba si hay errores en la definicion del tipo (parte derecha)
            if (!derecha.esError()) {
            
                Nodo id;
                // desapilamos identificadores hasta llegar a la marca.
                // SIEMPRE hay por lo menos uno!
                // Aqui se desapila el nodo de marca también
                do {
                    id = _pilaNodos.pop();
                    // completamos la definicion de la variable
                    if (!id.esInicioListaIdentificadores()) {
                        _tablaSimbolos.completaVariable(id.getLexema(), derecha.getTipos());
                    }
                } while (!id.esInicioListaIdentificadores());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void DefinicionDeTipo() {
        //REGLA: DefinicionDeTipo: Identificador = EsquemaDeTipo
        try {
            Nodo derecha = _pilaNodos.pop(); // parte derecha, el valor
            _pilaNodos.pop(); // operador
            Nodo izquierda = _pilaNodos.pop(); // parte izq, en nm de la funcion

            // comprobación de errores:
            // si se da un error llamamos al gestor de errores y no lo apilamos error
            // si la parte derecha es un identificador debemos comprobar que sea
            // una definicion de tipo.
            if (derecha._tipoToken.equals(TipoToken.IDENTIFICADOR) && !_tablaSimbolos.esDeTipo(derecha.getLexema(), TipoSimbolo.TIPO)) {
                // lo de la derecha no es un tipo
                Nodo n = new Nodo();
                n.addTipo(TipoSemantico.ERROR);
                _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico("El simbolo <" + derecha.getLexema() + "> no es una definicion de tipo",
                        derecha.getLinea(),
                        derecha.getColumna()));
            } else {
                // hay que completar el simbolo idetificado por el lexema de la parte izq
                // con tipo simbolo Tipo.
                _tablaSimbolos.completaTipo(izquierda.getLexema(), derecha.getTipos());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void TipoConjunto() {
        //REGLA TipoConjunto: SET OF TipoSimple

        try {

            Nodo n = _pilaNodos.pop();

            // si no es error completamos diciendole que es de tipo conjunto
            if (!n.esError()) {
                n.addTipo(TipoSemantico.CONJUNTO);
            }
            // Si hay un error lo propagamos
            _pilaNodos.push(n);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Comprobamos que el identificador que representa el tipo
     * predefinido haya sido como marcado en la tabla de simbolos
     * como tipo. Si no ha sido marcado como tipo significa que no
     * se ha declarado en la parte del TYPE y por lo tanto es un
     * tipo incorrecto.
     */
    private void TipoPredefinidoPorUsuario() {
        try {
            Nodo tipoPredefinido = _pilaNodos.pop(); // TIPO PREDEFINIDO
            
            if(_tablaSimbolos.esDeTipo(tipoPredefinido.getLexema(), TipoSimbolo.TIPO)){
                // Obtenemos el tipo semantico del identificador predefinido
                tipoPredefinido.setTipo(_tablaSimbolos.getTipoSemanticoSimbolo(tipoPredefinido.getLexema()));
                
                // Lo apilamos
                _pilaNodos.push(tipoPredefinido);
            }
            else{
                Nodo n = new Nodo();
                n.addTipo(TipoSemantico.ERROR);
                _pilaNodos.add(n);
                _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico("El tipo <" + tipoPredefinido.getLexema() + "> no está definido",
                        tipoPredefinido.getLinea(),
                        tipoPredefinido.getColumna()));
            }         
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    private void TipoPuntero() {
        //REGLA: TipoPuntero:POINTER TO EsquemaDeTipo
        try {
            Nodo n = _pilaNodos.pop();

            // si no es error completamos diciendole que es de tipo puntero
            if (!n.esError()) {
                n.addTipo(TipoSemantico.PUNTERO);
            }
            // Si hay un error lo propagamos
            _pilaNodos.push(n);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Añade una marca en la pila para poder desapilar la lista hasta este 
     * elemento.
     */
    private void marcaInicioLista() {
        // Quitamos el primer identificador de la lista para meter la marca en 
        // su lugar
        Nodo primerIdenDeLaLista = _pilaNodos.pop();

        // Creamos y apilamos el nodo de marca de inicio de la lista
        Nodo n = new Nodo();
        n.creaInicioListaIdentificadores();
        _pilaNodos.push(n);

        // Apilamos el primer identificador de la lista, consiguiendo tener todos 
        // los identificadores encima del nodo de marca de inicio de la lista de 
        // identificadores 
        _pilaNodos.push(primerIdenDeLaLista);
    }

    private void TRUE() {
        _pilaNodos.pop(); // Operador 
        Nodo n = new Nodo(); // NIL
        ArrayList<TipoSemantico> tipoSemantico = n.getTipos();
        tipoSemantico.add(TipoSemantico.NULO);
        n.setTipo(tipoSemantico);
        _pilaNodos.push(n);
    }

    private void FALSE() {
        _pilaNodos.pop(); // Operador 
        Nodo n = new Nodo(); // FALSE
        ArrayList<TipoSemantico> tipoSemantico = n.getTipos();
        tipoSemantico.add(TipoSemantico.BOOLEANO);
        n.setTipo(tipoSemantico);
        _pilaNodos.push(n);
    }

    private void NIL() {
        _pilaNodos.pop(); // Operador 
        Nodo n = new Nodo(); // NIL
        ArrayList<TipoSemantico> tipoSemantico = n.getTipos();
        tipoSemantico.add(TipoSemantico.NULO);
        n.setTipo(tipoSemantico);
        _pilaNodos.push(n);
    }

    private void BITSET() {
        Nodo n = _pilaNodos.pop();
        ArrayList<TipoSemantico> tipoSemantico = n.getTipos();
        tipoSemantico.add(TipoSemantico.BITSET);
        n.setTipo(tipoSemantico);
        _pilaNodos.push(n);
    }

    private void BOOLEAN() {
        Nodo n = _pilaNodos.pop();
        ArrayList<TipoSemantico> tipoSemantico = n.getTipos();
        tipoSemantico.add(TipoSemantico.BOOLEANO);
        n.setTipo(tipoSemantico);
        _pilaNodos.push(n);
    }

    private void CARDINAL() {
        Nodo n = _pilaNodos.pop();
        ArrayList<TipoSemantico> tipoSemantico = n.getTipos();
        tipoSemantico.add(TipoSemantico.CARDINAL);
        n.setTipo(tipoSemantico);
        _pilaNodos.push(n);
    }

    private void CHAR() {
        Nodo n = _pilaNodos.pop();
        ArrayList<TipoSemantico> tipoSemantico = n.getTipos();
        tipoSemantico.add(TipoSemantico.CARACTER);
        n.setTipo(tipoSemantico);

        _pilaNodos.push(n);
    }

    private void INTEGER() {
        Nodo n = _pilaNodos.pop();
        ArrayList<TipoSemantico> tipoSemantico = n.getTipos();
        tipoSemantico.add(TipoSemantico.ENTERO);
        n.setTipo(tipoSemantico);

        _pilaNodos.push(n);
    }

    private void LONGINT() {
        Nodo n = _pilaNodos.pop();
        ArrayList<TipoSemantico> tipoSemantico = n.getTipos();
        tipoSemantico.add(TipoSemantico.ENTERO_LARGO);
        n.setTipo(tipoSemantico);
        _pilaNodos.push(n);
    }

    private void LONGREAL() {
        Nodo n = _pilaNodos.pop();
        ArrayList<TipoSemantico> tipoSemantico = n.getTipos();
        tipoSemantico.add(TipoSemantico.REAL_LARGO);
        n.setTipo(tipoSemantico);

        _pilaNodos.push(n);
    }

    private void PROC() {
        Nodo n = _pilaNodos.pop();
        ArrayList<TipoSemantico> tipoSemantico = n.getTipos();
        tipoSemantico.add(TipoSemantico.CARDINAL);
        n.setTipo(tipoSemantico);
        _pilaNodos.push(n);
    }

    private void REAL() {
        Nodo n = _pilaNodos.pop();
        ArrayList<TipoSemantico> tipoSemantico = n.getTipos();
        tipoSemantico.add(TipoSemantico.REAL);
        n.setTipo(tipoSemantico);
        _pilaNodos.push(n);
    }

    /**
     * Comprueba que los dos nodos sean de tipo booleano
     * @param nodo1 Primer nodo a comprobar
     * @param nodo2 Segundo nodo a comprobar
     * @return true si los dos nodos son de tipo booleano y false en otro caso
     */
    private boolean sonBooleanos(Nodo nodo1, Nodo nodo2) {
        if (nodo1 != null && nodo2 != null) {
            if (nodo1.getTipoBasico() == TipoSemantico.ENTERO && nodo1.getTipoBasico() == TipoSemantico.ENTERO) {
                return true;
            }
            return false;
        }
        return false;
    }

    /**
     * Comprueba que el nodo introducido sea de tipo booleano
     * @param nodo Nodo a comprobar
     * @return true si el nodo es de tipo booleano, false en otro caso
     */
    private boolean esBooleano(Nodo nodo) {
        if (nodo != null && nodo.getTipoBasico() == TipoSemantico.ENTERO) {
            return true;
        }
        return false;
    }

    /**
     * Comprueba que los dos nodos sean de tipo entero
     * @param nodo1 Primer nodo a comprobar
     * @param nodo2 Segundo nodo a comprobar
     * @return true si los dos nodos son de tipo entero y false en otro caso
     */
    private boolean sonEnteros(Nodo nodo1, Nodo nodo2) {
        if (nodo1 != null && nodo2 != null) {
            if (nodo1.getTipoBasico() == TipoSemantico.ENTERO && nodo1.getTipoBasico() == TipoSemantico.ENTERO) {
                return true;
            }
            return false;
        }
        return false;
    }

    /**
     * Comprueba que el nodo introducido sea de tipo entero
     * @param nodo Nodo a comprobar
     * @return true si el nodo es de tipo entero, false en otro caso
     */
    private boolean esEntero(Nodo nodo) {
        if (nodo != null && nodo.getTipoBasico() == TipoSemantico.ENTERO) {
            return true;
        }
        return false;
    }

    /**
     * Comprueba que los dos nodos sean de tipo real
     * @param nodo1 Primer nodo a comprobar
     * @param nodo2 Segundo nodo a comprobar
     * @return true si los dos nodos son de tipo real y false en otro caso
     */
    private boolean sonReales(Nodo nodo1, Nodo nodo2) {
        if (nodo1 != null && nodo2 != null) {
            if (nodo1.getTipoBasico() == TipoSemantico.REAL && nodo1.getTipoBasico() == TipoSemantico.REAL) {
                return true;
            }
            return false;
        }
        return false;
    }

    /**
     * Comprueba que el nodo introducido sea de tipo real
     * @param nodo Nodo a comprobar
     * @return true si el nodo es de tipo real, false en otro caso
     */
    private boolean esReal(Nodo nodo) {
        if (nodo != null && nodo.getTipoBasico() == TipoSemantico.REAL) {
            return true;
        }
        return false;
    }

    /**
     * Comprueba que los dos nodos sean de tipo char
     * @param nodo1 Primer nodo a comprobar
     * @param nodo2 Segundo nodo a comprobar
     * @return true si los dos nodos son de tipo char y false en otro caso
     */
    private boolean sonChars(Nodo nodo1, Nodo nodo2) {
        if (nodo1 != null && nodo2 != null) {
            if ((nodo1.getTipoBasico() == TipoSemantico.CARACTER) && nodo1.getTipoBasico() == TipoSemantico.CARACTER) {
                return true;
            }
            return false;
        }
        return false;
    }

    /**
     * Comprueba que el nodo introducido sea de tipo char
     * @param nodo Nodo a comprobar
     * @return true si el nodo es de tipo chars, false en otro caso
     */
    private boolean esChar(Nodo nodo) {
        if (nodo != null && nodo.getTipoBasico().equals("CHAR")) {
            return true;
        }
        return false;
    }
}
