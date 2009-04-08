package tabla_de_simbolos;

import java.util.ArrayList;
import java.util.Hashtable;
import tabla_de_simbolos.simbolo.*;


/**
 * El gestor de la TS tiene como funciones básicas:
 *      - Registrar los identificadores usados en el programa fuente.
 *      - Almacenar información sobre los distintos atributos de cada
 *      identificador.
 * Ademas tiene los siguientes requisitos:
 *      - Busqueda eficiente del registro asociado a cada identificador.
 *      - Almacenamiento y consulta eficiente (rápido) de los campos del registro
 *      de un identificador(sus atributos).
 *  
 * @author Javier Salcedo Gómez
 */
public class TablaDeSimbolos {

    /**
     * Nombre asociado de la tabla de símbolos.
     */
    private String _nombre;
    /**
     * Estructura de datos que contiene al menos un registro por cada identificador
     * del programa fuente con campos para cada uno de los atributos del mismo.
     * Se compone de LEXEMA y RESTO de ATRIBUTOS.
     */
    private Hashtable<String, InfoSimbolo> _TS;
    /**
     * Tabla adjunta solo para palabras reservadas.
     */
    private Hashtable<String, Integer> _palabrasReservadas;
    /**
     * Tabla adjunta solo para funciones predefinidas.
     */
    private Hashtable<String, Integer> _funcionesPredefinidas;
    /**
     * Tabla adjunta solo para procedimientos predefinidos.
     */
    private Hashtable<String, Integer> _procedimientosPredefinidos;
    /**
     * Puntero a la TS que almacena la información de un ámbito superior, en 
     * el que se contiene el que gestiona la TS actual. 
     */
    private TablaDeSimbolos _continente;
    /**
     * Lista de punteros a las TS que almacena la información de un ámbito inferior,
     * contenido en el que se gestiona la TS en cuestión.
     */
    private ArrayList<TablaDeSimbolos> _contenido;

    public TablaDeSimbolos() {

        _nombre = "TS Por Defecto";
        _continente = null;
        _contenido = new ArrayList<TablaDeSimbolos>();
        _TS = new Hashtable<String, InfoSimbolo>();
        iniciaPalabrasFuncionesProcedimientos();
    }

    /**
     * Inicia las tablas de las palabras reservadas, procedimientos 
     * y funciones predefinidas.
     */
    private void iniciaPalabrasFuncionesProcedimientos() {

        // Creamos la tabla de palabras reservadas asociadas a la tabla
        _palabrasReservadas = new Hashtable<String, Integer>();

        // Creamos la tabla de funciones predefinidas asociadas a la tabla
        _funcionesPredefinidas = new Hashtable<String, Integer>();

        // Creamos la tabla de procedimientos predefinidos asociadss a la tabla
        _procedimientosPredefinidos = new Hashtable<String, Integer>();

        // Añade las palabras reservadas a la tabla
        generaListaPalabrasReservadas();

        // Añade las funciones predefinidas a la tabla
        generaListaFuncionesPredefinidas();

        // Añade los procedimientos predifinidos a la tabla
        generaListaProcedimientosPredefinidos();
    }

    /**
     * Inicializa la tabla de palabras reservadas de la clase PalabrasReservadas.
     */
    private void generaListaPalabrasReservadas() {

        PalabrasReservadas[] palabrasReservadas = PalabrasReservadas.values();
        for (PalabrasReservadas palabra : palabrasReservadas) {
            _palabrasReservadas.put(palabra.toString(), palabra.ordinal());
        }
    }

    /**
     * Inicializa la tabla de funciones predefinidas de la clase FuncionesPredefinidas.
     */
    private void generaListaFuncionesPredefinidas() {

        FuncionesPredefinidas[] funcionesPredefinidas = FuncionesPredefinidas.values();
        for (FuncionesPredefinidas funcion : funcionesPredefinidas) {
            _funcionesPredefinidas.put(funcion.toString(), funcion.ordinal());
        }
    }

