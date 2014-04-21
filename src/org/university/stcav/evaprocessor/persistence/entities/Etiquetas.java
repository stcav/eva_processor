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
@Table(name = "Etiquetas", catalog = "stcav1", schema = "")
@NamedQueries({
    @NamedQuery(name = "Etiquetas.findAll", query = "SELECT e FROM Etiquetas e"),
    @NamedQuery(name = "Etiquetas.findByIdEtiquetas", query = "SELECT e FROM Etiquetas e WHERE e.idEtiquetas = :idEtiquetas"),
    @NamedQuery(name = "Etiquetas.findByNombre", query = "SELECT e FROM Etiquetas e WHERE e.nombre = :nombre")})
public class Etiquetas implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idEtiquetas", nullable = false)
    private Long idEtiquetas;
    @Column(name = "Nombre", length = 255)
    private String nombre;

    public Etiquetas() {
    }

    public Etiquetas(Long idEtiquetas) {
        this.idEtiquetas = idEtiquetas;
    }

    public Long getIdEtiquetas() {
        return idEtiquetas;
    }

    public void setIdEtiquetas(Long idEtiquetas) {
        this.idEtiquetas = idEtiquetas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEtiquetas != null ? idEtiquetas.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Etiquetas)) {
            return false;
        }
        Etiquetas other = (Etiquetas) object;
        if ((this.idEtiquetas == null && other.idEtiquetas != null) || (this.idEtiquetas != null && !this.idEtiquetas.equals(other.idEtiquetas))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.university.stcav.evaprocessor.persistence.entities.Etiquetas[idEtiquetas=" + idEtiquetas + "]";
    }

}
