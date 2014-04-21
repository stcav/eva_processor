/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.university.stcav.evaprocessor.model;

/**
 *
 * @author johan
 */
public class Color {
    private Long idColor;
    private String nombre;
    private String codigoRGB;

    public Color() {
    }

    public String getCodigoRGB() {
        return codigoRGB;
    }

    public void setCodigoRGB(String codigoRGB) {
        this.codigoRGB = codigoRGB;
    }

    public Long getIdColor() {
        return idColor;
    }

    public void setIdColor(Long idColor) {
        this.idColor = idColor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    
}
