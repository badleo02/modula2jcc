package tabla_de_simbolos;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import org.apache.log4j.Logger;
import semantico.TipoSemantico;

/**
 * Tabla de simbolos del compilador.
 *
 * @author Grupo 3, Grupo 11
 */
public class TablaSimbolos {

    // CONSTANTES
    private static final Logger _logger = Logger.getLogger(TablaSimbolos.class);    // ATRIBUTOS

    // ATRIBUTOS
    // TODO: eliminar estos objetos, son strings o algo!! NUNCA objetos
    private Hashtable<String, ArrayList<Object>> _palabrasReservadas;
    private Hashtable<String, ArrayList<Object>> _funcionesPredefinidas;
    private Hashtable<String, ArrayList<Object>> _procedimientosPredefinidos;
    
    /**
     * Puntero a la tabla en el ambito actual
     */
    private static TablaSimbolos _tablaSimbolosActual;
    
    /**
     * nombre de la tabla de simbolos
     */
    private String _nombre;
    
    /**
     * Tabla asociada a cada simbolo de la tabla.
     */
    private Hashtable<String, Simbolo> _tabla;
    
    /**
     * Lexemas de las variables exportadas en los modulos
     */
    private ArrayList<String> _exportadas;
    
    /**
     * 
     */
    private TablaSimbolos _tablaContinente;
    
    /**
     * Puntero a la tabla que lo contiene.
     */
    private TablaSimbolos _tablaPadre;

    private void iniciaPalabrasFuncionesProcedimientos(){
    
        // Creamos la tabla de palabras reservadas asociadas a la tabla
        _palabrasReservadas = new Hashtable<String, ArrayList<Object>>();

        // Creamos la tabla de funciones predefinidas asociadas a la tabla
        _funcionesPredefinidas = new Hashtable<String, ArrayList<Object>>();

        // Creamos la tabla de procedimientos predefinidos asociadss a la tabla
        _procedimientosPredefinidos = new Hashtable<String, ArrayList<Object>>();

        // Añade las palabras reservadas a la tabla
        generaListaPalabrasReservadas();

        // Añade las funciones predefinidas a la tabla
        generaListaFuncionesPredefinidas();

        // Añade los procedimientos predifinidos a la tabla
        generaListaProcedimientosPredefinidos();        
    }
    
    /**
     * Constructor por defecto de la clase TablaSimbolos.
     */
    public TablaSimbolos() {

        _nombre = "ModuloPrincipal";
        _tablaSimbolosActual = this;
        _tablaContinente = null;
        _tablaPadre = null;
        _tabla = new Hashtable();
        _exportadas = new ArrayList();        
        iniciaPalabrasFuncionesProcedimientos();
    }

    /**
     * Constructor de la clase TablaSimbolos.
     *
     * @param nombre Nombre de la tabla.
     */
    public TablaSimbolos(String nombre) {

        _nombre = nombre;
        _tablaSimbolosActual = this;
        _tablaContinente = null;
        _tablaPadre = null;
        _tabla = new Hashtable();
        _exportadas = new ArrayList();        
        iniciaPalabrasFuncionesProcedimientos();
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
        _tablaSimbolosActual = this;
        _tablaContinente = continente;
        _tablaPadre = modPadre;
        _tabla = new Hashtable();
        _exportadas = new ArrayList();        
        iniciaPalabrasFuncionesProcedimientos();
    }

    /**
     * Inicializa la tabla de palabras reservadas de la clase PalabrasReservadas.
     */
    private void generaListaPalabrasReservadas() {

        PalabrasReservadas[] palabrasReservadas = PalabrasReservadas.values();
        for (PalabrasReservadas palabra : palabrasReservadas) {

            ArrayList<Object> atributos = new ArrayList<Object>();
            atributos.add(new String(palabra.toString()));
            _palabrasReservadas.put(palabra.toString(), atributos);
        }
    }

    /**
     * Inicializa la tabla de palabras reservadas de la clase PalabrasReservadas.
     */
    private void generaListaFuncionesPredefinidas() {

        FuncionesPredefinidas[] funcionesPredefinidas = FuncionesPredefinidas.values();
        for (FuncionesPredefinidas funcion : funcionesPredefinidas) {

            ArrayList<Object> atributos = new ArrayList<Object>();
            //atributos.add(new Integer(funcion.ordinal()));
            atributos.add(new String(funcion.toString()));
            _funcionesPredefinidas.put(funcion.toString(), atributos);
        }
    }

