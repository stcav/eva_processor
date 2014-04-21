/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.university.stcav.evaprocessor.persistence.entities;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author stcav
 */
@Entity
@Table(name = "Notificado", catalog = "stcav1", schema = "")
@NamedQueries({
    @NamedQuery(name = "Notificado.findAll", query = "SELECT n FROM Notificado n"),
    @NamedQuery(name = "Notificado.findByNoticiaidNoticia", query = "SELECT n FROM Notificado n WHERE n.notificadoPK.noticiaidNoticia = :noticiaidNoticia"),
    @NamedQuery(name = "Notificado.findByUsuarioidUsuario", query = "SELECT n FROM Notificado n WHERE n.notificadoPK.usuarioidUsuario = :usuarioidUsuario")})
public class Notificado implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected NotificadoPK notificadoPK;

    public Notificado() {
    }

    public Notificado(NotificadoPK notificadoPK) {
        this.notificadoPK = notificadoPK;
    }

    public Notificado(long noticiaidNoticia, long usuarioidUsuario) {
        this.notificadoPK = new NotificadoPK(noticiaidNoticia, usuarioidUsuario);
    }

    public NotificadoPK getNotificadoPK() {
        return notificadoPK;
    }

    public void setNotificadoPK(NotificadoPK notificadoPK) {
        this.notificadoPK = notificadoPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (notificadoPK != null ? notificadoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Notificado)) {
            return false;
        }
        Notificado other = (Notificado) object;
        if ((this.notificadoPK == null && other.notificadoPK != null) || (this.notificadoPK != null && !this.notificadoPK.equals(other.notificadoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.university.stcav.evaprocessor.persistence.entities.Notificado[notificadoPK=" + notificadoPK + "]";
    }

}
