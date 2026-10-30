/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.dao;

import br.com.banco.ConexaoBanco;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
/**
 *
 * @author Gabri
 */
public class UsuarioDAO {
    // INSERT - Cadastrar usuario
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

    // SELECT - Buscar usuario pelo ID
    public String buscar(String idUsu) throws SQLException {

        String sql = "SELECT nomeUsu FROM Usuario "
                + "WHERE idUsu = ? AND statusUsu = 'ATIVO'";

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, idUsu);

            try (ResultSet resultado = comando.executeQuery()) {

                if (resultado.next()) {
                    return resultado.getString("nomeUsu");
                }

                return null;
            }
        }
    }

    // UPDATE - Atualizar dados do usuario
    public boolean atualizar(String idUsu, String nomeUsu,
                             String emailUsu, String telefoneUsu,
                             String perfilUsu, String idSet)
                             throws SQLException {

        String sql = "UPDATE Usuario SET nomeUsu = ?, "
                + "emailUsu = ?, telefoneUsu = ?, "
                + "perfilUsu = ?, idSet = ? "
                + "WHERE idUsu = ? AND statusUsu = 'ATIVO'";

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, nomeUsu);
            comando.setString(2, emailUsu);
            comando.setString(3, telefoneUsu);
            comando.setString(4, perfilUsu);
            comando.setString(5, idSet);
            comando.setString(6, idUsu);

            return comando.executeUpdate() > 0;
        }
    }

    // DELETE - Desativar usuario
    public boolean excluir(String idUsu) throws SQLException {

        String sql = "UPDATE Usuario SET statusUsu = 'INATIVO' "
                + "WHERE idUsu = ? AND statusUsu = 'ATIVO'";

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, idUsu);

            return comando.executeUpdate() > 0;
        }
    }
}
