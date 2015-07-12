/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Rafael
 */
public class Colecionador implements Serializable {

    private int idColecionador;
    private String nomeColecionador;
    private boolean coordenador;
    private boolean efetuandoTransacao;
    private int porta;
    private ArrayList<Cartao> cartoes = new ArrayList<>();
    private ArrayList<ColecionadorEncontrado> listaParticipantes = new ArrayList<>();
    private ArrayList<Troca> trocasQueSouParticipante = new ArrayList<>();
    private ArrayList<Troca> trocasQueSouCoordenador = new ArrayList<>();

    private static Colecionador instancia;

    public static Colecionador getInstancia() {
        return instancia;
    }

    public static void setInstancia(Colecionador instancia) {
        Colecionador.instancia = instancia;
    }

    public ArrayList<ColecionadorEncontrado> getListaParticipantes() {
        return listaParticipantes;
    }

    public void setListaParticipantes(ArrayList<ColecionadorEncontrado> listaParticipantes) {
        this.listaParticipantes = listaParticipantes;
    }

    public int getIdColecionador() {
        return idColecionador;
    }

    public void setIdColecionador(int idColecionador) {
        this.idColecionador = idColecionador;
    }

    public String getNomeColecionador() {
        return nomeColecionador;
    }

    public void setNomeColecionador(String nomeColecionador) {
        this.nomeColecionador = nomeColecionador;
    }

    public boolean isCoordenador() {
        return coordenador;
    }

    public void setCoordenador(boolean coordenador) {
        this.coordenador = coordenador;
    }

    public ArrayList<Cartao> getCartoes() {
        return cartoes;
    }

    public void setCartoes(ArrayList<Cartao> cartoes) {
        this.cartoes = cartoes;
    }

    public int getPorta() {
        return porta;
    }

    public void setPorta(int porta) {
        this.porta = porta;
    }

    public ArrayList<Troca> getTrocasQueSouParticipante() {
        return trocasQueSouParticipante;
    }

    public void setTrocasQueSouParticipante(ArrayList<Troca> trocasQueSouParticipante) {
        this.trocasQueSouParticipante = trocasQueSouParticipante;
    }

    public ArrayList<Troca> getTrocasQueSouCoordenador() {
        return trocasQueSouCoordenador;
    }

    public void setTrocasQueSouCoordenador(ArrayList<Troca> trocasQueSouCoordenador) {
        this.trocasQueSouCoordenador = trocasQueSouCoordenador;
    }

    public boolean isEfetuandoTransacao() {
        return efetuandoTransacao;
    }

    public void setEfetuandoTransacao(boolean efetuandoTransacao) {
        this.efetuandoTransacao = efetuandoTransacao;
    }

    public ColecionadorEncontrado getUsuarioParticipantePorId(int idColecionadorConectado) {
        ColecionadorEncontrado resultado = null;
        for (ColecionadorEncontrado ce : listaParticipantes) {
            if (ce.getIdColecionador() == idColecionadorConectado) {
                resultado = ce;
                break;
            }
        }
        return resultado;
    }

    public Troca getTrocaQueSouParticipantePorId(String idTroca) {
        Troca resultado = null;
        for (Troca tr : trocasQueSouParticipante) {
            if (tr.getId().equalsIgnoreCase(idTroca)) {
                resultado = tr;
                break;
            }
        }
        return resultado;
    }
    
    public int getNumeroParticipantesAtivos() {
        int contador = 0;
        for (ColecionadorEncontrado ce : listaParticipantes) {
            if (ce.isAtivo()) {
                contador++;
            }
        }
        return contador;
    }

}
