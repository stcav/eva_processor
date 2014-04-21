/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.university.stcav.evaprocessor.model.event;

/**
 *
 * @author JoGa
 */
public class Descriptor {
    private ElementLine lc;
    private ElementLine lt;
    private ElementLine li;

    public Descriptor() {
    }

    public Descriptor(ElementLine lc, ElementLine lt, ElementLine li) {
        this.lc = lc;
        this.lt = lt;
        this.li = li;
    }

    public ElementLine getLc() {
        return lc;
    }

    public void setLc(ElementLine lc) {
        this.lc = lc;
    }

    public ElementLine getLi() {
        return li;
    }

    public void setLi(ElementLine li) {
        this.li = li;
    }

    public ElementLine getLt() {
        return lt;
    }

    public void setLt(ElementLine lt) {
        this.lt = lt;
    }

}
