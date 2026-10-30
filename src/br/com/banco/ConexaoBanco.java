/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.banco;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author Gabri
 */
public class ConexaoBanco {
    
    private static final String URL = "jdbc:mysql://localhost:3306/telecomdesk";
    private static final String USUARIO = "root";

    public static Connection conectar() throws SQLException {
        String senha = System.getenv("TELECOMDESK_DB_PASSWORD");
        return DriverManager.getConnection(URL, USUARIO, "catolica");
    }
}
