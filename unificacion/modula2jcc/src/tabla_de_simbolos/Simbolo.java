package tabla_de_simbolos;

import java.util.ArrayList;
import org.apache.log4j.Logger;
import semantico.TipoSemantico;

/**
 *
 * @author Grupo 3
 */
public class Simbolo {

    // ATRIBUTOS
    /**
     * Array de tipos semanticos del simbolo para simbolos compuestos.
     */
    private ArrayList<TipoSemantico> _tipoSemantico;

    /**
     * valor en el caso de las constantes
     */
    private String _valor;
    /**
     * numero de argumentos para funciones y procedimientos
     */
    private int _numArgs;
    /**
     * ambito anidado para funciones y procedimientos
     */
    private TablaSimbolos _contenido;
    /**
     * tipo semantico de los argumentos funciones y procedimientos
     */
    private ArrayList<ArrayList<TipoSemantico>> _tiposArgumentos;
    /**
     * modo de paso de los argumentos en funciones y procedimientos
     */
    private ArrayList _pasoArgumentos;
    /**
     * tipo de simbolo:
     * variable, constante, funcion,  procedimiento...
     */
    private TipoSimbolo _tipoSimbolo;
    /**
     * Logger de la aplicacion
     */
    private static final Logger _logger = Logger.getLogger(Simbolo.class);

    /**
     * Constructor por defecto de la clase Argumentos.
     */
    public Simbolo() {
        _contenido = null;
    }

    /**
     * Constructor de la clase Argumentos.
     * 
     * @param contenido Contenido de los argumentos.
     */
    public Simbolo(TablaSimbolos contenido) {

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
    public void setValor(String valor) {

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

    /**
     * Devuelve el lexema del simbolo.
     * 
     * @return El lexema del simbolo.
     */
    public String getLexema() {

        return (String) _valor;
    }

    /**
     * Devuelve el tipo semantico del simbolo.
     * 
     * @return El tipo semantico del simbolo.
     */
    public ArrayList<TipoSemantico> getTipos() {
        return _tipoSemantico;
    }

    /**
     * Establece el valor del tipo semantico a valor <b>tipos</b>.
     * 
     * @param tipos Nuevo valor a establecer.
     */
    public void setTipos(ArrayList<TipoSemantico> tipos) {
        
        _tipoSemantico = tipos;
    }

    /**
     * Establece el tipo semantico del simbolo a valor <b>tipoSimbolo</b>.
     * 
     * @param tipoSimbolo Nuevo valor a establecer.
     */
    public void setTipoSimbolo(TipoSimbolo tipoSimbolo) {
        
        _tipoSimbolo = tipoSimbolo;
    }

    /**
     * Devuelve el tipo de simbolo del simbolo.
     * 
     * @return El tipo de simbolo del simbolo.
     */
    public TipoSimbolo getTipoSimbolo() {
        
        return _tipoSimbolo;
    }
}
