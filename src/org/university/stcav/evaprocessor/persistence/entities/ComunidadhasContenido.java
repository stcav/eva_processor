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
@Table(name = "Comunidad_has_Contenido", catalog = "stcav1", schema = "")
@NamedQueries({
    @NamedQuery(name = "ComunidadhasContenido.findAll", query = "SELECT c FROM ComunidadhasContenido c"),
    @NamedQuery(name = "ComunidadhasContenido.findByComunidadidComunidad", query = "SELECT c FROM ComunidadhasContenido c WHERE c.comunidadhasContenidoPK.comunidadidComunidad = :comunidadidComunidad"),
    @NamedQuery(name = "ComunidadhasContenido.findByContenidoidContenido", query = "SELECT c FROM ComunidadhasContenido c WHERE c.comunidadhasContenidoPK.contenidoidContenido = :contenidoidContenido")})
public class ComunidadhasContenido implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ComunidadhasContenidoPK comunidadhasContenidoPK;

    public ComunidadhasContenido() {
    }

    public ComunidadhasContenido(ComunidadhasContenidoPK comunidadhasContenidoPK) {
        this.comunidadhasContenidoPK = comunidadhasContenidoPK;
    }

    public ComunidadhasContenido(long comunidadidComunidad, long contenidoidContenido) {
        this.comunidadhasContenidoPK = new ComunidadhasContenidoPK(comunidadidComunidad, contenidoidContenido);
    }

    public ComunidadhasContenidoPK getComunidadhasContenidoPK() {
        return comunidadhasContenidoPK;
    }

    public void setComunidadhasContenidoPK(ComunidadhasContenidoPK comunidadhasContenidoPK) {
        this.comunidadhasContenidoPK = comunidadhasContenidoPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (comunidadhasContenidoPK != null ? comunidadhasContenidoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ComunidadhasContenido)) {
            return false;
        }
        ComunidadhasContenido other = (ComunidadhasContenido) object;
        if ((this.comunidadhasContenidoPK == null && other.comunidadhasContenidoPK != null) || (this.comunidadhasContenidoPK != null && !this.comunidadhasContenidoPK.equals(other.comunidadhasContenidoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.university.stcav.evaprocessor.persistence.entities.ComunidadhasContenido[comunidadhasContenidoPK=" + comunidadhasContenidoPK + "]";
    }

}
