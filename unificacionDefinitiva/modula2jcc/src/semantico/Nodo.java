package semantico;

import java.util.ArrayList;
import scanner.TipoToken;

/**
 *
 * La clase Nodo contiene los atributos heredados y/o sintetizados
 * de cada una de los símbolos de producción.
 *
 * @author Grupo 3, Grupo 11.
 */
public class Nodo {
    
    /**
     * Tipo semantico del nodo
     */
    private ArrayList<TipoSemantico> _tipoSemantico = null;     
    /**
     * Nodo marcador para las listas de identificadores
     */
    private boolean _marcaListaIdentificadores;
    private String _lexema;
    private int _linea;
    private int _columna;
    private TipoToken _tipoToken;
    
    /**
     * Constructora por defecto de la clase Nodo. Crea el array de tipos asociado.
     */
    public Nodo() {

        _marcaListaIdentificadores = false;
        _tipoSemantico = new ArrayList<TipoSemantico>();
    }

    /**
     * Constructora de la clase Nodo. Crea el array de tipos asociado y actualiza
     * los atributos pasados como argumentos.
     * 
     * @param lexema Lexema asociado al nodo.
     * @param linea Linea asociada al nodo.
     * @param columna Columna asociada al nodo.
     * @param tipoToken Tipo de token asociado al nodo.
     */
    public Nodo(String lexema, int linea, int columna, TipoToken tipoToken) {
        
        _lexema = lexema;
        _linea = linea;
        _columna = columna;
        _tipoToken = tipoToken;
   
        _tipoSemantico = new ArrayList<TipoSemantico>();
        _marcaListaIdentificadores = false;
    }
  
    /**
     * Devuelve el tipo semantico del objeto.
     * 
     * @return El tipo semantico del objeto.
     */
    public ArrayList<TipoSemantico> getTipoSemantico() {

        return _tipoSemantico;
    }

    /**
     * Establece el tipo semantico del objeto.
     * 
     * @param tipos Tipo Nuevo lexema a establecer.
     */
    public void setTipo(ArrayList tipos) {

        _tipoSemantico = tipos;
    }

    /**
     * Devuelve el primer simbolo del array de tipos semanticos.
     * 
     * @return El primer simbolo del array de tipos semanticos.
     */
    public TipoSemantico getTipoBasico() {

        return (TipoSemantico) _tipoSemantico.get(0);
    }

    /**
     * Añade por la derecha el tipo en el array de tipos.
     * 
     * @param tipo Tipo a concatenar.
     */
    public void addTipo(TipoSemantico tipo) {

        if (_tipoSemantico == null) {
            _tipoSemantico = new ArrayList();
        }
        _tipoSemantico.add(tipo);
    }

    /**
     * Devuelve el string correspondiente a la clase Nodo.
     * 
     * @return El string correspondiente a la clase Nodo.
     */
    @Override
    public String toString() {

        StringBuilder trace = new StringBuilder("Nodo{tipo: ");
        trace.append(_tipoSemantico);
        trace.append("; tipo.size: ");

        if (_tipoSemantico != null) {
            trace.append(_tipoSemantico.size());
        } else {
            trace.append("0");
        }
        trace.append("}");
        return trace.toString();
    }

    /**
     * comprueba si es un nodo error. Un nodo es error si tiene
     * error en cualquier posicion de tipo semantico de su 
     * arraylist.
     * 
     * @return si es un error.
     */
    public boolean esError() {
        return _tipoSemantico.contains(TipoSemantico.ERROR.name());
    }

    /**
     * Crea un nodo marcador. Establece el atributo _marcaListaIdentificadores a true.
     * Con esto es con lo que distinguimos el comienzo de la 
     * declaracion de una lista de variables.
     */
    public void crearMarcaListaIdentificadores() {

        _marcaListaIdentificadores = true;
    }

    /**
     * Devuelve verdadero si es un nodo de marca de inicio de lista
     * de identificadores y falso en caso contrario.
     * 
     * @return Verdadero si es un nodo de marca de inicio de lista
     * de identificadores y falso en caso contrario.
     */
    public boolean esMarcaListaIdentificadores() {
        
        return _marcaListaIdentificadores;
    }
    
    /**
     * Devuelve el campo _lexema del token.
     * 
     * @return El campo lexema del token.
     */
    public String getLexema() {

        return _lexema;
    }
    
    /**
     * Devuelve la columna asociada al token.
     * 
     * @return La columna asociada al token.
     */
    public int getColumna() {
        
        return _columna;
    }

    /**
     * Devuelve la linea asociada al token.
     * 
     * @return La linea asociada al token.
     */
    public int getLinea() {
        
        return _linea;
    }

    /**
     * Devuelve el TipoToken correspondiente al entero que representa el _tipo.
     * 
     * @return El TipoToken correspondiente al entero que representa el _tipo.
     */
    public TipoToken getTipoToken() {

        return _tipoToken;
    }
}