    /**
     * Inicializa la tabla de palabras reservadas de la clase PalabrasReservadas.
     */
    private void generaListaProcedimientosPredefinidos() {

        ProcedimientosPredefinidos[] procedimientoPredefinido = ProcedimientosPredefinidos.values();
        for (ProcedimientosPredefinidos procedimiento : procedimientoPredefinido) {

            ArrayList<Object> atributos = new ArrayList<Object>();
            //atributos.add(new Integer(funcion.ordinal()));
            atributos.add(new String(procedimiento.toString()));
            _procedimientosPredefinidos.put(procedimiento.toString(), atributos);
        }
    }

    /**
     * Busca una palabra en la tabla de palabras reservadas. 
     * 
     * @param identificador Identificador a buscar.
     *  
     * @return Verdadero si el identificador dado es una palabra reservada y
     * falso en caso contrario.
     *  
     * @see #estaInsertada(String) 
     */
    public boolean esPalabraReservada(String identificador) {

        return _palabrasReservadas.containsKey(identificador.toUpperCase());
    }

    /**
     * Busca una palabra en la tabla de palabras reservadas. 
     * 
     * @param identificador Identificador a buscar.
     *  
     * @return Verdadero si el identificador dado es una palabra reservada y
     * falso en caso contrario.
     *  
     * @see #estaInsertada(String) 
     */
    public boolean esFuncionPredefinida(String identificador) {

        return _funcionesPredefinidas.containsKey(identificador.toUpperCase());
    }

    /**
     * Busca una palabra en la tabla de palabras reservadas. 
     * 
     * @param identificador Identificador a buscar.
     *  
     * @return Verdadero si el identificador dado es una palabra reservada y
     * falso en caso contrario.
     *  
     * @see #estaInsertada(String) 
     */
    public boolean esProcedimientoPredefinido(String identificador) {

        return _procedimientosPredefinidos.containsKey(identificador.toUpperCase());
    }

