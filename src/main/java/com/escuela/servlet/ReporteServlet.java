package com.escuela.servlet;

import com.escuela.util.ReporteUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/ReporteServlet")
public class ReporteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        String tipo = req.getParameter("tipo");
        
        if (tipo == null || tipo.trim().isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "El parámetro 'tipo' es obligatorio.");
            return;
        }

        try {
            Map<String, Object> params = new HashMap<>();
            String jrxml;

            switch (tipo) {
                case "matricula": {
                    String nivel = req.getParameter("nivel");
                    String gradoStr = req.getParameter("grado");
                    String seccion = req.getParameter("seccion");
                    
                    params.put("NIVEL", (nivel == null || nivel.trim().isEmpty()) ? null : nivel.trim());
                    
                    if (gradoStr != null && !gradoStr.trim().isEmpty()) {
                        params.put("GRADO", Integer.parseInt(gradoStr.trim()));
                    } else {
                        params.put("GRADO", null);
                    }
                    
                    params.put("SECCION", (seccion == null || seccion.trim().isEmpty()) ? null : seccion.trim());
                    jrxml = "reporte_matricula.jrxml";
                    break;
                }
                case "notas": {
                    String alumnoIdStr = req.getParameter("alumnoId");
                    if (alumnoIdStr == null || alumnoIdStr.trim().isEmpty()) {
                        resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "El parámetro 'alumnoId' es obligatorio para notas.");
                        return;
                    }
                    params.put("ALUMNO_ID", Integer.parseInt(alumnoIdStr.trim()));
                    params.put("ANIO", java.time.LocalDate.now().getYear());
                    jrxml = "reporte_notas.jrxml";
                    break;
                }
                case "pagos": {
                    String alumnoIdStr = req.getParameter("alumnoId");
                    if (alumnoIdStr == null || alumnoIdStr.trim().isEmpty()) {
                        resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "El parámetro 'alumnoId' es obligatorio para pagos.");
                        return;
                    }
                    params.put("ALUMNO_ID", Integer.parseInt(alumnoIdStr.trim()));
                    params.put("ANIO", java.time.LocalDate.now().getYear());
                    jrxml = "reporte_pagos.jrxml";
                    break;
                }
                case "economico": {
                    String fechaIniStr = req.getParameter("fechaIni");
                    String fechaFinStr = req.getParameter("fechaFin");
                    
                    if (fechaIniStr == null || fechaFinStr == null || 
                        fechaIniStr.trim().isEmpty() || fechaFinStr.trim().isEmpty()) {
                        resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Las fechas 'fechaIni' y 'fechaFin' son obligatorias.");
                        return;
                    }
                    
                    params.put("FECHA_INI", Date.valueOf(fechaIniStr.trim()));
                    params.put("FECHA_FIN", Date.valueOf(fechaFinStr.trim()));
                    jrxml = "reporte_economico.jrxml";
                    break;
                }
                default:
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Tipo de reporte no válido: " + tipo);
                    return;
            }

            // Generar el PDF
            ReporteUtil.generarPDF(resp, jrxml, params, tipo + ".pdf");

        } catch (NumberFormatException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Error en el formato de los parámetros numéricos: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Error en el formato de fecha (use YYYY-MM-DD): " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            // Este mensaje te dirá EXACTAMENTE qué archivo .jrxml falta o qué falló
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al generar reporte: " + e.getMessage());
        }
    }
}