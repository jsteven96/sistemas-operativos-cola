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
import logica.Temporizador;

/**
 *
 * @author root
 */
public class InterfazProcesos extends JFrame{
    private JPanel panel;
    private JLabel lblEliminar;
    private JTextArea taNumCrear;
    private JTextArea taIdEliminar;
    private JButton btnEliminar;
    private JButton btnAgregar;
    private JButton btnAtender;
    private JButton btnSalir;
    public static final String AGREGAR_PROCESOS = "Agregar procesos";
    public static final String ELIMINAR_PROCESOS = "Eliminar procesos";
    public static final String ATENDER_PROCESOS = "Atender procesos";
    public static final String SALIR = "Salir";
    private Lienzo miLienzo;
    private Cola miCola;
    private Gestor miGestor;
    private Tabla miTabla;
    private JTable tblGantt;
    private JScrollPane jScrollPanel;

    public InterfazProcesos() throws Exception{
        super("Ejercicio cola de Procesos");
      
        //Creación de controles
        panel = new JPanel();
        taNumCrear = new JTextArea(5, 40);
        taNumCrear.setFont(new Font("Verdana", Font.PLAIN, 14));
        taNumCrear.setLineWrap(true);
        taNumCrear.setWrapStyleWord(true);
        
        taIdEliminar = new JTextArea(5, 40);
        taIdEliminar.setFont(new Font("Verdana", Font.PLAIN, 14));
        taIdEliminar.setLineWrap(true);
        taIdEliminar.setWrapStyleWord(true);
        
        //Creación de etiquetas
        
        lblEliminar = new JLabel("Id de proceso a eliminar");
        lblEliminar.setFont(new Font("Verdana", Font.PLAIN, 14));
        
        //Creación de botones
        btnEliminar = new JButton(InterfazProcesos.ELIMINAR_PROCESOS);
        btnAgregar = new JButton(InterfazProcesos.AGREGAR_PROCESOS);
        btnAtender = new JButton(InterfazProcesos.ATENDER_PROCESOS);
        btnSalir = new JButton(InterfazProcesos.SALIR);
        Controlador objControlador = new Controlador(this);
        btnEliminar.addActionListener(objControlador);
        btnAgregar.addActionListener(objControlador);
        btnSalir.addActionListener(objControlador);
        btnAtender.addActionListener(objControlador);
        
        btnEliminar.setFont(new Font("Verdana", Font.BOLD, 14));
        btnAgregar.setFont(new Font("Verdana", Font.BOLD, 14));
        btnSalir.setFont(new Font("Verdana", Font.BOLD, 14));
        btnAtender.setFont(new Font("Verdana", Font.BOLD, 14));
        inicializar();
        //Creación del lienzo
        miLienzo = new Lienzo(miGestor);
        miLienzo.setSize(800, 200);
        
        
        //Creación del diagrama de Gantt
        miTabla = new Tabla(miGestor);
        this.miTabla.dibujarProcesos();
        this.jScrollPanel = new JScrollPane();
        this.jScrollPanel.setBounds(100,75,691,229);
        this.tblGantt = new JTable();
        this.tblGantt.setBounds(100,75,691,229);
        this.tblGantt.setModel(this.miTabla.getModel());
        this.jScrollPanel.setViewportView(tblGantt);
        
        JPanel panelBotones = new JPanel();
        
        GridBagLayout gridbag = new GridBagLayout();
        panelBotones.setLayout(gridbag);
        GridBagConstraints gbc = new GridBagConstraints();
        
        panelBotones.add(lblEliminar);
        panelBotones.add(btnAgregar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnSalir);
        panelBotones.add(btnAtender);
        
        panelBotones.add(taIdEliminar);
        
        panelBotones.add(miLienzo);
        
        panelBotones.add(jScrollPanel);
        
        
        gbc.insets.top = 2;
        gbc.insets.bottom = 2;
        gbc.insets.left = 5;
        gbc.insets.right = 5;
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        gridbag.setConstraints(btnAgregar, gbc);
        
        
        gbc.gridx = 2;
        gbc.gridy = 0;
        gridbag.setConstraints(lblEliminar, gbc);
        
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        gridbag.setConstraints(btnEliminar, gbc);
        
        gbc.gridx = 2;
        gbc.gridy = 1;
        gridbag.setConstraints(taIdEliminar, gbc);
        
        
        gbc.gridx = 1;
        gbc.gridy = 3;
        gridbag.setConstraints(btnSalir, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 2;
        gridbag.setConstraints(btnAtender, gbc);
        
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 5;
        gridbag.setConstraints(miLienzo, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gridbag.setConstraints(this.jScrollPanel, gbc);
        
        Container contenedor = getContentPane();
        contenedor.add(panelBotones, BorderLayout.CENTER);
        contenedor.add(this.jScrollPanel, BorderLayout.EAST);
        
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(InterfazProcesos.this);
        }catch(Exception ex){
            System.out.println(ex);
        }
        
        setSize(1600, 600);
        setVisible(true);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void inicializar(){
        this.miCola = new Cola();
        Nodo nodoA = new Nodo();
        Nodo nodoB = new Nodo();
        Nodo nodoC = new Nodo();
        Nodo nodoD = new Nodo();
        this.miCola.agregarNodo(nodoA);
        this.miCola.agregarNodo(nodoB);
        this.miCola.agregarNodo(nodoC);
        this.miCola.agregarNodo(nodoD);
        
        this.miGestor = new Gestor(miCola);
        
    }

    public void agregarProceso() {
        this.miLienzo.agregarNodo();
        this.miTabla.dibujarProcesos();
    }
    
    public void eliminarProceso(){
        if(!"".equals(taIdEliminar.getText())){
            this.miLienzo.eliminarNodo(Integer.parseInt(taIdEliminar.getText()));
            this.miTabla.dibujarProcesos();
           
        }
    }
    
    public void atenderProceso(){
        this.miLienzo.atender();
        
    }
}
