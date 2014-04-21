/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.university.stcav.evaprocessor.persistence.entities;

import java.io.Serializable;
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
@Table(name = "Programa", catalog = "stcav1", schema = "")
@NamedQueries({
    @NamedQuery(name = "Programa.findAll", query = "SELECT p FROM Programa p"),
    @NamedQuery(name = "Programa.findByIdPrograma", query = "SELECT p FROM Programa p WHERE p.idPrograma = :idPrograma"),
    @NamedQuery(name = "Programa.findByComunidadidComunidad", query = "SELECT p FROM Programa p WHERE p.comunidadidComunidad = :comunidadidComunidad"),
    @NamedQuery(name = "Programa.findByNombre", query = "SELECT p FROM Programa p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "Programa.findByDia", query = "SELECT p FROM Programa p WHERE p.dia = :dia"),
    @NamedQuery(name = "Programa.findByHora", query = "SELECT p FROM Programa p WHERE p.hora = :hora")})
public class Programa implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idPrograma", nullable = false)
    private Long idPrograma;
    @Basic(optional = false)
    @Column(name = "Comunidad_idComunidad", nullable = false)
    private long comunidadidComunidad;
    @Column(name = "Nombre", length = 255)
    private String nombre;
    @Column(name = "Dia", length = 2)
    private String dia;
    @Column(name = "Hora")
    @Temporal(TemporalType.TIME)
    private Date hora;
    @Lob
    @Column(name = "Descripcion", length = 65535)
    private String descripcion;
    @Lob
    @Column(name = "Ruta_screenshot", length = 65535)
    private String rutascreenshot;

    public Programa() {
    }

    public Programa(Long idPrograma) {
        this.idPrograma = idPrograma;
    }

    public Programa(Long idPrograma, long comunidadidComunidad) {
        this.idPrograma = idPrograma;
        this.comunidadidComunidad = comunidadidComunidad;
    }

    public Long getIdPrograma() {
        return idPrograma;
    }

    public void setIdPrograma(Long idPrograma) {
        this.idPrograma = idPrograma;
    }

    public long getComunidadidComunidad() {
        return comunidadidComunidad;
    }

    public void setComunidadidComunidad(long comunidadidComunidad) {
        this.comunidadidComunidad = comunidadidComunidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getRutascreenshot() {
        return rutascreenshot;
    }

    public void setRutascreenshot(String rutascreenshot) {
        this.rutascreenshot = rutascreenshot;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPrograma != null ? idPrograma.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Programa)) {
            return false;
        }
        Programa other = (Programa) object;
        if ((this.idPrograma == null && other.idPrograma != null) || (this.idPrograma != null && !this.idPrograma.equals(other.idPrograma))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.university.stcav.evaprocessor.persistence.entities.Programa[idPrograma=" + idPrograma + "]";
    }

}
