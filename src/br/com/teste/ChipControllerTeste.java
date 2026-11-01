/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.teste;

import br.com.controle.ChipController;
import br.com.model.Chip;
import br.com.model.Sessao;
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
            System.out.println("CADASTRO: Passou");
            this.chip = chip;
            return true;
        } 
        System.out.println("CADASTRO: Falhou: ");
        return false;
    }
    
    public boolean testarBuscar(){
        Chip chipBusca = chipController.buscar(this.chip.getNumeroChip());
        if(chipBusca != null){
            System.out.println("BUSCA: Passou");
            this.chip = chipBusca;
            return true;
        } 
        System.out.println("BUSCA: Falhou: ");
        return false;
    }
    
    public void executar(Sessao sessao) {
        System.out.println("\nTESTE: ChipController");
        ChipControllerTeste teste = new ChipControllerTeste();
        boolean status1 = teste.testarCadastrar(sessao);
        boolean status2 = teste.testarBuscar();
        if (status1 && status2) {
            System.out.println("STATUS:\u001B[32m APROVADO\u001B[0m");
        } else {
            System.out.println("STATUS:\u001B[31m REPROVADO\u001B[0m");
        }
        
    }
}
