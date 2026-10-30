/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.model;

import br.com.banco.ConexaoBanco;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
/**
 *
 * @author Gabri
 */
public class EmprestimoDAO {
    // INSERT - Cadastrar emprestimo
    public void inserir(String idEmp, String idApa,
                        String idUsu, String idChip)
                        throws SQLException {

        String sql = "INSERT INTO Emprestimo "
                + "(idEmp, idApa, idUsu, idChip) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, idEmp);
            comando.setString(2, idApa);
            comando.setString(3, idUsu);
            comando.setString(4, idChip);

            comando.executeUpdate();
        }
    }

    // SELECT - Buscar situacao do emprestimo
    public String buscar(String idEmp) throws SQLException {

        String sql = "SELECT statusEmp FROM Emprestimo "
                + "WHERE idEmp = ?";

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, idEmp);

            try (ResultSet resultado = comando.executeQuery()) {

                if (resultado.next()) {
                    return resultado.getString("statusEmp");
                }

                return null;
            }
        }
    }

    // UPDATE - Atualizar situacao e data de devolucao
    public boolean atualizar(String idEmp, String statusEmp,
                             LocalDateTime dataDevolucao)
                             throws SQLException {

        String sql = "UPDATE Emprestimo "
                + "SET statusEmp = ?, dataDevolucaoEmp = ? "
                + "WHERE idEmp = ?";

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, statusEmp);

            if (dataDevolucao == null) {
                comando.setNull(2, Types.TIMESTAMP);
            } else {
                comando.setTimestamp(2, Timestamp.valueOf(dataDevolucao));
            }

            comando.setString(3, idEmp);

            return comando.executeUpdate() > 0;
        }
    }

    // Cancelar emprestimo sem apagar o historico
    public boolean cancelar(String idEmp) throws SQLException {

        String sql = "UPDATE Emprestimo "
                + "SET statusEmp = 'CANCELADO' "
                + "WHERE idEmp = ?";

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, idEmp);

            return comando.executeUpdate() > 0;
        }
    }
}
