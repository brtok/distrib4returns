/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Modelo.Troca;
import java.rmi.Remote;

/**
 * Interface de envio de ações para outros participantes
 * @author Rafael
 */
public interface ComunicacaoClient extends Remote {
   
    void EnviaProposta(Troca troca) throws Exception;
    
    Troca RespondeProposta(Troca troca, ComunicacaoClient cliente) throws Exception;
    
}
