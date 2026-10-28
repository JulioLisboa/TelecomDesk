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
public class TipoAparelhoDAO {
    public void inserir(String idTip, String nomeTip) throws SQLException {

        String sql = "INSERT INTO Tipo_Aparelho (idTip, nomeTip) VALUES (?, ?)";

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, idTip);
            comando.setString(2, nomeTip);

            comando.executeUpdate();
        }
    }
}
