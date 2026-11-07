/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package br.com.visao;

import br.com.model.Sessao;
import br.com.model.Tipo_Aparelho;
import br.com.model.Modelo;
import br.com.controle.TipoAparelhoController;
import br.com.controle.ModeloController;
import br.com.controle.AparelhoController;
import br.com.model.Aparelho;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class TelaAparelhos extends javax.swing.JFrame {

    
    private Sessao sessaoLogada;

   
    public TelaAparelhos(Sessao sessao) {
        initComponents();
        this.sessaoLogada = sessao;
        
        // Bloqueia redimensionamento e centraliza
        this.setResizable(false); 
        this.setLocationRelativeTo(null);
        
        // Desativa o botão desta página no menu lateral
        jGerenciarAparelhos.setEnabled(false); 
        
        carregarComboBoxes();
        carregarTabela();
        configurarSelecaoTabela();
    }

    // Selecionar um aparelho carrega os valores REAIS do banco para edição.
    private void configurarSelecaoTabela() {
        tbAparelhos.setRowSelectionAllowed(true);
        tbAparelhos.setColumnSelectionAllowed(false);
        tbAparelhos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tbAparelhos.getSelectionModel().addListSelectionListener(evento -> {
            if (!evento.getValueIsAdjusting()) {
                preencherCamposSelecionados();
            }
        });
        // Permite recarregar os campos ao clicar novamente na mesma linha.
        tbAparelhos.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evento) {
                int linha = tbAparelhos.rowAtPoint(evento.getPoint());
                if (linha >= 0) {
                    tbAparelhos.setRowSelectionInterval(linha, linha);
                    preencherCamposSelecionados();
                }
            }
        });
    }

    private void preencherCamposSelecionados() {
        int linha = tbAparelhos.getSelectedRow();
        if (linha < 0) return;

        // A tabela contém nomes de tipo/modelo, mas as ComboBoxes precisam dos IDs.
        Object id = tbAparelhos.getValueAt(linha, 0);
        if (id == null) return;
        try {
            br.com.dao.AparelhoDAO dao = new br.com.dao.AparelhoDAO();
            Aparelho aparelho = dao.buscarPorIdApa(id.toString());
            if (aparelho == null) {
                JOptionPane.showMessageDialog(this, "Aparelho não encontrado no banco de dados.");
                return;
            }
            jImei.setText(aparelho.getImeiApa());
            selecionarTipoAparelho(aparelho.getIdTip());
            selecionarModelo(aparelho.getIdMod());
        } catch (java.sql.SQLException erro) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar aparelho: " + erro.getMessage());
        }
    }

    private void selecionarTipoAparelho(String idTipo) {
        jTipoApa.setSelectedIndex(-1);
        for (int i = 0; i < jTipoApa.getItemCount(); i++) {
            Object item = jTipoApa.getItemAt(i);
            if (item instanceof Tipo_Aparelho &&
                    java.util.Objects.equals(((Tipo_Aparelho) item).getIdTip(), idTipo)) {
                jTipoApa.setSelectedIndex(i);
                return;
            }
        }
        System.err.println("Tipo do aparelho não encontrado na lista: " + idTipo);
    }

    private void selecionarModelo(String idModelo) {
        jModeloApa.setSelectedIndex(-1);
        for (int i = 0; i < jModeloApa.getItemCount(); i++) {
            Object item = jModeloApa.getItemAt(i);
            if (item instanceof Modelo &&
                    java.util.Objects.equals(((Modelo) item).getIdMod(), idModelo)) {
                jModeloApa.setSelectedIndex(i);
                return;
            }
        }
        System.err.println("Modelo do aparelho não encontrado na lista: " + idModelo);
    }

    private void carregarComboBoxes() {
        jTipoApa.removeAllItems();
        jModeloApa.removeAllItems();

        try {
            // Carrega Tipos de Aparelho do Banco de Dados
            TipoAparelhoController tipoControl = new TipoAparelhoController();
            List<Tipo_Aparelho> tipos = tipoControl.listar();
            if (tipos != null) {
                for (Tipo_Aparelho t : tipos) {
                    jTipoApa.addItem(t);
                }
            } else {
                jTipoApa.addItem("Sem tipos cadastrados");
            }

            // Carrega Modelos do Banco de Dados
            ModeloController modControl = new ModeloController();
            List<Modelo> modelos = modControl.listar();
            if (modelos != null) {
                for (Modelo m : modelos) {
                    jModeloApa.addItem(m);
                }
            } else {
                jModeloApa.addItem("Sem modelos cadastrados");
            }
        } catch (Exception e) {
            System.out.println("Erro ao carregar comboboxes: " + e.getMessage());
        }
    }
   private void carregarTabela() {
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) tbAparelhos.getModel();
        model.setRowCount(0); 

        try {
            AparelhoController controle = new AparelhoController();
            java.util.List<Aparelho> lista = controle.listar(this.sessaoLogada);

            if (lista != null) {
                for (br.com.model.Aparelho a : lista) {
                    model.addRow(new Object[]{
                        a.getIdApa(),      // 1ª Coluna: ID
                        a.getImeiApa(),    // 2ª Coluna: IMEI
                        a.getIdTip(),      // 3ª Coluna: Tipo (Agora exibe o NOME graças ao DAO!)
                        a.getIdMod()       // 4ª Coluna: Modelo (Agora exibe o NOME graças ao DAO!)
                    });
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao carregar tabela de aparelhos: " + e.getMessage());
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
        jTable1 = new javax.swing.JTable();
        jImei = new javax.swing.JTextField();
        jTipoApa = new javax.swing.JComboBox<>();
        jModeloApa = new javax.swing.JComboBox<>();
        jPanel1 = new javax.swing.JPanel();
        jGerenciarAparelhos = new javax.swing.JButton();
        jGerenciarEmprestimos = new javax.swing.JButton();
        jGerenciarColaboradores = new javax.swing.JButton();
        jGerenciarChips = new javax.swing.JButton();
        jDashboard = new javax.swing.JButton();
        jSair = new javax.swing.JButton();
        jNomeUsu = new javax.swing.JLabel();
        jCadastrar = new javax.swing.JButton();
        jExcluir = new javax.swing.JButton();
        jEditar = new javax.swing.JButton();
        jLimpar = new javax.swing.JButton();
        jTbAparelhos = new javax.swing.JScrollPane();
        tbAparelhos = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTipoApa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jModeloApa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        jGerenciarAparelhos.setBackground(new java.awt.Color(102, 102, 102));
        jGerenciarAparelhos.setForeground(new java.awt.Color(255, 255, 255));
        jGerenciarAparelhos.setText("Gerenciar Aparelhos");
        jGerenciarAparelhos.setBorderPainted(false);
        jGerenciarAparelhos.setContentAreaFilled(false);
        jGerenciarAparelhos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGerenciarAparelhosActionPerformed(evt);
            }
        });

        jGerenciarEmprestimos.setBackground(new java.awt.Color(102, 102, 102));
        jGerenciarEmprestimos.setForeground(new java.awt.Color(255, 255, 255));
        jGerenciarEmprestimos.setText("Gerenciar Emprestimos");
        jGerenciarEmprestimos.setBorderPainted(false);
        jGerenciarEmprestimos.setContentAreaFilled(false);
        jGerenciarEmprestimos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGerenciarEmprestimosActionPerformed(evt);
            }
        });

        jGerenciarColaboradores.setBackground(new java.awt.Color(102, 102, 102));
        jGerenciarColaboradores.setForeground(new java.awt.Color(255, 255, 255));
        jGerenciarColaboradores.setText("Gerenciar Colaboradores");
        jGerenciarColaboradores.setBorderPainted(false);
        jGerenciarColaboradores.setContentAreaFilled(false);
        jGerenciarColaboradores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGerenciarColaboradoresActionPerformed(evt);
            }
        });

        jGerenciarChips.setBackground(new java.awt.Color(102, 102, 102));
        jGerenciarChips.setForeground(new java.awt.Color(255, 255, 255));
        jGerenciarChips.setText("Gerenciar Chips");
        jGerenciarChips.setBorderPainted(false);
        jGerenciarChips.setContentAreaFilled(false);
        jGerenciarChips.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGerenciarChipsActionPerformed(evt);
            }
        });

        jDashboard.setBackground(new java.awt.Color(102, 102, 102));
        jDashboard.setForeground(new java.awt.Color(255, 255, 255));
        jDashboard.setText("Dashboard");
        jDashboard.setBorderPainted(false);
        jDashboard.setContentAreaFilled(false);
        jDashboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jDashboardActionPerformed(evt);
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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jGerenciarEmprestimos)
                    .addComponent(jGerenciarChips)
                    .addComponent(jDashboard)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSair, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jNomeUsu)))
                    .addComponent(jGerenciarColaboradores)
                    .addComponent(jGerenciarAparelhos))
                .addGap(12, 12, 12))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jNomeUsu)
                .addGap(35, 35, 35)
                .addComponent(jDashboard)
                .addGap(18, 18, 18)
                .addComponent(jGerenciarChips)
                .addGap(18, 18, 18)
                .addComponent(jGerenciarAparelhos)
                .addGap(18, 18, 18)
                .addComponent(jGerenciarEmprestimos)
                .addGap(18, 18, 18)
                .addComponent(jGerenciarColaboradores)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSair, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

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

        jEditar.setText("Editar");
        jEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jEditarActionPerformed(evt);
            }
        });

        jLimpar.setText("Limpar Campos");
        jLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jLimparActionPerformed(evt);
            }
        });

        tbAparelhos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "IMEI", "Tipo", "Modelo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTbAparelhos.setViewportView(tbAparelhos);

        jLabel1.setText("IMEI:");

        jLabel2.setText("Aparelho:");

        jLabel3.setText("Modelo:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jImei, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTipoApa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(101, 101, 101)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jModeloApa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jTbAparelhos, javax.swing.GroupLayout.PREFERRED_SIZE, 890, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLimpar)
                    .addComponent(jCadastrar)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jExcluir, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jEditar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jModeloApa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jImei, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1)
                        .addComponent(jLabel2)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jCadastrar))
                    .addComponent(jTipoApa, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTbAparelhos, javax.swing.GroupLayout.DEFAULT_SIZE, 518, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLimpar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jExcluir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jEditar)
                        .addGap(5, 5, 5)))
                .addContainerGap())
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jGerenciarAparelhosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGerenciarAparelhosActionPerformed
       
    }//GEN-LAST:event_jGerenciarAparelhosActionPerformed

    private void jGerenciarEmprestimosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGerenciarEmprestimosActionPerformed
        // TODO add your handling code here:
        TelaEmprestimo tela = new TelaEmprestimo(this.sessaoLogada);
        tela.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jGerenciarEmprestimosActionPerformed

    private void jGerenciarColaboradoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGerenciarColaboradoresActionPerformed
        // TODO add your handling code here:
        TelaColaboradores tela = new TelaColaboradores(this.sessaoLogada);
        tela.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jGerenciarColaboradoresActionPerformed

    private void jGerenciarChipsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGerenciarChipsActionPerformed
        // TODO add your handling code here:
        TelaChips tela = new TelaChips(this.sessaoLogada);
        tela.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jGerenciarChipsActionPerformed

    private void jDashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jDashboardActionPerformed
        // TODO add your handling code here:
        TelaDashboard dashboard = new TelaDashboard(this.sessaoLogada);
        dashboard.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jDashboardActionPerformed

    private void jCadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCadastrarActionPerformed
        // TODO add your handling code here:
        try {
            String imei = jImei.getText();
            
            
            if (jTipoApa.getSelectedItem() == null || jModeloApa.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Selecione um Tipo e um Modelo válidos!");
                return;
            }
            
            // Cast do Object para a Classe Modelo/Tipo original
            Tipo_Aparelho tipo = (Tipo_Aparelho) jTipoApa.getSelectedItem();
            Modelo mod = (Modelo) jModeloApa.getSelectedItem();
            
            AparelhoController controle = new AparelhoController();
            
            if (controle.cadastrar(this.sessaoLogada, imei, tipo.getIdTip(), mod.getIdMod())) {
                JOptionPane.showMessageDialog(this, "Aparelho cadastrado com sucesso!");
                jLimparActionPerformed(evt); // Limpa os campos após o sucesso
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar. Verifique se o IMEI é válido e se você tem permissão.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }//GEN-LAST:event_jCadastrarActionPerformed

    private void jExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jExcluirActionPerformed
        // TODO add your handling code here:
        int linhaSelecionada = tbAparelhos.getSelectedRow();
        if (linhaSelecionada == -1) {
            javax.swing.JOptionPane.showMessageDialog(this, "Selecione um aparelho na tabela para excluir!");
            return;
        }

        String idApa = tbAparelhos.getValueAt(linhaSelecionada, 0).toString();
        int confirm = javax.swing.JOptionPane.showConfirmDialog(this, "Deseja realmente excluir este aparelho?", "Confirmação", javax.swing.JOptionPane.YES_NO_OPTION);

        if (confirm == javax.swing.JOptionPane.YES_OPTION) {
            try {
                br.com.dao.AparelhoDAO dao = new br.com.dao.AparelhoDAO();
                if (dao.excluir(idApa)) {
                    javax.swing.JOptionPane.showMessageDialog(this, "Aparelho excluído com sucesso!");
                    carregarTabela();
                    jLimparActionPerformed(evt);
                } else {
                    javax.swing.JOptionPane.showMessageDialog(this, "Erro ao excluir aparelho.");
                }
            } catch (Exception e) {
                javax.swing.JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
            }
        }
    }//GEN-LAST:event_jExcluirActionPerformed

    private void jLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jLimparActionPerformed
        // TODO add your handling code here:
        jImei.setText("");
        if(jTipoApa.getItemCount() > 0) jTipoApa.setSelectedIndex(0);
        if(jModeloApa.getItemCount() > 0) jModeloApa.setSelectedIndex(0);
        tbAparelhos.clearSelection();
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
            br.com.visao.TelaLogin telaLogin = new br.com.visao.TelaLogin();
            telaLogin.setVisible(true);
        }
    }//GEN-LAST:event_jSairActionPerformed

    private void jEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jEditarActionPerformed
        int linha = tbAparelhos.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um aparelho na tabela para editar!");
            return;
        }

        String novoImei = jImei.getText().trim();
        Object tipoSelecionado = jTipoApa.getSelectedItem();
        Object modeloSelecionado = jModeloApa.getSelectedItem();
        if (novoImei.isEmpty() || !(tipoSelecionado instanceof Tipo_Aparelho)
                || !(modeloSelecionado instanceof Modelo)) {
            JOptionPane.showMessageDialog(this, "Preencha o IMEI, o Tipo e o Modelo corretamente.");
            return;
        }

        String idApa = tbAparelhos.getValueAt(linha, 0).toString();
        String idTipo = ((Tipo_Aparelho) tipoSelecionado).getIdTip();
        String idModelo = ((Modelo) modeloSelecionado).getIdMod();
        AparelhoController controle = new AparelhoController();
        if (controle.editar(this.sessaoLogada, idApa, novoImei, idTipo, idModelo)) {
            JOptionPane.showMessageDialog(this, "Aparelho atualizado com sucesso!");
            carregarTabela();
            jLimparActionPerformed(evt);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Não foi possível atualizar o aparelho. Confira os dados, a permissão e o log do NetBeans.");
        }
    }//GEN-LAST:event_jEditarActionPerformed

    /**
     * @param args the command line arguments
     */
  

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jCadastrar;
    private javax.swing.JButton jDashboard;
    private javax.swing.JButton jEditar;
    private javax.swing.JButton jExcluir;
    private javax.swing.JButton jGerenciarAparelhos;
    private javax.swing.JButton jGerenciarChips;
    private javax.swing.JButton jGerenciarColaboradores;
    private javax.swing.JButton jGerenciarEmprestimos;
    private javax.swing.JTextField jImei;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JButton jLimpar;
    private javax.swing.JComboBox<Object> jModeloApa;
    private javax.swing.JLabel jNomeUsu;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton jSair;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JScrollPane jTbAparelhos;
    private javax.swing.JComboBox<Object> jTipoApa;
    private javax.swing.JTable tbAparelhos;
    // End of variables declaration//GEN-END:variables
}
