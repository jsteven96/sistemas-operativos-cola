/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

import java.awt.Color;
import java.awt.Component;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import logica.Gestor;
import logica.Nodo;
import logica.Observable;

/**
 *
 * @author root
 */
public class GestorCeldas extends DefaultTableCellRenderer{
    private Gestor objGestor;
    private Nodo auxiliar;
    
    public GestorCeldas(Gestor inpGestor){
        this.objGestor = inpGestor;
        this.auxiliar = new Nodo();
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean focused, int row, int column) {
        /*this.auxiliar = this.objGestor.terminados.cabeza;
        setBackground(Color.WHITE);
        table.setForeground(Color.BLACK);
        super.getTableCellRendererComponent(table, value, selected, focused, row, column);
        while (this.auxiliar.siguiente.id != -1) {
            this.auxiliar = this.auxiliar.siguiente;
            for (int i = 0; i < this.auxiliar.rafaga; i++) {
                if(column == i && row == this.auxiliar.id){
                    setBackground(Color.DARK_GRAY);
                }
                    
            }

        }*/
        return this;
    }

}
