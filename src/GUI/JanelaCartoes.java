/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Comunicacao.RMIClient;
import IOarquivo.IOCartao;
import Modelo.Cartao;
import Modelo.Colecionador;
import Modelo.ColecionadorEncontrado;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Rafael
 */
public class JanelaCartoes extends javax.swing.JDialog {

    private int idUsuario;
    Colecionador logado;

    /**
     * Creates new form JanelaCartoes
     */
    public JanelaCartoes(int idUsuario) throws Exception {
        initComponents();
        this.idUsuario = idUsuario;
        logado = Colecionador.getInstancia();
        if (logado.getIdColecionador() == idUsuario) {
            btnPedirTroca.setVisible(false);
        } else {
            btnNovoCartao.setVisible(false);
        }
        setModal(true);
        CarregaCartoes();
    }

    /**
     * Carrega os cartões e popula a tabela
     *
     * @throws java.lang.Exception
     */
    public void CarregaCartoes() throws Exception {
        ArrayList<Cartao> cartoes = new ArrayList<>();
        if (idUsuario == logado.getIdColecionador()) {
            IOCartao iocar = new IOCartao();
            cartoes = iocar.RecuperarCartoes();
        } else {
            ColecionadorEncontrado conexao = logado.getUsuarioParticipantePorId(idUsuario);
            RMIClient rmic = new RMIClient(conexao);
            cartoes = rmic.SolicitaListaCartoes(idUsuario);
        }

        if (cartoes != null) {
            Object[][] tabela = new Object[cartoes.size()][3];
            for (int i = 0; i < cartoes.size(); i++) {
                tabela[i][0] = cartoes.get(i).getIdCartao();
                tabela[i][1] = cartoes.get(i).getLocal();
                ColecionadorEncontrado ce = logado.getUsuarioParticipantePorId(cartoes.get(i).getIdProprietario());
                tabela[i][2] = ce.getNome();
            }
            PopulaTabela(tabela);
        }

    }

    public void PopulaTabela(Object[][] tabela) throws Exception {
        tbCartoes.setModel(new javax.swing.table.DefaultTableModel(
                tabela,
                new String[]{
                    "ID", "Local", "Proprietário"
                }
        ) {
            Class[] types = new Class[]{
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class,};
            boolean[] canEdit = new boolean[]{
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        tbCartoes.setColumnSelectionAllowed(true);
        jScrollPane2.setViewportView(tbCartoes);
        tbCartoes.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (tbCartoes.getColumnModel().getColumnCount() > 0) {
            tbCartoes.getColumnModel().getColumn(0).setResizable(false);
            tbCartoes.getColumnModel().getColumn(0).setPreferredWidth(28);
            tbCartoes.getColumnModel().getColumn(1).setPreferredWidth(95);
            tbCartoes.getColumnModel().getColumn(2).setPreferredWidth(95);
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

        jScrollPane2 = new javax.swing.JScrollPane();
        tbCartoes = new javax.swing.JTable();
        btnNovoCartao = new javax.swing.JButton();
        btnPedirTroca = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        tbCartoes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Cartão", "Descrição", "Proprietário"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tbCartoes);

        btnNovoCartao.setText("Novo Cartão");
        btnNovoCartao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoCartaoActionPerformed(evt);
            }
        });

        btnPedirTroca.setText("Pedir Troca");
        btnPedirTroca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPedirTrocaActionPerformed(evt);
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
                        .addComponent(btnNovoCartao)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnPedirTroca))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 408, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnPedirTroca)
                    .addComponent(btnNovoCartao))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnNovoCartaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoCartaoActionPerformed
        JanelaCadastroCartao jcc = new JanelaCadastroCartao();
        jcc.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnNovoCartaoActionPerformed

    private void btnPedirTrocaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPedirTrocaActionPerformed
        try {
            int selecao = tbCartoes.getSelectedRow();
            if (selecao < 0) {
                JOptionPane.showMessageDialog(null, "É preciso selecionar um cartão.");
            } else {
                DefaultTableModel dtm = (DefaultTableModel) tbCartoes.getModel();
                
                int idSelecionado = (Integer) dtm.getValueAt(selecao, 0);
                String localSelecionado = (String) dtm.getValueAt(selecao, 1);
                
                //IOCartao iocar = new IOCartao();
                Cartao cartaoSelecionado  = new Cartao();
                cartaoSelecionado.setIdCartao(idSelecionado);
                cartaoSelecionado.setLocal(localSelecionado);
                
                this.dispose();
                JanelaSolicitacaoTroca jst = new JanelaSolicitacaoTroca(cartaoSelecionado, idUsuario);
                jst.setVisible(true);
            }
        } catch (Exception ex) {
            Logger.getLogger(JanelaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_btnPedirTrocaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNovoCartao;
    private javax.swing.JButton btnPedirTroca;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tbCartoes;
    // End of variables declaration//GEN-END:variables
}
