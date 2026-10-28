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
public class AparelhoDAO {
    public void inserir(String idApa, String imeiApa,
                        String idTip, String idMod) throws SQLException {

        String sql = "INSERT INTO Aparelho "
                + "(idApa, imeiApa, idTip, idMod) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, idApa);
            comando.setString(2, imeiApa);
            comando.setString(3, idTip);
            comando.setString(4, idMod);

            comando.executeUpdate();
        }
    }
}
