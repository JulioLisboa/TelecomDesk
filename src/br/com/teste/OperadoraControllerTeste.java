/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.teste;

import br.com.controle.OperadoraController;
import br.com.model.Operadora;
import java.util.List;

/**
 *
 * @author dev
 */
public class OperadoraControllerTeste {
    private final OperadoraController operadoraController;

    public OperadoraControllerTeste() {
        this.operadoraController = new OperadoraController();
    }
    
    public boolean testarListar() {
        List<Operadora> result = operadoraController.listar();

        if (result != null && !result.isEmpty()) {
            System.out.println("Listar: Passou");
            return true;
        }

        System.out.println("Listar: Falhou");
        return false;
    }
    
    public void executar() {
        System.out.println("\nTESTE: OperadoraController");
        OperadoraControllerTeste teste = new OperadoraControllerTeste();
        boolean status1 = teste.testarListar();
        if (status1) {
            System.out.println("STATUS:\u001B[32m APROVADO\u001B[0m");
        } else {
            System.out.println("STATUS:\u001B[31m REPROVADO\u001B[0m");
        }
        
    }
}
