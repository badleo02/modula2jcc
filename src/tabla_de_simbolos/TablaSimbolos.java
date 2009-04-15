/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tabla_de_simbolos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import org.apache.log4j.Logger;
import scanner.TipoToken;

/**
 * Tabla de simbolos del compilador.
 *
 * @author Grupo 3
 */
public class TablaSimbolos {

    // CONSTANTES
    private static final Logger _logger = Logger.getLogger(TablaSimbolos.class);

    // ATRIBUTOS
    private static TablaSimbolos _tablaSimbolos = null;
    private String _nombre = null;
    private Hashtable _tabla = null;
    private ArrayList _exportadas = null;
    private TablaSimbolos _continente = null;
    private TablaSimbolos _modPadre = null;


    public static TablaSimbolos getInstance(){
        if(_tablaSimbolos == null){
            _tablaSimbolos = new TablaSimbolos();
            return _tablaSimbolos;
        }
        else return _tablaSimbolos;
    }

    /**
     * Constructor por defecto de la clase TablaSimbolos.
     */
    public TablaSimbolos() {

        _tabla = new Hashtable();
        _continente = null;
        _modPadre = null;
        _exportadas = new ArrayList();
    }

    /**
     * Constructor de la clase TablaSimbolos.
     *
     * @param nombre Nombre de la tabla.
     */
    public TablaSimbolos(String nombre) {

        _nombre = nombre;
        _tabla = new Hashtable();
        _continente = null;
        _modPadre = null;
        _exportadas = new ArrayList();
    }

    /**
     * Constructor de la clase TablaSimbolos.
     *
     * @param nombre Nombre de la tabla de simbolos.
     * @param continente Continente de la tabla de simbolos.
     * @param modPadre Modulo padre de la tabla de simbolos.
     */
    public TablaSimbolos(String nombre, TablaSimbolos continente, TablaSimbolos modPadre) {

        _nombre = nombre;
        _continente = continente;
        _modPadre = modPadre;
        _exportadas = new ArrayList();
        _tabla = new Hashtable();
    }

    /**
     * Inicializa la tabla de simbolos.
     */
    public void inicializarTabla() {

        _tablaSimbolos = null;
    }

    /**
     * Inserta una variable exportada en el array de variables exportadas.
     *
     * @param lexema Lexema de la nueva variable exportada.
     */
    public void insertarExportada(String lexema) {

        _exportadas.add(lexema);
    }

    /**
     * Devuelve el puntero a la tabla de simbolos inferior.
     *
     * @param nombre Nombre de la tabla de simbolos inferior.
     *
     * @return El puntero a la tabla de simbolos inferior.
     */
    public TablaSimbolos accederAmbitoInf(String nombre) {

        Argumentos arg = (Argumentos) _tabla.get(nombre);

        if (arg != null)
            return arg.getContenido();
        else
            return null;
    }

    /**
     * Cierra el ámbito abierto por un módulo.
     */
    public void cerrarModulo() {

        if (_logger.isDebugEnabled())
            _logger.debug("Cerrando módulo");

        if (_modPadre == null && _continente == null)
            // Intentamos cerrar la _tabla principal
            _logger.debug("Cerrando la tabla principal.");
        else
            if (_modPadre != null)
                _tablaSimbolos = _modPadre;
            else
                _logger.error("Intentando cerrar un módulo que no se puede cerrar.");
    }

    /**
     * Cierra el ámbito abierto por un prcedimiento.
     */
    public void cerrarProcedimiento() {

        if (_logger.isDebugEnabled())
            _logger.debug("Cerrando procedimiento");

        if (_continente != null)
            _tablaSimbolos = _continente;
        else
            _logger.error("Intentando cerrar un procedimiento que no se puede cerrar.");

    }

    /**
     * Metodo que se encarga de poner el tipo a un identificador
     * que exista en la _tabla de simbolos.
     *
     * @param ident Nombre del identificador.
     * @param type Tipo que se le va a asignar al identificador.
     */
    public void setTypeIdent(String ident, String type) {
        Argumentos arg = (Argumentos) _tabla.get(ident);
        if (arg != null) {
            arg.setTipo(type);
        }
    }

