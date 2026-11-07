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
import br.com.model.Setor;
import br.com.model.Usuario;
import java.sql.SQLException;
import java.util.List;
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
    
    // Compatibilidade com chamadas antigas: o perfil padrao continua COLABORADOR.
    public boolean cadastrar(String nome, String email, String telefone, String senha, String idSet) {
        return cadastrar(nome, email, telefone, senha, "COLABORADOR", idSet);
    }

    // Cadastro com perfil selecionado na tela (COLABORADOR ou TECNICO).
    public boolean cadastrar(String nome, String email, String telefone, String senha, String perfilUsu, String idSet) {
        try {
            String perfil = perfilUsu == null ? "" : perfilUsu.trim().toUpperCase();
            if (!"COLABORADOR".equals(perfil) && !"TECNICO".equals(perfil)) {
                throw new IllegalArgumentException("Selecione um perfil válido!");
            }

            Usuario usu = new Usuario(nome, email, telefone, senha, idSet);
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
            
            usuarioDao.inserir(usu.getIdUsu(), usu.getNomeUsu(), usu.getEmailUsu(), usu.getTelefoneUsu(), usu.getSenhaUsu(), perfil, usu.getIdSet());
            return true;
        } catch (IllegalArgumentException | SQLException e) {
            System.out.println("Erro ao cadastrar usuário: " + e.getMessage());
            return false;

        } catch (Exception e) {
            System.out.println("Erro ao cadastrar usuário: " + e.getMessage());
            return false;
        }
    }
    
    // Edita os dados cadastrais de um usuário ativo.
    public boolean editar(Sessao sessao, Usuario usu) {
        try {
            if (!SessaoUtil.sessaoValida(sessao) || usu == null) {
                throw new IllegalArgumentException("Sessão inválida ou usuário não informado!");
            }

            boolean tecnico = SessaoUtil.perfilValido(sessao, "TECNICO");
            boolean editandoProprioCadastro = usu.getIdUsu() != null
                    && usu.getIdUsu().equals(sessao.getUsuario().getIdUsu());

            if (!editandoProprioCadastro && !tecnico) {
                throw new IllegalArgumentException("Sem permissão para editar outro colaborador!");
            }

            if (ValidacaoUtil.campoVazio(new String[]{
                usu.getNomeUsu(), usu.getEmailUsu(), usu.getTelefoneUsu(),
                usu.getIdSet(), usu.getIdUsu()
            })) {
                throw new IllegalArgumentException("Preencha todos os campos obrigatórios!");
            }

            if (!ValidacaoUtil.tamanhoValido(usu.getNomeUsu(), 60)) {
                throw new IllegalArgumentException("O nome deve ter no máximo 60 caracteres!");
            }
            if (!ValidacaoUtil.tamanhoValido(usu.getEmailUsu(), 60)) {
                throw new IllegalArgumentException("O e-mail deve ter no máximo 60 caracteres!");
            }
            if (!ValidacaoUtil.tamanhoValido(usu.getTelefoneUsu(), 15)) {
                throw new IllegalArgumentException("O telefone deve ter no máximo 15 caracteres!");
            }
            if (setorDao.buscarPorId(usu.getIdSet()) == null) {
                throw new IllegalArgumentException("O setor informado não foi encontrado!");
            }

            Usuario usuarioExistente = usuarioDao.buscarPorIdUsu(usu.getIdUsu());
            if (usuarioExistente == null) {
                throw new IllegalArgumentException("O usuário informado não foi encontrado!");
            }
            if (!"ATIVO".equalsIgnoreCase(usuarioExistente.getStatusUsu())) {
                throw new IllegalArgumentException("Não é permitido editar um usuário inativo!");
            }

            // Apenas técnicos podem alterar o perfil. Isso impede que um usuário
            // comum eleve o próprio acesso ao editar seus dados.
            String perfilFinal = tecnico ? usu.getPerfilUsu() : usuarioExistente.getPerfilUsu();
            if (perfilFinal == null
                    || !("COLABORADOR".equalsIgnoreCase(perfilFinal)
                    || "TECNICO".equalsIgnoreCase(perfilFinal))) {
                throw new IllegalArgumentException("Selecione um perfil válido!");
            }

            return usuarioDao.atualizar(
                    usu.getIdUsu(), usu.getNomeUsu(), usu.getEmailUsu(),
                    usu.getTelefoneUsu(), perfilFinal.toUpperCase(), usu.getIdSet());

        } catch (IllegalArgumentException | SQLException e) {
            System.out.println("Erro ao editar usuário: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println("Erro inesperado ao editar usuário: " + e.getMessage());
            return false;
        }
    }
    public java.util.List<Usuario> listar(Sessao sessao) {
        try {
            // Removida a trava temporariamente para forçar a listagem
            return usuarioDao.listar(); 
        } catch (Exception e) {
            System.out.println("Erro ao listar os utilizadores: " + e.getMessage());
            return null;
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
            return usuarioDao.desativar(idUsu);
        } catch (IllegalArgumentException | SQLException e) {
            System.out.println("Erro ao desativar Usuário: " + e.getMessage());
            return false;
        }
    }
    
    public java.util.List<Object[]> listarParaTabela() {
        java.util.List<Object[]> dados = new java.util.ArrayList<>();
        try {
            Sessao sessao = null;
            java.util.List<Usuario> usuarios = listar(sessao);
            if (usuarios != null) {
                for (Usuario u : usuarios) {
                    dados.add(new Object[]{
                        u.getIdUsu(), u.getNomeUsu(), u.getEmailUsu(),
                        u.getPerfilUsu(), u.getNomeSet(), u.getStatusUsu()
                    });
                }
            }
        } catch (Exception e) {}
        return dados;
    }

    public java.util.List<String> listarUsuariosFormatados() {
        java.util.List<String> lista = new java.util.ArrayList<>();
        try {
            Sessao sessao = null;
            java.util.List<Usuario> usuarios = listar(sessao);
            if (usuarios != null) for (Usuario u : usuarios) lista.add(u.getIdUsu() + " - " + u.getNomeUsu());
        } catch (Exception e) {}
        return lista;
    }

    public boolean cadastrar(Usuario usu) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public java.util.List<br.com.model.Setor> listarSetores() {
        try {
            return setorDao.listar(); 
        } catch (Exception e) {
            System.out.println("Erro ao listar os Setores: " + e.getMessage());
            return null;
        }
    }
    public String buscarNomePorId(String idUsu) {
        try {
            // Usa o método que já existe no seu UsuarioDAO
            br.com.model.Usuario usu = usuarioDao.buscarPorIdUsu(idUsu);
            if (usu != null && usu.getNomeUsu() != null) {
                return usu.getNomeUsu();
            }
        } catch (Exception e) {
            System.out.println("Erro ao traduzir ID para Nome: " + e.getMessage());
        }
        return "ID: " + idUsu.substring(0, 8) + "..."; // Devolve um ID encurtado se falhar
    }
}
