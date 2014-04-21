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
public class ComunidadhasEtiquetasPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "Comunidad_idComunidad", nullable = false)
    private long comunidadidComunidad;
    @Basic(optional = false)
    @Column(name = "Etiquetas_idEtiquetas", nullable = false)
    private long etiquetasidEtiquetas;

    public ComunidadhasEtiquetasPK() {
    }

    public ComunidadhasEtiquetasPK(long comunidadidComunidad, long etiquetasidEtiquetas) {
        this.comunidadidComunidad = comunidadidComunidad;
        this.etiquetasidEtiquetas = etiquetasidEtiquetas;
    }

    public long getComunidadidComunidad() {
        return comunidadidComunidad;
    }

    public void setComunidadidComunidad(long comunidadidComunidad) {
        this.comunidadidComunidad = comunidadidComunidad;
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
        hash += (int) comunidadidComunidad;
        hash += (int) etiquetasidEtiquetas;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ComunidadhasEtiquetasPK)) {
            return false;
        }
        ComunidadhasEtiquetasPK other = (ComunidadhasEtiquetasPK) object;
        if (this.comunidadidComunidad != other.comunidadidComunidad) {
            return false;
        }
        if (this.etiquetasidEtiquetas != other.etiquetasidEtiquetas) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.university.stcav.evaprocessor.persistence.entities.ComunidadhasEtiquetasPK[comunidadidComunidad=" + comunidadidComunidad + ", etiquetasidEtiquetas=" + etiquetasidEtiquetas + "]";
    }

}
