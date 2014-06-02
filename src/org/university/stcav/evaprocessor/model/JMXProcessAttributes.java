/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.university.stcav.evaprocessor.model;

import java.util.HashMap;

/**
 *
 * @author johaned
 */
public class JMXProcessAttributes {
    private String vCodec;
    private String aCodec;
    private String resolution;
    private int duration;
    private Double processDuration;
    private int bitrate;
    HashMap <String, Object> attributes;

    public JMXProcessAttributes(String vCodec, String aCodec, String resolution, int duration, Double processDuration, int bitrate) {
        this.vCodec = vCodec;
        this.aCodec = aCodec;
        this.resolution = resolution;
        this.duration = duration;
        this.processDuration = processDuration;
        this.bitrate = bitrate;
        attributes = new HashMap<String, Object>();
    }
    
    public HashMap<String, Object> getAttributes(){
        attributes.clear();
        attributes.put("VCodec", this.vCodec);
        attributes.put("ACodec", this.aCodec);
        attributes.put("Resolution", this.resolution);
        attributes.put("Duration", this.duration);
        attributes.put("ProcessDuration", this.processDuration);
        attributes.put("Bitrate", this.bitrate);
        return attributes;
    }

    public String getvCodec() {
        return vCodec;
    }

    public String getaCodec() {
        return aCodec;
    }

    public String getResolution() {
        return resolution;
    }

    public int getDuration() {
        return duration;
    }

    public Double getProcessDuration() {
        return processDuration;
    }

    public int getBitrate() {
        return bitrate;
    }

    public void setvCodec(String vCodec) {
        this.vCodec = vCodec;
    }

    public void setaCodec(String aCodec) {
        this.aCodec = aCodec;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setProcessDuration(Double processDuration) {
        this.processDuration = processDuration;
    }

    public void setBitrate(int bitrate) {
        this.bitrate = bitrate;
    }
    
}
