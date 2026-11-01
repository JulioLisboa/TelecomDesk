/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.teste;

import br.com.controle.UsuarioController;
import br.com.model.Sessao;
import br.com.model.Usuario;
import java.util.List;
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
    
    public boolean testarEditar(Sessao sessao){
            this.usu.setNomeUsu("Usuario Editado");
            boolean result = usuarioController.editar(sessao,this.usu);
            if(result){
                System.out.println("Editar: Passou");
                return true;
            } 
            System.out.println("Editar: Falhou: ");
            return false;
    }
    
    public boolean testarBuscar(Sessao sessao){
            Usuario result = usuarioController.buscar(sessao, this.usu.getEmailUsu());
            if(result != null){
                System.out.println("Buscar: Passou");
                return true;
            } 
            System.out.println("Buscar: Falhou: ");
            return false;
    }
    
    public boolean testarListar(Sessao sessao) {
        List<Usuario> result = usuarioController.listar(sessao);

        if (result != null && !result.isEmpty()) {
            System.out.println("Listar: Passou");
            return true;
        }

        System.out.println("Listar: Falhou");
        return false;
    }
    
    public boolean testarDesativar(Sessao sessao){
            boolean result = usuarioController.desativar(sessao,this.usu.getIdUsu());
            if(result){
                System.out.println("Desativar: Passou");
                return true;
            } 
            System.out.println("Desativar: Falhou: ");
            return false;
    }


    public void executar(Sessao sessao) {
        System.out.println("\nTESTE: UsuarioController");
        UsuarioControllerTeste teste = new UsuarioControllerTeste();
        boolean status1 = teste.testarCadastrar();
        boolean status2 = teste.testarEditar(sessao);
        boolean status3 = teste.testarDesativar(sessao);
        boolean status4 = teste.testarBuscar(sessao);
        boolean status5 = teste.testarListar(sessao);
        if (status1 && status2 && status3 && status4 && status5) {
            System.out.println("STATUS:\u001B[32m APROVADO\u001B[0m");
        } else {
            System.out.println("STATUS:\u001B[31m REPROVADO\u001B[0m");
        }
        
    }
}
