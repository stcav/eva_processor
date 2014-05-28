/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.university.stcav.evaprocessor.model;

/**
 *
 * @author johan
 */
public class Layout {
    public static String MAINPATH = "/home/johaned/javaspace/university/stcav/";
    public static String MAINDOMAINPATH = "/home/johaned/servers/stcav/glassfish-domain/docroot/";
    public static String PORT = "9107";
    public static String URLPROCESSORMEDIA="http://localhost:8000/";
    public static String URLPROCESSORMEDIA_="http://localhost:8001/";
    public static String URLCONTENTPROCESSORSERVER="http://localhost:"+PORT+"/ContentProcessorServer/ContentProcessorServlet";
    public static String PATHCONTENTTEMP= MAINPATH+"content_temp/";
    public static String PATHSTACKVIDEOPROCESSOR= MAINPATH;
    public static String STACKVIDEOPROCESSOR="stackvideoprocessor";
    public static String PATHEVAPERFORMANCEFILE= MAINPATH;
    //public static String PATHVIDEOPROCESSFOLDER="/home/stcav/stcav/VideoProcessorFolder/";
    public static String EVAPERFORMANCEFILE="evaperformancefile";
    public static String PATHCONTENTREPOSITORY= MAINDOMAINPATH+"ContentRepository/";
    public static String PATHCONTENTPOSTER= MAINDOMAINPATH+"PosterRepository/";
    public static String PATHPROGRAMREPOSITORY= MAINDOMAINPATH+"ProgramRepository/";
    public static String PATHPROGRAMPOSTER= MAINDOMAINPATH+"ProgramRepository/PosterProgram/";
    public static String PATHVIDEOPROCESSCONTENT= MAINPATH+"VideoProcessFolder/content/";
    public static String PATHVIDEOPROCESSEVENT= MAINPATH+"VideoProcessFolder/event/";
    public static String URLPARTENONPROCESSORCOLOR ="http://localhost:"+PORT+"/PartenonServer/ColorProcessorServlet";
    public static String PATHFONTS= MAINPATH+"fonts/";
    public static String EXT="mp4";
    public static String PATHPROGRAMBROADCAST= MAINDOMAINPATH+"ProgramRepository/Broadcast/";
    public static String URLEVENTPROCESSOR= "http://localhost:"+PORT+"/ProgrammeProcessorServer/EventProcessorServlet";
    public static String ERRORSTACKVIDEOPROCESSOR= "errorstackvideoprocessor";
    
    // JMX Config Media folder
    public static String PATHMBEANDESCRIPTOR="/home/johaned/javaspace/university/stcav/gestv/InstrumentFolder/media";
    
    // JMX Server Labels
    public static String MEDIASERVER = "EVAProcessor";
    
    // Server Config
    public static String HOSTNAME = "Test";
    public static String JMX_PORT = "2843";
    public static boolean ISJMXABLE = false;
}
