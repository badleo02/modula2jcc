package parser;

import semantico.Nodo;
import semantico.PilaNodos;
import java.util.Vector;
import observadores.ObservadorLexico;
import scanner.Scanner;
import scanner.TipoToken;
import scanner.Token;

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
    private PilaNodos _pilaNodos;
    
    /**
     * Constructor de la clase SlkToken.
     * 
     * @param analizadorLexico Analizador lexico del compilador.
     * @param pilaNodos Pila para la gestion de las acciones semanticas.
     */
    public SlkToken(Scanner analizadorLexico, PilaNodos pilaNodos) {

        _analizadorLexico = analizadorLexico;
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
            avisarTokenGenerado();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insertamos el token reconocido en la pila menos los signos de puntuacion,
        // el token de fin de fichero y el operador de asignacion
        if (!_token.getTipoToken().equals(TipoToken.EOF) &&
                !_token.getTipoToken().equals(TipoToken.PUNTUACION) &&
                !_token.getTipoToken().equals(TipoToken.PALABRA_RESERVADA)) {
            insertarNodo();
        }
        switch (_token.getTipoToken()) {

            case EOF:
                return SlkConstants.END_OF_SLK_INPUT_;

            case PALABRA_RESERVADA:

                if (_token.getLexema().equals("ARRAY")) {
                    return SlkConstants.ARRAY_;
                } else if (_token.getLexema().equals("BEGIN")) {
                    return SlkConstants.BEGIN_;
                } else if (_token.getLexema().equals("BY")) {
                    return SlkConstants.BY_;
                } else if (_token.getLexema().equals("CASE")) {
                    return SlkConstants.CASE_;
                } else if (_token.getLexema().equals("CONST")) {
                    return SlkConstants.CONST_;
                } else if (_token.getLexema().equals("DEFINITION")) {
                    return SlkConstants.DEFINITION_;
                } else if (_token.getLexema().equals("DO")) {
                    return SlkConstants.DO_;
                } else if (_token.getLexema().equals("ELSE")) {
                    return SlkConstants.ELSE_;
                } else if (_token.getLexema().equals("ELSIF")) {
                    return SlkConstants.ELSIF_;
                } else if (_token.getLexema().equals("END")) {
                    return SlkConstants.END_;
                } else if (_token.getLexema().equals("EXIT")) {
                    return SlkConstants.EXIT_;
                } else if (_token.getLexema().equals("EXPORT")) {
                    return SlkConstants.EXPORT_;
                } else if (_token.getLexema().equals("FOR")) {
                    return SlkConstants.FOR_;
                } else if (_token.getLexema().equals("FROM")) {
                    return SlkConstants.FROM_;
                } else if (_token.getLexema().equals("IF")) {
                    return SlkConstants.IF_;
                } else if (_token.getLexema().equals("IMPLEMENTATION")) {
                    return SlkConstants.IMPLEMENTATION_;
                } else if (_token.getLexema().equals("IMPORT")) {
                    return SlkConstants.IMPORT_;
                } else if (_token.getLexema().equals("LOOP")) {
                    return SlkConstants.LOOP_;
                } else if (_token.getLexema().equals("MODULE")) {
                    return SlkConstants.MODULE_;
                } else if (_token.getLexema().equals("OF")) {
                    return SlkConstants.OF_;
                } else if (_token.getLexema().equals("POINTER")) {
                    return SlkConstants.POINTER_;
                } else if (_token.getLexema().equals("PROCEDURE")) {
                    return SlkConstants.PROCEDURE_;
                } else if (_token.getLexema().equals("QUALIFIED")) {
                    return SlkConstants.QUALIFIED_;
                } else if (_token.getLexema().equals("RECORD")) {
                    return SlkConstants.RECORD_;
                } else if (_token.getLexema().equals("REPEAT")) {
                    return SlkConstants.REPEAT_;
                } else if (_token.getLexema().equals("RETURN")) {
                    return SlkConstants.RETURN_;
                } else if (_token.getLexema().equals("SET")) {
                    return SlkConstants.SET_;
                } else if (_token.getLexema().equals("THEN")) {
                    return SlkConstants.THEN_;
                } else if (_token.getLexema().equals("TO")) {
                    return SlkConstants.TO_;
                } else if (_token.getLexema().equals("TYPE")) {
                    return SlkConstants.TYPE_;
                } else if (_token.getLexema().equals("VAR")) {
                    return SlkConstants.VAR_;
                } else if (_token.getLexema().equals("UNTIL")) {
                    return SlkConstants.UNTIL_;
                } else if (_token.getLexema().equals("WHILE")) {
                    return SlkConstants.WHILE_;
                } else if (_token.getLexema().equals("WITH")) {
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

                if (_token.getLexema().equals("SUMA")) {
                    return (short) '+';
                } else if (_token.getLexema().equals("RESTA")) {
                    return (short) '-';
                } else if (_token.getLexema().equals("OR")) {
                    return SlkConstants.OR_;
                }

            case OPERADOR_MULTIPLICADOR:

                if (_token.getLexema().equals("MULTIPLICACION")) {
                    return (short) '*';
                } else if (_token.getLexema().equals("DIVISION")) {
                    return (short) '/';
                } else if (_token.getLexema().equals("DIVISION_ENTERA")) {
                    return SlkConstants.DIV_;
                } else if (_token.getLexema().equals("MOD")) {
                    return SlkConstants.MOD_;
                } else if (_token.getLexema().equals("MOD")) {
                    return SlkConstants.MOD_;
                } else if (_token.getLexema().equals("AND")) {
                    return SlkConstants.AND_;
                }

            case PUNTUACION:

                if (_token.getLexema().equals("PUNTO")) {
                    return (short) '.';
                } else if (_token.getLexema().equals("COMA")) {
                    return (short) ',';
                } else if (_token.getLexema().equals("PUNTO_Y_COMA")) {
                    return (short) ';';
                } else if (_token.getLexema().equals("DOS_PUNTOS")) {
                    return (short) ':';
                } else if (_token.getLexema().equals("PARENTESIS_IZQUIERDO")) {
                    return (short) '(';
                } else if (_token.getLexema().equals("PARENTESIS_DERECHO")) {
                    return (short) ')';
                } else if (_token.getLexema().equals("CORCHETE_IZQUIERDO")) {
                    return (short) '[';
                } else if (_token.getLexema().equals("CORCHETE_DERECHO")) {
                    return (short) ']';
                } else if (_token.getLexema().equals("PUNTERO")) {
                    return (short) '^';
                } else if (_token.getLexema().equals("BARRA_VERTICAL")) {
                    return (short) '|';
                } else if (_token.getLexema().equals("PUNTO_PUNTO")) {
                    return SlkConstants.DOT_DOT_;
                }

            case OPERADOR_COMPARADOR:

                if (_token.getLexema().equals("MAYOR")) {
                    return (short) '>';
                } else if (_token.getLexema().equals("MENOR")) {
                    return (short) '<';
                } else if (_token.getLexema().equals("MENOR_IGUAL")) {
                    return SlkConstants.LESS_EQUAL_;
                } else if (_token.getLexema().equals("MAYOR_IGUAL")) {
                    return SlkConstants.GREATER_EQUAL_;
                } else if (_token.getLexema().equals("IGUAL")) {
                    return (short) '=';
                } else if (_token.getLexema().equals("DISTINTO")) {
                    return SlkConstants.LESS_GREATER_;
                } else if (_token.getLexema().equals("ALMOHADILLA")) {
                    return (short) '#';
                } else if (_token.getLexema().equals("IN")) {
                    return SlkConstants.IN_;
                }

            case OPERADOR_UNITARIO:

                if (_token.getLexema().equals("NOT")) {
                    return SlkConstants.NOT_;
                } else if (_token.getLexema().equals("NEGACION")) {
                    return (short) '~';
                }
            case OPERADOR_ASIGNACION:
                return SlkConstants.COLON_EQUAL_;


            case TIPO_SIMPLE:

                if (_token.getLexema().equals("BITSET")) {
                    return SlkConstants.BITSET_;
                } else if (_token.getLexema().equals("BOOLEAN")) {
                    return SlkConstants.BOOLEAN_;
                } else if (_token.getLexema().equals("CARDINAL")) {
                    return SlkConstants.CARDINAL_;
                } else if (_token.getLexema().equals("CHAR")) {
                    return SlkConstants.CHAR_;
                } else if (_token.getLexema().equals("INTEGER")) {
                    return SlkConstants.INTEGER_;
                } else if (_token.getLexema().equals("LONGINT")) {
                    return SlkConstants.LONGINT_;
                } else if (_token.getLexema().equals("LONGREAL")) {
                    return SlkConstants.LONGREAL_;
                } else if (_token.getLexema().equals("PROC")) {
                    return SlkConstants.PROC_;
                } else if (_token.getLexema().equals("REAL")) {
                    return SlkConstants.REAL_;
                }


            case CONSTANTE_PREDEFINIDA:

                if (_token.getLexema().equals("FALSE")) {
                    return SlkConstants.FALSE_;
                } else if (_token.getLexema().equals("NIL")) {
                    return SlkConstants.NIL_;
                } else if (_token.getLexema().equals("TRUE")) {
                    return SlkConstants.TRUE_;
                }

            case FUNCION_PREDEFINIDA:

                if (_token.getLexema().equals("ABS")) {
                    return SlkConstants.ABS_;
                } else if (_token.getLexema().equals("CAP")) {
                    return SlkConstants.CAP_;
                } else if (_token.getLexema().equals("CHR")) {
                    return SlkConstants.CHR_;
                } else if (_token.getLexema().equals("FLOAT")) {
                    return SlkConstants.FLOAT_;
                } else if (_token.getLexema().equals("HIGH")) {
                    return SlkConstants.HIGH_;
                } else if (_token.getLexema().equals("MAX")) {
                    return SlkConstants.MAX_;
                } else if (_token.getLexema().equals("MIN")) {
                    return SlkConstants.MIN_;
                } else if (_token.getLexema().equals("ODD")) {
                    return SlkConstants.ODD_;
                } else if (_token.getLexema().equals("ORD")) {
                    return SlkConstants.ORD_;
                } else if (_token.getLexema().equals("SIZE")) {
                    return SlkConstants.SIZE_;
                } else if (_token.getLexema().equals("TRUNC")) {
                    return SlkConstants.TRUNC_;
                } else if (_token.getLexema().equals("VAL")) {
                    return SlkConstants.VAL_;
                }

            case PROCEDIMIENTO_PREDEFINIDO:

                if (_token.getLexema().equals("DEC")) {
                    return SlkConstants.DEC_;
                } else if (_token.getLexema().equals("HALT")) {
                    return SlkConstants.HALT_;
                } else if (_token.getLexema().equals("INC")) {
                    return SlkConstants.INC_;
                } else if (_token.getLexema().equals("INCL")) {
                    return SlkConstants.INCL_;
                } else if (_token.getLexema().equals("EXCL")) {
                    return SlkConstants.EXCL_;
                } else if (_token.getLexema().equals("ALLOCATE")) {
                    return SlkConstants.ALLOCATE_;
                } else if (_token.getLexema().equals("DEALLOCATE")) {
                    return SlkConstants.DEALLOCATE_;
                } else if (_token.getLexema().equals("NEW")) {
                    return SlkConstants.NEW_;
                } else if (_token.getLexema().equals("DISPOSE")) {
                    return SlkConstants.DISPOSE_;
                } else if (_token.getLexema().equals("WriteLn")) {
                    return SlkConstants.WRITELN_;
                } else if (_token.getLexema().equals("WriteInt")) {
                    return SlkConstants.WRITEINT_;
                } else if (_token.getLexema().equals("WriteCard")) {
                    return SlkConstants.WRITECARD_;
                } else if (_token.getLexema().equals("WriteChar")) {
                    return SlkConstants.WRITECHAR_;
                } else if (_token.getLexema().equals("WriteReal")) {
                    return SlkConstants.WRITEREAL_;
                } else if (_token.getLexema().equals("WriteString")) {
                    return SlkConstants.WRITESTRING_;
                } else if (_token.getLexema().equals("ReadChar")) {
                    return SlkConstants.READCHAR_;
                } else if (_token.getLexema().equals("ReadString")) {
                    return SlkConstants.READSTRING_;
                } else if (_token.getLexema().equals("ReadReal")) {
                    return SlkConstants.READREAL_;
                } else if (_token.getLexema().equals("ReadInt")) {
                    return SlkConstants.READINT_;
                } else if (_token.getLexema().equals("ReadCard")) {
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

        return _token.getLexema();
    }

    /**
     * Avisa a los observadores del lexico que el analizador lexico ha generado
     * un token.
     */
    private void avisarTokenGenerado() {

        for (ObservadorLexico obs : _observadoresLexico) {
            obs.tokenGenerado(_token.getLinea(), _token.getColumna(), _token.getTipoToken(), _token.getLexema());
        }
    }

    /**
     * Añade un nuevo observador al analizador lexico, para ser informado de todos
     * los eventos que ocurran. Evita registros múltiples de un observador
     * comprobando que dicho observador no se encuentre ya en el vector.
     * 
     * @param obs Observador a añadir a nuestra lista.
     */
    public void addObserver(ObservadorLexico obs) {

        if (!_observadoresLexico.contains(obs)) {
            _observadoresLexico.add(obs);
        }
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

    /**
     * Crea e inserta un nuevo nodo en la pila.
     * EL TIPO SEMANTICO SE COMPLETARA EN LAS ACCIONES CORRESPONDIENTES
     */
    private void insertarNodo() {
        
        // Creamos el nodo a apilar con los datos del token recibido del scanner.
        _pilaNodos.push(new Nodo(_token.getLexema(), _token.getLinea(), _token.getColumna(), _token.getTipoToken()));
    }
}
