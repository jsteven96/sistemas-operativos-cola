/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

import java.awt.event.*;

/**
 *
 * @author root
 */
public class Controlador implements ActionListener {
    InterfazProcesos objInterfaz;
    
    public Controlador(){
        
    }
    
    public Controlador(InterfazProcesos interfazEntrante){
        objInterfaz = interfazEntrante;
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getActionCommand().equals(InterfazProcesos.SALIR)){
            System.exit(0);
        }
        if(e.getActionCommand().equals(InterfazProcesos.AGREGAR_PROCESOS_R)){
            objInterfaz.agregarProceso();
        }
       
        if(e.getActionCommand().equals(InterfazProcesos.ATENDER_PROCESOS)){
            objInterfaz.atenderProceso();
        }
        if(e.getActionCommand().equals(InterfazProcesos.BLOQUEAR_PROCESO)){
            objInterfaz.bloquearProceso();
        }
        if(e.getActionCommand().equals(InterfazProcesos.DESBLOQUEAR_PROCESO)){
            objInterfaz.desbloquearProceso();
        }
        if(e.getActionCommand().equals(InterfazProcesos.AGREGAR_PROCESOS_P)){
            objInterfaz.agregarProcesoP();
        }
        if(e.getActionCommand().equals(InterfazProcesos.AGREGAR_PROCESOS_F)){
            objInterfaz.agregarProcesoF();
        }
    }
    
}
