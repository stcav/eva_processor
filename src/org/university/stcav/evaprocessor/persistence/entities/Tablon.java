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
@Table(name = "Tablon", catalog = "stcav1", schema = "")
@NamedQueries({
    @NamedQuery(name = "Tablon.findAll", query = "SELECT t FROM Tablon t"),
    @NamedQuery(name = "Tablon.findByIdTablon", query = "SELECT t FROM Tablon t WHERE t.idTablon = :idTablon"),
    @NamedQuery(name = "Tablon.findByMensaje", query = "SELECT t FROM Tablon t WHERE t.mensaje = :mensaje"),
    @NamedQuery(name = "Tablon.findByEstampa", query = "SELECT t FROM Tablon t WHERE t.estampa = :estampa"),
    @NamedQuery(name = "Tablon.findByUsuarioidUsuario", query = "SELECT t FROM Tablon t WHERE t.usuarioidUsuario = :usuarioidUsuario"),
    @NamedQuery(name = "Tablon.findByComunidadidComunidad", query = "SELECT t FROM Tablon t WHERE t.comunidadidComunidad = :comunidadidComunidad")})
public class Tablon implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idTablon", nullable = false)
    private Long idTablon;
    @Column(name = "Mensaje", length = 255)
    private String mensaje;
    @Basic(optional = false)
    @Column(name = "Estampa", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date estampa;
    @Basic(optional = false)
    @Column(name = "Usuario_idUsuario", nullable = false)
    private long usuarioidUsuario;
    @Basic(optional = false)
    @Column(name = "Comunidad_idComunidad", nullable = false)
    private long comunidadidComunidad;
    @Lob
    @Column(name = "Foto", length = 65535)
    private String foto;

    public Tablon() {
    }

    public Tablon(Long idTablon) {
        this.idTablon = idTablon;
    }

    public Tablon(Long idTablon, Date estampa, long usuarioidUsuario, long comunidadidComunidad) {
        this.idTablon = idTablon;
        this.estampa = estampa;
        this.usuarioidUsuario = usuarioidUsuario;
        this.comunidadidComunidad = comunidadidComunidad;
    }

    public Long getIdTablon() {
        return idTablon;
    }

    public void setIdTablon(Long idTablon) {
        this.idTablon = idTablon;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Date getEstampa() {
        return estampa;
    }

    public void setEstampa(Date estampa) {
        this.estampa = estampa;
    }

    public long getUsuarioidUsuario() {
        return usuarioidUsuario;
    }

    public void setUsuarioidUsuario(long usuarioidUsuario) {
        this.usuarioidUsuario = usuarioidUsuario;
    }

    public long getComunidadidComunidad() {
        return comunidadidComunidad;
    }

    public void setComunidadidComunidad(long comunidadidComunidad) {
        this.comunidadidComunidad = comunidadidComunidad;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTablon != null ? idTablon.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tablon)) {
            return false;
        }
        Tablon other = (Tablon) object;
        if ((this.idTablon == null && other.idTablon != null) || (this.idTablon != null && !this.idTablon.equals(other.idTablon))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.university.stcav.evaprocessor.persistence.entities.Tablon[idTablon=" + idTablon + "]";
    }

}
