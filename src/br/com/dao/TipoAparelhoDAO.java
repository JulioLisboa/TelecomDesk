/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.dao;

import br.com.banco.ConexaoBanco;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author Gabri
 */
public class TipoAparelhoDAO {
     // Cadastrar um tipo de aparelho
    public void inserir(String idTip, String nomeTip) throws SQLException {

        String sql = "INSERT INTO Tipo_Aparelho (idTip, nomeTip) VALUES (?, ?)";

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, idTip);
            comando.setString(2, nomeTip);

            comando.executeUpdate();
        }
    }

    // Buscar um tipo de aparelho pelo ID
    public String buscar(String idTip) throws SQLException {

        String sql = "SELECT nomeTip FROM Tipo_Aparelho "
                + "WHERE idTip = ? AND deletado_em IS NULL";

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, idTip);

            try (ResultSet resultado = comando.executeQuery()) {

                if (resultado.next()) {
                    return resultado.getString("nomeTip");
                }

                return null;
            }
        }
    }

    // Atualizar o nome do tipo de aparelho
    public boolean atualizar(String idTip, String nomeTip) throws SQLException {

        String sql = "UPDATE Tipo_Aparelho SET nomeTip = ? "
                + "WHERE idTip = ? AND deletado_em IS NULL";

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, nomeTip);
            comando.setString(2, idTip);

            return comando.executeUpdate() > 0;
        }
    }

    // Excluir um tipo de aparelho logicamente
    public boolean excluir(String idTip) throws SQLException {

        String sql = "UPDATE Tipo_Aparelho SET deletado_em = NOW() "
                + "WHERE idTip = ? AND deletado_em IS NULL";

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, idTip);

            return comando.executeUpdate() > 0;
        }
    }
}
