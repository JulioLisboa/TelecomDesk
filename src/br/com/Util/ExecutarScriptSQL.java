/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.Util;

import br.com.banco.ConexaoBanco;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author dev
 */
public class ExecutarScriptSQL {
    public boolean executar() {
        String caminhoScript = "sql/inicial.sql";

        try {
            String script = Files.readString(new File(caminhoScript).toPath());

            try (Connection conexao = ConexaoBanco.conectar();
                 Statement comando = conexao.createStatement()) {

                String[] comandos = script.split(";");

                for (String sql : comandos) {
                    sql = sql.trim();

                    if (!sql.isEmpty()) {
                        comando.execute(sql);
                    }
                }
            }

            System.out.println("Script executado com sucesso!");
            return true;

        } catch (IOException | SQLException e) {
            System.err.println("Erro ao executar script SQL: " + e.getMessage());
            return false;
        }
    }
}
