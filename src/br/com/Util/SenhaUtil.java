/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.Util;

import java.security.MessageDigest;

/**
 *
 * @author dev
 */
public class SenhaUtil {
    public static String criptografar(String senha) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");

        byte[] hash = md.digest(senha.getBytes());

        StringBuilder resultado = new StringBuilder();

        for (byte b : hash) {
            resultado.append(String.format("%02x", b));
        }

        return resultado.toString();
    }
    // o static vai permitir utilizar a função sem intanciar a classe
    public static boolean verificar(String senhaDigitada, String hashArmazenado)throws Exception {
        String hashGerado = criptografar(senhaDigitada);
        return hashGerado.equals(hashArmazenado);
    }
     
     
}
