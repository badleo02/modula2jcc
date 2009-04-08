package main;

import gestor_de_errores.GestorErrores;
import gui.Ventana;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import semantico.PilaNodos;
import tabla_de_simbolos.TablaDeSimbolos;

/**
 * Clase principal del compilador.
 * 
 * @author Javier Salcedo Gómez
 */
public class Main {

    // ATRIBUTOS
    private static Ventana _ventana;
    private static GestorErrores _gestorDeErrores;
    private static TablaDeSimbolos _tablaDeSimbolos;
    private static PilaNodos _pilaNodos;
    
    /**
     * Metodo main de la aplicacion.
     * 
     * @param args Argumentos de la linea de comandos
     */
    public static void main(String args[]) {
        try {

            // Interfaz Nimbus
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        
            // Creamos el gestor de errores
            _gestorDeErrores = new GestorErrores();

            // Creamos la TABLA GLOBAL de simbolos
            _tablaDeSimbolos = new TablaDeSimbolos();

            // Creamos la pila para el analisis semantico
            _pilaNodos = new PilaNodos();
            
            // Creamos la ventana de la aplicacion
            _ventana = new Ventana(_gestorDeErrores, _tablaDeSimbolos, _pilaNodos);
            _ventana.setVisible(true);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}