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
    public Cola bloqueados;
    public Nodo auxiliar;
    public Observador miObservador;
    public int retardo;
    public int tiempo;
    
    
    public Gestor(Cola cola) {
        this.listos = cola;
        this.terminados = new Cola();
        this.auxiliar = new Nodo();
        this.retardo = 2000;
        this.tiempo = 0;
        this.bloqueados = new Cola();
        
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
    
    public void calcularTiempo(){
        while(true){
            this.tiempo++;
            System.out.println(this.tiempo);
        }
    }
    
    

    public void atender() {
        
        if (this.listos.numElementos() != 0) {
            this.auxiliar = this.listos.cabeza;
            while (auxiliar.siguiente != null) {
                if (auxiliar.siguiente.id != -1) {
                    auxiliar = auxiliar.siguiente;
                    if (auxiliar.rafaga <= 3) {
                        //System.out.println("Nodo " + auxiliar.id + " con " + auxiliar.rafaga + " rafaga atendido");
                         Nodo copia = new Nodo();
                        copia.id = auxiliar.id;
                        copia.rafaga = auxiliar.rafaga;
                        this.terminados.agregarNodo(copia);
                        this.listos.eliminarNodo(auxiliar.id);
                        notificarObservador();
                        /*this.listos.mostrarCola();
                        System.out.println("-----------");
                        this.terminados.mostrarCola();*/
                        try {
                            Thread.sleep(this.retardo);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Gestor.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                    } else {
                        //System.out.println("Nodo " + auxiliar.id + " con " + auxiliar.rafaga + " rafaga se volvera a la cola");
                        auxiliar.rafaga -= 3;
                        this.listos.eliminarNodo(auxiliar.id);
                        Nodo copia = new Nodo();
                        copia.id = auxiliar.id;
                        copia.rafaga = auxiliar.rafaga;
                        this.listos.agregarNodo(copia);
                        notificarObservador();
                        //this.listos.mostrarCola();
                        try {
                            Thread.sleep(this.retardo);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Gestor.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                } else {
                    if (listos.numElementos() > 0) {
                        auxiliar = auxiliar.siguiente.siguiente;
                        if (auxiliar.rafaga <= 3) {
                            //System.out.println("Nodo " + auxiliar.id + " con " + auxiliar.rafaga + " rafaga atendido");
                            this.listos.eliminarNodo(auxiliar.id);
                            notificarObservador();
                            this.listos.mostrarCola();
                            try {
                            Thread.sleep(this.retardo);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Gestor.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        } else {
                            //System.out.println("Nodo " + auxiliar.id + " con " + auxiliar.rafaga + " rafaga se volvera a la cola");
                            auxiliar.rafaga -= 3;
                            this.listos.eliminarNodo(auxiliar.id);
                            Nodo copia = new Nodo();
                            copia.id = auxiliar.id;
                            copia.rafaga = auxiliar.rafaga;
                            this.listos.agregarNodo(copia);
                            notificarObservador();
                            //this.listos.mostrarCola();
                            try {
                            Thread.sleep(this.retardo);
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
    
    @Override
    public void notificarObservador() {
        miObservador.actualizarDatos(this);
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
