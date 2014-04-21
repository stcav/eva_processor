/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.university.stcav.evaprocessor.persistence.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author stcav
 */
@Entity
@Table(name = "Evento", catalog = "stcav1", schema = "")
@NamedQueries({
    @NamedQuery(name = "Evento.findAll", query = "SELECT e FROM Evento e"),
    @NamedQuery(name = "Evento.findByIdEvento", query = "SELECT e FROM Evento e WHERE e.idEvento = :idEvento"),
    @NamedQuery(name = "Evento.findByProgramaidPrograma", query = "SELECT e FROM Evento e WHERE e.programaidPrograma = :programaidPrograma"),
    @NamedQuery(name = "Evento.findByNombre", query = "SELECT e FROM Evento e WHERE e.nombre = :nombre"),
    @NamedQuery(name = "Evento.findByFechacreacion", query = "SELECT e FROM Evento e WHERE e.fechacreacion = :fechacreacion"),
    @NamedQuery(name = "Evento.findByEstado", query = "SELECT e FROM Evento e WHERE e.estado = :estado"),
    @NamedQuery(name = "Evento.findByDuracion", query = "SELECT e FROM Evento e WHERE e.duracion = :duracion")})
public class Evento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idEvento", nullable = false)
    private Long idEvento;
    @Basic(optional = false)
    @Column(name = "Programa_idPrograma", nullable = false)
    private long programaidPrograma;
    @Column(name = "Nombre", length = 255)
    private String nombre;
    @Lob
    @Column(name = "Descripcion", length = 65535)
    private String descripcion;
    @Column(name = "Fecha_creacion")
    @Temporal(TemporalType.DATE)
    private Date fechacreacion;
    @Column(name = "Estado", length = 13)
    private String estado;
    @Lob
    @Column(name = "Descriptor", length = 65535)
    private String descriptor;
    @Column(name = "Duracion", precision = 17, scale = 3)
    private BigDecimal duracion;
    @Lob
    @Column(name = "Ruta", length = 65535)
    private String ruta;

    public Evento() {
    }

    public Evento(Long idEvento) {
        this.idEvento = idEvento;
    }

    public Evento(Long idEvento, long programaidPrograma) {
        this.idEvento = idEvento;
        this.programaidPrograma = programaidPrograma;
    }

    public Long getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Long idEvento) {
        this.idEvento = idEvento;
    }

    public long getProgramaidPrograma() {
        return programaidPrograma;
    }

    public void setProgramaidPrograma(long programaidPrograma) {
        this.programaidPrograma = programaidPrograma;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechacreacion() {
        return fechacreacion;
    }

    public void setFechacreacion(Date fechacreacion) {
        this.fechacreacion = fechacreacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDescriptor() {
        return descriptor;
    }

    public void setDescriptor(String descriptor) {
        this.descriptor = descriptor;
    }

    public BigDecimal getDuracion() {
        return duracion;
    }

    public void setDuracion(BigDecimal duracion) {
        this.duracion = duracion;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEvento != null ? idEvento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Evento)) {
            return false;
        }
        Evento other = (Evento) object;
        if ((this.idEvento == null && other.idEvento != null) || (this.idEvento != null && !this.idEvento.equals(other.idEvento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.university.stcav.evaprocessor.persistence.entities.Evento[idEvento=" + idEvento + "]";
    }

}
