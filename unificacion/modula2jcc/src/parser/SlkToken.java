package parser;

import gestor_de_errores.GestorErrores;
import java.util.ArrayList;
import java.util.Vector;
import observadores.ObservadorLexico;
import scanner.Scanner;
import scanner.TipoToken;
import scanner.Token;
import tabla_de_simbolos.TablaSimbolos;

/**
 * Clase que controla los tokens generados por la herramienta Slk asi como su
 * integracion con los otros componentes de la aplicacion.
 * 
 * @author Javier Salcedo Gomez
 */
public class SlkToken {

    // ATRIBUTOS
    private Token _token;
    public Scanner _analizadorLexico;
    private Vector<ObservadorLexico> _observadoresLexico;
    private TablaSimbolos _tablaSimbolos;
    private PilaNodos _pilaNodos;
    private GestorErrores _gestorDeErrores;

    /**
     * Constructor de la clase SlkToken.
     */
    public SlkToken(Scanner analizadorLexico, GestorErrores gestorDeErrores,TablaSimbolos tablaSimbolos,  PilaNodos pilaNodos) {

        _analizadorLexico = analizadorLexico;
        _gestorDeErrores = gestorDeErrores;
        _observadoresLexico = new Vector<ObservadorLexico>();
        _pilaNodos = pilaNodos;
    }

    /**
     * Nivel de profundidad privado de Slk.
     * 
     * @param level Nivel a establecer.
     * 
     * @return 0 por defecto.
     */
    public short peek(int level) {

        return 0;
    }

