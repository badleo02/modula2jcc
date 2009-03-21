package main;

import gestor_de_errores.GestorErrores;
import gui.Ventana;
import parser.PilaNodos;
import tabla_de_simbolos.TablaSimbolos;

//***************************************************************************//
/**
 * Clase principal del compilador.
 * 
 * @author Javier Salcedo Gómez
 */
public class Main {

    // ATRIBUTOS
    private static Ventana _ventana;
    private static GestorErrores _gestorDeErrores;
    private static TablaSimbolos _tablaSimbolos;
    private static PilaNodos _pilaNodos;
    
//***************************************************************************//
    /**
     * Metodo main de la aplicacion.
     * 
     * @param args Argumentos de la linea de comandos
     */
    public static void main(String args[]) {

        // Creamos el gestor de errores
        _gestorDeErrores = new GestorErrores();

        // Creamos la tabla de simbolos
        _tablaSimbolos = new TablaSimbolos();

        _pilaNodos = new PilaNodos();

        // Creamos la ventana de la aplicacion
        _ventana = new Ventana(_gestorDeErrores,_tablaSimbolos, _pilaNodos);
        _ventana.setVisible(true);
    }
}