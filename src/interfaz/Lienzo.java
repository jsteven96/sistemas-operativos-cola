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
public class Lienzo extends Canvas implements Observador {

    public Nodo auxiliar;
    private Gestor objGestor;
    private boolean refrescar;

    public Lienzo(Gestor inpObjGestor) {
        this.objGestor = inpObjGestor;
        this.auxiliar = this.objGestor.getListosFIFO().cabeza;

        this.objGestor.registrar(this);

    }

    @Override
    public void paint(Graphics g) {
        limpiar();
        dibujarProcesos(g);
    }

    public void limpiar() {
        Graphics g = getGraphics();
        Dimension d = getSize();;
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, d.width, d.height);

    }

    private void dibujarProcesos(Graphics g) {
        //Puntos de partida
        int x = 100, y = 20;
        int x1 = 5, y1 = 315;
        int x2 = 4, y2 = 410;
        int yP = 120;
        int yF = 220;
        

        g.setFont(new Font("Verdana", Font.PLAIN, 10));

        //Dibujado de titulos
        g.drawString("En ejecución", 3, 10);
        g.drawString("Cola de listos RoundRobin", 90, 10);
        g.drawString("Cola de listos Prioridad", 90, 110);
        g.drawString("Cola de listos FIFO", 90, 210);
        
        
        g.drawString("Nodos bloqueados", 3, 305);
        g.drawString("Nodos programados", 3, 400);
        g.drawString("Tiempo", 3, 380);
        g.drawString(Integer.toString(this.objGestor.getTiempo()), 50, 380);

        //Nodo en ejecución
        g.drawRect(20, y, 30, 30);
        if (this.objGestor.getEnEjecucion() != null) {
            if (this.objGestor.getEnEjecucion().id != -1) {
                int i = this.objGestor.enEjecucion.id;
                g.setColor(new Color(i * 102 % 255, i * 75 % 255, i * 32 % 255));
                g.fillRect(25, y + 5, 20, 20);
                g.setColor(Color.black);
                g.drawString("Id: " + Integer.toString(this.objGestor.getEnEjecucion().id), 5, y + 42);
                g.setColor(Color.RED);
                g.drawString("R: " + Integer.toString(this.objGestor.getEnEjecucion().getRafaga()), 5, y + 53);
            }

        }
        
        //Dibujado de la cola de listos RoundRobin
        this.auxiliar = this.objGestor.getListosRoundRobin().cabeza;
        while (this.auxiliar.siguiente.id != -1) {
            this.auxiliar = this.auxiliar.siguiente;
            int i = this.auxiliar.id % 1000;
            g.setColor(new Color(i * 102 % 255, i * 75 % 255, i * 32 % 255));
            g.fillRect(x, y, 20, 20);
            g.setColor(Color.DARK_GRAY);
            g.drawString("Id " + Integer.toString(this.auxiliar.id), x, y + 42);
            g.drawString("TL " + Integer.toString(this.auxiliar.tiempoLlegada), x, y + 53);
            g.setColor(Color.RED);
            g.drawString("R " + Integer.toString(this.auxiliar.rafaga), x, y + 64);
            g.setColor(Color.DARK_GRAY);
            g.drawString("E " + Integer.toString(this.auxiliar.tiempoEspera), x, y + 75);
            x += 40;
        }
        
        x = 100;
        
        
        
        //Dibujado de la cola de listos de Prioridades
        this.auxiliar = this.objGestor.getListosPrioridad().cabeza;
        while (this.auxiliar.siguiente.id != -1) {
            this.auxiliar = this.auxiliar.siguiente;
            int i = this.auxiliar.id % 1000;
            g.setColor(new Color(i * 102 % 255, i * 75 % 255, i * 32 % 255));
            g.fillRect(x, yP, 20, 20);
            g.setColor(Color.DARK_GRAY);
            g.drawString("Id " + Integer.toString(this.auxiliar.id), x, yP + 42);
            g.drawString("TL " + Integer.toString(this.auxiliar.tiempoLlegada), x, yP + 53);
            g.setColor(Color.RED);
            g.drawString("P " + Integer.toString(this.auxiliar.prioridad), x, yP + 64);
            g.setColor(Color.DARK_GRAY);
            g.drawString("E " + Integer.toString(this.auxiliar.tiempoEspera), x, yP + 75);
            x += 40;
        }
        
        x = 100;
        
        //Dibujado de la cola de listos FIFO
        this.auxiliar = this.objGestor.getListosFIFO().cabeza;
        while (this.auxiliar.siguiente.id != -1) {
            this.auxiliar = this.auxiliar.siguiente;
            int i = this.auxiliar.id % 1000;
            g.setColor(new Color(i * 102 % 255, i * 75 % 255, i * 32 % 255));
            g.fillRect(x, yF, 20, 20);
            g.setColor(Color.DARK_GRAY);
            g.drawString("Id " + Integer.toString(this.auxiliar.id), x, yF + 42);
            g.drawString("TL " + Integer.toString(this.auxiliar.tiempoLlegada), x, yF + 53);
            g.setColor(Color.RED);
            g.drawString("R " + Integer.toString(this.auxiliar.rafaga), x, yF + 64);
            g.setColor(Color.DARK_GRAY);
            g.drawString("E " + Integer.toString(this.auxiliar.tiempoEspera), x, yF + 75);
            x += 40;
        }
        
        //Dibujado de la cola de bloqueados

        this.auxiliar = this.objGestor.getBloqueados().cabeza;
        while (this.auxiliar.siguiente.id != -1) {
            this.auxiliar = this.auxiliar.siguiente;
            int i = this.auxiliar.id % 1000;
            g.setColor(new Color(i * 102 % 255, i * 75 % 255, i * 32 % 255));
            g.fillRect(x1, y1, 30, 30);
            g.setColor(Color.DARK_GRAY);
            g.drawString("Id: " + Integer.toString(this.auxiliar.id), x1, y1 + 42);
            g.drawString("R: " + Integer.toString(this.auxiliar.rafaga), x1, y1 + 53);
            x1 += 50;
        }
        
        //Dibujado de los procesos programados de RoundRobin

        for (int i = 0; i < this.objGestor.procesosProgramadosR.size(); i++) {
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(x2, y2, 20, 20);
            g.setColor(Color.DARK_GRAY);
            Nodo a = new Nodo();
            a = (Nodo) this.objGestor.procesosProgramadosR.get(i);
            g.drawString("Id " + Integer.toString(a.id), x2, y2 + 42);
            g.drawString("TL " + Integer.toString(a.tiempoLlegada), x2, y2 + 53);
            g.drawString("R " + Integer.toString(a.rafaga), x2, y2 + 64);
            x2 += 40;
        }
        
        x2 = 4;
        
        
        //Dibujado de los procesos programados de Prioridad
        for (int i = 0; i < this.objGestor.procesosProgramadosP.size(); i++) {
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(x2, y2 + 70, 20, 20);
            g.setColor(Color.DARK_GRAY);
            Nodo a = new Nodo();
            a = (Nodo) this.objGestor.procesosProgramadosP.get(i);
            g.drawString("Id " + Integer.toString(a.id), x2, y2 + 101);
            g.drawString("TL " + Integer.toString(a.tiempoLlegada), x2, y2 + 112);
            g.drawString("R " + Integer.toString(a.rafaga), x2, y2 + 123);
            x2 += 40;
        }
        
        x2 = 4;
        //Dibujado de los procesos programados de FIFO
        for (int i = 0; i < this.objGestor.procesosProgramadosF.size(); i++) {
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(x2, y2 + 130, 20, 20);
            g.setColor(Color.DARK_GRAY);
            Nodo a = new Nodo();
            a = (Nodo) this.objGestor.procesosProgramadosF.get(i);
            g.drawString("Id " + Integer.toString(a.id), x2, y2 + 161);
            g.drawString("TL " + Integer.toString(a.tiempoLlegada), x2, y2 + 172);
            g.drawString("R " + Integer.toString(a.rafaga), x2, y2 + 183);
            x2 += 40;
        }
    }

    @Override
    public void actualizarDatos(Observable sujeto) {
        if (this.objGestor == sujeto) {
            this.auxiliar = this.objGestor.getListosFIFO().cabeza;
            repaint();
        }
    }

    public void agregarNodo() {
        this.objGestor.agregarNodo();
    }
    
    public void agregarNodoP(){
        this.objGestor.agregarNodoP();
    }
    
    public void agregarNodoF(){
        this.objGestor.agregarNodoF();
    }

    public void bloquearProceso() {
        this.objGestor.bloquearProceso();
    }

    public void desbloquearProceso() {
        this.objGestor.desbloquearProceso();
    }

    public void atender() {
        Thread hilo = new Thread(this.objGestor);
        hilo.start();

    }

}
