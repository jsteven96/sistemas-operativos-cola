/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import interfaz.InterfazProcesos;

/**
 *
 * @author root
 */
public class Prueba {
    
    public static void main(String [] args) throws Exception{
        InterfazProcesos UI = new InterfazProcesos();
        Nodo nodo1 = new Nodo();
        
        Nodo nodo2 = new Nodo();
        Cola prueba = new Cola();
        prueba.agregarNodo(nodo1);
        prueba.agregarNodo(nodo2);
        Nodo nodo3 = new Nodo();
        prueba.agregarNodo(nodo3);
        Nodo nodo4 = new Nodo();
        prueba.agregarNodo(nodo4);
        prueba.mostrarCola();
        
        
        Gestor miGestor = new Gestor(prueba);
        miGestor.atender();
        
        
        
    }
}
