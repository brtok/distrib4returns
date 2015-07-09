/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Comunicacao;

import Interface.ComunicacaoClient;
import Interface.ComunicacaoServer;
import Modelo.Cartao;
import Modelo.ColecionadorEncontrado;
import Modelo.Troca;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 *
 * @author Rafael
 */
public class RMIClient extends UnicastRemoteObject implements ComunicacaoClient {

    private Registry reg;
    private ComunicacaoServer obj;

    public RMIClient(ColecionadorEncontrado conexao) throws Exception {
        super();
        reg = LocateRegistry.getRegistry("localhost", conexao.getPorta());
        String nomeServer = "servidor" + conexao.getIdColecionador();
        nomeServer = nomeServer.trim();
        obj = (ComunicacaoServer) reg.lookup(nomeServer);
    }

    @Override
    public Troca RespondeProposta(Troca troca, ComunicacaoClient cliente) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public ArrayList<Cartao> SolicitaListaCartoes(int idColecionador) throws Exception {
        ArrayList<Cartao> cartoes = obj.ListarCartoes();
        return cartoes;
    }

    @Override
    public void EnviaProposta(Troca troca) throws Exception {
        obj.ReceberProposta(troca);
    }
}
