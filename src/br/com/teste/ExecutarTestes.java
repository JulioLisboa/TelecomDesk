/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.teste;

import br.com.Util.ExecutarScriptSQL;
import br.com.model.Sessao;

/**
 *
 * @author dev
 */
public class ExecutarTestes {
    private final TesteConexao testeConexao;
    private final UsuarioControllerTeste usuarioControllerTeste;
    private final SessaoControllerTeste sessaoControllerTeste;
    private final ChipControllerTeste chipControllerTeste;
    private final AparelhoControllerTeste aparelhoControllerTeste;
    private final ModeloControllerTeste modeloControllerTeste;
    private final OperadoraControllerTeste operadoraControllerTeste;
    private final TipoAparelhoControllerTeste tipoAparelhoControllerTeste;
    private final EmprestimoControllerTeste emprestimoControllerTeste;
    private final ExecutarScriptSQL executarScriptSQL;

    public ExecutarTestes() {
        this.testeConexao = new TesteConexao();
        this.usuarioControllerTeste = new UsuarioControllerTeste();
        this.sessaoControllerTeste = new SessaoControllerTeste();
        this.chipControllerTeste = new ChipControllerTeste();
        this.aparelhoControllerTeste = new AparelhoControllerTeste();
        this.modeloControllerTeste = new ModeloControllerTeste();
        this.operadoraControllerTeste = new OperadoraControllerTeste();
        this.tipoAparelhoControllerTeste = new TipoAparelhoControllerTeste();
        this.emprestimoControllerTeste = new EmprestimoControllerTeste();
        this.executarScriptSQL = new ExecutarScriptSQL();
    }
    
    public static void main(String[] args){
        ExecutarTestes executarTestes = new ExecutarTestes();

        System.out.println("===== EXECUTANDO SCRIPT SQL =====");

        if (!executarTestes.executarScriptSQL.executar()) {
            System.err.println("Erro no script SQL.");
            return;
        }
        
        
        System.out.println("===== INICIANDO TESTES =====");

        executarTestes.testeConexao.executar();
        System.out.println("-----------------------");
        Sessao sessao = executarTestes.sessaoControllerTeste.executar();
        System.out.println("-----------------------");
        executarTestes.modeloControllerTeste.executar();
        System.out.println("-----------------------");
        executarTestes.operadoraControllerTeste.executar();
        System.out.println("-----------------------");
        executarTestes.tipoAparelhoControllerTeste.executar();
        System.out.println("-----------------------");
        if(sessao != null){
            executarTestes.usuarioControllerTeste.executar(sessao);
            System.out.println("-----------------------");
            executarTestes.chipControllerTeste.executar(sessao);
            System.out.println("-----------------------");
            executarTestes.aparelhoControllerTeste.executar(sessao);
            System.out.println("-----------------------");
            executarTestes.emprestimoControllerTeste.executar(sessao);
            System.out.println("-----------------------");
            
        } else {
            System.out.println("\nVarios testes \u001B[33mIgnorados\u001B[0m");
        }
       
        System.out.println("\n===== TESTES FINALIZADOS =====");
    }
}

