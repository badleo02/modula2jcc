package gui;

import javax.swing.table.DefaultTableModel;
import observadores.ObservadorLexico;
import scanner.TipoToken;

/**
 * Ventana que muestra la lista de tokens generados durante el analisis lexico.
 * 
 * @author  Javier Salcedo Gomez
 */
public class VentanaTokens extends javax.swing.JFrame implements ObservadorLexico{

    /**
     * Modelo de la tabla que muestra los tokens generados por el analisis lexico.
     */
    private MiModelo _modTabla;
    
    /** 
     * Constructor de la clase VentanaTokens 
     */
    public VentanaTokens() {
        
        initComponents();
        
        // Construimos la tabla dinamica
        _modTabla = new MiModelo (
                new Object[0][0],
                new String[]{"Linea", "Columna", "Tipo Token", "Lexema"}); 
        _tablaTokens.setModel(_modTabla);
        _tablaTokens.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
 
        // Redimensionamos las columnas 
        _tablaTokens.getColumnModel().getColumn(0).setPreferredWidth(50);
        _tablaTokens.getColumnModel().getColumn(1).setPreferredWidth(65);
        _tablaTokens.getColumnModel().getColumn(2).setPreferredWidth(200);
        _tablaTokens.getColumnModel().getColumn(3).setPreferredWidth(200);
        _tablaTokens.getColumnModel().getColumn(0).setResizable(false);
        _tablaTokens.getColumnModel().getColumn(1).setResizable(false);
        _tablaTokens.getColumnModel().getColumn(2).setResizable(false);
        _tablaTokens.getColumnModel().getColumn(3).setResizable(false);           
        
        _tablaTokens.setModel(_modTabla);
            
        // Redimensionamos la ventana para cuadrarla con el JTable
        setSize(555, 600);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        _scrollPaneTokens = new javax.swing.JScrollPane();
        _tablaTokens = new javax.swing.JTable();

        setTitle("Lista de Tokens Generados");
        setResizable(false);

        _tablaTokens.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        _tablaTokens.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);
        _tablaTokens.setFocusable(false);
        _scrollPaneTokens.setViewportView(_tablaTokens);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 522, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(_scrollPaneTokens, javax.swing.GroupLayout.DEFAULT_SIZE, 502, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(12, 12, 12)
                    .addComponent(_scrollPaneTokens, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane _scrollPaneTokens;
    private javax.swing.JTable _tablaTokens;
    // End of variables declaration//GEN-END:variables

    /**
     * Avisa que el léxico ha generado un token.
     * 
     * @param linea Linea asociada al token.
     * @param columna Columna asociada al token.
     * @param tipo Tipo asociado al token.
     * @param lexema Lexema asociado al token.
     */
    public void tokenGenerado(int linea, int columna, TipoToken tipo, String lexema) {
        
        Object[] fila = new Object[]{linea, columna, tipo, lexema};
        _modTabla.addRow(fila);
        _tablaTokens.setModel(_modTabla);
    }
    
    /**
     * Reinicia la tabla que muestra los tokens asociados al analisis lexico.
     */
    public void reiniciar(){
    
        _modTabla.setRowCount(0);
        _tablaTokens.setModel(_modTabla);
    }
    
    /**
     * Clase que controla el modelo de la tabla que muestra los tokens
     */
    private class MiModelo extends DefaultTableModel{

        /**
         * Constructor de la clase MiModelo.
         * 
         * @param object Array de valores.
         * @param string Columnas de la tabla.
         */
        private MiModelo(Object[][] object, String[] string) {
            
            super(object, string);
        }
    
        /**
         * @see DefaultTableModel.isCellEditable
         */
        @Override
        public boolean isCellEditable(int row, int column){
            
            return false;
        }
    }   
}
