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
    public Nodo cabeza;
    Nodo auxiliar;
    int ultimo;

    public Cola() {
        this.cabeza = new Nodo();
        this.cabeza.id = -1;
        this.cabeza.siguiente = this.cabeza;
        this.cabeza.rafaga = 0;
        this.auxiliar = new Nodo();
        this.ultimo = 0;
    }
    
    public void agregarNodo(Nodo nodo){
        auxiliar = cabeza;
        
        while(auxiliar.siguiente.id != -1){
            auxiliar = auxiliar.siguiente;    
        }
        
        if(nodo.id == -2){
            nodo.setId(this.ultimo);
            this.ultimo++;
        }
        nodo.siguiente = auxiliar.siguiente;
        auxiliar.siguiente = nodo;
        
    }
    
    public void eliminarNodo(int id){
        
        auxiliar = cabeza;
        while(auxiliar.siguiente.id != id){
            if(auxiliar.siguiente.id == -1){
                break;
            }
            auxiliar = auxiliar.siguiente;
        }
        if(auxiliar.siguiente.id != -1){
            auxiliar.siguiente = auxiliar.siguiente.siguiente;    
        }
    }
    
    public void mostrarCola(){
        System.out.println(cabeza.id);
        auxiliar = cabeza;
        while(auxiliar.siguiente.id != -1){
            auxiliar = auxiliar.siguiente;
            System.out.println("ID "+auxiliar.id+" TL "+auxiliar.tiempoLlegada+" R "+auxiliar.rafaga+" TC "+auxiliar.tiempoComienzo+" TF "+auxiliar.tiempoFinal);
        }
    }
    
    public int numElementos(){
        int contador = 0;
        auxiliar = cabeza;
        while(auxiliar.siguiente.id != -1){
            auxiliar = auxiliar.siguiente;
            contador++;
        }
        return contador;
    }
}
