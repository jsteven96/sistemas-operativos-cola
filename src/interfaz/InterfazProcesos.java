/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

import java.awt.*;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.*;
import javax.swing.UIManager;
import logica.Cola;
import logica.Gestor;
import logica.Nodo;





/**
 *
 * @author root
 */
public class InterfazProcesos extends JFrame{
    private JPanel panel;
    private JLabel lblProcesos;
    private JLabel lblAgregar;
    private JLabel lblEliminar;
    private JTextArea taNumCrear;
    private JTextArea taIdEliminar;
    private JButton btnEliminar;
    private JButton btnAgregar;
    private JButton btnSalir;
    public static final String AGREGAR_PROCESOS = "Agregar procesos";
    public static final String ELIMINAR_PROCESO = "Eliminar procesos";
    public static final String SALIR = "Salir";
    private Lienzo miLienzo;
    private Cola miCola;
    private Gestor miGestor;

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
        
        //Creación de botones
        btnEliminar = new JButton(InterfazProcesos.ELIMINAR_PROCESO);
        btnAgregar = new JButton(InterfazProcesos.AGREGAR_PROCESOS);
        btnSalir = new JButton(InterfazProcesos.SALIR);
        Controlador objControlador = new Controlador(this);
        btnEliminar.addActionListener(objControlador);
        btnAgregar.addActionListener(objControlador);
        btnSalir.addActionListener(objControlador);
        
        //Creación de gestor
        this.miCola = new Cola();
        /*-----
        Pruebas
        -----*/
        Nodo nodoA = new Nodo();
        Nodo nodoB = new Nodo();
        Nodo nodoC = new Nodo();
        Nodo nodoD = new Nodo();
        this.miCola.agregarNodo(nodoA);
        this.miCola.agregarNodo(nodoB);
        this.miCola.agregarNodo(nodoC);
        this.miCola.agregarNodo(nodoD);
        
        this.miGestor = new Gestor(miCola);
        miLienzo = new Lienzo(miGestor);
        miLienzo.setSize(400, 200);
        
        
        JPanel panelBotones = new JPanel();
        
        GridBagLayout gridbag = new GridBagLayout();
        panelBotones.setLayout(gridbag);
        GridBagConstraints gbc = new GridBagConstraints();
        panelBotones.add(lblProcesos);
        panelBotones.add(lblAgregar);
        panelBotones.add(lblEliminar);
        
        panelBotones.add(btnAgregar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnSalir);
        
        panelBotones.add(taIdEliminar);
        
        panelBotones.add(miLienzo);
        /*
        gbc.insets.top = 5;
        gbc.insets.bottom = 5;
        gbc.insets.left = 5;
        gbc.insets.right = 5;
        */
        gbc.gridx = 0;
        gbc.gridy = 0;
        gridbag.setConstraints(lblProcesos, gbc);
        
        
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        gridbag.setConstraints(lblAgregar, gbc);
        
        
        
        gbc.gridx = 2;
        gbc.gridy = 0;
        gridbag.setConstraints(btnAgregar, gbc);
        
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        gridbag.setConstraints(lblEliminar, gbc);
        
        
        gbc.gridx = 2;
        gbc.gridy = 1;
        gridbag.setConstraints(btnEliminar, gbc);
        
        gbc.gridx = 3;
        gbc.gridy = 1;
        gridbag.setConstraints(taIdEliminar, gbc);
        
        
        gbc.gridx = 1;
        gbc.gridy = 2;
        gridbag.setConstraints(btnSalir, gbc);
        
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        gridbag.setConstraints(miLienzo, gbc);
        
        Container contenedor = getContentPane();
        contenedor.add(panelBotones, BorderLayout.CENTER);
        
        
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(InterfazProcesos.this);
        }catch(Exception ex){
            System.out.println(ex);
        }
        
        setSize(700, 500);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void agregarProceso() {
        this.miLienzo.agregarNodo();
    }
    
    public void eliminarProceso(){
        if(!"".equals(taIdEliminar.getText())){
            this.miLienzo.eliminarNodo(Integer.parseInt(taIdEliminar.getText()));
        }
    }
}
