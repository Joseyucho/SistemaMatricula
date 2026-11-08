package com.escuela.facade;

import com.escuela.dao.PagoDAO;
import com.escuela.modelo.Pago;

import java.math.BigDecimal;
import java.util.List;

public class PagoFacade {
    private final PagoDAO dao = new PagoDAO();

    public boolean registrarPago(int calendarioId, int alumnoId, BigDecimal monto, int usuarioId) {
        return dao.registrarPago(calendarioId, alumnoId, monto, usuarioId);
    }
    public List<Pago> estadoCuenta(int alumnoId) { return dao.listarPorAlumno(alumnoId); }
}