    /**
     * Metodo que comprueba si dos tipos son compatibles.
     *
     * @param ident Nombre de la variable.
     * @param tipo Tipo con el que se va a comprobar.
     *
     * @return True si los tipos son compatibles, false si no lo son.
     */
    public boolean sonConpatibles(String ident, TipoToken tipo) {
        Argumentos arg = (Argumentos) _tabla.get(ident);

        if (arg != null) {

            String[] tipoVar = arg.getTipo().split("-");

            TipoToken tipoIdent = null;

            if (tipoVar[0].equals("INTEGER"))
                tipoIdent = TipoToken.valueOf("NUMERO_ENTERO");

            if (tipoVar[0].equals("REAL"))
                tipoIdent = TipoToken.valueOf("NUMERO_REAL");

            if (tipoVar[0].equals("BOOLEAN"))
                tipoIdent = TipoToken.valueOf("PALABRA_RESERVADA");

            if (tipo.compareTo(tipoIdent) == 0)
                return true;
             else
                return false;

        }
        else {

            _logger.error("Variable " + ident + " no declarada");
            return false;
        }
    }

    /**
     * Comprueba que los dos identificadores sean del tipo booleano.
     *
     * @param ident1 Lexema del primer identificador.
     * @param ident2 Lexema del segundo identificador.
     *
     * @return False si los dos no son de tipo boolean y true si lo son.
     */
    public boolean sonBooleanos(String ident1, String ident2) {

        Argumentos arg1 = (Argumentos) _tabla.get(ident1);
        Argumentos arg2 = (Argumentos) _tabla.get(ident2);

        if (arg1 != null && arg2 != null) {

            String[] tipo1 = arg1.getTipo().split("-");
            String[] tipo2 = arg2.getTipo().split("-");

            boolean iguales = false;

            for (int i = 0; i < tipo1.length; i++)
                if (!tipo1[i].equals(tipo2[i]) ) {
                    iguales = false;
                }
                else if(tipo1[i].equals("BOOLEAN")){
                    iguales = true;
                }


            return iguales;
        }

        return false;
    }

    /**
     * Comprueba que los dos identificadores sean del tipo reales.
     *
     * @param ident1 Lexema del primer identificador.
     * @param ident2 Lexema del segundo identificador.
     *
     * @return False si los dos no son de tipo real y true si lo son.
     */
    public boolean sonReales(String ident1, String ident2) {

        Argumentos arg1 = (Argumentos) _tabla.get(ident1);
        Argumentos arg2 = (Argumentos) _tabla.get(ident2);

        if (arg1 != null && arg2 != null) {

            String[] tipo1 = arg1.getTipo().split("-");
            String[] tipo2 = arg2.getTipo().split("-");

            boolean iguales = true;

            for (int i = 0; i < tipo1.length; i++)
                if (!tipo1[i].equals(tipo2[i]))
                    iguales = false;

            return iguales;
        }

        return false;
    }

    /**
     * Comprueba que los dos identificadores sean del tipo entero.
     *
     * @param ident1 Lexema del primer identificador.
     * @param ident2 Lexema del segundo identificador.
     *
     * @return False si los dos no son de tipo entero y true si lo son.
     */
    public boolean sonEnteros(String ident1, String ident2) {

        Argumentos arg1 = (Argumentos) _tabla.get(ident1);
        Argumentos arg2 = (Argumentos) _tabla.get(ident2);

        if (arg1 != null && arg2 != null) {

            String[] tipo1 = arg1.getTipo().split("-");
            String[] tipo2 = arg2.getTipo().split("-");

            boolean iguales = true;

            for (int i = 0; i < tipo1.length; i++)
                if (!tipo1[i].equals(tipo2[i]))
                    iguales = false;

            return iguales;
        }

        return false;
    }

    /**
     * Comprueba que los tipos sean del tipo booleano.
     *
     * @param tipo1 Tipo a comparar.
     * @param ident1 Lexema del identificador.
     *
     * @return False si los dos no son de tipo boolean y true si lo son.
     */
    public boolean sonBooleanos(TipoToken tipo1, String ident1) {

        Argumentos arg1 = (Argumentos) _tabla.get(ident1);

        if (arg1 != null) {

            String[] tipo = arg1.getTipo().split("-");

            TipoToken tipoIdent = null;

            if (tipo[0].equals("INTEGER"))
                tipoIdent = TipoToken.valueOf("NUMERO_ENTERO");

            if (tipo[0].equals("REAL"))
                tipoIdent = TipoToken.valueOf("NUMERO_REAL");

            if (tipo[0].equals("BOOLEAN"))
                tipoIdent = TipoToken.valueOf("PALABRA_RESERVADA");

            if ((tipo1.compareTo(tipoIdent) == 0) && tipo1.compareTo(TipoToken.valueOf("PALABRA_RESERVADA"))==0)
                return true;
            else
                return false;
        }

        return false;
    }

