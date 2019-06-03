/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.text.AbstractDocument.Content;
import logica.*;

/**
 *
 * @author root
 */
public class Diagrama extends JFrame implements Observador {
    private JPanel pnlDiagrama;
    private JLabel lblTitulo;
    private Gestor objGestor;
    private JTable tblProcesos;
    private DefaultTableModel dtm;
    private Nodo auxiliar;
    private JScrollPane jspDiagrama;
    //private GestorCeldas gesCeldas;
    public int veces = 0;
    
    public Diagrama(Gestor inpObjGestor){
        super("Diagrama de Gantt");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100,100,400,430);
        this.pnlDiagrama = new JPanel();
        this.pnlDiagrama.setBorder(new EmptyBorder(10,10,10,10));
        
        //Creación de JScrollPane
        this.jspDiagrama = new JScrollPane();
        
        //Asignación del objeto gestor observable
        this.objGestor = inpObjGestor;
        this.objGestor.registrar(this);
        this.auxiliar = this.objGestor.getListos().cabeza;
        
        //Creación de la tabla
        this.tblProcesos = new JTable();
        this.dtm = new DefaultTableModel();
        this.tblProcesos.setModel(this.dtm);
        
        this.tblProcesos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        this.tblProcesos.doLayout();
        this.jspDiagrama.setViewportView(this.tblProcesos);
        
        this.pnlDiagrama.setLayout(new FlowLayout(FlowLayout.LEADING));
        this.tblProcesos.setPreferredScrollableViewportSize(new Dimension(400, 430));
        this.pnlDiagrama.add(this.jspDiagrama);
        Container content = getContentPane();
        content.add(this.pnlDiagrama);
        
        String[] titulo = new String[]{"Id"};
        this.dtm.setColumnIdentifiers(titulo);
        
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(Diagrama.this);
        }catch(Exception ex){
            System.out.println(ex);
        }
        setVisible(true);
        pack();
        
        
        
    }

    
    
    public void dibujarProcesos(){
        contarUltimo();
        
        
    }
    
    public void contarUltimo(){
        /*int ult = 0;
        this.auxiliar = this.objGestor.getTerminados().cabeza;
        while(this.auxiliar.siguiente.id != -1){
            this.auxiliar = this.auxiliar.siguiente;
            this.dtm.addColumn();
        }*/
        
    }
    
    public void eliminarProcesos(){
        this.dtm.setRowCount(1);
    }
    
    @Override
    public void actualizarDatos(Observable sujeto) {
        if(this.objGestor.equals(sujeto)){
            this.auxiliar = this.objGestor.getTerminados().cabeza;
            dibujarProcesos();
            System.out.println("Actualizando datos");
        }
    }
    
    
}
