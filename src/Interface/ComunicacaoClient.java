/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Modelo.Cartao;
import Modelo.Troca;
import java.rmi.Remote;

/**
 * Interface de envio de ações para outros participantes
 * @author Bruno Tokarski e Rafael Vidal
 */
public interface ComunicacaoClient extends Remote {
   
    void EnviaPropostaParaCoordenador(Troca troca) throws Exception;
    
    void EnviaPropostaParaParticipante(Troca troca) throws Exception;
    
    void EnviarAtualizacaoTroca(String idTroca, int situacaoTroca, boolean solicitanteAceita, boolean solicitadoAceita) throws Exception;
    
    void ResponderTroca(String idTroca, int idParticipante, boolean aceito) throws Exception;
    
    boolean VerificaPropriedadeCartao(int idCartao) throws Exception;
    
    void RecebeCartao(Cartao cartao) throws Exception;
    
    void EnviaCartao(int idCartao) throws Exception;
}
