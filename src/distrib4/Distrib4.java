/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package distrib4;

import GUI.JanelaInicial;
import javax.swing.UIManager;

/**
 *
 * @author Rafael
 */
public class Distrib4 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            JanelaInicial ji = new JanelaInicial();
            ji.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
