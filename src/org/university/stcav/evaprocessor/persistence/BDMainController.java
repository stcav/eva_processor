/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.university.stcav.evaprocessor.persistence;

import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.university.stcav.evaprocessor.persistence.controller.ContenidoJpaController;
import org.university.stcav.evaprocessor.persistence.controller.EventoJpaController;
import org.university.stcav.evaprocessor.persistence.controller.exceptions.NonexistentEntityException;
import org.university.stcav.evaprocessor.persistence.entities.Contenido;
import org.university.stcav.evaprocessor.persistence.entities.Evento;

/**
 *
 * @author johan
 */
public class BDMainController {

    //********************************************* SOLO EVENTOS *********************************************//
    public static Evento getEventByID(Long idcontent) {
        EventoJpaController ejc = new EventoJpaController();
        Evento e = ejc.findEvento(idcontent);
        return e;
    }
    

    //********************************************* SOLO CONTENIDOS *********************************************//

    public static Contenido modifyContent(Long idContent, String rutascreenshot, String estado, double duracion) {
        ContenidoJpaController cjc = new ContenidoJpaController();
        Contenido c = cjc.findContenido(idContent);
        c.setEstado("completado");
        c.setDuracion(duracion);
        c.setRutascreenshot(rutascreenshot);
        return c;
    }

    public static Contenido modifyDurationContent(Long idcontent, double dur) {
        ContenidoJpaController cjc = new ContenidoJpaController();
        Contenido c = cjc.findContenido(idcontent);
        c.setDuracion(dur);
        return c;
    }

    public static Contenido getContentByID(Long idcontent) {
        ContenidoJpaController cjc = new ContenidoJpaController();
        Contenido c = cjc.findContenido(idcontent);
        return c;
    }

    public static Contenido modifyScreenshotContent(Long idcontent, String sc) {
        ContenidoJpaController cjc = new ContenidoJpaController();
        Contenido c = cjc.findContenido(idcontent);
        c.setRutascreenshot(sc);
        return c;
    }

    public static Contenido modifyStateContent(Long idcontent, String estado) {
        ContenidoJpaController cjc = new ContenidoJpaController();
        Contenido c = cjc.findContenido(idcontent);
        c.setEstado(estado);
        return c;
    }

    public static String getStateContent(Long idcontent) {
        ContenidoJpaController cjc = new ContenidoJpaController();
        Contenido c = cjc.findContenido(idcontent);
        return c.getEstado();
    }

    public static String getSrcContent(Long idcontent) {
        ContenidoJpaController cjc = new ContenidoJpaController();
        Contenido c = cjc.findContenido(idcontent);
        return c.getRutafuente();
    }

    public static String getDescriptorContent(Long idcontent) {
        ContenidoJpaController cjc = new ContenidoJpaController();
        Contenido c = cjc.findContenido(idcontent);
        return c.getDescriptor();
    }
}
