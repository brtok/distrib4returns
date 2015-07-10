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
    
    //1: Aguardando, 2: Efetuada, 3: Recusada
    private int situacaoTroca;

    
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
    
}