    /**
     * Inicializa la tabla de procedimientos predefinidos de la clase ProcedimientosPredefinidos.
     */
    private void generaListaProcedimientosPredefinidos() {

        ProcedimientosPredefinidos[] procedimientoPredefinido = ProcedimientosPredefinidos.values();
        for (ProcedimientosPredefinidos procedimiento : procedimientoPredefinido) {
            _procedimientosPredefinidos.put(procedimiento.toString(), procedimiento.ordinal());
        }
    }

    /**
     * Busca una palabra en la tabla de palabras reservadas. 
     * 
     * @param identificador Identificador a buscar.
     *  
     * @return Verdadero si el identificador dado es una palabra reservada y
     * falso en caso contrario.
     */
    public boolean esPalabraReservada(String identificador) {

        return _palabrasReservadas.containsKey(identificador);
    }

    /**
     * Busca una palabra en la tabla de palabras reservadas. 
     * 
     * @param identificador Identificador a buscar.
     *  
     * @return Verdadero si el identificador dado es una palabra reservada y
     * falso en caso contrario.
     */
    public boolean esFuncionPredefinida(String identificador) {

        return _funcionesPredefinidas.containsKey(identificador);
    }

    /**
     * Busca una palabra en la tabla de palabras reservadas. 
     * 
     * @param identificador Identificador a buscar.
     *  
     * @return Verdadero si el identificador dado es una palabra reservada y
     * falso en caso contrario.
     */
    public boolean esProcedimientoPredefinido(String identificador) {

        return _procedimientosPredefinidos.containsKey(identificador);
    }

    /**
     * Inserta un identificador en la TS. Devuelve cierto si se ha insertado
     * y falso en caso contrario.
     *
     * @param lexema Lexema del identificador.
     * 
     * @return Verdadero si se ha conseguido insertar el identificador en la tabla
     * de simbolos actual y falso en caso contrario.
     */
    public boolean inserta(String lexema, TipoSimbolo tipo) {

        // Si no esta declarado en el ambito actual y no se llama igual que 
        // ningun Modulo de ningun ambito superior
        if (!_TS.containsKey(lexema) && !estaModuloDeclarado(lexema)){

            switch (tipo) {

                case SUBPROGRAMA:
                    _TS.put(lexema, new InfoSimboloSubprograma());
                    break;
                case MODULO:
                    _TS.put(lexema, new InfoSimboloModulo());
                    break;
                case GENERAL:
                    _TS.put(lexema, new InfoSimboloGeneral());
                    break;
            }
            return true;
        }
        return false;
    }

    /**
     * Busca el lexema del identificador asociado en el ambito actual y en
     * los superiores y devuelve la entrada correspondiente de su TS. Si no lo
     * encuentra devolverá null y significará que ese identificador no estará
     * declarado.
     * 
     * @param lexema Lexema asociado al identificador.
     * 
     * @return La entrada correspondiente a la TS donde está declarado el 
     * identificado por el lexema de existir y null en caso contrario.
     */
    public InfoSimbolo busca(String lexema) {

        InfoSimbolo estaVar = _TS.get(lexema);

        // Buscamos recursivamente hasta que encuentre la tabla asociada
        if (estaVar == null && _continente != null) {
            estaVar = _continente.busca(lexema);
        }
        return estaVar;
    }

    /**
     * Crea una nueva tabla (ámbito) en la tabla de símbolos.
     * 
     * @param tabla Puntero a la tabla activa en ese momento.
     * 
     * @return Un puntero a la nueva tabla activa que acaba de crearse para
     * un amito mas interno.
     */
    public TablaDeSimbolos abrirAmbito(TablaDeSimbolos tabla) {

        // Se crea la nueva tabla de simbolos
        TablaDeSimbolos nuevaTabla = new TablaDeSimbolos();

        // Añadimos un nuevo puntero de contenido a la tabla activa hacia la nueva tabla
        ArrayList<TablaDeSimbolos> punteros = tabla.getContenido();
        punteros.add(nuevaTabla);
        tabla.setContenido(punteros);

        // Su tabla continente es la que estaba activa en ese momento
        nuevaTabla.setContinente(tabla);

        // Se devuelve el puntero de la nueva tabla para que sea ahora la tabla activa
        return nuevaTabla;
    }

