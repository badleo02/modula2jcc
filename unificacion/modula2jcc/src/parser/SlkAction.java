package parser;

import gestor_de_errores.GestorErrores;
import gestor_de_errores.TErrorSemantico;
import tabla_de_simbolos.TablaSimbolos;
import org.apache.log4j.Logger;
import tabla_de_simbolos.TipoSimbolo;

/**
 * Clase que implementa las acciones semanticas.
 * 
 * @author Macrogrupo 1
 */
public class SlkAction {

    // CONSTANTES
    private static final Logger logger = Logger.getLogger(SlkAction.class);    // ATRIBUTOS
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
                //AsociacionConstante();
                break;
            case 2:
                DefinicionDeTipo();
                break;
        }
    }

    private void AsociacionConstante() {

        Nodo derecha = _pilaNodos.pop(); // parte derecha, el valor
        _pilaNodos.pop();// operador
        Nodo izquierda = _pilaNodos.pop(); // parte izq, en nm de la funcion

        // comprobación de errores:
        if (derecha.getTipoBasico().equals(TipoSimbolo.Error.toString())) {
            Nodo n = new Nodo();
            n.setTipoBasico(TipoSimbolo.Error.toString());
            _pilaNodos.add(n);
        } else {

            // hay que completar el simbolo idetificado por el lexema de la parte izq
            // con tipo simbolo Constante. Tipo de la parte derecha y valor
            _tablaSimbolos.completaConstante(izquierda.getLexema(), derecha.getTipoBasico(), derecha.getLexema());
        }
    }

    private void DefinicionDeTipo() {

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
                n.setTipoBasico(TipoSimbolo.Error.toString());
                //_pilaNodos.add(n);
                _gestorDeErrores.insertaErrorSemantico(new TErrorSemantico("El simbolo <" + derecha.getLexema() + "> no es una definicion de tipo",
                        derecha.getLinea(),
                        derecha.getColumna()));
            } else // hay que completar el simbolo idetificado por el lexema de la parte izq
            // con tipo simbolo Tipo.
            {
                _tablaSimbolos.completaTipo(izquierda.getLexema(), derecha.getTipos());
            }
        }
        catch(Exception e){}
    }
}