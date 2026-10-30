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
public class ModeloDAO {
    // INSERT - Cadastrar modelo
    public void inserir(String idMod, String nomeMod,
                        String idMar) throws SQLException {

        String sql = "INSERT INTO Modelo "
                + "(idMod, nomeMod, idMar) VALUES (?, ?, ?)";

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, idMod);
            comando.setString(2, nomeMod);
            comando.setString(3, idMar);

            comando.executeUpdate();
        }
    }

    // SELECT - Buscar modelo
    public String buscar(String idMod) throws SQLException {

        String sql = "SELECT nomeMod FROM Modelo "
                + "WHERE idMod = ? AND deletado_em IS NULL";

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, idMod);

            try (ResultSet resultado = comando.executeQuery()) {

                if (resultado.next()) {
                    return resultado.getString("nomeMod");
                }

                return null;
            }
        }
    }

    // UPDATE - Atualizar modelo e marca
    public boolean atualizar(String idMod, String nomeMod,
                             String idMar) throws SQLException {

        String sql = "UPDATE Modelo SET nomeMod = ?, idMar = ? "
                + "WHERE idMod = ? AND deletado_em IS NULL";

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, nomeMod);
            comando.setString(2, idMar);
            comando.setString(3, idMod);

            return comando.executeUpdate() > 0;
        }
    }

    // DELETE - Exclusao logica
    public boolean excluir(String idMod) throws SQLException {

        String sql = "UPDATE Modelo SET deletado_em = NOW() "
                + "WHERE idMod = ? AND deletado_em IS NULL";

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, idMod);

            return comando.executeUpdate() > 0;
        }
    }
}
