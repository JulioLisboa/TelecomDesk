/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.teste;

import br.com.model.Sessao;

/**
 *
 * @author dev
 */
public class ExecutarTestes {
    private final TesteConexao testeConexao;
    private final UsuarioControllerTeste usuarioControllerTeste;
    private final SessaoControllerTeste sessaoControllerTeste;

    public ExecutarTestes() {
        this.testeConexao = new TesteConexao();
        this.usuarioControllerTeste = new UsuarioControllerTeste();
        this.sessaoControllerTeste = new SessaoControllerTeste();
    }
    
    public static void main(String[] args){
        ExecutarTestes executarTestes = new ExecutarTestes();
        
        
        System.out.println("===== INICIANDO TESTES =====");

        executarTestes.testeConexao.executar();
        Sessao sessao = executarTestes.sessaoControllerTeste.executar();
        if(sessao != null){
            executarTestes.usuarioControllerTeste.executar(sessao);
        } else {
            System.out.println("\nTESTE: UsuarioController");
            System.out.println("STATUS:\u001B[33mIgnorado\u001B[0m");
        }
        


        System.out.println("\n===== TESTES FINALIZADOS =====");
    }
}

