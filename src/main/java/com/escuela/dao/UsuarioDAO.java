package com.escuela.dao;

import com.escuela.conexion.ConexionBD;
import com.escuela.modelo.Usuario;
import org.mindrot.jbcrypt.BCrypt;
import java.util.ArrayList;
import java.util.List;

import java.sql.*;

public class UsuarioDAO {

    /** Patrón de un hash BCrypt válido: $2a$ / $2b$ / $2y$ + coste 2 dígitos + 53 chars = 60 total */
    private static final String BCRYPT_PATTERN = "^\\$2[aby]\\$\\d{2}\\$.{53}$";

    public Usuario validar(String usuario, String clavePlana) {
        if (usuario == null || clavePlana == null
                || usuario.trim().isEmpty() || clavePlana.trim().isEmpty()) {
            return null;
        }

        String sql = "SELECT id, usuario, clave, rol, nombres, apellidos "
                   + "FROM usuarios WHERE usuario=? AND activo=1";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, usuario.trim());

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    System.err.println("--> [DAO] ❌ Usuario NO EXISTE o está INACTIVO: " + usuario);
                    return null;
                }

                String hashGuardado = rs.getString("clave");
                int longitud = (hashGuardado != null ? hashGuardado.length() : 0);

                System.out.println("--> [DAO] Usuario ENCONTRADO: " + usuario);
                System.out.println("--> [DAO] Hash en BD: [" + hashGuardado + "]");
                System.out.println("--> [DAO] Longitud del hash: " + longitud);

                // Validación EXPLÍCITA del formato BCrypt
                if (hashGuardado == null || !hashGuardado.matches(BCRYPT_PATTERN)) {
                    System.err.println("--> [DAO] ⚠️ HASH NO ES BCRYPT VÁLIDO. "
                            + "Longitud actual = " + longitud
                            + " (se esperaban 60). Revisa la columna 'clave' en MySQL.");
                    return null;
                }

                boolean coincide;
                try {
                    coincide = BCrypt.checkpw(clavePlana, hashGuardado);
                } catch (IllegalArgumentException e) {
                    System.err.println("--> [DAO] ⚠️ HASH CORRUPTO: " + e.getMessage());
                    return null;
                }

                System.out.println("--> [DAO] ¿Coincide la clave con BCrypt?: " + coincide);

                if (coincide) {
                    Usuario u = new Usuario();
                    u.setId(rs.getInt("id"));
                    u.setUsuario(rs.getString("usuario"));
                    u.setRol(rs.getString("rol"));
                    u.setNombres(rs.getString("nombres"));
                    u.setApellidos(rs.getString("apellidos"));
                    return u;
                }

                System.out.println("--> [DAO] ⚠️ El hash es válido pero la clave no coincide.");
            }
        } catch (SQLException e) {
            System.err.println("--> [DAO] ❌ ERROR SQL en validar: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public boolean crear(Usuario u, String clavePlana) {
        if (u == null || clavePlana == null
                || u.getUsuario() == null || u.getRol() == null) {
            System.err.println("❌ Error al crear usuario: Faltan datos obligatorios.");
            return false;
        }

        String sql = "INSERT INTO usuarios(usuario, clave, rol, nombres, apellidos) "
                   + "VALUES(?,?,?,?,?)";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            String hash = BCrypt.hashpw(clavePlana, BCrypt.gensalt());
            System.out.println("--> [DAO.crear] Hash generado (len=" + hash.length() + "): " + hash);

            ps.setString(1, u.getUsuario());
            ps.setString(2, hash);
            ps.setString(3, u.getRol());
            ps.setString(4, u.getNombres());
            ps.setString(5, u.getApellidos());

            if (ps.executeUpdate() > 0) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) u.setId(keys.getInt(1));
                }
                return true;
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            System.err.println("⚠️ El usuario '" + u.getUsuario() + "' ya existe.");
        } catch (SQLException e) {
            System.err.println("❌ Error SQL en crear: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public int buscarIdPorUsuario(String usuario) {
        if (usuario == null) return 0;
        String sql = "SELECT id FROM usuarios WHERE usuario=?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, usuario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("id");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error SQL en buscarIdPorUsuario: " + e.getMessage());
        }
        return 0;
    }
}