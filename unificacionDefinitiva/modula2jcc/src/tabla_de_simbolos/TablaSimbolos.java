package tabla_de_simbolos;

import tabla_de_simbolos.simbolo.InfoSimboloSubprograma;
import tabla_de_simbolos.simbolo.TipoSimbolo;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

/**
 * Tabla de simbolos del compilador.
 *
 * @author Grupo 3, Grupo 11
 */
public class TablaSimbolos {

//    /**
//     * Tabla de palabras reservadas.
//     */
//    private Hashtable<String, ArrayList<Object>> _palabrasReservadas;
//    /**
//     * Tabla de funciones predefinidas.
//     */
//    private Hashtable<String, ArrayList<Object>> _funcionesPredefinidas;
//    /**
//     * Tabla de procedimientos predefinidos.
//     */
//    private Hashtable<String, ArrayList<Object>> _procedimientosPredefinidos;
//    /**
//     * Puntero a la tabla en el ambito actual
//     */
//    private static TablaSimbolos _tablaSimbolosActual;
//    /**
//     * id de la tabla de simbolos
//     */
//    private String _nombre;
//    /**
//     * Tabla asociada a cada simbolo de la tabla.
//     */
//    private Hashtable<String, InfoSimboloSubprograma> _simbolos;
//    /**
//     * Lexemas de las variables exportadas en los modulos.
//     */
//    private ArrayList<String> _exportadas;
//    /**
//     * Tabla subyancente a la tabla actual.
//     */
//    private TablaSimbolos _continente;
//    /**
//     * Puntero a la tabla que lo contiene.
//     */
//    private TablaSimbolos _contenido;
//    
//    /**
//     * Constructor por defecto de la clase TablaSimbolos.
//     */
//    public TablaSimbolos() {
//
//        _nombre = "ModuloPrincipal";
//        _tablaSimbolosActual = this;
//        _continente = null;
//        _contenido = null;
//        _simbolos = new Hashtable();
//        _exportadas = new ArrayList();
//        iniciaPalabrasFuncionesProcedimientos();
//    }
//
//    /**
//     * Constructor de la clase TablaSimbolos.
//     *
//     * @param id Nombre de la tabla.
//     */
//    public TablaSimbolos(String nombre) {
//
//        _nombre = nombre;
//        _tablaSimbolosActual = this;
//        _continente = null;
//        _contenido = null;
//        _simbolos = new Hashtable();
//        _exportadas = new ArrayList();
//        iniciaPalabrasFuncionesProcedimientos();
//    }
//
//    /**
//     * Constructor de la clase TablaSimbolos.
//     *
//     * @param id Nombre de la tabla de simbolos.
//     * @param continente Continente de la tabla de simbolos.
//     * @param modPadre Modulo padre de la tabla de simbolos.
//     */
//    public TablaSimbolos(String nombre, TablaSimbolos continente, TablaSimbolos modPadre) {
//
//        _nombre = nombre;
//        _tablaSimbolosActual = this;
//        _continente = continente;
//        _contenido = modPadre;
//        _simbolos = new Hashtable();
//        _exportadas = new ArrayList();
//        iniciaPalabrasFuncionesProcedimientos();
//    }
//
//    /**
//     * Inicia las tablas de las palabras reservadas, procedimientos 
//     * y funciones predefinidas.
//     */
//    private void iniciaPalabrasFuncionesProcedimientos() {
//
//        // Creamos la tabla de palabras reservadas asociadas a la tabla
//        _palabrasReservadas = new Hashtable<String, ArrayList<Object>>();
//
//        // Creamos la tabla de funciones predefinidas asociadas a la tabla
//        _funcionesPredefinidas = new Hashtable<String, ArrayList<Object>>();
//
//        // Creamos la tabla de procedimientos predefinidos asociadss a la tabla
//        _procedimientosPredefinidos = new Hashtable<String, ArrayList<Object>>();
//
//        // Añade las palabras reservadas a la tabla
//        generaListaPalabrasReservadas();
//
//        // Añade las funciones predefinidas a la tabla
//        generaListaFuncionesPredefinidas();
//
//        // Añade los procedimientos predifinidos a la tabla
//        generaListaProcedimientosPredefinidos();
//    }
//
//    /**
//     * Inicializa la tabla de palabras reservadas de la clase PalabrasReservadas.
//     */
//    private void generaListaPalabrasReservadas() {
//
//        PalabrasReservadas[] palabrasReservadas = PalabrasReservadas.values();
//        for (PalabrasReservadas palabra : palabrasReservadas) {
//
//            ArrayList<Object> atributos = new ArrayList<Object>();
//            atributos.add(new String(palabra.toString()));
//            _palabrasReservadas.put(palabra.toString(), atributos);
//        }
//    }
//
//    /**
//     * Inicializa la tabla de palabras reservadas de la clase PalabrasReservadas.
//     */
//    private void generaListaFuncionesPredefinidas() {
//
//        FuncionesPredefinidas[] funcionesPredefinidas = FuncionesPredefinidas.values();
//        for (FuncionesPredefinidas funcion : funcionesPredefinidas) {
//
//            ArrayList<Object> atributos = new ArrayList<Object>();
//            //atributos.add(new Integer(funcion.ordinal()));
//            atributos.add(new String(funcion.toString()));
//            _funcionesPredefinidas.put(funcion.toString(), atributos);
//        }
//    }
//
//    /**
//     * Inicializa la tabla de palabras reservadas de la clase PalabrasReservadas.
//     */
//    private void generaListaProcedimientosPredefinidos() {
//
//        ProcedimientosPredefinidos[] procedimientoPredefinido = ProcedimientosPredefinidos.values();
//        for (ProcedimientosPredefinidos procedimiento : procedimientoPredefinido) {
//
//            ArrayList<Object> atributos = new ArrayList<Object>();
//            //atributos.add(new Integer(funcion.ordinal()));
//            atributos.add(new String(procedimiento.toString()));
//            _procedimientosPredefinidos.put(procedimiento.toString(), atributos);
//        }
//    }
//
//    /**
//     * Busca una palabra en la tabla de palabras reservadas. 
//     * 
//     * @param identificador Identificador a buscar.
//     *  
//     * @return Verdadero si el identificador dado es una palabra reservada y
//     * falso en caso contrario.
//     *  
//     * @see #estaInsertada(String) 
//     */
//    public boolean esPalabraReservada(String identificador) {
//
//        return _palabrasReservadas.containsKey(identificador.toUpperCase());
//    }
//
//    /**
//     * Busca una palabra en la tabla de palabras reservadas. 
//     * 
//     * @param identificador Identificador a buscar.
//     *  
//     * @return Verdadero si el identificador dado es una palabra reservada y
//     * falso en caso contrario.
//     *  
//     * @see #estaInsertada(String) 
//     */
//    public boolean esFuncionPredefinida(String identificador) {
//
//        return _funcionesPredefinidas.containsKey(identificador.toUpperCase());
//    }
//
//    /**
//     * Busca una palabra en la tabla de palabras reservadas. 
//     * 
//     * @param identificador Identificador a buscar.
//     *  
//     * @return Verdadero si el identificador dado es una palabra reservada y
//     * falso en caso contrario.
//     *  
//     * @see #estaInsertada(String) 
//     */
//    public boolean esProcedimientoPredefinido(String identificador) {
//
//        return _procedimientosPredefinidos.containsKey(identificador.toUpperCase());
//    }
//
//    /**
//     * Comprueba que el simbolo de la tabla sea de tipo correspondiente.
//     * La primera comprobacion se hace porque si pones un identificador
//     * que no has establecido del tipo correspondiente lanzará un null
//     * pointer exception y la segunda condicion es la que comprueba en si
//     * si se ha establecido antes. 
//     * 
//     * @param lexema Lexema del simbolo de la tabla.
//     * @param Tipo Tipo del simbolo de la tabla.
//     * 
//     * @return Verdadero si el simbolo de la tabla asociado al lexema 
//     * es de tipo "tipo".
//     */
//    public boolean esDeTipo(String lexema, TipoSimbolo Tipo) {
//
//        return _simbolos.get(lexema).getTipoSimbolo() != null && _simbolos.get(lexema).getTipoSimbolo().equals(Tipo);
//    }
//
//    /**
//     * Inserta una variable exportada en el array de variables exportadas.
//     *
//     * @param lexema Lexema de la nueva variable exportada.
//     */
//    public void insertarExportada(String lexema) {
//
//        _exportadas.add(lexema);
//    }
//
//    /**
//     * Devuelve el puntero a la tabla de simbolos inferior identificada
//     * por el id.
//     *
//     * @param id Nombre de la tabla de simbolos inferior.
//     *
//     * @return El puntero a la tabla de simbolos inferior.
//     */
//    public TablaSimbolos accederAmbitoInf(String nombre) {
//
//        InfoSimboloSubprograma simbolo = (InfoSimboloSubprograma) _simbolos.get(nombre);
//
//        // Buscamos mas abajo
//        if (simbolo != null) {
//            return simbolo.getAmbito();
//        } else {
//            return null; // No existe
//        }
//    }
//
//    /**
//     * Cierra el ámbito abierto por un módulo. Consiste en que la tabla actual
//     * sea ahora la de un ambito superior al actual (_contenido).
//     */
//    public void cerrarAmbitoModulo() {
//
//        if (!esModuloPrincipal())
//            if(_contenido != null)
//                _tablaSimbolosActual = _contenido;        
//    }
//
//    /**
//     * Cierra el ámbito abierto por un prcedimiento.
//     */
//    public void cerrarAmbitoProcedimiento() {
//
//        if (_continente != null) {
//            _tablaSimbolosActual = _continente;
//        }
//    }
//
//
//    /**
//     * Devuelve el contenido de una tabla.
//     *
//     * @param excluir El valor a excluir en la salida.
//     *
//     * @return El contenido de una tabla.
//     */
//    public String contenidoTabla(String excluir) {
//
//        Set keySet = _simbolos.keySet();
//        String salida = "---------Nombre tabla: " + this.getNombre() + "---------" + '\n';
//
//        for (Iterator i = keySet.iterator(); i.hasNext();) {
//
//            Object key = i.next();
//            InfoSimboloSubprograma simbolo = (InfoSimboloSubprograma) _simbolos.get(key);
//
//            if (!key.equals(excluir)) {
//                if (simbolo != null) {
//                    TablaSimbolos tablaInt = simbolo.getAmbito();
//                    if (tablaInt != null) {
//                        salida = salida + key + " -->  " + tablaInt.getNombre() + '\n';
//                    } else {
//                        salida = salida + key + '\n';
//                    }
//                }
//            }
//        }
//
//        return salida;
//    }
//
//    /**
//     * Devuelve el contenido de una tabla.
//     *
//     * @return El contenido de una tabla.
//     */
//    public String contenidoTabla() {
//
//        Set keySet = _simbolos.keySet();
//        String salida = "";
//
//        for (Iterator i = keySet.iterator(); i.hasNext();) {
//
//            Object key = i.next();
//            InfoSimboloSubprograma simbolo = (InfoSimboloSubprograma) _simbolos.get(key);
//
//            if (simbolo != null) {
//
//                TablaSimbolos tablaInt = simbolo.getAmbito();
//
//                if (tablaInt != null) {
//                    salida = salida + key + " -->  " + tablaInt.getNombre() + "\n";
//
//                    if (tablaInt.getExportadas().size() > 0) {
//
//                        for (Iterator j = tablaInt.getExportadas().iterator(); j.hasNext();) {
//                            salida = salida + "     " + j.next() + "  exportada de modulo " + tablaInt.getNombre() + '\n';
//                        }
//                    }
//                } else {
//                    
//                    salida = salida + "Lexema: " + key + 
//                            ", Tipo Semantico: " + simbolo.getTipos() +
//                            ", Tipo Simbolo: " + simbolo.getTipoSimbolo() + 
//                            "\n";
//                }
//            }
//        }
//
//        return salida;
//    }
//
//    /**
//     * Devuelve las variables visibles de una tabla.
//     *
//     * @param excluir Valor a excluir.
//     *
//     * @return Las variables visibles de una tabla.
//     */
//    public String getVariablesVisibles(String excluir) {
//
//        String salida;
//
//        salida = contenidoTabla(excluir);
//
//        if (_continente != null) {
//            salida = salida + _continente.getVariablesVisibles(_nombre);
//        }
//        return salida;
//    }
//
//    /**
//     * Comprueba si el nuevo actual es Modulo Principal o no.
//     * 
//     * @return Verdadero si es nuevo principal y falso en caso contrario.
//     */
//    public boolean esModuloPrincipal() {
//
//        return (_contenido == null);
//    }
//
//    /**
//     * Crea un nuevo ambito en la tabla. Si es estamos en el 
//     * ambito prinicipal solo actualizara el id con el identificador
//     * despues de MODULE. Si no 
//     *
//     * @param lexema Nombre del nuevo a insertar.
//     */
//    public void crearAmbito(String lexema) {
//
//        if (!esModuloPrincipal()) {
//
//            TablaSimbolos nuevo = new TablaSimbolos(lexema, null, this);
//            _simbolos.put(lexema, new InfoSimboloSubprograma(nuevo));
//            _tablaSimbolosActual = nuevo;
//        } else {
//            _nombre = lexema;
//        }
//    }
//
//    /**
//     * Comprueba si un identificador está definido en alguno de losla estaIdentificadorDefinido contenida en otra.
//     *
//     * @param id Nombre del identificador a comprobar.
//     *
//     * @return Verdadera si estaIdentificadorDefinido y falso en caso contrario.
//     */
//    public boolean estaIdentificadorDefinido(String id) {
//
//        boolean estaVar = _simbolos.containsKey(id);
//
//        while (_continente != null) {
//            estaVar = estaVar || _continente.estaIdentificadorDefinido(id);
//        }
//        return estaVar;
//    }
//
//    /**
//     * Añade una varibal exportada al arrays de variables exportadas de la tabla.
//     *
//     * @param lexema Lexema de la variable exportada.
//     */
//    public void addExportada(String lexema) {
//
//        if (!_exportadas.contains(lexema)) {
//            _exportadas.add(lexema);
//        }
//    }
//
//    /**
//     * Inserta un nuevo simbolo en la tabla de simbolos.
//     * 
//     * @param lexema Lexema asociado al simbolo.
//     * @param simbolo InfoSimboloSubprograma a introducir en la tabla.
//     */
//    private void insertaIdentificador(String lexema, InfoSimboloSubprograma simbolo) {
//
//        if (!_simbolos.containsKey(lexema) && !_nombre.equals(lexema)) {
//            _simbolos.put(lexema, simbolo);
//        }
//    }
//
//    /**
//     * Construye las variables exportadas de la tabla a partir de las
//     * exportadas de un ambito superior.
//     */
//    public void generarExportadas() {
//
//        for (Iterator it = _exportadas.iterator(); it.hasNext();) {
//            String clave = (String) it.next();
//            InfoSimboloSubprograma reg = (InfoSimboloSubprograma) _simbolos.get(clave);
//            _contenido.insertaIdentificador(clave, reg);
//        }
//    }
//
//    /**
//     * Devuelve el id de la tabla.
//     *
//     * @return El id de la tabla.
//     */
//    public String getNombre() {
//
//        return _tablaSimbolosActual._nombre;
//    }
//
//    /**
//     * Devuelve el continente de la tabla.
//     *
//     * @return El continente de la tabla.
//     */
//    public TablaSimbolos getContinente() {
//
//        return _continente;
//    }
//
//    /**
//     * Devuelve las variables exportadas de la tabla.
//     *
//     * @return Las variables exportadas de la tabla.
//     */
//    public ArrayList getExportadas() {
//
//        return _exportadas;
//    }
//
//    /**
//     * Devuelve el nuevo padre de la tabla.
//     *
//     * @return El nuevo padre de la tabla.
//     */
//    public TablaSimbolos getModPadre() {
//
//        return _contenido;
//    }
//
//    /**
//     * Devuelve la tabla donde almacena los identificadores.
//     *
//     * @return La tabla donde almacena los identificadores.
//     */
//    public Hashtable getTabla() {
//
//        return _simbolos;
//    }
//
//    /**
//     * Establece el continente de la tabla a valor <b>continente</b>.
//     *
//     * @param continente Nuevo valor a establecer.
//     */
//    public void setContinente(TablaSimbolos continente) {
//
//        _continente = continente;
//    }
//
//    /**
//     * Establece las variables exportadas de la tabla a valor <b>exportadas</b>.
//     *
//     * @param exportadas Nuevo valor a establecer.
//     */
//    public void setExportadas(ArrayList exportadas) {
//
//        _exportadas = exportadas;
//    }
//
//    /**
//     * Establece el nuevo padre de la tabla a valor <b>modPadre</b>.
//     *
//     * @param modPadre Nuevo valor a establecer.
//     */
//    public void setModPadre(TablaSimbolos modPadre) {
//
//        _contenido = modPadre;
//    }
//
//    /**
//     * Establece la tabla de identificadores de la tabla a valor <b>tabla</b>.
//     *
//     * @param tabla Nuevo valor a establecer.
//     */
//    public void setTabla(Hashtable tabla) {
//
//        _simbolos = tabla;
//    }
//
//    /**
//     * Establece el id de la tabla a valor <b>id</b>.
//     *
//     * @param id Nuevo valor a establecer.
//     */
//    public void setNombre(String nombre) {
//
//        _nombre = nombre;
//    }
//
//    /**
//     * Inserta un identificador en la tabla de simbolos.
//     *
//     * @param lexema Lexema del identificador.
//     */
//    public void inserta(String lexema) {
//
//        // Si no esta declarado y no se llama igual que el ambito donde
//        // se encuentra
//        if (!_simbolos.containsKey(lexema) && !_nombre.equals(lexema)) {
//            _simbolos.put(lexema, new InfoSimboloSubprograma());
//        }
//    }
//
//    /**
//     * Completa la información de un simbolo.
//     * 
//     * @param lexema Lexema asociado al simbolo.
//     * @param tipoSemantico Tipo semantico del simbolo.
//     * @param valor Valor del simbolo.
//     * @param tipo Tipo del simbolo.
//     */
//    public void completa(String lexema, ArrayList<String> tipoSemantico, String valor, TipoSimbolo tipo) {
//
//        InfoSimboloSubprograma s = _simbolos.get(lexema);
//        s.setTipoSimbolo(tipo);
//        s.setTipos(tipoSemantico);
//
//        if (tipo.equals(TipoSimbolo.CONSTANTE)) {
//            s.setValor(valor);
//        }
//        if (tipo.equals(TipoSimbolo.SUBPROGRAMA)) {
//        }
//    }
//    
//    /**
//     * Devuelve el tipo semantico de un simbolo de la tabla identificado
//     * por el lexema.
//     * @param lexema Lexema del simbolo.
//     * 
//     * @return El tipo semantico de un simbolo de la tabla identificado
//     * por el lexema.
//     */
//    public ArrayList<String> getTipoSemanticoSimbolo(String lexema) {
//
//        InfoSimboloSubprograma s = _simbolos.get(lexema);
//        return s.getTipos();
//    }
}