    /**
     * Devuelve un short que representa el token correspondiente. Para ello 
     * usando nuestro analizador lexico obtenemos el token y devolvemos un
     * short correspondiente a una de las constantes Slk generadas por la 
     * herramienta.
     * 
     * @return El short correspondiente a la transformacion del token leido de
     * nuestro analizador lexico anterior.
     */
    public short get() {

        _token = new Token(11, 0);

        try {

            _token = _analizadorLexico.get_token();
            avisarTokenGenerado(_token.toString());
        } 
        catch (Exception e) {}

        // Insertamos el token reconocido en la pila menos los signos de puntuacion,
        // el token de fin de fichero y el operador de asignacion
        if(!_token.getTipoToken().equals(TipoToken.EOF) && 
                !_token.getTipoToken().equals(TipoToken.PUNTUACION) &&
                !_token.getTipoToken().equals(TipoToken.PALABRA_RESERVADA))
            insertarNodo();
   
        switch (_token.getTipoToken()) {

            case EOF:
                return SlkConstants.END_OF_SLK_INPUT_;

            case PALABRA_RESERVADA:

                if (_token.getAtributo().equals("ARRAY")) {
                    return SlkConstants.ARRAY_;
                } else if (_token.getAtributo().equals("BEGIN")) {
                    return SlkConstants.BEGIN_;
                } else if (_token.getAtributo().equals("BY")) {
                    return SlkConstants.BY_;
                } else if (_token.getAtributo().equals("CASE")) {
                    return SlkConstants.CASE_;
                } else if (_token.getAtributo().equals("CONST")) {
                    return SlkConstants.CONST_;
                } else if (_token.getAtributo().equals("DEFINITION")) {
                    return SlkConstants.DEFINITION_;
                } else if (_token.getAtributo().equals("DO")) {
                    return SlkConstants.DO_;
                } else if (_token.getAtributo().equals("ELSE")) {
                    return SlkConstants.ELSE_;
                } else if (_token.getAtributo().equals("ELSIF")) {
                    return SlkConstants.ELSIF_;
                } else if (_token.getAtributo().equals("END")) {
                    return SlkConstants.END_;
                } else if (_token.getAtributo().equals("EXIT")) {
                    return SlkConstants.EXIT_;
                } else if (_token.getAtributo().equals("EXPORT")) {
                    return SlkConstants.EXPORT_;
                } else if (_token.getAtributo().equals("FOR")) {
                    return SlkConstants.FOR_;
                } else if (_token.getAtributo().equals("FROM")) {
                    return SlkConstants.FROM_;
                } else if (_token.getAtributo().equals("IF")) {
                    return SlkConstants.IF_;
                } else if (_token.getAtributo().equals("IMPLEMENTATION")) {
                    return SlkConstants.IMPLEMENTATION_;
                } else if (_token.getAtributo().equals("IMPORT")) {
                    return SlkConstants.IMPORT_;
                } else if (_token.getAtributo().equals("LOOP")) {
                    return SlkConstants.LOOP_;
                } else if (_token.getAtributo().equals("MODULE")) {
                    return SlkConstants.MODULE_;
                } else if (_token.getAtributo().equals("OF")) {
                    return SlkConstants.OF_;
                } else if (_token.getAtributo().equals("POINTER")) {
                    return SlkConstants.POINTER_;
                } else if (_token.getAtributo().equals("PROCEDURE")) {
                    return SlkConstants.PROCEDURE_;
                } else if (_token.getAtributo().equals("QUALIFIED")) {
                    return SlkConstants.QUALIFIED_;
                } else if (_token.getAtributo().equals("RECORD")) {
                    return SlkConstants.RECORD_;
                } else if (_token.getAtributo().equals("REPEAT")) {
                    return SlkConstants.REPEAT_;
                } else if (_token.getAtributo().equals("RETURN")) {
                    return SlkConstants.RETURN_;
                } else if (_token.getAtributo().equals("SET")) {
                    return SlkConstants.SET_;
                } else if (_token.getAtributo().equals("THEN")) {
                    return SlkConstants.THEN_;
                } else if (_token.getAtributo().equals("TO")) {
                    return SlkConstants.TO_;
                } else if (_token.getAtributo().equals("TYPE")) {
                    return SlkConstants.TYPE_;
                } else if (_token.getAtributo().equals("VAR")) {
                    return SlkConstants.VAR_;
                } else if (_token.getAtributo().equals("UNTIL")) {
                    return SlkConstants.UNTIL_;
                } else if (_token.getAtributo().equals("WHILE")) {
                    return SlkConstants.WHILE_;
                } else if (_token.getAtributo().equals("WITH")) {
                    return SlkConstants.WITH_;
                }

            case IDENTIFICADOR:
                return SlkConstants.IDENTIFICADOR_;

            case NUMERO_ENTERO:
                return SlkConstants.NUMEROENTERO_;

            case NUMERO_REAL:
                return SlkConstants.NUMEROREAL_;

            case CARACTER:
                return SlkConstants.CARACTER_;

            case CADENA:
                return SlkConstants.CADENA_;

            case OPERADOR_SUMADOR:

                if (_token.getAtributo().equals("SUMA")) {
                    return (short) '+';
                } else if (_token.getAtributo().equals("RESTA")) {
                    return (short) '-';
                } else if (_token.getAtributo().equals("OR")) {
                    return SlkConstants.OR_;
                }

            case OPERADOR_MULTIPLICADOR:

                if (_token.getAtributo().equals("MULTIPLICACION")) {
                    return (short) '*';
                } else if (_token.getAtributo().equals("DIVISION")) {
                    return (short) '/';
                } else if (_token.getAtributo().equals("DIVISION_ENTERA")) {
                    return SlkConstants.DIV_;
                } else if (_token.getAtributo().equals("MOD")) {
                    return SlkConstants.MOD_;
                } else if (_token.getAtributo().equals("MOD")) {
                    return SlkConstants.MOD_;
                } else if (_token.getAtributo().equals("AND")) {
                    return SlkConstants.AND_;
                }

            case PUNTUACION:

                if (_token.getAtributo().equals("PUNTO")) {
                    return (short) '.';
                } else if (_token.getAtributo().equals("COMA")) {
                    return (short) ',';
                } else if (_token.getAtributo().equals("PUNTO_Y_COMA")) {
                    return (short) ';';
                } else if (_token.getAtributo().equals("DOS_PUNTOS")) {
                    return (short) ':';
                } else if (_token.getAtributo().equals("PARENTESIS_IZQUIERDO")) {
                    return (short) '(';
                } else if (_token.getAtributo().equals("PARENTESIS_DERECHO")) {
                    return (short) ')';
                } else if (_token.getAtributo().equals("CORCHETE_IZQUIERDO")) {
                    return (short) '[';
                } else if (_token.getAtributo().equals("CORCHETE_DERECHO")) {
                    return (short) ']';
                } else if (_token.getAtributo().equals("PUNTERO")) {
                    return (short) '^';
                } else if (_token.getAtributo().equals("BARRA_VERTICAL")) {
                    return (short) '|';
                } else if (_token.getAtributo().equals("PUNTO_PUNTO")) {
                    return SlkConstants.DOT_DOT_;
                }

            case OPERADOR_COMPARADOR:

                if (_token.getAtributo().equals("MAYOR")) {
                    return (short) '>';
                } else if (_token.getAtributo().equals("MENOR")) {
                    return (short) '<';
                } else if (_token.getAtributo().equals("MENOR_IGUAL")) {
                    return SlkConstants.LESS_EQUAL_;
                } else if (_token.getAtributo().equals("MAYOR_IGUAL")) {
                    return SlkConstants.GREATER_EQUAL_;
                } else if (_token.getAtributo().equals("IGUAL")) {
                    return (short) '=';
                } else if (_token.getAtributo().equals("DISTINTO")) {
                    return SlkConstants.LESS_GREATER_;
                } else if (_token.getAtributo().equals("ALMOHADILLA")) {
                    return (short) '#';
                } else if (_token.getAtributo().equals("IN")) {
                    return SlkConstants.IN_;
                }

            case OPERADOR_UNITARIO:

                if (_token.getAtributo().equals("NOT")) {
                    return SlkConstants.NOT_;
                } else if (_token.getAtributo().equals("NEGACION")) {
                    return (short) '~';
                }
            case OPERADOR_ASIGNACION:
                return SlkConstants.COLON_EQUAL_;

            
            case TIPO_SIMPLE:
                
                if (_token.getAtributo().equals("BITSET")) {
                    return SlkConstants.BITSET_;
                } else if (_token.getAtributo().equals("BOOLEAN")) {
                    return SlkConstants.BOOLEAN_;
                } else if (_token.getAtributo().equals("CARDINAL")) {
                    return SlkConstants.CARDINAL_;
                } else if (_token.getAtributo().equals("CHAR")) {
                    return SlkConstants.CHAR_;
                } else if (_token.getAtributo().equals("INTEGER")) {
                    return SlkConstants.INTEGER_;
                } else if (_token.getAtributo().equals("LONGINT")) {
                    return SlkConstants.LONGINT_;
                } else if (_token.getAtributo().equals("LONGREAL")) {
                    return SlkConstants.LONGREAL_;
                } else if (_token.getAtributo().equals("PROC")) {
                    return SlkConstants.PROC_;
                } else if (_token.getAtributo().equals("REAL")) {
                    return SlkConstants.REAL_;
                } 
                
            
            case CONSTANTE_PREDEFINIDA:
                
                if (_token.getAtributo().equals("FALSE")) {
                    return SlkConstants.FALSE_;
                } else if (_token.getAtributo().equals("NIL")) {
                    return SlkConstants.NIL_;
                } else if (_token.getAtributo().equals("TRUE")) {
                    return SlkConstants.TRUE_;
                } 
                
            case FUNCION_PREDEFINIDA:

                if (_token.getAtributo().equals("ABS")) {
                    return SlkConstants.ABS_;
                } else if (_token.getAtributo().equals("CAP")) {
                    return SlkConstants.CAP_;
                } else if (_token.getAtributo().equals("CHR")) {
                    return SlkConstants.CHR_;
                } else if (_token.getAtributo().equals("FLOAT")) {
                    return SlkConstants.FLOAT_;
                } else if (_token.getAtributo().equals("HIGH")) {
                    return SlkConstants.HIGH_;
                } else if (_token.getAtributo().equals("MAX")) {
                    return SlkConstants.MAX_;
                } else if (_token.getAtributo().equals("MIN")) {
                    return SlkConstants.MIN_;
                } else if (_token.getAtributo().equals("ODD")) {
                    return SlkConstants.ODD_;
                } else if (_token.getAtributo().equals("ORD")) {
                    return SlkConstants.ORD_;
                } else if (_token.getAtributo().equals("SIZE")) {
                    return SlkConstants.SIZE_;
                } else if (_token.getAtributo().equals("TRUNC")) {
                    return SlkConstants.TRUNC_;
                } else if (_token.getAtributo().equals("VAL")) {
                    return SlkConstants.VAL_;
                }

            case PROCEDIMIENTO_PREDEFINIDO:

                if (_token.getAtributo().equals("DEC")) {
                    return SlkConstants.DEC_;
                } else if (_token.getAtributo().equals("HALT")) {
                    return SlkConstants.HALT_;
                } else if (_token.getAtributo().equals("INC")) {
                    return SlkConstants.INC_;
                } else if (_token.getAtributo().equals("INCL")) {
                    return SlkConstants.INCL_;
                } else if (_token.getAtributo().equals("EXCL")) {
                    return SlkConstants.EXCL_;
                } else if (_token.getAtributo().equals("ALLOCATE")) {
                    return SlkConstants.ALLOCATE_;
                } else if (_token.getAtributo().equals("DEALLOCATE")) {
                    return SlkConstants.DEALLOCATE_;
                } else if (_token.getAtributo().equals("NEW")) {
                    return SlkConstants.NEW_;
                } else if (_token.getAtributo().equals("DISPOSE")) {
                    return SlkConstants.DISPOSE_;
                } else if (_token.getAtributo().equals("WriteLn")) {
                    return SlkConstants.WRITELN_;
                } else if (_token.getAtributo().equals("WriteInt")) {
                    return SlkConstants.WRITEINT_;
                } else if (_token.getAtributo().equals("WriteCard")) {
                    return SlkConstants.WRITECARD_;
                } else if (_token.getAtributo().equals("WriteChar")) {
                    return SlkConstants.WRITECHAR_;
                } else if (_token.getAtributo().equals("WriteReal")) {
                    return SlkConstants.WRITEREAL_;
                } else if (_token.getAtributo().equals("WriteString")) {
                    return SlkConstants.WRITESTRING_;
                } else if (_token.getAtributo().equals("ReadChar")) {
                    return SlkConstants.READCHAR_;
                } else if (_token.getAtributo().equals("ReadString")) {
                    return SlkConstants.READSTRING_;
                } else if (_token.getAtributo().equals("ReadReal")) {
                    return SlkConstants.READREAL_;
                } else if (_token.getAtributo().equals("ReadInt")) {
                    return SlkConstants.READINT_;
                } else if (_token.getAtributo().equals("ReadCard")) {
                    return SlkConstants.READCARD_;
                }
        }

        return SlkConstants.END_OF_SLK_INPUT_;
    }
    
