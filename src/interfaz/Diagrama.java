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
        this.auxiliar = this.objGestor.listos.cabeza;
        
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
        
        
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(Diagrama.this);
        }catch(Exception ex){
            System.out.println(ex);
        }
        
        
        setVisible(true);
        pack();
        
        
        
    }

    @Override
    public void actualizarDatos(Observable sujeto) {
        if(sujeto.equals(this.objGestor)){
            this.auxiliar = this.objGestor.listos.cabeza;
            dibujarProcesos();
        }
    }
    
    public void dibujarProcesos(){
        eliminarProcesos();
        this.auxiliar = this.objGestor.listos.cabeza;
        while(this.auxiliar.siguiente.id != -1){
            this.auxiliar = this.auxiliar.siguiente;
            Vector info = new Vector();
            info.add(this.auxiliar.id);
            this.dtm.addRow(info);
        }
    }
    
    public void eliminarProcesos(){
        this.dtm.setRowCount(0);
        eliminarCabeceras();
        dibujarCabeceras();
    }
    
    public void dibujarCabeceras(){
        this.auxiliar = this.objGestor.listos.cabeza;
        int col = 0;
        Vector ids = new Vector();
        ids.add(new String("Id"));
        while(this.auxiliar.siguiente.id != -1){
            this.auxiliar = this.auxiliar.siguiente;
            col += this.auxiliar.rafaga;
            
        }
        for(int i = 0; i < col; i++){
            ids.add(i);
        }
        this.dtm.setColumnIdentifiers(ids);
        TableColumnModel aux = this.tblProcesos.getColumnModel();
        for(int i = 0; i < this.dtm.getColumnCount(); i++){
            aux.getColumn(i).setPreferredWidth(20);
        }
    }
    
    public void eliminarCabeceras(){
        this.dtm.setColumnCount(0);
    }
    
}
