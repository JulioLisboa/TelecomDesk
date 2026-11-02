/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.controle;

import br.com.Util.SessaoUtil;
import br.com.Util.ValidacaoUtil;
import br.com.dao.AparelhoDAO;
import br.com.dao.ChipDAO;
import br.com.dao.EmprestimoDAO;
import br.com.dao.UsuarioDAO;
import br.com.model.Emprestimo;
import br.com.model.Sessao;
import java.sql.SQLException;
import java.util.UUID;

/**
 *
 * @author dev
 */
public class EmprestimoController {
    private final EmprestimoDAO emprestimoDAO;
    private final ChipDAO chipDAO;
    private final AparelhoDAO aparelhoDAO;
    private final UsuarioDAO usuarioDAO;

    public EmprestimoController() {
        this.emprestimoDAO = new EmprestimoDAO();
        this.chipDAO = new ChipDAO();
        this.aparelhoDAO = new AparelhoDAO();
        this.usuarioDAO = new UsuarioDAO();
    }
    
    public boolean regitsrarEmprestimo(Sessao sessao, String idApa, String idChip, String idUsu){
       // String idEmp, String idApa, String idUsu, String idChip
       try{
           if (!SessaoUtil.sessaoValida(sessao)) {
                throw new IllegalArgumentException("Sessão inválida!");
            }

            if (!SessaoUtil.perfilValido(sessao, "TECNICO")) {
                throw new IllegalArgumentException("Sem permissão!");
            }
            if (ValidacaoUtil.campoVazio(new String[]{idApa, idChip, idUsu})) {
                throw new IllegalArgumentException("Preencha todos os campos obrigatórios!");
            }

            if (!ValidacaoUtil.tamanhoValido(idApa, 36)) {
                throw new IllegalArgumentException("O idApa deve ter no máximo 36 caracteres!");
            }
            if (!ValidacaoUtil.tamanhoValido(idChip, 36)) {
                throw new IllegalArgumentException("O idChip deve ter no máximo 36 caracteres!");
            }
            if (!ValidacaoUtil.tamanhoValido(idUsu, 36)) {
                throw new IllegalArgumentException("O idUsu deve ter no máximo 36 caracteres!");
            }
            if(!"DISPONIVEL".equals(chipDAO.buscarPorIdChip(idChip).getStatusChip())){
                throw new IllegalArgumentException("este chip não esta dicponivel!");
            }
            if(!"DISPONIVEL".equals(aparelhoDAO.buscarPorIdApa(idApa).getStatusApa())){
                throw new IllegalArgumentException("este aparelho não esta dicponivel!");
            }
            if(!"ATIVO".equals(usuarioDAO.buscarPorIdUsu(idUsu).getStatusUsu())){
                throw new IllegalArgumentException("este chip não esta dicponivel!");
            }
            emprestimoDAO.inserir(UUID.randomUUID().toString(), idApa, idUsu, idChip);
            aparelhoDAO.atualizarStatus(idApa, "EMPRESTADO");
            chipDAO.atualizarStatus(idChip, "EMPRESTADO");
            return true;
       } catch (IllegalArgumentException | SQLException e){
            System.out.println("Erro ao cadastrar um emprestimo: " + e.getMessage());
            return false;
       }
    }
}
