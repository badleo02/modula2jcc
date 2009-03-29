package tabla_de_simbolos;

import java.util.ArrayList;
import org.apache.log4j.Logger;

/**
 *
 * @author Grupo 3
 */
public class Simbolo {

    // ATRIBUTOS
    /**
     * tipo semantico del identificador,
     * tipo semantico del retorno en el caso de funciones
     */
    private String _tipo;

    /**
     * Array de tipos semanticos del simbolo para simbolos compuestos.
     */
    private ArrayList<String> _tipos;

    public ArrayList<String> get_tipos() {
        return _tipos;
    }

    public void set_tipos(ArrayList<String> _tipos) {
        this._tipos = _tipos;
    }
    /**
     * lexema que identifica el simbolo
     */
    private String _lexema;

    public String get_lexema() {
        return _lexema;
    }
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
    private ArrayList _tiposArgumentos;
    /**
     * modo de paso de los argumentos en funciones y procedimientos
     */
    private ArrayList _pasoArgumentos;

    private static final Logger _logger = Logger.getLogger(Simbolo.class);

    /**
     * tipo de simbolo:
     * variable, constante, funciones y procedimientos
     */
    private TipoSimbolo _tipoSimbolo;

    public void set_tipoSimbolo(TipoSimbolo _tipoSimbolo) {
        this._tipoSimbolo = _tipoSimbolo;
    }

    public TipoSimbolo get_tipoSimbolo() {
        return _tipoSimbolo;
    }

    /**
     * Constructor por defecto de la clase Argumentos.
     */
    public Simbolo(String lexema) {
        _lexema = lexema;
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
     * Establece el tipo semantico del identificador,
     * el retorno en caso de funciones
     * 
     * @param tipo Nuevo valor a establecer.
     */
    public void setTipoSemantico(String tipo) {
        
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

    public String getLexema(){
        return(String) _valor;
    }
}
