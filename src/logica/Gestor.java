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

    public Cola listos;
    public Cola terminados;
    public Nodo auxiliar;
    public Observador observador;
    

    public Gestor(Cola cola) {
        this.listos = cola;
        this.terminados = new Cola();
        this.auxiliar = new Nodo();
        this.observador = new Lienzo(this);
        
    }

    public Cola getListos() {
        return listos;
    }

    public void setListos(Cola listos) {
        this.listos = listos;
    }

    public Nodo getAuxiliar() {
        return auxiliar;
    }

    public void setAuxiliar(Nodo auxiliar) {
        this.auxiliar = auxiliar;
    }
    
    
    

    public void atender() {
        if (this.listos.numElementos() != 0) {
            this.auxiliar = this.listos.cabeza;
            while (auxiliar.siguiente != null) {
                if (auxiliar.siguiente.id != -1) {
                    auxiliar = auxiliar.siguiente;
                    if (auxiliar.servicios <= 3) {
                        System.out.println("Nodo " + auxiliar.id + " con " + auxiliar.servicios + " servicios atendido");
                        //this.terminados.agregarNodo(auxiliar);
                        this.listos.eliminarNodo(auxiliar.id);
                        
                        notificarObservador();
                        this.listos.mostrarCola();
                        
                    } else {
                        System.out.println("Nodo " + auxiliar.id + " con " + auxiliar.servicios + " servicios se volvera a la cola");
                        auxiliar.servicios -= 3;
                        this.listos.eliminarNodo(auxiliar.id);
                        Nodo copia = new Nodo();
                        copia.id = auxiliar.id;
                        copia.servicios = auxiliar.servicios;
                        this.listos.agregarNodo(copia);
                        notificarObservador();
                        this.listos.mostrarCola();

                    }
                } else {
                    if (listos.numElementos() > 0) {
                        auxiliar = auxiliar.siguiente.siguiente;
                        if (auxiliar.servicios <= 3) {
                            System.out.println("Nodo " + auxiliar.id + " con " + auxiliar.servicios + " servicios atendido");
                            this.listos.eliminarNodo(auxiliar.id);
                            notificarObservador();
                            this.listos.mostrarCola();
                        } else {
                            System.out.println("Nodo " + auxiliar.id + " con " + auxiliar.servicios + " servicios se volvera a la cola");
                            auxiliar.servicios -= 3;
                            this.listos.eliminarNodo(auxiliar.id);
                            Nodo copia = new Nodo();
                            copia.id = auxiliar.id;
                            copia.servicios = auxiliar.servicios;
                            this.listos.agregarNodo(copia);
                            notificarObservador();
                            this.listos.mostrarCola();

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
