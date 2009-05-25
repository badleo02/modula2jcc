/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tabla_de_simbolos.simbolo;

import java.util.ArrayList;
import semantico.TipoSemantico;

/**
 * un simbolo que es una definición de tipo
 *
 * @author Luis Ayuso
 */
public class InfoSimboloTipo extends InfoSimbolo {

    public InfoSimboloTipo(ArrayList<TipoSemantico> tipoSemantico) {
        super._tipoSemantico = tipoSemantico;
    }

    
    @Override
    public TipoSimbolo getTipoSimbolo() {
        return TipoSimbolo.TIPO;
    }
    
    public String toString(){
        return "\tTIPO:" + _tipoSemantico+ "\n";
    }

}
