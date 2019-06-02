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
    private JPanel pnlBotones;
    private JPanel pnlLienzo;
    private JPanel pnlTabla;
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
    private Diagrama miDiagrama;

    public InterfazProcesos() throws Exception{
        super("Ejercicio cola de Procesos");
      
        //Creación de controles
        this.pnlBotones = new JPanel();
        this.pnlLienzo = new JPanel();
        this.pnlTabla = new JPanel();
        taNumCrear = new JTextArea();
        taNumCrear.setFont(new Font("Verdana", Font.PLAIN, 14));
        taNumCrear.setLineWrap(true);
        taNumCrear.setWrapStyleWord(true);
        
        taIdEliminar = new JTextArea(5, 5);
        taIdEliminar.setFont(new Font("Verdana", Font.PLAIN, 14));
        //taIdEliminar.setLineWrap(true);
        //taIdEliminar.setWrapStyleWord(true);
        
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
        miLienzo.setSize(400, 200);
        
        //Creación del diagrama de Gantt
        miTabla = new Tabla(miGestor);
        this.miTabla.dibujarProcesos();
        this.jScrollPanel = new JScrollPane();
        this.tblGantt = new JTable();
        this.tblGantt.setModel(this.miTabla.getModel());
        this.jScrollPanel.setViewportView(tblGantt);
        
        this.miDiagrama = new Diagrama(this.miGestor);
        this.miDiagrama.dibujarProcesos();
        this.miDiagrama.dibujarCabeceras();
        
        //Distribución de cada elemento en un panel particular
        this.pnlBotones.setLayout(new BoxLayout(this.pnlBotones, BoxLayout.Y_AXIS));
        this.pnlBotones.add(btnAgregar);
        this.pnlBotones.add(this.lblEliminar);
        
        this.pnlBotones.add(this.taIdEliminar);
        
        this.pnlBotones.add(this.btnEliminar);
        this.pnlBotones.add(this.btnAtender);
        this.pnlBotones.add(this.btnSalir);
        
        
        
        this.pnlLienzo.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.pnlLienzo.add(this.miLienzo);
        
        this.pnlTabla.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.pnlTabla.add(this.jScrollPanel);
        
        Container contenedor = getContentPane();
        contenedor.add(this.pnlBotones, BorderLayout.WEST);
        contenedor.add(this.pnlLienzo, BorderLayout.CENTER);
        contenedor.add(this.pnlTabla, BorderLayout.NORTH);
        
        
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(InterfazProcesos.this);
        }catch(Exception ex){
            System.out.println(ex);
        }
        
        pack();
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
        this.miDiagrama.dibujarProcesos();
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
