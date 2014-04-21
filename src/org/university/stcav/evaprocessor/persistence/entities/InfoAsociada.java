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
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author stcav
 */
@Entity
@Table(name = "Info_Asociada", catalog = "stcav1", schema = "")
@NamedQueries({
    @NamedQuery(name = "InfoAsociada.findAll", query = "SELECT i FROM InfoAsociada i"),
    @NamedQuery(name = "InfoAsociada.findByIdInfoAsociada", query = "SELECT i FROM InfoAsociada i WHERE i.idInfoAsociada = :idInfoAsociada"),
    @NamedQuery(name = "InfoAsociada.findByEventoidEvento", query = "SELECT i FROM InfoAsociada i WHERE i.eventoidEvento = :eventoidEvento"),
    @NamedQuery(name = "InfoAsociada.findByTiempodespliegue", query = "SELECT i FROM InfoAsociada i WHERE i.tiempodespliegue = :tiempodespliegue"),
    @NamedQuery(name = "InfoAsociada.findByDuracion", query = "SELECT i FROM InfoAsociada i WHERE i.duracion = :duracion")})
public class InfoAsociada implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idInfo_Asociada", nullable = false)
    private Long idInfoAsociada;
    @Basic(optional = false)
    @Column(name = "Evento_idEvento", nullable = false)
    private long eventoidEvento;
    @Lob
    @Column(name = "Texto", length = 65535)
    private String texto;
    @Column(name = "Tiempo_despliegue")
    private Integer tiempodespliegue;
    @Column(name = "Duracion")
    private Integer duracion;

    public InfoAsociada() {
    }

    public InfoAsociada(Long idInfoAsociada) {
        this.idInfoAsociada = idInfoAsociada;
    }

    public InfoAsociada(Long idInfoAsociada, long eventoidEvento) {
        this.idInfoAsociada = idInfoAsociada;
        this.eventoidEvento = eventoidEvento;
    }

    public Long getIdInfoAsociada() {
        return idInfoAsociada;
    }

    public void setIdInfoAsociada(Long idInfoAsociada) {
        this.idInfoAsociada = idInfoAsociada;
    }

    public long getEventoidEvento() {
        return eventoidEvento;
    }

    public void setEventoidEvento(long eventoidEvento) {
        this.eventoidEvento = eventoidEvento;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Integer getTiempodespliegue() {
        return tiempodespliegue;
    }

    public void setTiempodespliegue(Integer tiempodespliegue) {
        this.tiempodespliegue = tiempodespliegue;
    }

    public Integer getDuracion() {
        return duracion;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idInfoAsociada != null ? idInfoAsociada.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InfoAsociada)) {
            return false;
        }
        InfoAsociada other = (InfoAsociada) object;
        if ((this.idInfoAsociada == null && other.idInfoAsociada != null) || (this.idInfoAsociada != null && !this.idInfoAsociada.equals(other.idInfoAsociada))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.university.stcav.evaprocessor.persistence.entities.InfoAsociada[idInfoAsociada=" + idInfoAsociada + "]";
    }

}
