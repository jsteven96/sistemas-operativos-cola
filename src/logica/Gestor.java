/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import interfaz.Observador;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author root
 */

public class Gestor implements Observable, Runnable {

    //private ColaPrioridad listos;
    private Cola listos;
    private Cola terminados;
    private Cola bloqueados;
    public Nodo enEjecucion;
    public Nodo auxiliar;
    public ArrayList observadores;
    public int retardo;
    private int tiempo;
    public ArrayList procesosProgramados;
    public int pp;
    public int rafagaEjecutada;
    public ArrayList<ArrayList> estado;
    public boolean atendiendo;
    public ArrayList<Integer> procesos;

    public Gestor(Cola cola) {
        this.listos = cola;
        this.terminados = new Cola();
        
        this.retardo = 1000;
        this.tiempo = 0;
        this.bloqueados = new Cola();
        this.observadores = new ArrayList();
        this.procesosProgramados = new ArrayList();
        this.pp = 100;
        this.rafagaEjecutada = 0;
        this.estado = new ArrayList();
        this.atendiendo = false;
        this.procesos = new ArrayList();
    }

    public Cola getListos() {
        return listos;
    }

    public void setListos(Cola listos) {
        this.listos = listos;
    }

    public Nodo getEnEjecucion() {
        return enEjecucion;
    }

    public void setEnEjecucion(Nodo enEjecucion) {
        this.enEjecucion = enEjecucion;
    }

    public Cola getTerminados() {
        return terminados;
    }

    public void setTerminados(Cola terminados) {
        this.terminados = terminados;
    }

    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    public Cola getBloqueados() {
        return bloqueados;
    }

    public void setBloqueados(Cola bloqueados) {
        this.bloqueados = bloqueados;
    }

    public void bloquearProceso() {
        
        Nodo copiaBloqueados;
        if (this.enEjecucion.getRafaga() != 0) {
            copiaBloqueados = this.enEjecucion.clone();
            copiaBloqueados.bloqueado = true;
            copiaBloqueados.setIniBloq(this.tiempo);
            copiaBloqueados.setVecesBloqueado(copiaBloqueados.getVecesBloqueado()+1);
            copiaBloqueados.gettFinal().add(this.tiempo);
            copiaBloqueados.getRafagaEjecutada().add(this.rafagaEjecutada);
            copiaBloqueados.gettRetorno().add(copiaBloqueados.gettFinal().get(copiaBloqueados.gettFinal().size()-1)-copiaBloqueados.getTiempoLlegada());
            copiaBloqueados.gettEspera().add(copiaBloqueados.gettRetorno().get(copiaBloqueados.gettRetorno().size()-1)-copiaBloqueados.getRafagaParcial());
            this.bloqueados.agregarNodo(copiaBloqueados);
            this.atendiendo = false;
            
            this.rafagaEjecutada = 0;
            notificarGantt();
        }
    }

    public void desbloquearProceso() {
        Nodo copia;
        copia = this.getBloqueados().cabeza.siguiente.clone();
        
        copia.setTiempoComienzo(this.tiempo);
        copia.setFinBloq(this.tiempo);
        copia.setTiempoBloqueado(copia.finBloq-copia.iniBloq);
        copia.setTiempoEspera(0);
        
        copia.setTiempoFinal(copia.tiempoComienzo + copia.rafagaParcial);
        
        this.bloqueados.eliminarNodo(copia.id);
        this.listos.agregarNodo(copia);
        
    }
    
