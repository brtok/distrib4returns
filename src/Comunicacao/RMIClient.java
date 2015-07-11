/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Comunicacao;

import Interface.ComunicacaoClient;
import Interface.ComunicacaoServer;
import Modelo.Cartao;
import Modelo.Colecionador;
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

    public RMIClient(int idConexao) throws Exception {
        super();
        Colecionador logado = Colecionador.getInstancia();
        ColecionadorEncontrado conexao = logado.getUsuarioParticipantePorId(idConexao);
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
    public void EnviaPropostaParaParticipante(Troca troca) throws Exception {
        obj.ReceberPropostaComoParticipante(troca);
    }
    
    @Override
    public void EnviarAtualizacaoTroca(String idTroca, int situacaoTroca, boolean solicitanteAceita, boolean solicitadoAceita) throws Exception {
        obj.AtualizarTroca(idTroca, situacaoTroca, solicitanteAceita, solicitadoAceita);
    }
    
    @Override
    public void ResponderTroca(String idTroca, int idParticipante, boolean aceito) throws Exception {
        obj.ReceberRespostaTroca(idTroca, idParticipante, aceito);
    }

    @Override
    public boolean VerificaPropriedadeCartao(int idCartao) throws Exception {
        
        boolean possuiCartao = obj.ResponderPropriedadeCartao(idCartao);
        
        return possuiCartao;
    }

    @Override
    public void RecebeCartao(Cartao cartao) throws Exception {
        obj.AdicionaCartao(cartao);
    }

    @Override
    public void EnviaCartao(int idCartao) throws Exception {
        obj.RetiraCartao(idCartao);
    }
    
}
