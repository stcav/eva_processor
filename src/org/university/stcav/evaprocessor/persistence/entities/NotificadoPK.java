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
public class NotificadoPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "Noticia_idNoticia", nullable = false)
    private long noticiaidNoticia;
    @Basic(optional = false)
    @Column(name = "Usuario_idUsuario", nullable = false)
    private long usuarioidUsuario;

    public NotificadoPK() {
    }

    public NotificadoPK(long noticiaidNoticia, long usuarioidUsuario) {
        this.noticiaidNoticia = noticiaidNoticia;
        this.usuarioidUsuario = usuarioidUsuario;
    }

    public long getNoticiaidNoticia() {
        return noticiaidNoticia;
    }

    public void setNoticiaidNoticia(long noticiaidNoticia) {
        this.noticiaidNoticia = noticiaidNoticia;
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
        hash += (int) noticiaidNoticia;
        hash += (int) usuarioidUsuario;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NotificadoPK)) {
            return false;
        }
        NotificadoPK other = (NotificadoPK) object;
        if (this.noticiaidNoticia != other.noticiaidNoticia) {
            return false;
        }
        if (this.usuarioidUsuario != other.usuarioidUsuario) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.university.stcav.evaprocessor.persistence.entities.NotificadoPK[noticiaidNoticia=" + noticiaidNoticia + ", usuarioidUsuario=" + usuarioidUsuario + "]";
    }

}
