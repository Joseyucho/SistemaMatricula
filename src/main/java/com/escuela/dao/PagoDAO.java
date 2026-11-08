package com.escuela.dao;

import com.escuela.conexion.ConexionBD;
import com.escuela.modelo.Pago;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PagoDAO {

    public boolean registrarPago(int calendarioId, int alumnoId, BigDecimal monto, int usuarioId) {
        String sqlPago = "INSERT INTO pagos(calendario_id,alumno_id,monto,metodo,usuario_id) VALUES(?,?,?,'EFECTIVO',?)";
        String sqlUpd  = "UPDATE calendario_pagos SET estado='PAGADO' WHERE id=?";
        try (Connection con = ConexionBD.getConexion()) {
            con.setAutoCommit(false);
            try (PreparedStatement ps = con.prepareStatement(sqlPago)) {
                ps.setInt(1, calendarioId);
                ps.setInt(2, alumnoId);
                ps.setBigDecimal(3, monto);
                ps.setInt(4, usuarioId);
                ps.executeUpdate();
            }
            try (PreparedStatement ps = con.prepareStatement(sqlUpd)) {
                ps.setInt(1, calendarioId);
                ps.executeUpdate();
            }
            con.commit();
            return true;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public List<Pago> listarPorAlumno(int alumnoId) {
        List<Pago> lista = new ArrayList<>();
        String sql = "SELECT cp.*, CONCAT(a.apellidos,', ',a.nombres) AS alumno " +
                     "FROM calendario_pagos cp JOIN alumnos a ON cp.alumno_id=a.id " +
                     "WHERE cp.alumno_id=? ORDER BY cp.anio, cp.mes";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, alumnoId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Pago p = new Pago();
                    p.setId(rs.getInt("id"));
                    p.setAlumnoId(rs.getInt("alumno_id"));
                    p.setConcepto(rs.getString("concepto"));
                    p.setMonto(rs.getBigDecimal("monto"));
                    p.setMes(rs.getInt("mes"));
                    p.setAnio(rs.getInt("anio"));
                    p.setEstado(rs.getString("estado"));
                    p.setFechaVencimiento(rs.getDate("fecha_vencimiento"));
                    p.setAlumno(rs.getString("alumno"));
                    lista.add(p);
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    public void generarMensualidades(int alumnoId, int anio, BigDecimal monto) {
        String sql = "INSERT INTO calendario_pagos(alumno_id,concepto,monto,mes,anio,fecha_vencimiento) " +
                     "VALUES(?,?,?,?,?,?)";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            for (int mes = 3; mes <= 12; mes++) {
                ps.setInt(1, alumnoId);
                ps.setString(2, "Mensualidad " + mes);
                ps.setBigDecimal(3, monto);
                ps.setInt(4, mes);
                ps.setInt(5, anio);
                ps.setDate(6, java.sql.Date.valueOf(String.format("%d-%02d-10", anio, mes)));
                ps.executeUpdate();
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }
}