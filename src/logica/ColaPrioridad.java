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
public class ColaPrioridad {

    public Nodo cabeza;
    private Nodo auxiliar;
    public int ultimo;

    public ColaPrioridad() {
        this.cabeza = new Nodo();
        this.cabeza.setId(-1);
        this.cabeza.setRafaga(0);
        this.cabeza.prioridad = 4;
        this.cabeza.siguiente = this.cabeza;
        this.ultimo = 0;
    }

    public void agregarNodo(Nodo nodo) {
        this.auxiliar = this.cabeza;
        if (this.auxiliar.siguiente.id == -1) {
            if (nodo.id == -2) {
                nodo.setId(this.ultimo);
                this.ultimo++;
            }
            nodo.siguiente = this.auxiliar.siguiente;
            this.auxiliar.siguiente = nodo;

        } else {
            while (this.auxiliar.siguiente.id != -1) {
                this.auxiliar = this.auxiliar.siguiente;
                if (this.auxiliar.siguiente.prioridad > nodo.prioridad) {
                    break;
                }
            }
            if (nodo.id == -2) {
                nodo.setId(this.ultimo);
                this.ultimo++;
            }
            nodo.siguiente = this.auxiliar.siguiente;
            this.auxiliar.siguiente = nodo;
        }

    }
    
    public void mostrarCola(){
        System.out.println(cabeza.id);
        auxiliar = cabeza;
        while(auxiliar.siguiente.id != -1){
            auxiliar = auxiliar.siguiente;
            System.out.println("ID "+auxiliar.id+" TL "+auxiliar.tiempoLlegada+" R "+auxiliar.rafaga+" TC "+auxiliar.tiempoComienzo+" TF "+auxiliar.tiempoFinal+ " P "+auxiliar.prioridad);
        }
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
    
    public int numElementos(){
        int contador = 0;
        auxiliar = cabeza;
        while(auxiliar.siguiente.id != -1){
            auxiliar = auxiliar.siguiente;
            contador++;
        }
        return contador;
    }
    
    public Nodo buscarNodo(int id){
        this.auxiliar = this.cabeza;
        while(this.auxiliar.siguiente.id != id){
            this.auxiliar = this.auxiliar.siguiente;
        }
        return this.auxiliar.siguiente;
    }
    
}
