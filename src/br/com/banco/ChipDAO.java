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
public class ChipDAO {
    public void inserir(String idChip, String iccidChip,
                        String numeroChip, String idOpe) throws SQLException {

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
}
