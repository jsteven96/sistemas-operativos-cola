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
    private int limHorizontal;
    private int limVertical;
    private Nodo auxiliar;
    
    public PanelGantt(Gestor inpObjGestor){
        this.objGestor = inpObjGestor;
        this.auxiliar = this.objGestor.getTerminados().cabeza;
        setBackground(Color.WHITE);
        this.objGestor.registrar(this);
        setPreferredSize(new Dimension(1000, 1000));
    }
    
    public void dibujarProcesos(Graphics g){
        limpiar(g);
        int ancho = 0;
        int x = 5, y = 20;
        int x1 = 5, y1 = 105;
        g.setFont(new Font("Verdana", Font.PLAIN, 10));
        this.auxiliar = this.objGestor.getTerminados().cabeza;
        
        while (this.auxiliar.siguiente.id != -1) {
            this.auxiliar = this.auxiliar.siguiente;
            int i= this.auxiliar.id;
            ancho = ancho + this.auxiliar.rafaga;
            g.setColor(new Color(i * 102 % 255, i * 75 % 255, i * 32 % 255));
            g.fillRect(x, y, 20, 20);
            g.setColor(Color.DARK_GRAY);
            g.drawString("Id " + Integer.toString(this.auxiliar.id), x, y + 42);
            g.drawString("TL " + Integer.toString(this.auxiliar.tiempoLlegada), x, y + 53);
            g.drawString("R " + Integer.toString(this.auxiliar.rafaga), x, y + 64);
            x += 40;
            
        }
        
        
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
