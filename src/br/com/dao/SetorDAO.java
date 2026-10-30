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
public class SetorDAO {
      // Cadastrar um setor
    public void inserir(String idSet, String nomeSet) throws SQLException {

        String sql = "INSERT INTO Setor (idSet, nomeSet) VALUES (?, ?)";

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, idSet);
            comando.setString(2, nomeSet);

            comando.executeUpdate();
        }
    }

    // Buscar um setor pelo ID
    public String buscar(String idSet) throws SQLException {

        String sql = "SELECT nomeSet FROM Setor "
                + "WHERE idSet = ? AND deletado_em IS NULL";

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, idSet);

            try (ResultSet resultado = comando.executeQuery()) {

                if (resultado.next()) {
                    return resultado.getString("nomeSet");
                }

                return null;
            }
        }
    }
    
    // Atualizar o nome de um setor
    public boolean atualizar(String idSet, String nomeSet) throws SQLException {

        String sql = "UPDATE Setor SET nomeSet = ? "
                + "WHERE idSet = ? AND deletado_em IS NULL";

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, nomeSet);
            comando.setString(2, idSet);

            return comando.executeUpdate() > 0;
        }
    }
    
     // Excluir um setor logicamente
    public boolean excluir(String idSet) throws SQLException {

        String sql = "UPDATE Setor SET deletado_em = NOW() "
                + "WHERE idSet = ? AND deletado_em IS NULL";

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, idSet);

            return comando.executeUpdate() > 0;
        }
    }
}
