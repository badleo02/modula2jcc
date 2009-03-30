package parser;

import gestor_de_errores.GestorErrores;
import gestor_de_errores.TErrorSemantico;
import java.util.ArrayList;
import scanner.TipoToken;
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
                AsociacionConstante();
                break;
            case 2:
                DefinicionDeTipo();
                break;
            case 3:
                TipoSimple_Enumerado();
                break;
            case 4:
                TipoEnumerado();
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
                marcaInicioLista();
                break;
            case 9:
                EliminarMarcaLista();
                break;
        }
    }

    private void AsociacionConstante() {
        try {
            Nodo derecha = _pilaNodos.pop(); // parte derecha, el valor

            _pilaNodos.pop();// operador

            Nodo izquierda = _pilaNodos.pop(); // parte izq, en nm de la funcion

            // comprobación de errores:
            if (derecha.getTipoBasico().equals(TipoSimbolo.Error.toString())) {
                Nodo n = new Nodo();
                n.addTipo(TipoSimbolo.Error.toString());
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
            if (derecha.esError()) {
                _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico("la definicion de tipos no es correcta",
                        derecha.getLinea(),
                        derecha.getColumna()));
            } else {
                Nodo id;
                // desapilamos identificadores hasta llegar a la marca.
                // SIEMPRE hay por lo menos uno!
                do {
                    id = _pilaNodos.pop();
                    // completamos la definicion de la variable
                    _tablaSimbolos.completaVariable(id.getLexema(), derecha.getTipos());

                } while (!_pilaNodos.isEmpty());
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
            if (!_tablaSimbolos.esDeTipo(derecha.getLexema(), TipoSimbolo.Tipo)) {
                // lo de la derecha no es un tipo
                Nodo n = new Nodo();
                n.addTipo(TipoSimbolo.Error.toString());
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

    private void EliminarMarcaLista() {
    	//REGLA: RestoListaIdentificadores:	_epsilon_ _action_EliminarMarcaLista
        
    }

    private void TipoConjunto() {
    	//REGLA TipoConjunto: SET OF TipoSimple

        try {

            Nodo n = _pilaNodos.pop();

            // si no es error completamos diciendole que es de tipo conjunto
            if (!n.esError()) {
                n.addTipo("CONJUNTO");
            }
            // Si hay un error lo propagamos
            _pilaNodos.push(n);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void TipoEnumerado() {
    	//REGLA TTipoEnumerado:\( ListaDeIdentificadores \)

        // TODO: MARCADOR EN LISTA DE VARIABLES!!!!
        try {
            Nodo n = null;
            ArrayList<String> tipoSemantico = null;

            // Desapilamos cada elemento identificador del enumerado
            // Siempre tiene que haber al menos 1!!
            do {

                n = _pilaNodos.pop(); // Identificador de Enumerado

                if (!n.esMarcador()) {
                    // Completamos el array de tipo semantico de cada identificador de enumerado
                    tipoSemantico = n.getTipos();
                    tipoSemantico.add(TipoSimbolo.ElementoEnumerado.name());
                    _tablaSimbolos.completaTipo(n.getLexema(), tipoSemantico);
                }
            } while (!n.esMarcador());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     */
    private void TipoSimple_Enumerado() {
    	
    	//REGLA: TipoSimple: TipoEnumerado

        // TODO: MARCADOR EN LISTA DE VARIABLES!!!!
        try {

            Nodo n = _pilaNodos.pop();

            // Completamos el tipo semantico del nodo indicandole que es un enumerado
            // para asignarselo a la regla superior
            ArrayList<String> tipoSemantico = n.getTipos();
            tipoSemantico.add(TipoSimbolo.Enumerado.name());
            n.setTipo(tipoSemantico);

            // Se lo pasamos a la regla superior
            _pilaNodos.push(n);
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
                n.addTipo("PUNTERO");
            }
            // Si hay un error lo propagamos
            _pilaNodos.push(n);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void marcaInicioLista() {
    	//REGLA: ListaDeIdentificadores: _action_marcaInicioLista Identificador RestoListaIdentificadores
        // añade una marca en la pila para poder desapilar la lista hasta este
        // elemento.
        Nodo n = new Nodo();
        n.creaMarcador();
        _pilaNodos.push(n);
    }
//
//    private void inicioListaVariables() {
//        try {
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * Comprueba que los dos nodos sean de tipo booleano
     * @param nodo1 Primer nodo a comprobar
     * @param nodo2 Segundo nodo a comprobar
     * @return true si los dos nodos son de tipo booleano y false en otro caso
     */
    private boolean sonBooleanos(Nodo nodo1, Nodo nodo2) {
        if (nodo1 != null && nodo2 != null) {
            if (nodo1.getTipoBasico().equals("BOOLEAN") && nodo1.getTipoBasico().equals("BOOLEAN")) {
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
        if (nodo != null && nodo.getTipoBasico().equals("BOOLEAN")) {
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
            if (nodo1.getTipoBasico().equals("INTEGER") && nodo1.getTipoBasico().equals("INTEGER")) {
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
        if (nodo != null && nodo.getTipoBasico().equals("INTEGER")) {
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
            if (nodo1.getTipoBasico().equals("REAL") && nodo1.getTipoBasico().equals("REAL")) {
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
        if (nodo != null && nodo.getTipoBasico().equals("REAL")) {
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
            if (nodo1.getTipoBasico().equals("CHAR") && nodo1.getTipoBasico().equals("CHAR")) {
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
