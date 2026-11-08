package com.escuela.conexion;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConexionBD {

    private static final String JNDI_NAME = "jdbc/colegioDB";

    public static Connection getConexion() throws SQLException {
        DataSource ds;
        try {
            InitialContext ctx = new InitialContext();
            ds = (DataSource) ctx.lookup(JNDI_NAME);
        } catch (NamingException e) {
            System.err.println("❌ [ConexionBD] JNDI '" + JNDI_NAME + "' no encontrado. "
                    + "Verifica GlassFish Admin Console → Resources → JDBC Resources.");
            throw new SQLException("Recurso JNDI '" + JNDI_NAME + "' no encontrado", e);
        }

        if (ds == null) {
            throw new SQLException("DataSource '" + JNDI_NAME + "' es null");
        }

        try {
            Connection con = ds.getConnection();
            if (con == null) {
                throw new SQLException("El pool devolvió una conexión null");
            }
            return con;
        } catch (SQLException e) {
            System.err.println("❌ [ConexionBD] No se pudo obtener conexión del pool. "
                    + "Causa: " + e.getMessage());
            throw e;
        }
    }
}