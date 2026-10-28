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
public class OperadoraDAO {
    public void inserir(String idOpe, String nomeOpe) throws SQLException {

        String sql = "INSERT INTO Operadora (idOpe, nomeOpe) VALUES (?, ?)";

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, idOpe);
            comando.setString(2, nomeOpe);

            comando.executeUpdate();
        }
    }
}
