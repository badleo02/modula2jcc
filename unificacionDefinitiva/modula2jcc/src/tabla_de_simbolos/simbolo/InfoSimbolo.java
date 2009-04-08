package tabla_de_simbolos.simbolo;

import java.util.ArrayList;

/**
 * Informacion relativa a un identificador en una entrada de la TS. Como puede
 * haber distintos tipos de información dependiendo del tipo que represente 
 * (un subprograma, un modulo, un array, un registro, un enumerado, etc.) se
 * deja como abstracta proporcionando a los que hereden de ella el tipo semantico
 * que es un campo comun a todos ellos.
 * 
 * @author Javier Salcedo Gomez.
 */
public abstract class InfoSimbolo {

    /**
     * Array de tipos semanticos del simbolo para simbolos compuestos.
     */
    private ArrayList<String> _tipoSemantico;
    
    /**
     * Devuelve el tipo semantico asociado a un identificador en la TS.
     * 
     * @return El tipo semantico asociado a un identificador en la TS.
     */
    public ArrayList<String> getTipoSemantico() {
        
        return _tipoSemantico;
    }

    /**
     * Establece el tipo semantico a valor <b>tipoSemantico</b>.
     * 
     * @param tipoSemantico Nuevo valor a establecer.
     */
    public void setTipoSemantico(ArrayList<String> tipoSemantico) {
        
        _tipoSemantico = tipoSemantico;
    }   
    
    /**
     * Devuelve el tipo semantico básico de un identificador en la TS.
     * En este caso se corresponde con la posición 0 del arraylist de tipos.
     * 
     * @return el tipo semantico básico de un identificador en la TS.
     */
    public String getTipoBasico(){
        
        return _tipoSemantico.get(0);
    }
    
    /**
     * @see Object.toString().
     */
    @Override
    public String toString() {
        
        String cadena = "   TIPO -> (";
        
        if(_tipoSemantico != null)
            for(String tipo : _tipoSemantico)
                cadena += tipo + ", ";
        
        cadena += ")\n";
        
        return cadena;
    }
}
