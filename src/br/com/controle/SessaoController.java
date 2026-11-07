/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.controle;

import br.com.Util.ValidacaoUtil;
import br.com.dao.UsuarioDAO;
import br.com.model.Sessao;
import br.com.model.Usuario;
import br.com.Util.SenhaUtil;
import java.sql.SQLException;


/**
 *
 * @author dev
 */
public class SessaoController {
    private final UsuarioDAO usuarioDao;

    public SessaoController() {
        this.usuarioDao = new UsuarioDAO();
    }
    
    
    public Sessao login(String emailUsu, String senhaUsu){
        try {
            if (ValidacaoUtil.campoVazio(new String[]{emailUsu, senhaUsu})) {
                throw new IllegalArgumentException("Informe todos os campo obrigatorios!");
            }   
            Usuario usu = usuarioDao.buscarPorEmail(emailUsu);
            if(usu != null){
                if(SenhaUtil.verificar(senhaUsu, usu.getSenhaUsu())){
                    return new Sessao(usu);
                }
            }
            return null;
        } catch (IllegalArgumentException | SQLException e) {
            System.out.println("Erro ao realizar o login do Usuário: " + e.getMessage());
            return null;
        } catch (Exception e){
            System.out.println("Erro ao realizar o login do Usuário: " + e.getMessage());
            return null;
        }
    }
    
}