    /**
     * Cierra una tabla (ámbito) en la tabla de símbolos.
     * 
     * @param tabla Puntero a la tabla que deja de ser activa, pasando a serlo
     * la tabla del ámbito que contiene la primera que la representa.
     * 
     * @return Un puntero a la nueva tabla activa, que es la del continente 
     * que se desactiva.
     */
    public TablaDeSimbolos cerrarAmbito(TablaDeSimbolos tabla) {

        if(tabla.getContinente() != null)
            return tabla.getContinente();
        else
            // Devolvemos un puntero a la tabla GLOBAL
            return this;
    }

    /**
     * Busca un ambito con el mismo nombre entre los ambitos superiores.
     * 
     * @param lexema Nombre del ambito asociado.
     * 
     * @return Devuelve verdadero si ya hay un ambito con el mismo nombre
     * en un ambito superior.
     */
    public boolean estaModuloDeclarado(String lexema) {

        boolean estaDefinido = lexema.equals(_nombre);

        // Buscamos recursivamente hasta llegar al final
        if (_continente != null) {
            estaDefinido = estaDefinido || _continente.estaModuloDeclarado(lexema);
        }
        return estaDefinido;
    }

    /**
     * Comprueba si la tabla es la tabla global o no. La tabla 
     * global será la que no esté contenida en niguna otra, es decir, la que 
     * su campo _contiente sea null.
     * 
     * @return Verdadero si la tabla es la tabla global o no.
     */
    public boolean esTablaGlobal() {

        return _continente == null;
    }

    /**
     * 
     * @return
     */
    public Hashtable<String, InfoSimbolo> getTS() {

        return _TS;
    }

    /**
     * 
     * @param TS
     */
    public void setTS(Hashtable<String, InfoSimbolo> TS) {

        _TS = TS;
    }

    /**
     * 
     * @return
     */
    public ArrayList<TablaDeSimbolos> getContenido() {

        return _contenido;
    }

    /**
     * 
     * @param contenido
     */
    public void setContenido(ArrayList<TablaDeSimbolos> contenido) {

        _contenido = contenido;
    }

    /**
     * 
     * @return
     */
    public TablaDeSimbolos getContinente() {

        return _continente;
    }

    /**
     * 
     * @param continente
     */
    public void setContinente(TablaDeSimbolos continente) {

        _continente = continente;
    }

    /**
     * 
     * @return
     */
    public Hashtable<String, Integer> getFuncionesPredefinidas() {

        return _funcionesPredefinidas;
    }

    /**
     * 
     * @param funcionesPredefinidas
     */
    public void setFuncionesPredefinidas(Hashtable<String, Integer> funcionesPredefinidas) {

        _funcionesPredefinidas = funcionesPredefinidas;
    }

    /**
     * 
     * @return
     */
    public String getNombre() {

        return _nombre;
    }

    /**
     * 
     * @param nombre
     */
    public void setNombre(String nombre) {

        _nombre = nombre;
    }

    /**
     * 
     * @return
     */
    public Hashtable<String, Integer> getPalabrasReservadas() {

        return _palabrasReservadas;
    }

    /**
     * 
     * @param palabrasReservadas
     */
    public void setPalabrasReservadas(Hashtable<String, Integer> palabrasReservadas) {

        _palabrasReservadas = palabrasReservadas;
    }

    /**
     * 
     * @return
     */
    public Hashtable<String, Integer> getProcedimientosPredefinidos() {

        return _procedimientosPredefinidos;
    }

    /**
     * 
     * @param procedimientosPredefinidos
     */
    public void setProcedimientosPredefinidos(Hashtable<String, Integer> procedimientosPredefinidos) {

        _procedimientosPredefinidos = procedimientosPredefinidos;
    }
}
