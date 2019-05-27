/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

import logica.Observable;

/**
 *
 * @author root
 */
public interface Observador {
    public void actualizarDatos(Observable sujeto);
    
}
