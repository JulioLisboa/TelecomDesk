/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.dao;

import br.com.banco.ConexaoBanco;
import br.com.model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
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

    // SELECT - Buscar usuario pelo email
    public Usuario buscarPorEmail(String emailUsu) throws SQLException {

        String sql = "SELECT * FROM Usuario "
                + "WHERE emailUsu = ?";

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, emailUsu);

            try (ResultSet resultado = comando.executeQuery()) {
                if (resultado.next()) {
                    String idUsu = resultado.getString("idUsu");
                    String nomeUsu = resultado.getString("nomeUsu");
                    emailUsu = resultado.getString("emailUsu");
                    String telefoneUsu = resultado.getString("telefoneUsu");
                    String tokenUsu = resultado.getString("tokenUsu");
                    String senhaUsu = resultado.getString("senhaUsu");
                    String statusUsu = resultado.getString("statusUsu");
                    String perfilUsu = resultado.getString("perfilUsu");
                    String idSet = resultado.getString("idSet");

                    return new Usuario(
                        idUsu,
                        nomeUsu,
                        emailUsu,
                        telefoneUsu,
                        tokenUsu,
                        senhaUsu,
                        statusUsu,
                        perfilUsu,
                        idSet
                    );
                }

                return null;
            }
        }
    }
    
    // SELECT - Buscar usuario pelo id
    public String buscarPorId(String idUsu) throws SQLException {

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
    
    // listar
    public List<Usuario> listar() throws SQLException {

        String sql = "SELECT u.idUsu, u.nomeUsu, u.emailUsu, s.idSet, s.nomeSet, u.statusUsu "
                   + "FROM Usuario AS u "
                   + "INNER JOIN Setor AS s ON u.idSet = s.idSet "
                   + "ORDER BY u.statusUsu ASC";

        List<Usuario> usuarios = new ArrayList<>();

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql);
             ResultSet resultado = comando.executeQuery()) {

            while (resultado.next()) {
                String idUsu = resultado.getString("idUsu");
                String nomeUsu = resultado.getString("nomeUsu");
                String emailUsu = resultado.getString("emailUsu");
                String idSet = resultado.getString("idSet");
                String nomeSet = resultado.getString("nomeSet");
                String statusUsu = resultado.getString("statusUsu");

                Usuario usuario = new Usuario(
                    idUsu,
                    nomeUsu,
                    emailUsu,
                    statusUsu,
                    nomeSet,
                    idSet
                );

                usuarios.add(usuario);
            }
        }
        return usuarios;
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
    public boolean desativar(String idUsu) throws SQLException {

        String sql = "UPDATE Usuario SET statusUsu = 'INATIVO' "
                + "WHERE idUsu = ?";

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, idUsu);

            return comando.executeUpdate() > 0;
        }
    }
}
