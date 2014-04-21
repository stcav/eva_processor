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
@Table(name = "Contenido_has_Etiquetas", catalog = "stcav1", schema = "")
@NamedQueries({
    @NamedQuery(name = "ContenidohasEtiquetas.findAll", query = "SELECT c FROM ContenidohasEtiquetas c"),
    @NamedQuery(name = "ContenidohasEtiquetas.findByContenidoidContenido", query = "SELECT c FROM ContenidohasEtiquetas c WHERE c.contenidohasEtiquetasPK.contenidoidContenido = :contenidoidContenido"),
    @NamedQuery(name = "ContenidohasEtiquetas.findByEtiquetasidEtiquetas", query = "SELECT c FROM ContenidohasEtiquetas c WHERE c.contenidohasEtiquetasPK.etiquetasidEtiquetas = :etiquetasidEtiquetas")})
public class ContenidohasEtiquetas implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ContenidohasEtiquetasPK contenidohasEtiquetasPK;

    public ContenidohasEtiquetas() {
    }

    public ContenidohasEtiquetas(ContenidohasEtiquetasPK contenidohasEtiquetasPK) {
        this.contenidohasEtiquetasPK = contenidohasEtiquetasPK;
    }

    public ContenidohasEtiquetas(long contenidoidContenido, long etiquetasidEtiquetas) {
        this.contenidohasEtiquetasPK = new ContenidohasEtiquetasPK(contenidoidContenido, etiquetasidEtiquetas);
    }

    public ContenidohasEtiquetasPK getContenidohasEtiquetasPK() {
        return contenidohasEtiquetasPK;
    }

    public void setContenidohasEtiquetasPK(ContenidohasEtiquetasPK contenidohasEtiquetasPK) {
        this.contenidohasEtiquetasPK = contenidohasEtiquetasPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (contenidohasEtiquetasPK != null ? contenidohasEtiquetasPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ContenidohasEtiquetas)) {
            return false;
        }
        ContenidohasEtiquetas other = (ContenidohasEtiquetas) object;
        if ((this.contenidohasEtiquetasPK == null && other.contenidohasEtiquetasPK != null) || (this.contenidohasEtiquetasPK != null && !this.contenidohasEtiquetasPK.equals(other.contenidohasEtiquetasPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.university.stcav.evaprocessor.persistence.entities.ContenidohasEtiquetas[contenidohasEtiquetasPK=" + contenidohasEtiquetasPK + "]";
    }

}
