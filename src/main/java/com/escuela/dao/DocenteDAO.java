package com.escuela.dao;

import com.escuela.conexion.ConexionBD;
import com.escuela.modelo.Docente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DocenteDAO {

    public boolean insertar(Docente d) {
        String sql = "INSERT INTO docentes(dni,nombres,apellidos,especialidad,telefono,email,usuario_id) VALUES(?,?,?,?,?,?,?)";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, d.getDni());
            ps.setString(2, d.getNombres());
            ps.setString(3, d.getApellidos());
            ps.setString(4, d.getEspecialidad());
            ps.setString(5, d.getTelefono());
            ps.setString(6, d.getEmail());
            ps.setInt(7, d.getUsuarioId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public List<Docente> listar() {
        List<Docente> lista = new ArrayList<>();
        String sql = "SELECT * FROM docentes ORDER BY apellidos";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Docente d = new Docente();
                d.setId(rs.getInt("id"));
                d.setDni(rs.getString("dni"));
                d.setNombres(rs.getString("nombres"));
                d.setApellidos(rs.getString("apellidos"));
                d.setEspecialidad(rs.getString("especialidad"));
                d.setTelefono(rs.getString("telefono"));
                d.setEmail(rs.getString("email"));
                d.setUsuarioId(rs.getInt("usuario_id"));
                lista.add(d);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    public Docente buscarPorUsuarioId(int usuarioId) {
        String sql = "SELECT * FROM docentes WHERE usuario_id=?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, usuarioId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Docente d = new Docente();
                    d.setId(rs.getInt("id"));
                    d.setNombres(rs.getString("nombres"));
                    d.setApellidos(rs.getString("apellidos"));
                    d.setUsuarioId(rs.getInt("usuario_id"));
                    return d;
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }
}