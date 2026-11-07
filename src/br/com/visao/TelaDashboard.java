/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package br.com.visao;

import br.com.controle.EmprestimoController;
import br.com.model.Emprestimo;
import br.com.model.Sessao;
import javax.swing.JOptionPane;

public class TelaDashboard extends javax.swing.JFrame {

    // 1. Variável global agora usa a Sessão do novo backend
    private Sessao sessaoLogada;

    // 2. Construtor recebe a Sessão
    public TelaDashboard(Sessao sessao) {
        initComponents();
        
        this.sessaoLogada = sessao;
        
        // Bloqueia a tela cheia e centraliza (resolve o problema visual)
        this.setResizable(false);
        this.setLocationRelativeTo(null); 
        
        // Desativa o botão Dashboard, pois já estamos nele
        jDashboard.setEnabled(false);
        
        configurarPermissoes();
        carregarTabela();
        carregarTabela2();
       
        if (this.sessaoLogada != null && this.sessaoLogada.getUsuario() != null) {
            jNomeUsu.setText("Olá, " + this.sessaoLogada.getUsuario().getNomeUsu());
        }
    }

    private void configurarPermissoes() {
        // Extrai as informações do usuário a partir da sessão logada
        String nome = sessaoLogada.getUsuario().getNomeUsu();
        String perfil = sessaoLogada.getUsuario().getPerfilUsu();

        jBoasVindas.setText("Bem-vindo(a), " + nome);

        // Garante que TODOS (Técnicos e Colaboradores) veem os botões de ação
        jAceitarEmprestimo.setVisible(true);
        jRecusarEmprestimo.setVisible(true);

        if ("COLABORADOR".equals(perfil)) {
            // Oculta apenas os menus de gestão lateral
            jGerenciarAparelhos1.setVisible(false);
            jGerenciarChips1.setVisible(false);
            jGerenciarEmprestimos1.setVisible(false);
            jGerenciarColaboradores1.setVisible(false);
            
        } else if ("TECNICO".equals(perfil)) {
            // Libera os menus de gestão para o técnico
            jGerenciarAparelhos1.setVisible(true);
            jGerenciarChips1.setVisible(true);
            jGerenciarEmprestimos1.setVisible(true);
            jGerenciarColaboradores1.setVisible(true);
        }
    }
   private void carregarTabela() {
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) tbDashboard.getModel();
        model.setRowCount(0); 

