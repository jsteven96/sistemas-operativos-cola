/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

/**
 *
 * @author root
 */
public class Nodo {
    public int rafaga;
    public int id;
    public Nodo siguiente;
    public int tiempoLlegada;
    public int tiempoComienzo;
    public int tiempoRetorno;
    public int tiempoFinal;
    public int tiempoEspera;
    
    
    public Nodo(){
        this.rafaga = (int) (1 +Math.random() * 7);
        //this.siguiente = null;
        //El id -2 significa que no tiene id aún
        this.id = -2;
        this.tiempoLlegada = (int) (1 + Math.random() * 5);
        this.tiempoComienzo = 0;
        this.tiempoFinal = 0;
        this.tiempoRetorno = 0;
        this.tiempoEspera = 0;
    }    

    public void setId(int id) {
        this.id = id;
    }

    public void setRafaga(int rafaga) {
        this.rafaga = rafaga;
    }
    
    
}
