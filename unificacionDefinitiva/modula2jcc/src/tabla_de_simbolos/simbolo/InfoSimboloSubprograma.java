package tabla_de_simbolos.simbolo;

import java.util.ArrayList;
import semantico.TipoSemantico;
import tabla_de_simbolos.TablaDeSimbolos;

/**
 * Clase que gestiona la informacion relativa a un identificador de la TS que
 * representa un tipo SUBPROGRAMA.
 * 
 * @author Javier Salcedo Gómez
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
    private ArrayList<TipoSimbolo> _valorRetorno;
    
    /**
     * la tabla de simbolos de este ambito
     */
    private TablaDeSimbolos _tablaSimbolos;
    
    /**
     * Constructor por defecto de la clase InfoSimboloSubprograma.
     */
    public InfoSimboloSubprograma() {

        _tipoArgumentos = new ArrayList<ArrayList<TipoSemantico>>(); 
        _pasoArgumentos = new ArrayList<TipoPasoParametro>();
        _valorRetorno = new ArrayList<TipoSimbolo>();
        _numArgs = 0;
    }
        
    /**
     * Devuelve el tipo de valor de retorno del subprograma.
     * 
     * @return El tipo de valor de retorno del subprograma.
     */
    public ArrayList<TipoSimbolo> getValorRetorno() {

        return _valorRetorno;
    }

    /**
     * establece el ambito de este procedimiento, el arrea donde estan
     * definidos sus simbolos.
     * 
     * @param tabla la tabla donde estan sus simbolos
     */
    public void setAmbito(TablaDeSimbolos tabla) {
        _tablaSimbolos =tabla;
    }

    /**
     * Establece el valor de retorno del subprograma a valor <b>valorRetorno</b>.
     * 
     * @param valorRetorno Nuevo valor a establecer.
     */
    public void setValorRetorno(ArrayList<TipoSimbolo> valorRetorno) {
       
        _valorRetorno = valorRetorno;
    }

    /**
     * Devuelve el numero de argumentos.
     * 
     * @return El numero de argumentos.
     */
    public int getNumArgs() {

        return _numArgs;
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
     * Establece el numero de argumentos a valor <b>numArgs</b>.
     * 
     * @param numArgs Nuevo valor a establecer.
     */
    public void setNumArgs(int numArgs) {

        _numArgs = numArgs;
    }

    /**
     * EsttoStringablece el paso de argumentos a valor <b>pasoArgumentos</b>.
     * 
     * @param pasoArgumentos Nuevo valor a establecer.
     */
    public void setPasoArgumentos(ArrayList<TipoPasoParametro> pasoArgumentos) {

        _pasoArgumentos = pasoArgumentos;
    }

    /**
     * Establece el tipo de los argumentos a valor <b>tipoArgumentos</b>.
     * 
     * @param tipoArgumentos Nuevo valor a establecer.
     */
    public void setTipoArgumentos(ArrayList<ArrayList<TipoSemantico>> tipoArgumentos) {

        _tipoArgumentos = tipoArgumentos;
    }
    
    /**
     * @see Object.toString().
     */
    @Override
    public String toString() {
      
        String cadena = "Procedimiento (";
                
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
}
