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
public class ChipDAO {
    // INSERT - Cadastrar chip
    public void inserir(String idChip, String iccidChip,
                        String numeroChip, String idOpe)
                        throws SQLException {

        String sql = "INSERT INTO Chip "
                + "(idChip, iccidChip, numeroChip, idOpe) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, idChip);
            comando.setString(2, iccidChip);
            comando.setString(3, numeroChip);
            comando.setString(4, idOpe);

            comando.executeUpdate();
        }
    }

    // SELECT - Buscar chip pelo ID
    public String buscar(String idChip) throws SQLException {

        String sql = "SELECT numeroChip FROM Chip "
                + "WHERE idChip = ? AND deletado_em IS NULL";

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, idChip);

            try (ResultSet resultado = comando.executeQuery()) {

                if (resultado.next()) {
                    return resultado.getString("numeroChip");
                }

                return null;
            }
        }
    }

    // UPDATE - Atualizar dados do chip
    public boolean atualizar(String idChip, String iccidChip,
                             String numeroChip, String idOpe)
                             throws SQLException {

        String sql = "UPDATE Chip SET iccidChip = ?, "
                + "numeroChip = ?, idOpe = ? "
                + "WHERE idChip = ? AND deletado_em IS NULL";

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, iccidChip);
            comando.setString(2, numeroChip);
            comando.setString(3, idOpe);
            comando.setString(4, idChip);

            return comando.executeUpdate() > 0;
        }
    }

    // DELETE - Exclusao logica
    public boolean excluir(String idChip) throws SQLException {

        String sql = "UPDATE Chip SET deletado_em = NOW() "
                + "WHERE idChip = ? AND deletado_em IS NULL";

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, idChip);

            return comando.executeUpdate() > 0;
        }
    }
}
