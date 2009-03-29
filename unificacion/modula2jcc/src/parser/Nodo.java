package parser;

import scanner.TipoToken;
import java.util.ArrayList;
import tabla_de_simbolos.TipoSimbolo;

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
    private ArrayList<String> _tipos = null; // Tipo semántico del objeto
    private String _lexema = "";
    private int _linea;
    private int _columna;
    private boolean _marcador;

    /**
     * Constructora por defecto de la clase Nodo. Crea el array de tipos asociado.
     */
    public Nodo() {
    _marcador = false;
        _tipos = new ArrayList();
    }

    /**
     * Constructora de la clase Nodo. Crea el array de tipos asociado y actualiza
     * los atributos pasados como argumentos.
     * 
     * @param tipo Tipo semantico asociado.
     * @param valor Lexema del token.
     * @param linea Linea del token.
     * @param columna Columna del token.
     * @param tipoAdelantado Tipo adelantado que espera recibir.
     */
    public Nodo(String tipo, String valor, int linea, int columna) {
    _marcador = false;
        _tipos = new ArrayList();
       // concatRight(tipo);
        _tipos.add(tipo);
        setValor(valor);
        setLinea(linea);
        setColumna(columna);
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
     * Devuelve el valor del objeto.
     * 
     * @return El valor del objeto.
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
     * Establece el valor de la columna a valor <b>columna</b>.
     *
     * @param _columna Nuevo valor a establecer.
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
     * Establece la fila del nodo a valor <b>fila</b>.
     *
     * @param fila Nuevo valor a establecer.
     */
    public void setLinea(int fila) {

        _linea = fila;
    }

    /**
     * Establece el valor del objeto a <b>valor</b>.
     * 
     * @param valor Nuevo valor a establecer.
     */
    public void setValor(String valor) {

        _lexema = valor;
    }

    /**
     * Establece el tipo semantico del objeto.
     * 
     * @param tipos Tipo Nuevo valor a establecer.
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
     * comprueba si estamos hablando de un error
     * @return si es un error.
     */
    public boolean esError(){
       return  getTipoBasico().equals(TipoSimbolo.Error.toString());
    }

    public void creaMarcador(){
        _marcador = true;
    }

    public boolean esMarcador(){
        return  _marcador;
    }
}
