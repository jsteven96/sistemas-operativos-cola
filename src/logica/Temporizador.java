/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import interfaz.Observador;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author root
 */
public class Temporizador implements Observable, Runnable {
    public int tiempo;
    
    public Temporizador(){
        this.tiempo = 0;
    }

    @Override
    public void notificarObservadores() {
        
    }

    @Override
    public void registrar(Observador obs) {
        
    }
    
    public void contar() {
        while (true) {
            this.tiempo++;
            System.out.println("Tiempo " + this.tiempo);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Temporizador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @Override
    public void run() {
        contar();
    }   
}
