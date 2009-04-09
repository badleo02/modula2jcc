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
    /**
     * Tipo de retorno del Subprograma. a los PROCEDURE se les pone VOID.
     */
    private ArrayList<TipoSemantico> _valorRetorno;
    
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
        _valorRetorno = retorno;
        _numArgs = numArgs;
        _tablaSimbolos = ambitoProc;
    }
        
    /**
     * Devuelve el tipo de valor de retorno del subprograma.
     * 
     * @return El tipo de valor de retorno del subprograma.
     */
    public ArrayList<TipoSemantico> getValorRetorno() {

        return _valorRetorno;
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
      
        String cadena = "\tProcedimiento (";
                
        for (ArrayList<TipoSemantico> arrayList : _tipoArgumentos) {
            cadena += "[";
            for (TipoSemantico tipoSemantico : arrayList) {
                cadena += tipoSemantico.name();
                cadena += ",";
            }
            cadena += "]";
        }
       
        cadena += ")";
        if (!_valorRetorno.isEmpty()){
             cadena += ":"; 
              cadena += super.toString();
        }
        
        cadena += "\n";
        return cadena;
    }

    @Override
    public TipoSimbolo getTipoSimbolo() {
      return TipoSimbolo.SUBPROGRAMA;
    }
}
