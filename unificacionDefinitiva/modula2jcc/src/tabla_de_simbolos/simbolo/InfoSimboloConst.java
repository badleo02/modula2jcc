/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tabla_de_simbolos.simbolo;

import java.util.ArrayList;
import semantico.TipoSemantico;

/**
 *
 * @author Luis Ayuso
 */
public class InfoSimboloConst extends InfoSimbolo {
    
    /**
     * el valor que tiene esta constante en una cadena de texto
     */
    private String _valor;
    
    /**
     * el valor de la variable en una cadena de texto
     * @return la cadena con el valor
     */
    public String getValor(){
        return _valor;               
    }
    

    public InfoSimboloConst( ArrayList<TipoSemantico> tipoSemantico, String valor) {
       super._tipoSemantico = tipoSemantico;
               
    }

    @Override
    public TipoSimbolo getTipoSimbolo() {
     return TipoSimbolo.CONSTANTE;
    }
   
    public String toString()
    {
        return "\tCONSTANTE:" + _tipoSemantico+ "=" + _valor+ "\n";
    }    
}
