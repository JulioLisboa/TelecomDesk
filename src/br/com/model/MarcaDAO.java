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
public class MarcaDAO {
     // Cadastrar uma marca
    public void inserir(String idMar, String nomeMar) throws SQLException {

        String sql = "INSERT INTO Marca (idMar, nomeMar) VALUES (?, ?)";

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, idMar);
            comando.setString(2, nomeMar);

            comando.executeUpdate();
        }
    }

    // Buscar uma marca pelo ID
    public String buscar(String idMar) throws SQLException {

        String sql = "SELECT nomeMar FROM Marca "
                + "WHERE idMar = ? AND deletado_em IS NULL";

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, idMar);

            try (ResultSet resultado = comando.executeQuery()) {

                if (resultado.next()) {
                    return resultado.getString("nomeMar");
                }

                return null;
            }
        }
    }

    // Atualizar o nome de uma marca
    public boolean atualizar(String idMar, String nomeMar) throws SQLException {

        String sql = "UPDATE Marca SET nomeMar = ? "
                + "WHERE idMar = ? AND deletado_em IS NULL";

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, nomeMar);
            comando.setString(2, idMar);

            return comando.executeUpdate() > 0;
        }
    }

    // Excluir uma marca logicamente
    public boolean excluir(String idMar) throws SQLException {

        String sql = "UPDATE Marca SET deletado_em = NOW() "
                + "WHERE idMar = ? AND deletado_em IS NULL";

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, idMar);

            return comando.executeUpdate() > 0;
        }
    }
}