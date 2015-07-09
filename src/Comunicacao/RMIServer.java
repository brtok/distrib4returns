/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Comunicacao;

import GUI.JanelaAvisoTroca;
import IOarquivo.IOCartao;
import Interface.ComunicacaoServer;
import Modelo.Cartao;
import Modelo.Colecionador;
import Modelo.Troca;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Métodos acessíveis aos outros colecionadores via RMI
 * @author Rafael
 */
public class RMIServer extends UnicastRemoteObject implements ComunicacaoServer {
    
    public RMIServer() throws RemoteException {
        super();
    }

    @Override
    public void ReceberProposta(Troca troca) throws InterruptedException {
        
        JanelaAvisoTroca jat = new JanelaAvisoTroca(troca);
        jat.setVisible(true);
        
    }
    
    @Override
    public ArrayList<Cartao> ListarCartoes() {
        try {
            IOCartao ioc = new IOCartao();
            return ioc.RecuperarCartoes();
        } catch (Exception ex) {
            Logger.getLogger(RMIClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     * Método para Registro e declaração Inicial do Serviço de RMI
     * 
     * @throws Exception 
     */
    public void IniciaRMI() throws Exception {
        Colecionador logado = Colecionador.getInstancia();
        String nomeServer = "servidor" + logado.getIdColecionador();
        Registry reg = LocateRegistry.createRegistry(logado.getPorta());
        reg.bind(nomeServer, new RMIServer());
        System.out.println("RMIServer criado e registrado");
    }    

}
