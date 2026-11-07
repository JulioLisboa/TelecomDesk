/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.model;

/**
 *
 * @author dev
 */
public class Usuario {
    private String idUsu;
    private String nomeUsu;
    private String emailUsu;
    private String telefoneUsu;
    private String tokenUsu;
    private String senhaUsu;
    private String statusUsu;
    private String perfilUsu;
    private String nomeSet;
    private String idSet;

    // utilizado para cadastrar um usuario novo
    public Usuario(String nomeUsu, String emailUsu, String telefoneUsu, String senhaUsu, String idSet) {
        this.nomeUsu = nomeUsu;
        this.emailUsu = emailUsu;
        this.telefoneUsu = telefoneUsu;
        this.senhaUsu = senhaUsu;
        this.idSet = idSet;
    }

    public Usuario(String idUsu, String nomeUsu, String emailUsu, String telefoneUsu, String statusUsu, String perfilUsu, String idSet) {
        this.idUsu = idUsu;
        this.nomeUsu = nomeUsu;
        this.emailUsu = emailUsu;
        this.telefoneUsu = telefoneUsu;
        this.statusUsu = statusUsu;
        this.perfilUsu = perfilUsu;
        this.idSet = idSet;
    }

    public Usuario(String idUsu, String nomeUsu, String emailUsu, String statusUsu, String nomeSet, String idSet) {
        this.idUsu = idUsu;
        this.nomeUsu = nomeUsu;
        this.emailUsu = emailUsu;
        this.statusUsu = statusUsu;
        this.nomeSet = nomeSet;
        this.idSet = idSet;
    }
    
    public Usuario(String idUsu, String nomeUsu, String emailUsu, String telefoneUsu, String tokenUsu, String senhaUsu, String statusUsu, String perfilUsu, String idSet) {
        this.idUsu = idUsu;
        this.nomeUsu = nomeUsu;
        this.emailUsu = emailUsu;
        this.telefoneUsu = telefoneUsu;
        this.tokenUsu = tokenUsu;
        this.senhaUsu = senhaUsu;
        this.statusUsu = statusUsu;
        this.perfilUsu = perfilUsu;
        this.idSet = idSet;
    }

    // Construtor vazio para permitir criar o objeto e preencher seus campos com setters.
    public Usuario() {
    }

    public String getIdUsu() {
        return idUsu;
    }

    public void setIdUsu(String idUsu) {
        this.idUsu = idUsu;
    }

    public String getNomeUsu() {
        return nomeUsu;
    }

    public void setNomeUsu(String nomeUsu) {
        this.nomeUsu = nomeUsu;
    }

    public String getEmailUsu() {
        return emailUsu;
    }

    public void setEmailUsu(String emailUsu) {
        this.emailUsu = emailUsu;
    }

    public String getTelefoneUsu() {
        return telefoneUsu;
    }

    public void setTelefoneUsu(String telefoneUsu) {
        this.telefoneUsu = telefoneUsu;
    }

    public String getTokenUsu() {
        return tokenUsu;
    }

    public void setTokenUsu(String tokenUsu) {
        this.tokenUsu = tokenUsu;
    }

    public String getSenhaUsu() {
        return senhaUsu;
    }

    public void setSenhaUsu(String senhaUsu) {
        this.senhaUsu = senhaUsu;
    }

    public String getStatusUsu() {
        return statusUsu;
    }

    public void setStatusUsu(String statusUsu) {
        this.statusUsu = statusUsu;
    }

    public String getPerfilUsu() {
        return perfilUsu;
    }

    public void setPerfilUsu(String perfilUsu) {
        this.perfilUsu = perfilUsu;
    }

    public String getNomeSet() {
        return nomeSet;
    }

    public void setNomeSet(String nomeSet) {
        this.nomeSet = nomeSet;
    }

    public String getIdSet() {
        return idSet;
    }

    public void setIdSet(String idSet) {
        this.idSet = idSet;
    }
    
    @Override
    public String toString() {
        // Exibe o nome do colaborador na lista de empréstimos
        return this.getNomeUsu(); 
    }
    
}
