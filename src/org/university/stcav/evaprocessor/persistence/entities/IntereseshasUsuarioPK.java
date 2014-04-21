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
public class IntereseshasUsuarioPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "Intereses_idIntereses", nullable = false)
    private long interesesidIntereses;
    @Basic(optional = false)
    @Column(name = "Usuario_idUsuario", nullable = false)
    private long usuarioidUsuario;

    public IntereseshasUsuarioPK() {
    }

    public IntereseshasUsuarioPK(long interesesidIntereses, long usuarioidUsuario) {
        this.interesesidIntereses = interesesidIntereses;
        this.usuarioidUsuario = usuarioidUsuario;
    }

    public long getInteresesidIntereses() {
        return interesesidIntereses;
    }

    public void setInteresesidIntereses(long interesesidIntereses) {
        this.interesesidIntereses = interesesidIntereses;
    }

    public long getUsuarioidUsuario() {
        return usuarioidUsuario;
    }

    public void setUsuarioidUsuario(long usuarioidUsuario) {
        this.usuarioidUsuario = usuarioidUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) interesesidIntereses;
        hash += (int) usuarioidUsuario;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IntereseshasUsuarioPK)) {
            return false;
        }
        IntereseshasUsuarioPK other = (IntereseshasUsuarioPK) object;
        if (this.interesesidIntereses != other.interesesidIntereses) {
            return false;
        }
        if (this.usuarioidUsuario != other.usuarioidUsuario) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.university.stcav.evaprocessor.persistence.entities.IntereseshasUsuarioPK[interesesidIntereses=" + interesesidIntereses + ", usuarioidUsuario=" + usuarioidUsuario + "]";
    }

}