    /**
     * Comprueba que los tipos sean del tipo real.
     *
     * @param tipo1 Tipo a comparar.
     * @param ident1 Lexema del identificador.
     *
     * @return False si los dos no son de tipo real y true si lo son.
     */
    public boolean sonReales(TipoToken tipo1, String ident1) {

        Argumentos arg1 = (Argumentos) _tabla.get(ident1);

        if (arg1 != null) {

            String[] tipo = arg1.getTipo().split("-");

            TipoToken tipoIdent = null;

            if (tipo[0].equals("INTEGER"))
                tipoIdent = TipoToken.valueOf("NUMERO_ENTERO");

            if (tipo[0].equals("REAL"))
                tipoIdent = TipoToken.valueOf("NUMERO_REAL");

            if (tipo[0].equals("BOOLEAN"))
                tipoIdent = TipoToken.valueOf("PALABRA_RESERVADA");

            if ((tipo1.compareTo(tipoIdent) == 0) && (tipo1.compareTo(TipoToken.valueOf("NUMERO_REAL")) == 0))
                return true;
            else
                return false;
        }

        return false;
    }

    /**
     * Comprueba que los tipos sean del tipo entero.
     *
     * @param tipo1 tipo a comparar.
     * @param ident1 Lexema del identificador.
     *
     * @return false si los dos no son de tipo entero y true si lo son.
     */
    public boolean sonEnteros(TipoToken tipo1, String ident1) {

        Argumentos arg1 = (Argumentos) _tabla.get(ident1);

        if (arg1 != null) {

            String[] tipo = arg1.getTipo().split("-");

            TipoToken tipoIdent = null;

            if (tipo[0].equals("INTEGER"))
                tipoIdent = TipoToken.valueOf("NUMERO_ENTERO");

            if (tipo[0].equals("REAL"))
                tipoIdent = TipoToken.valueOf("NUMERO_REAL");

            if (tipo[0].equals("BOOLEAN"))
                tipoIdent = TipoToken.valueOf("PALABRA_RESERVADA");

            if ((tipo1.compareTo(tipoIdent) == 0) && (tipo1.compareTo(TipoToken.valueOf("PALABRA_RESERVADA")) == 0))
                return true;
            else
                return false;

        }
        return false;
    }

    /**
     * Metodo que comprueba si dos tipos son compatibles.
     *
     * @param ident Nombre de la variable.
     * @param ident2 Nombre de la segunda variable.
     *
     * @return true si los tipos son compatibles, false si no lo son.
     */
    public boolean sonConpatiblesIdentificadores(String ident, String ident2) {

        //comprobar que esta en todos los ambitos posibles
        Argumentos arg = (Argumentos) _tabla.get(ident);
        Argumentos arg2 = (Argumentos) _tabla.get(ident2);

        if (arg != null && arg2 != null){

            String tipoVar = arg.getTipo();
            String tipoVar2 = arg2.getTipo();

            if (tipoVar2.equals(tipoVar))
                return true;
            else
                return false;
        }
        else {

            _logger.error("Error en la obtencion de los tipos de las variables en el metodo sonCompatiblesIdentificadores");
            return false;
        }

    }

    /**
     * Devuelve el arraylist con el tipo (compuesto o no) del identificador.
     *
     * @param ident Nombre del identificador.
     *
     * @return Arralist con los tipos del identificador.
     */
    public ArrayList dameTipoIdentificador(String ident) {

        // Buscar en todos los ambitos
        Argumentos arg = (Argumentos) _tabla.get(ident);

        if (arg != null) {

            String tipos = arg.getTipo();
            String[] aux = tipos.split("-");

            ArrayList salida = new ArrayList();

            for (int i = 0; i < aux.length; i++) {

                TipoToken tipo = null;

                if (aux[i].equals("INTEGER")) {
                    tipo = TipoToken.valueOf("NUMERO_ENTERO");
                }
                else
                    if (aux[i].equals("REAL")) {
                        tipo = TipoToken.valueOf("NUMERO_REAL");
                    }
                    // Modificado
                    else
                        if (aux[i].equals("BOOLEAN")) {
                            tipo = TipoToken.valueOf("PALABRA_RESERVADA");
                        }
                salida.add(tipo);
            }

            return salida;
        }
        else {

            _logger.error("Variable " + ident + " no declarada se retornara null");
            return null;
        }
    }

