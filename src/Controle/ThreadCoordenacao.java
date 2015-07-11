/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import Comunicacao.RMIClient;
import GUI.JanelaPrincipal;
import Modelo.Colecionador;
import Modelo.Troca;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bruno
 */
public class ThreadCoordenacao extends Thread {

    private Colecionador logado;

    /**
     *
     */
    @Override
    public void run() {
        try {
            logado = Colecionador.getInstancia();
            RMIClient rmic;
            while (true) {
                for (Troca t : logado.getTrocasQueSouCoordenador()) {
                    //PRIMEIRO TESTE: se alguém caiu, cancelar a transação
                    if ((!(logado.getUsuarioParticipantePorId(t.getIdSolicitado()).isAtivo()))
                            || (!(logado.getUsuarioParticipantePorId(t.getIdSolicitante()).isAtivo()))) {
                        t.setSituacaoTroca(6);
                        if (logado.getUsuarioParticipantePorId(t.getIdSolicitado()).isAtivo()) {
                            AtualizarSolicitado(t);
                        }
                        if (logado.getUsuarioParticipantePorId(t.getIdSolicitante()).isAtivo()) {
                            AtualizarSolicitante(t);
                        }
                    }
                    
                    //TESTES por situação
                    switch (t.getSituacaoTroca()) {
                        //Status 1: Enviado para coordenador
                        //Repassa para o Solicitado
                        case 1:
                            t.setSituacaoTroca(2);
                            rmic = new RMIClient(t.getIdSolicitado());
                            rmic.EnviaPropostaParaParticipante(t);
                            AtualizarSolicitante(t);
                            break;
                        //Status 2: Aguardando Resposta
                        //Testa apenas se o solicitante não cancelou a transação
                        case 2:
                            if (!(t.isSolicitanteAceita())) {
                                t.setSituacaoTroca(6);
                                AtualizarSolicitado(t);
                                AtualizarSolicitante(t);
                            }
                            break;
                        //Se o solicitado aceitou, inicia o segundo passo da verificação
                        case 3:
                            System.out.println("Troca aceita");
                            t.setSituacaoTroca(3);
                            AtualizarSolicitado(t);
                            AtualizarSolicitante(t);
                            
                            //Verificar se o solicitante ainda possui o cartão
                            rmic = new RMIClient(t.getIdSolicitante());
                            boolean solicitanteTemCartao = rmic.VerificaPropriedadeCartao(t.getCartaoManda().getIdCartao());
                            System.out.println("Solicitante tem cartao " + solicitanteTemCartao);
                            
                            //Verifica se o solicitado ainda possui o cartão
                            rmic = new RMIClient(t.getIdSolicitado());
                            boolean solicitadoTemCartao = rmic.VerificaPropriedadeCartao(t.getCartaoRecebe().getIdCartao());
                            System.out.println("Solicitado tem cartão " + solicitadoTemCartao);
                            
                            //Se os dois tiverem o cartão, efetua a troca
                            if(solicitanteTemCartao && solicitadoTemCartao)
                            {
                                EfetuarTroca(t);
                                t.setSituacaoTroca(5);
                            }else
                            {
                                t.setSituacaoTroca(6);
                            }
                                                        
                            AtualizarSolicitado(t);
                            AtualizarSolicitante(t);
                            break;
                        case 4:

                            break;
                    }
                    sleep(500);
                }
                
                sleep(2500);
                
                //TESTE COM AS TRANSAÇÕES PARTICIPANTES
                //se o coordenador caiu, cancelar a própria transação
                for (Troca t : logado.getTrocasQueSouParticipante()) {
                    if (!(logado.getUsuarioParticipantePorId(t.getIdCoordenador()).isAtivo())) {
                        t.setSituacaoTroca(6);
                        JanelaPrincipal.atualizarTabelaTransacoes();
                    }
                    sleep(500);
                }
                
                sleep(2500);
            }
        } catch (Exception ex) {
            Logger.getLogger(ThreadCoordenacao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void AtualizarSolicitante(Troca troca) throws Exception {
        RMIClient rmic = new RMIClient(troca.getIdSolicitante());
        rmic.EnviarAtualizacaoTroca(troca.getId(), troca.getSituacaoTroca(), troca.isSolicitadoAceita(), troca.isSolicitadoAceita());
        System.out.println("Atualizou o solcitante " + troca.getIdSolicitante());
    }

    public void AtualizarSolicitado(Troca troca) throws Exception {
        RMIClient rmic = new RMIClient(troca.getIdSolicitado());
        rmic.EnviarAtualizacaoTroca(troca.getId(), troca.getSituacaoTroca(), troca.isSolicitadoAceita(), troca.isSolicitadoAceita());
        System.out.println("Atualizou o solicitado " + troca.getIdSolicitado());
    }
    
    public void EfetuarTroca(Troca troca) throws Exception
    {
        RMIClient rmiSolicitante = new RMIClient(troca.getIdSolicitante());
        RMIClient rmiSolicitado = new RMIClient(troca.getIdSolicitado());
        
        //Entrega o novo cartão ao solicitado
        rmiSolicitado.RecebeCartao(troca.getCartaoManda());
        
        //Retira o cartão trocado do solicitante
        rmiSolicitante.EnviaCartao(troca.getCartaoManda().getIdCartao());
        
        //Entrega o novo cartão ao solicitante
        rmiSolicitante.RecebeCartao(troca.getCartaoRecebe());
        
        //Retira o cartão trocado do do solicitado
        rmiSolicitado.EnviaCartao(troca.getCartaoRecebe().getIdCartao());
        
    }

}
