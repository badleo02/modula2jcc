package scanner;

/**
 * Representa un Token lexico. Se compone de tipo, lexema, linea y columna.
 * 
 * @author Grupo1, Javier Salcedo Gomez
 */
public class Token {
    
    // ATRIBUTOS
    private int _tipo;
    private int _linea;
    private int _columna;
    private Object _lexema;
    
    /**
     * Constructor de la clase Token.
     * 
     * @param tipo El tipo del token.
     * @param linea La linea asociada al token.
     * @param columna La columna asociada al token.
     * @param lexema El lexema del token.
     */
    public Token(int tipo, int linea, int columna, Object lexema) {

        _tipo = tipo;
        _linea = linea;
        _columna = columna;
        _lexema = lexema;
    }

    /**
     * Constructor de la clase Token.
     * 
     * @param _tipo Tipo del token.
     * @param lexema Lexema del token.
     */
    public Token(int tipo, Object lexema) {

        _tipo = tipo;
        _lexema = lexema;
    }

    /**
     * Constructor de la clase Token.
     * 
     * @param tipo El tipo del token.
     */
    public Token(int tipo) {

        _tipo = tipo;
    }

    /**
     * Devuelve el campo _lexema del token transformado a String. Por defecto
     * al transformarlo a String nos devuelve [ATRIBUTO] por lo que quitamos 
     * los dos corchetes para devolver solamente ATRIBUTO. 
     * 
     * @return El campo lexema del token transformado a String.
     */
    public String getLexema() {

        String atribAux = _lexema.toString();
        atribAux = atribAux.toString().replace("[", " ");
        atribAux = atribAux.toString().replace("]", " ");
        atribAux = atribAux.trim();

        return atribAux;
    }

    /**
     * Establece el campo lexema a valor <b>lexema</b>.
     * 
     * @param lexema Nuevo valor a establecer.
     */
    public void setLexema(Object lexema) {
        _lexema = lexema;
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
     * Establece el numero de columna a valor <b>columna</b>.
     * 
     * @param columna Nuevo valor a establecer.
     */
    public void setColumna(int columna) {
        _columna = columna;
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
     * Establece el numero de linea a valor <b>linea</b>.
     * 
     * @param linea Nuevo valor a establecer.
     */
    public void setLinea(int linea) {
        _linea = linea;
    }

    /**
     * Devuelve el entero correspondiente al tipo token.
     * 
     * @return El entero correspondiente al tipo token.
     */
    public int getTipo() {
        return _tipo;
    }

    /**
     * Establece el entero de tipo a valor <b>tipo</b>.
     * 
     * @param tipo Nuevo valor a establecer.
     */
    public void setTipo(int tipo) {
        _tipo = tipo;
    }
    
        /**
     * Devuelve el TipoToken correspondiente al entero que representa el _tipo.
     * 
     * @return El TipoToken correspondiente al entero que representa el _tipo.
     */
    public TipoToken getTipoToken() {

        switch (_tipo) {

            case 0:
                return TipoToken.EOF;
            case 1:
                return TipoToken.PALABRA_RESERVADA;
            case 2:
                return TipoToken.IDENTIFICADOR;
            case 3:
                return TipoToken.NUMERO_ENTERO;
            case 4:
                return TipoToken.NUMERO_REAL;
            case 5:
                return TipoToken.CARACTER;
            case 6:
                return TipoToken.CADENA;
            case 7:
                return TipoToken.OPERADOR_SUMADOR;
            case 8:
                return TipoToken.OPERADOR_MULTIPLICADOR;
            case 9:
                return TipoToken.OPERADOR_UNITARIO;
            case 10:
                return TipoToken.PUNTUACION;
            case 11:
                return TipoToken.OPERADOR_COMPARADOR;
            case 12:
                return TipoToken.OPERADOR_ASIGNACION;
            case 13:
                return TipoToken.TIPO_SIMPLE;
            case 14:
                return TipoToken.CONSTANTE_PREDEFINIDA;
            case 15:
                return TipoToken.FUNCION_PREDEFINIDA;
            case 16:
                return TipoToken.PROCEDIMIENTO_PREDEFINIDO;
        }

        return null;
    }
    
    /**
     * Muestra la informacion de un token como un String.
     * 
     * @return El String correspondiente que representa la informacion del token.
     */
    @Override
    public String toString() {

        String string = new String();
        string += " Linea: " + _linea;
        string += ", Columna: " + _columna;
        
        string += " -> ( " + TipoToken.values()[_tipo];
        if (getLexema().matches("")) {
            string += ", NULL )";
        } else {
            string += ", " + getLexema() + " )";
        }
        
        return string;
    }
}