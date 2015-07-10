/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Modelo.Cartao;
import Modelo.Troca;
import java.rmi.Remote;
import java.util.ArrayList;

/**
 * Interface de recebimento de ações de outros colecionadores
 * @author Bruno Tokarski e Rafael Vidal
 */
public interface ComunicacaoServer extends Remote {
        
    void ReceberPropostaComoCoordenador(Troca troca) throws Exception;
    
    void ReceberPropostaComoParticipante(Troca troca) throws Exception;
    
    ArrayList<Cartao> ListarCartoes() throws Exception;
    
    void AtualizarTroca(Troca troca) throws Exception;
    
    void ReceberRespostaTroca(String idTroca, int idParticipante, boolean aceito) throws Exception;
    
}
