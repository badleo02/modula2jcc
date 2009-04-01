package semantico;

import java.util.ArrayList;
import scanner.TipoToken;

/**
 *
 * La clase Nodo contiene los atributos heredados y/o sintetizados
 * de cada una de los símbolos de producción.
 * 
 * Si necesitas un nuevo atributo, agrégalo pero pon un comentario
 * indicando claramente para qué sirve. Quizá otra persona necesite
 * hacer uso del mismo.
 *
 * @author Grupo 3
 */
public class Nodo {
    // ATRIBUTOS
    /**
     * Tipo semantico del nodo
     */
    private ArrayList<String> _tipos = null; // Tipo semántico del objeto
    /**
     * Lexema del nodo
     */
    private String _lexema = "";
    /**
     * La linea asociada al nodo
     */
    private int _linea;
    /**
     * La columna asociada al nodo
     */
    private int _columna;
    /**
     * Nodo marcador para las listas de identificadores
     */
    private boolean _inicioListaIdentificadores;
    public TipoToken _tipoToken;

    /**
     * Constructora por defecto de la clase Nodo. Crea el array de tipos asociado.
     */
    public Nodo() {

        _inicioListaIdentificadores = false;
        _tipos = new ArrayList();
    }

    /**
     * Constructora de la clase Nodo. Crea el array de tipos asociado y actualiza
     * los atributos pasados como argumentos.
     * 
     * @param tipo Tipo semantico asociado.
     * @param lexema Lexema del token.
     * @param linea Linea del token.
     * @param columna Columna del token.
     * @param tipoAdelantado Tipo adelantado que espera recibir.
     */
    public Nodo(String lexema, int linea, int columna) {

        _tipos = new ArrayList();
        setLexema(lexema);
        setLinea(linea);
        setColumna(columna);
        _inicioListaIdentificadores = false;
    }

    public Nodo(TipoToken tipoToken, String lexema, int linea, int columna) {
    
        _tipoToken = tipoToken;
        _tipos = new ArrayList();
        setLexema(lexema);
        setLinea(linea);
        setColumna(columna);
        _inicioListaIdentificadores = false;
    }

    /**
     * Devuelve el tipo semantico del objeto.
     * 
     * @return El tipo semantico del objeto.
     */
    public ArrayList<String> getTipos() {

        return _tipos;
    }

    /**
     * Devuelve el primer simbolo del array de tipos semanticos.
     * 
     * @return El primer simbolo del array de tipos semanticos.
     */
    public String getTipoBasico() {

        return (String) _tipos.get(0);
    }

    /**
     * Devuelve el lexema del objeto.
     * 
     * @return El lexema del objeto.
     */
    public String getLexema() {

        return _lexema;
    }

    /**
     * Devuelve la columna del token del nodo.
     *
     * @return La columna del token del nodo.
     */
    public int getColumna() {

        return _columna;
    }

    /**
     * Establece el lexema de la columna a lexema <b>columna</b>.
     *
     * @param _columna Nuevo lexema a establecer.
     */
    public void setColumna(int columna) {

        _columna = columna;
    }

    /**
     * Devuelve la fila del token del nodo.
     *
     * @return La fila del token del nodo.
     */
    public int getLinea() {

        return _linea;
    }

    /**
     * Establece la fila del nodo a lexema <b>fila</b>.
     *
     * @param fila Nuevo lexema a establecer.
     */
    public void setLinea(int fila) {

        _linea = fila;
    }

    /**
     * Establece el lexema del objeto a <b>lexema</b>.
     * 
     * @param lexema Nuevo lexema a establecer.
     */
    public void setLexema(String valor) {

        _lexema = valor;
    }

    /**
     * Establece el tipo semantico del objeto.
     * 
     * @param tipos Tipo Nuevo lexema a establecer.
     */
    public void setTipo(ArrayList tipos) {

        _tipos = tipos;
    }

    /**
     * Añade por la derecha el tipo en el array de tipos.
     * 
     * @param tipo Tipo a concatenar.
     */
    public void addTipo(String tipo) {

        if (_tipos == null) {
            _tipos = new ArrayList();
        }
        _tipos.add(tipo);
    }

    /**
     * Devuelve el string correspondiente a la clase Nodo.
     * 
     * @return El string correspondiente a la clase Nodo.
     */
    @Override
    public String toString() {

        StringBuilder trace = new StringBuilder("Nodo{tipo: ");
        trace.append(_tipos);
        trace.append("; tipo.size: ");

        if (_tipos != null) {
            trace.append(_tipos.size());
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
        return _tipos.contains(TipoSemantico.ERROR.name());
    }

    /**
     * Crea un nodo marcador. Establece el atributo _inicioListaIdentificadores a true.
     * Con esto es con lo que distinguimos el comienzo de la 
     * declaracion de una lista de variables.
     */
    public void creaInicioListaIdentificadores() {

        _inicioListaIdentificadores = true;
    }

    /**
     * Devuelve verdadero si es un nodo de marca de inicio de lista
     * de identificadores y falso en caso contrario.
     * 
     * @return Verdadero si es un nodo de marca de inicio de lista
     * de identificadores y falso en caso contrario.
     */
    public boolean esInicioListaIdentificadores() {
        return _inicioListaIdentificadores;
    }
}
