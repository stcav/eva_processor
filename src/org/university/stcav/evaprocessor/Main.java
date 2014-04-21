/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.university.stcav.evaprocessor;

import com.google.gson.Gson;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.Vector;
import org.university.stcav.evaprocessor.model.Layout;
import org.university.stcav.evaprocessor.model.ProcessItem;
import org.university.stcav.evaprocessor.processor.FileProcessor;
import org.university.stcav.evaprocessor.processor.VideoProcessor;

/**
 *
 * @author johan
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException, IOException {



        ProcessItem pi;
        Gson gson = new Gson();
        Vector v = new Vector();
        ExecutorThread et;

        while (true) {
            try {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                if (FileProcessor.is_process(Layout.PATHSTACKVIDEOPROCESSOR + Layout.STACKVIDEOPROCESSOR)) {
                    pi = gson.fromJson(FileProcessor.get_first_line_file(Layout.PATHSTACKVIDEOPROCESSOR + Layout.STACKVIDEOPROCESSOR), ProcessItem.class);
                    FileProcessor.delete_first_line_file(Layout.PATHSTACKVIDEOPROCESSOR + Layout.STACKVIDEOPROCESSOR);
                    et = new ExecutorThread();
                    v.add(et);
                    ((ExecutorThread) v.firstElement()).iniciar(pi);
                }

            } catch (Exception ex) {
                System.out.println("ha ocurrido un error interno, la accion que lo origino sera transportada al ErrorStackVideoProcessor");
            }
        }
    }
}
