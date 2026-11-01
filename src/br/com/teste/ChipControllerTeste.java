/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.teste;

import br.com.controle.ChipController;
import br.com.model.Chip;
import br.com.model.Sessao;
import java.util.List;
import java.util.Random;

/**
 *
 * @author dev
 */
public class ChipControllerTeste {
    private final ChipController chipController;
    private Chip chip;

    public ChipControllerTeste() {
        this.chipController = new ChipController();
    }

    
    public boolean testarCadastrar(Sessao sessao){
        Random random = new Random();
        String numero1 = String.valueOf(
            1_000_000_000L + (long) (random.nextDouble() * 9_000_000_000L)
        );

        String numero2 = String.valueOf(
            1_000_000_000L + (long) (random.nextDouble() * 9_000_000_000L)
        );
        Chip chip = new Chip(numero1, numero2, "OPE001");
        boolean result = chipController.cadastrar(sessao, chip.getIccidChip(), chip.getNumeroChip(), chip.getIdOpe());
        if(result){
            System.out.println("Cadastro: Passou");
            this.chip = chip;
            return true;
        } 
        System.out.println("Cadastro: Falhou: ");
        return false;
    }
    
    public boolean testarBuscar(){
        Chip chipBusca = chipController.buscar(this.chip.getNumeroChip());
        if(chipBusca != null){
            System.out.println("Busca: Passou");
            this.chip = chipBusca;
            return true;
        } 
        System.out.println("Busca: Falhou: ");
        return false;
    }
    
    public boolean testarListar(Sessao sessao) {
        List<Chip> result = chipController.listar(sessao);

        if (result != null && !result.isEmpty()) {
            System.out.println("Listar: Passou");
            return true;
        }

        System.out.println("Listar: Falhou");
        return false;
    }
    
    public void executar(Sessao sessao) {
        System.out.println("\nTESTE: ChipController");
        ChipControllerTeste teste = new ChipControllerTeste();
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
