/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.controle;

import br.com.Util.SenhaUtil;
import br.com.Util.SessaoUtil;
import br.com.Util.ValidacaoUtil;
import br.com.dao.SetorDAO;
import br.com.dao.UsuarioDAO;
import br.com.model.Sessao;
import br.com.model.Usuario;
import java.sql.SQLException;
import java.util.UUID;


/**
 *
 * @author dev
 */
public class UsuarioController {
    private final UsuarioDAO usuarioDao;
    private final SetorDAO setorDao;

    public UsuarioController() {
        this.usuarioDao = new UsuarioDAO();
        this.setorDao = new SetorDAO();
    }
    
    public Usuario buscar(Sessao sessao,String emailUsu){
        try {
            if(!SessaoUtil.sessaoValida(sessao) || !SessaoUtil.perfilValido(sessao, "TECNICO")){
                throw new IllegalArgumentException("Sem permissão!");
            }
            if (ValidacaoUtil.campoVazio(new String[]{emailUsu})) {
                throw new IllegalArgumentException("Informe o id de um Usuário!");
            }
            return usuarioDao.buscarPorEmail(emailUsu);
        } catch (IllegalArgumentException | SQLException e) {
            System.out.println("Erro ao buscar Usuário: " + e.getMessage());
            return null;
        }
    } 
    
    //precisa de: nome, email, telefone, senha, idSet
    public boolean cadastrar(Usuario usu) {
        try {
            if (ValidacaoUtil.campoVazio(new String[]{
                usu.getNomeUsu(),
                usu.getEmailUsu(),
                usu.getTelefoneUsu(),
                usu.getSenhaUsu(),
                usu.getIdSet()
                })) {
                throw new IllegalArgumentException("Preencha todos os campos obrigatórios!");
            }

            if (!ValidacaoUtil.tamanhoValido(usu.getNomeUsu(), 60)) {
                throw new IllegalArgumentException("O nome deve ter no máximo 60 caracteres!");
            }
            if (!ValidacaoUtil.tamanhoValido(usu.getEmailUsu(), 60)) {
                throw new IllegalArgumentException("O Email deve ter no máximo 60 caracteres!");
            }
            if (!ValidacaoUtil.tamanhoValido(usu.getTelefoneUsu(), 15)) {
                throw new IllegalArgumentException("O Telefone deve ter no máximo 15 caracteres!");
            }
            if(setorDao.buscarPorId(usu.getIdSet()) == null){
                throw new IllegalArgumentException("O Setor informado não foi encontrado!");
            } 

            usu.setIdUsu(UUID.randomUUID().toString());
            usu.setSenhaUsu(SenhaUtil.criptografar(usu.getSenhaUsu()));
            
            usuarioDao.inserir(usu.getIdUsu(), usu.getNomeUsu(), usu.getEmailUsu(), usu.getTelefoneUsu(), usu.getSenhaUsu(), "COLABORADOR", usu.getIdSet());
            return true;
        } catch (IllegalArgumentException | SQLException e) {
            System.out.println("Erro ao cadastrar usuário: " + e.getMessage());
            return false;

        } catch (Exception e) {
            System.out.println("Erro ao cadastrar usuário: " + e.getMessage());
            return false;
        }
    }
    
    //precisa de: nome, email, telefone, idSet, IdUsu
    public boolean editar(Sessao sessao, Usuario usu){
        try {
            if(!SessaoUtil.sessaoValida(sessao)){
                throw new IllegalArgumentException("Sem permissão 1!");
            }
            if(!usu.getIdUsu().equals(sessao.getUsuario().getIdUsu()) && !SessaoUtil.perfilValido(sessao, "TECNICO")){
                throw new IllegalArgumentException("Sem permissão 2!");
            }
            if (ValidacaoUtil.campoVazio(new String[]{
                usu.getNomeUsu(),
                usu.getEmailUsu(),
                usu.getTelefoneUsu(),
                usu.getIdSet(),
                usu.getIdUsu()
                })) {
                throw new IllegalArgumentException("Preencha todos os campos obrigatórios!");
            }

            if (!ValidacaoUtil.tamanhoValido(usu.getNomeUsu(), 60)) {
                throw new IllegalArgumentException("O nome deve ter no máximo 60 caracteres!");
            }
            if (!ValidacaoUtil.tamanhoValido(usu.getEmailUsu(), 60)) {
                throw new IllegalArgumentException("O Email deve ter no máximo 60 caracteres!");
            }
            if (!ValidacaoUtil.tamanhoValido(usu.getTelefoneUsu(), 15)) {
                throw new IllegalArgumentException("O Telefone deve ter no máximo 15 caracteres!");
            }
            if(setorDao.buscarPorId(usu.getIdSet()) == null){
                throw new IllegalArgumentException("O Setor informado não foi encontrado!");
            }
            if(usuarioDao.buscarPorId(usu.getIdUsu()) == null){
                throw new IllegalArgumentException("O usuario informado não foi encontrado!");
            } 
            usuarioDao.atualizar(usu.getIdUsu(), usu.getNomeUsu(), usu.getEmailUsu(), usu.getTelefoneUsu(), "COLABORADOR", usu.getIdSet());
            return true;
        } catch (IllegalArgumentException | SQLException e) {
            System.out.println("Erro ao editar Usuário: " + e.getMessage());
            return false;
        }
    }
    
    public boolean desativar(Sessao sessao, String idUsu){
        try {
            if(!SessaoUtil.sessaoValida(sessao)){
                throw new IllegalArgumentException("Sem permissão!");
            }
            if(!idUsu.equals(sessao.getUsuario().getIdUsu()) && !SessaoUtil.perfilValido(sessao, "TECNICO")){
                throw new IllegalArgumentException("Sem permissão 2!");
            }
            if (ValidacaoUtil.campoVazio(new String[]{idUsu})) {
                throw new IllegalArgumentException("Informe o id de um Usuário!");
            }
            usuarioDao.desativar(idUsu);
            return true;
        } catch (IllegalArgumentException | SQLException e) {
            System.out.println("Erro ao desativar Usuário: " + e.getMessage());
            return false;
        }
    }
}