    public void atender() {
        while (true) {
            try {
                Thread.sleep(this.retardo);
            } catch (InterruptedException ex) {
                Logger.getLogger(Gestor.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
                //------------Nuevo código para Round Robin
                if(this.rafagaEjecutada == 4  && this.enEjecucion.rafaga>0){
                    Nodo copia = this.enEjecucion.clone();
                    this.enEjecucion.setId(-1);
                    copia.bloqueado = true;
                    copia.tiempoEspera = 0;
                    copia.gettFinal().add(this.tiempo);
                    copia.getRafagaEjecutada().add(this.rafagaEjecutada);
                    copia.gettRetorno().add(copia.gettFinal().get(copia.gettFinal().size()-1)-copia.getTiempoLlegada());
                    copia.gettEspera().add(copia.gettRetorno().get(copia.gettRetorno().size()-1)-copia.getRafagaParcial());
                    
                    this.listos.agregarNodo(copia);
                    this.atendiendo = false;
                    this.rafagaEjecutada = 0;
                }
                //------------
            
               
            
            guardarProcesos();
            notificarGantt();
            //Si no hay nadie siendo atendido y el que sigue de la cabeza de listos no es la misma cabeza
            //pase en ejecución al siguiente de la cabeza
            if (this.atendiendo == false && this.listos.cabeza.siguiente.id != -1) {
                this.enEjecucion = this.listos.cabeza.siguiente.clone();
                this.enEjecucion.listo = false;
                this.enEjecucion.enEjecucion = true;
                this.enEjecucion.gettComienzo().add(this.tiempo);
                if(this.enEjecucion.bloqueado == false){
                    dibujarTiempo();
                }
                dibujarTiempoBloqueado();
                this.enEjecucion.setTiempoBloqueado(0);
                dibujarTiempoDeEspera();
                this.listos.eliminarNodo(this.enEjecucion.id);
                this.atendiendo = true;
                notificarObservadores();
            }
            
           
            
            //Si hay alguien en atención, disminuya la rafaga en uno y aumente la rafaga ejecutada general
            
            if(this.atendiendo == true){
                if (this.enEjecucion.id != -1) {
                this.enEjecucion.setRafaga(this.enEjecucion.getRafaga() - 1);
                this.enEjecucion.setRafagaParcial(this.enEjecucion.getRafagaParcial() + 1);
                this.rafagaEjecutada++;
                actualizarTiempoEspera();
                actualizarEstado();
                notificarObservadores();
                
                /*
                //------------Nuevo código para Round Robin
                if(this.rafagaEjecutada == 4  && this.enEjecucion.rafaga>0){
                    Nodo copia = this.enEjecucion.clone();
                    this.enEjecucion.setId(-1);
                    copia.bloqueado = true;
                    copia.tiempoEspera = 0;
                    copia.gettFinal().add(this.tiempo+1);
                    copia.getRafagaEjecutada().add(this.rafagaEjecutada);
                    copia.gettRetorno().add(copia.gettFinal().get(copia.gettFinal().size()-1)-copia.getTiempoLlegada());
                    copia.gettEspera().add(copia.gettRetorno().get(copia.gettRetorno().size()-1)-copia.getRafagaParcial());
                    
                    this.listos.agregarNodo(copia);
                    this.atendiendo = false;
                    this.rafagaEjecutada = 0;
                }
                //------------
            
               */
                
                if (this.enEjecucion.rafaga == 0) {
                    Nodo copia = this.enEjecucion.clone();
                    this.enEjecucion.setId(-1);
                    copia.gettFinal().add(this.tiempo+1);
                    copia.getRafagaEjecutada().add(this.enEjecucion.getRafagaParcial());
                    copia.gettRetorno().add(copia.gettFinal().get(copia.gettFinal().size()-1)-copia.getTiempoLlegada());
                    copia.gettEspera().add(copia.gettRetorno().get(copia.gettRetorno().size()-1)-copia.getRafagaParcial());
                    this.terminados.agregarNodo(copia);
                    notificarObservadores();
                    this.atendiendo = false;
                    this.rafagaEjecutada = 0;
                    
                }

            }
            }
            this.setTiempo(this.getTiempo() + 1);
            encolarProgramados();
            
            notificarObservadores();
        }
    }

    public void guardarProcesos(){
        this.auxiliar = this.getListos().cabeza;
        while(this.auxiliar.siguiente.id != -1){
            this.auxiliar = this.auxiliar.siguiente;
            if(!this.procesos.contains(this.auxiliar.id)){
             this.procesos.add(this.auxiliar.id);
             ArrayList proceso  = new ArrayList();
             proceso.add(this.auxiliar.id);
             this.estado.add(proceso);
            }
        }
        
    }
    
    public void actualizarEstado(){
        if(this.enEjecucion.id != -1){
            this.estado.get(this.procesos.indexOf(this.enEjecucion.id)).add(2);
        }
    }
    
   public void dibujarTiempo(){
       if(this.enEjecucion.id != -1){
            for(int i = 0; i < this.enEjecucion.tiempoLlegada; i++){
                this.estado.get(this.procesos.indexOf(this.enEjecucion.id)).add(0);
            }
        }
   }
   
   public void dibujarTiempoDeEspera(){
       if(this.enEjecucion.id != -1){
            for(int i = 0; i < this.enEjecucion.tiempoEspera; i++){
                this.estado.get(this.procesos.indexOf(this.enEjecucion.id)).add(1);
            }
        }
   }
    
    public void actualizarTiempoEspera(){
        this.auxiliar = this.listos.cabeza;
        while(this.auxiliar.siguiente.id != -1){
            this.auxiliar = this.auxiliar.siguiente;
            this.auxiliar.setTiempoEspera(this.auxiliar.getTiempoEspera() + 1);
        }
    }
    
    public void dibujarTiempoBloqueado(){
         if(this.enEjecucion.id != -1){
            for(int i = 0; i < this.enEjecucion.tiempoBloqueado; i++){
                this.estado.get(this.procesos.indexOf(this.enEjecucion.id)).add(3);
            }
        }
    }
    

    public void agregarNodo() {
        ordenarNodosProgramados();
        Nodo nuevo = new Nodo();
        nuevo.setTiempoLlegada(nuevo.getTiempoLlegada() + this.getTiempo());

        if (nuevo.tiempoLlegada == 0) {
            this.getListos().agregarNodo(nuevo);
            guardarProcesos();
        } else {
            nuevo.setId(this.pp);
            this.procesosProgramados.add(nuevo);
            this.pp++;
        }
        ordenarNodosProgramados();
        notificarObservadores();
    }

    public void eliminarNodo(int indice) {
        this.getListos().eliminarNodo(indice);
        notificarObservadores();
    }

    @Override
    public void notificarObservadores() {
        for (int i = 0; i < this.observadores.size() - 1; i++) {
            Observador observador = (Observador) this.observadores.get(i);
            observador.actualizarDatos(this);
        }
    }

    public void notificarGantt() {
        Observador gantt = (Observador) this.observadores.get(2);
        gantt.actualizarDatos(this);
    }

    @Override
    public void registrar(Observador obs) {
        this.observadores.add(obs);
    }

    @Override
    public void run() {
        atender();
    }

    public void ordenarNodosProgramados() {
        Nodo auxA;
        Nodo auxB;
        for (int i = 1; i < this.procesosProgramados.size(); i++) {
            for (int j = 0; j < this.procesosProgramados.size() - 1; j++) {
                auxA = (Nodo) this.procesosProgramados.get(j);
                auxB = (Nodo) this.procesosProgramados.get(j + 1);
                if (auxA.tiempoLlegada > auxB.tiempoLlegada) {
                    this.procesosProgramados.set(j, this.procesosProgramados.get(j + 1));
                    this.procesosProgramados.set(j + 1, auxA);
                }
            }
        }
    }

    public void mostrarNodosProgramados() {
        for (int i = 0; i < this.procesosProgramados.size(); i++) {
            Nodo a = (Nodo) this.procesosProgramados.get(i);
        }
    }

    public void incrementarTiempo() {
        this.setTiempo(this.getTiempo() + 1);

    }

    public void encolarProgramados() {
        for (int i = 0; i < this.procesosProgramados.size(); i++) {
            Nodo d = (Nodo) this.procesosProgramados.get(i);
            if (d.getTiempoLlegada() == this.tiempo) {
                this.listos.agregarNodo(d);
                notificarObservadores();
            }
        }
    }
}