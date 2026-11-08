package com.escuela.util;

import org.mindrot.jbcrypt.BCrypt;

public class SeguridadUtil {
    public static String hash(String clave) { return BCrypt.hashpw(clave, BCrypt.gensalt()); }
    public static boolean verificar(String clave, String hash) { return BCrypt.checkpw(clave, hash); }
}