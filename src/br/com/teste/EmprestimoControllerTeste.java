/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.teste;

import br.com.controle.EmprestimoController;
import br.com.model.Emprestimo;
import br.com.model.Sessao;


/**
 *
 * @author dev
 */
public class EmprestimoControllerTeste {
    private final EmprestimoController emprestimoController;
    private Emprestimo emprestimo;

    public EmprestimoControllerTeste() {
        this.emprestimoController = new EmprestimoController();
    }

    
    
    public boolean testarRegistrar(Sessao sessao){
        boolean result = emprestimoController.regitsrarEmprestimo(sessao, "APA001", "CHP001", "3b4c5d6e-7f80-4901-b234-c56789012345");
        if(result){
            System.out.println("Registro: Passou");
            return true;
        } 
        System.out.println("Registro: Falhou: ");
        return false;
    }
    
    
    public void executar(Sessao sessao) {
        System.out.println("\nTESTE: EmprestimoController");
        EmprestimoControllerTeste teste = new EmprestimoControllerTeste();
        boolean status1 = teste.testarRegistrar(sessao);
        if (status1 ) {
            System.out.println("STATUS:\u001B[32m APROVADO\u001B[0m");
        } else {
            System.out.println("STATUS:\u001B[31m REPROVADO\u001B[0m");
        }
        
    }
}
