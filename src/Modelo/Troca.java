/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.Serializable;

/**
 *
 * @author Rafael
 */
public class Troca implements Serializable{
    
    private String id;
    private int idSolicitante;
    private int idSolicitado;
    private int idCoordenador;
    private Cartao cartaoManda;
    private Cartao cartaoRecebe;
    
    //1: Enviada para o Coordenador
    //2: Aguardando Resposta
    //3: Aguardando Finalização
    //4: Fazer a Troca
    //5: Efetuada
    //6: Recusada
    private int situacaoTroca;

    //Decisões
    private boolean solicitanteAceita;
    private boolean solicitadoAceita;
    private boolean solicitadoRespondeu;

    public Troca(String id, int idSolicitante, int idSolicitado, int idCoordenador, Cartao cartaoManda, Cartao cartaoRecebe) {
        this.id = id;
        this.idSolicitante = idSolicitante;
        this.idSolicitado = idSolicitado;
        this.idCoordenador = idCoordenador;
        this.cartaoManda = cartaoManda;
        this.cartaoRecebe = cartaoRecebe;
        situacaoTroca = 1;
        solicitanteAceita = true;
        solicitadoAceita = false;
        solicitadoRespondeu = false;
    }
    
    public int getIdSolicitante() {
        return idSolicitante;
    }

    public void setIdSolicitante(int idSolicitante) {
        this.idSolicitante = idSolicitante;
    }

    public int getIdSolicitado() {
        return idSolicitado;
    }

    public void setIdSolicitado(int idSolicitado) {
        this.idSolicitado = idSolicitado;
    }

    public Cartao getCartaoManda() {
        return cartaoManda;
    }

    public void setCartaoManda(Cartao cartaoManda) {
        this.cartaoManda = cartaoManda;
    }

    public Cartao getCartaoRecebe() {
        return cartaoRecebe;
    }

    public void setCartaoRecebe(Cartao cartaoRecebe) {
        this.cartaoRecebe = cartaoRecebe;
    }

    public int getSituacaoTroca() {
        return situacaoTroca;
    }

    public void setSituacaoTroca(int situacaoTroca) {
        this.situacaoTroca = situacaoTroca;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIdCoordenador() {
        return idCoordenador;
    }

    public void setIdCoordenador(int idCoordenador) {
        this.idCoordenador = idCoordenador;
    }

    public boolean isSolicitanteAceita() {
        return solicitanteAceita;
    }

    public void setSolicitanteAceita(boolean solicitanteAceita) {
        this.solicitanteAceita = solicitanteAceita;
    }

    public boolean isSolicitadoAceita() {
        return solicitadoAceita;
    }

    public void setSolicitadoAceita(boolean solicitadoAceita) {
        this.solicitadoAceita = solicitadoAceita;
    }

    public boolean isSolicitadoRespondeu() {
        return solicitadoRespondeu;
    }

    public void setSolicitadoRespondeu(boolean solicitadoRespondeu) {
        this.solicitadoRespondeu = solicitadoRespondeu;
    }
    
}
