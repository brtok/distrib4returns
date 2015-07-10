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
    private Colecionador solicitante;
    private Colecionador solicitado;
    private Cartao cartaoManda;
    private Cartao cartaoRecebe;
    private int idCoordenador;
    
    
    //1: Aguardando, 2: Efetuada, 3: Recusada
    private int situacaoTroca;

    public Colecionador getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(Colecionador solicitante) {
        this.solicitante = solicitante;
    }

    public Colecionador getSolicitado() {
        return solicitado;
    }

    public void setSolicitado(Colecionador solicitado) {
        this.solicitado = solicitado;
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
    
}
