/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

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
    public int iniBloqueado;
    public int finBloqueado;
    public int rafagaParcial;
    
    
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
        this.iniBloqueado = -1;
        this.finBloqueado = -1;
        this.bloqueado = false;
        this.rafagaParcial = 0;
    }    

    public int getIniBloqueado() {
        return iniBloqueado;
    }

    public void setIniBloqueado(int iniBloqueado) {
        this.iniBloqueado = iniBloqueado;
    }

    public int getFinBloqueado() {
        return finBloqueado;
    }

    public void setFinBloqueado(int finBloqueado) {
        this.finBloqueado = finBloqueado;
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
        clon.setIniBloqueado(this.iniBloqueado);
        clon.setFinBloqueado(this.finBloqueado);
        return clon;
    }
    
    
}
