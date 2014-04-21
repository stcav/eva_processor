/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.university.stcav.evaprocessor.model.event;

import java.util.List;

/**
 *
 * @author JoGa
 */
public class ElementLine {
    private List<Element> elements;
    private int seconds;
    private int pixels;
    private Element elementOverNow;
    private boolean flagOver;
    private boolean isDropOverNow;
    private int type;
    private boolean isMove;
    private int limXL;
    private int limXR;
    private int referencePoint;
    private int finalPoint;
    private int action;
    private int cursor;
    private boolean isClick;

    public ElementLine() {
    }

    public ElementLine(List<Element> elements, int seconds, int pixels, Element elementOverNow, boolean flagOver, boolean isDropOverNow, int type, boolean isMove, int limXL, int limXR, int referencePoint, int finalPoint, int action, int cursor, boolean isClick) {
        this.elements = elements;
        this.seconds = seconds;
        this.pixels = pixels;
        this.elementOverNow = elementOverNow;
        this.flagOver = flagOver;
        this.isDropOverNow = isDropOverNow;
        this.type = type;
        this.isMove = isMove;
        this.limXL = limXL;
        this.limXR = limXR;
        this.referencePoint = referencePoint;
        this.finalPoint = finalPoint;
        this.action = action;
        this.cursor = cursor;
        this.isClick = isClick;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public int getCursor() {
        return cursor;
    }

    public void setCursor(int cursor) {
        this.cursor = cursor;
    }

    public Element getElementOverNow() {
        return elementOverNow;
    }

    public void setElementOverNow(Element elementOverNow) {
        this.elementOverNow = elementOverNow;
    }

    public List<Element> getElements() {
        return elements;
    }

    public void setElements(List<Element> elements) {
        this.elements = elements;
    }

    public int getFinalPoint() {
        return finalPoint;
    }

    public void setFinalPoint(int finalPoint) {
        this.finalPoint = finalPoint;
    }

    public boolean isFlagOver() {
        return flagOver;
    }

    public void setFlagOver(boolean flagOver) {
        this.flagOver = flagOver;
    }

    public boolean isIsClick() {
        return isClick;
    }

    public void setIsClick(boolean isClick) {
        this.isClick = isClick;
    }

    public boolean isIsDropOverNow() {
        return isDropOverNow;
    }

    public void setIsDropOverNow(boolean isDropOverNow) {
        this.isDropOverNow = isDropOverNow;
    }

    public boolean isIsMove() {
        return isMove;
    }

    public void setIsMove(boolean isMove) {
        this.isMove = isMove;
    }

    public int getLimXL() {
        return limXL;
    }

    public void setLimXL(int limXL) {
        this.limXL = limXL;
    }

    public int getLimXR() {
        return limXR;
    }

    public void setLimXR(int limXR) {
        this.limXR = limXR;
    }

    public int getPixels() {
        return pixels;
    }

    public void setPixels(int pixels) {
        this.pixels = pixels;
    }

    public int getReferencePoint() {
        return referencePoint;
    }

    public void setReferencePoint(int referencePoint) {
        this.referencePoint = referencePoint;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


}
