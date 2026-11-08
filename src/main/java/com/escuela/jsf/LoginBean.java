package com.escuela.jsf;

import com.escuela.dao.UsuarioDAO;
import com.escuela.modelo.Usuario;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

import java.io.Serializable;

@Named("loginBean")
@SessionScoped
public class LoginBean implements Serializable {
    private String usuario;
    private String clave;
    private Usuario logueado;

    public String login() {
        UsuarioDAO dao = new UsuarioDAO();
        Usuario u = dao.validar(usuario, clave);
        if (u == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Credenciales incorrectas", null));
            return null;
        }
        logueado = u;
        return "dashboard?faces-redirect=true";
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "login?faces-redirect=true";
    }

    // getters/setters
    public String getUsuario() { return usuario; }
    public void setUsuario(String u) { this.usuario = u; }
    public String getClave() { return clave; }
    public void setClave(String c) { this.clave = c; }
    public Usuario getLogueado() { return logueado; }
}