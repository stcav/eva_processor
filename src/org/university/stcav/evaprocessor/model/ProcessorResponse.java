/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.university.stcav.evaprocessor.model;

import java.io.BufferedReader;
import java.util.Vector;

/**
 *
 * @author johan
 */
public class ProcessorResponse {
    private Vector<String> stdout;
    private Vector<String> stderror;

    public ProcessorResponse() {
        stderror = new Vector<String>();
        stdout =new Vector<String>();
    }

    public ProcessorResponse(Vector<String> stdout, Vector<String> stderror) {
        this.stdout = stdout;
        this.stderror = stderror;
    }

    public Vector<String> getStderror() {
        return stderror;
    }

    public void setStderror(Vector<String> stderror) {
        this.stderror = stderror;
    }

    public Vector<String> getStdout() {
        return stdout;
    }

    public void setStdout(Vector<String> stdout) {
        this.stdout = stdout;
    }




    
}
