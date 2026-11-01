/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.controle;

import br.com.dao.ModeloDAO;
import br.com.model.Modelo;
import java.util.List;
import java.sql.SQLException;

/**
 *
 * @author dev
 */
public class ModeloController {
    private final ModeloDAO modeloDAO;

    public ModeloController() {
        this.modeloDAO = new ModeloDAO();
    }
    
    public List<Modelo> listar() {
        try {
            return modeloDAO.listar();
        } catch (IllegalArgumentException | SQLException e) {
            System.out.println("Erro ao listar os Modelos: " + e.getMessage());
            return null;
        }
    }
}
