/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.controle;

import br.com.dao.TipoAparelhoDAO;
import br.com.model.Tipo_Aparelho;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author dev
 */
public class TipoAparelhoController {
    private final TipoAparelhoDAO tipoAparelhoDAO;

    public TipoAparelhoController() {
        this.tipoAparelhoDAO = new TipoAparelhoDAO();
    }
    
    public List<Tipo_Aparelho> listar() {
        try {
            return tipoAparelhoDAO.listar();
        } catch (IllegalArgumentException | SQLException e) {
            System.out.println("Erro ao listar os tipos de Aparelhos: " + e.getMessage());
            return null;
        }
    }
}
