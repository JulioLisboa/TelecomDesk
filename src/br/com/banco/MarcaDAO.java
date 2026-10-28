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
public class MarcaDAO {
     public void inserir(String idMar, String nomeMar) throws SQLException {

        String sql = "INSERT INTO Marca (idMar, nomeMar) VALUES (?, ?)";

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, idMar);
            comando.setString(2, nomeMar);

            comando.executeUpdate();
        }
    }
}
