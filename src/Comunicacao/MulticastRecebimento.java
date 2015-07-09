/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Comunicacao;

import GUI.JanelaPrincipal;
import Modelo.Colecionador;
import Modelo.ColecionadorEncontrado;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bruno
 */
public class MulticastRecebimento extends Thread {

    @Override
    public void run() {
        try {
            InetAddress address = InetAddress.getByName("228.5.6.7");
            MulticastSocket clientSocket = new MulticastSocket(8885);
            clientSocket.joinGroup(address);
            while (true) {
                byte[] buf = new byte[256];
                DatagramPacket msgPacket = new DatagramPacket(buf, buf.length);
                clientSocket.receive(msgPacket);
                String mensagem = new String(buf);
                String mensagemQuebrada[] = mensagem.trim().split("#");
                int idColecionador = Integer.parseInt(mensagemQuebrada[0]);
                int portaColecionador = Integer.parseInt(mensagemQuebrada[1]);
                String nome = mensagemQuebrada[2];
                int numeroCartoes = Integer.parseInt(mensagemQuebrada[3]);
                Colecionador instancia = Colecionador.getInstancia();
                ArrayList<ColecionadorEncontrado> participantes = instancia.getListaParticipantes();
                int indice = -1;
                for (int i = 0; i < participantes.size(); i++) {
                    if (participantes.get(i).getIdColecionador() == idColecionador) {
                        indice = i;
                    }
                }
                if (indice == -1) {
                    long tempo = System.currentTimeMillis() + 20000;
                    ColecionadorEncontrado ce = new ColecionadorEncontrado(idColecionador, portaColecionador, tempo, nome, numeroCartoes);
                    participantes.add(ce);
                } else {
                    long tempo = System.currentTimeMillis() + 20000;
                    participantes.get(indice).setConsiderarQueda(tempo);
                    participantes.get(indice).setNumeroCartoes(numeroCartoes);
                    participantes.get(indice).setPorta(portaColecionador);
                }
                JanelaPrincipal.atualizarTabelaColecionadores();
            }
        } catch (Exception ex) {
            Logger.getLogger(MulticastRecebimento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
