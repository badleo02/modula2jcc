package observadores;

import scanner.TipoToken;

/**
 * Interfaz que proporciona los métodos necesarios para que los observadores
 * del analizador lexico muestren la información de los eventos más importantes
 * producidos en él.
 * 
 * @author Javier Salcedo Gómez
 */
public interface ObservadorLexico {

    /**
     * Avisa que el léxico ha generado un token.
     * 
     * @param linea Linea asociada al token.
     * @param columna Columna asociada al token.
     * @param tipo Tipo asociado al token.
     * @param lexema Lexema asociado al token.
     */
    public void tokenGenerado(int linea, int columna, TipoToken tipo, String lexema);
}
