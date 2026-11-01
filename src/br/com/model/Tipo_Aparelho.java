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
public class Tipo_Aparelho {
    private String idTip;
    private String nomeTip;
    private String deletado_em;

    public Tipo_Aparelho(String idTip, String nomeTip, String deletado_em) {
        this.idTip = idTip;
        this.nomeTip = nomeTip;
        this.deletado_em = deletado_em;
    }

    public String getIdTip() {
        return idTip;
    }

    public void setIdTip(String idTip) {
        this.idTip = idTip;
    }

    public String getNomeTip() {
        return nomeTip;
    }

    public void setNomeTip(String nomeTip) {
        this.nomeTip = nomeTip;
    }

    public String getDeletado_em() {
        return deletado_em;
    }

    public void setDeletado_em(String deletado_em) {
        this.deletado_em = deletado_em;
    }
    
    
}
