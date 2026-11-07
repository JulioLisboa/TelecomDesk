/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package br.com.visao;

import br.com.model.Sessao;
import br.com.model.Operadora;
import br.com.controle.OperadoraController;
import br.com.controle.ChipController;
import br.com.dao.ChipDAO;
import br.com.model.Chip;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class TelaChips extends javax.swing.JFrame {

    // Variável global agora usa a Sessão do novo backend
    private Sessao sessaoLogada;

    // Construtor recebe a Sessão
    public TelaChips(Sessao sessao) { 
        initComponents();
        this.sessaoLogada = sessao;
        
        // Bloqueia redimensionamento e centraliza
        this.setResizable(false); 
        this.setLocationRelativeTo(null);
        
        // Desativa o botão desta página no menu lateral
        jGerenciarChips2.setEnabled(false); 
        
        carregarComboBoxes();
        carregarTabela();
        configurarSelecaoTabela();
        if (this.sessaoLogada != null && this.sessaoLogada.getUsuario() != null) {
            jNomeUsu.setText("Olá, " + this.sessaoLogada.getUsuario().getNomeUsu());
        }
    }
    
    // Selecionar um chip carrega os valores REAIS do banco para edição.
    private void configurarSelecaoTabela() {
        tbChip.setRowSelectionAllowed(true);
        tbChip.setColumnSelectionAllowed(false);
        tbChip.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tbChip.getSelectionModel().addListSelectionListener(evento -> {
            if (!evento.getValueIsAdjusting()) {
                preencherCamposSelecionados();
            }
        });
        tbChip.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evento) {
                int linha = tbChip.rowAtPoint(evento.getPoint());
                if (linha >= 0) {
                    tbChip.setRowSelectionInterval(linha, linha);
                    preencherCamposSelecionados();
                }
            }
        });
    }

    private void preencherCamposSelecionados() {
        int linha = tbChip.getSelectedRow();
        if (linha < 0) return;
        Object id = tbChip.getValueAt(linha, 0);
        if (id == null) return;
        try {
            // O resultado da tabela exibe nome da operadora; buscamos o ID real.
            ChipDAO dao = new ChipDAO();
            Chip chip = dao.buscarPorIdChip(id.toString());
            if (chip == null) {
                JOptionPane.showMessageDialog(this, "Chip não encontrado no banco de dados.");
                return;
            }
            jIccid.setText(chip.getIccidChip());
            jNumeroTel.setText(chip.getNumeroChip());
            selecionarOperadora(chip.getIdOpe());
        } catch (java.sql.SQLException erro) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar chip: " + erro.getMessage());
        }
    }

    private void selecionarOperadora(String idOperadora) {
        jOperadora.setSelectedIndex(-1);
        for (int i = 0; i < jOperadora.getItemCount(); i++) {
            Object item = jOperadora.getItemAt(i);
            if (item instanceof Operadora &&
                    java.util.Objects.equals(((Operadora) item).getIdOpe(), idOperadora)) {
                jOperadora.setSelectedIndex(i);
                return;
            }
        }
        System.err.println("Operadora do chip não encontrada na lista: " + idOperadora);
    }

    private void carregarComboBoxes() {
        jOperadora.removeAllItems();
        
        try {
            // Carrega as Operadoras do Banco de Dados
            OperadoraController opeControl = new OperadoraController();
            List<Operadora> operadoras = opeControl.listar();
            
            if (operadoras != null && !operadoras.isEmpty()) {
                for (Operadora op : operadoras) {
                    jOperadora.addItem(op);
                }
            } else {
                jOperadora.addItem("Sem operadoras cadastradas");
            }
        } catch (Exception e) {
            System.out.println("Erro ao carregar combobox de operadoras: " + e.getMessage());
        }
    }
    private void carregarTabela() {
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) tbChip.getModel();
        model.setRowCount(0); 

        try {
            ChipController controle = new ChipController();
            java.util.List<Chip> lista = controle.listar(this.sessaoLogada);

            if (lista != null) {
                for (br.com.model.Chip c : lista) {
                    model.addRow(new Object[]{
                        c.getIdChip(),      // 1ª Coluna: ID
                        c.getIccidChip(),   // 2ª Coluna: ICCID
                        c.getNumeroChip(),  // 3ª Coluna: Número de Telefone
                        c.getIdOpe()        // 4ª Coluna: Operadora (Agora exibe o NOME graças ao DAO!)
                    });
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao carregar tabela de chips: " + e.getMessage());
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

        jPanel4 = new javax.swing.JPanel();
        jGerenciarAparelhos2 = new javax.swing.JButton();
        jGerenciarEmprestimos2 = new javax.swing.JButton();
        jGerenciarColaboradores2 = new javax.swing.JButton();
        jGerenciarChips2 = new javax.swing.JButton();
        jDashboard1 = new javax.swing.JButton();
        jSair = new javax.swing.JButton();
        jNomeUsu = new javax.swing.JLabel();
        jIccid = new javax.swing.JTextField();
        jNumeroTel = new javax.swing.JTextField();
        jOperadora = new javax.swing.JComboBox<>();
        jCadastrar = new javax.swing.JButton();
        jExcluir = new javax.swing.JButton();
        jLimpar = new javax.swing.JButton();
        jEditar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbChip = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1200, 600));

        jPanel4.setBackground(new java.awt.Color(0, 0, 0));

        jGerenciarAparelhos2.setBackground(new java.awt.Color(102, 102, 102));
        jGerenciarAparelhos2.setForeground(new java.awt.Color(255, 255, 255));
        jGerenciarAparelhos2.setText("Gerenciar Aparelhos");
        jGerenciarAparelhos2.setContentAreaFilled(false);
        jGerenciarAparelhos2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGerenciarAparelhos2ActionPerformed(evt);
            }
        });

        jGerenciarEmprestimos2.setBackground(new java.awt.Color(102, 102, 102));
        jGerenciarEmprestimos2.setForeground(new java.awt.Color(255, 255, 255));
        jGerenciarEmprestimos2.setText("Gerenciar Emprestimos");
        jGerenciarEmprestimos2.setContentAreaFilled(false);
        jGerenciarEmprestimos2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGerenciarEmprestimos2ActionPerformed(evt);
            }
        });

        jGerenciarColaboradores2.setBackground(new java.awt.Color(102, 102, 102));
        jGerenciarColaboradores2.setForeground(new java.awt.Color(255, 255, 255));
        jGerenciarColaboradores2.setText("Gerenciar Colaboradores");
        jGerenciarColaboradores2.setBorderPainted(false);
        jGerenciarColaboradores2.setContentAreaFilled(false);
        jGerenciarColaboradores2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGerenciarColaboradores2ActionPerformed(evt);
            }
        });

        jGerenciarChips2.setBackground(new java.awt.Color(102, 102, 102));
        jGerenciarChips2.setForeground(new java.awt.Color(255, 255, 255));
        jGerenciarChips2.setText("Gerenciar Chips");
        jGerenciarChips2.setContentAreaFilled(false);
        jGerenciarChips2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGerenciarChips2ActionPerformed(evt);
            }
        });

        jDashboard1.setBackground(new java.awt.Color(102, 102, 102));
        jDashboard1.setForeground(new java.awt.Color(255, 255, 255));
        jDashboard1.setText("Dashboard");
        jDashboard1.setContentAreaFilled(false);
        jDashboard1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jDashboard1ActionPerformed(evt);
            }
        });

        jSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/visao/—Pngtree—vector logout icon_4162877.png"))); // NOI18N
        jSair.setContentAreaFilled(false);
        jSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jSairActionPerformed(evt);
            }
        });

        jNomeUsu.setForeground(new java.awt.Color(255, 255, 255));
        jNomeUsu.setText("Usuario:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jGerenciarColaboradores2)
                    .addComponent(jGerenciarChips2)
                    .addComponent(jDashboard1)
                    .addComponent(jGerenciarEmprestimos2)
                    .addComponent(jGerenciarAparelhos2)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jNomeUsu)
                            .addComponent(jSair, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jNomeUsu)
                .addGap(35, 35, 35)
                .addComponent(jDashboard1)
                .addGap(18, 18, 18)
                .addComponent(jGerenciarChips2)
                .addGap(18, 18, 18)
                .addComponent(jGerenciarAparelhos2)
                .addGap(18, 18, 18)
                .addComponent(jGerenciarEmprestimos2)
                .addGap(18, 18, 18)
                .addComponent(jGerenciarColaboradores2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSair, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jOperadora.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jCadastrar.setText("Cadastrar");
        jCadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCadastrarActionPerformed(evt);
            }
        });

        jExcluir.setText("Excluir");
        jExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jExcluirActionPerformed(evt);
            }
        });

        jLimpar.setText("Limpar Campos");
        jLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jLimparActionPerformed(evt);
            }
        });

        jEditar.setText("Editar");
        jEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jEditarActionPerformed(evt);
            }
        });

        jScrollPane1.setPreferredSize(new java.awt.Dimension(1200, 600));

        tbChip.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "ICCID", "Número de Telefone", "Operadora"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tbChip);

        jLabel1.setText("ICCID:");

        jLabel2.setText("Número de Telefone:");

        jLabel3.setText("Operadora:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 792, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jExcluir, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jEditar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLimpar)
                            .addComponent(jCadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jIccid, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jNumeroTel, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jOperadora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jNumeroTel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jIccid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jOperadora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jCadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 541, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLimpar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jExcluir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jEditar)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jGerenciarAparelhos2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGerenciarAparelhos2ActionPerformed
        TelaAparelhos tela = new TelaAparelhos(this.sessaoLogada);
        tela.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jGerenciarAparelhos2ActionPerformed

    private void jGerenciarEmprestimos2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGerenciarEmprestimos2ActionPerformed
        // TODO add your handling code here:
        TelaEmprestimo tela = new TelaEmprestimo(this.sessaoLogada);
        tela.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jGerenciarEmprestimos2ActionPerformed

    private void jGerenciarColaboradores2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGerenciarColaboradores2ActionPerformed
        // TODO add your handling code here:
        TelaColaboradores tela = new TelaColaboradores(this.sessaoLogada);
        tela.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jGerenciarColaboradores2ActionPerformed

    private void jGerenciarChips2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGerenciarChips2ActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jGerenciarChips2ActionPerformed

    private void jDashboard1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jDashboard1ActionPerformed
        // TODO add your handling code here:
        TelaDashboard dashboard = new TelaDashboard(this.sessaoLogada);
        dashboard.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jDashboard1ActionPerformed

    private void jCadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCadastrarActionPerformed
        // TODO add your handling code here:
        try {
            String iccid = jIccid.getText();
            String numero = jNumeroTel.getText();
            
            // Verifica se a operadora é válida
            if (jOperadora.getSelectedItem() == null || jOperadora.getSelectedItem() instanceof String) {
                JOptionPane.showMessageDialog(this, "Selecione uma Operadora válida!");
                return;
            }
            
            // Cast do Object para a Classe Operadora
            Operadora op = (Operadora) jOperadora.getSelectedItem();
            
            ChipController controle = new ChipController();
            
            // Envia Sessão, ICCID, Número e o ID da Operadora
            if (controle.cadastrar(this.sessaoLogada, iccid, numero, op.getIdOpe())) {
                JOptionPane.showMessageDialog(this, "Chip cadastrado com sucesso!");
                jLimparActionPerformed(evt); // Limpa os campos após o sucesso
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar. Verifique os dados e a sua permissão.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
     
    }//GEN-LAST:event_jCadastrarActionPerformed

    private void jExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jExcluirActionPerformed
        // TODO add your handling code here:
        int linhaSelecionada = tbChip.getSelectedRow();
        if (linhaSelecionada == -1) {
            javax.swing.JOptionPane.showMessageDialog(this, "Selecione um chip na tabela para excluir!");
            return;
        }

        String idChip = tbChip.getValueAt(linhaSelecionada, 0).toString();
        int confirm = javax.swing.JOptionPane.showConfirmDialog(this, "Deseja realmente excluir este chip?", "Confirmação", javax.swing.JOptionPane.YES_NO_OPTION);

        if (confirm == javax.swing.JOptionPane.YES_OPTION) {
            try {
                br.com.dao.ChipDAO dao = new br.com.dao.ChipDAO();
                if (dao.excluir(idChip)) {
                    javax.swing.JOptionPane.showMessageDialog(this, "Chip excluído com sucesso!");
                    carregarTabela();
                    jLimparActionPerformed(evt);
                } else {
                    javax.swing.JOptionPane.showMessageDialog(this, "Erro ao excluir chip.");
                }
            } catch (Exception e) {
                javax.swing.JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
            }
        }
    }//GEN-LAST:event_jExcluirActionPerformed

    private void jLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jLimparActionPerformed
        // TODO add your handling code here:
        jIccid.setText("");
        jNumeroTel.setText("");
        if(jOperadora.getItemCount() > 0) jOperadora.setSelectedIndex(0);
        tbChip.clearSelection();
    }//GEN-LAST:event_jLimparActionPerformed

    private void jSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jSairActionPerformed
        // TODO add your handling code here:
        int confirmacao = javax.swing.JOptionPane.showConfirmDialog(this, 
            "Deseja realmente sair do sistema?", "Confirmar Logout", 
            javax.swing.JOptionPane.YES_NO_OPTION);
            
        if (confirmacao == javax.swing.JOptionPane.YES_OPTION) {
            // Fecha a janela atual
            this.dispose();
            
            // Instancia e abre a Tela de Login (substitua TelaLogin pelo nome exato do seu ficheiro de login)
            TelaLogin telaLogin = new br.com.visao.TelaLogin();
            telaLogin.setVisible(true);
        }
    }//GEN-LAST:event_jSairActionPerformed

    private void jEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jEditarActionPerformed
        int linha = tbChip.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um chip na tabela para editar!");
            return;
        }

        String iccid = jIccid.getText().trim();
        String numero = jNumeroTel.getText().trim();
        Object operadoraSelecionada = jOperadora.getSelectedItem();
        if (iccid.isEmpty() || numero.isEmpty() || !(operadoraSelecionada instanceof Operadora)) {
            JOptionPane.showMessageDialog(this, "Preencha ICCID, Número e Operadora corretamente.");
            return;
        }

        String idChip = tbChip.getValueAt(linha, 0).toString();
        String idOperadora = ((Operadora) operadoraSelecionada).getIdOpe();
        ChipController controle = new ChipController();
        if (controle.editar(this.sessaoLogada, idChip, iccid, numero, idOperadora)) {
            JOptionPane.showMessageDialog(this, "Chip atualizado com sucesso!");
            carregarTabela();
            jLimparActionPerformed(evt);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Não foi possível atualizar o chip. Confira os dados, a permissão e o log do NetBeans.");
        }
    }//GEN-LAST:event_jEditarActionPerformed

                                    
    /**
     * @param args the command line arguments
     */
   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jCadastrar;
    private javax.swing.JButton jDashboard1;
    private javax.swing.JButton jEditar;
    private javax.swing.JButton jExcluir;
    private javax.swing.JButton jGerenciarAparelhos2;
    private javax.swing.JButton jGerenciarChips2;
    private javax.swing.JButton jGerenciarColaboradores2;
    private javax.swing.JButton jGerenciarEmprestimos2;
    private javax.swing.JTextField jIccid;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JButton jLimpar;
    private javax.swing.JLabel jNomeUsu;
    private javax.swing.JTextField jNumeroTel;
    private javax.swing.JComboBox<Object> jOperadora;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JButton jSair;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbChip;
    // End of variables declaration//GEN-END:variables
}