    /**
     * Comprueba que el simbolo de la tabla sea de tipo correspondiente.
     * La primera comprobacion se hace porque si pones un identificador
     * que no has establecido del tipo correspondiente lanzará un null
     * pointer exception y la segunda condicion es la que comprueba en si
     * si se ha establecido antes. 
     * 
     * @param lexema Lexema del simbolo de la tabla.
     * @param Tipo Tipo del simbolo de la tabla.
     * 
     * @return Verdadero si el simbolo de la tabla asociado al lexema 
     * es de tipo "tipo".
     */
    public boolean esDeTipo(String lexema, TipoSimbolo Tipo) {
    
        return _tabla.get(lexema).getTipoSimbolo() != null && _tabla.get(lexema).getTipoSimbolo().equals(Tipo);
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
     * Devuelve el puntero a la tabla de simbolos inferior identificada
     * por el nombre.
     *
     * @param nombre Nombre de la tabla de simbolos inferior.
     *
     * @return El puntero a la tabla de simbolos inferior.
     */
    public TablaSimbolos accederAmbitoInf(String nombre) {

        Simbolo arg = (Simbolo) _tabla.get(nombre);

        // Buscamos mas abajo
        if (arg != null)
            return arg.getContenido();
        else
            return null; // No existe
    }

    /**
     * Cierra el ámbito abierto por un módulo.
     */
    public void cerrarAmbitoModulo(String lexema) {

        // Intentamos cerrar la _tabla principal
        if (!esModuloPrincipal())
            _tablaSimbolosActual = _tablaPadre;
        else
            _tablaSimbolosActual = null;
        
        // Eliminamos ese identificador ya que es el del modulo
        _tabla.remove(lexema);
    }

    /**
     * Cierra el ámbito abierto por un prcedimiento.
     */
    public void cerrarAmbitoProcedimiento() {

        if (_tablaContinente != null)
            _tablaSimbolosActual = _tablaContinente;     
    }

    /**
     * Metodo que se encarga de poner el tipo a un identificador
     * que exista en la _tabla de simbolos.
     *
     * @param ident Nombre del identificador.
     * @param type Tipo que se le va a asignar al identificador.
     */
//    public void setTypeIdent(String ident, String type) {
//        Simbolo arg = (Simbolo) _tabla.get(ident);
//        if (arg != null) {
//            arg.setTipoSemantico(type);
//        }
//    }
//
//    /**
//     * Metodo que comprueba si dos tipos son compatibles.
//     *
//     * @param ident Nombre de la variable.
//     * @param tipo Tipo con el que se va a comprobar.
//     *
//     * @return True si los tipos son compatibles, false si no lo son.
//     */
//    public boolean sonConpatibles(String ident, TipoToken tipo) {
//        Simbolo arg = (Simbolo) _tabla.get(ident);
//
//        if (arg != null) {
//
//            String[] tipoVar = arg.getTipo().split("-");
//
//            TipoToken tipoIdent = null;
//
//            if (tipoVar[0].equals("INTEGER")) {
//                tipoIdent = TipoToken.valueOf("NUMERO_ENTERO");
//            }
//            if (tipoVar[0].equals("REAL")) {
//                tipoIdent = TipoToken.valueOf("NUMERO_REAL");
//            }
//            if (tipoVar[0].equals("BOOLEAN")) {
//                tipoIdent = TipoToken.valueOf("PALABRA_RESERVADA");
//            }
//            if (tipo.compareTo(tipoIdent) == 0) {
//                return true;
//            } else {
//                return false;
//            }
//        } else {
//
//            _logger.error("Variable " + ident + " no declarada");
//            return false;
//        }
//    }
//
//    /**
//     * Comprueba que los dos identificadores sean del tipo booleano.
//     *
//     * @param ident1 Lexema del primer identificador.
//     * @param ident2 Lexema del segundo identificador.
//     *
//     * @return False si los dos no son de tipo boolean y true si lo son.
//     */
//    public boolean sonBooleanos(String ident1, String ident2) {
//
//        Simbolo arg1 = (Simbolo) _tabla.get(ident1);
//        Simbolo arg2 = (Simbolo) _tabla.get(ident2);
//
//        if (arg1 != null && arg2 != null) {
//
//            String[] tipo1 = arg1.getTipo().split("-");
//            String[] tipo2 = arg2.getTipo().split("-");
//
//            boolean iguales = false;
//
//            for (int i = 0; i < tipo1.length; i++) {
//                if (!tipo1[i].equals(tipo2[i])) {
//                    iguales = false;
//                } else if (tipo1[i].equals("BOOLEAN")) {
//                    iguales = true;
//                }
//            }
//            return iguales;
//        }
//
//        return false;
//    }
//
//    /**
//     * Comprueba que los dos identificadores sean del tipo reales.
//     *
//     * @param ident1 Lexema del primer identificador.
//     * @param ident2 Lexema del segundo identificador.
//     *
//     * @return False si los dos no son de tipo real y true si lo son.
//     */
//    public boolean sonReales(String ident1, String ident2) {
//
//        Simbolo arg1 = (Simbolo) _tabla.get(ident1);
//        Simbolo arg2 = (Simbolo) _tabla.get(ident2);
//
//        if (arg1 != null && arg2 != null) {
//
//            String[] tipo1 = arg1.getTipo().split("-");
//            String[] tipo2 = arg2.getTipo().split("-");
//
//            boolean iguales = true;
//
//            for (int i = 0; i < tipo1.length; i++) {
//                if (!tipo1[i].equals(tipo2[i])) {
//                    iguales = false;
//                }
//            }
//            return iguales;
//        }
//
//        return false;
//    }
//
//    /**
//     * Comprueba que los dos identificadores sean del tipo entero.
//     *
//     * @param ident1 Lexema del primer identificador.
//     * @param ident2 Lexema del segundo identificador.
//     *
//     * @return False si los dos no son de tipo entero y true si lo son.
//     */
//    public boolean sonEnteros(String ident1, String ident2) {
//
//        Simbolo arg1 = (Simbolo) _tabla.get(ident1);
//        Simbolo arg2 = (Simbolo) _tabla.get(ident2);
//
//        if (arg1 != null && arg2 != null) {
//
//            String[] tipo1 = arg1.getTipo().split("-");
//            String[] tipo2 = arg2.getTipo().split("-");
//
//            boolean iguales = true;
//
//            for (int i = 0; i < tipo1.length; i++) {
//                if (!tipo1[i].equals(tipo2[i])) {
//                    iguales = false;
//                }
//            }
//            return iguales;
//        }
//
//        return false;
//    }
//
//    /**
//     * Comprueba que los tipos sean del tipo booleano.
//     *
//     * @param tipo1 Tipo a comparar.
//     * @param ident1 Lexema del identificador.
//     *
//     * @return False si los dos no son de tipo boolean y true si lo son.
//     */
//    public boolean sonBooleanos(TipoToken tipo1, String ident1) {
//
//        Simbolo arg1 = (Simbolo) _tabla.get(ident1);
//
//        if (arg1 != null) {
//
//            String[] tipo = arg1.getTipo().split("-");
//
//            TipoToken tipoIdent = null;
//
//            if (tipo[0].equals("INTEGER")) {
//                tipoIdent = TipoToken.valueOf("NUMERO_ENTERO");
//            }
//            if (tipo[0].equals("REAL")) {
//                tipoIdent = TipoToken.valueOf("NUMERO_REAL");
//            }
//            if (tipo[0].equals("BOOLEAN")) {
//                tipoIdent = TipoToken.valueOf("PALABRA_RESERVADA");
//            }
//            if ((tipo1.compareTo(tipoIdent) == 0) && tipo1.compareTo(TipoToken.valueOf("PALABRA_RESERVADA")) == 0) {
//                return true;
//            } else {
//                return false;
//            }
//        }
//
//        return false;
//    }
//
//    /**
//     * Comprueba que los tipos sean del tipo real.
//     *
//     * @param tipo1 Tipo a comparar.
//     * @param ident1 Lexema del identificador.
//     *
//     * @return False si los dos no son de tipo real y true si lo son.
//     */
//    public boolean sonReales(TipoToken tipo1, String ident1) {
//
//        Simbolo arg1 = (Simbolo) _tabla.get(ident1);
//
//        if (arg1 != null) {
//
//            String[] tipo = arg1.getTipo().split("-");
//
//            TipoToken tipoIdent = null;
//
//            if (tipo[0].equals("INTEGER")) {
//                tipoIdent = TipoToken.valueOf("NUMERO_ENTERO");
//            }
//            if (tipo[0].equals("REAL")) {
//                tipoIdent = TipoToken.valueOf("NUMERO_REAL");
//            }
//            if (tipo[0].equals("BOOLEAN")) {
//                tipoIdent = TipoToken.valueOf("PALABRA_RESERVADA");
//            }
//            if ((tipo1.compareTo(tipoIdent) == 0) && (tipo1.compareTo(TipoToken.valueOf("NUMERO_REAL")) == 0)) {
//                return true;
//            } else {
//                return false;
//            }
//        }
//
//        return false;
//    }
//
//    /**
//     * Comprueba que los tipos sean del tipo entero.
//     *
//     * @param tipo1 tipo a comparar.
//     * @param ident1 Lexema del identificador.
//     *
//     * @return false si los dos no son de tipo entero y true si lo son.
//     */
//    public boolean sonEnteros(TipoToken tipo1, String ident1) {
//
//        Simbolo arg1 = (Simbolo) _tabla.get(ident1);
//
//        if (arg1 != null) {
//
//            String[] tipo = arg1.getTipo().split("-");
//
//            TipoToken tipoIdent = null;
//
//            if (tipo[0].equals("INTEGER")) {
//                tipoIdent = TipoToken.valueOf("NUMERO_ENTERO");
//            }
//            if (tipo[0].equals("REAL")) {
//                tipoIdent = TipoToken.valueOf("NUMERO_REAL");
//            }
//            if (tipo[0].equals("BOOLEAN")) {
//                tipoIdent = TipoToken.valueOf("PALABRA_RESERVADA");
//            }
//            if ((tipo1.compareTo(tipoIdent) == 0) && (tipo1.compareTo(TipoToken.valueOf("PALABRA_RESERVADA")) == 0)) {
//                return true;
//            } else {
//                return false;
//            }
//        }
//        return false;
//    }
//
//    /**
//     * Metodo que comprueba si dos tipos son compatibles.
//     *
//     * @param ident Nombre de la variable.
//     * @param ident2 Nombre de la segunda variable.
//     *
//     * @return true si los tipos son compatibles, false si no lo son.
//     */
//    public boolean sonConpatiblesIdentificadores(String ident, String ident2) {
//
//        //comprobar que estaContenida en todos los ambitos posibles
//        Simbolo arg = (Simbolo) _tabla.get(ident);
//        Simbolo arg2 = (Simbolo) _tabla.get(ident2);
//
//        if (arg != null && arg2 != null) {
//
//            String tipoVar = arg.getTipo();
//            String tipoVar2 = arg2.getTipo();
//
//            if (tipoVar2.equals(tipoVar)) {
//                return true;
//            } else {
//                return false;
//            }
//        } else {
//
//            _logger.error("Error en la obtencion de los tipos de las variables en el metodo sonCompatiblesIdentificadores");
//            return false;
//        }
//
//    }
//
//    /**
//     * Devuelve el arraylist con el tipo (compuesto o no) del identificador.
//     *
//     * @param ident Nombre del identificador.
//     *
//     * @return Arralist con los tipos del identificador.
//     */
//    public ArrayList dameTipoIdentificador(String ident) {
//
//        // Buscar en todos los ambitos
//        Simbolo arg = (Simbolo) _tabla.get(ident);
//
//        if (arg != null) {
//
//            String tipos = arg.getTipo();
//            String[] aux = tipos.split("-");
//
//            ArrayList salida = new ArrayList();
//
//            for (int i = 0; i < aux.length; i++) {
//
//                TipoToken tipo = null;
//
//                if (aux[i].equals("INTEGER")) {
//                    tipo = TipoToken.valueOf("NUMERO_ENTERO");
//                } else if (aux[i].equals("REAL")) {
//                    tipo = TipoToken.valueOf("NUMERO_REAL");
//                } // Modificado
//                else if (aux[i].equals("BOOLEAN")) {
//                    tipo = TipoToken.valueOf("PALABRA_RESERVADA");
//                }
//                salida.add(tipo);
//            }
//
//            return salida;
//        } else {
//
//            _logger.error("Variable " + ident + " no declarada se retornara null");
//            return null;
//        }
//    }

    /**
     * Devuelve el contenido de una tabla.
     *
     * @param excluir El valor a excluir en la salida.
     *
     * @return El contenido de una tabla.
     */
    public String contenidoTabla(String excluir) {

        Set keySet = _tabla.keySet();
        String salida = "---------Nombre tabla: " + this.getNombre() + "---------" + '\n';

        for (Iterator i = keySet.iterator(); i.hasNext();) {

            Object key = i.next();
            Simbolo args = (Simbolo) _tabla.get(key);

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
            Simbolo args = (Simbolo) _tabla.get(key);

            if (args != null) {

                TablaSimbolos tablaInt = args.getContenido();

                if (tablaInt != null) {
                    salida = salida + key + " -->  " + tablaInt.getNombre() + '\n';

                    if (tablaInt.getExportadas().size() > 0) {

                        for (Iterator j = tablaInt.getExportadas().iterator(); j.hasNext();) {
                            salida = salida + "     " + j.next() + "  exportada de modulo " + tablaInt.getNombre() + '\n';
                        }
                    }
                } else {
                    salida = salida + key + '\n';
                }
            }
        }

        return salida;
    }

    /**
     * Inserta un identificador de procedimiento.
     *
     * @param lexema Lexema del identificador.
     */
    public void insertarIdentificador(String lexema) {
        
        // Si no esta declarado y no se llama igual que el ambito donde
        // se encuentra
        if (!_tabla.containsKey(lexema) && !_nombre.equals(lexema))
            _tabla.put(lexema, new Simbolo());
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

        if (_tablaContinente != null) {
            salida = salida + _tablaContinente.getVariablesVisibles(_nombre);
        }
        return salida;
    }

    /**
     * Comprueba si el nuevo actual es Modulo Principal o no.
     * 
     * @return Verdadero si es nuevo principal y falso en caso contrario.
     */
    public boolean esModuloPrincipal() {

        return _tablaContinente == null && _tablaPadre == null;
    }

    /**
     * Crea un nuevo ambito en la tabla. Si es estamos en el 
     * ambito prinicipal solo actualizara el nombre con el identificador
     * despues de MODULE. Si no 
     *
     * @param lexema Nombre del nuevo a insertar.
     */
    public void crearAmbito(String lexema) {

        if(!esModuloPrincipal()){
            
            TablaSimbolos nuevo = new TablaSimbolos(lexema, null, this);
            _tabla.put(lexema, new Simbolo(nuevo));
            _tablaSimbolosActual = nuevo;
        }
        else
            _nombre = lexema;
    }

    /**
     * Comprueba si la tabla estaContenida contenida en otra.
     *
     * @param var Nombre de la tabla a comprobar.
     *
     * @return Verdadera si estaContenida y falso en caso contrario.
     */
    public boolean estaContenida(String var) {

        boolean estaVar = _tabla.containsKey(var);

        while (_tablaContinente != null) {
            estaVar = estaVar || _tablaContinente.estaContenida(var);
        }
        return estaVar;
    }

    /**
     * Añade una varibal exportada al arrays de variables exportadas de la tabla.
     *
     * @param lexema Lexema de la variable exportada.
     */
    public void addExportada(String lexema) {

        if (!_exportadas.contains(lexema)) {
            _exportadas.add(lexema);
        }
    }

    /**
     * Construye las variables exportadas de la tabla a partir de las
     * exportadas de un ambito superior.
     */
    public void generarExportadas() {

        for (Iterator it = _exportadas.iterator(); it.hasNext();) {
            String clave = (String) it.next();
            Simbolo reg = (Simbolo) _tabla.get(clave);
            _tablaPadre.insertarIdentificador(clave, reg);
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

        return _tablaContinente;
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
     * Devuelve el nuevo padre de la tabla.
     *
     * @return El nuevo padre de la tabla.
     */
    public TablaSimbolos getModPadre() {

        return _tablaPadre;
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

        _tablaContinente = continente;
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
     * Establece el nuevo padre de la tabla a valor <b>modPadre</b>.
     *
     * @param modPadre Nuevo valor a establecer.
     */
    public void setModPadre(TablaSimbolos modPadre) {

        _tablaPadre = modPadre;
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

    public boolean completaConstante(String lexema, ArrayList<TipoSemantico> tipoSemantico, String valor) {
        Simbolo s = _tabla.get(lexema);
        if (s.estaCompletado())
            return false;

        s.setTipoSimbolo(TipoSimbolo.CONSTANTE);
        s.setTipos(tipoSemantico);
        s.setValor(valor);
        s.completo();    // cierra el simbolo para no modificarlo ni machacarlo
        return true;
    }

    public boolean completaVariable(String lexema, ArrayList<TipoSemantico> tipoSemantico) {
        Simbolo s = _tabla.get(lexema);
        if (s.estaCompletado())
            return false;

        s.setTipoSimbolo(TipoSimbolo.VARIABLE);
        s.setTipos(tipoSemantico);
        s.completo();    // cierra el simbolo para no modificarlo ni machacarlo
        return true;
    }

    public boolean completaTipo(String lexema, ArrayList<TipoSemantico> tipoSemantico) {
        Simbolo s = _tabla.get(lexema);
        if (s.estaCompletado())
            return false;
        s.setTipoSimbolo(TipoSimbolo.TIPO);
        s.setTipos(tipoSemantico);
        s.completo();    // cierra el simbolo para no modificarlo ni machacarlo
        return true;
    }

    public boolean completaFuncion(String lexema, String tipoSemanticoRetorno, int numParam, String[] tipoSemanticoParams) {
        Simbolo s = _tabla.get(lexema);
        if (s.estaCompletado())
            return false;
        s.setTipoSimbolo(TipoSimbolo.FUNCION);
        s.completo();    // cierra el simbolo para no modificarlo ni machacarlo
        return true;
    }

    private void insertarIdentificador(String lexema, Simbolo reg) {
        if (!_tabla.containsKey(lexema) && !_nombre.equals(lexema)) {
            _logger.debug("Insertando identificador en la tabla del modulo " + _nombre + " y lexema " + lexema);
            _tabla.put(lexema, reg);
        }
    }
    
    /**
     * Devuelve el tipo semantico de un simbolo de la tabla identificado
     * por el lexema.
     * @param lexema Lexema del simbolo.
     * 
     * @return El tipo semantico de un simbolo de la tabla identificado
     * por el lexema.
     */
    public ArrayList<TipoSemantico> getTipoSemanticoSimbolo(String lexema){
    
       Simbolo s = _tabla.get(lexema);
       return s.getTipos();
    }
}
