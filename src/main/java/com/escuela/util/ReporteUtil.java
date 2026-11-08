package com.escuela.util;

import com.escuela.conexion.ConexionBD;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.*;

import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.Map;

public class ReporteUtil {

    /**
     * Genera un PDF a partir de un archivo .jrxml y lo escribe directamente en la respuesta HTTP.
     *
     * @param response      La respuesta HTTP donde se escribirá el PDF.
     * @param jrxmlFile     Nombre del archivo .jrxml (ej. "reporte_pagos.jrxml") ubicado en src/main/resources.
     * @param parametros    Parámetros del reporte.
     * @param nombreArchivo Nombre del archivo PDF que verá el usuario.
     */
    public static void generarPDF(HttpServletResponse response, String jrxmlFile,
                                  Map<String, Object> parametros, String nombreArchivo) throws Exception {

        // 1. Cargamos el archivo .jrxml desde el classpath (src/main/resources)
        InputStream jrxmlStream = ReporteUtil.class.getResourceAsStream("/" + jrxmlFile);
        if (jrxmlStream == null) {
            throw new Exception("El archivo " + jrxmlFile + " no se encontró en el classpath (src/main/resources).");
        }

        Connection con = null;
        try {
            // 2. Compilamos el reporte desde el Stream
            JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlStream);

            // 3. Obtenemos la conexión a la base de datos
            con = ConexionBD.getConexion();

            // 4. Llenamos el reporte con los parámetros y la conexión
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, con);

            // 5. Configuramos la respuesta HTTP para el PDF
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=\"" + nombreArchivo + "\"");
            // Indicamos que el contenido no se cachee
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0);

            // 6. Exportamos el PDF directamente al OutputStream de la respuesta
            try (OutputStream out = response.getOutputStream()) {
                JasperExportManager.exportReportToPdfStream(jasperPrint, out);
                out.flush(); // Es seguro hacer flush aquí porque el PDF ya está completo
            }

        } finally {
            // Cerramos recursos en orden inverso al que se abrieron
            if (jrxmlStream != null) {
                try { jrxmlStream.close(); } catch (Exception ignored) {}
            }
            if (con != null) {
                try { con.close(); } catch (Exception ignored) {}
            }
        }
    }
}