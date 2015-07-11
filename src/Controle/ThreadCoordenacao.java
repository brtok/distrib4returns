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
            boolean posseOk;
                    
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
                            
                            //Verifica se os participantes da troca ainda tem posse das cartas
                            posseOk = VerificaPosseCartas(t);
                            
                            //Se um dos particpantes cancelou a transação ou
                            //Se ao menos um dos participantes não tiver mais a posse da carta, a troca é abortada
                            if (!(t.isSolicitanteAceita()) || (!posseOk)) {
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

                            //Verifica se os participantes da troca ainda tem posse das cartas
                            posseOk = VerificaPosseCartas(t);

                            //Se os dois tiverem o cartão, efetua a troca
                            if (posseOk) {
                                EfetuarTroca(t);
                                t.setSituacaoTroca(5);
                            } else {
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
            ex.printStackTrace();
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

    public void EfetuarTroca(Troca troca) throws Exception {
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

    public boolean VerificaPosseCartas(Troca troca) throws Exception{
        
        RMIClient rmic;
        
        //Verificar se o solicitante ainda possui o cartão
        rmic = new RMIClient(troca.getIdSolicitante());
        boolean solicitanteTemCartao = rmic.VerificaPropriedadeCartao(troca.getCartaoManda().getIdCartao());
        System.out.println("Solicitante tem cartao " + solicitanteTemCartao);

        //Verifica se o solicitado ainda possui o cartão
        rmic = new RMIClient(troca.getIdSolicitado());
        boolean solicitadoTemCartao = rmic.VerificaPropriedadeCartao(troca.getCartaoRecebe().getIdCartao());
        System.out.println("Solicitado tem cartão " + solicitadoTemCartao);

        //Se os dois tiverem o cartão, efetua a troca
        if (solicitanteTemCartao && solicitadoTemCartao) {
            return true;
        } else {
            return false;
        }
    }

}
