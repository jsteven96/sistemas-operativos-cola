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
    
    
    public Nodo(){
        rafaga = (int) (1 +Math.random() * 7);
        siguiente = null;
        //El id -2 significa que no tiene id aún
        id = -2;
        this.tiempoLlegada = (int) (1 + Math.random() * 5);
    }    

    public void setId(int id) {
        this.id = id;
    }
}
