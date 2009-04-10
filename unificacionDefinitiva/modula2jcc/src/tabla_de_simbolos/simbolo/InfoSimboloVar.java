/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tabla_de_simbolos.simbolo;

import java.util.ArrayList;
import semantico.TipoSemantico;

/**
 *simbolo que identifica una variable.
 * 
 * @author Luis Ayuso
 */
public class InfoSimboloVar extends InfoSimbolo{

    public InfoSimboloVar(ArrayList<TipoSemantico> tipoSemantico){
        super._tipoSemantico = tipoSemantico;
    }
    
    @Override
    public TipoSimbolo getTipoSimbolo() {
       return TipoSimbolo.VARIABLE;
    }
    public String toString(){
        return "\tVARIABLE:" + _tipoSemantico + "\n";
    }
}
