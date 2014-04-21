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
@Table(name = "Intereses", catalog = "stcav1", schema = "")
@NamedQueries({
    @NamedQuery(name = "Intereses.findAll", query = "SELECT i FROM Intereses i"),
    @NamedQuery(name = "Intereses.findByIdIntereses", query = "SELECT i FROM Intereses i WHERE i.idIntereses = :idIntereses"),
    @NamedQuery(name = "Intereses.findByNombre", query = "SELECT i FROM Intereses i WHERE i.nombre = :nombre")})
public class Intereses implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idIntereses", nullable = false)
    private Long idIntereses;
    @Column(name = "Nombre", length = 255)
    private String nombre;

    public Intereses() {
    }

    public Intereses(Long idIntereses) {
        this.idIntereses = idIntereses;
    }

    public Long getIdIntereses() {
        return idIntereses;
    }

    public void setIdIntereses(Long idIntereses) {
        this.idIntereses = idIntereses;
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
        hash += (idIntereses != null ? idIntereses.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Intereses)) {
            return false;
        }
        Intereses other = (Intereses) object;
        if ((this.idIntereses == null && other.idIntereses != null) || (this.idIntereses != null && !this.idIntereses.equals(other.idIntereses))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.university.stcav.evaprocessor.persistence.entities.Intereses[idIntereses=" + idIntereses + "]";
    }

}
