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
public class Gestor {

    public Cola cola;
    public Nodo auxiliar;

    public Gestor(Cola cola) {
        this.cola = cola;
        this.auxiliar = new Nodo();
    }

    public void atender() {
        if (this.cola.numElementos() != 0) {
            this.auxiliar = this.cola.cabeza;
            while (auxiliar.siguiente != null) {
                if (auxiliar.siguiente.id != -1) {
                    auxiliar = auxiliar.siguiente;
                    if (auxiliar.servicios <= 3) {
                        System.out.println("Nodo " + auxiliar.id + " con " + auxiliar.servicios + " servicios atendido");
                        this.cola.eliminarNodo(auxiliar.id);
                        this.cola.mostrarCola();
                    } else {
                        System.out.println("Nodo " + auxiliar.id + " con " + auxiliar.servicios + " servicios se volvera a la cola");
                        auxiliar.servicios -= 3;
                        this.cola.eliminarNodo(auxiliar.id);
                        Nodo copia = new Nodo();
                        copia.id = auxiliar.id;
                        copia.servicios = auxiliar.servicios;
                        this.cola.agregarNodo(copia);
                        this.cola.mostrarCola();

                    }
                } else {
                    if (cola.numElementos() > 0) {
                        auxiliar = auxiliar.siguiente.siguiente;
                        if (auxiliar.servicios <= 3) {
                            System.out.println("Nodo " + auxiliar.id + " con " + auxiliar.servicios + " servicios atendido");
                            this.cola.eliminarNodo(auxiliar.id);
                            this.cola.mostrarCola();
                        } else {
                            System.out.println("Nodo " + auxiliar.id + " con " + auxiliar.servicios + " servicios se volvera a la cola");
                            auxiliar.servicios -= 3;
                            this.cola.eliminarNodo(auxiliar.id);
                            Nodo copia = new Nodo();
                            copia.id = auxiliar.id;
                            copia.servicios = auxiliar.servicios;
                            this.cola.agregarNodo(copia);
                            this.cola.mostrarCola();

                        }
                    } else {
                        break;
                    }
                }

            }
        }
    }

}
