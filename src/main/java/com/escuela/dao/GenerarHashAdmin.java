package com.escuela.dao;

import org.mindrot.jbcrypt.BCrypt;

public class GenerarHashAdmin {
    public static void main(String[] args) {
        String password = "admin123";
        String hash = BCrypt.hashpw(password, BCrypt.gensalt());
        
        System.out.println("============================================================");
        System.out.println("HASH GENERADO PARA 'admin123':");
        System.out.println(hash);
        System.out.println("LONGITUD: " + hash.length() + " caracteres");
        System.out.println("============================================================");
    }
}