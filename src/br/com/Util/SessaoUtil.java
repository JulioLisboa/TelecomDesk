/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.Util;

import br.com.model.Sessao;

/**
 *
 * @author dev
 */
public class SessaoUtil {

    public static boolean sessaoValida(Sessao sessao) {
        return sessao != null
                && sessao.getUsuario() != null
                && !"INATIVO".equals(
                        sessao.getUsuario().getStatusUsu());
    }

    public static boolean perfilValido(Sessao sessao, String perfil) {
        return sessaoValida(sessao)
                && perfil != null
                && perfil.equals(
                        sessao.getUsuario().getPerfilUsu());
    }
}
