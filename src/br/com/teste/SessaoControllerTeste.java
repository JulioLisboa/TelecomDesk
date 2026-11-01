/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.teste;

import br.com.controle.SessaoController;
import br.com.model.Sessao;


/**
 *
 * @author dev
 */
public class SessaoControllerTeste {
    public Sessao testarlogin() {
        SessaoController sessaoController = new SessaoController();
        Sessao sessao = sessaoController.login("admin@gmail.com", "123456");
        boolean result = sessao != null;
        if(result){
            System.out.println("Login: Passou");
            return sessao;
        } 
        System.out.println("Login: Falhou: ");
        return null;       
    }
    
    public Sessao executar() {
        System.out.println("\nTESTE: SessaoController");
        SessaoControllerTeste teste = new SessaoControllerTeste();
        Sessao sessao = teste.testarlogin();
        boolean status = sessao != null;
        if (status) {
            System.out.println("STATUS:\u001B[32m APROVADO\u001B[0m");
            return sessao;
        } else {
            System.out.println("STATUS:\u001B[31m REPROVADO\u001B[0m");
            return null;
        }
        
    }
}
