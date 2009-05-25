package tabla_de_simbolos.simbolo;

import java.util.ArrayList;
import semantico.TipoSemantico;
import tabla_de_simbolos.TablaDeSimbolos;

/**
 * Clase que gestiona la informacion relativa a un identificador de la TS que
 * representa un tipo SUBPROGRAMA.
 * 
 * @author Javier Salcedo Gómez, Luis Ayuso
 */
public class InfoSimboloSubprograma extends InfoSimbolo{

    /**
     * Numero de argumentos para funciones y procedimientos
     */
    private int _numArgs;
    /**
     * Tipo semantico de los argumentos funciones y procedimientos
     */
    private ArrayList<ArrayList<TipoSemantico>> _tipoArgumentos;
    /**
     * Modo de paso de los argumentos en funciones y procedimientos.
     * POR REFERENCIA, POR VALOR.
     */
    private ArrayList<TipoPasoParametro> _pasoArgumentos;
//    /**
//     * Tipo de retorno del Subprograma. a los PROCEDURE se les pone VOID.
//     */
//    private ArrayList<TipoSemantico> _valorRetorno;
    
    /**
     * la tabla de simbolos de este ambito
     */
    private TablaDeSimbolos _tablaSimbolos;
    
    /**
     * Constructor por defecto de la clase InfoSimboloSubprograma.
     */
    public InfoSimboloSubprograma(int numArgs, 
                                  ArrayList<TipoPasoParametro> pasoArgumentos, 
                                  ArrayList<ArrayList<TipoSemantico>> tipoArgumentos,
                                  TablaDeSimbolos ambitoProc,
                                  ArrayList<TipoSemantico> retorno) {
        
        _tipoArgumentos = tipoArgumentos;
        _pasoArgumentos = pasoArgumentos;
        super._tipoSemantico = retorno;
        _numArgs = numArgs;
        _tablaSimbolos = ambitoProc;
    }
        
    /**
     * Devuelve el tipo de valor de retorno del subprograma.
     * 
     * @return El tipo de valor de retorno del subprograma.
     */
    public ArrayList<TipoSemantico> getValorRetorno() {

        return super._tipoSemantico;
    }

    /**
     * Devuelve el paso de argumentos.
     * 
     * @return El paso de argumentos.
     */
    public ArrayList getPasoArgumentos() {

        return _pasoArgumentos;
    }

    /**
     * Devuelve los tipos de los argumentos.
     * 
     * @return Los tipos de los argumentos.
     */
    public ArrayList getTipoArgumentos() {

        return _tipoArgumentos;
    }

    
    /**
     * @see Object.toString().
     */
    @Override
    public String toString() {
      
        String cadena = "";
        
        if (!super._tipoSemantico.isEmpty())
            cadena = "\tFunicion (";
        else
            cadena = "\tProcedimiento (";
                
        for (int i=0; i< _numArgs; i++) {
            cadena += _pasoArgumentos.get(i).name();
            
            cadena += "[";
            for (TipoSemantico tipoSemantico : _tipoArgumentos.get(i)) {
                cadena += tipoSemantico.name();
                cadena += ",";
            }
            cadena += "] ";
        }
       
        cadena += ")";
        if (!getValorRetorno().isEmpty()){
            cadena += ":";
            for (TipoSemantico tipoSemantico : _tipoSemantico) {
                cadena += tipoSemantico;
            }
        }
        
        cadena += "\n";
        return cadena;
    }

    @Override
    public TipoSimbolo getTipoSimbolo() {
      return TipoSimbolo.SUBPROGRAMA;
    }
}
