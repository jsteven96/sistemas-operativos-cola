/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import interfaz.Observador;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author root
 */
public class Gestor implements Observable, Runnable {

    private ColaPrioridad listos;
    private Cola terminados;
    private Cola bloqueados;
    public Nodo auxiliar;
    public ArrayList observadores;
    public int retardo;
    private int tiempo;
    public ArrayList procesosProgramados;
    public int pp;

    public Gestor(ColaPrioridad cola) {
        this.listos = cola;
        this.terminados = new Cola();
        this.auxiliar = new Nodo();
        this.retardo = 2000;
        this.tiempo = 0;
        this.bloqueados = new Cola();
        this.observadores = new ArrayList();
        this.procesosProgramados = new ArrayList();
        this.pp = 100;

    }

    public synchronized ColaPrioridad getListos() {
        return listos;
    }

    public void setListos(ColaPrioridad listos) {
        this.listos = listos;
    }

    public Nodo getAuxiliar() {
        return auxiliar;
    }

    public void setAuxiliar(Nodo auxiliar) {
        this.auxiliar = auxiliar;
    }

    public synchronized Cola getTerminados() {
        return terminados;
    }

    public void setTerminados(Cola terminados) {
        this.terminados = terminados;
    }

    public synchronized int getTiempo() {
        return tiempo;
    }

    public synchronized void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    public Cola getBloqueados() {
        return bloqueados;
    }

    public void setBloqueados(Cola bloqueados) {
        this.bloqueados = bloqueados;
    }

    public void bloquearProceso() {
        Nodo copia;
        if (this.auxiliar.getRafaga() != 0) {
            this.auxiliar.setIniBloqueado(this.tiempo);
            copia = this.auxiliar.clone();
            this.bloqueados.agregarNodo(copia);
            this.listos.eliminarNodo(copia.id);
        }
    }

    public void desbloquearProceso() {
        Nodo copia;
        copia = this.getBloqueados().cabeza.siguiente.clone();
        copia.setFinBloqueado(this.tiempo);
        
        this.bloqueados.eliminarNodo(copia.id);
        this.listos.agregarNodo(copia);
        System.out.println("TI "+copia.getIniBloqueado()+" TF "+copia.getFinBloqueado());
    }

    public void agregarATerminados(int rafagaParcial) {
        Nodo copia;
        copia = this.auxiliar.clone();
        copia.rafaga = rafagaParcial;
        copia.tiempoComienzo = this.getTiempo() - rafagaParcial;
        copia.setTiempoLlegada(this.auxiliar.getTiempoLlegada());
        copia.tiempoFinal = this.tiempo;
        copia.setTiempoRetorno(copia.getTiempoFinal() - copia.getTiempoLlegada());
        copia.setTiempoEspera(copia.getTiempoRetorno() - copia.getRafaga());
        this.terminados.agregarNodo(copia);
        notificarObservadores();
        this.listos.eliminarNodo(copia.id);
        notificarObservadores();
    }

    public void atender() {
        int rafagaP = 0;
        this.auxiliar = this.listos.cabeza;
        while (true) {
            notificarGantt();
            this.auxiliar = this.listos.cabeza.siguiente;
            rafagaP++;
            this.auxiliar.setRafaga(this.auxiliar.getRafaga() - 1);
            this.auxiliar.setRafagaParcial(this.auxiliar.getRafagaParcial()+1);
            notificarGantt();
            this.tiempo++;
            encolarProgramados();
            notificarObservadores();
            if (this.auxiliar.rafaga <= 0) {
                agregarATerminados(rafagaP);
                rafagaP = 0;
            }
            try {
                Thread.sleep(this.retardo);
            } catch (InterruptedException ex) {
                Logger.getLogger(Gestor.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public void agregarNodo() {
        ordenarNodosProgramados();
        Nodo nuevo = new Nodo();
        nuevo.setTiempoLlegada(nuevo.getTiempoLlegada() + this.getTiempo());

        if (nuevo.tiempoLlegada == 0) {
            this.getListos().agregarNodo(nuevo);
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
        //miObservador.actualizarDatos(this);
    }

    public void notificarGantt() {
        Observador gantt = (Observador) this.observadores.get(2);
        gantt.actualizarDatos(this);
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
            System.out.println("ID " + a.id + " TL " + a.tiempoLlegada);
        }
    }

    public void incrementarTiempo() {
        //notificarObservadores();
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
