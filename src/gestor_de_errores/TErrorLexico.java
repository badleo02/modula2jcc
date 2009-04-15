package gestor_de_errores;

/**
 * Clase que se encarga de gestionar los errores producidos durante el analisis 
 * lexico.
 * 
 * @author Grupo1, Javier Salcedo Gomez
 */
public class TErrorLexico {
    
    // ATRIBUTOS
    private ErroresLexicos _error;
    private int _linea;
    private int _columna;
    private String _mensaje;

    /**
     * Constructor de la clase TErrorLexico.
     * 
     * @param error El _tipo de error de la clase enum ErroresLexicos.
     * @param _linea La fila donde se ha producido el error. 
     * @param _columna La _columna donde se ha producido el error.
     * @param mensaje Mensaje informativo asociado.
     */
    public TErrorLexico(ErroresLexicos error, String mensaje, int fila, int columna) {

        _error = error;
        _linea = fila;
        _columna = columna;
        _mensaje = mensaje;
    }

    /**
     * Devuelve el numero de la _columna donde el error se ha producido.
     * 
     * @return El numero de la _columna donde el error se ha producido.
     */
    public int getColumna() {

        return _columna;
    }

    /**
     * Establece el numero de la _columna para el error.
     * 
     * @param _columna El numero de la _columna.
     */
    public void setColumna(int columna) {

        _columna = columna;
    }

    /**
     * Devuelve el numero de linea donde se ha producido el error.
     * 
     * @return El numero de linea donde se ha producido el error.
     */
    public int getFila() {

        return _linea;
    }

    /**
     * Establece el numero de la linea para el error.
     * 
     * @param _linea Linea en la que se ha producido el error.
     */
    public void setFila(int linea) {

        _linea = linea;
    }

    /**
     * Devuelve el _tipo de error producido.
     * 
     * @return El _tipo de error producido.
     */
    public ErroresLexicos getError() {

        return _error;
    }

    /**
     * Establece el _tipo de error producido.
     * 
     * @param _error El _tipo del error producido.
     */
    public void setError(ErroresLexicos error) {

        _error = error;
    }

    /**
     * Transforma la clase TErrorLexico en un String para presentarlo por 
     * pantalla.
     * 
     * @return String correspondiente a la clase TErrorLexico.
     */
    @Override
    public String toString() {

        String error = new String();
        error += "\n Linea: ";
        error += _linea;
        error += ", Columna: ";
        error += _columna;
        error += ":\n ";
        error += _error.toString().replace("_", " ");
        error += " : ";
        error += _mensaje;
        error += " \n";
        return error;
    }
}
