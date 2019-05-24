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
public class Prueba {
    
    public static void main(String [] args){
        Nodo nodo1 = new Nodo();
        Nodo nodo2 = new Nodo();
        Cola prueba = new Cola();
        prueba.agregarNodo(nodo1);
        prueba.agregarNodo(nodo2);
        prueba.mostrarCola();
        Nodo nodo3 = new Nodo();
        prueba.agregarNodo(nodo3);
        prueba.eliminarNodo(1);
        prueba.mostrarCola();
    }
}
