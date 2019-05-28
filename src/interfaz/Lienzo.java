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
    private boolean refrescar;
    public int x = 10;
    public int y = 10;
    
    
    public Lienzo(Gestor inpObjGestor){
        this.objGestor = inpObjGestor;
        this.micola = this.objGestor.listos;
        this.auxiliar = this.objGestor.listos.cabeza;
        this.objGestor.registrar(this);
        
    }
    
    @Override
    public void paint(Graphics g){
        limpiar();
        dibujarProcesos(g);
        
    }
    
     public void limpiar(){
        Graphics g = getGraphics();
        Dimension d = getSize();;
        g.setColor(Color.WHITE);
       
        g.fillRect(0, 0, 400, 200);
        
    }
     
     private void dibujarProcesos(Graphics g) {
        int x = 5, y = 20;
        int x1 = 5, y1 = 80;
        this.auxiliar = this.objGestor.listos.cabeza;
        while (this.auxiliar.siguiente.id != -1) {

            this.auxiliar = this.auxiliar.siguiente;
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(x, y, 30, 30);
            g.setColor(Color.DARK_GRAY);
            g.drawString("Id: " + Integer.toString(this.auxiliar.id), x, y + 40);
            g.drawString("S: " + Integer.toString(this.auxiliar.servicios), x, y + 50);
            x += 50;
        }
        
        this.auxiliar = this.objGestor.terminados.cabeza;
        while (this.auxiliar.siguiente.id != -1) {

            this.auxiliar = this.auxiliar.siguiente;
            g.setColor(Color.black);
            g.fillRect(x1, y1, 30, 30);
            g.setColor(Color.DARK_GRAY);
            g.drawString("Id: " + Integer.toString(this.auxiliar.id), x1, y1 + 40);
            g.drawString("S: " + Integer.toString(this.auxiliar.servicios), x1, y1 + 50);
            x1 += 50;
        }
        

    }

    @Override
    public void actualizarDatos(Observable sujeto) {
        if(this.objGestor == sujeto){
            this.auxiliar = this.objGestor.listos.cabeza;
            repaint();
        }
        
        
        
    }
    
    public void agregarNodo(){
        Nodo nuevo = new Nodo();
        this.objGestor.listos.agregarNodo(nuevo);
        repaint();
    }
    
    public void eliminarNodo(int indice){
        this.objGestor.listos.eliminarNodo(indice);
        repaint();
    }
    
    public void atender(){
        Thread hilo = new Thread(this.objGestor);
        hilo.start();
    }
    
}
