/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Modelo.Colecionador;
import Modelo.ColecionadorEncontrado;
import Modelo.Troca;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Rafael
 */
public class JanelaPrincipal extends javax.swing.JFrame {

    /**
     * Creates new form JanelaPrincipal
     */
    public JanelaPrincipal() {
        initComponents();
        Colecionador instancia = Colecionador.getInstancia();
        setTitle("Principal - " + instancia.getIdColecionador() + " - " + instancia.getNomeColecionador());
    }

    public static void atualizarTabelaColecionadores() {
        Colecionador instancia = Colecionador.getInstancia();
        ArrayList<ColecionadorEncontrado> usuarios = instancia.getListaParticipantes();
        Object[][] tabela = new Object[usuarios.size()][3];
        for (int i = 0; i < usuarios.size(); i++) {
            tabela[i][0] = usuarios.get(i).getIdColecionador();
            tabela[i][1] = usuarios.get(i).getNome();
            tabela[i][2] = usuarios.get(i).getNumeroCartoes();
        }

        //popularTabela
        tabelaColecionadores.setModel(new javax.swing.table.DefaultTableModel(
                tabela, new String[]{"ID Colecionador", "Nome", "Qtd. Cartões"}) {
                    Class[] types = new Class[]{
                        java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class
                    };
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
        jScrollPane2.setViewportView(tabelaColecionadores);
        if (tabelaColecionadores.getColumnModel().getColumnCount() > 0) {
            tabelaColecionadores.getColumnModel().getColumn(0).setResizable(false);
            tabelaColecionadores.getColumnModel().getColumn(0).setPreferredWidth(88);
            tabelaColecionadores.getColumnModel().getColumn(1).setPreferredWidth(101);
            tabelaColecionadores.getColumnModel().getColumn(2).setPreferredWidth(101);
        }
    }

    public static void atualizarTabelaTransacoes() {
        Colecionador logado = Colecionador.getInstancia();
        ArrayList<Troca> transacoes = logado.getTrocasQueSouParticipante();
        Object[][] tabela = new Object[transacoes.size()][4];
        for (int i = 0; i < transacoes.size(); i++) {
            tabela[i][0] = transacoes.get(i).getId();

            //Verificar se sou solicitante ou solicitado
            if (transacoes.get(i).getIdSolicitado() == logado.getIdColecionador()) {
                tabela[i][1] = transacoes.get(i).getCartaoRecebe().getIdCartao() + "-" + transacoes.get(i).getCartaoRecebe().getLocal();
                tabela[i][2] = transacoes.get(i).getCartaoManda().getIdCartao() + "-" + transacoes.get(i).getCartaoManda().getLocal();
            } else {
                tabela[i][1] = transacoes.get(i).getCartaoManda().getIdCartao() + "-" + transacoes.get(i).getCartaoManda().getLocal();
                tabela[i][2] = transacoes.get(i).getCartaoRecebe().getIdCartao() + "-" + transacoes.get(i).getCartaoRecebe().getLocal();
            }

            switch (transacoes.get(i).getSituacaoTroca()) {
                case 1:
                    tabela[i][3] = "Aguardando";
                    break;
                case 2:
                    tabela[i][3] = "Aguardando";
                    break;
                case 3:
                    tabela[i][3] = "Aguardando";
                    break;
                case 4:
                    tabela[i][3] = "Aguardando";
                    break;
                case 5:
                    tabela[i][3] = "Efetuada";
                    break;
                case 6:
                    tabela[i][3] = "Recusada";
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Erro");
                    System.exit(0);
                    break;
            }
        }

        tabelaTransacoes.setModel(new javax.swing.table.DefaultTableModel(
                tabela,
                new String[]{
                    "ID", "Trocar Meu", "Trocar Por", "Status da Troca"
                }
        ) {
            Class[] types = new Class[]{
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean[]{
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        jScrollPane3.setViewportView(tabelaTransacoes);
        if (tabelaTransacoes.getColumnModel().getColumnCount() > 0) {
            tabelaTransacoes.getColumnModel().getColumn(0).setResizable(false);
            tabelaTransacoes.getColumnModel().getColumn(0).setPreferredWidth(29);
            tabelaTransacoes.getColumnModel().getColumn(1).setPreferredWidth(87);
            tabelaTransacoes.getColumnModel().getColumn(2).setPreferredWidth(87);
            tabelaTransacoes.getColumnModel().getColumn(3).setPreferredWidth(87);
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

        btnVisualizarCartoes = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelaColecionadores = new javax.swing.JTable();
        btnMeusCartoes = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        btnVisualizarTroca = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabelaTransacoes = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        btnVisualizarCartoes.setText("Visualizar Cartões do Usuário Selecionado");
        btnVisualizarCartoes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVisualizarCartoesActionPerformed(evt);
            }
        });

        tabelaColecionadores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Colecionador", "Nome", "Qtd. Cartões"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tabelaColecionadores);

        btnMeusCartoes.setText("Meus Cartões");
        btnMeusCartoes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMeusCartoesActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 204));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Colecionadores Ativos");

        btnVisualizarTroca.setText("Visualizar Detalhes da Troca Selecionada");
        btnVisualizarTroca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVisualizarTrocaActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 204));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Transações Que Estou Participando");

        tabelaTransacoes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Trocar Meu", "Trocar Por", "Status da Troca"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tabelaTransacoes);
        if (tabelaTransacoes.getColumnModel().getColumnCount() > 0) {
            tabelaTransacoes.getColumnModel().getColumn(0).setResizable(false);
            tabelaTransacoes.getColumnModel().getColumn(0).setPreferredWidth(29);
            tabelaTransacoes.getColumnModel().getColumn(1).setPreferredWidth(87);
            tabelaTransacoes.getColumnModel().getColumn(2).setPreferredWidth(87);
            tabelaTransacoes.getColumnModel().getColumn(3).setPreferredWidth(87);
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(btnVisualizarCartoes)
                                .addGap(18, 18, 18)
                                .addComponent(btnMeusCartoes)
                                .addGap(18, 18, 18)
                                .addComponent(btnVisualizarTroca, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnVisualizarCartoes)
                    .addComponent(btnMeusCartoes)
                    .addComponent(btnVisualizarTroca))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnVisualizarCartoesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVisualizarCartoesActionPerformed
        try {
            int selecao = tabelaColecionadores.getSelectedRow();
            if (selecao < 0) {
                JOptionPane.showMessageDialog(null, "É preciso selecionar um usuário na tabela.");
            } else {
                DefaultTableModel dtm = (DefaultTableModel) tabelaColecionadores.getModel();
                int quantidadeCartoes = (Integer) dtm.getValueAt(selecao, 2);
                if (quantidadeCartoes > 0) {
                    int idSelecionado = (Integer) dtm.getValueAt(selecao, 0);
                    JanelaCartoes jc = new JanelaCartoes(idSelecionado);
                    jc.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "O usuário selecionado não possui cartões.");
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(JanelaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnVisualizarCartoesActionPerformed

    private void btnMeusCartoesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMeusCartoesActionPerformed
        try {
            Colecionador instancia = Colecionador.getInstancia();
            JanelaCartoes jc = new JanelaCartoes(instancia.getIdColecionador());
            jc.setVisible(true);
        } catch (Exception ex) {
            Logger.getLogger(JanelaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnMeusCartoesActionPerformed

	private void btnVisualizarTrocaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVisualizarTrocaActionPerformed
            try {
                int selecao = tabelaTransacoes.getSelectedRow();
                if (selecao < 0) {
                    JOptionPane.showMessageDialog(null, "É preciso selecionar uma transação na tabela.");
                } else {
                    DefaultTableModel dtm = (DefaultTableModel) tabelaTransacoes.getModel();
                    String idSelecionado = (String) dtm.getValueAt(selecao, 0);
                    JanelaDadosTroca av = new JanelaDadosTroca(idSelecionado);
                    av.setVisible(true);
                }
            } catch (Exception ex) {
                Logger.getLogger(JanelaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
    }//GEN-LAST:event_btnVisualizarTrocaActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnMeusCartoes;
    private javax.swing.JButton btnVisualizarCartoes;
    private javax.swing.JButton btnVisualizarTroca;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private static javax.swing.JScrollPane jScrollPane2;
    private static javax.swing.JScrollPane jScrollPane3;
    private static javax.swing.JTable tabelaColecionadores;
    private static javax.swing.JTable tabelaTransacoes;
    // End of variables declaration//GEN-END:variables
}
