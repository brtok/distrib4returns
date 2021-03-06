/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import Modelo.Cartao;
import Modelo.Colecionador;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * Leitura e escrita de cartões em arquivo
 * @author Bruno Tokarski e Rafael Vidal
 */
public class IOCartao {

    /**
     * Salva o cartão em arquivo
     * @param cartao
     * @return
     * @throws Exception 
     */
    public boolean SalvaCartao(Cartao cartao) throws Exception {
        ArrayList<Cartao> cartoes;
        boolean duplicado = false;
        Colecionador logado = Colecionador.getInstancia();

        //Adiciona informação do proprietário do cartão
        cartao.setIdProprietario(logado.getIdColecionador());

        File arquivo = new File("C:/Distrib4/Cartao-" + logado.getIdColecionador() + ".dst");
        if (arquivo.exists()) {
            cartoes = RecuperarCartoes();
            for (Cartao c : cartoes) {
                if (c.getIdCartao() == cartao.getIdCartao()) {
                    duplicado = true;
                }
            }
        } else {
            cartoes = new ArrayList<>();
        }

        if (duplicado) {
            JOptionPane.showMessageDialog(null, "Já existe um cartão cadastrado com ID " + cartao.getIdCartao() + ".");
            return false;
        } else {
            cartoes.add(cartao);

            //Deleta o arquivo
            arquivo.delete();
            //Cria um arquivo novo para salvar o array atualizado
            FileOutputStream arquivoGrav = new FileOutputStream(arquivo);
            ObjectOutputStream objGravar = new ObjectOutputStream(arquivoGrav);
            objGravar.writeObject(cartoes);
            logado.setCartoes(cartoes);
            
            return true;
        }
    }

    /**
     * Edita um cartão salvo
     * @param cartao
     * @throws Exception 
     */
    public void EditaCartao(Cartao cartao) throws Exception {
        ArrayList<Cartao> cartoes;
        Colecionador logado = Colecionador.getInstancia();
        File arquivo = new File("C:/Distrib4/Cartao-" + logado.getIdColecionador() + ".dst");
        Cartao cartaoAntes;
        if (arquivo.exists()) {
            cartoes = RecuperarCartoes();
            for (Cartao c : cartoes) {
                if (c.getIdCartao() == cartao.getIdCartao()) {
                    cartaoAntes = c;
                    cartoes.remove(c);
                    break;
                }
            }
        } else {
            cartoes = new ArrayList<>();
        }
        cartoes.add(cartao);

        //Deleta o arquivo
        arquivo.delete();

        //Cria um arquivo novo para salvar o array atualizado
        FileOutputStream arquivoGrav = new FileOutputStream(arquivo);
        ObjectOutputStream objGravar = new ObjectOutputStream(arquivoGrav);
        objGravar.writeObject(cartoes);
        logado.setCartoes(cartoes);
        JOptionPane.showMessageDialog(null, "Cartão ID " + cartao.getIdCartao() + " modificado com sucesso.");
    }

    /**
     * Recupera todos os cartões salvos
     * @return lista de cartões do usuário
     * @throws Exception 
     */
    public ArrayList<Cartao> RecuperarCartoes() throws Exception {
        ArrayList<Cartao> cartoes = new ArrayList<>();
        Colecionador logado = Colecionador.getInstancia();
        File arquivo = new File("C:/Distrib4/Cartao-" + logado.getIdColecionador() + ".dst");
        if (arquivo.exists()) {
            FileInputStream arquivoLeitura = new FileInputStream("C:/Distrib4/Cartao-" + logado.getIdColecionador() + ".dst");
            if (arquivoLeitura.available() != 0) {
                ObjectInputStream objLeitura = new ObjectInputStream(arquivoLeitura);
                cartoes = (ArrayList<Cartao>) objLeitura.readObject();
            }
        }
        logado.setCartoes(cartoes);
        return cartoes;
    }

    /**
     * Retorna um cartão a partir de seu id
     * @param id
     * @return cartão com o id recebido como parâmetro
     * @throws Exception 
     */
    public Cartao RecuperarCartaoPorID(int id) throws Exception {
        ArrayList<Cartao> cartoes = new ArrayList<>();
        Colecionador logado = Colecionador.getInstancia();
        File arquivo = new File("C:/Distrib4/Cartao-" + logado.getIdColecionador() + ".dst");
        if (arquivo.exists()) {
            cartoes = RecuperarCartoes();
            Cartao c = null;
            for (Cartao cartao : cartoes) {
                if (cartao.getIdCartao() == id) {
                    c = cartao;
                }
            }
            return c;
        } else {
            JOptionPane.showMessageDialog(null, "Ainda não existe nenhum cartão cadastrado");
            return null;
        }
    }

    /**
     * Exclui um cartão da coleção do usuário
     * @param idCartao
     * @throws Exception 
     */
    public void ExcluirCartao(int idCartao) throws Exception {
        
        ArrayList<Cartao> cartoes = null;
        Colecionador logado = Colecionador.getInstancia();

        File arquivo = new File("C:/Distrib4/Cartao-" + logado.getIdColecionador() + ".dst");
        if (arquivo.exists()) {
            cartoes = RecuperarCartoes();
            for (Cartao c : cartoes) {
                if (c.getIdCartao() == idCartao) {
                    cartoes.remove(c);
                    break;
                }
            }
        }
        
        //Deleta o arquivo
        arquivo.delete();
        
        //Cria um arquivo novo para salvar o array atualizado
        FileOutputStream arquivoGrav = new FileOutputStream(arquivo);
        ObjectOutputStream objGravar = new ObjectOutputStream(arquivoGrav);
        objGravar.writeObject(cartoes);
        logado.setCartoes(cartoes);

    }

}
