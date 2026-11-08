package com.escuela.dao;

import org.mindrot.jbcrypt.BCrypt;

public class GenerarHashManual {
    public static void main(String[] args) {
        String password = "admin123";
        String hash = BCrypt.hashpw(password, BCrypt.gensalt());
        
        System.out.println("=========================================");
        System.out.println("COPIA ESTE HASH:");
        System.out.println(hash);
        System.out.println("LONGITUD: " + hash.length());
        System.out.println("=========================================");
    }
}