    /**
     * Devuelve el contenido de una tabla.
     *
     * @param excluir El valor a excluir en la salida.
     *
     * @return El contenido de una tabla.
     */
    public String contenidoTabla(String excluir) {

        Collection valores = _tabla.values();
        Enumeration valor = _tabla.keys();
        Set keySet = _tabla.keySet();
        String salida = "---------Nombre tabla: " + this.getNombre() + "---------" + '\n';

        for (Iterator i = keySet.iterator(); i.hasNext();) {

            Object key = i.next();
            Argumentos args = (Argumentos) _tabla.get(key);

            if (!key.equals(excluir)) {
                if (args != null) {
                    TablaSimbolos tablaInt = args.getContenido();
                    if (tablaInt != null) {
                        salida = salida + key + " -->  " + tablaInt.getNombre() + '\n';
                    } else {
                        salida = salida + key + '\n';
                    }
                }
            }
        }

        return salida;
    }

    /**
     * Devuelve el contenido de una tabla.
     *
     * @return El contenido de una tabla.
     */
    public String contenidoTabla() {

        Set keySet = _tabla.keySet();
        String salida = "---------Nombre tabla: " + this.getNombre() + "---------" + '\n';

        for (Iterator i = keySet.iterator(); i.hasNext();) {

            Object key = i.next();
            Argumentos args = (Argumentos) _tabla.get(key);

            if (args != null) {

                TablaSimbolos tablaInt = args.getContenido();

                if (tablaInt != null) {
                    salida = salida + key + " -->  " + tablaInt.getNombre() + '\n';

                    if (tablaInt.getExportadas().size() > 0) {

                        for (Iterator j = tablaInt.getExportadas().iterator(); j.hasNext();)
                            salida = salida + "     " + j.next() + "  exportada de modulo " + tablaInt.getNombre() + '\n';
                    }
                }
                else
                    salida = salida + key + '\n';
            }
        }

        return salida;
    }

    /**
     * Inserta un identificador en la tabla actual.
     *
     * @param lexema Lexema del identificador.
     */
    public void insertarIdentificador(String lexema) {

        if (_tabla.containsKey(lexema) || _nombre.equals(lexema))
            _logger.error("Ya esta definida la variable " + lexema + " o la variable se llama = que el modulo");
        else {

            _logger.debug("Insertando identificador en la tabla del modulo " + _nombre + " y lexema " + lexema);
            _tabla.put(lexema, new Argumentos());
        }
    }

    /**
     * Inserta un identificador de procedimiento.
     *
     * @param lexema Lexema del identificador.
     * @param contenido Argumentos del procedimiento.
     */
    public void insertarIdentificador(String lexema, Argumentos contenido) {

        if (_tabla.containsKey(lexema) || _nombre.equals(lexema))
            _logger.error("Ya esta definida la variable " + lexema + " o la variable se llama = que el modulo");
        else {

            _logger.debug("Insertando identificador en la tabla del modulo " + _nombre + " y lexema " + lexema);
            _tabla.put(lexema, contenido);
        }
    }

    /**
     * Devuelve las variables visibles de una tabla.
     *
     * @param excluir Valor a excluir.
     *
     * @return Las variables visibles de una tabla.
     */
    public String getVariablesVisibles(String excluir) {

        String salida;

        salida = contenidoTabla(excluir);

        if (_continente != null)
            salida = salida + _continente.getVariablesVisibles(_nombre);

        return salida;
    }

