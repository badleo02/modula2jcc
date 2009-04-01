package semantico;

/**
 * Tipos semanticos posibles para un simbolo.
 * 
 * @author Javier Salcedo Gómez
 */
public enum TipoSemantico {

    VOID, ERROR, ENTERO, REAL, ENTERO_LARGO, REAL_LARGO, CARDINAL, BOOLEANO, 
    PROCEDIMIENTO, FUNCION, CARACTER, BITSET, NULO, PUNTERO, CONJUNTO,
    REGISTRO, ENUMERADO, SUBRANGO, PROCEDIMIENTO_PREDEF, FUNCION_PREDEF,
}
