/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import logica.*;

/**
 *
 * @author root
 */
public class Lienzo extends Canvas implements Observador{
    
    public Cola micola;
    public Nodo auxiliar;
    
    private Gestor objGestor;
    
    public Lienzo(Gestor inpObjGestor){
        this.objGestor = inpObjGestor;
        this.micola = this.objGestor.cola;
        this.auxiliar = this.objGestor.cola.cabeza;
        
    }
    
    @Override
    public void paint(Graphics g){
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 400, 200);
        dibujarProcesos(g);

    }
    
     public void limpiar(){
        Graphics g = getGraphics();
        Dimension d = getSize();
        Color c = getBackground();
        g.setColor(c);
       
        g.fillRect(0, 0, 400, 200);
        
    }
     
     private void dibujarProcesos(Graphics g) {
        int x = 5, y = 20;
        this.auxiliar = this.objGestor.cola.cabeza;
        while (this.auxiliar.siguiente.id != -1) {

            this.auxiliar = this.auxiliar.siguiente;
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(x, y, 30, 30);
            g.setColor(Color.DARK_GRAY);
            g.drawString("Id: " + Integer.toString(this.auxiliar.id), x, y + 40);
            g.drawString("S: " + Integer.toString(this.auxiliar.servicios), x, y + 50);
            x += 50;
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(Lienzo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @Override
    public void actualizarDatos(Observable sujeto) {
        if(this.objGestor == sujeto){
            
            this.auxiliar = this.objGestor.cola.cabeza;
            repaint();
                        
        }
    }
    
    public void limpiarPantalla(){
        limpiar();
        //this.objGestor.atender();
        //System.out.println("Atender");
    }
    
    public void agregarNodo(){
        Nodo nuevo = new Nodo();
        this.objGestor.cola.agregarNodo(nuevo);
        repaint();
    }
    
    public void eliminarNodo(int indice){
        this.objGestor.cola.eliminarNodo(indice);
        repaint();
    }
    
}
