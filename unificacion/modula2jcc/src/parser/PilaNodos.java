/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import java.util.Stack;

/**
 * Pila que almacena los nodos del arbol sintactico anotado.
 * 
 * @author Grupo 3
 */
public class PilaNodos extends Stack<Nodo> {

    // ATRIBUTOS
//    private static PilaNodos _pilaNodos = null;
    private boolean _parteDeclarativa = true;
    
    /**
     * Devuelve la instancia unica de la clase PilaNodos.
     * 
     * @return La instancia unica de la clase PilaNodos.
     */
//    public static PilaNodos getInstancia() {
//
//        if (_pilaNodos == null)
//            _pilaNodos = new PilaNodos();
//
//        return _pilaNodos;
//    }

    /**
     * Pone a true la variable que indica si estamos en parte declarativa
     */
    public void activarParteDeclarativa(){
        
        _parteDeclarativa = true;
    }

    /**
     * Pone a false la variable que indica si estamos en parte declarativa
     */
    public void desactivarParteDeclarativa(){
        
        _parteDeclarativa = false;
    }

    /**
     * Nos dice si estamos en parte declarativa o no.
     * 
     * @return Verdadero si estamos en la parte declarativa y falso en caso contrario.
     */
    public boolean esParteDeclarativa(){
        
        return _parteDeclarativa;
    }
}
