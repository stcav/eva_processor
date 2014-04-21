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
public class ContenidohasEtiquetasPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "Contenido_idContenido", nullable = false)
    private long contenidoidContenido;
    @Basic(optional = false)
    @Column(name = "Etiquetas_idEtiquetas", nullable = false)
    private long etiquetasidEtiquetas;

    public ContenidohasEtiquetasPK() {
    }

    public ContenidohasEtiquetasPK(long contenidoidContenido, long etiquetasidEtiquetas) {
        this.contenidoidContenido = contenidoidContenido;
        this.etiquetasidEtiquetas = etiquetasidEtiquetas;
    }

    public long getContenidoidContenido() {
        return contenidoidContenido;
    }

    public void setContenidoidContenido(long contenidoidContenido) {
        this.contenidoidContenido = contenidoidContenido;
    }

    public long getEtiquetasidEtiquetas() {
        return etiquetasidEtiquetas;
    }

    public void setEtiquetasidEtiquetas(long etiquetasidEtiquetas) {
        this.etiquetasidEtiquetas = etiquetasidEtiquetas;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) contenidoidContenido;
        hash += (int) etiquetasidEtiquetas;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ContenidohasEtiquetasPK)) {
            return false;
        }
        ContenidohasEtiquetasPK other = (ContenidohasEtiquetasPK) object;
        if (this.contenidoidContenido != other.contenidoidContenido) {
            return false;
        }
        if (this.etiquetasidEtiquetas != other.etiquetasidEtiquetas) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.university.stcav.evaprocessor.persistence.entities.ContenidohasEtiquetasPK[contenidoidContenido=" + contenidoidContenido + ", etiquetasidEtiquetas=" + etiquetasidEtiquetas + "]";
    }

}
