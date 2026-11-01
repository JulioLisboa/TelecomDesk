/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.controle;

import br.com.dao.OperadoraDAO;
import br.com.model.Operadora;
import java.util.List;
import java.sql.SQLException;

/**
 *
 * @author dev
 */
public class OperadoraController {
    private final OperadoraDAO operadoraDAO;

    public OperadoraController() {
        this.operadoraDAO = new OperadoraDAO();
    }
    
    public List<Operadora> listar() {
        try {
            return operadoraDAO.listar();
        } catch (IllegalArgumentException | SQLException e) {
            System.out.println("Erro ao listar as Operadoras: " + e.getMessage());
            return null;
        }
    }
}
