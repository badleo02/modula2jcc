package scanner;

//***************************************************************************//
/**
 * Representa un Token lexico.
 * Almacena su tipo, linea, columna y sus atributos.
 * 
 * @author Grupo1.
 */
public class Token {
    
    // ATRIBUTOS
    public int _tipo;
    public int _linea;
    public int _columna;
    public Object _atributos;
    
//	***************************************************************************//
    /**
     * Constructor de la clase Token.
     * 
     * @param tipo El tipo del token.
     * @param linea La linea asociada al token.
     * @param columna La columna asociada al token.
     * @param atributos Los atributos del token.
     */
    public Token(int tipo, int linea, int columna, Object atributos) {

        _tipo = tipo;
        _linea = linea;
        _columna = columna;
        _atributos = atributos;
    }
//	***************************************************************************//
    /**
     * Constructor de la clase Token.
     * 
     * @param _tipo Tipo del token.
     * @param atributos Atributos del token.
     */
    public Token(int tipo, Object atributos) {

        _tipo = tipo;
        _atributos = atributos;
    }
//	***************************************************************************//
    /**
     * Constructor de la clase Token.
     * 
     * @param _tipo El _tipo del token.
     */
    public Token(int tipo) {

        _tipo = tipo;
    }
//	***************************************************************************//
    /**
     * Devuelve el campo _atributos del token transformado a String. Por defecto
     * al transformarlo a String nos devuelve [ATRIBUTO] por lo que quitamos 
     * los dos corchetes para devolver solamente ATRIBUTO. 
     * 
     * @return El campo _atributos del token transformado a String.
     */
    public String getAtributo() {

        String atribAux = _atributos.toString();
        atribAux = atribAux.toString().replace("[", " ");
        atribAux = atribAux.toString().replace("]", " ");
        atribAux = atribAux.trim();

        return atribAux;
    }
//	***************************************************************************//
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
//	***************************************************************************//
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
        if (getAtributo().matches("")) {
            string += ", NULL )";
        } else {
            string += ", " + getAtributo() + " )";
        }
        
        return string;
    }
}