package com.escuela.util;

import org.mindrot.jbcrypt.BCrypt;

public class GenerarHash {
    public static void main(String[] args) {
        System.out.println(BCrypt.hashpw("admin123", BCrypt.gensalt()));
    }
}