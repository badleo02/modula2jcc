package tabla_de_simbolos;

import java.util.ArrayList;
import org.apache.log4j.Logger;

/**
 *
 * @author Grupo 3
 */
public class Argumentos {

    // ATRIBUTOS
    private String _tipo;
    private Object _valor;
    private int _numArgs;
    private TablaSimbolos _contenido;
    private ArrayList _tiposArgumentos;
    private ArrayList _pasoArgumentos;
    private static final Logger _logger = Logger.getLogger(Argumentos.class);

    /**
     * Constructor por defecto de la clase Argumentos.
     */
    public Argumentos() {
        
        _contenido = null;
    }

    /**
     * Constructor de la clase Argumentos.
     * 
     * @param contenido Contenido de los argumentos.
     */
    public Argumentos(TablaSimbolos contenido) {
        
        _contenido = contenido;
        _logger.debug("Creando la tabla con puntero  " + contenido.getNombre());
    }

    /**
     * Devuelve el contenido de los argumentos.
     * 
     * @return El contenido de los argumentos.
     */
    public TablaSimbolos getContenido() {
        
        return _contenido;
    }

    /**
     * Devuelve el numero de argumentos.
     * 
     * @return El numero de argumentos.
     */
    public int getNumArgs() {
        
        return _numArgs;
    }

    /**
     * Devuelve el paso de argumentos.
     * 
     * @return El paso de argumentos.
     */
    public ArrayList getPasoArgumentos() {
        
        return _pasoArgumentos;
    }

    /**
     * Devuelve el tipo de los argumentos.
     * 
     * @return El tipo de los argumentos.
     */
    public String getTipo() {
        
        return _tipo;
    }

    /**
     * Devuelve los tipos de los argumentos.
     * 
     * @return Los tipos de los argumentos.
     */
    public ArrayList getTipoArgumentos() {
       
        return _tiposArgumentos;
    }

    /**
     * Devuelve el valor de los argumentos.
     * 
     * @return El valor de los argumentos.
     */
    public Object getValor() {
        
        return _valor;
    }

    /**
     * Establece el numero de argumentos a valor <b>numArgs</b>.
     * 
     * @param numArgs Nuevo valor a establecer.
     */
    public void setNumArgs(int numArgs) {
        
        _numArgs = numArgs;
    }

    /**
     * Establece el paso de argumentos a valor <b>pasoArgumentos</b>.
     * 
     * @param pasoArgumentos Nuevo valor a establecer.
     */
    public void setPasoArgumentos(ArrayList pasoArgumentos) {
        
        _pasoArgumentos = pasoArgumentos;
    }

    /**
     * Establece el tipo los de argumentos a valor <b>tipo</b>.
     * 
     * @param tipo Nuevo valor a establecer.
     */
    public void setTipo(String tipo) {
        
        _tipo = tipo;
    }
    
    /**
     * Establece el tipo de los argumentos a valor <b>tipoArgumentos</b>.
     * 
     * @param tipoArgumentos Nuevo valor a establecer.
     */
    public void setTipoArgumentos(ArrayList tipoArgumentos) {
        
        _tiposArgumentos = tipoArgumentos;
    }
    
    /**
     * Establece el valor de los argumentos a valor <b>valor</b>.
     * 
     * @param valor Nuevo valor a establecer.
     */
    public void setValor(Object valor) {
        
        _valor = valor;
    }
    
    /**
     * Establece el contenido de los argumentos a valor <b>contenido</b>.
     * 
     * @param contenido Nuevo valor a establecer.
     */
    public void setContenido(TablaSimbolos contenido) {
        
        _contenido = contenido;
    }
}
