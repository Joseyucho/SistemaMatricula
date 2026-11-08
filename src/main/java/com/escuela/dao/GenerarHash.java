package com.escuela.dao;

import org.mindrot.jbcrypt.BCrypt;

public class GenerarHash {
    public static void main(String[] args) {
        // Genera el hash para la contraseña que quieres usar (admin123)
        String hash = BCrypt.hashpw("admin123", BCrypt.gensalt());
        
        System.out.println("============================================================");
        System.out.println("COPIA ESTO Y PONLO EN MYSQL:");
        System.out.println(hash);
        System.out.println("Longitud: " + hash.length()); // Debe ser 60
        System.out.println("============================================================");
    }
}