    /**
     * Devuelve el lexema del token.
     * 
     * @return El lexema del token.
     */
    public String getLexema() {

        return _token.getAtributo();
    }

    /**
     * Avisa a los observadores del lexico que el analizador lexico ha generado
     * un token.
     * 
     * @param infoToken Informacion del token generado por el lexico.
     */
    private void avisarTokenGenerado(String infoToken) {

        for (ObservadorLexico obs : _observadoresLexico)
            obs.tokenGenerado(infoToken);     
    }

    /**
     * Añade un nuevo observador al analizador lexico, para ser informado de todos
     * los eventos que ocurran. Evita registros múltiples de un observador
     * comprobando que dicho observador no se encuentre ya en el vector.
     * 
     * @param obs Observador a añadir a nuestra lista.
     */
    public void addObserver(ObservadorLexico obs) {

        if (!_observadoresLexico.contains(obs))
            _observadoresLexico.add(obs);
    }

    /**
     * Elimina un observador de la lista de observadores del lexico.
     * 
     * @param obs Observador a eliminar de nuestra lista de la lista de observadores
     * del lexico.
     */
    public void removeObserver(ObservadorLexico obs) {

        _observadoresLexico.removeElement(obs);
    }

    private TipoToken getTipoToken() {
       return _token.getTipoToken();
    }

