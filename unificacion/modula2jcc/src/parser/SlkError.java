package parser;

import gestor_de_errores.GestorErrores;
import gestor_de_errores.TErrorSintactico;

/**
 * Clase que gestiona los errores cometidos durante el analisis sintactico 
 * llevado a cabo por la clase SlkParser.
 * 
 * @author Javier Salcedo Gomez
 */
public class SlkError {

    // ATRIBUTOS
    private SlkToken _tokens;
    @SuppressWarnings("unused")
    private SlkLog _log;
    private GestorErrores _gestorDeErrores;

    /**
     * Constructor de la clase SlkError.
     * 
     * @param tokens Tokens leidos por el Scanner.
     * @param log Registro de las acciones aplicadas.
     */
    public SlkError(SlkToken tokens, SlkLog log, GestorErrores gestor) {

        _tokens = tokens;
        _log = log;
        _gestorDeErrores = gestor;
    }

    /**
     * El token de entrada no se corresponde con el simbolo de la tabla. 
     * 
     * @param terminal El terminal analizado.
     * @param token Token del analizador lexico.
     * 
     * @return El token asociado.
     */
    public short mismatch(short terminal, short token) {

        String error = "Esperaba \"" + SlkString.GetSymbolName(terminal) +
                "\" pero encontro \"" + SlkString.GetSymbolName(token) + "\".";
        _gestorDeErrores.insertaErrorSintactico(new TErrorSintactico(error, _tokens._analizadorLexico.getYyline(), _tokens._analizadorLexico.getYycolumn()));
        return token;
    }

    /**
     * No existe ninguna entrada de la tabla para el par del NoTerminal/Token. 
     * 
     * @param nonterminal Simbolo no terminal asociado.
     * @param token Token del lexico.
     * @param level Nivel de analisis.
     * 
     * @return El token asociado.
     */
    public short no_entry(short nonterminal, short token, int level) {

        _gestorDeErrores.insertaErrorSintactico(new TErrorSintactico("Token de entrada \"" + SlkString.GetSymbolName(token) + "\" erroneo.", _tokens._analizadorLexico.getYyline(), _tokens._analizadorLexico.getYycolumn()));
        token = _tokens.get();
        return token;
    }

    /**
     * La pila del parser ha quedado vacia pero quedan elementos de entrada
     * por analizar. 
     */
    public void input_left() {

        String error = "La pila del parser ha quedado vacia " +
                "pero todavia quedan elementos por analizar.";

        _gestorDeErrores.insertaErrorSintactico(new TErrorSintactico(error, _tokens._analizadorLexico.getYyline(), _tokens._analizadorLexico.getYycolumn()));
    }
}
