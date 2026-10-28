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
public class SetorDAO {
     public void inserir(String idSet, String nomeSet) throws SQLException {

        String sql = "INSERT INTO Setor (idSet, nomeSet) VALUES (?, ?)";

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, idSet);
            comando.setString(2, nomeSet);

            comando.executeUpdate();
        }
    }
}
