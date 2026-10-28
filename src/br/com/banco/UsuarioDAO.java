/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.banco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 *
 * @author Gabri
 */
public class UsuarioDAO {
    public void inserir(String idUsu, String nomeUsu,
                        String emailUsu, String telefoneUsu,
                        String senhaUsu, String perfilUsu,
                        String idSet) throws SQLException {

        String sql = "INSERT INTO Usuario "
                + "(idUsu, nomeUsu, emailUsu, telefoneUsu, "
                + "senhaUsu, perfilUsu, idSet) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, idUsu);
            comando.setString(2, nomeUsu);
            comando.setString(3, emailUsu);
            comando.setString(4, telefoneUsu);
            comando.setString(5, senhaUsu);
            comando.setString(6, perfilUsu);
            comando.setString(7, idSet);

            comando.executeUpdate();
        }
    }   
}
