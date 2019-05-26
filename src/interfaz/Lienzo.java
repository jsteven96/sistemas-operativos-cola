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
import logica.*;

/**
 *
 * @author root
 */
public class Lienzo extends Canvas {
    
    public Cola micola;
    public Nodo auxiliar;
    
    public Lienzo(){
        this.auxiliar = new Nodo();
        Nodo nodo1 = new Nodo();
        Nodo nodo2 = new Nodo();
        Nodo nodo3 = new Nodo();
        Nodo nodo4 = new Nodo();
        this.micola = new Cola();
        this.micola.agregarNodo(nodo1);
        this.micola.agregarNodo(nodo2);
        this.micola.agregarNodo(nodo3);
        this.micola.agregarNodo(nodo4);
    }
    
    @Override
    public void paint(Graphics g){
        g.setColor(Color.white);
        g.fillRect(0, 0, 2000, 200);
        g.setColor(Color.LIGHT_GRAY);
        g.drawRect(0, 0, 2000, 200);
        dibujarProcesos(g);

    }
    
     public void limpiar(){
        Graphics g = getGraphics();
        Dimension d = getSize();
        Color c = getBackground();
        g.setColor(c);
       
        g.fillRect(0, 0, 10, 10);
        repaint();
    }
     
     private void dibujarProcesos(Graphics g){
        int x = 5, y = 20;
        
        this.auxiliar = this.micola.cabeza;
        while(this.auxiliar.siguiente.id != -1){
            
            this.auxiliar = this.auxiliar.siguiente;            
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(x, y, 30, 30);
            g.setColor(Color.DARK_GRAY);
            g.drawString("Id: "+Integer.toString(this.auxiliar.id), x, y+40);
            g.drawString("S: "+Integer.toString(this.auxiliar.servicios), x, y+50);
            x += 50;
        }
        
    }
    
}
