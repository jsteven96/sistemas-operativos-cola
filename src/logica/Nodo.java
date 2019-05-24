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
    public int servicios;
    public int id;
    public Nodo siguiente;
    
    public Nodo(){
        servicios = (int) (1+Math.random() * 5);
        siguiente = null;
    }    

    public void setId(int id) {
        this.id = id;
    }
    
    
}
