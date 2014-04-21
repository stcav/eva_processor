/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.university.stcav.evaprocessor.model;

/**
 *
 * @author johan
 */
public class ProcessItem {
    private int type;// 1 -> contenido, 2-> evento
    private int action;
    private long id;
    private String src;
    private int priority;

    //Constantes de edicion
    public static int CONTENIDO=1;
    public static int EVENTO=2;
    public static int ADAPTTOHDH264=1;
    public static int DESCRIPTORREADER=2;

    public ProcessItem() {
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public ProcessItem(int type, int action, long id, String src, int priority) {
        this.type = type;
        this.action = action;
        this.id = id;
        this.src = src;
        this.priority = priority;
    }

    public ProcessItem(int type, long id, String src, int priority) {
        this.type = type;
        this.id = id;
        this.src = src;
        this.priority = priority;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
