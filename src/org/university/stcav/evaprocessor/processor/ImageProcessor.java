/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.university.stcav.evaprocessor.processor;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author johan
 */
public class ImageProcessor {

    public static BufferedImage create_image(int width, int height, String color) {
        //Formato color = "rgb(R,G,B)" donde R,G,B son enteros entre 0 y 255
        int r;
        int g;
        int b;
        color=color.substring(4, color.length()-1);
        
        System.out.println(color);
        r=Integer.parseInt(color.split(",")[0]);
        g=Integer.parseInt(color.split(",")[1]);
        b=Integer.parseInt(color.split(",")[2]);
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = bufferedImage.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(r,g,b));
        g2.fillRect(0, 0, width, height);
        // Aquí deberíamos introducir el código que queramos pintar.
        g2.dispose();
        return bufferedImage;
    }

    public static void export_image_color(String path, String format, String color, int width, int height) {
        try {
            ImageIO.write(create_image(width, height, color), format, new File(path));
        } catch (IOException ex) {
        }
    }
}
