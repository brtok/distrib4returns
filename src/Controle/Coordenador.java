/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import Modelo.Troca;
import java.util.ArrayList;

/**
 *
 * @author Bruno
 */
public class Coordenador {

    private static Coordenador instancia;
    private ArrayList<Troca> trocasQueSouCoordenador = new ArrayList<>();

    public static Coordenador getInstancia() {
        if (instancia == null) {
            instancia = new Coordenador();
        }
        return instancia;
    }

    public ArrayList<Troca> getTrocasQueSouCoordenador() {
        return trocasQueSouCoordenador;
    }

    public void setTrocasQueSouCoordenador(ArrayList<Troca> trocasQueSouCoordenador) {
        this.trocasQueSouCoordenador = trocasQueSouCoordenador;
    }

}
