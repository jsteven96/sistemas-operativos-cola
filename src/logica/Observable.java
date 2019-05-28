/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import interfaz.Observador;

/**
 *
 * @author root
 */
public interface Observable {
    public void notificarObservador();
    public void registrar(Observador obs);
}
