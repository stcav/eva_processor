/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.university.stcav.evaprocessor.processor;

import com.google.gson.Gson;
import java.io.FileNotFoundException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.university.stcav.eva.model.AudioElement;
import org.university.stcav.eva.model.FontElement;
import org.university.stcav.eva.model.MediaElement;
import org.university.stcav.eva.model.VideoElement;
import org.university.stcav.eva.processor.Processor;
import org.university.stcav.evaprocessor.communication.RestClient;
import org.university.stcav.evaprocessor.model.Color;
import org.university.stcav.evaprocessor.model.EditionElement;
import org.university.stcav.evaprocessor.model.Layout;
import org.university.stcav.evaprocessor.model.PerformanceElement;
import org.university.stcav.evaprocessor.model.ProcessItem;
import org.university.stcav.evaprocessor.model.event.Descriptor;
import org.university.stcav.evaprocessor.model.event.Element;
import org.university.stcav.evaprocessor.model.event.ElementLine;
import org.university.stcav.evaprocessor.persistence.BDMainController;
import org.university.stcav.evaprocessor.persistence.entities.Contenido;
import org.university.stcav.evaprocessor.persistence.entities.Evento;

//import sun.awt.windows.ThemeReader;

/**
 *
 * @author johan
 */
public class VideoProcessor {

    public VideoProcessor() {
    }

