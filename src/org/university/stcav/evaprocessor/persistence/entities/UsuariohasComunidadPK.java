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
public class UsuariohasComunidadPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "Usuario_idUsuario", nullable = false)
    private long usuarioidUsuario;
    @Basic(optional = false)
    @Column(name = "Comunidad_idComunidad", nullable = false)
    private long comunidadidComunidad;

    public UsuariohasComunidadPK() {
    }

    public UsuariohasComunidadPK(long usuarioidUsuario, long comunidadidComunidad) {
        this.usuarioidUsuario = usuarioidUsuario;
        this.comunidadidComunidad = comunidadidComunidad;
    }

    public long getUsuarioidUsuario() {
        return usuarioidUsuario;
    }

    public void setUsuarioidUsuario(long usuarioidUsuario) {
        this.usuarioidUsuario = usuarioidUsuario;
    }

    public long getComunidadidComunidad() {
        return comunidadidComunidad;
    }

    public void setComunidadidComunidad(long comunidadidComunidad) {
        this.comunidadidComunidad = comunidadidComunidad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) usuarioidUsuario;
        hash += (int) comunidadidComunidad;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuariohasComunidadPK)) {
            return false;
        }
        UsuariohasComunidadPK other = (UsuariohasComunidadPK) object;
        if (this.usuarioidUsuario != other.usuarioidUsuario) {
            return false;
        }
        if (this.comunidadidComunidad != other.comunidadidComunidad) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.university.stcav.evaprocessor.persistence.entities.UsuariohasComunidadPK[usuarioidUsuario=" + usuarioidUsuario + ", comunidadidComunidad=" + comunidadidComunidad + "]";
    }

}
