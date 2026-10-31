/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.test;

/**
 *
 * @author dev
 */
public class ExecutarTestes {
    private final TesteConexao testeConexao;
    private final UsuarioControllerTeste usuarioControllerTeste;

    public ExecutarTestes() {
        this.testeConexao = new TesteConexao();
        this.usuarioControllerTeste = new UsuarioControllerTeste();
    }
    
    public static void main(String[] args){
        ExecutarTestes executarTestes = new ExecutarTestes();
        
        
        System.out.println("===== INICIANDO TESTES =====");

        executarTestes.testeConexao.executar();
        executarTestes.usuarioControllerTeste.executar();


        System.out.println("\n===== TESTES FINALIZADOS =====");
    }
}

