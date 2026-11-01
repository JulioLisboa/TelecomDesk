/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.teste;
import br.com.banco.ConexaoBanco;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author dev
 */

public class TesteConexao {
    
    public boolean conexao() {

        try (Connection conexao = ConexaoBanco.conectar()) {
            System.out.println("Conexao: Passou");
            return true;

        } catch (SQLException e) {
            System.out.println("Conexao: Falhou: " + e.getMessage());
            return false;
        }
    }
    public void executar() {
        System.out.println("\nTESTE: ConexaoBanco");
        TesteConexao testeConexao = new TesteConexao();
        boolean status = testeConexao.conexao();
        if (status) {
            System.out.println("STATUS:\u001B[32m APROVADO\u001B[0m");
        } else {
            System.out.println("STATUS:\u001B[31m REPROVADO\u001B[0m");
        }
        
    }
}
