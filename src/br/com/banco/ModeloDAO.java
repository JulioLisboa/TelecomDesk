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
public class ModeloDAO {
    public void inserir(String idMod, String nomeMod, String idMar) throws SQLException {

        String sql = "INSERT INTO Modelo (idMod, nomeMod, idMar) VALUES (?, ?, ?)";

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, idMod);
            comando.setString(2, nomeMod);
            comando.setString(3, idMar);

            comando.executeUpdate();
        }
    }
}
