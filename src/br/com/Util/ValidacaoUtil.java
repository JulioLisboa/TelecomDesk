/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.Util;

/**
 *
 * @author dev
 */
public class ValidacaoUtil {
    
    public static boolean campoVazio(String[] valores) {
        for (String valor : valores) {
            if (valor == null || valor.isBlank()) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean tamanhoValido(String campo, int tamanhoCampo){
        return campo.length() <= tamanhoCampo;
    }
}
