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
@Table(name = "Tipo_Noticia", catalog = "stcav1", schema = "")
@NamedQueries({
    @NamedQuery(name = "TipoNoticia.findAll", query = "SELECT t FROM TipoNoticia t"),
    @NamedQuery(name = "TipoNoticia.findByIdTipoNoticia", query = "SELECT t FROM TipoNoticia t WHERE t.idTipoNoticia = :idTipoNoticia"),
    @NamedQuery(name = "TipoNoticia.findByDescripcion", query = "SELECT t FROM TipoNoticia t WHERE t.descripcion = :descripcion")})
public class TipoNoticia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idTipo_Noticia", nullable = false)
    private Long idTipoNoticia;
    @Column(name = "Descripcion", length = 255)
    private String descripcion;

    public TipoNoticia() {
    }

    public TipoNoticia(Long idTipoNoticia) {
        this.idTipoNoticia = idTipoNoticia;
    }

    public Long getIdTipoNoticia() {
        return idTipoNoticia;
    }

    public void setIdTipoNoticia(Long idTipoNoticia) {
        this.idTipoNoticia = idTipoNoticia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoNoticia != null ? idTipoNoticia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoNoticia)) {
            return false;
        }
        TipoNoticia other = (TipoNoticia) object;
        if ((this.idTipoNoticia == null && other.idTipoNoticia != null) || (this.idTipoNoticia != null && !this.idTipoNoticia.equals(other.idTipoNoticia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.university.stcav.evaprocessor.persistence.entities.TipoNoticia[idTipoNoticia=" + idTipoNoticia + "]";
    }

}
