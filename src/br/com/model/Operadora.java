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
public class Operadora {
    private String idOpe;
    private String nomeOpe;
    private String deletado_em;

    public Operadora(String idOpe, String nomeOpe, String deletado_em) {
        this.idOpe = idOpe;
        this.nomeOpe = nomeOpe;
        this.deletado_em = deletado_em;
    }

    public String getIdOpe() {
        return idOpe;
    }

    public void setIdOpe(String idOpe) {
        this.idOpe = idOpe;
    }

    public String getNomeOpe() {
        return nomeOpe;
    }

    public void setNomeOpe(String nomeOpe) {
        this.nomeOpe = nomeOpe;
    }

    public String getDeletado_em() {
        return deletado_em;
    }

    public void setDeletado_em(String deletado_em) {
        this.deletado_em = deletado_em;
    }
    
    
}
