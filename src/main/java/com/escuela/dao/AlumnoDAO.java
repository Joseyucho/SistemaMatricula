package com.escuela.dao;

import com.escuela.conexion.ConexionBD;
import com.escuela.modelo.Alumno;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlumnoDAO {

    public boolean insertar(Alumno a) {
        String sql = "INSERT INTO alumnos(dni,nombres,apellidos,direccion,apoderado,telefono,nivel,grado,seccion) VALUES(?,?,?,?,?,?,?,?,?)";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, a.getDni());
            ps.setString(2, a.getNombres());
            ps.setString(3, a.getApellidos());
            ps.setString(4, a.getDireccion());
            ps.setString(5, a.getApoderado());
            ps.setString(6, a.getTelefono());
            ps.setString(7, a.getNivel());
            ps.setInt(8, a.getGrado());
            ps.setString(9, a.getSeccion());
            if (ps.executeUpdate() > 0) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) a.setId(keys.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public List<Alumno> listar() {
        List<Alumno> lista = new ArrayList<>();
        String sql = "SELECT * FROM alumnos WHERE estado=1 ORDER BY nivel,grado,seccion,apellidos";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    public List<Alumno> listarPorGrado(String nivel, int grado, String seccion) {
        List<Alumno> lista = new ArrayList<>();
        String sql = "SELECT * FROM alumnos WHERE estado=1 AND nivel=? AND grado=? AND seccion=? ORDER BY apellidos";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nivel);
            ps.setInt(2, grado);
            ps.setString(3, seccion);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    public Alumno buscarPorId(int id) {
        String sql = "SELECT * FROM alumnos WHERE id=?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    /** Cuenta mensualidades PENDIENTES vencidas (bloqueo > 2) */
    public int contarMesesAdeudados(int alumnoId) {
        String sql = "SELECT COUNT(*) FROM calendario_pagos " +
                     "WHERE alumno_id=? AND estado='PENDIENTE' AND fecha_vencimiento < CURDATE()";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, alumnoId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    private Alumno mapear(ResultSet rs) throws SQLException {
        Alumno a = new Alumno();
        a.setId(rs.getInt("id"));
        a.setDni(rs.getString("dni"));
        a.setNombres(rs.getString("nombres"));
        a.setApellidos(rs.getString("apellidos"));
        a.setDireccion(rs.getString("direccion"));
        a.setApoderado(rs.getString("apoderado"));
        a.setTelefono(rs.getString("telefono"));
        a.setNivel(rs.getString("nivel"));
        a.setGrado(rs.getInt("grado"));
        a.setSeccion(rs.getString("seccion"));
        a.setEstado(rs.getBoolean("estado"));
        return a;
    }
}