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
 * Métodos do cliente RMI
 * @author Bruno Tokarski e Rafael Vidal
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

    /**
     * Solicitar a lista de cartões
     * @param idColecionador
     * @return lista de cartões do usuário solicitado
     * @throws Exception 
     */
    public ArrayList<Cartao> SolicitaListaCartoes(int idColecionador) throws Exception {
        ArrayList<Cartao> cartoes = obj.ListarCartoes();
        return cartoes;
    }

    /**
     * Envia a proposta de troca para o coordenador trabalhar com a mesma
     * @param troca
     * @throws Exception 
     */
    @Override
    public void EnviaPropostaParaCoordenador(Troca troca) throws Exception {
        obj.ReceberPropostaComoCoordenador(troca);
    }
    
    /**
     * Enviar a proposta de troca para o participante decidir pelo sim ou pelo não
     * @param troca
     * @throws Exception 
     */
    @Override
    public void EnviaPropostaParaParticipante(Troca troca) throws Exception {
        obj.ReceberPropostaComoParticipante(troca);
    }
    
    /**
     * Envia atualização da situação da troca para o solicitante e solicitado
     * @param idTroca
     * @param situacaoTroca
     * @param solicitanteAceita
     * @param solicitadoAceita
     * @throws Exception 
     */
    @Override
    public void EnviarAtualizacaoTroca(String idTroca, int situacaoTroca, boolean solicitanteAceita, boolean solicitadoAceita) throws Exception {
        obj.AtualizarTroca(idTroca, situacaoTroca, solicitanteAceita, solicitadoAceita);
    }
    
    /**
     * Responder (positivamente ou negativamente) uma proposta de troca
     * @param idTroca
     * @param idParticipante
     * @param aceito
     * @throws Exception 
     */
    @Override
    public void ResponderTroca(String idTroca, int idParticipante, boolean aceito) throws Exception {
        obj.ReceberRespostaTroca(idTroca, idParticipante, aceito);
    }

    /**
     * Verifica se o solicitado e o solicitante possuem os cartões envolvidos na troca
     * @param idCartao
     * @return
     * @throws Exception 
     */
    @Override
    public boolean VerificaPropriedadeCartao(int idCartao) throws Exception {
        
        boolean possuiCartao = obj.ResponderPropriedadeCartao(idCartao);
        
        return possuiCartao;
    }

    /**
     * Receber um novo cartão (após a troca ser efetuada)
     * @param cartao
     * @throws Exception 
     */
    @Override
    public void RecebeCartao(Cartao cartao) throws Exception {
        obj.AdicionaCartao(cartao);
    }

    /**
     * Enviar um cartão (após a troca ser efetuada)
     * @param idCartao
     * @throws Exception 
     */
    @Override
    public void EnviaCartao(int idCartao) throws Exception {
        obj.RetiraCartao(idCartao);
    }
    
}
