/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import javax.swing.JPanel;
import logica.Gestor;
import logica.Nodo;
import logica.Observable;

/**
 *
 * @author root
 */
public class PanelGantt extends JPanel implements Observador {
    private Gestor objGestor;
    private Nodo auxiliar;
    private Map<Integer, Integer> posiciones;
    private int fila;
    private Vector atendidos;
    
    public PanelGantt(Gestor inpObjGestor){
        this.objGestor = inpObjGestor;
        this.auxiliar = this.objGestor.getTerminados().cabeza;
        setBackground(Color.WHITE);
        this.objGestor.registrar(this);
        //setPreferredSize(new Dimension(4000, 1000));
        this.posiciones = new HashMap<>();
        this.fila = 0;
        this.atendidos = new Vector();
    }
    
    public void dibujarProcesos(Graphics g){
        limpiar(g);
        /*
        int x = 40, y = 40;
         
        int cont = 0;
        
        
        
        for(int i = 0; i < this.objGestor.estado.size(); i++){
            for(int j = 0; j < this.objGestor.estado.get(i).size(); j++){
                g.drawString(this.objGestor.estado.get(i).get(j)+"", x, y);
                
                x+=40;
            }
            x = 40;
            y += 30;
        }
        */
        int col = 0;
        int x1 = 40, y1 = 40;
        
        g.setFont(new Font("Verdana", Font.PLAIN, 13));
        g.setColor(Color.BLACK);
        
        for(int i = 0; i < this.objGestor.estado.size(); i++){
                g.drawString(this.objGestor.estado.get(i).get(0)+"", 10, y1+20);
            y1 += 22;
        }
        
        y1 = 40;
        
        for(int i = 0; i < this.objGestor.estado.size(); i++){
            for(int j = 1; j < this.objGestor.estado.get(i).size(); j++){
                
                if(this.objGestor.estado.get(i).get(j).equals(0)){
                    g.setColor(Color.LIGHT_GRAY);
                    g.drawRect(x1,y1, 18, 18);
                }
                if(this.objGestor.estado.get(i).get(j).equals(1)){
                    g.setColor(Color.LIGHT_GRAY);
                    g.fillRect(x1,y1, 20, 20);
                }
                if(this.objGestor.estado.get(i).get(j).equals(2)){
                    int s =(Integer) this.objGestor.estado.get(i).get(0);
                    g.setColor(new Color(s * 102 % 255, s * 75 % 255, s * 32 % 255));
                    g.fillRect(x1,y1, 20, 20);
                }
                if(this.objGestor.estado.get(i).get(j).equals(3)){
                    g.setColor(Color.RED);
                    g.fillRect(x1,y1, 20, 20);
                }
               x1+=21;
            }
            x1 = 40;
            y1 += 22;
        }
        
        x1 = 40;

        if (this.objGestor.getTerminados().numElementos() != 0) {
            this.auxiliar = this.objGestor.getTerminados().cabeza;

            while (this.auxiliar.siguiente.id != -1) {
                this.auxiliar = this.auxiliar.siguiente;

            }
            col += this.auxiliar.gettFinal().get(this.auxiliar.gettFinal().size() - 1);

            for (int i = 0; i <= col; i++) {
                g.setColor(Color.LIGHT_GRAY);
                g.drawRect((i * 21) + x1, 0, 20, 20);
                g.setColor(Color.BLACK);
                g.drawString(" " + Integer.toString(i), (i * 21) + x1, 11);
            }
        }

        
        /*
        
        this.auxiliar = this.objGestor.getTerminados().cabeza;
        while(this.auxiliar.siguiente.id != -1){
            cont = 0;
            col = 0;
            this.auxiliar = this.auxiliar.siguiente;
            g.setColor(Color.BLACK);
            g.drawString(Integer.toString(this.auxiliar.id), 0, y+13);
            for(int i = this.auxiliar.tiempoLlegada; i < this.auxiliar.getTiempoFinal(); i++){
                 if(i >= this.auxiliar.iniBloqueado && i <= this.auxiliar.finBloqueado){
                        g.setColor(Color.red);
                        g.fillRect(21*i+x, y, 20, 20);
                    }
                if(i < this.auxiliar.tiempoComienzo){
                    g.setColor(Color.BLACK);
                    g.setColor(Color.LIGHT_GRAY);
                    g.fillRect(21*i+x, y, 20, 20);
                    cont++;  
                }else{
                    g.setColor(Color.BLACK);
                    //g.drawString(Integer.toString(col), 21*i+x, 10);
                    int s= this.auxiliar.id % 1000;
                    
                    g.setColor(new Color(s * 102 % 255, s * 75 % 255, s * 32 % 255));
                    g.fillRect(21*i+x, y, 20, 20);
                    cont++; 
                }
            }
            y += 24;
        }*/
    }
    
    public void limpiar(Graphics g){
        Dimension d = getSize();
        Color c = getBackground();
        g.setColor(c);
        g.fillRect(0, 0, d.width, d.height);
        
    }
    
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        dibujarProcesos(g);
        
    }
    
    @Override
    public void paint(Graphics g){
        dibujarProcesos(g);
    }

    @Override
    public void actualizarDatos(Observable sujeto) {
        if(this.objGestor.equals(sujeto)){
            repaint();
        }
    }
    
}
