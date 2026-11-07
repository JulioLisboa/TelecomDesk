/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.model;

import java.util.Date;

/**
 *
 * @author dev
 */
public class Setor {
    private String idSet;
    private String nomeSet;
    private Date deletado_em;
    
    public Setor(String idSet, String nomeSet) {
        this.idSet = idSet;
        this.nomeSet = nomeSet;
    }

    public String getIdSet() {
        return idSet;
    }

    public void setIdSet(String idSet) {
        this.idSet = idSet;
    }

    public String getNomeSet() {
        return nomeSet;
    }

    public void setNomeSet(String nomeSet) {
        this.nomeSet = nomeSet;
    }

    public Date getDeletado_em() {
        return deletado_em;
    }

    public void setDeletado_em(Date deletado_em) {
        this.deletado_em = deletado_em;
    }

}

