package org.university.stcav.evaprocessor.communication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class RestClient {

    private HttpURLConnection hcon;
    private DataInputStream dis;
    private StringBuffer responseMessage;
    private int redirectTimes = 0;
    private boolean redirect;
    private DataOutputStream dos;

    public RestClient() {
    }

    private void configureConnection(HttpURLConnection conn) throws IOException {
        String locale = "es-ES";
        conn.setRequestProperty("Accept-Language", locale);
        conn.setRequestProperty("Content-Type", "application/text");
        conn.setRequestProperty("Accept", "application/text");
        conn.setConnectTimeout(0);
        conn.setReadTimeout(0);
    }

    private HttpURLConnection getConnection(String url) throws IOException {
        URL serverAddress = null;
        serverAddress = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) serverAddress.openConnection();
        configureConnection(conn);
        return conn;
    }

    public String get(String url) throws IOException {
        return operacion("GET", url, "");
    }

    public String post(String url, String data) throws IOException {
        return operacion("POST", url, data);
    }

    public String put(String url, String data) throws IOException {
        return operacion("PUT", url, data);
    }
    
    public String delete(String url) throws IOException {
        return operacion("DELETE", url, "");
    }

    private String operacion(String opc, String url, String data) {
        responseMessage = new StringBuffer();
        try {
            redirectTimes = 0;
            do {
                redirect = false;
                hcon = getConnection(url);
                if (opc.equals("GET")) {
                    hcon.setRequestMethod("GET");
                } else if (opc.equals("POST")) {
                    hcon.setRequestMethod("POST");
                    hcon.setDoOutput(true);
                    hcon.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    if (data != null) {
                        hcon.setRequestProperty("Content-Length", "" + data.length());
                        dos = new DataOutputStream(hcon.getOutputStream());
                        byte[] request_body = data.getBytes();
                        for (int i = 0; i < request_body.length; i++) {
                            dos.writeByte(request_body[i]);
                        }
                        dos.flush();
                    }
                } else if (opc.equals("PUT")) {
                    hcon.setRequestMethod("PUT");
                    hcon.setDoOutput(true);
                    hcon.setRequestProperty("Content-Type", "application/json");
                    if (data != null) {
                        hcon.setRequestProperty("Content-Length", "" + data.length());
                        dos = new DataOutputStream(hcon.getOutputStream());
                        byte[] request_body = data.getBytes();
                        for (int i = 0; i < request_body.length; i++) {
                            dos.writeByte(request_body[i]);
                        }
                        dos.flush();
                    }
                }else if(opc.equals("DELETE")){
                    hcon.setRequestMethod("DELETE");
                    hcon.setDoOutput(true);
                    hcon.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                }

                dis = new DataInputStream(hcon.getInputStream());
                int ch;
                while ((ch = dis.read()) != -1) {
                    responseMessage.append((char) ch);
                }
                int status = hcon.getResponseCode();
                switch (status) {
                    case HttpURLConnection.HTTP_OK:
                        System.out.println("HTTP 200:OK");
                        break;
                    case HttpURLConnection.HTTP_NO_CONTENT:
                        System.out.println("HTTP 204:La respuesta no trae ningun contenido");
                        break;
                    default:
                        hcon.disconnect();
                        throw new IOException("Response status not OK:" + status);
                }
            } while (redirect == true && redirectTimes < 5);
            if (redirectTimes == 5) {
                throw new IOException("Too much redirects");
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.append("ERROR: ");
        } finally {
            try {
                if (hcon != null) {
                    hcon.disconnect();
                }
                if (dis != null) {
                    dis.close();
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        return responseMessage.toString();
    }
}
