/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import interfaz.Lienzo;
import interfaz.Observador;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author root
 */
public class Gestor implements Observable, Runnable {

    public Cola listos;
    public Cola terminados;
    public Nodo auxiliar;
    public Observador miObservador;
    
    //--------
    int y = 0;
    

    public Gestor(Cola cola) {
        this.listos = cola;
        this.terminados = new Cola();
        this.auxiliar = new Nodo();
        
        
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
                         Nodo copia = new Nodo();
                        copia.id = auxiliar.id;
                        copia.servicios = auxiliar.servicios;
                        this.terminados.agregarNodo(copia);
                        this.listos.eliminarNodo(auxiliar.id);
                        notificarObservador();
                        this.listos.mostrarCola();
                        System.out.println("-----------");
                        this.terminados.mostrarCola();
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Gestor.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
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
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Gestor.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                } else {
                    if (listos.numElementos() > 0) {
                        auxiliar = auxiliar.siguiente.siguiente;
                        if (auxiliar.servicios <= 3) {
                            System.out.println("Nodo " + auxiliar.id + " con " + auxiliar.servicios + " servicios atendido");
                            this.listos.eliminarNodo(auxiliar.id);
                            notificarObservador();
                            this.listos.mostrarCola();
                            try {
                            Thread.sleep(2000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Gestor.class.getName()).log(Level.SEVERE, null, ex);
                        }
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
                            try {
                            Thread.sleep(2000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Gestor.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        }
                    } else {
                        break;
                    }
                }

            }
        }
    }
    
    public void pruebaNotificacion(){
        notificarObservador();
        
        this.listos.eliminarNodo(y);
        y += 1;
    }
    
    

    @Override
    public void notificarObservador() {
        miObservador.actualizarDatos(this);
        System.out.println("Notificando");
        
        
    }

    @Override
    public void registrar(Observador obs) {
        miObservador = obs;
    }

    @Override
    public void run() {
        atender();
    }

}
