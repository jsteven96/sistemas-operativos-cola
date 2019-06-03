/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.*;
import logica.*;

/**
 *
 * @author root
 */
public class DiagramaPanel extends JFrame{
    private Gestor objGestor;
    private PanelGantt miPanel;
    private JScrollPane jsp;
    
    
    public DiagramaPanel(Gestor inpObjGestor){
        super("Diagrama de Gantt");
        this.objGestor = inpObjGestor;
        this.miPanel = new PanelGantt(this.objGestor);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.jsp = new JScrollPane();
        this.jsp.setBounds(5, 112, 350, 180);
        jsp.setBounds(5, 112, 360, 200);
        jsp.setViewportView(this.miPanel);
        
        //this.objGestor.registrar(this);
        Container contenedor = getContentPane();
        contenedor.add(jsp);
        
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(DiagramaPanel.this);
        }catch(Exception ex){
            System.out.println(ex);
        }
        setBackground(Color.WHITE);
        setSize(800, 800);
        setVisible(true);
    }
    
}
