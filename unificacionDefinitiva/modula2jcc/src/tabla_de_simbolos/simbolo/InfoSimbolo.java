package tabla_de_simbolos.simbolo;

import java.util.ArrayList;
import java.util.Iterator;
import semantico.TipoSemantico;

/**
 * Informacion relativa a un identificador en una entrada de la TS. Como puede
 * haber distintos tipos de información dependiendo del tipo que represente 
 * (un subprograma, un modulo, un array, un registro, un enumerado, etc.) se
 * deja como abstracta proporcionando a los que hereden de ella el tipo semantico
 * que es un campo comun a todos ellos.
 * 
 * @author Javier Salcedo Gomez. Luis Ayuso
 */
public abstract class InfoSimbolo {

    /**
     * Array de tipos semanticos del simbolo para simbolos compuestos.
     */
    protected ArrayList<TipoSemantico> _tipoSemantico;

    /**
     * Devuelve el tipo semantico asociado a un identificador en la TS.
     * 
     * @return El tipo semantico asociado a un identificador en la TS.
     */
    public ArrayList<TipoSemantico> getTipoSemantico() {
        
        return _tipoSemantico;
    }

    
    /**
     * Devuelve el tipo semantico básico de un identificador en la TS.
     * En este caso se corresponde con la posición 0 del arraylist de tipos.
     * 
     * @return el tipo semantico básico de un identificador en la TS.
     */
    public TipoSemantico getTipoBasico(){
        
        return _tipoSemantico.get(0);
    }
    
    /**
     * recupera el tipo de este simbolo, tipo de simbolo, <b>NO</b> semantico
     * @return el tipo del simbolo
     */
    public abstract TipoSimbolo getTipoSimbolo();
}
