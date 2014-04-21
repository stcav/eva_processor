/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.university.stcav.evaprocessor.processor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.Vector;

/**
 *
 * @author johan
 */
public class FileProcessor {

    public static void do_file_write(String name, String line) {
        //nombre es la ruta y nombre del archivo sobre el que deseamos trabajar
        //linea es la cadena que deseamos introducir en nuestro archivo
        File archivo = new File(name);
        try {
            FileWriter escribirArchivo = new FileWriter(archivo, true);
            //true para no modificar lo que ya estaba en el archivo, false para hacer lo contrario
            BufferedWriter buffer = new BufferedWriter(escribirArchivo);
            buffer.write(line);
            buffer.newLine();
            buffer.close();//buena practica cerrar para liberar memoria
        } catch (Exception ex) {
            System.out.println("Error:" + ex);/*en caso de haber error aqui se mostrarÃ¡;
            aunque puedes mostrarlo con una ventana emergente o como quieras*/
        }
    }

    public static Vector<String> do_read_file(String name) {
        //nombre es la ruta y nombre del archivo sobre el que deseamos trabajar
        Vector<String> returned = new Vector<String>();
        String line = "";
        File archivo = new File(name);
        try {
            FileReader leerArchivo = new FileReader(archivo);
            BufferedReader buffer = new BufferedReader(leerArchivo);
            while ((line = buffer.readLine()) != null) {
                returned.add(line);
            }
            buffer.close();
        } catch (Exception ex) {
            System.out.println("Error:" + ex);
        }
        return returned;
    }

    public static String get_first_line_file(String name) {
        String line = "";
        File archivo = new File(name);
        try {
            FileReader filereader = new FileReader(archivo);
            BufferedReader buffer = new BufferedReader(filereader);
            while ((line = buffer.readLine()) != null) {
                buffer.close();
                return line;
            }
            buffer.close();
        } catch (Exception ex) {
            System.out.println("Error:" + ex);
        }
        return null;
    }

    public static void delete_first_line_file(String name) {
        Vector<String> p = new Vector<String>();
        File archivo = new File(name);
        p=do_read_file(name);
        try {
            FileWriter fileWriter = new FileWriter(archivo,false);
            //true para no modificar lo que ya estaba en el archivo, false para hacer lo contrario
            BufferedWriter buffer = new BufferedWriter(fileWriter);
            for (int i = 1; i < p.size(); i++) {
                buffer.write(p.get(i));
                buffer.newLine();
            }
            buffer.close();
        } catch (Exception ex) {
            System.out.println("Error:" + ex);
        }
    }

        public static boolean is_process(String name) {
        String line = "";
        File archivo = new File(name);
        try {
            FileReader leerArchivo = new FileReader(archivo);
            BufferedReader buffer = new BufferedReader(leerArchivo);
            while ((line = buffer.readLine()) != null) {
                buffer.close();
                return true;
            }
            buffer.close();
        } catch (Exception ex) {
            System.out.println("Error:" + ex);
        }

        return false;
    }
}
