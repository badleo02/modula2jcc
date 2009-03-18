package parser;

import gestor_de_errores.ErroresSintacticos;
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
     * The input token does not match the parse stack token. 
     * 
     * @param terminal
     * @param token
     * 
     * @return
     */
    public short mismatch(short terminal, short token) {

        String error = "Esperaba \"" + SlkString.GetSymbolName(terminal) +
                "\" pero encontr� \"" + SlkString.GetSymbolName(token) +
                "\".";
        _gestorDeErrores.insertaErrorSintactico(new TErrorSintactico(ErroresSintacticos.ERROR_SINTACTICO_IMPOSIBLE_EMPAREJAR_TERMINAL_CON_TOKEN, error, _tokens._analizadorLexico.yyline, _tokens._analizadorLexico.yycolumn));
        return token;
    }

    /**
     * No parse table entry exists for the nonterminal/token pair. 
     * 
     * @param nonterminal
     * @param token
     * @param level
     * @return
     */
    public short no_entry(short nonterminal, short token, int level) {

        String error = "\"" + SlkString.GetSymbolName(token) + "\".";

        _gestorDeErrores.insertaErrorSintactico(new TErrorSintactico(ErroresSintacticos.ERROR_SINTACTICO_TOKEN_DE_ENTRADA_ERRONEO, error, _tokens._analizadorLexico.yyline, _tokens._analizadorLexico.yycolumn));
        token = _tokens.get();
        return token;
    }

    /**
     * The parse stack is empty, but input remains. 
     */
    public void input_left() {

        String error = "La pila del parser ha quedado vacia " +
                "pero todavia quedan por analizar.";

        _gestorDeErrores.insertaErrorSintactico(new TErrorSintactico(ErroresSintacticos.ERROR_SINTACTICO_PILAVACIA_ANTES_DE_TIEMPO, error, _tokens._analizadorLexico.yyline, _tokens._analizadorLexico.yycolumn));
    }
}
