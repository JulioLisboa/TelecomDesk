/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.test;

import br.com.controle.UsuarioController;
import br.com.model.Usuario;
import java.util.UUID;

/**
 *
 * @author dev
 */
public class UsuarioControllerTeste {
    private UsuarioController usuarioController;
    private Usuario usu;

    public UsuarioControllerTeste() {
        usuarioController = new UsuarioController();

        usu = new Usuario(
            "Usuario Teste",
            "teste_" + UUID.randomUUID() + "@email.com",
            "61999999999",
            "123456",
            "SET002"
        );
    }

    public boolean testarCadastrar() {
        boolean result = usuarioController.cadastrar(this.usu);
            if(result){
                System.out.println("Cadatro: Passou");
                return true;
            } 
            System.out.println("Cadastro: Falhou: ");
            return false;
    }
    
    public boolean testarEditar(){
            this.usu.setNomeUsu("Usuario Editado");
            boolean result = usuarioController.editar(this.usu);
            if(result){
                System.out.println("Editar: Passou");
                return true;
            } 
            System.out.println("Editar: Falhou: ");
            return false;
    }
    
    public boolean testarBuscar(){
            Usuario result = usuarioController.buscar(this.usu.getEmailUsu());
            if(result != null){
                System.out.println("Buscar: Passou");
                return true;
            } 
            System.out.println("Buscar: Falhou: ");
            return false;
    }
    
    public boolean testarDesativar(){
            boolean result = usuarioController.desativar(this.usu.getIdUsu());
            if(result){
                System.out.println("Desativar: Passou");
                return true;
            } 
            System.out.println("Desativar: Falhou: ");
            return false;
    }


    public void executar() {
        System.out.println("\nTESTE: UsuarioController");
        UsuarioControllerTeste teste = new UsuarioControllerTeste();
        boolean status1 = teste.testarCadastrar();
        boolean status2 = teste.testarEditar();
        boolean status3 = teste.testarDesativar();
        boolean status4 = teste.testarBuscar();
        if (status1 && status2 && status3 && status4) {
            System.out.println("\u001B[32mSTATUS: APROVADO\u001B[0m");
        } else {
            System.out.println("\u001B[31mSTATUS: REPROVADO\u001B[0m");
        }
        
    }
}
