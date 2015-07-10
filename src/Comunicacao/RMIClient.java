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

    public ArrayList<Cartao> SolicitaListaCartoes(int idColecionador) throws Exception {
        ArrayList<Cartao> cartoes = obj.ListarCartoes();
        return cartoes;
    }

    @Override
    public void EnviaPropostaParaCoordenador(Troca troca) throws Exception {
        obj.ReceberPropostaComoCoordenador(troca);
    }
    
    @Override
    public void EnviarAtualizacaoTroca(Troca troca) throws Exception {
        obj.AtualizarTroca(troca);
    }
    
    @Override
    public void ResponderTroca(String idTroca, int idParticipante, boolean aceito) throws Exception {
        obj.ReceberRespostaTroca(idTroca, idParticipante, aceito);
    }
    
}
