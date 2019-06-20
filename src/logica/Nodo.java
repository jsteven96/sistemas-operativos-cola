/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.util.ArrayList;

/**
 *
 * @author root
 */
public class Nodo implements Cloneable {
    public int rafaga;
    public int id;
    public Nodo siguiente;
    public int tiempoLlegada;
    public int tiempoComienzo;
    public int tiempoRetorno;
    public int tiempoFinal;
    public int tiempoEspera;
    public int prioridad;
    public boolean bloqueado;
    public boolean enEjecucion;
    public boolean listo;
    public int rafagaParcial;
    public int iniBloq;
    public int finBloq;
    public int tiempoBloqueado;
    
    //--------Nuevos atributos por bloqueo------
    public ArrayList<Integer> rafagaEjecutada;
    public ArrayList<Integer> tComienzo;
    public ArrayList<Integer> tFinal;
    public ArrayList<Integer> tRetorno;
    public ArrayList<Integer> tEspera;
    
    
    public Nodo(){
        this.rafaga = (int) (1 + Math.random() * 7);
        //this.siguiente = null;
        //El id -2 significa que no tiene id aún
        this.id = -2;
        this.tiempoLlegada = (int) (Math.random() * 3);
        this.prioridad = (int) (1 + Math.random() * 4);
        this.tiempoComienzo = 0;
        this.tiempoFinal = 0;
        this.tiempoRetorno = 0;
        this.tiempoEspera = 0;
        this.iniBloq = 0;
        this.finBloq = 0;
        this.tiempoBloqueado = 0;
        this.bloqueado = false;
        this.enEjecucion = false;
        this.listo = true;
        this.rafagaParcial = 0;
        
        //-------Inicialización de nuevos atributos-------
        this.rafagaEjecutada = new ArrayList();
        this.tComienzo = new ArrayList();
        this.tFinal = new ArrayList();
        this.tRetorno = new ArrayList();
        this.tEspera = new ArrayList();
    }    
    
    public void setId(int id) {
        this.id = id;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public synchronized void setRafaga(int rafaga) {
        this.rafaga = rafaga;
    }

    public synchronized int getTiempoLlegada() {
        return tiempoLlegada;
    }

    public synchronized void setTiempoLlegada(int tiempoLlegada) {
        this.tiempoLlegada = tiempoLlegada;
    }

    public synchronized int getRafaga() {
        return rafaga;
    }

    public synchronized int getId() {
        return id;
    }

    public Nodo getSiguiente() {
        return siguiente;
    }

    public synchronized int getTiempoComienzo() {
        return tiempoComienzo;
    }

    public synchronized int getTiempoRetorno() {
        return tiempoRetorno;
    }

    public synchronized int getTiempoFinal() {
        return tiempoFinal;
    }

    public synchronized int getTiempoEspera() {
        return tiempoEspera;
    }

    public synchronized void setTiempoComienzo(int tiempoComienzo) {
        this.tiempoComienzo = tiempoComienzo;
    }

    public void setTiempoRetorno(int tiempoRetorno) {
        this.tiempoRetorno = tiempoRetorno;
    }

    public void setTiempoFinal(int tiempoFinal) {
        this.tiempoFinal = tiempoFinal;
    }

    public void setTiempoEspera(int tiempoEspera) {
        this.tiempoEspera = tiempoEspera;
    }

    public boolean isBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(boolean bloqueado) {
        this.bloqueado = bloqueado;
    }

    public int getRafagaParcial() {
        return rafagaParcial;
    }

    public void setRafagaParcial(int rafagaParcial) {
        this.rafagaParcial = rafagaParcial;
    }

    public int getIniBloq() {
        return iniBloq;
    }

    public void setIniBloq(int iniBloq) {
        this.iniBloq = iniBloq;
    }

    public int getFinBloq() {
        return finBloq;
    }

    public void setFinBloq(int finBloq) {
        this.finBloq = finBloq;
    }

    public int getTiempoBloqueado() {
        return tiempoBloqueado;
    }

    public void setTiempoBloqueado(int tiempoBloqueado) {
        this.tiempoBloqueado = tiempoBloqueado;
    }

    public ArrayList<Integer> gettComienzo() {
        return tComienzo;
    }

    public void settComienzo(ArrayList<Integer> tComienzo) {
        this.tComienzo = tComienzo;
    }

    public ArrayList<Integer> gettFinal() {
        return tFinal;
    }

    public void settFinal(ArrayList<Integer> tFinal) {
        this.tFinal = tFinal;
    }

    public ArrayList<Integer> gettRetorno() {
        return tRetorno;
    }

    public void settRetorno(ArrayList<Integer> tRetorno) {
        this.tRetorno = tRetorno;
    }

    public ArrayList<Integer> gettEspera() {
        return tEspera;
    }

    public void settEspera(ArrayList<Integer> tEspera) {
        this.tEspera = tEspera;
    }

    public ArrayList<Integer> getRafagaEjecutada() {
        return rafagaEjecutada;
    }

    public void setRafagaEjecutada(ArrayList<Integer> rafagaEjecutada) {
        this.rafagaEjecutada = rafagaEjecutada;
    }
    
    @Override
    public Nodo clone(){
        Nodo clon = new Nodo();
        clon.setId(this.id);
        clon.setRafaga(this.rafaga);
        clon.setTiempoComienzo(this.tiempoComienzo);
        clon.setTiempoEspera(this.tiempoEspera);
        clon.setTiempoFinal(this.tiempoFinal);
        clon.setTiempoLlegada(this.tiempoLlegada);
        clon.setTiempoRetorno(this.tiempoRetorno);
        clon.setBloqueado(this.bloqueado);
        
        //----------- Bloqueo
        clon.setIniBloq(this.iniBloq);
        clon.setFinBloq(this.finBloq);
        clon.setTiempoBloqueado(tiempoBloqueado);
        
        clon.setRafagaEjecutada(rafagaEjecutada);
        clon.settComienzo(tComienzo);
        clon.settFinal(tFinal);
        clon.settRetorno(tRetorno);
        clon.settEspera(tEspera);
        
        return clon;
    }
    
    
}
