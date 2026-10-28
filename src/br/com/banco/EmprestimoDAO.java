/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.banco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author Gabri
 */
public class EmprestimoDAO {
    public void inserir(String idEmp, String idApa,
                        String idUsu, String idChip) throws SQLException {

        String consulta = "SELECT COUNT(*) FROM Emprestimo "
                + "WHERE (idApa = ? OR idChip = ?) "
                + "AND statusEmp IN ('AGUARDANDO ENTREGA', "
                + "'EMPRESTADO', 'AGUARDANDO DEVOLUCAO')";

        String sql = "INSERT INTO Emprestimo "
                + "(idEmp, idApa, idUsu, idChip) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection conexao = ConexaoBanco.conectar()) {

            try (PreparedStatement verificar =
                    conexao.prepareStatement(consulta)) {

                verificar.setString(1, idApa);
                verificar.setString(2, idChip);

                try (ResultSet resultado = verificar.executeQuery()) {

                    if (resultado.next() && resultado.getInt(1) > 0) {
                        throw new SQLException(
                            "Aparelho ou chip ja possui emprestimo em aberto!"
                        );
                    }
                }
            }

            try (PreparedStatement comando =
                    conexao.prepareStatement(sql)) {

                comando.setString(1, idEmp);
                comando.setString(2, idApa);
                comando.setString(3, idUsu);
                comando.setString(4, idChip);

                comando.executeUpdate();
            }
        }
    }
}
