/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.teste;

import br.com.controle.EmprestimoController;
import br.com.controle.SessaoController;
import br.com.dao.EmprestimoDAO;
import br.com.model.Emprestimo;
import br.com.model.Sessao;
import java.sql.SQLException;


/**
 *
 * @author dev
 */
public class EmprestimoControllerTeste {
    private final EmprestimoController emprestimoController;
    private final EmprestimoDAO emprestimoDAO;
    private Emprestimo emprestimo;

    public EmprestimoControllerTeste() {
        this.emprestimoController = new EmprestimoController();
        this.emprestimoDAO = new EmprestimoDAO();
    }

    
    
    public boolean testarRegistrar(Sessao sessao){
        boolean result = emprestimoController.regitsrarEmprestimo(sessao, "APA001", "CHP001", "3b4c5d6e-7f80-4901-b234-c56789012345");
        if(result){
            System.out.println("Registro: Passou");
            return true;
        } 
        System.out.println("Registro: Falhou");
        return false;
    }
    
    public boolean testarAceitar(){
        Sessao sessaoColaborador = new SessaoController().login("mariana.souza@gmail.com", "123456");
        if(sessaoColaborador == null){
            System.out.println("Aceitar: Falhou: login do colaborador");
            return false;
        }

        boolean result = emprestimoController.aceitarEmprestimo(sessaoColaborador, "EMP001");
        if(result){
            System.out.println("Aceitar: Passou");
            return true;
        } 
        System.out.println("Aceitar: Falhou");
        return false;
    }
    
    public boolean testarSolicitarDevolucao(){
        Sessao sessaoColaborador = new SessaoController().login("mariana.souza@gmail.com", "123456");
        if(sessaoColaborador == null){
            System.out.println("Solicitação: Falhou: login do colaborador");
            return false;
        }

        boolean result = emprestimoController.solicitarDevolucao(sessaoColaborador, "EMP001");
        if(result){
            System.out.println("Solicitação: Passou");
            return true;
        } 
        System.out.println("Solicitação: Falhou");
        return false;
    }
    
  
    public boolean testarAceitarDevolucao(Sessao sessao){
        boolean result = emprestimoController.aceitarDevolucao(sessao, "EMP001");
        if(result){
            System.out.println("Aceitar devolução: Passou");
            return true;
        } 
        System.out.println("Aceitar devolução: Falhou");
        return false;
    }
    
    public boolean testarCancelarTecnico(Sessao sessao){
        boolean result = emprestimoController.cancelarEmprestimo(sessao, "EMP002");
        if(result){
            System.out.println("Cancelar(técnico): Passou");
            return true;
        } 
        System.out.println("Cancelar(técnico): Falhou");
        return false;
    }
    
    public boolean testarCancelarColaborador(){
        Sessao sessaoColaborador = new SessaoController().login("mariana.souza@gmail.com", "123456");
        if(sessaoColaborador == null){
            System.out.println("Cancelar(colaborador): Falhou: login do colaborador");
            return false;
        }

        boolean result = emprestimoController.cancelarEmprestimo(sessaoColaborador, "EMP003");
        if(result){
            System.out.println("Cancelar(colaborador): Passou");
            return true;
        } 
        System.out.println("Cancelar(colaborador): Falhou");
        return false;
    }
    
    
    public void executar(Sessao sessao) {
        System.out.println("\nTESTE: EmprestimoController");
        EmprestimoControllerTeste teste = new EmprestimoControllerTeste();
        boolean status1 = teste.testarRegistrar(sessao);
        boolean status2 = teste.testarAceitar();
        boolean status3 = teste.testarSolicitarDevolucao();
        boolean status4 = teste.testarAceitarDevolucao(sessao);
        boolean status5 = teste.testarCancelarTecnico(sessao);
        boolean status6 = teste.testarCancelarColaborador();
        if (status1 && status2 && status3 && status4 && status5 && status6) {
            System.out.println("STATUS:\u001B[32m APROVADO\u001B[0m");
        } else {
            System.out.println("STATUS:\u001B[31m REPROVADO\u001B[0m");
        }
        
    }
}
