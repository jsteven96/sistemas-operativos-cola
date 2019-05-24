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
public class Cola {
    Nodo cabeza;
    Nodo auxiliar = new Nodo();

    public Cola() {
        this.cabeza = new Nodo();
        this.cabeza.id = -1;
        this.cabeza.siguiente = this.cabeza;
        this.cabeza.servicios = 0;
        
    }
    
    public void agregarNodo(Nodo nodo){
        auxiliar = cabeza;
        
        while(auxiliar.siguiente.id != -1){
            auxiliar = auxiliar.siguiente;    
        }
        nodo.siguiente = auxiliar.siguiente;
        nodo.setId(auxiliar.id +1);
        auxiliar.siguiente = nodo;
        
    }
    
    public void eliminarNodo(int id){
        auxiliar = cabeza;
        while(auxiliar.siguiente.id != id){
            auxiliar = auxiliar.siguiente;
        }
        auxiliar.siguiente = auxiliar.siguiente.siguiente;
        
    }
    
    public void mostrarCola(){
        auxiliar = cabeza;
        while(auxiliar.siguiente.id != -1){
            auxiliar = auxiliar.siguiente;
            System.out.println(auxiliar.id+" "+auxiliar.servicios);
        }
    }
}
