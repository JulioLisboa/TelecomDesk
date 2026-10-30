/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.banco;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author dev
 */

public class TesteConexao {
    public static void main(String[] args) {

        try (Connection conexao = ConexaoBanco.conectar()) {
            System.out.println("Conexao realizada com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
