/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.university.stcav.evaprocessor.persistence.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author stcav
 */
@Entity
@Table(name = "Votacion", catalog = "stcav1", schema = "")
@NamedQueries({
    @NamedQuery(name = "Votacion.findAll", query = "SELECT v FROM Votacion v"),
    @NamedQuery(name = "Votacion.findByIdVotacion", query = "SELECT v FROM Votacion v WHERE v.idVotacion = :idVotacion"),
    @NamedQuery(name = "Votacion.findByValoracionidValoracion", query = "SELECT v FROM Votacion v WHERE v.valoracionidValoracion = :valoracionidValoracion"),
    @NamedQuery(name = "Votacion.findByRespuesta", query = "SELECT v FROM Votacion v WHERE v.respuesta = :respuesta")})
public class Votacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idVotacion", nullable = false)
    private Long idVotacion;
    @Basic(optional = false)
    @Column(name = "Valoracion_idValoracion", nullable = false)
    private long valoracionidValoracion;
    @Column(name = "Respuesta", length = 2)
    private String respuesta;

    public Votacion() {
    }

    public Votacion(Long idVotacion) {
        this.idVotacion = idVotacion;
    }

    public Votacion(Long idVotacion, long valoracionidValoracion) {
        this.idVotacion = idVotacion;
        this.valoracionidValoracion = valoracionidValoracion;
    }

    public Long getIdVotacion() {
        return idVotacion;
    }

    public void setIdVotacion(Long idVotacion) {
        this.idVotacion = idVotacion;
    }

    public long getValoracionidValoracion() {
        return valoracionidValoracion;
    }

    public void setValoracionidValoracion(long valoracionidValoracion) {
        this.valoracionidValoracion = valoracionidValoracion;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVotacion != null ? idVotacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Votacion)) {
            return false;
        }
        Votacion other = (Votacion) object;
        if ((this.idVotacion == null && other.idVotacion != null) || (this.idVotacion != null && !this.idVotacion.equals(other.idVotacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.university.stcav.evaprocessor.persistence.entities.Votacion[idVotacion=" + idVotacion + "]";
    }

}
