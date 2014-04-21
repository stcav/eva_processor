/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.university.stcav.evaprocessor.model.event;

/**
 *
 * @author JoGa
 */
public class Element {
    private String id;
    private String home;
    private String end;
    private String type;
    private String duration;
    private boolean isMovable;
    private boolean isResizable;
    private boolean availableTransition;
    private int homeTransition;
    private int endTransition;
    private String color;
    private String font;
    private String text;

    public Element() {
    }

    public Element(String id, String home, String end, String type, String duration, boolean isMovable, boolean isResizable, boolean availableTransition, int homeTransition, int endTransition, String color, String font, String text) {
        this.id = id;
        this.home = home;
        this.end = end;
        this.type = type;
        this.duration = duration;
        this.isMovable = isMovable;
        this.isResizable = isResizable;
        this.availableTransition = availableTransition;
        this.homeTransition = homeTransition;
        this.endTransition = endTransition;
        this.color = color;
        this.font = font;
        this.text = text;
    }

    
    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public boolean isAvailableTransition() {
        return availableTransition;
    }

    public void setAvailableTransition(boolean availableTransition) {
        this.availableTransition = availableTransition;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public int getEndTransition() {
        return endTransition;
    }

    public void setEndTransition(int endTransition) {
        this.endTransition = endTransition;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public int getHomeTransition() {
        return homeTransition;
    }

    public void setHomeTransition(int homeTransition) {
        this.homeTransition = homeTransition;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isIsMovable() {
        return isMovable;
    }

    public void setIsMovable(boolean isMovable) {
        this.isMovable = isMovable;
    }

    public boolean isIsResizable() {
        return isResizable;
    }

    public void setIsResizable(boolean isResizable) {
        this.isResizable = isResizable;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    
}

