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

    private Cola listos;
    private Cola terminados;
    private Cola bloqueados;
    public Nodo auxiliar;
    public ArrayList observadores;
    //public Observador miObservador;
    public int retardo;
    private int tiempo;
    public ArrayList procesosProgramados;
    public int pp;

    public Gestor(Cola cola) {
        this.listos = cola;
        this.terminados = new Cola();
        this.auxiliar = new Nodo();
        this.retardo = 1000;
        this.tiempo = 0;
        this.bloqueados = new Cola();
        this.observadores = new ArrayList();
        this.procesosProgramados = new ArrayList();
        this.pp = 100;

    }

    public synchronized Cola getListos() {
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
        this.auxiliar.bloqueado = true;
    }

    public void atender() {
        while (true) {
            this.auxiliar = this.getListos().cabeza;
            if (this.listos.numElementos() == 0) {
                incrementarTiempo();
                encolarProgramados();
                this.listos.mostrarCola();
                notificarObservadores();
                try {
                    Thread.sleep(this.retardo);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Gestor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            while (this.auxiliar.siguiente.id != -1) {
                this.auxiliar = this.auxiliar.siguiente;
                if (this.auxiliar.bloqueado == true) {
                    Nodo copia = new Nodo();
                    copia.setId(this.getAuxiliar().id);
                    copia.setRafaga(this.getAuxiliar().getRafaga());
                    copia.setTiempoComienzo(this.getTiempo());
                    copia.setTiempoLlegada(this.auxiliar.getTiempoLlegada());
                    copia.setTiempoFinal(this.getTiempo() + this.auxiliar.getRafaga());
                    copia.setTiempoRetorno(copia.getTiempoFinal() - copia.getTiempoLlegada());
                    copia.setTiempoEspera(copia.getTiempoRetorno() - copia.getRafaga());
                    this.listos.eliminarNodo(this.auxiliar.id);
                    this.bloqueados.agregarNodo(copia);
                    notificarObservadores();
                } else {
                    Nodo copia = new Nodo();
                    copia.setId(this.getAuxiliar().id);
                    copia.setRafaga(this.getAuxiliar().getRafaga());
                    copia.setTiempoComienzo(this.getTiempo());
                    copia.setTiempoLlegada(this.auxiliar.getTiempoLlegada());
                    copia.setTiempoFinal(this.getTiempo() + this.auxiliar.getRafaga());
                    copia.setTiempoRetorno(copia.getTiempoFinal() - copia.getTiempoLlegada());
                    copia.setTiempoEspera(copia.getTiempoRetorno() - copia.getRafaga());
                    for (int i = 0; i < copia.getRafaga(); i++) {
                        if (this.auxiliar.bloqueado == true) {
                            Nodo copia2 = new Nodo();
                            copia2.setId(this.getAuxiliar().id);
                            copia2.setRafaga(this.getAuxiliar().getRafaga());
                            copia2.setTiempoComienzo(this.getTiempo());
                            copia2.setTiempoLlegada(this.auxiliar.getTiempoLlegada());
                            copia2.setTiempoFinal(this.getTiempo() + this.auxiliar.getRafaga());
                            copia2.setTiempoRetorno(copia2.getTiempoFinal() - copia2.getTiempoLlegada());
                            copia2.setTiempoEspera(copia2.getTiempoRetorno() - copia2.getRafaga());
                            copia2.setIniBloqueado(this.getTiempo());
                            this.listos.eliminarNodo(this.auxiliar.id);
                            this.bloqueados.agregarNodo(copia2);
                            notificarObservadores();
                            break;
                        }
                        this.auxiliar.setRafaga(this.auxiliar.getRafaga() - 1);
                        incrementarTiempo();
                        encolarProgramados();
                        notificarObservadores();
                        try {
                            Thread.sleep(this.retardo);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Gestor.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    this.getTerminados().agregarNodo(copia);
                    notificarGantt();
                    this.getListos().eliminarNodo(this.auxiliar.id);
                    notificarObservadores();
                }

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

    public Nodo duplicarNodo(Nodo anterior) {
        Nodo copia = new Nodo();
        copia.setId(anterior.getId());
        copia.setRafaga(anterior.getRafaga());
        copia.setTiempoLlegada(anterior.getTiempoLlegada());
        copia.setBloqueado(anterior.isBloqueado());
        copia.setIniBloqueado(anterior.getIniBloqueado());
        copia.setFinBloqueado(anterior.getFinBloqueado());
        copia.setTiempoEspera(anterior.getTiempoEspera());
        return copia;
    }

    public void desbloquearProceso() {
        if (bloqueados.numElementos() > 0) {
            Nodo bloq = duplicarNodo(bloqueados.cabeza.getSiguiente());
            bloq.setFinBloqueado(this.getTiempo());
            bloqueados.eliminarNodo(bloq.getId());
            this.listos.agregarNodo(bloq);
            //Modificar tiempos
            this.notificarObservadores();
        }
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
