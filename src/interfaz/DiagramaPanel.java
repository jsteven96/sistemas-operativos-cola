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
        this.miPanel.setPreferredSize(new Dimension(4000,2000));
       
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.jsp = new JScrollPane();
        
        jsp.setViewportView(this.miPanel);
        this.jsp.setPreferredSize(new Dimension(900,600));
        
        Container contenedor = getContentPane();
        contenedor.add(jsp);
        
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(DiagramaPanel.this);
        }catch(Exception ex){
            System.out.println(ex);
        }
        setBackground(Color.WHITE);
        pack();
        setVisible(true);
    }
    
}
