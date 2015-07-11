/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Comunicacao;

import GUI.JanelaPrincipal;
import Controle.IOCartao;
import Interface.ComunicacaoServer;
import Modelo.Cartao;
import Modelo.Colecionador;
import Modelo.Troca;
import static java.lang.Thread.sleep;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Métodos acessíveis aos outros colecionadores via RMI
 *
 * @author Rafael
 */
public class RMIServer extends UnicastRemoteObject implements ComunicacaoServer {

    public RMIServer() throws RemoteException {
        super();
    }

    /**
     * Método para Registro e declaração Inicial do Serviço de RMI
     *
     * @throws Exception
     */
    public void IniciaRMI() throws Exception {
        Colecionador logado = Colecionador.getInstancia();
        String nomeServer = "servidor" + logado.getIdColecionador();
        Registry reg = LocateRegistry.createRegistry(logado.getPorta());
        reg.bind(nomeServer, new RMIServer());
        System.out.println("RMIServer criado e registrado");
    }

    @Override
    public void ReceberPropostaComoCoordenador(Troca troca) throws InterruptedException {
        Colecionador logado = Colecionador.getInstancia();
        logado.getTrocasQueSouCoordenador().add(troca);
    }

    @Override
    public void ReceberPropostaComoParticipante(Troca troca) throws InterruptedException {
        Colecionador instancia = Colecionador.getInstancia();
        instancia.getTrocasQueSouParticipante().add(troca);
        JanelaPrincipal.atualizarTabelaTransacoes();
    }

    @Override
    public ArrayList<Cartao> ListarCartoes() {
        try {
            IOCartao ioc = new IOCartao();
            return ioc.RecuperarCartoes();
        } catch (Exception ex) {
            Logger.getLogger(RMIClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void AtualizarTroca(String idTroca, int situacaoTroca, boolean solicitanteAceita, boolean solicitadoAceita) throws Exception {
        Colecionador instancia = Colecionador.getInstancia();
        int indice = -1;
        for (int i = 0; i < instancia.getTrocasQueSouParticipante().size(); i++) {
            if (instancia.getTrocasQueSouParticipante().get(i).getId().equalsIgnoreCase(idTroca)) {
                indice = i;
            }
        }
        instancia.getTrocasQueSouParticipante().get(indice).setSituacaoTroca(situacaoTroca);
        instancia.getTrocasQueSouParticipante().get(indice).setSolicitanteAceita(solicitanteAceita);
        instancia.getTrocasQueSouParticipante().get(indice).setSolicitadoAceita(solicitadoAceita);
        JanelaPrincipal.atualizarTabelaTransacoes();
    }

    @Override
    public void ReceberRespostaTroca(String idTroca, int idParticipante, boolean aceito) throws Exception {
        Colecionador instancia = Colecionador.getInstancia();
        int indice = -1;
        for (int i = 0; i < instancia.getTrocasQueSouCoordenador().size(); i++) {
            if (instancia.getTrocasQueSouCoordenador().get(i).getId().equalsIgnoreCase(idTroca)) {
                indice = i;
            }
        }
        if (instancia.getTrocasQueSouCoordenador().get(indice).getIdSolicitado() == idParticipante) {

            instancia.getTrocasQueSouCoordenador().get(indice).setSolicitadoAceita(aceito);
            if (aceito) {
                instancia.getTrocasQueSouCoordenador().get(indice).setSituacaoTroca(3);
            } else {
                instancia.getTrocasQueSouCoordenador().get(indice).setSituacaoTroca(6);
            }

        }
//        if (instancia.getTrocasQueSouCoordenador().get(indice).getIdSolicitante()== idParticipante) {
//            instancia.getTrocasQueSouCoordenador().get(indice).setSolicitanteAceita(aceito);
//        }
    }

    @Override
    public boolean ResponderPropriedadeCartao(int idCartao) throws Exception {

        IOCartao iocar = new IOCartao();

        Cartao cartao = iocar.RecuperarCartaoPorID(idCartao);

        if (cartao == null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void AdicionaCartao(Cartao cartao) throws Exception {

        Colecionador logado = Colecionador.getInstancia();

        while (true) {
            //Só efetua a transação caso o servidor esteja destrvado
            if (!logado.isEfetuandoTransacao()) {
                //Ativa a trava do servidor
                logado.setEfetuandoTransacao(true);

                IOCartao iocar = new IOCartao();
                iocar.SalvaCartao(cartao);

                //Desativa a trava do servidor
                logado.setEfetuandoTransacao(false);
                break;
            }
            sleep(500);
        }

    }

    @Override
    public void RetiraCartao(int idCartao) throws Exception {

        Colecionador logado = Colecionador.getInstancia();

        while (true) {
            //Só efetua a transação caso o servidor esteja destrvado
            if (!logado.isEfetuandoTransacao()) {
                //Ativa a trava do servidor
                logado.setEfetuandoTransacao(true);

                IOCartao iocar = new IOCartao();
                iocar.ExcluirCartao(idCartao);

                //Desativa a trava do servidor
                logado.setEfetuandoTransacao(false);
                break;
            }
            sleep(500);
        }
    }
}
