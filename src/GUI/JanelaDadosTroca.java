/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Comunicacao.RMIClient;
import Modelo.Cartao;
import Modelo.Colecionador;
import Modelo.ColecionadorEncontrado;
import Modelo.Troca;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bruno
 */
public class JanelaDadosTroca extends javax.swing.JDialog {

    Troca troca;

    /**
     * Creates new form JanelaAvisoTroca
     *
     * @param idTroca
     * @throws java.lang.InterruptedException
     */
    public JanelaDadosTroca(String idTroca) throws InterruptedException {
        initComponents();
        setModal(true);

        Colecionador logado = Colecionador.getInstancia();
        this.setTitle("Solicitação de Troca - " + logado.getIdColecionador());

        troca = logado.getTrocaQueSouParticipantePorId(idTroca);
        Cartao cartaoRecebe;
        Cartao cartaoManda;
        ColecionadorEncontrado outroColecionador;

        //Verificar se sou solicitante ou solicitado
        if (troca.getIdSolicitado() == logado.getIdColecionador()) {
            cartaoRecebe = troca.getCartaoManda();
            cartaoManda = troca.getCartaoRecebe();
            outroColecionador = logado.getUsuarioParticipantePorId(troca.getIdSolicitante());
        } else {
            cartaoRecebe = troca.getCartaoRecebe();
            cartaoManda = troca.getCartaoManda();
            botaoAceitar.setVisible(false);
            botaoRejeitar.setText("Cancelar Solicitação");
            outroColecionador = logado.getUsuarioParticipantePorId(troca.getIdSolicitado());
        }
        
        
        String textoTroca = "Trocar meu " + cartaoManda.getIdCartao() + "-" + cartaoManda.getLocal()
                + " por " + cartaoRecebe.getIdCartao() + "-" + cartaoRecebe.getLocal() + " do usuário "
                + outroColecionador.getIdColecionador() + "-" + outroColecionador.getNome();
        txtTroca.setText(textoTroca);

        switch (troca.getSituacaoTroca()) {
            case 1:
                labelStatus.setForeground(new java.awt.Color(0, 102, 204));
                labelStatus.setText("1-Aguardando");
                break;
            case 2:
                labelStatus.setForeground(new java.awt.Color(0, 102, 204));
                labelStatus.setText("2-Aguardando");
                break;
            case 3:
                labelStatus.setForeground(new java.awt.Color(0, 102, 204));
                labelStatus.setText("3-Aguardando");
                break;
            case 4:
                labelStatus.setForeground(new java.awt.Color(0, 153, 51));
                labelStatus.setText("4-Aguardando");
                botaoAceitar.setVisible(false);
                botaoRejeitar.setVisible(false);
                break;
            case 5:
                labelStatus.setForeground(new java.awt.Color(0, 153, 51));
                labelStatus.setText("5-Efetuada");
                botaoAceitar.setVisible(false);
                botaoRejeitar.setVisible(false);
                break;
            case 6:
                labelStatus.setForeground(new java.awt.Color(204, 0, 0));
                labelStatus.setText("6-Recusada");
                botaoAceitar.setVisible(false);
                botaoRejeitar.setVisible(false);
                break;
            case 7:
                labelStatus.setForeground(new java.awt.Color(204, 0, 0));
                labelStatus.setText("7-Cancelada");
                botaoAceitar.setVisible(false);
                botaoRejeitar.setVisible(false);
                break;
            default:
                labelStatus.setText("ERRO");
                sleep(3000);
                dispose();
                break;
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtTroca = new javax.swing.JTextField();
        labelStatus = new javax.swing.JLabel();
        botaoAceitar = new javax.swing.JButton();
        botaoRejeitar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Solicitação de Troca");

        jLabel2.setText("Status:");

        txtTroca.setEditable(false);

        labelStatus.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        labelStatus.setText("LABELSTATUS");

        botaoAceitar.setBackground(new java.awt.Color(0, 153, 0));
        botaoAceitar.setText("Aceitar");
        botaoAceitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoAceitarActionPerformed(evt);
            }
        });

        botaoRejeitar.setBackground(new java.awt.Color(204, 0, 0));
        botaoRejeitar.setText("Rejeitar");
        botaoRejeitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoRejeitarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelStatus))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtTroca, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(botaoAceitar, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(botaoRejeitar, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(labelStatus))
                .addGap(18, 18, 18)
                .addComponent(txtTroca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botaoRejeitar)
                    .addComponent(botaoAceitar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void botaoAceitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoAceitarActionPerformed
        try {
            Colecionador logado = Colecionador.getInstancia();
            RMIClient rmic = new RMIClient(troca.getIdCoordenador());
            rmic.ResponderTroca(troca.getId(), logado.getIdColecionador(), true);
            dispose();
        } catch (Exception ex) {
            Logger.getLogger(JanelaDadosTroca.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_botaoAceitarActionPerformed

    private void botaoRejeitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoRejeitarActionPerformed
        try {
            Colecionador logado = Colecionador.getInstancia();
            RMIClient rmic = new RMIClient(troca.getIdCoordenador());
            rmic.ResponderTroca(troca.getId(), logado.getIdColecionador(), false);
            dispose();
        } catch (Exception ex) {
            Logger.getLogger(JanelaDadosTroca.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_botaoRejeitarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botaoAceitar;
    private javax.swing.JButton botaoRejeitar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel labelStatus;
    private javax.swing.JTextField txtTroca;
    // End of variables declaration//GEN-END:variables
}
