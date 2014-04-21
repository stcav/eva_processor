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
public class ComunidadhasContenidoPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "Comunidad_idComunidad", nullable = false)
    private long comunidadidComunidad;
    @Basic(optional = false)
    @Column(name = "Contenido_idContenido", nullable = false)
    private long contenidoidContenido;

    public ComunidadhasContenidoPK() {
    }

    public ComunidadhasContenidoPK(long comunidadidComunidad, long contenidoidContenido) {
        this.comunidadidComunidad = comunidadidComunidad;
        this.contenidoidContenido = contenidoidContenido;
    }

    public long getComunidadidComunidad() {
        return comunidadidComunidad;
    }

    public void setComunidadidComunidad(long comunidadidComunidad) {
        this.comunidadidComunidad = comunidadidComunidad;
    }

    public long getContenidoidContenido() {
        return contenidoidContenido;
    }

    public void setContenidoidContenido(long contenidoidContenido) {
        this.contenidoidContenido = contenidoidContenido;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) comunidadidComunidad;
        hash += (int) contenidoidContenido;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ComunidadhasContenidoPK)) {
            return false;
        }
        ComunidadhasContenidoPK other = (ComunidadhasContenidoPK) object;
        if (this.comunidadidComunidad != other.comunidadidComunidad) {
            return false;
        }
        if (this.contenidoidContenido != other.contenidoidContenido) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.university.stcav.evaprocessor.persistence.entities.ComunidadhasContenidoPK[comunidadidComunidad=" + comunidadidComunidad + ", contenidoidContenido=" + contenidoidContenido + "]";
    }

}
