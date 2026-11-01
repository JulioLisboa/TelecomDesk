/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.teste;

import br.com.controle.ModeloController;
import br.com.model.Modelo;
import java.util.List;

/**
 *
 * @author dev
 */
public class ModeloControllerTeste {
    private final ModeloController modeloController;

    public ModeloControllerTeste() {
        this.modeloController = new ModeloController();
    }
    
    public boolean testarListar() {
        List<Modelo> result = modeloController.listar();

        if (result != null && !result.isEmpty()) {
            System.out.println("Listar: Passou");
            return true;
        }

        System.out.println("Listar: Falhou");
        return false;
    }
    
    public void executar() {
        System.out.println("\nTESTE: ModeloController");
        ModeloControllerTeste teste = new ModeloControllerTeste();
        boolean status1 = teste.testarListar();
        if (status1) {
            System.out.println("STATUS:\u001B[32m APROVADO\u001B[0m");
        } else {
            System.out.println("STATUS:\u001B[31m REPROVADO\u001B[0m");
        }
        
    }
}
