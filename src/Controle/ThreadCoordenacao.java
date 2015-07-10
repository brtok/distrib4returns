/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import Modelo.Colecionador;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bruno
 */
public class ThreadCoordenacao extends Thread {

    private Colecionador logado;

    /**
     *
     */
    @Override
    public void run() {
        try {
            logado = Colecionador.getInstancia();
            while (true) {
                
                sleep(3000);
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(ThreadCoordenacao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
