/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.university.stcav.evaprocessor.processor;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import org.university.stcav.evaprocessor.model.ProcessorResponse;


/**
 *
 * @author johan
 *
 */
public class ProcessExecutor {

    public static ProcessorResponse execute_process(String[] comando, String path, boolean debug) throws InterruptedException, IOException {
        String s = null;
        File f = new File(path);
        ProcessorResponse pr = new ProcessorResponse();

        try {
            // Ejcutamos el comando

            Process p = Runtime.getRuntime().exec(comando, null, f);
            p.waitFor();
            //System.out.println(p.waitFor());
            
            System.out.println("Comando: " + comando + " \n");

            BufferedReader stdOut = new BufferedReader(new InputStreamReader(
                    p.getInputStream()));

            BufferedReader stdError = new BufferedReader(new InputStreamReader(
                    p.getErrorStream()));

            // Leemos la salida del comando  
            //System.out.println("Ésta es la salida standard del comando:\n");
            /*while ((s = stdOut.readLine()) != null) {
                if (debug) {
                    System.out.println("+" + s);
                }
                pr.getStdout().add(s);

            }
            // Leemos los errores si los hubiera
            //System.out.println("Ésta es la salida standard de error del comando (si la hay):\n");
            while ((s = stdError.readLine()) != null) {
                if (debug) {
                    System.out.println("-" + s);
                }
                pr.getStderror().add(s);
            }
            System.out.println("****************************************************************************");*/

            return pr;

        } catch (IOException e) {
            System.out.println("Excepción: ");
            e.printStackTrace();
            return null;
        }

    }
}
