/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.university.stcav.evaprocessor.persistence.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author stcav
 */
@Embeddable
public class UsuariohasProfesionPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "Usuario_idUsuario", nullable = false)
    private long usuarioidUsuario;
    @Basic(optional = false)
    @Column(name = "Profesion_idProfesion", nullable = false)
    private long profesionidProfesion;

    public UsuariohasProfesionPK() {
    }

    public UsuariohasProfesionPK(long usuarioidUsuario, long profesionidProfesion) {
        this.usuarioidUsuario = usuarioidUsuario;
        this.profesionidProfesion = profesionidProfesion;
    }

    public long getUsuarioidUsuario() {
        return usuarioidUsuario;
    }

    public void setUsuarioidUsuario(long usuarioidUsuario) {
        this.usuarioidUsuario = usuarioidUsuario;
    }

    public long getProfesionidProfesion() {
        return profesionidProfesion;
    }

    public void setProfesionidProfesion(long profesionidProfesion) {
        this.profesionidProfesion = profesionidProfesion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) usuarioidUsuario;
        hash += (int) profesionidProfesion;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuariohasProfesionPK)) {
            return false;
        }
        UsuariohasProfesionPK other = (UsuariohasProfesionPK) object;
        if (this.usuarioidUsuario != other.usuarioidUsuario) {
            return false;
        }
        if (this.profesionidProfesion != other.profesionidProfesion) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.university.stcav.evaprocessor.persistence.entities.UsuariohasProfesionPK[usuarioidUsuario=" + usuarioidUsuario + ", profesionidProfesion=" + profesionidProfesion + "]";
    }

}