    /**
     * Crea e inserta un nuevo nodo en la pila.
     */
    private void insertarNodo() {
        Nodo nodo = null;
        switch (getTipoToken()) {
            case TIPO_SIMPLE:
                // Creamos el nodo a apilar con los datos del token recibido del scanner.
                nodo = new Nodo(_token.getAtributo(),
                        _token.getAtributo(),
                        _token._linea,
                        _token._columna);
                break;
            case CONSTANTE_PREDEFINIDA:
                String semantico = "";
                if ((_token.getAtributo().equals("FALSE"))||(_token.getAtributo().equals("TRUE")))
                    semantico = "BOOLEAN";
                else 
                    semantico = "PUNTERO";
                
                nodo = new Nodo(semantico,
                        _token.getAtributo(),
                        _token._linea,
                        _token._columna);
                break;
            default:
                nodo = new Nodo(_token.getTipoToken().name(),
                        _token.getAtributo(),
                        _token._linea,
                        _token._columna);
                break;
        }

        // Creamos el nodo a apilar con los datos del token recibido del scanner.
//        Nodo nodo = new Nodo(getTipoSemantico(),
//                             _token.getAtributo(),
//                             _token._linea,
//                             _token._columna);
//
        // Apilamos el nuevo nodo en la pila
        _pilaNodos.push(nodo);
    }
}
