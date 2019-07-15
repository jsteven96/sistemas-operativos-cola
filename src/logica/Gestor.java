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

    //private ColaPrioridad listosFIFO;
    //Cada cola debe tener una prioridad, la de mayor prioridad es la de RoundRobin, la de segunda es la de prioridades
    //y la de tercer prioridad tiene el algoritmo de FIFO
    
    //Definición de las diferentes colas:
    private Cola listosFIFO;
    private ColaPrioridad listosPrioridad;
    private Cola listosRoundRobin;
    
    //Definición de otras colas
    private Cola terminados;
    private Cola bloqueados;
    
    //La sección crítica o el recurso compartido es el nodo en ejecución
    public Nodo enEjecucion;
    
    public Nodo auxiliar;
    public ArrayList observadores;
    public int retardo;
    private int tiempo;
    
    public ArrayList procesosProgramadosR;
    public ArrayList procesosProgramadosP;
    public ArrayList procesosProgramadosF;
    
    public int pR;
    public int pP;
    public int pF;
    
    public int nR;
    public int nP;
    public int nF;
    
    public int rafagaEjecutada;
    public ArrayList<ArrayList> estado;
    public boolean atendiendo;
    public ArrayList<Integer> procesos;

    public Gestor(Cola cola, ColaPrioridad prioridad, Cola round) {
        this.listosFIFO = cola;
        this.listosPrioridad = prioridad;
        this.listosRoundRobin = round;
        //--------------------------------        
        this.terminados = new Cola();
        this.retardo = 1000;
        this.tiempo = 0;
        this.bloqueados = new Cola();
        this.observadores = new ArrayList();
        this.procesosProgramadosR = new ArrayList();
        this.procesosProgramadosP = new ArrayList();
        this.procesosProgramadosF = new ArrayList();
        
        this.pR = 50;
        this.pP = 50;
        this.pF = 50;
        
        this.nR = 100;
        this.nP = 200;
        this.nF = 300;
        this.rafagaEjecutada = 0;
        this.estado = new ArrayList();
        this.atendiendo = false;
        this.procesos = new ArrayList();
    }

    public Cola getListosFIFO() {
        return listosFIFO;
    }

    public void setListosFIFO(Cola listosFIFO) {
        this.listosFIFO = listosFIFO;
    }

    public Nodo getEnEjecucion() {
        return enEjecucion;
    }

    public void setEnEjecucion(Nodo enEjecucion) {
        this.enEjecucion = enEjecucion;
    }

    public synchronized Cola getTerminados() {
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

    public synchronized ArrayList<ArrayList> getEstado() {
        return estado;
    }

    public synchronized ColaPrioridad getListosPrioridad() {
        return listosPrioridad;
    }

    public void setListosPrioridad(ColaPrioridad listosPrioridad) {
        this.listosPrioridad = listosPrioridad;
    }

    public synchronized Cola getListosRoundRobin() {
        return listosRoundRobin;
    }

    public void setListosRoundRobin(Cola listosRoundRobin) {
        this.listosRoundRobin = listosRoundRobin;
    }
    
    

    public void bloquearProceso() {
        Nodo copiaBloqueados;
        if (this.enEjecucion.getRafaga() != 0 || this.enEjecucion.id != -1) {
            copiaBloqueados = this.enEjecucion.clone();

            copiaBloqueados.bloqueado = true;
            copiaBloqueados.setIniBloq(this.tiempo);
            copiaBloqueados.setVecesBloqueado(copiaBloqueados.getVecesBloqueado() + 1);
            copiaBloqueados.gettFinal().add(this.tiempo);
            copiaBloqueados.getRafagaEjecutada().add(this.rafagaEjecutada);
            copiaBloqueados.gettRetorno().add(copiaBloqueados.gettFinal().get(copiaBloqueados.gettFinal().size() - 1) - copiaBloqueados.getTiempoLlegada());
            copiaBloqueados.gettEspera().add(copiaBloqueados.gettRetorno().get(copiaBloqueados.gettRetorno().size() - 1) - copiaBloqueados.getRafagaParcial());
            this.bloqueados.agregarNodo(copiaBloqueados);
            this.atendiendo = false;

            this.rafagaEjecutada = 0;
            notificarGantt();
        }
    }

    public void desbloquearProceso() {
        Nodo copia;
        if (this.getBloqueados().cabeza.siguiente.id != -1) {
            copia = this.getBloqueados().cabeza.siguiente.clone();

            copia.setTiempoComienzo(this.tiempo);
            copia.setFinBloq(this.tiempo);
            copia.setTiempoBloqueado(copia.finBloq - copia.iniBloq);
            copia.setTiempoEspera(0);

            copia.setTiempoFinal(copia.tiempoComienzo + copia.rafagaParcial);

            this.bloqueados.eliminarNodo(copia.id);
            this.listosFIFO.agregarNodo(copia);

        }
    }

    public void atender() {
        while (true) {
            try {
                Thread.sleep(this.retardo);
            } catch (InterruptedException ex) {
                Logger.getLogger(Gestor.class.getName()).log(Level.SEVERE, null, ex);
            }
            guardarProcesos();
            notificarGantt();
            //Si no hay nadie siendo atendido y el que sigue de la cabeza de listosFIFO no es la misma cabeza
            //pase en ejecución al siguiente de la cabeza
            if (this.atendiendo == false && this.listosFIFO.cabeza.siguiente.id != -1) {
                this.enEjecucion = this.listosFIFO.cabeza.siguiente.clone();
                this.enEjecucion.listo = false;
                this.enEjecucion.enEjecucion = true;
                this.enEjecucion.gettComienzo().add(this.tiempo);
                if (this.enEjecucion.bloqueado == false) {
                    dibujarTiempo();
                }
                dibujarTiempoBloqueado();
                this.enEjecucion.setTiempoBloqueado(0);
                dibujarTiempoDeEspera();
                this.listosFIFO.eliminarNodo(this.enEjecucion.id);
                this.atendiendo = true;
                //notificarObservadores();
                notificarLienzo();
            }

            //Si hay alguien en atención, disminuya la rafaga en uno y aumente la rafaga ejecutada general
            if (this.atendiendo == true) {
                if (this.enEjecucion.id != -1) {
                    //------Nuevo código para SRTF
                    this.auxiliar = this.listosFIFO.cabeza;

                    while (this.auxiliar.siguiente.id != -1) {
                        this.auxiliar = this.auxiliar.siguiente;
                        if (this.auxiliar.rafaga < this.enEjecucion.rafaga) {
                            Nodo copia = this.enEjecucion.clone();
                            copia.bloqueado = true;
                            copia.tiempoEspera = 0;
                            copia.gettFinal().add(this.tiempo);
                            copia.getRafagaEjecutada().add(this.rafagaEjecutada);
                            copia.gettRetorno().add(copia.gettFinal().get(copia.gettFinal().size() - 1) - copia.getTiempoLlegada());
                            copia.gettEspera().add(copia.gettRetorno().get(copia.gettRetorno().size() - 1) - copia.getRafagaParcial());
                            this.listosFIFO.agregarNodo(copia);
                            this.enEjecucion = this.auxiliar.clone();
                            this.enEjecucion.listo = false;
                            this.enEjecucion.enEjecucion = true;
                            this.enEjecucion.gettComienzo().add(this.tiempo);
                            if (this.enEjecucion.bloqueado == false) {
                                dibujarTiempo();
                            }
                            dibujarTiempoBloqueado();
                            this.enEjecucion.setTiempoBloqueado(0);
                            dibujarTiempoDeEspera();
                            this.listosFIFO.eliminarNodo(this.auxiliar.id);

                            break;

                            //Codigo nuevo
                        } else {
                            if (this.auxiliar.rafaga == this.enEjecucion.rafaga && this.auxiliar.tiempoLlegada < this.enEjecucion.tiempoLlegada) {
                                Nodo copia = this.enEjecucion.clone();
                                copia.bloqueado = true;
                                copia.tiempoEspera = 0;
                                copia.gettFinal().add(this.tiempo);
                                copia.getRafagaEjecutada().add(this.rafagaEjecutada);
                                copia.gettRetorno().add(copia.gettFinal().get(copia.gettFinal().size() - 1) - copia.getTiempoLlegada());
                                copia.gettEspera().add(copia.gettRetorno().get(copia.gettRetorno().size() - 1) - copia.getRafagaParcial());
                                this.listosFIFO.agregarNodo(copia);
                                this.enEjecucion = this.auxiliar.clone();
                                this.enEjecucion.listo = false;
                                this.enEjecucion.enEjecucion = true;
                                this.enEjecucion.gettComienzo().add(this.tiempo);
                                if (this.enEjecucion.bloqueado == false) {
                                    dibujarTiempo();
                                }
                                dibujarTiempoBloqueado();
                                this.enEjecucion.setTiempoBloqueado(0);
                                dibujarTiempoDeEspera();
                                this.listosFIFO.eliminarNodo(this.auxiliar.id);

                                break;
                            }
                        }
                        //---------------------
                    }
                    //------------------------------
                    
                    
                    this.enEjecucion.setRafaga(this.enEjecucion.getRafaga() - 1);
                    this.enEjecucion.setRafagaParcial(this.enEjecucion.getRafagaParcial() + 1);
                    this.rafagaEjecutada++;
                    actualizarTiempoEspera();
                    actualizarEstado();
                    //notificarObservadores();
                    notificarLienzo();
                    
                    if (this.enEjecucion.rafaga == 0) {
                        Nodo copia = this.enEjecucion.clone();
                        this.enEjecucion.setId(-1);
                        copia.gettFinal().add(this.tiempo + 1);
                        copia.getRafagaEjecutada().add(this.enEjecucion.getRafagaParcial());
                        copia.gettRetorno().add(copia.gettFinal().get(copia.gettFinal().size() - 1) - copia.getTiempoLlegada());
                        copia.gettEspera().add(copia.gettRetorno().get(copia.gettRetorno().size() - 1) - copia.getRafagaParcial());
                        this.terminados.agregarNodo(copia);
                        //notificarObservadores();
                        notificarLienzo();
                        notificarTabla();
                        this.atendiendo = false;
                        this.rafagaEjecutada = 0;

                    }

                }
            }
            this.setTiempo(this.getTiempo() + 1);
            encolarProgramadosR();
            //notificarObservadores();
            notificarLienzo();
        }
    }

    public void guardarProcesos() {
        this.auxiliar = this.getListosFIFO().cabeza;
        while (this.auxiliar.siguiente.id != -1) {
            this.auxiliar = this.auxiliar.siguiente;
            if (!this.procesos.contains(this.auxiliar.id)) {
                this.procesos.add(this.auxiliar.id);
                ArrayList proceso = new ArrayList();
                proceso.add(this.auxiliar.id);
                this.getEstado().add(proceso);
                
            }
        }

    }

    public void actualizarEstado() {
        if (this.enEjecucion.id != -1) {
            //this.estado.get(this.procesos.indexOf(this.enEjecucion.id)).add(2);
            this.getEstado().get(this.procesos.indexOf(this.enEjecucion.id)).add(2);
                    
        }
    }

    public void dibujarTiempo() {
        if (this.enEjecucion.id != -1) {
            for (int i = 0; i < this.enEjecucion.tiempoLlegada; i++) {
                //this.estado.get(this.procesos.indexOf(this.enEjecucion.id)).add(0);
                this.getEstado().get(this.procesos.indexOf(this.enEjecucion.id)).add(0);
            }
        }
    }

    public void dibujarTiempoDeEspera() {
        if (this.enEjecucion.id != -1) {
            for (int i = 0; i < this.enEjecucion.tiempoEspera; i++) {
                //this.estado.get(this.procesos.indexOf(this.enEjecucion.id)).add(1);
                this.getEstado().get(this.procesos.indexOf(this.enEjecucion.id)).add(1);
            }
        }
    }

    public void actualizarTiempoEspera() {
        this.auxiliar = this.listosFIFO.cabeza;
        while (this.auxiliar.siguiente.id != -1) {
            this.auxiliar = this.auxiliar.siguiente;
            this.auxiliar.setTiempoEspera(this.auxiliar.getTiempoEspera() + 1);
        }
    }

    public void dibujarTiempoBloqueado() {
        if (this.enEjecucion.id != -1) {
            for (int i = 0; i < this.enEjecucion.tiempoBloqueado; i++) {
                //this.estado.get(this.procesos.indexOf(this.enEjecucion.id)).add(3);
                this.getEstado().get(this.procesos.indexOf(this.enEjecucion.id)).add(3);
            }
        }
    }

    public void agregarNodo() {
        ordenarNodosProgramadosR();
        Nodo nuevo = new Nodo();
        nuevo.setTiempoLlegada(nuevo.getTiempoLlegada() + this.getTiempo());
        if (nuevo.tiempoLlegada == 0) {
            nuevo.setId(this.nR);
            this.getListosRoundRobin().agregarNodo(nuevo);
            this.nR++;
            guardarProcesos();
        } else {
            nuevo.setId(100 + this.pR);
            this.procesosProgramadosR.add(nuevo);
            this.pR++;
        }
        ordenarNodosProgramadosR();
        notificarObservadores();
    }
    
    public void agregarNodoP(){
        ordenarNodosProgramadosP();
        Nodo nuevo = new Nodo();
        nuevo.setTiempoLlegada(nuevo.getTiempoLlegada() + this.getTiempo());

        if (nuevo.tiempoLlegada == 0) {
            nuevo.setId(this.nP);
            this.getListosPrioridad().agregarNodo(nuevo);
            this.nP++;
            guardarProcesos();
        } else {
            nuevo.setId(200 + this.pP);
            this.procesosProgramadosP.add(nuevo);
            this.pP++;
        }
        ordenarNodosProgramadosP();
        notificarObservadores();
    }
    
    public void agregarNodoF(){
        ordenarNodosProgramadosF();
        Nodo nuevo = new Nodo();
        nuevo.setTiempoLlegada(nuevo.getTiempoLlegada() + this.getTiempo());

        if (nuevo.tiempoLlegada == 0) {
            nuevo.setId(this.nF);
            this.getListosFIFO().agregarNodo(nuevo);
            this.nF++;
            guardarProcesos();
        } else {
            nuevo.setId(300 + this.pF);
            this.procesosProgramadosF.add(nuevo);
            this.pF++;
        }
        ordenarNodosProgramadosF();
        notificarObservadores();
    }

    public void eliminarNodo(int indice) {
        this.getListosFIFO().eliminarNodo(indice);
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
    
    public void notificarTabla(){
        Observador tabla = (Observador) this.observadores.get(1);
        tabla.actualizarDatos(this);
    }
    
    public void notificarLienzo(){
        Observador li = (Observador) this.observadores.get(0);
        li.actualizarDatos(this);
    }
    @Override
    public void registrar(Observador obs) {
        this.observadores.add(obs);
    }

    @Override
    public void run() {
        atender();
    }

    public void ordenarNodosProgramadosR() {
        Nodo auxA;
        Nodo auxB;
        for (int i = 1; i < this.procesosProgramadosR.size(); i++) {
            for (int j = 0; j < this.procesosProgramadosR.size() - 1; j++) {
                auxA = (Nodo) this.procesosProgramadosR.get(j);
                auxB = (Nodo) this.procesosProgramadosR.get(j + 1);
                if (auxA.tiempoLlegada > auxB.tiempoLlegada) {
                    this.procesosProgramadosR.set(j, this.procesosProgramadosR.get(j + 1));
                    this.procesosProgramadosR.set(j + 1, auxA);
                }
            }
        }
    }
    public void ordenarNodosProgramadosP() {
        Nodo auxA;
        Nodo auxB;
        for (int i = 1; i < this.procesosProgramadosP.size(); i++) {
            for (int j = 0; j < this.procesosProgramadosP.size() - 1; j++) {
                auxA = (Nodo) this.procesosProgramadosP.get(j);
                auxB = (Nodo) this.procesosProgramadosP.get(j + 1);
                if (auxA.tiempoLlegada > auxB.tiempoLlegada) {
                    this.procesosProgramadosP.set(j, this.procesosProgramadosP.get(j + 1));
                    this.procesosProgramadosP.set(j + 1, auxA);
                }
            }
        }
    }
    
    public void ordenarNodosProgramadosF() {
        Nodo auxA;
        Nodo auxB;
        for (int i = 1; i < this.procesosProgramadosF.size(); i++) {
            for (int j = 0; j < this.procesosProgramadosF.size() - 1; j++) {
                auxA = (Nodo) this.procesosProgramadosF.get(j);
                auxB = (Nodo) this.procesosProgramadosF.get(j + 1);
                if (auxA.tiempoLlegada > auxB.tiempoLlegada) {
                    this.procesosProgramadosF.set(j, this.procesosProgramadosF.get(j + 1));
                    this.procesosProgramadosF.set(j + 1, auxA);
                }
            }
        }
    }

    public void mostrarNodosProgramadosR() {
        for (int i = 0; i < this.procesosProgramadosR.size(); i++) {
            Nodo a = (Nodo) this.procesosProgramadosR.get(i);
        }
    }
    
    public void mostrarNodosProgramadosP(){
        for (int i = 0; i < this.procesosProgramadosP.size(); i++) {
            Nodo a = (Nodo) this.procesosProgramadosP.get(i);
        }
    }
    
    public void mostrarNodosProgramadosF(){
        for (int i = 0; i < this.procesosProgramadosF.size(); i++) {
            Nodo a = (Nodo) this.procesosProgramadosF.get(i);
        }
    }

    public void incrementarTiempo() {
        this.setTiempo(this.getTiempo() + 1);

    }

    public void encolarProgramadosR() {
        for (int i = 0; i < this.procesosProgramadosR.size(); i++) {
            Nodo d = (Nodo) this.procesosProgramadosR.get(i);
            if (d.getTiempoLlegada() == this.tiempo) {
                this.listosRoundRobin.agregarNodo(d);
                notificarObservadores();
            }
        }
    }
    
    public void encolarProgramadosP(){
        for (int i = 0; i < this.procesosProgramadosP.size(); i++) {
            Nodo d = (Nodo) this.procesosProgramadosP.get(i);
            if (d.getTiempoLlegada() == this.tiempo) {
                this.listosPrioridad.agregarNodo(d);
                notificarObservadores();
            }
        }
    }
    
    public void encolarProgramadosF(){
        for (int i = 0; i < this.procesosProgramadosF.size(); i++) {
            Nodo d = (Nodo) this.procesosProgramadosF.get(i);
            if (d.getTiempoLlegada() == this.tiempo) {
                this.listosFIFO.agregarNodo(d);
                notificarObservadores();
            }
        }
    }
}
