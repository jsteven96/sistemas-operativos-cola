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
         //Puntos de partida
        int x = 100, y = 20;
        int x1 = 5, y1 = 115;
        int x2 = 4, y2 = 210;
        
        g.setFont(new Font("Verdana", Font.PLAIN, 10));
        
        //Dibujado de titulos
        g.drawString("En ejecución", 3, 10);
        g.drawString("Cola de listos", 90, 10);
        g.drawString("Nodos bloqueados", 3, 105);
        g.drawString("Nodos programados", 3, 200);
        g.drawString("Tiempo", 3, 180);
        g.drawString(Integer.toString(this.objGestor.getTiempo()), 50, 180);
        
        //Nodo en ejecución
        g.drawRect(5, y, 30, 30);
        if(this.objGestor.getEnEjecucion() != null){
            if(this.objGestor.getEnEjecucion().id != -1){
            int i= this.objGestor.enEjecucion.id;
            g.setColor(new Color(i * 102 % 255, i * 75 % 255, i * 32 % 255));
            g.fillRect(10, y + 5, 20, 20);
            g.setColor(Color.black);
            g.drawString("Id: "+Integer.toString(this.objGestor.getEnEjecucion().id), 5, y+42);
            g.drawString("R: "+Integer.toString(this.objGestor.getEnEjecucion().getRafaga()), 5, y+53);
        }
        
        
        }
        
        
                
        this.auxiliar = this.objGestor.getListos().cabeza;
        while (this.auxiliar.siguiente.id != -1) {
            this.auxiliar = this.auxiliar.siguiente;
            int i= this.auxiliar.id % 1000;
            
            g.setColor(new Color(i * 102 % 255, i * 75 % 255, i * 32 % 255));
            g.fillRect(x, y, 20, 20);
            g.setColor(Color.DARK_GRAY);
            g.drawString("Id " + Integer.toString(this.auxiliar.id), x, y + 42);
            g.drawString("TL " + Integer.toString(this.auxiliar.tiempoLlegada), x, y + 53);
            g.drawString("R " + Integer.toString(this.auxiliar.rafaga), x, y + 64);
            g.drawString("E " + Integer.toString(this.auxiliar.tiempoEspera), x, y + 75);
            x += 40;
        }
        
        this.auxiliar = this.objGestor.getBloqueados().cabeza;
        
        while(this.auxiliar.siguiente.id != -1){
            this.auxiliar = this.auxiliar.siguiente;
            int i= this.auxiliar.id % 1000;
            g.setColor(new Color(i*102%255, i*75%255, i*32%255));
            g.fillRect(x1, y1, 30, 30);
            g.setColor(Color.DARK_GRAY);
            g.drawString("Id: " + Integer.toString(this.auxiliar.id), x1, y1 + 42);
            g.drawString("R: " + Integer.toString(this.auxiliar.rafaga), x1, y1 + 53);
            x1 += 50;
        }
        
        for(int i = 0; i < this.objGestor.procesosProgramados.size(); i++){
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(x2, y2, 20, 20);
            g.setColor(Color.DARK_GRAY);
            Nodo a = new Nodo();
            a = (Nodo) this.objGestor.procesosProgramados.get(i);
            g.drawString("Id " + Integer.toString(a.id), x2, y2 + 42);
            g.drawString("TL " + Integer.toString(a.tiempoLlegada), x2, y2 + 53);
            g.drawString("P " + Integer.toString(a.prioridad), x2, y2 + 64);
            x2 += 40;
        }
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
    
    public void desbloquearProceso(){
        this.objGestor.desbloquearProceso();
    }
    
    public void atender(){
        Thread hilo = new Thread(this.objGestor);
        hilo.start();
        
    }
    
}
