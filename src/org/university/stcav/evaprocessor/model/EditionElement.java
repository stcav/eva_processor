/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.university.stcav.evaprocessor.model;

/**
 *
 * @author johan
 */
public class EditionElement {
    private int id;
    private int idContent;
    private int duration;
    private float cut_home;
    private float cut_end;
    private int idTransition;
    private String src;

    public EditionElement() {
    }

    public EditionElement(int id, int idContent, int duration, float cut_home, float cut_end, int idTransition, String src) {
        this.id = id;
        this.idContent = idContent;
        this.duration = duration;
        this.cut_home = cut_home;
        this.cut_end = cut_end;
        this.idTransition = idTransition;
        this.src = src;
    }

    public float getCut_end() {
        return cut_end;
    }

    public void setCut_end(float cut_end) {
        this.cut_end = cut_end;
    }

    public float getCut_home() {
        return cut_home;
    }

    public void setCut_home(float cut_home) {
        this.cut_home = cut_home;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdContent() {
        return idContent;
    }

    public void setIdContent(int idContent) {
        this.idContent = idContent;
    }

    public int getIdTransition() {
        return idTransition;
    }

    public void setIdTransition(int idTransition) {
        this.idTransition = idTransition;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

}
