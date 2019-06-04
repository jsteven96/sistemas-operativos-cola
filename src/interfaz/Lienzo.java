/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
    public Nodo auxiliar;
    private Gestor objGestor;
    private boolean refrescar;
    
    public Lienzo(Gestor inpObjGestor){
        this.objGestor = inpObjGestor;
        this.auxiliar = this.objGestor.getListos().cabeza;
        
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
        g.fillRect(0, 0, d.width, d.height);
        
    }
     
     private void dibujarProcesos(Graphics g) {
        
        int x = 5, y = 20;
        int x1 = 5, y1 = 105;
        g.setFont(new Font("Verdana", Font.PLAIN, 10));
        g.drawString("Cola de listos", 3, 10);
        g.drawRect(x, y, 30, 30);
        g.drawString("Id: "+Integer.toString(this.objGestor.getListos().cabeza.id), x, y+42);
        x +=50;
        g.drawString("Nodos bloqueados", 3, 95);
        g.setColor(Color.WHITE);
        g.fillRect(3, 160, 100, 20);
        g.setColor(Color.DARK_GRAY);
        g.drawString("Tiempo", 3, 170);
        g.drawString(Integer.toString(this.objGestor.getTiempo()), 3, 180);
        
        this.auxiliar = this.objGestor.getListos().cabeza;
        while (this.auxiliar.siguiente.id != -1) {
            this.auxiliar = this.auxiliar.siguiente;
            int i= this.auxiliar.id;
            
            g.setColor(new Color(i * 102 % 255, i * 75 % 255, i * 32 % 255));
            g.fillRect(x, y, 20, 20);
            g.setColor(Color.DARK_GRAY);
            g.drawString("Id " + Integer.toString(this.auxiliar.id), x, y + 42);
            g.drawString("TL " + Integer.toString(this.auxiliar.tiempoLlegada), x, y + 53);
            g.drawString("R " + Integer.toString(this.auxiliar.rafaga), x, y + 64);
            x += 40;
        }
        
        
        this.auxiliar = this.objGestor.getBloqueados().cabeza;
        
        while(this.auxiliar.siguiente.id != -1){
            this.auxiliar = this.auxiliar.siguiente;
            int i= this.auxiliar.siguiente.id;
            g.setColor(new Color(i*102%255, i*75%255, i*32%255));
            g.fillRect(x1, y1, 30, 30);
            g.setColor(Color.DARK_GRAY);
            g.drawString("Id: " + Integer.toString(this.auxiliar.id), x1, y1 + 42);
            g.drawString("S: " + Integer.toString(this.auxiliar.rafaga), x1, y1 + 53);
            x1 += 50;
        }
        
        /*for(int i = 0; i < this.objGestor.procesosProgramados.size(); i++){
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(x1, y1, 30, 30);
            g.setColor(Color.DARK_GRAY);
            Nodo a = new Nodo();
            a = (Nodo) this.objGestor.procesosProgramados.get(i);
            g.drawString("Id " + Integer.toString(a.id), x1, y1 + 42);
            g.drawString("TL " + Integer.toString(a.tiempoLlegada), x1, y1 + 53);
            x1 += 50;
        }*/
        /*
        this.auxiliar = this.objGestor.terminados.cabeza;
        while (this.auxiliar.siguiente.id != -1) {
            int i= this.auxiliar.siguiente.id;
            this.auxiliar = this.auxiliar.siguiente;
            g.setColor(new Color(i*102%255, i*75%255, i*32%255));
            g.fillRect(x1, y1, 30, 30);
            g.setColor(Color.DARK_GRAY);
            g.drawString("Id: " + Integer.toString(this.auxiliar.id), x1, y1 + 42);
            g.drawString("S: " + Integer.toString(this.auxiliar.rafaga), x1, y1 + 53);
            x1 += 50;
        }*/
        

    }

    @Override
    public void actualizarDatos(Observable sujeto) {
        if(this.objGestor == sujeto){
            this.auxiliar = this.objGestor.getListos().cabeza;
            repaint();
        }
        
        
        
    }
    
    public void agregarNodo(){
        this.objGestor.agregarNodo();
    }
    
    public void eliminarNodo(int indice){
        this.objGestor.eliminarNodo(indice);
        
    }
    
    public void bloquearProceso(){
        this.objGestor.bloquearProceso();
    }
    
    public void atender(){
        Thread hilo = new Thread(this.objGestor);
        hilo.start();
        
    }
    
}
