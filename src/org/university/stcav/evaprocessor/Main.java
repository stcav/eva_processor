/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.university.stcav.evaprocessor;

import com.google.gson.Gson;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.sql.Date;
import java.sql.Time;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import javax.management.MBeanServer;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;
import org.university.stcav.evaprocessor.conf.Configurator;
import org.university.stcav.evaprocessor.jmx.logic.DynamicMBeanFactory;
import org.university.stcav.evaprocessor.jmx.logic.MBeanServerController;
import org.university.stcav.evaprocessor.jmx.model.MyDynamicMBean;
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
        List<MyDynamicMBean> mdmbs;
        JMXConnectorServer cs = null;
        // JMX Initializer
        if (Layout.ISJMXENABLE) {
            to_configure_JMXAgent();
            MBeanServer mbeanServer = ManagementFactory.getPlatformMBeanServer();
            try {
                LocateRegistry.createRegistry(Integer.parseInt(Layout.JMX_PORT));
                JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://" + InetAddress.getLocalHost().getHostName() + ":" + Layout.JMX_PORT + "/jmxrmi");
                cs = JMXConnectorServerFactory.newJMXConnectorServer(url, null, mbeanServer);
                cs.start();
                System.out.println("Escuchando en " + cs.getAddress());
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mdmbs = DynamicMBeanFactory.mbeans_register(Layout.PATHMBEANDESCRIPTOR, Layout.JMXSERVER);
        }
        
        
        // EVA Processor Initializer
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

    private static void to_configure_JMXAgent() {
        try {
            Configurator c = Configurator.getInstance();
            if (!c.isConfigured()) {
                c.to_configure_JMXParams(Layout.HOSTNAME, Layout.PORT);
                System.out.println("El sistema esta configurado - host: " + System.getProperty("visualvm.display.name") + " port: " + System.getProperty("com.sun.management.jmxremote.port"));
            } else {
                System.out.println("El sistema esta configurado - host: " + System.getProperty("visualvm.display.name") + " port: " + System.getProperty("com.sun.management.jmxremote.port"));
            }
        } catch (UnknownHostException ex) {
        }
    }
}
