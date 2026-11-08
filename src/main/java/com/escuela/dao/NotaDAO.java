package com.escuela.dao;
import com.escuela.conexion.ConexionBD;
import com.escuela.modelo.Nota;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotaDAO {

    public void registrar(int alumnoId, int cursoId, int bimestre, double nota, int anio) throws SQLException {
        // Validación de bloqueo por mora
        if (contarMensualidadesImpagas(alumnoId) > 2) {
            throw new SQLException("ALUMNO BLOQUEADO: tiene más de 2 mensualidades impagas.");
        }
        String sql = "INSERT INTO notas(alumno_id,curso_id,bimestre,nota,anio) VALUES(?,?,?,?,?) " +
                     "ON DUPLICATE KEY UPDATE nota=?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, alumnoId);
            ps.setInt(2, cursoId);
            ps.setInt(3, bimestre);
            ps.setDouble(4, nota);
            ps.setInt(5, anio);
            ps.setDouble(6, nota);
            ps.executeUpdate();
        }
    }

    public List<Nota> listarPorAlumno(int alumnoId, int anio) {
        List<Nota> lista = new ArrayList<>();
        String sql = "SELECT n.*, c.nombre AS curso, CONCAT(a.apellidos,', ',a.nombres) AS alumno " +
                     "FROM notas n JOIN cursos c ON n.curso_id=c.id JOIN alumnos a ON n.alumno_id=a.id " +
                     "WHERE n.alumno_id=? AND n.anio=? ORDER BY n.bimestre, c.nombre";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, alumnoId);
            ps.setInt(2, anio);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Nota n = new Nota();
                    n.setId(rs.getInt("id"));
                    n.setAlumnoId(rs.getInt("alumno_id"));
                    n.setCursoId(rs.getInt("curso_id"));
                    n.setBimestre(rs.getInt("bimestre"));
                    n.setNota(rs.getBigDecimal("nota"));
                    n.setAnio(rs.getInt("anio"));
                    n.setCurso(rs.getString("curso"));
                    n.setAlumno(rs.getString("alumno"));
                    lista.add(n);
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    private int contarMensualidadesImpagas(int alumnoId) throws SQLException {
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