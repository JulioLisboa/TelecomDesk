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
public class Emprestimo {
    private String idEmp;
    private String dataEmp;
    private String dataDevolucaoEmp;
    private String statusEmp;
    private String idApa;
    private String idUsu;
    private String idChip;

    public Emprestimo(String idEmp, String dataEmp, String dataDevolucaoEmp,
                      String statusEmp, String idApa, String idUsu, String idChip) {
        this.idEmp = idEmp;
        this.dataEmp = dataEmp;
        this.dataDevolucaoEmp = dataDevolucaoEmp;
        this.statusEmp = statusEmp;
        this.idApa = idApa;
        this.idUsu = idUsu;
        this.idChip = idChip;
    }

    public String getIdEmp() {
        return idEmp;
    }

    public void setIdEmp(String idEmp) {
        this.idEmp = idEmp;
    }

    public String getDataEmp() {
        return dataEmp;
    }

    public void setDataEmp(String dataEmp) {
        this.dataEmp = dataEmp;
    }

    public String getDataDevolucaoEmp() {
        return dataDevolucaoEmp;
    }

    public void setDataDevolucaoEmp(String dataDevolucaoEmp) {
        this.dataDevolucaoEmp = dataDevolucaoEmp;
    }

    public String getStatusEmp() {
        return statusEmp;
    }

    public void setStatusEmp(String statusEmp) {
        this.statusEmp = statusEmp;
    }

    public String getIdApa() {
        return idApa;
    }

    public void setIdApa(String idApa) {
        this.idApa = idApa;
    }

    public String getIdUsu() {
        return idUsu;
    }

    public void setIdUsu(String idUsu) {
        this.idUsu = idUsu;
    }

    public String getIdChip() {
        return idChip;
    }

    public void setIdChip(String idChip) {
        this.idChip = idChip;
    }
}
