/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.university.stcav.evaprocessor;

import com.google.gson.Gson;
import java.sql.Date;
import java.sql.Time;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.university.stcav.evaprocessor.model.Layout;
import org.university.stcav.evaprocessor.model.ProcessItem;
import org.university.stcav.evaprocessor.processor.FileProcessor;
import org.university.stcav.evaprocessor.processor.VideoProcessor;

/**
 *
 * @author stcav
 */
public class ExecutorThread implements Runnable {

    private Thread hilo;
    private ProcessItem pi;

    public ExecutorThread() {
    }

    public void iniciar(ProcessItem pi_) {
        pi = pi_;
        hilo = new Thread(this);
        hilo.start();
    }

    public void run() {
        try {
            System.out.println(pi.getId() + " " + System.currentTimeMillis());
            switch (pi.getType()) {
                case 1: {//Contenido
                    switch (pi.getAction()) {
                        case 1: {
                            VideoProcessor.content_adapter_HD(pi);
                            break;
                        }
                        case 2: {
                            VideoProcessor.content_descriptor_processor(pi);
                            break;
                        }
                        default: {
                            break;
                        }
                    }
                    break;
                }
                case 2: {//Evento
                    switch (pi.getAction()) {
                        case 1: {
                            break;
                        }
                        case 2: {
                            VideoProcessor.event_descriptor_processor(pi);
                            break;
                        }
                        default: {
                            break;
                        }
                    }
                    break;
                }
                default: {
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("ha ocurrido un error interno, la accion que lo origino sera transportada al ErrorStackVideoProcessor");
            e.printStackTrace();
            String errorLine = new Gson().toJson(pi);
            FileProcessor.do_file_write(Layout.PATHSTACKVIDEOPROCESSOR + Layout.ERRORSTACKVIDEOPROCESSOR, new Time(System.currentTimeMillis())+" "+new Date(System.currentTimeMillis())+ "*" + errorLine);
            System.out.println("_______________________________________________________________________________________________________________________________________________________________________________________");
        }

    }
}
