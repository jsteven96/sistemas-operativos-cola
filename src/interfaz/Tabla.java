/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;


import java.util.*;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import logica.Gestor;
import logica.Nodo;
import logica.Observable;

/**
 *
 * @author root
 */
public class Tabla implements Observador {
    private Nodo auxiliar;
    private Gestor objGestor;
    private DefaultTableModel model;
    
    public Tabla(Gestor inpObjGestor){
        this.auxiliar = new Nodo();
        this.model = new DefaultTableModel();
        this.objGestor = inpObjGestor;
        this.objGestor.registrar(this);
        String[] titulo = new String[]{"Id", "T. Llegada","Rafaga","T. Comienzo", "T. Final", "T. Retorno", "T. Espera", "Prioridad"};
        this.model.setColumnIdentifiers(titulo);
        
    }

    

    public DefaultTableModel getModel() {
        return this.model;
    }

    public void setModel(DefaultTableModel model) {
        this.model = model;
    }
    
    public void dibujarProcesos(){
        eliminarProcesos();
        this.auxiliar = this.objGestor.getTerminados().cabeza;
        while(this.auxiliar.siguiente.id != -1){
            this.auxiliar = this.auxiliar.siguiente;
            Vector fila = new Vector();
            fila.add(this.auxiliar.id);
            fila.add(this.auxiliar.tiempoLlegada);
            fila.add(this.auxiliar.getRafagaEjecutada());
            fila.add(this.auxiliar.gettComienzo());
            fila.add(this.auxiliar.gettFinal());
            fila.add(this.auxiliar.gettRetorno());
            fila.add(this.auxiliar.gettEspera());
            fila.add(this.auxiliar.prioridad);
            this.model.addRow(fila);
        }
        
    }
    
    public void eliminarProcesos(){
        this.model.setRowCount(0);
    }
    
    
    @Override
    public void actualizarDatos(Observable sujeto) {
        if(this.objGestor.equals(sujeto)){
            this.auxiliar = this.objGestor.getTerminados().cabeza;
            dibujarProcesos();
        }
    }
    
}
