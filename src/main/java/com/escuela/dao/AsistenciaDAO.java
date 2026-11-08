package com.escuela.dao;
import com.escuela.conexion.ConexionBD;
import java.sql.*;

public class AsistenciaDAO {

    public void registrar(int alumnoId, int cursoId, Date fecha, String estado) throws SQLException {
        if (contarImpagas(alumnoId) > 2) {
            throw new SQLException("Alumno bloqueado por deuda (> 2 mensualidades).");
        }
        String sql = "INSERT INTO asistencia(alumno_id,curso_id,fecha,estado) VALUES(?,?,?,?) " +
                     "ON DUPLICATE KEY UPDATE estado=?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, alumnoId);
            ps.setInt(2, cursoId);
            ps.setDate(3, fecha);
            ps.setString(4, estado);
            ps.setString(5, estado);
            ps.executeUpdate();
        }
    }

    private int contarImpagas(int alumnoId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM calendario_pagos " +
                     "WHERE alumno_id=? AND estado='PENDIENTE' AND fecha_vencimiento < CURDATE()";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, alumnoId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return 0;
    }
}