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
public class Aparelho {
    private String idApa;
    private String imeiApa;
    private String statusApa;
    private String deletado_em;
    private String idTip;
    private String idMod;

    public Aparelho(String idApa, String imeiApa, String statusApa, String idTip, String idMod) {       
        this.idApa = idApa;
        this.imeiApa = imeiApa;
        this.statusApa = statusApa;
        this.idTip = idTip;
        this.idMod = idMod;
    }

    public Aparelho(String idApa, String imeiApa, String statusApa, String deletado_em, String idTip, String idMod) {
        this.idApa = idApa;
        this.imeiApa = imeiApa;
        this.statusApa = statusApa;
        this.deletado_em = deletado_em;
        this.idTip = idTip;
        this.idMod = idMod;
    }

    public String getIdApa() {
        return idApa;
    }

    public void setIdApa(String idApa) {
        this.idApa = idApa;
    }

    public String getImeiApa() {
        return imeiApa;
    }

    public void setImeiApa(String imeiApa) {
        this.imeiApa = imeiApa;
    }

    public String getStatusApa() {
        return statusApa;
    }

    public void setStatusApa(String statusApa) {
        this.statusApa = statusApa;
    }

    public String getDeletado_em() {
        return deletado_em;
    }

    public void setDeletado_em(String deletado_em) {
        this.deletado_em = deletado_em;
    }

    public String getIdTip() {
        return idTip;
    }

    public void setIdTip(String idTip) {
        this.idTip = idTip;
    }

    public String getIdMod() {
        return idMod;
    }

    public void setIdMod(String idMod) {
        this.idMod = idMod;
    }
    
    
    
    
}
