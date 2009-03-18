package parser;

/**
 * Clase que muestra las acciones y reglas del parser a modo de log.
 * 
 * @author Grupo 3
 */
public class SlkLog {

    /**
     * Constructor por defecto de la clase SlkLog.
     */
    public SlkLog() {
    }

    /**
     * Imprime un mensaje.
     * 
     * @param message Mensaje a imprimir.
     */
    public void trace(String message) {
        
        System.out.print(message);
    }

    /**
     * Muestra un mensaje con una determinada separacion.
     * 
     * @param message Mensaje a mostrar.
     * @param depth Separacion.
     */
    public void trace_depth(String message, int depth) {
        
        for (int i = 0; i < depth; ++i) 
            System.out.print("    ");

        System.out.print(message);
    }

    /**
     * Imprime una produccion del parser.
     * 
     * @param production_number Identificador de la produccion.
     */
    public void trace_production(short production_number) {
        
        System.out.print(SlkString.GetProductionName(production_number));
        System.out.print("\n");
    }

    /**
     * Imprime una accion del semantico.
     * 
     * @param action_number Identificador de la accion.
     */
    public void trace_action(short action_number) {
        
        System.out.print("\n");
        System.out.print(SlkString.GetSymbolName(action_number));
        System.out.print("\n\n");
    }
};

