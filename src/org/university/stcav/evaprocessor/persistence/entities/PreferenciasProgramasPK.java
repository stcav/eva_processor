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
public class PreferenciasProgramasPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "Usuario_idUsuario", nullable = false)
    private long usuarioidUsuario;
    @Basic(optional = false)
    @Column(name = "Programa_idPrograma", nullable = false)
    private long programaidPrograma;

    public PreferenciasProgramasPK() {
    }

    public PreferenciasProgramasPK(long usuarioidUsuario, long programaidPrograma) {
        this.usuarioidUsuario = usuarioidUsuario;
        this.programaidPrograma = programaidPrograma;
    }

    public long getUsuarioidUsuario() {
        return usuarioidUsuario;
    }

    public void setUsuarioidUsuario(long usuarioidUsuario) {
        this.usuarioidUsuario = usuarioidUsuario;
    }

    public long getProgramaidPrograma() {
        return programaidPrograma;
    }

    public void setProgramaidPrograma(long programaidPrograma) {
        this.programaidPrograma = programaidPrograma;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) usuarioidUsuario;
        hash += (int) programaidPrograma;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PreferenciasProgramasPK)) {
            return false;
        }
        PreferenciasProgramasPK other = (PreferenciasProgramasPK) object;
        if (this.usuarioidUsuario != other.usuarioidUsuario) {
            return false;
        }
        if (this.programaidPrograma != other.programaidPrograma) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.university.stcav.evaprocessor.persistence.entities.PreferenciasProgramasPK[usuarioidUsuario=" + usuarioidUsuario + ", programaidPrograma=" + programaidPrograma + "]";
    }

}
