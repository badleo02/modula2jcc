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

    private String _lugar;
    private int _tama;
    
    public InfoSimboloVar(ArrayList<TipoSemantico> tipoSemantico){
        super._tipoSemantico = tipoSemantico;
    }
    
    @Override
    public TipoSimbolo getTipoSimbolo() {
       return TipoSimbolo.VARIABLE;
    }

    public void setLugar(String lugar) {
        _lugar = lugar;
    }
    /**
     * cadena para acceder al registro de esta variable
     * @return
     */
    public String getLugar(){
        return _lugar;
    }

    /** tamaño en palabras de la maquina de codigo intermedio de la variable
     *  
     * @param tama
     */
    public void setTama(int tama) {
        _tama = tama;
    }
    
    public int getTama(){
        return _tama;
    }
    
    public String toString(){
        return "\tVARIABLE:" + _tipoSemantico + "\n";
    }
}