    /**
     * Inserta un nuevo modulo en la tabla.
     *
     * @param lexema Nombre del modulo a insertar.
     */
    public void insertarModulo(String lexema) {

        if (_tabla.contains(lexema) || _nombre.equals(lexema))
            _logger.error("Ya esta definido el modulo " + lexema);
        else {

            TablaSimbolos modulo = new TablaSimbolos(lexema, null, this);
            _tabla.put(lexema, new Argumentos(modulo));
            _logger.debug("Insertando modulo");
            _tablaSimbolos = modulo;
        }
    }

    /**
     * Inserta un nuevo procedimiento en la tabla de simbolos.
     *
     * @param lexema Lexema del procedimiento a insertar.
     *
     * @return La tabla de simbolose con el nuevo procedimiento ya insertado.
     */
    public TablaSimbolos insertarProcedimiento(String lexema) {

        if (_tabla.contains(lexema) || _nombre.equals(lexema)) {

            _logger.error("Ya esta definido el procedimiento " + lexema);
            return this;
        }
        else {

            TablaSimbolos modulo = new TablaSimbolos(lexema, this, null);
            _tabla.put(lexema, new Argumentos(modulo));
            _logger.debug("insertando procedimiento");

            return modulo;
        }
    }

    /**
     * Comprueba si la tabla esta contenida en otra.
     *
     * @param var Nombre de la tabla a comprobar.
     *
     * @return Verdadera si esta y falso en caso contrario.
     */
    public boolean esta(String var) {

        boolean estaVar = _tabla.containsKey(var);

        while (_continente != null)
            estaVar = estaVar || _continente.esta(var);

        return estaVar;
    }

    /**
     * Añade una variable exportada.
     *
     * @param contenido Contenido de la variable exportada.
     */
    public void añadirVariableExportada(String contenido) {

        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Añade una varibal exportada al arrays de variables exportadas de la tabla.
     *
     * @param lexema Lexema de la variable exportada.
     */
    public void anadirVariableExportada(String lexema) {

        if (!_exportadas.contains(lexema))
            _exportadas.add(lexema);
    }

    /**
     * Construye las variables exportadas de la tabla.
     */
    public void obtenerExportadas() {

        for (Iterator it = _exportadas.iterator(); it.hasNext();) {
            String clave = (String) it.next();
            Argumentos reg = (Argumentos) _tabla.get(clave);
            _modPadre.insertarIdentificador(clave, reg);
        }
    }

    /**
     * Devuelve el nombre de la tabla.
     *
     * @return El nombre de la tabla.
     */
    public String getNombre() {

        return _nombre;
    }

    /**
     * Devuelve el continente de la tabla.
     *
     * @return El continente de la tabla.
     */
    public TablaSimbolos getContinente() {

        return _continente;
    }

    /**
     * Devuelve las variables exportadas de la tabla.
     *
     * @return Las variables exportadas de la tabla.
     */
    public ArrayList getExportadas() {

        return _exportadas;
    }

    /**
     * Devuelve el modulo padre de la tabla.
     *
     * @return El modulo padre de la tabla.
     */
    public TablaSimbolos getModPadre() {

        return _modPadre;
    }

    /**
     * Devuelve la tabla donde almacena los identificadores.
     *
     * @return La tabla donde almacena los identificadores.
     */
    public Hashtable getTabla() {

        return _tabla;
    }

    /**
     * Establece el continente de la tabla a valor <b>continente</b>.
     *
     * @param continente Nuevo valor a establecer.
     */
    public void setContinente(TablaSimbolos continente) {

        _continente = continente;
    }

    /**
     * Establece las variables exportadas de la tabla a valor <b>exportadas</b>.
     *
     * @param exportadas Nuevo valor a establecer.
     */
    public void setExportadas(ArrayList exportadas) {

        _exportadas = exportadas;
    }

    /**
     * Establece el modulo padre de la tabla a valor <b>modPadre</b>.
     *
     * @param modPadre Nuevo valor a establecer.
     */
    public void setModPadre(TablaSimbolos modPadre) {

        _modPadre = modPadre;
    }

    /**
     * Establece la tabla de identificadores de la tabla a valor <b>tabla</b>.
     *
     * @param tabla Nuevo valor a establecer.
     */
    public void setTabla(Hashtable tabla) {

        _tabla = tabla;
    }

    /**
     * Establece el nombre de la tabla a valor <b>nombre</b>.
     *
     * @param nombre Nuevo valor a establecer.
     */
    public void setNombre(String nombre) {

        _nombre = nombre;
    }
}
