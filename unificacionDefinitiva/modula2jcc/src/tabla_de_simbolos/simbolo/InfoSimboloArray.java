package tabla_de_simbolos.simbolo;

import java.util.ArrayList;
import semantico.TipoSemantico;
import tabla_de_simbolos.TablaDeSimbolos;

/**
 * Clase que gestiona la informacion relativa a un identificador de la TS que
 * representa un tipo ARRAY.
 * 
 * @author Ivan Munsuri Ibanez
 */
public class InfoSimboloArray extends InfoSimbolo{

    /**
     * Numero de dimensiones del array.
     */
    private int _numeroDimensiones;
    /**
     * Array con el numero de componentes por cada dimension del mismo.
     */
    private int[] _numeroComponentesPorDimension;

    /**
     * Constructor por defecto de la clase InfoSimboloArray.
     */
    public InfoSimboloArray( int numeroDimensiones, ArrayList<TipoSemantico> tipoSemantico) {
        
        super._tipoSemantico = tipoSemantico;
        _numeroDimensiones = numeroDimensiones;
        _numeroComponentesPorDimension = new int[0];
    }
        
    /**
     * Devuelve el numero de dimensiones del array.
     * 
     * @return Devuelve un entero.
     */
    public int getNumeroDimensiones(){
        return _numeroDimensiones;
    }

    /**
     * Establece el numero de componentes por cada dimension del array.
     *
     * @param numeroComponentesPorDimension array de enteros.
     */
    public void setNumeroComponentesPorDimension( int[] numeroComponentesPorDimension ) {

        _numeroComponentesPorDimension = numeroComponentesPorDimension;
    }

    /**
     * Devuelve el numero de componentes por cada dimension del array.
     * 
     * @return Devuelve un array de enteros.
     */
    public int[] getNumeroComponentesPorDimension() {

        return _numeroComponentesPorDimension;
    }

    /**
     * Devuelve el numero de componentes de cierta dimension del array.
     *
     * @return Devuelve un enteros.
     */
    public int getNumeroComponentesDimension( int index ) {

        return _numeroComponentesPorDimension[ index ];
    }

    
    /**TODO:
     * @see Object.toString().
     */
    @Override
    public String toString() {
      
        /*String cadena = "NumeroDimensiones: "+getNumeroDimensiones()+"\n";
        for( int i = 0;  i < getNumeroDimensiones();  i++ )
          cadena += "Dimension: " +i+" -> "+getNumeroComponentesDimension( i )+"componentes\n"*/
        String cadena = "";
        if( getNumeroDimensiones() == getNumeroComponentesPorDimension().length ){
            cadena = "\tARRAY:" + _tipoSemantico;
            cadena += "\tnDim: "+getNumeroDimensiones()+"; [ ";
            for( int i = 0;  i < getNumeroDimensiones();  i++ )
              cadena += getNumeroComponentesDimension( i )+", ";
            cadena += " ]"+cadena;
        }else{
            cadena = "\tARRAY:" + _tipoSemantico;
            cadena += "\tnDim: "+getNumeroDimensiones();
        }

        return cadena+"\n";
    }

    /**
     * Recupera el tipo de este simbolo, tipo de simbolo, <b>NO</b> semantico
     * @return Devuelve el tipo del simbolo.
     */
    public TipoSimbolo getTipoSimbolo() {
      return TipoSimbolo.ARRAY;
    }
}
