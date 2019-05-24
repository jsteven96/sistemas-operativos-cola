/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

import java.awt.*;
import javax.swing.*;




/**
 *
 * @author root
 */
public class InterfazProcesos extends JFrame {
    private JPanel panel;
    private JLabel lblProcesos;
    private JLabel lblAgregar;
    private JLabel lblEliminar;
    private JTextArea taNumCrear;
    private JTextArea taIdEliminar;
    private JButton btnEliminar;
    private JButton btnAgregar;

    public InterfazProcesos() throws Exception{
        super("Ejercicio cola de Procesos");
      
        //Creación de controles
        panel = new JPanel();
        taNumCrear = new JTextArea(5, 40);
        taNumCrear.setFont(new Font("Serif", Font.PLAIN, 14));
        taNumCrear.setLineWrap(true);
        taNumCrear.setWrapStyleWord(true);
        
        taIdEliminar = new JTextArea(5, 40);
        taIdEliminar.setFont(new Font("Serif", Font.PLAIN, 14));
        taIdEliminar.setLineWrap(true);
        taIdEliminar.setWrapStyleWord(true);
        
        //Creación de etiquetas
        lblProcesos = new JLabel("Procesos: ");
        lblAgregar = new JLabel("Agregar procesos ");
        lblEliminar = new JLabel("Eliminar procesos ");
        
        JPanel panelBoton = new JPanel();
        panelBoton.add(lblProcesos);
        panelBoton.add(lblAgregar);
        panelBoton.add(lblEliminar);
        
        Container contenedor = getContentPane();
        contenedor.add(panelBoton, BorderLayout.CENTER);
        
        setSize(400, 300);
        setVisible(true);
    }
    
    
}
