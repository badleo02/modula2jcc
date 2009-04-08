package tabla_de_simbolos.simbolo;

import java.util.ArrayList;

/**
 * Clase que representa el tipo de información de un identificador de MODULE.
 * 
 * @author Javier Salcedo Gomez.
 */
public class InfoSimboloModulo extends InfoSimbolo{

    /**
     * Lista de variables importadas del modulo.
     */
    private ArrayList<String> _importadas;
    /**
     * Lista de variables exportadas del modulo.
     */
    private ArrayList<String> _exportadas;
    /**
     * Indica si el modulo es de tipo DEFINITION.
     */
    private boolean _esDefinition;
    /**
     * Indica si las variables exportadas estan QUALIFIED o no.
     */
    private boolean _cualificadas;
    
    /**
     * Constructor por defecto de la clase InfoSimboloModulo.
     */
    public InfoSimboloModulo() {
    
        _importadas = new ArrayList<String>();
        _exportadas = new ArrayList<String>();    
        _esDefinition = false;
        _cualificadas = false;
    }

    /**
     * 
     * @return
     */
    public boolean getCualificadas() {
        
        return _cualificadas;
    }

    /**
     * 
     * @param cualificadas
     */
    public void setCualificadas(boolean cualificadas) {
        
        _cualificadas = cualificadas;
    }

    /**
     * 
     * @return
     */
    public boolean getEsDefinition() {
        
        return _esDefinition;
    }

    /**
     * 
     * @param esDefinition
     */
    public void setEsDefinition(boolean esDefinition) {
        
        _esDefinition = esDefinition;
    }

    /**
     * 
     * @return
     */
    public ArrayList<String> getExportadas() {
        
        return _exportadas;
    }

    /**
     * 
     * @param exportadas
     */
    public void setExportadas(ArrayList<String> exportadas) {
        
        _exportadas = exportadas;
    }

    /**
     * 
     * @return
     */
    public ArrayList<String> getImportadas() {
        
        return _importadas;
    }

    /**
     * 
     * @param importadas
     */
    public void setImportadas(ArrayList<String> importadas) {
        
        _importadas = importadas;
    }
    
    /**
     * @see Object.toString().
     */
    @Override
    public String toString() {
        
        String cadena = super.toString();
        
        cadena += "   Importadas -> (";
        
        for(String importada : _importadas){
            cadena += importada + ", ";
        }
        cadena += ")\n   Exportadas -> (";
        
        for(String exportada : _exportadas){
            cadena += exportada + ", ";
        }
        cadena += ")\n";
        
        if(_cualificadas)
            cadena += "   QUALIFIED -> SI\n";
        else
            cadena += "   QUALIFIED -> NO\n";
        
        if(_esDefinition)
            cadena += "   DEFINITION -> SI\n";
        else
            cadena += "   DEFINITION -> NO\n";
        
        return cadena;
    }

}
