package tabla_de_simbolos.simbolo;

/**
 * Clase que representa el tipo de información de un identificador de tipo general
 * como un tipo, constante o variable.
 * 
 * @author Javier Salcedo Gomez.
 */
public class InfoSimboloGeneral extends InfoSimbolo{

    /**
     * Indica si el simbolo es constante.
     */
    private boolean _esConstante;
    /**
     * Indica si el simbolo es un tipo.
     */
    private boolean _esTipo;

    /**
     * Constructor por defecto de la clase InfoSimboloGeneral.
     */
    public InfoSimboloGeneral() {
        
        _esConstante = false;
        _esTipo = false;
    }

    /**
     * 
     * @return
     */
    public boolean getEsConstante() {
        
        return _esConstante;
    }

    /**
     * 
     * @param esConstante
     */
    public void setEsConstante(boolean esConstante) {
        
        _esConstante = esConstante;
    }

    /**
     * 
     * @return
     */
    public boolean getEsTipo() {
        
        return _esTipo;
    }

    /**
     * 
     * @param esTipo
     */
    public void setEsTipo(boolean esTipo) {
        
        _esTipo = esTipo;
    }

    /**
     * @see Object.toString().
     */
    @Override
    public String toString() {
        
        String cadena = super.toString();
        
        if(_esConstante)
            cadena += "   CONST -> SI\n";
        else
            cadena += "   CONST -> NO\n";
            
        if(_esTipo)
            cadena += "   TYPE -> SI\n";
        else
            cadena += "   TYPE -> NO\n";
            
        return cadena;
    }
}
