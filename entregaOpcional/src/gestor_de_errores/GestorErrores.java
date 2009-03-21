package gestor_de_errores;

import java.util.ArrayList;

//***************************************************************************//
/**
 * Clase que se encarga de gestionar todos los errores propios de la fase 
 * de analisis (lexico, sintactico, semantico) de nuestro compilador del 
 * lenguaje de Modula-2.
 * 
 * @author Grupo1, Javier Salcedo Gomez
 */
public class GestorErrores {
    
    // ATRIBUTOS
    private ArrayList<TErrorLexico> _listaErroresLexicos;
    private ArrayList<TErrorSintactico> _listaErroresSintacticos;
    private ArrayList<TErrorSemantico> _listaErroresSemanticos;
    private boolean _flagErroresLexicos;
    private boolean _flagErroresSintacticos;
    private boolean _flagErroresSemanticos;

    /**
     * Constructor de la clase GestorErrores.
     * Crea las listas de errores correspondientes a cada análisis y establece
     * el flag para mostrar los errores a falso (porque no hay errores que 
     * mostrar por el momento).
     */
    public GestorErrores() {

        _listaErroresLexicos = new ArrayList<TErrorLexico>();
        _listaErroresSintacticos = new ArrayList<TErrorSintactico>();
        _listaErroresSemanticos = new ArrayList<TErrorSemantico>();
        setFlagErroresLexicos(false);
        setFlagErroresSintacticos(false);
        setFlagErroresSemanticos(false);
    }

    /**
     * Devuelve la instancia de la clase.
     * 
     * @return La instancia de la clase GestorErrores.
     */
    public static GestorErrores getInstancia() {

        return new GestorErrores();
    }

    /**
     * Devuelve la lista de errores léxicos producidos durante el analisis 
     * lexico.
     * 
     * @return La lista de errores léxicos producidos durante el analisis 
     * lexico.
     */
    public ArrayList<TErrorLexico> getErroresLexicos() {

        return _listaErroresLexicos;
    }

    /**
     * Reinicia los flag y las listas de errores.
     */
    public void reiniciar() {
        
        _listaErroresLexicos.clear();
        _listaErroresSintacticos.clear();
        _listaErroresSemanticos.clear();
        setFlagErroresLexicos(false);
        setFlagErroresSintacticos(false);
        setFlagErroresSemanticos(false);
    }

    /**
     * Establece la lista de errores lexicos a valor <b>errores</b>.
     * 
     * @param errores La lista de errores lexicos.
     */
    public void setErroresLexicos(ArrayList<TErrorLexico> errores) {

        _listaErroresLexicos = errores;
    }

    /**
     * Devuelve la lista de errores sintacticos producidos durante el analisis 
     * sintactico.
     * 
     * @return La lista de errores sintacticos producidos durante el analisis 
     * sintactico.
     */
    public ArrayList<TErrorSintactico> getErroresSintacticos() {

        return _listaErroresSintacticos;
    }

    /**
     * Establece la lista de errores sintacticos a valor <b>errores</b>.
     * 
     * @param errores La lista de errores sintacticos.
     */
    public void setErroresSintacticos(ArrayList<TErrorSintactico> errores) {

        _listaErroresSintacticos = errores;
    }

    /**
     * Inserta un error de _tipo lexico.
     * 
     * @param error El error lexico. 
     */
    public void insertaErrorLexico(TErrorLexico error) {

        _listaErroresLexicos.add(error);
        setFlagErroresLexicos(true);
    }

    /**
     * Inserta un error de _tipo sintactico.
     * 
     * @param error El error sintactico. 
     */
    public void insertaErrorSintactico(TErrorSintactico error) {

        _listaErroresSintacticos.add(error);
        setFlagErroresSintacticos(true);
    }

    /**
     * Inserta un error de _tipo semantico.
     *
     * @param error El error semantico.
     */
    public void insertaErrorSemantico(TErrorSemantico error) {

        _listaErroresSemanticos.add(error);
        setFlagErroresSemanticos(true);
    }

    /**
     * Devuelve un String con todos los errores lexicos que se han producido 
     * durante el análisis.
     * 
     * @return Un String con todos los errores lexicos que se han producido 
     * durante el análisis.
     */
    public String dameErroresLexicos(){
    
        String salida = new String();
        salida = "";
        
        TErrorLexico listaLexicos;
        
        if (_flagErroresLexicos) {
        
            salida = salida + "\n  Errores Lexicos:\n";        
            salida = salida + " ------------------\n";

            for (int i = 0; i < _listaErroresLexicos.size(); i++) {
           
                listaLexicos = _listaErroresLexicos.get(i);
                salida = salida + listaLexicos.toString();
            }
            
            return salida;
        }
        else
            return salida = salida + "\n --> No hay Errores Lexicos.";
    }
    
    /**
     * Devuelve un String con todos los errores sintacticos que se han producido 
     * durante el análisis.
     * 
     * @return Un String con todos los errores sintacticos que se han producido 
     * durante el análisis.
     */
    public String dameErroresSintacticos(){
    
        String salida = new String();
        salida = "";
        TErrorSintactico listaSintacticos;
        
        if (_flagErroresSintacticos) {
        
            salida = salida + "\n  Errores Sintacticos: \n";        
            salida = salida + " ----------------------\n";

            for (int i = 0; i < _listaErroresSintacticos.size(); i++) {
           
                listaSintacticos = _listaErroresSintacticos.get(i);
                salida = salida + listaSintacticos.toString();
            }
            
            return salida;
        }
        else
            return salida = salida + "\n --> No hay Errores Sintacticos.";
    }
    
    /**
     * Devuelve un String con todos los errores semanticos que se han producido 
     * durante el análisis.
     * 
     * @return Un String con todos los errores semanticos que se han producido 
     * durante el análisis.
     */
    public String dameErroresSemanticos(){
    
        String salida = new String();
        salida = "";
        TErrorSemantico listaSemanticos;

        if (_flagErroresSemanticos) {

            salida = salida + "\n  Errores Semanticos: \n";
            salida = salida + " ----------------------\n";

            for (int i = 0; i < _listaErroresSemanticos.size(); i++) {

                listaSemanticos = _listaErroresSemanticos.get(i);
                salida = salida + listaSemanticos.toString();
            }

            return salida;
        }
        else
            return salida = salida + "\n --> No hay Errores Semanticos.";
    }
    
    /**
     * Establece el flag de mostrar errores lexicos a valor <b>valor</b>.
     * 
     * @param valor Nuevo valor a establecer.
     */
    private void setFlagErroresLexicos(boolean valor) {

        _flagErroresLexicos = valor;
    }

    private void setFlagErroresSemanticos(boolean valor) {
        _flagErroresSemanticos = valor;
    }

    /**
     * Establece el flag de mostrar errores sintacticos a valor <b>valor</b>.
     * 
     * @param valor Nuevo valor a establecer.
     */
    private void setFlagErroresSintacticos(boolean valor) {
        
        _flagErroresSintacticos = valor;
    }
}
