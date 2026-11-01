/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.teste;

import br.com.controle.TipoAparelhoController;
import br.com.model.Tipo_Aparelho;
import java.util.List;

/**
 *
 * @author dev
 */
public class TipoAparelhoControllerTeste {
        private final TipoAparelhoController tipoAparelhoController;

    public TipoAparelhoControllerTeste() {
        this.tipoAparelhoController = new TipoAparelhoController();
    }
    
    public boolean testarListar() {
        List<Tipo_Aparelho> result = tipoAparelhoController.listar();

        if (result != null && !result.isEmpty()) {
            System.out.println("Listar: Passou");
            return true;
        }

        System.out.println("Listar: Falhou");
        return false;
    }
    
    public void executar() {
        System.out.println("\nTESTE: TipoAparelhoController");
        TipoAparelhoControllerTeste teste = new TipoAparelhoControllerTeste();
        boolean status1 = teste.testarListar();
        if (status1) {
            System.out.println("STATUS:\u001B[32m APROVADO\u001B[0m");
        } else {
            System.out.println("STATUS:\u001B[31m REPROVADO\u001B[0m");
        }
        
    }
}
