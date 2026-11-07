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
public class Chip {
    private String idChip;
    private String iccidChip;
    private String numeroChip;
    private String statusChip;
    private String deletado_em;
    private String idOpe;
    
    public Chip(String iccidChip, String numeroChip, String idOpe) {
        this.iccidChip = iccidChip;
        this.numeroChip = numeroChip;
        this.idOpe = idOpe;
    }

    public Chip(String idChip, String iccidChip, String numeroChip, String statusChip, String deletado_em, String idOpe) {
        this.idChip = idChip;
        this.iccidChip = iccidChip;
        this.numeroChip = numeroChip;
        this.statusChip = statusChip;
        this.deletado_em = deletado_em;
        this.idOpe = idOpe;
    }

    public String getIdChip() {
        return idChip;
    }

    public void setIdChip(String idChip) {
        this.idChip = idChip;
    }

    public String getIccidChip() {
        return iccidChip;
    }

    public void setIccidChip(String iccidChip) {
        this.iccidChip = iccidChip;
    }

    public String getNumeroChip() {
        return numeroChip;
    }

    public void setNumeroChip(String numeroChip) {
        this.numeroChip = numeroChip;
    }

    public String getStatusChip() {
        return statusChip;
    }

    public void setStatusChip(String statusChip) {
        this.statusChip = statusChip;
    }

    public String getDeletado_em() {
        return deletado_em;
    }

    public void setDeletado_em(String deletado_em) {
        this.deletado_em = deletado_em;
    }

    public String getIdOpe() {
        return idOpe;
    }

    public void setIdOpe(String idOpe) {
        this.idOpe = idOpe;
    }
    
    @Override
    public String toString() {
        // Exibe o número de telefone na ComboBox
        return "Tel: " + this.getNumeroChip(); 
    }
    
    
}
