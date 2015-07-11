/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Comunicacao;

import Modelo.Colecionador;
import Modelo.ColecionadorEncontrado;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bruno
 */
public class MulticastTeste extends Thread {

    @Override
    public void run() {
        try {
            sleep(10000);
            while (true) {
                Colecionador instancia = Colecionador.getInstancia();
                for (ColecionadorEncontrado ce : instancia.getListaParticipantes()) {
                    if (System.currentTimeMillis() > ce.getConsiderarQueda()) {
                        ce.setAtivo(false);
                    }
                }
                sleep(10000);
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(MulticastTeste.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
