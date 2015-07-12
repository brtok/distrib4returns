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
    private int otimizadorDeTempo;

    /**
     *
     */
    @Override
    public void run() {
        logado = Colecionador.getInstancia();
        RMIClient rmic;
        boolean posseOk;
        otimizadorDeTempo = 1000;

        while (true) {
            for (int i = 0; i < logado.getTrocasQueSouCoordenador().size(); i++) {

                Troca t = logado.getTrocasQueSouCoordenador().get(i);
                //PRIMEIRO TESTE: se alguém caiu, cancelar a transação
                if ((!(logado.getUsuarioParticipantePorId(t.getIdSolicitado()).isAtivo()))
                        || (!(logado.getUsuarioParticipantePorId(t.getIdSolicitante()).isAtivo()))) {
                    if (t.getSituacaoTroca() == 1 || t.getSituacaoTroca() == 2
                            || t.getSituacaoTroca() == 3 || t.getSituacaoTroca() == 4) {
                        t.setSituacaoTroca(7);
                        if (logado.getUsuarioParticipantePorId(t.getIdSolicitado()).isAtivo()) {
                            try {
                                AtualizarSolicitado(t);
                            } catch (Exception ex) {
                                Logger.getLogger(ThreadCoordenacao.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        if (logado.getUsuarioParticipantePorId(t.getIdSolicitante()).isAtivo()) {
                            try {
                                AtualizarSolicitante(t);
                            } catch (Exception ex) {
                                Logger.getLogger(ThreadCoordenacao.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }

                //TESTES por situação
                switch (t.getSituacaoTroca()) {
                    //Status 1: Enviado para coordenador
                    //Repassa para o Solicitado
                    case 1:
                        t.setSituacaoTroca(2);
                         {
                            try {
                                rmic = new RMIClient(t.getIdSolicitado());
                                rmic.EnviaPropostaParaParticipante(t);
                                AtualizarSolicitante(t);
                            } catch (Exception ex) {
                                Logger.getLogger(ThreadCoordenacao.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        break;

                    //Status 2: Aguardando Resposta
                    //Testa apenas se o solicitante não cancelou a transação
                    case 2: {
                        try {
                            //Verifica se os participantes da troca ainda tem posse das cartas
                            posseOk = VerificaPosseCartasFase1(t);
                            //Se um dos particpantes cancelou a transação ou
                            //Se ao menos um dos participantes não tiver mais a posse da carta, a troca é abortada
                            if (!(t.isSolicitanteAceita()) || (!posseOk)) {
                                t.setSituacaoTroca(7);
                                AtualizarOsDois(t);
                            }
                        } catch (Exception e) {
                        }
                    }
                    break;

                    //Status 3: Já Respondeu
                    //Se o solicitado aceitou, inicia o segundo passo da verificação
                    case 3:
                        try {
                            //Verifica se os participantes da troca ainda tem posse das cartas
                            posseOk = VerificaPosseCartasFase1(t);

                            //Se os dois tiverem o cartão, efetua a troca
                            if (posseOk && todosAceitam(t)) {
                                EfetuarTroca(t);
                                t.setSituacaoTroca(4);
                                otimizadorDeTempo = 10;
                            } else {
                                t.setSituacaoTroca(6);
                            }
                            AtualizarOsDois(t);
                        } catch (Exception ex) {
                            Logger.getLogger(ThreadCoordenacao.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;

                    //Status 4: Fez a troca, verificar se foi feita mesmo
                    case 4:
                        try {
                            //Verifica se a troca foi realmente feita
                            posseOk = VerificaPosseCartasFase2(t);

                            //Se os dois tiverem o cartão agora trocados, finaliza a operação
                            if (posseOk) {
                                t.setSituacaoTroca(5);
                            } else {
                                t.setSituacaoTroca(7);
                            }
                            otimizadorDeTempo = 1000;
                            AtualizarOsDois(t);
                        } catch (Exception ex) {
                            Logger.getLogger(ThreadCoordenacao.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                }
                try {
                    sleep(otimizadorDeTempo);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ThreadCoordenacao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            try {
                sleep(2 * otimizadorDeTempo);
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadCoordenacao.class.getName()).log(Level.SEVERE, null, ex);
            }

            //TESTE COM AS TRANSAÇÕES PARTICIPANTES
            //se o coordenador caiu, cancelar a própria transação
            for (int i = 0; i < logado.getTrocasQueSouParticipante().size(); i++) {
                if (!(logado.getUsuarioParticipantePorId(logado.getTrocasQueSouParticipante().get(i).getIdCoordenador()).isAtivo())) {
                    if (logado.getTrocasQueSouParticipante().get(i).getSituacaoTroca() == 1
                            || logado.getTrocasQueSouParticipante().get(i).getSituacaoTroca() == 2
                            || logado.getTrocasQueSouParticipante().get(i).getSituacaoTroca() == 3
                            || logado.getTrocasQueSouParticipante().get(i).getSituacaoTroca() == 4) {
                        logado.getTrocasQueSouParticipante().get(i).setSituacaoTroca(7);
                        JanelaPrincipal.atualizarTabelaTransacoes();
                    }
                }
                try {
                    sleep(otimizadorDeTempo);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ThreadCoordenacao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            try {
                sleep(2 * otimizadorDeTempo);
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadCoordenacao.class.getName()).log(Level.SEVERE, null, ex);
            }
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

    public void AtualizarOsDois(Troca troca) throws Exception {
        AtualizarSolicitado(troca);
        AtualizarSolicitante(troca);
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

    public boolean VerificaPosseCartasFase1(Troca troca) throws Exception {
        RMIClient rmic;
        //Verificar se o solicitante ainda possui o cartão
        rmic = new RMIClient(troca.getIdSolicitante());
        boolean solicitanteTemCartao = rmic.VerificaPropriedadeCartao(troca.getCartaoManda().getIdCartao());
        //System.out.println("Solicitante tem cartao " + solicitanteTemCartao);

        //Verifica se o solicitado ainda possui o cartão
        rmic = new RMIClient(troca.getIdSolicitado());
        boolean solicitadoTemCartao = rmic.VerificaPropriedadeCartao(troca.getCartaoRecebe().getIdCartao());
        //System.out.println("Solicitado tem cartão " + solicitadoTemCartao);
        //Se os dois tiverem o cartão, efetua a troca
        return (solicitanteTemCartao && solicitadoTemCartao);
    }

    public boolean VerificaPosseCartasFase2(Troca troca) throws Exception {
        RMIClient rmic;
        //Verificar se o solicitante ainda possui o cartão
        rmic = new RMIClient(troca.getIdSolicitante());
        boolean solicitanteTemCartao = rmic.VerificaPropriedadeCartao(troca.getCartaoRecebe().getIdCartao());
        //System.out.println("Solicitante tem cartao " + solicitanteTemCartao);

        //Verifica se o solicitado ainda possui o cartão
        rmic = new RMIClient(troca.getIdSolicitado());
        boolean solicitadoTemCartao = rmic.VerificaPropriedadeCartao(troca.getCartaoManda().getIdCartao());
        //System.out.println("Solicitado tem cartão " + solicitadoTemCartao);
        //Se os dois tiverem o cartão, efetua a troca
        return (solicitanteTemCartao && solicitadoTemCartao);
    }

    public boolean todosAceitam(Troca troca) {
        return (troca.isSolicitanteAceita() && troca.isSolicitadoAceita());
    }

}
