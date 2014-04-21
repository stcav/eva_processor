/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.university.stcav.evaprocessor.model;

import java.sql.Date;
import org.university.stcav.eva.model.MediaElement;
import org.university.stcav.eva.processor.Processor;

/**
 *
 * @author johan
 */
public class PerformanceElement {
    
    private int action;
    private long homeTime;
    private long endTime;
    private MediaElement me;

    public static int ADAPTCONTENTTOH264 = 1;
    public static int CUTVIDEO=2;
    public static int FADEOUT = 3;
    public static int FADE=4;
    public static int INSERTSILENCE=5;
    public static int CREATEVIDEOTOIMAGE=6;
    public static int INSERTTEXT=7;
    public static int TRANSCODETOPALVIDEO=8;
    public static int TRANSCODETOMP2=9;
    public static int AUDIOVIDEOESTOPES=10;
    
    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public PerformanceElement() {
    }

    public PerformanceElement(int action, long homeTime, long endTime, MediaElement me) {
        this.action = action;
        this.homeTime = homeTime;
        this.endTime = endTime;
        this.me = me;
    }


    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getHomeTime() {
        return homeTime;
    }

    public void setHomeTime(long homeTime) {
        this.homeTime = homeTime;
    }

    public MediaElement getMe() {
        return me;
    }

    public void setMe(MediaElement me) {
        this.me = me;
    }

    public Date get_process_date() {
        return new Date(homeTime);
    }

    public double get_process_time() {
        return (endTime - homeTime) * 1000;
    }

    public String get_codec_audio() {
        return me.getAudioElement().getCodec();
    }

    public String get_codec_video() {
        return me.getVideoElement().getCodec();
    }

    public String get_duration_me() {
        return me.getDuration();
    }

    public int get_duration_me_seconds() {
        return Processor.do_TimeToSeconds(me.getDuration());
    }
}
