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
        
    void ReceberProposta(Troca troca) throws Exception;
    
    ArrayList<Cartao> ListarCartoes() throws Exception;
    
}
