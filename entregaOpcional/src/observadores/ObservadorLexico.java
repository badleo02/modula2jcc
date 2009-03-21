package observadores;

//***************************************************************************//
/**
 * Interfaz que proporciona los métodos necesarios para que los observadores
 * del analizador lexico muestren la información de los eventos más importantes
 * producidos en él.
 * 
 * @author Javier Salcedo Gómez
 */
public interface ObservadorLexico {

//***************************************************************************//
    /**
     * Avisa que el léxico ha generado un token.
     * 
     * @param infoToken Información del token generado por el léxico.
     */
    public void tokenGenerado(String infoToken);
}
