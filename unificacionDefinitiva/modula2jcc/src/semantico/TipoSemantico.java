package semantico;

/**
 * Tipos semanticos posibles para un simbolo.
 * 
 * @author Javier Salcedo Gomez
 */
public enum TipoSemantico {

    VOID, 
    ERROR, 
    ENTERO, 
    REAL, 
    ENTERO_LARGO, 
    REAL_LARGO, 
    CARDINAL, 
    BOOLEANO,
    PROCEDIMIENTO, 
    FUNCION, 
    CARACTER, 
    BITSET, 
    PUNTERO, 
    CADENA, 
    CONJUNTO,
    REGISTRO, 
    ENUMERADO, 
    SUBRANGO, 
    MODULO,
    PROCEDIMIENTO_PREDEF, 
    FUNCION_PREDEF,
    ARRAY;
}
