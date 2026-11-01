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
public class Modelo {
    private String idMod;
    private String nomeMod;
    private String deletado_em;
    private String idMar;

    public Modelo(String idMod, String nomeMod, String deletado_em, String idMar) {
        this.idMod = idMod;
        this.nomeMod = nomeMod;
        this.deletado_em = deletado_em;
        this.idMar = idMar;
    }
    
    
}
