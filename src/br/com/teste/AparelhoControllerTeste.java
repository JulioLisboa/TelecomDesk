/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.teste;

import br.com.controle.AparelhoController;
import br.com.model.Aparelho;
import br.com.model.Sessao;
import java.util.List;
import java.util.Random;

/**
 *
 * @author dev
 */
public class AparelhoControllerTeste {
    private final AparelhoController aparelhoController;
    private Aparelho aparelho;
    private String imeiApa;

    public AparelhoControllerTeste() {
        this.aparelhoController = new AparelhoController();
    }

    
    public boolean testarCadastrar(Sessao sessao){
        Random random = new Random();
        String numero1 = String.valueOf(
            1_000_000_000L + (long) (random.nextDouble() * 9_000_000_000L)
        );
        this.imeiApa = numero1;
        boolean result = aparelhoController.cadastrar(sessao, this.imeiApa, "TIP001", "MOD001");
        if(result){
            System.out.println("Cadastro: Passou");
            return true;
        } 
        System.out.println("Cadastro: Falhou: ");
        return false;
    }
    
    public boolean testarBuscar(){
        Aparelho aparelhoBusca = aparelhoController.buscar(this.imeiApa);
        if(aparelhoBusca != null){
            System.out.println("Busca: Passou");
            this.aparelho = aparelhoBusca;
            return true;
        } 
        System.out.println("Busca: Falhou: ");
        return false;
    }
    
    public boolean testarListar(Sessao sessao) {
        List<Aparelho> result = aparelhoController.listar(sessao);

        if (result != null && !result.isEmpty()) {
            System.out.println("Listar: Passou");
            return true;
        }

        System.out.println("Listar: Falhou");
        return false;
    }
    
    public void executar(Sessao sessao) {
        System.out.println("\nTESTE: ChipController");
        AparelhoControllerTeste teste = new AparelhoControllerTeste();
        boolean status1 = teste.testarCadastrar(sessao);
        boolean status2 = teste.testarBuscar();
        boolean status3 = teste.testarListar(sessao);
        if (status1 && status2 && status3) {
            System.out.println("STATUS:\u001B[32m APROVADO\u001B[0m");
        } else {
            System.out.println("STATUS:\u001B[31m REPROVADO\u001B[0m");
        }
        
    }
}
