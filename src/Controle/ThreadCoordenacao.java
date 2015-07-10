/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import Modelo.Colecionador;
import Modelo.Troca;
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
                for (Troca t : logado.getTrocasQueSouCoordenador()) {
                    switch (t.getSituacaoTroca()) {
                        case 1:
                            
                            break;
                        case 2:
                            
                            break;
                        case 3:
                            
                            break;
                        case 4:
                            
                            break;
                    }
                    sleep(1000);
                }
                sleep(5000);
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(ThreadCoordenacao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
