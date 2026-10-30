/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.model;

import br.com.banco.ConexaoBanco;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
/**
 *
 * @author Gabri
 */
public class OperadoraDAO {
    // Cadastrar uma operadora
    public void inserir(String idOpe, String nomeOpe) throws SQLException {

        String sql = "INSERT INTO Operadora (idOpe, nomeOpe) VALUES (?, ?)";

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, idOpe);
            comando.setString(2, nomeOpe);

            comando.executeUpdate();
        }
    }

    // Buscar uma operadora pelo ID
    public String buscar(String idOpe) throws SQLException {

        String sql = "SELECT nomeOpe FROM Operadora "
                + "WHERE idOpe = ? AND deletado_em IS NULL";

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, idOpe);

            try (ResultSet resultado = comando.executeQuery()) {

                if (resultado.next()) {
                    return resultado.getString("nomeOpe");
                }

                return null;
            }
        }
    }

    // Atualizar o nome de uma operadora
    public boolean atualizar(String idOpe, String nomeOpe) throws SQLException {

        String sql = "UPDATE Operadora SET nomeOpe = ? "
                + "WHERE idOpe = ? AND deletado_em IS NULL";

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, nomeOpe);
            comando.setString(2, idOpe);

            return comando.executeUpdate() > 0;
        }
    }

    // Excluir uma operadora logicamente
    public boolean excluir(String idOpe) throws SQLException {

        String sql = "UPDATE Operadora SET deletado_em = NOW() "
                + "WHERE idOpe = ? AND deletado_em IS NULL";

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, idOpe);

            return comando.executeUpdate() > 0;
        }
    }
}
