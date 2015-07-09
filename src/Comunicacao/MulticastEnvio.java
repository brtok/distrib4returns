/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Comunicacao;

import Modelo.Colecionador;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bruno
 */
public class MulticastEnvio extends Thread {

    /**
     * Thread de envio de hello para clientes a fim de manter o status online
     * frente aos mesmos
     */
    @Override
    public void run() {
        try {
            DatagramSocket serverSocket = new DatagramSocket();
            InetAddress address = InetAddress.getByName("228.5.6.7");
            Colecionador instancia = Colecionador.getInstancia();
            while (true) {
                String mensagem = instancia.getIdColecionador() + "#" + instancia.getPorta() + "#" + instancia.getNomeColecionador() + "#" + instancia.getCartoes().size();
                DatagramPacket msgPacket = new DatagramPacket(mensagem.getBytes(), mensagem.getBytes().length, address, 8885);
                serverSocket.send(msgPacket);
                System.out.println("Mandou: " + mensagem);
                sleep(10000);
            }
        } catch (Exception ex) {
            Logger.getLogger(MulticastEnvio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