        try {
            br.com.controle.EmprestimoController controleEmp = new br.com.controle.EmprestimoController();
            java.util.List<br.com.model.Emprestimo> listaEmp = controleEmp.listar(this.sessaoLogada);
            
            String idUsuarioLogado = this.sessaoLogada.getUsuario().getIdUsu();

            if (listaEmp != null) {
                for (br.com.model.Emprestimo e : listaEmp) {
                    // FILTRO ESTRITO: Apenas os empréstimos que pertencem ao usuário logado
                    if (e.getIdUsu() != null && e.getIdUsu().equals(idUsuarioLogado)) {
                        model.addRow(new Object[]{
                            e.getIdEmp(),                             // 1ª Coluna: ID Empréstimo
                            "Aparelho e Chip",                        // 2ª Coluna: Tipo
                            e.getIdApa() + " / " + e.getIdChip(),     // 3ª Coluna: Identificação
                            "Registrado"                              // 4ª Coluna: Data (Fixo para evitar erro)
                        });
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao carregar Dashboard: " + e.getMessage());
        }
    }

   private void carregarTabela2() {
        if (this.sessaoLogada == null || this.sessaoLogada.getUsuario() == null) return;
        
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) tbNotificacoes.getModel();
        model.setRowCount(0); 

        try {
            String idUsuarioLogado = this.sessaoLogada.getUsuario().getIdUsu();
            br.com.controle.EmprestimoController controle = new br.com.controle.EmprestimoController();
            java.util.List<br.com.model.Emprestimo> lista = controle.listar(this.sessaoLogada);

            if (lista != null) {
                for (br.com.model.Emprestimo e : lista) {
                    // FILTRO ESTRITO: Apenas as notificações que pertencem ao usuário logado
                    if (e.getIdUsu() != null && e.getIdUsu().equals(idUsuarioLogado)) {
                        if ("AGUARDANDO ENTREGA".equals(e.getStatusEmp()) || "AGUARDANDO DEVOLUCAO".equals(e.getStatusEmp())) {
                            model.addRow(new Object[]{
                                e.getIdEmp(),                             // 1ª Coluna: ID Empréstimo
                                "Apa: " + e.getIdApa(),                   // 2ª Coluna: Ativo Solicitado
                                "Pendente"                                // 3ª Coluna: Data (Fixo para evitar erro)
                            });
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao carregar Notificações: " + e.getMessage());
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

        jBoasVindas = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbDashboard = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbNotificacoes = new javax.swing.JTable();
        jAceitarEmprestimo = new javax.swing.JButton();
        jRecusarEmprestimo = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jGerenciarAparelhos1 = new javax.swing.JButton();
        jGerenciarEmprestimos1 = new javax.swing.JButton();
        jGerenciarColaboradores1 = new javax.swing.JButton();
        jGerenciarChips1 = new javax.swing.JButton();
        jDashboard = new javax.swing.JButton();
        jSair = new javax.swing.JButton();
        jNomeUsu = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jBoasVindas.setText("Bem-Vindo");

        tbDashboard.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID Empréstimo", "Tipo (Aparelho / Chip)", "Identificação (IMEI / Número)", "Data do Empréstimo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tbDashboard);

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));

        tbNotificacoes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID Empréstimo", "Ativo Solicitado", "Data"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tbNotificacoes);

        jAceitarEmprestimo.setBackground(new java.awt.Color(51, 204, 0));
        jAceitarEmprestimo.setText("Aceitar");
        jAceitarEmprestimo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jAceitarEmprestimoActionPerformed(evt);
            }
        });

        jRecusarEmprestimo.setBackground(new java.awt.Color(153, 0, 0));
        jRecusarEmprestimo.setText("Recusar");
        jRecusarEmprestimo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRecusarEmprestimoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(jAceitarEmprestimo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 113, Short.MAX_VALUE)
                .addComponent(jRecusarEmprestimo)
                .addGap(33, 33, 33))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 485, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jAceitarEmprestimo)
                    .addComponent(jRecusarEmprestimo))
                .addGap(24, 24, 24))
        );

        jPanel3.setBackground(new java.awt.Color(0, 0, 0));

        jGerenciarAparelhos1.setBackground(new java.awt.Color(102, 102, 102));
        jGerenciarAparelhos1.setForeground(new java.awt.Color(255, 255, 255));
        jGerenciarAparelhos1.setText("Gerenciar Aparelhos");
        jGerenciarAparelhos1.setContentAreaFilled(false);
        jGerenciarAparelhos1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGerenciarAparelhos1ActionPerformed(evt);
            }
        });

        jGerenciarEmprestimos1.setBackground(new java.awt.Color(102, 102, 102));
        jGerenciarEmprestimos1.setForeground(new java.awt.Color(255, 255, 255));
        jGerenciarEmprestimos1.setText("Gerenciar Emprestimos");
        jGerenciarEmprestimos1.setContentAreaFilled(false);
        jGerenciarEmprestimos1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGerenciarEmprestimos1ActionPerformed(evt);
            }
        });

        jGerenciarColaboradores1.setBackground(new java.awt.Color(102, 102, 102));
        jGerenciarColaboradores1.setForeground(new java.awt.Color(255, 255, 255));
        jGerenciarColaboradores1.setText("Gerenciar Colaboradores");
        jGerenciarColaboradores1.setContentAreaFilled(false);
        jGerenciarColaboradores1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGerenciarColaboradores1ActionPerformed(evt);
            }
        });

        jGerenciarChips1.setBackground(new java.awt.Color(102, 102, 102));
        jGerenciarChips1.setForeground(new java.awt.Color(255, 255, 255));
        jGerenciarChips1.setText("Gerenciar Chips");
        jGerenciarChips1.setAutoscrolls(true);
        jGerenciarChips1.setBorderPainted(false);
        jGerenciarChips1.setContentAreaFilled(false);
        jGerenciarChips1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGerenciarChips1ActionPerformed(evt);
            }
        });

        jDashboard.setBackground(new java.awt.Color(102, 102, 102));
        jDashboard.setForeground(new java.awt.Color(255, 255, 255));
        jDashboard.setText("Dashboard");
        jDashboard.setContentAreaFilled(false);
        jDashboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jDashboardActionPerformed(evt);
            }
        });

        jSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/visao/—Pngtree—vector logout icon_4162877.png"))); // NOI18N
        jSair.setBorderPainted(false);
        jSair.setContentAreaFilled(false);
        jSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jSairActionPerformed(evt);
            }
        });

        jNomeUsu.setForeground(new java.awt.Color(255, 255, 255));
        jNomeUsu.setText("Usuario:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jGerenciarColaboradores1)
                    .addComponent(jGerenciarEmprestimos1)
                    .addComponent(jGerenciarAparelhos1)
                    .addComponent(jGerenciarChips1)
                    .addComponent(jDashboard)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSair, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jNomeUsu))))
                .addGap(16, 16, 16))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jNomeUsu)
                .addGap(36, 36, 36)
                .addComponent(jDashboard)
                .addGap(18, 18, 18)
                .addComponent(jGerenciarChips1)
                .addGap(18, 18, 18)
                .addComponent(jGerenciarAparelhos1)
                .addGap(18, 18, 18)
                .addComponent(jGerenciarEmprestimos1)
                .addGap(18, 18, 18)
                .addComponent(jGerenciarColaboradores1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSair, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 890, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(369, 369, 369)
                        .addComponent(jBoasVindas)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jBoasVindas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap())
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jGerenciarAparelhos1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGerenciarAparelhos1ActionPerformed
       TelaAparelhos tela = new TelaAparelhos(this.sessaoLogada);
        tela.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jGerenciarAparelhos1ActionPerformed

    private void jGerenciarEmprestimos1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGerenciarEmprestimos1ActionPerformed
        // TODO add your handling code here:
        TelaEmprestimo tela = new TelaEmprestimo(this.sessaoLogada);
        tela.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jGerenciarEmprestimos1ActionPerformed

    private void jGerenciarColaboradores1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGerenciarColaboradores1ActionPerformed
        // TODO add your handling code here:
        TelaColaboradores tela = new TelaColaboradores(this.sessaoLogada);
        tela.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jGerenciarColaboradores1ActionPerformed

    private void jGerenciarChips1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGerenciarChips1ActionPerformed
        // TODO add your handling code here:
        TelaChips tela = new TelaChips(this.sessaoLogada);
        tela.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jGerenciarChips1ActionPerformed

    private void jDashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jDashboardActionPerformed
        // TODO add your handling code here:
        TelaDashboard dashboard = new TelaDashboard(this.sessaoLogada);
        dashboard.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jDashboardActionPerformed

    private void jAceitarEmprestimoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jAceitarEmprestimoActionPerformed
        // TODO add your handling code here:
        int linhaSelecionada = tbNotificacoes.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um empréstimo na tabela!");
            return;
        }

        String idEmp = tbNotificacoes.getValueAt(linhaSelecionada, 0).toString();

        try {
            br.com.controle.EmprestimoController controle = new br.com.controle.EmprestimoController();
            if (controle.aceitarEmprestimo(this.sessaoLogada, idEmp)) {
                JOptionPane.showMessageDialog(this, "Empréstimo aceito com sucesso!");
                carregarTabela();  // Atualiza a tabela principal
                carregarTabela2(); // Atualiza a tabela de notificações (remove o pedido)
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao aceitar empréstimo. Verifique se tem permissão.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }//GEN-LAST:event_jAceitarEmprestimoActionPerformed

    private void jRecusarEmprestimoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRecusarEmprestimoActionPerformed
        // TODO add your handling code here:
       int linhaSelecionada = tbNotificacoes.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um empréstimo na tabela!");
            return;
        }

        String idEmp = tbNotificacoes.getValueAt(linhaSelecionada, 0).toString();
        int confirm = JOptionPane.showConfirmDialog(this, "Deseja realmente recusar/cancelar este empréstimo?", "Confirmação", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                br.com.controle.EmprestimoController controle = new br.com.controle.EmprestimoController();
                if (controle.cancelarEmprestimo(this.sessaoLogada, idEmp)) {
                    JOptionPane.showMessageDialog(this, "Empréstimo recusado/cancelado com sucesso!");
                    carregarTabela();  // Atualiza a tabela principal
                    carregarTabela2(); // Atualiza a tabela de notificações
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao recusar empréstimo.");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
            }
        }
    }//GEN-LAST:event_jRecusarEmprestimoActionPerformed

    private void jSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jSairActionPerformed
        // TODO add your handling code here:
        int confirmacao = javax.swing.JOptionPane.showConfirmDialog(this, 
            "Deseja realmente sair do sistema?", "Confirmar Logout", 
            javax.swing.JOptionPane.YES_NO_OPTION);
            
        if (confirmacao == javax.swing.JOptionPane.YES_OPTION) {
            // Fecha a janela atual
            this.dispose();
            
            // Instancia e abre a Tela de Login (substitua TelaLogin pelo nome exato do seu ficheiro de login)
            TelaLogin telaLogin = new TelaLogin();
            telaLogin.setVisible(true);
        }
    }//GEN-LAST:event_jSairActionPerformed

    /**
     * @param args the command line arguments
     */
   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jAceitarEmprestimo;
    private javax.swing.JLabel jBoasVindas;
    private javax.swing.JButton jDashboard;
    private javax.swing.JButton jGerenciarAparelhos1;
    private javax.swing.JButton jGerenciarChips1;
    private javax.swing.JButton jGerenciarColaboradores1;
    private javax.swing.JButton jGerenciarEmprestimos1;
    private javax.swing.JLabel jNomeUsu;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JButton jRecusarEmprestimo;
    private javax.swing.JButton jSair;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tbDashboard;
    private javax.swing.JTable tbNotificacoes;
    // End of variables declaration//GEN-END:variables
}
