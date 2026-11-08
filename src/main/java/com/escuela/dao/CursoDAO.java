package com.escuela.dao;

import com.escuela.conexion.ConexionBD;
import com.escuela.modelo.Curso;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CursoDAO {

    public boolean insertar(Curso c) {
        String sql = "INSERT INTO cursos(nombre,nivel,grado,seccion,docente_id,hora_inicio,hora_fin,dia_semana) " +
                     "VALUES(?,?,?,?,?,?,?,?)";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getNivel());
            ps.setInt(3, c.getGrado());
            ps.setString(4, c.getSeccion());
            ps.setInt(5, c.getDocenteId());
            
            // Convertimos el String "07:30:00" a un objeto java.sql.Time para MySQL
            try {
                ps.setTime(6, Time.valueOf(c.getHoraInicio()));
                ps.setTime(7, Time.valueOf(c.getHoraFin()));
            } catch (IllegalArgumentException e) {
                System.err.println("❌ Error en el formato de hora. Asegúrate de enviar HH:mm:ss");
                return false;
            }
            
            ps.setString(8, c.getDiaSemana());
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) { 
            // ESTO ES LO QUE TE DIRÁ EL ERROR EXACTO EN LA CONSOLA
            System.err.println("❌ ERROR SQL AL INSERTAR CURSO:");
            e.printStackTrace(); 
            return false; 
        }
    }

    public List<Curso> listar() {
        List<Curso> lista = new ArrayList<>();
        String sql = "SELECT c.*, CONCAT(d.apellidos,', ',d.nombres) AS docente " +
                     "FROM cursos c LEFT JOIN docentes d ON c.docente_id=d.id " +
                     "ORDER BY c.nivel,c.grado,c.seccion,c.nombre";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    public List<Curso> listarPorDocente(int docenteId) {
        List<Curso> lista = new ArrayList<>();
        String sql = "SELECT c.*, CONCAT(d.apellidos,', ',d.nombres) AS docente " +
                     "FROM cursos c LEFT JOIN docentes d ON c.docente_id=d.id " +
                     "WHERE c.docente_id=? ORDER BY c.grado, c.nombre";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, docenteId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    private Curso mapear(ResultSet rs) throws SQLException {
        Curso c = new Curso();
        c.setId(rs.getInt("id"));
        c.setNombre(rs.getString("nombre"));
        c.setNivel(rs.getString("nivel"));
        c.setGrado(rs.getInt("grado"));
        c.setSeccion(rs.getString("seccion"));
        c.setDocenteId(rs.getInt("docente_id"));
        c.setNombreDocente(rs.getString("docente"));
        
        // Convertimos Time de SQL a String normal
        Time hIni = rs.getTime("hora_inicio");
        Time hFin = rs.getTime("hora_fin");
        c.setHoraInicio(hIni != null ? hIni.toString() : "");
        c.setHoraFin(hFin != null ? hFin.toString() : "");
        
        c.setDiaSemana(rs.getString("dia_semana"));
        return c;
    }
}