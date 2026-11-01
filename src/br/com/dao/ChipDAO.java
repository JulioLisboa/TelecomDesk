/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.dao;

import br.com.banco.ConexaoBanco;
import br.com.model.Chip;
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

    // SELECT - Buscar chip pelo numeroChip
    public Chip buscarPorNumeroChip(String numeroChip) throws SQLException {

        String sql = "SELECT * FROM Chip "
                + "WHERE numeroChip = ?";

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, numeroChip);

            try (ResultSet resultado = comando.executeQuery()) {

                if (resultado.next()) {
                    String idChip = resultado.getString("idChip");
                    String iccidChip = resultado.getString("iccidChip");
                    numeroChip = resultado.getString("numeroChip");
                    String statusChip = resultado.getString("statusChip");
                    String deletado_em = resultado.getString("deletado_em");
                    String idOpe = resultado.getString("idOpe");
                    return new Chip(idChip, iccidChip, numeroChip, statusChip, deletado_em, idOpe);
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
    
    public List<Chip> listar() throws SQLException {

        String sql = "SELECT * FROM Chip WHERE deletado_em IS NULL";

        List<Chip> chips = new ArrayList<>();

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql);
             ResultSet resultado = comando.executeQuery()) {

            while (resultado.next()) {
                String idChip = resultado.getString("idChip");
                String iccidChip = resultado.getString("iccidChip");
                String numeroChip = resultado.getString("numeroChip");
                String statusChip = resultado.getString("statusChip");
                String deletado_em = resultado.getString("deletado_em");
                String idOpe = resultado.getString("idOpe");

                Chip chip = new Chip(idChip, iccidChip, numeroChip, statusChip, deletado_em, idOpe);
                chips.add(chip);
            }
        }
        return chips;
    }
}
