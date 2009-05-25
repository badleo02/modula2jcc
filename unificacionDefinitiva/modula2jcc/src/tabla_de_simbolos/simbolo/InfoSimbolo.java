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

    //contiene la pos o dire relativa en mem (desplazamiento con respecto a la base, programa o sub prgrama)
    private String _lugarPosicion;

    //Variable desplazamiento que apunta a la siguiente pos libre (relativa a la base)
    //tal vez no todos los info los necesiten, puede que mudarlo
    private String _desplazamiento;

    //acnho estimado del tipo semantico, el factor de escala
    private String _ancho;

    private int _tamanio;

    /**
     * Array de tipos semanticos del simbolo para simbolos compuestos.
     */
    protected ArrayList<TipoSemantico> _tipoSemantico;

    public void setLugarPosicion(String lugarPosicion) {
        _lugarPosicion = lugarPosicion;
    }
    /**
     * cadena para acceder al registro de esta variable
     * @return
     */
    public String getLugar(){
        return _lugarPosicion;
    }

    
    public void setAncho( String ancho ){
        _ancho = ancho;
    }


    public String getAncho(){
        return _ancho;
    }

    public void setDesplazamiento( String desplazamiento ){ //xa sumascon anchos parsear, igual hacer un metodo de sumar ancho+despla
        _desplazamiento = desplazamiento;
    }

    public String getDesplazamiento(){
        return _desplazamiento;
    }

    /** tamaño en palabras de la maquina de codigo intermedio de la variable
     *
     * @param tama
     */
    public void setTamanio(int tamanio) {
        _tamanio = tamanio;
    }

    public int getTamanio(){
        return _tamanio;
    }

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
