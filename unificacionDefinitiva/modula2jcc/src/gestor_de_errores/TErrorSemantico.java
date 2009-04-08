package gestor_de_errores;

/**
 * Clase que se encarga de gestionar los errores producidos durante el analisis
 * semantico.
 * 
 * @author Javier Salcedo Gomez
 */
public class TErrorSemantico {

    // ATRIBUTOS
    private String _mensaje;
    private int _linea;
    private int _columna;

    /**
     * Constructor de la clase TErrorSemantico.
     *
     * @param mensaje Mensaje de error asociado.
     * @param _linea Linea donde se ha producido el error.
     * @param _columna Columna donde se ha producido el error.
     */
    public TErrorSemantico(String mensaje, int linea, int columna) {

        _mensaje = mensaje;
        _linea = linea;
        _columna = columna;
    }

    /**
     * Devuelve el mensaje de error asociado al error Semantico.
     *
     * @return El mensaje de error asociado al error sintatico.
     */
    public String getMensaje() {

        return _mensaje;
    }

    /**
     * Establece el mensaje de error asociado a valor mensaje.
     *
     * @param mensaje Nuevo valor a establecer.
     */
    public void setMensaje(String mensaje) {

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
     * Transforma la clase TErrorSemantico en un String para presentarlo por
     * pantalla.
     *
     * @return String correspondiente a la clase TErrorSemantico.
     */
    @Override
    public String toString() {

        String error = "Error Semantico -> Linea: " + _linea;
        error += ", Columna: " + _columna + " : " + _mensaje + "\n";
        return error;
    }
}
