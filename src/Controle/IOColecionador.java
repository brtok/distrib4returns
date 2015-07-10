/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import Modelo.Colecionador;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Bruno
 */
public class IOColecionador {

    ArrayList<Colecionador> colecionadores;

    public boolean SalvaColecionador(Colecionador colecionador) throws Exception {
        boolean duplicado = false;
        File arquivo = new File("C:/Distrib4/Colecionadores.dst");

        if (arquivo.exists()) {
            colecionadores = RecuperarColecionadores();
            for (Colecionador c : colecionadores) {
                if (c.getIdColecionador() == colecionador.getIdColecionador()) {
                    duplicado = true;
                }
            }
            //Deleta o arquivo
            arquivo.delete();
        } else {
            arquivo.createNewFile();
            colecionadores = new ArrayList<>();
        }

        if (duplicado) {
            JOptionPane.showMessageDialog(null, "Já existe um colecionador com ID " + colecionador.getIdColecionador() + ".");
            return false;
        } else {
            colecionadores.add(colecionador);
            //Cria um arquivo novo para salvar o array atualizado
            FileOutputStream arquivoGrav = new FileOutputStream(arquivo);
            ObjectOutputStream objGravar = new ObjectOutputStream(arquivoGrav);
            objGravar.writeObject(colecionadores);
            JOptionPane.showMessageDialog(null, "Colecionador ID " + colecionador.getIdColecionador() + " cadastrado com sucesso.");
            return true;
        }
    }

    public Colecionador RecuperaColecionadorPorID(int id) throws Exception {
        File arquivo = new File("C:/Distrib4/Colecionadores.dst");
        Colecionador colecionador = null;
        if (arquivo.exists()) {
            colecionadores = RecuperarColecionadores();
            for (Colecionador c : colecionadores) {
                if (c.getIdColecionador() == id) {
                    colecionador = c;
                    break;
                }
            }
        }
        if (colecionador == null) {
            JOptionPane.showMessageDialog(null, "Usuário não cadastrado.");
        }
        return colecionador;
    }

    public ArrayList<Colecionador> RecuperarColecionadores() throws Exception {
        File arquivo = new File("C:/Distrib4/Colecionadores.dst");
        if (arquivo.exists()) {
            FileInputStream arquivoLeitura = new FileInputStream("C:/Distrib4/Colecionadores.dst");
            if (arquivoLeitura.available() != 0) {
                ObjectInputStream objLeitura = new ObjectInputStream(arquivoLeitura);
                colecionadores = (ArrayList<Colecionador>) objLeitura.readObject();
            }
        }
        return colecionadores;
    }

    public void RemoverColecionador(int idColecionador) throws Exception {
        File arquivo = new File("C:/Distrib4/Colecionadores.dst");
        if (arquivo.exists()) {
            colecionadores = RecuperarColecionadores();
            Colecionador colecionador = null;
            for (Colecionador c : colecionadores) {
                if (c.getIdColecionador() == idColecionador) {
                    colecionador = c;
                }
            }
            if (colecionador != null) {
                colecionadores.remove(colecionador);
            }
            arquivo.delete();
            FileOutputStream arquivoGrav = new FileOutputStream(arquivo);
            ObjectOutputStream objGravar = new ObjectOutputStream(arquivoGrav);
            objGravar.writeObject(colecionadores);
            JOptionPane.showMessageDialog(null, "Colecionador ID " + idColecionador + " removido com sucesso.");
        } else {
            JOptionPane.showMessageDialog(null, "Usuário não cadastrado.");
        }
    }

}
