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
public class AparelhoDAO {
    // INSERT - Cadastrar aparelho
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

    // SELECT - Buscar aparelho pelo ID
    public String buscar(String idApa) throws SQLException {

        String sql = "SELECT imeiApa FROM Aparelho "
                + "WHERE idApa = ? AND deletado_em IS NULL";

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, idApa);

            try (ResultSet resultado = comando.executeQuery()) {

                if (resultado.next()) {
                    return resultado.getString("imeiApa");
                }

                return null;
            }
        }
    }

    // UPDATE - Atualizar dados do aparelho
    public boolean atualizar(String idApa, String imeiApa,
                             String idTip, String idMod)
                             throws SQLException {

        String sql = "UPDATE Aparelho SET imeiApa = ?, "
                + "idTip = ?, idMod = ? "
                + "WHERE idApa = ? AND deletado_em IS NULL";

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, imeiApa);
            comando.setString(2, idTip);
            comando.setString(3, idMod);
            comando.setString(4, idApa);

            return comando.executeUpdate() > 0;
        }
    }

    // DELETE - Exclusao logica
    public boolean excluir(String idApa) throws SQLException {

        String sql = "UPDATE Aparelho SET deletado_em = NOW() "
                + "WHERE idApa = ? AND deletado_em IS NULL";

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, idApa);

            return comando.executeUpdate() > 0;
        }
    }
}