    public static void event_descriptor_processor(ProcessItem pi) {
        //Procesador de sentencias JSON
        Gson gson = new Gson();
        //Cliente REST
        RestClient rc = new RestClient();
        //Instancia del evento
        Evento e = BDMainController.getEventByID(pi.getId());
        //Instacia del contenido
        Contenido c;
        //Extrayendo los elementos multimedia implicados en la edicion
        String descriptor = e.getDescriptor();
        List<Element> lc;
        List<Element> lt;
        lc = new Gson().fromJson(descriptor, Descriptor.class).getLc().getElements();
        lt = new Gson().fromJson(descriptor, Descriptor.class).getLt().getElements();
        Element lte;
        Element lteNext;
        Element lce;

        //Nombre del directorio temporal
        String dirtemp = e.getIdEvento() + "_" + System.currentTimeMillis();
        String mediatemp;
        String fileText;

        //MediaElements necesarios
        MediaElement me;
        MediaElement meConfig = new MediaElement(new AudioElement("libfdk_aac", "48000", "stereo", "224k"), new VideoElement("libx264", "1280x720", "16:9", "2000k", "25"), "nombre");
        MediaElement meConfigTemp;
        //Comando
        String command;
        //Elemento de registro del rendimiento
        PerformanceElement pe;
        //Lista de union de videos
        List<MediaElement> mes = new ArrayList<MediaElement>();
        //Others
        String color_;
        //********************************************** Linea de contenido **********************************************//
        //comprobando si el descriptor no es un arreglo nulo
        if (lc.size() > 0) {
            try {
                //Creando el directorio temporal
                ProcessExecutor.execute_process(("mkdir " + dirtemp).split(" "), Layout.PATHVIDEOPROCESSEVENT, true);
                //Analalizando el vector de edicion
                for (int i = 0; i < lc.size(); i++) {
                    lce = lc.get(i);
                    mediatemp = i + "." + Layout.EXT;
                    if (lce.getType().equals("1")) {//Es un elemento de tipo Contenido
                        //Copiando el elemento multimedia a la carpeta temporal asignada para el proceso e indexandolo con un nombre
                        c = BDMainController.getContentByID(Long.parseLong(lce.getId()));
                        ProcessExecutor.execute_process(("cp " + c.getRutafuente() + " " + Layout.PATHVIDEOPROCESSEVENT + dirtemp + "/" + mediatemp).split(" "), Layout.PATHCONTENTREPOSITORY, false);
                        //Comprobando si hay fade-out
                        if (lce.isAvailableTransition()) {
                            int fadeIn = 0;
                            int fadeOut = 0;
                            if (lce.getHomeTransition() != (-1)) {
                                fadeIn = 2;
                            }
                            if (lce.getEndTransition() != (-1)) {
                                fadeOut = 2;
                            }
                            System.out.println("********** Edicion de fade ********** " + i);
                            me = Processor.get_mediaElement(mediatemp, Layout.PATHVIDEOPROCESSEVENT + dirtemp, false);
                            me.setName(Layout.PATHVIDEOPROCESSEVENT + dirtemp + "/" + me.getName());
                            meConfig.setName(Layout.PATHVIDEOPROCESSEVENT + dirtemp + "/" + "_" + mediatemp);

                            pe = new PerformanceElement(PerformanceElement.FADE, System.currentTimeMillis(), 0, me);
                            command = Processor.do_fade_to_video(me, meConfig, fadeIn, fadeOut, Layout.PATHVIDEOPROCESSEVENT + dirtemp, false, false);
                            System.out.println(command);
                            System.out.println(rc.get(Layout.URLPROCESSORMEDIA + command.replace(" ", "¬")));
                            ProcessExecutor.execute_process(("mv " + meConfig.getName() + " " + me.getName()).split(" "), Layout.PATHVIDEOPROCESSEVENT + dirtemp, false);
                            pe.setEndTime(System.currentTimeMillis());

                            FileProcessor.do_file_write(Layout.PATHEVAPERFORMANCEFILE + Layout.EVAPERFORMANCEFILE, gson.toJson(pe));
                        }

                    } else if (lce.getType().equals("2")) {//Elemento de tipo color plano
                        System.out.println("********** Edicion de color ********** " + i);
                        me = new MediaElement();
                        me.setName(Layout.PATHVIDEOPROCESSEVENT + dirtemp + "/" + mediatemp);
                        System.out.println(me.getName());
                        meConfig.setName(Layout.PATHVIDEOPROCESSEVENT + dirtemp + "/" + "_" + mediatemp);

                        Color color = gson.fromJson(rc.post(Layout.URLPARTENONPROCESSORCOLOR, "operation=1&id=" + lce.getId()), Color.class);
                        ImageProcessor.export_image_color(Layout.PATHVIDEOPROCESSEVENT + dirtemp + "/" + changeExtension(mediatemp, "jpg"), "jpg", color.getCodigoRGB(), 1280, 720);

                        pe = new PerformanceElement(PerformanceElement.CREATEVIDEOTOIMAGE, System.currentTimeMillis(), 0, me);
                        command = Processor.create_video_from_image(Layout.PATHVIDEOPROCESSEVENT + dirtemp + "/" + changeExtension(mediatemp, "jpg"), Math.round(Float.parseFloat(lce.getDuration())), meConfig.getVideoElement(), meConfig.getName(), Layout.PATHVIDEOPROCESSEVENT + dirtemp, false, false);
                        System.out.println(command);
                        System.out.println(rc.get(Layout.URLPROCESSORMEDIA + command.replace(" ", "¬")));
                        pe.setEndTime(System.currentTimeMillis());
                        FileProcessor.do_file_write(Layout.PATHEVAPERFORMANCEFILE + Layout.EVAPERFORMANCEFILE, gson.toJson(pe));

                        pe = new PerformanceElement(PerformanceElement.INSERTSILENCE, System.currentTimeMillis(), 0, me);
                        command = Processor.insert_silence_to_video(meConfig, me, Layout.PATHVIDEOPROCESSEVENT + dirtemp, false, false);
                        System.out.println(command);
                        System.out.println(rc.get(Layout.URLPROCESSORMEDIA + command.replace(" ", "¬")));
                        pe.setEndTime(System.currentTimeMillis());
                        FileProcessor.do_file_write(Layout.PATHEVAPERFORMANCEFILE + Layout.EVAPERFORMANCEFILE, gson.toJson(pe));

                        //Eliminando el directorio temporal
                        ProcessExecutor.execute_process(("rm -R " + meConfig.getName()).split(" "), Layout.PATHVIDEOPROCESSEVENT + dirtemp, false);
                    }
                    mes.add(Processor.get_mediaElement(mediatemp, Layout.PATHVIDEOPROCESSEVENT + dirtemp, false));
                }

                //Haciendo la union de los elementos, si solo es uno, no se genera union
                if (lc.size() > 1) {
                    System.out.println("********** MERGE ********** ");
                    command = Processor.do_merge_videos_x264_return(mes, Layout.PATHVIDEOPROCESSEVENT + dirtemp, Layout.PATHPROGRAMREPOSITORY + e.getRuta(), true, false);
                    System.out.println(command);
                    //Thread.sleep(3000);
                    System.out.println(rc.get(Layout.URLPROCESSORMEDIA + command.replace(" ", "¬")+"*"+Layout.PATHVIDEOPROCESSEVENT + dirtemp+"*"));
                } else {
                    ProcessExecutor.execute_process(("mv " + mes.get(0).getName() + " " + Layout.PATHPROGRAMREPOSITORY + e.getRuta()).split(" "), Layout.PATHVIDEOPROCESSEVENT + dirtemp, false);
                }

                //Eliminando el directorio temporal
                //ProcessExecutor.execute_process(("rm -R " + dirtemp).split(" "), Layout.PATHVIDEOPROCESSEVENT, false);

            } catch (InterruptedException ex) {
                Logger.getLogger(VideoProcessor.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(VideoProcessor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }



        //********************************************** Linea de texto **********************************************//

        if (lt.size() > 0) {
            try {
                mes.clear();
                me = Processor.get_mediaElement(e.getRuta(), Layout.PATHPROGRAMREPOSITORY, true);
                me.setName(Layout.PATHPROGRAMREPOSITORY + e.getRuta());
                meConfig = new MediaElement(new AudioElement("libfdk_aac", "48000", "stereo", "224k"), new VideoElement("libx264", "1280x720", "16:9", "2000k", "25"), "nombre");
                meConfigTemp = new MediaElement(new AudioElement("libfdk_aac", "48000", "stereo", "224k"), new VideoElement("libx264", "1280x720", "16:9", "2000k", "25"), "nombre");
                //indice merge de corte
                int index = 0;

                //Creando el directorio temporal
                ProcessExecutor.execute_process(("mkdir " + dirtemp).split(" "), Layout.PATHVIDEOPROCESSEVENT, true);

                //Analizando si hay espacio entre el primer elemento  y el tiempo 0
                if (!lt.get(0).getHome().equals("0")) {
                    lte = lt.get(0);
                    mediatemp = index + "." + Layout.EXT;
                    meConfig.setName(Layout.PATHVIDEOPROCESSEVENT + dirtemp + "/" + mediatemp);

                    //Cortando el video
                    pe = new PerformanceElement(PerformanceElement.CUTVIDEO, System.currentTimeMillis(), 0, me);
                    command = Processor.cut_video_hd(0, Math.round(Float.parseFloat(lte.getHome())), me, meConfig, "/home/stcav/", false, false);
                    System.out.println(command);
                    System.out.println(rc.get(Layout.URLPROCESSORMEDIA + command.replace(" ", "¬")));

                    pe.setEndTime(System.currentTimeMillis());
                    FileProcessor.do_file_write(Layout.PATHEVAPERFORMANCEFILE + Layout.EVAPERFORMANCEFILE, gson.toJson(pe));

                    mes.add(Processor.get_mediaElement(mediatemp, Layout.PATHVIDEOPROCESSEVENT + dirtemp, false));

                    index++;
                }

                //Analalizando el vector de edicion
                for (int i = 0; i < (lt.size() - 1); i++) {// -1 para garatizar que siempre sobre un elemento de texto
                    lte = lt.get(i);
                    lteNext = lt.get(i + 1);
                    mediatemp = index + "." + Layout.EXT;
                    me.setName(Layout.PATHPROGRAMREPOSITORY + e.getRuta());
                    meConfig.setName(Layout.PATHVIDEOPROCESSEVENT + dirtemp + "/" + mediatemp);

                    //******* Fragmento de texto, Cortando el video
                    pe = new PerformanceElement(PerformanceElement.CUTVIDEO, System.currentTimeMillis(), 0, me);
                    command = Processor.cut_video_hd(Math.round(Float.parseFloat(lte.getHome())), Math.round(Float.parseFloat(lte.getEnd())), me, meConfig, "/home/stcav/", false, false);
                    System.out.println(command);
                    System.out.println(rc.get(Layout.URLPROCESSORMEDIA + command.replace(" ", "¬")));
                    pe.setEndTime(System.currentTimeMillis());
                    FileProcessor.do_file_write(Layout.PATHEVAPERFORMANCEFILE + Layout.EVAPERFORMANCEFILE, gson.toJson(pe));

                    //******* Fragmento de texto, Anadiendo texto
                    //*************************************************
                    color_ = "";
                    fileText = changeExtension(mediatemp, "txt");
                    if (lte.getColor().equals("white") || lte.getColor().equals("black") || lte.getColor().equals("yellow") || lte.getColor().equals("blue") || lte.getColor().equals("green") || lte.getColor().equals("red") || lte.getColor().equals("pink")) {
                        color_ = lte.getColor();
                    } else {
                        color_ = "white";
                    }
                    //Eliminando el problema de los caracteres especiales
                    lte.setText(lte.getText().replaceAll("Ã¡", "á"));
                    lte.setText(lte.getText().replaceAll("Ã©", "é"));
                    lte.setText(lte.getText().replaceAll("Ã­", "í"));
                    lte.setText(lte.getText().replaceAll("Ã³", "ó"));
                    lte.setText(lte.getText().replaceAll("Ãº", "ú"));
                    lte.setText(lte.getText().replaceAll("Â¿", "¿"));
                    lte.setText(lte.getText().replaceAll("Â¡", "¡"));
                    lte.setText(lte.getText().replaceAll("Â°", "°"));

                    //creando el archivo de texto
                    FileProcessor.do_file_write(Layout.PATHVIDEOPROCESSEVENT + dirtemp + "/" + fileText, lte.getText());
                    FontElement f = new FontElement(Layout.PATHVIDEOPROCESSEVENT + dirtemp + "/" + fileText, "40", color_, Layout.PATHFONTS + "FreeSerif.ttf", "70", "40");
                    System.out.println("/*/*/*/*/*/*/*/* Directorio de texto: "+Layout.PATHVIDEOPROCESSEVENT + dirtemp + "/" + fileText);
                    //*************************************************
                    pe = new PerformanceElement(PerformanceElement.INSERTTEXT, System.currentTimeMillis(), 0, me);
                    meConfigTemp.setName(Layout.PATHVIDEOPROCESSEVENT + dirtemp + "/" + "_" + mediatemp);
                    command = Processor.insert_text_from_file_to_video(meConfig, meConfigTemp, f, "/home/stcav", false, false);
                    System.out.println(command);
                    System.out.println(rc.get(Layout.URLPROCESSORMEDIA + command.replace(" ", "¬")));
                    ProcessExecutor.execute_process(("mv " + meConfigTemp.getName() + " " + meConfig.getName()).split(" "), Layout.PATHVIDEOPROCESSEVENT + dirtemp, false);
                    pe.setEndTime(System.currentTimeMillis());
                    FileProcessor.do_file_write(Layout.PATHEVAPERFORMANCEFILE + Layout.EVAPERFORMANCEFILE, gson.toJson(pe));

                    mes.add(Processor.get_mediaElement(mediatemp, Layout.PATHVIDEOPROCESSEVENT + dirtemp, false));

                    index++;

                    //****** Fragmento libre, cortando el video
                    mediatemp = index + "." + Layout.EXT;
                    me = Processor.get_mediaElement(e.getRuta(), Layout.PATHPROGRAMREPOSITORY, true);
                    me.setName(Layout.PATHPROGRAMREPOSITORY + e.getRuta());
                    meConfig.setName(Layout.PATHVIDEOPROCESSEVENT + dirtemp + "/" + mediatemp);

                    pe = new PerformanceElement(PerformanceElement.CUTVIDEO, System.currentTimeMillis(), 0, me);
                    command = Processor.cut_video_hd(Math.round(Float.parseFloat(lte.getEnd())), Math.round(Float.parseFloat(lteNext.getHome())), me, meConfig, "/home/stcav/", false, false);
                    System.out.println(command);
                    System.out.println(rc.get(Layout.URLPROCESSORMEDIA + command.replace(" ", "¬")));
                    pe.setEndTime(System.currentTimeMillis());
                    FileProcessor.do_file_write(Layout.PATHEVAPERFORMANCEFILE + Layout.EVAPERFORMANCEFILE, gson.toJson(pe));

                    mes.add(Processor.get_mediaElement(mediatemp, Layout.PATHVIDEOPROCESSEVENT + dirtemp, false));

                    index++;
                }
                //Anlizando el ultimo elemento del vector de edicion
                if (Math.round(Float.parseFloat(lt.get(lt.size() - 1).getEnd())) == Processor.do_TimeToSeconds(me.getDuration())) {//llega hasta el final del video
                    lte = lt.get(lt.size() - 1);
                    mediatemp = index + "." + Layout.EXT;
                    me.setName(Layout.PATHPROGRAMREPOSITORY + e.getRuta());
                    meConfig.setName(Layout.PATHVIDEOPROCESSEVENT + dirtemp + "/" + mediatemp);

                    //******* Fragmento de texto, Cortando el video
                    pe = new PerformanceElement(PerformanceElement.CUTVIDEO, System.currentTimeMillis(), 0, me);
                    command = Processor.cut_video_hd(Math.round(Float.parseFloat(lte.getHome())), Math.round(Float.parseFloat(lte.getEnd())), me, meConfig, "/home/stcav/", false, false);
                    System.out.println(command);
                    System.out.println(rc.get(Layout.URLPROCESSORMEDIA + command.replace(" ", "¬")));
                    pe.setEndTime(System.currentTimeMillis());
                    FileProcessor.do_file_write(Layout.PATHEVAPERFORMANCEFILE + Layout.EVAPERFORMANCEFILE, gson.toJson(pe));

                    //******* Fragmento de texto, Anadiendo texto
                    //*************************************************
                    color_ = "";
                    fileText = changeExtension(mediatemp, "txt");
                    if (lte.getColor().equals("white") || lte.getColor().equals("black") || lte.getColor().equals("yellow") || lte.getColor().equals("blue") || lte.getColor().equals("green") || lte.getColor().equals("red") || lte.getColor().equals("pink")) {
                        color_ = lte.getColor();
                    } else {
                        color_ = "white";
                    }
                    //Eliminando el problema de los caracteres especiales
                    lte.setText(lte.getText().replaceAll("Ã¡", "á"));
                    lte.setText(lte.getText().replaceAll("Ã©", "é"));
                    lte.setText(lte.getText().replaceAll("Ã­", "í"));
                    lte.setText(lte.getText().replaceAll("Ã³", "ó"));
                    lte.setText(lte.getText().replaceAll("Ãº", "ú"));
                    lte.setText(lte.getText().replaceAll("Â¿", "¿"));
                    lte.setText(lte.getText().replaceAll("Â¡", "¡"));
                    lte.setText(lte.getText().replaceAll("Â°", "°"));

                    //creando el archivo de texto
                    FileProcessor.do_file_write(Layout.PATHVIDEOPROCESSEVENT + dirtemp + "/" + fileText, lte.getText());
                    FontElement f = new FontElement(Layout.PATHVIDEOPROCESSEVENT + dirtemp + "/" + fileText, "40", color_, Layout.PATHFONTS + "FreeSerif.ttf", "70", "40");
                    System.out.println("/*/*/*/*/*/*/*/* Directorio de texto: "+Layout.PATHVIDEOPROCESSEVENT + dirtemp + "/" + fileText);
                    //*************************************************
               
                    pe = new PerformanceElement(PerformanceElement.INSERTTEXT, System.currentTimeMillis(), 0, me);
                    meConfigTemp.setName(Layout.PATHVIDEOPROCESSEVENT + dirtemp + "/" + "_" + mediatemp);
                    command = Processor.insert_text_from_file_to_video(meConfig, meConfigTemp, f, "/home/stcav", false, false);
                    System.out.println(command);
                    System.out.println(rc.get(Layout.URLPROCESSORMEDIA + command.replace(" ", "¬")));
                    ProcessExecutor.execute_process(("mv " + meConfigTemp.getName() + " " + meConfig.getName()).split(" "), Layout.PATHVIDEOPROCESSEVENT + dirtemp, false);
                    pe.setEndTime(System.currentTimeMillis());
                    FileProcessor.do_file_write(Layout.PATHEVAPERFORMANCEFILE + Layout.EVAPERFORMANCEFILE, gson.toJson(pe));

                    mes.add(Processor.get_mediaElement(mediatemp, Layout.PATHVIDEOPROCESSEVENT + dirtemp, false));
                } else {//Se deben hacer dos cortes mas
                    lte = lt.get(lt.size() - 1);
                    mediatemp = index + "." + Layout.EXT;
                    me.setName(Layout.PATHPROGRAMREPOSITORY + e.getRuta());
                    meConfig.setName(Layout.PATHVIDEOPROCESSEVENT + dirtemp + "/" + mediatemp);

                    //******* Fragmento de texto, Cortando el video
                    pe = new PerformanceElement(PerformanceElement.CUTVIDEO, System.currentTimeMillis(), 0, me);
                    command = Processor.cut_video_hd(Math.round(Float.parseFloat(lte.getHome())), Math.round(Float.parseFloat(lte.getEnd())), me, meConfig, "/home/stcav/", false, false);
                    System.out.println(command);
                    System.out.println(rc.get(Layout.URLPROCESSORMEDIA + command.replace(" ", "¬")));
                    pe.setEndTime(System.currentTimeMillis());
                    FileProcessor.do_file_write(Layout.PATHEVAPERFORMANCEFILE + Layout.EVAPERFORMANCEFILE, gson.toJson(pe));

                    //******* Fragmento de texto, Anadiendo texto
                    //*************************************************
                    color_ = "";
                    fileText = changeExtension(mediatemp, "txt");
                    if (lte.getColor().equals("white") || lte.getColor().equals("black") || lte.getColor().equals("yellow") || lte.getColor().equals("blue") || lte.getColor().equals("green") || lte.getColor().equals("red") || lte.getColor().equals("pink")) {
                        color_ = lte.getColor();
                    } else {
                        color_ = "white";
                    }
                    //Eliminando el problema de los caracteres especiales
                    lte.setText(lte.getText().replaceAll("Ã¡", "á"));
                    lte.setText(lte.getText().replaceAll("Ã©", "é"));
                    lte.setText(lte.getText().replaceAll("Ã­", "í"));
                    lte.setText(lte.getText().replaceAll("Ã³", "ó"));
                    lte.setText(lte.getText().replaceAll("Ãº", "ú"));
                    lte.setText(lte.getText().replaceAll("Â¿", "¿"));
                    lte.setText(lte.getText().replaceAll("Â¡", "¡"));
                    lte.setText(lte.getText().replaceAll("Â°", "°"));

                    //creando el archivo de texto
                    FileProcessor.do_file_write(Layout.PATHVIDEOPROCESSEVENT + dirtemp + "/" + fileText, lte.getText());
                    FontElement f = new FontElement(Layout.PATHVIDEOPROCESSEVENT + dirtemp + "/" + fileText, "40", color_, Layout.PATHFONTS + "FreeSerif.ttf", "70", "40");
                    System.out.println("/*/*/*/*/*/*/*/* Directorio de texto: "+Layout.PATHVIDEOPROCESSEVENT + dirtemp + "/" + fileText);
                    //*************************************************
                    pe = new PerformanceElement(PerformanceElement.INSERTTEXT, System.currentTimeMillis(), 0, me);
                    meConfigTemp.setName(Layout.PATHVIDEOPROCESSEVENT + dirtemp + "/" + "_" + mediatemp);
                    command = Processor.insert_text_from_file_to_video(meConfig, meConfigTemp, f, "/home/stcav", false, false);
                    System.out.println(command);
                    System.out.println(rc.get(Layout.URLPROCESSORMEDIA + command.replace(" ", "¬")));
                    ProcessExecutor.execute_process(("mv " + meConfigTemp.getName() + " " + meConfig.getName()).split(" "), Layout.PATHVIDEOPROCESSEVENT + dirtemp, false);
                    pe.setEndTime(System.currentTimeMillis());
                    FileProcessor.do_file_write(Layout.PATHEVAPERFORMANCEFILE + Layout.EVAPERFORMANCEFILE, gson.toJson(pe));

                    mes.add(Processor.get_mediaElement(mediatemp, Layout.PATHVIDEOPROCESSEVENT + dirtemp, false));

                    index++;

                    //****** Fragmento libre, cortando el video
                    mediatemp = index + "." + Layout.EXT;
                    me = Processor.get_mediaElement(e.getRuta(), Layout.PATHPROGRAMREPOSITORY, true);
                    me.setName(Layout.PATHPROGRAMREPOSITORY + e.getRuta());
                    meConfig.setName(Layout.PATHVIDEOPROCESSEVENT + dirtemp + "/" + mediatemp);

                    pe = new PerformanceElement(PerformanceElement.CUTVIDEO, System.currentTimeMillis(), 0, me);
                    command = Processor.cut_video_hd(Math.round(Float.parseFloat(lte.getEnd())), Processor.do_TimeToSeconds(me.getDuration()), me, meConfig, "/home/stcav/", false, false);
                    System.out.println(command);
                    System.out.println(rc.get(Layout.URLPROCESSORMEDIA + command.replace(" ", "¬")));
                    pe.setEndTime(System.currentTimeMillis());
                    FileProcessor.do_file_write(Layout.PATHEVAPERFORMANCEFILE + Layout.EVAPERFORMANCEFILE, gson.toJson(pe));

                    mes.add(Processor.get_mediaElement(mediatemp, Layout.PATHVIDEOPROCESSEVENT + dirtemp, false));
                }

                //Haciendo la union de los elementos, si solo es uno, no se genera union
                if (lt.size() > 0) {//**************************************************************************
                    System.out.println("********** MERGE ********** ");
                    command=Processor.do_merge_videos_x264_return(mes, Layout.PATHVIDEOPROCESSEVENT + dirtemp, Layout.PATHPROGRAMREPOSITORY + e.getRuta(), true, false);
                    System.out.println(command);
                    System.out.println(rc.get(Layout.URLPROCESSORMEDIA + command.replace(" ", "¬")+"*"+Layout.PATHVIDEOPROCESSEVENT + dirtemp+"*"));
                } else {
                    ProcessExecutor.execute_process(("mv " + mes.get(0).getName() + " " + Layout.PATHPROGRAMREPOSITORY + e.getRuta()).split(" "), Layout.PATHVIDEOPROCESSEVENT + dirtemp, false);
                }

                //Eliminando el directorio temporal
                //ProcessExecutor.execute_process(("rm -R " + dirtemp).split(" "), Layout.PATHVIDEOPROCESSEVENT, false);



            } catch (InterruptedException ex) {
                Logger.getLogger(VideoProcessor.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(VideoProcessor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            //*** Adecuando el evento para que pueda ser trasmitido por BROADCAST en calidad DVD Video PAL MPEG-2
            me = Processor.get_mediaElement(e.getRuta(), Layout.PATHPROGRAMREPOSITORY, true);
            //Dividiendo solo video
            pe = new PerformanceElement(PerformanceElement.TRANSCODETOPALVIDEO, System.currentTimeMillis(), 0, me);
            command = Processor.transcode_video_PALMPEG2(Layout.PATHPROGRAMREPOSITORY + e.getRuta(), Layout.PATHPROGRAMBROADCAST + changeExtension(e.getRuta(), "m2v"), "/home/stcav", false, false);
            System.out.println(command);
            System.out.println(rc.get(Layout.URLPROCESSORMEDIA + command.replace(" ", "¬")));
            pe.setEndTime(System.currentTimeMillis());
            FileProcessor.do_file_write(Layout.PATHEVAPERFORMANCEFILE + Layout.EVAPERFORMANCEFILE, gson.toJson(pe));
            //Dividiendo solo audio
            pe = new PerformanceElement(PerformanceElement.TRANSCODETOMP2, System.currentTimeMillis(), 0, me);
            command = Processor.transcode_audio_MPEG2(Layout.PATHPROGRAMREPOSITORY + e.getRuta(), Layout.PATHPROGRAMBROADCAST + changeExtension(e.getRuta(), "mp2"), command, false, false);
            System.out.println(command);
            System.out.println(rc.get(Layout.URLPROCESSORMEDIA + command.replace(" ", "¬")));
            pe.setEndTime(System.currentTimeMillis());
            FileProcessor.do_file_write(Layout.PATHEVAPERFORMANCEFILE + Layout.EVAPERFORMANCEFILE, gson.toJson(pe));
            //Sincronizando Audio y Video
            pe = new PerformanceElement(PerformanceElement.AUDIOVIDEOESTOPES, System.currentTimeMillis(), 0, me);
            ProcessExecutor.execute_process(("sh transcobcast.sh " + e.getRuta()).split(" "), Layout.PATHPROGRAMBROADCAST, true);
            pe.setEndTime(System.currentTimeMillis());
            FileProcessor.do_file_write(Layout.PATHEVAPERFORMANCEFILE + Layout.EVAPERFORMANCEFILE, gson.toJson(pe));
            //cambiando estado del evento a completado
            System.out.println(rc.post(Layout.URLEVENTPROCESSOR, "operation=5&id=" + e.getIdEvento() + "&estado=completado"));
            //cambiando la duracion del evento
            double dur = 0;
            try {
            Vector r = FileProcessor.do_read_file(Layout.PATHPROGRAMBROADCAST + "pes.length");
            Long length = Long.parseLong((String) r.get(0));
            dur = (length / 3600);
            dur = dur / 25;
            } catch (Exception i) {
            Vector r = FileProcessor.do_read_file(Layout.PATHPROGRAMBROADCAST + changeExtension(e.getRuta(), "audio.pes.length"));
            Long length = Long.parseLong((String) r.get(0));
            dur = (length / 3600);
            dur = dur / 25;
            }

            System.out.println(rc.post(Layout.URLEVENTPROCESSOR, "operation=6&id=" + e.getIdEvento() + "&duracion=" + dur));
            //Borrando archivos temporales
            ProcessExecutor.execute_process(("rm " + Layout.PATHPROGRAMBROADCAST + changeExtension(e.getRuta(), "m2v")).split(" "), Layout.PATHPROGRAMBROADCAST, true);
            ProcessExecutor.execute_process(("rm " + Layout.PATHPROGRAMBROADCAST + changeExtension(e.getRuta(), "mp2")).split(" "), Layout.PATHPROGRAMBROADCAST, true);
        } catch (InterruptedException ex) {
            Logger.getLogger(VideoProcessor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(VideoProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void content_descriptor_processor(ProcessItem pi) {
        //Procesador de sentencias JSON
        Gson gson = new Gson();
        //Cliente REST
        RestClient rc = new RestClient();
        //Instancia del contenido
        Contenido c = new Contenido();
        boolean updateTime = false;
        while (!updateTime) {
            c = BDMainController.getContentByID(pi.getId());
            if (c.getDescriptor() != null) {
                updateTime = true;
                System.out.println("*********** Ya esta listo el descriptor");
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(VideoProcessor.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        System.out.println(c.getTitulo() + "      " + c.getDescriptor());
        //Extrayendo los elementos multimedia implicados en la edicion
        String descriptor = c.getDescriptor();
        System.out.println(descriptor);
        EditionElement[] ees = new Gson().fromJson(descriptor, EditionElement[].class);
        EditionElement ee;
        //Nombre del directorio temporal
        String dirtemp = c.getIdContenido() + "_" + System.currentTimeMillis();
        String mediatemp;
        //MediaElements necesarios
        MediaElement me;
        MediaElement meConfig = new MediaElement(new AudioElement("libfdk_aac", "48000", "stereo", "224k"), new VideoElement("libx264", "1280x720", "16:9", "2000k", "25"), "nombre");
        //Comando
        String command;
        //Elemento de registro del rendimiento
        PerformanceElement pe;
        //Lista de union de videos
        List<MediaElement> mes = new ArrayList<MediaElement>();

        //comprobando si el descriptor no es un arreglo nulo
        if (ees.length > 0) {
            try {
                //Creando el directorio temporal
                ProcessExecutor.execute_process(("mkdir " + dirtemp).split(" "), Layout.PATHVIDEOPROCESSCONTENT, true);
                /*definitivamente esta no es la mejor manera de hacerlo, toca buscar una mejor forma
                inlcuyendo una mejor reutilizacion de funciones dentro de un proceso automatico ,
                OJO prevenir de q cuando se presente un error se salga el EVAProcessor */
                //Analalizando el vector de edicion
                for (int i = 0; i < ees.length; i++) {
                    ee = ees[i];
                    mediatemp = i + "." + Layout.EXT;
                    //Copiando el elemento multimedia a la carpeta temporal asignada para el proceso e indexandolo con un nombre
                    ProcessExecutor.execute_process(("cp " + ee.getSrc() + " " + Layout.PATHVIDEOPROCESSCONTENT + dirtemp + "/" + mediatemp).split(" "), Layout.PATHCONTENTREPOSITORY, false);
                    //comprobando si hay edicion en corte
                    if ((ee.getCut_end() - ee.getCut_home()) != ee.getDuration()) {
                        System.out.println("********** Edicion de corte ********** " + i);
                        me = Processor.get_mediaElement(mediatemp, Layout.PATHVIDEOPROCESSCONTENT + dirtemp, false);
                        me.setName(Layout.PATHVIDEOPROCESSCONTENT + dirtemp + "/" + me.getName());
                        meConfig.setName(Layout.PATHVIDEOPROCESSCONTENT + dirtemp + "/" + "_" + mediatemp);

                        pe = new PerformanceElement(PerformanceElement.CUTVIDEO, System.currentTimeMillis(), 0, me);
                        //cut_video_hd(Math.round(ee.getCut_home()), Math.round(ee.getCut_end()), me, me, "/home/stcav/");
                        command = Processor.cut_video_hd(Math.round(ee.getCut_home()), Math.round(ee.getCut_end()), me, meConfig, "/home/stcav/", false, false);
                        System.out.println(command);
                        System.out.println(rc.get(Layout.URLPROCESSORMEDIA + command.replace(" ", "¬")));
                        ProcessExecutor.execute_process(("mv " + meConfig.getName() + " " + me.getName()).split(" "), Layout.PATHVIDEOPROCESSCONTENT + dirtemp, false);
                        pe.setEndTime(System.currentTimeMillis());

                        FileProcessor.do_file_write(Layout.PATHEVAPERFORMANCEFILE + Layout.EVAPERFORMANCEFILE, gson.toJson(pe));
                    }
                    //Comprobando si hay fade-out
                    if (ee.getIdTransition() != (-1)) {
                        System.out.println("********** Edicion de fade ********** " + i);
                        me = Processor.get_mediaElement(mediatemp, Layout.PATHVIDEOPROCESSCONTENT + dirtemp, false);
                        me.setName(Layout.PATHVIDEOPROCESSCONTENT + dirtemp + "/" + me.getName());
                        meConfig.setName(Layout.PATHVIDEOPROCESSCONTENT + dirtemp + "/" + "_" + mediatemp);

                        pe = new PerformanceElement(PerformanceElement.FADEOUT, System.currentTimeMillis(), 0, me);
                        command = Processor.do_fade_to_video(me, meConfig, 0, 2, Layout.PATHVIDEOPROCESSCONTENT + dirtemp, false, false);
                        System.out.println(command);
                        System.out.println(rc.get(Layout.URLPROCESSORMEDIA + command.replace(" ", "¬")));
                        ProcessExecutor.execute_process(("mv " + meConfig.getName() + " " + me.getName()).split(" "), Layout.PATHVIDEOPROCESSCONTENT + dirtemp, false);
                        pe.setEndTime(System.currentTimeMillis());

                        FileProcessor.do_file_write(Layout.PATHEVAPERFORMANCEFILE + Layout.EVAPERFORMANCEFILE, gson.toJson(pe));
                    }
                    mes.add(Processor.get_mediaElement(mediatemp, Layout.PATHVIDEOPROCESSCONTENT + dirtemp, false));
                }

                //Haciendo la union de los elementos, si solo es uno, no se genera union
                if (ees.length > 1) {
                    System.out.println("********** MERGE ********** ");
                    Processor.do_merge_videos_x264(mes, Layout.PATHVIDEOPROCESSCONTENT + dirtemp, Layout.PATHCONTENTREPOSITORY + c.getRutafuente(), true, false);
                } else {
                    ProcessExecutor.execute_process(("mv " + mes.get(0).getName() + " " + Layout.PATHCONTENTREPOSITORY + c.getRutafuente()).split(" "), Layout.PATHVIDEOPROCESSCONTENT + dirtemp, false);
                }

                //Eliminando el directorio temporal
                ProcessExecutor.execute_process(("rm -R " + dirtemp).split(" "), Layout.PATHVIDEOPROCESSCONTENT, false);

                //Actualizando la informacion del contenido
                String contentName = c.getRutafuente();
                String posterName = changeExtension(contentName, "jpg");
                me = Processor.get_mediaElement(contentName, Layout.PATHCONTENTREPOSITORY, false);
                System.out.println(me.getName());//***
                Processor.create_image_from_video(me, 2, Layout.PATHCONTENTPOSTER + posterName, Layout.PATHCONTENTREPOSITORY, true, false);
                String JsonContent = gson.toJson(BDMainController.modifyContent(pi.getId(), posterName, "completado", Processor.do_TimeToSeconds(me.getDuration())));

                rc.post(Layout.URLCONTENTPROCESSORSERVER, "operation=5&content=" + JsonContent);

            } catch (InterruptedException ex) {
                Logger.getLogger(VideoProcessor.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(VideoProcessor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void content_adapter_HD(ProcessItem pi) {
        Gson gson = new Gson();
        RestClient rc = new RestClient();
        String contentName = BDMainController.getSrcContent(pi.getId());
        String posterName = changeExtension(contentName, "jpg");
        String JsonContent;
        PerformanceElement pe;
        String command;
        System.out.println("Nombres destinos: " + contentName + " " + posterName);
        JsonContent = gson.toJson(BDMainController.modifyStateContent(pi.getId(), "procesando"));
        try {
            rc.post(Layout.URLCONTENTPROCESSORSERVER, "operation=5&content=" + JsonContent);
        } catch (IOException ex) {
            Logger.getLogger(VideoProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            MediaElement me = Processor.get_mediaElement(pi.getSrc(), Layout.PATHCONTENTTEMP, true);
            me.setName(Layout.PATHCONTENTTEMP + me.getName());
            System.out.println(me.getName());//***
            MediaElement meConfig = new MediaElement(new AudioElement("libfdk_aac", "48000", "stereo", "224k"), new VideoElement("libx264", "1280x720", "16:9", "2000k", "25"), Layout.PATHCONTENTREPOSITORY + contentName);
            System.out.println(meConfig.getName());//***
            pe = new PerformanceElement(1, System.currentTimeMillis(), 0, me);

            command = Processor.resize_video_hd(me, meConfig, Layout.PATHCONTENTTEMP, false, true);
            System.out.println(command);
            System.out.println(rc.get(Layout.URLPROCESSORMEDIA + command.replace(" ", "¬")));

            pe.setEndTime(System.currentTimeMillis());
            FileProcessor.do_file_write(Layout.PATHEVAPERFORMANCEFILE + Layout.EVAPERFORMANCEFILE, gson.toJson(pe));
            me = Processor.get_mediaElement(contentName, Layout.PATHCONTENTREPOSITORY, true);
            System.out.println(me.getName());//***
            Processor.create_image_from_video(me, 2, Layout.PATHCONTENTPOSTER + posterName, Layout.PATHCONTENTREPOSITORY, true, false);
            
            JsonContent = gson.toJson(BDMainController.modifyContent(pi.getId(), posterName, "completado", Processor.do_TimeToSeconds(me.getDuration())));

            rc.post(Layout.URLCONTENTPROCESSORSERVER, "operation=5&content=" + JsonContent);

        } catch (Exception ex) {
            JsonContent = gson.toJson(BDMainController.modifyStateContent(pi.getId(), "fallo"));
            try {
                rc.post(Layout.URLCONTENTPROCESSORSERVER, "operation=5&content=" + JsonContent);
            } catch (IOException exc) {
                Logger.getLogger(VideoProcessor.class.getName()).log(Level.SEVERE, null, exc);
            }
            Logger.getLogger(VideoProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static String changeExtension(String file, String newFormat) {
        file = file.replace('.', ':');
        file = file.split(":")[0];
        System.out.println(file);
        file += "." + newFormat;
        System.out.println(" ******** " + file);
        return file;
    }

    public static void cut_video_hd(int home_cut, int end_cut, MediaElement me, MediaElement meConfig, String path) throws InterruptedException, IOException {
        String command = "ffmpeg -y -i " + me.getName() + " -vcodec copy -vbsf h264_mp4toannexb -acodec copy -absf aac_adtstoasc " + changeExtension(me.getName(), "ts");
        System.out.println(command);
        ProcessExecutor.execute_process(command.split(" "), path, false);
        command = "ffmpeg -sameq -ss " + home_cut + " -t " + (end_cut - home_cut) + " -y -i " + changeExtension(me.getName(), "ts") + " -vcodec copy -vbsf h264_mp4toannexb -acodec copy -absf aac_adtstoasc " + meConfig.getName();
        System.out.println(command);
        ProcessExecutor.execute_process(command.split(" "), path, false);
    }
}
