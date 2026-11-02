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

    public static Connection conectar() throws SQLException {
        String URL = "jdbc:mysql://localhost:3306/telecomdesk";
        String USUARIO = "root";
        String SENHA = "catolica";
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}
