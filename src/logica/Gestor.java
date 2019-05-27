/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import interfaz.Lienzo;
import interfaz.Observador;

/**
 *
 * @author root
 */
public class Gestor implements Observable {

    public Cola cola;
    public Nodo auxiliar;
    public Observador observador;
    

    public Gestor(Cola cola) {
        this.cola = cola;
        this.auxiliar = new Nodo();
        this.observador = new Lienzo(this);
        
    }

    public Cola getCola() {
        return cola;
    }

    public void setCola(Cola cola) {
        this.cola = cola;
    }

    public Nodo getAuxiliar() {
        return auxiliar;
    }

    public void setAuxiliar(Nodo auxiliar) {
        this.auxiliar = auxiliar;
    }
    
    
    

    public void atender() {
        if (this.cola.numElementos() != 0) {
            this.auxiliar = this.cola.cabeza;
            while (auxiliar.siguiente != null) {
                if (auxiliar.siguiente.id != -1) {
                    auxiliar = auxiliar.siguiente;
                    if (auxiliar.servicios <= 3) {
                        System.out.println("Nodo " + auxiliar.id + " con " + auxiliar.servicios + " servicios atendido");
                        this.cola.eliminarNodo(auxiliar.id);
                        notificarObservador();
                        this.cola.mostrarCola();
                    } else {
                        System.out.println("Nodo " + auxiliar.id + " con " + auxiliar.servicios + " servicios se volvera a la cola");
                        auxiliar.servicios -= 3;
                        this.cola.eliminarNodo(auxiliar.id);
                        Nodo copia = new Nodo();
                        copia.id = auxiliar.id;
                        copia.servicios = auxiliar.servicios;
                        this.cola.agregarNodo(copia);
                        notificarObservador();
                        this.cola.mostrarCola();

                    }
                } else {
                    if (cola.numElementos() > 0) {
                        auxiliar = auxiliar.siguiente.siguiente;
                        if (auxiliar.servicios <= 3) {
                            System.out.println("Nodo " + auxiliar.id + " con " + auxiliar.servicios + " servicios atendido");
                            this.cola.eliminarNodo(auxiliar.id);
                            notificarObservador();
                            this.cola.mostrarCola();
                        } else {
                            System.out.println("Nodo " + auxiliar.id + " con " + auxiliar.servicios + " servicios se volvera a la cola");
                            auxiliar.servicios -= 3;
                            this.cola.eliminarNodo(auxiliar.id);
                            Nodo copia = new Nodo();
                            copia.id = auxiliar.id;
                            copia.servicios = auxiliar.servicios;
                            this.cola.agregarNodo(copia);
                            notificarObservador();
                            this.cola.mostrarCola();

                        }
                    } else {
                        break;
                    }
                }

            }
        }
    }
    
    

    @Override
    public void notificarObservador() {
        observador.actualizarDatos(this);
        
    }

}
