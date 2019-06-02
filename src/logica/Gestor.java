/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import interfaz.Lienzo;
import interfaz.Observador;
import java.util.ArrayList;
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
    public ArrayList observadores;
    //public Observador miObservador;
    public int retardo;
    public int tiempo;
    public ArrayList procesosProgramados;
    
    
    public Gestor(Cola cola) {
        this.listos = cola;
        this.terminados = new Cola();
        this.auxiliar = new Nodo();
        this.retardo = 1000;
        this.tiempo = 0;
        this.bloqueados = new Cola();
        this.observadores = new ArrayList();
        this.procesosProgramados = new ArrayList();
        
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
                        notificarObservadores();
                        int lim = this.auxiliar.rafaga;
                        for (int i = 0; i < lim; i++) {
                            
                            this.auxiliar.setRafaga(this.auxiliar.rafaga - 1);
                            notificarObservadores();
                            try {
                                Thread.sleep(this.retardo);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(Gestor.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        }
                        Nodo copia = new Nodo();
                        copia.id = auxiliar.id;
                        copia.rafaga = auxiliar.rafaga;
                        this.terminados.agregarNodo(copia);
                        this.listos.eliminarNodo(auxiliar.id);
                        notificarObservadores();
                        
                        //System.out.println("Nodo " + auxiliar.id + " con " + auxiliar.rafaga + " rafaga atendido");
                        /*this.listos.mostrarCola();
                        System.out.println("-----------");
                        this.terminados.mostrarCola();*/

                    } else {
                        //System.out.println("Nodo " + auxiliar.id + " con " + auxiliar.rafaga + " rafaga se volvera a la cola");
                        notificarObservadores();
                        for (int i = 0; i < 3; i++) {
                            
                            this.auxiliar.setRafaga(this.auxiliar.rafaga - 1);
                            notificarObservadores();
                            try {
                                Thread.sleep(this.retardo);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(Gestor.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        }

                        this.listos.eliminarNodo(auxiliar.id);
                        Nodo copia = new Nodo();
                        copia.id = auxiliar.id;
                        copia.rafaga = auxiliar.rafaga;
                        this.listos.agregarNodo(copia);
                        notificarObservadores();
                        //this.listos.mostrarCola();


                    }
                } else {
                    if (listos.numElementos() > 0) {
                        notificarObservadores();
                        auxiliar = auxiliar.siguiente.siguiente;
                        if (auxiliar.rafaga <= 3) {
                            int lim = auxiliar.rafaga;
                            for (int i = 0; i < lim; i++) {
                                try {
                                    Thread.sleep(this.retardo);
                                } catch (InterruptedException ex) {
                                    Logger.getLogger(Gestor.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                auxiliar.setRafaga(auxiliar.rafaga - 1);
                                notificarObservadores();

                            }
                            //System.out.println("Nodo " + auxiliar.id + " con " + auxiliar.rafaga + " rafaga atendido");
                            this.listos.eliminarNodo(auxiliar.id);
                            notificarObservadores();
                            this.listos.mostrarCola();
                            

                        } else {
                            //System.out.println("Nodo " + auxiliar.id + " con " + auxiliar.rafaga + " rafaga se volvera a la cola");
                            notificarObservadores();
                            for (int i = 0; i < 3; i++) {
                                try {
                                    Thread.sleep(this.retardo);
                                } catch (InterruptedException ex) {
                                    Logger.getLogger(Gestor.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                auxiliar.setRafaga(auxiliar.rafaga - 1);
                                notificarObservadores();

                            }

                            this.listos.eliminarNodo(auxiliar.id);
                            Nodo copia = new Nodo();
                            copia.id = auxiliar.id;
                            copia.rafaga = auxiliar.rafaga;
                            this.listos.agregarNodo(copia);
                            notificarObservadores();
                            //this.listos.mostrarCola();
                            

                        }
                    } else {
                        notificarObservadores();
                        break;
                    }
                }

            }
        }
    }
    
    @Override
    public void notificarObservadores() {
        for(int i = 0; i < this.observadores.size(); i++){
            Observador observador = (Observador) this.observadores.get(i);
            observador.actualizarDatos(this);
        }
        //miObservador.actualizarDatos(this);
    }

    @Override
    public void registrar(Observador obs) {
        //miObservador = obs;
        this.observadores.add(obs);
    }

    @Override
    public void run() {
        atender();
    }

}
