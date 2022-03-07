package com.example.softwaredevelopmentverificationtask.Utils;

import android.icu.text.Edits;
import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class TelnetClient {
     private static Socket socket;
     private static InputStream response;
     private static OutputStream request;
    public static String executeHTTPrequest_POST(String URL, HashMap dataHash){
        // in that method we making simple HTTP request
        boolean tryToConnect = true;
        String res ="";
        if (URL.contains("http")&&URL!=null) {
            while (tryToConnect) {
                try {
                  socket = new Socket(urlParser(URL)[0],80);
                    if (socket!=null) {
                        response = socket.getInputStream();
                        request = socket.getOutputStream();

                    }
                } catch (IOException e) {
                   e.printStackTrace();
               }
                //here we making our HTTP request and saving it in array with bytes "data"
                byte[] data = ("GET "+urlParser(URL)[1]+" HTTP/1.1 \n"
                              +"Host: "+urlParser(URL)[0]+"\n"
                              +"Connection: close\n"
                              +"Content-type: aplication/json\n"
                              + "Content-length: "+new JSONObject(dataHash).toString().length()+"\n\n"
                              + new JSONObject(dataHash).toString()+"\n"
                              ).getBytes(StandardCharsets.UTF_8);
                if (request!=null&&response!=null) {
                    tryToConnect=false;
                    try {
                            request.write(data);
                        int c=0;
                        while ((c=response.read())!=-1){
                            res=res+String.valueOf((char)c);
                        }
                        Log.i("TELNET","HTTP response----------------------------\n"+res);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if(tryToConnect){
                    Log.i("TELNET","trying to connect");
                }
            }
        }else {
            Log.i("TELNET","wrong url");
        }
        return res;
    }
    //the method witch split host and URI
    private static String[] urlParser(String URL){
        String tmpHost =  URL.substring(0,URL.indexOf("/",7)+1);
        String tmpUrl = URL.replace(tmpHost,"");
        tmpUrl= tmpUrl.replaceFirst("/","#");
        String [] tmpStrgs =tmpUrl.split("#") ;
        tmpHost = tmpStrgs[0];
        tmpUrl=tmpStrgs[1];
        String[] result = new String[]{tmpHost,tmpUrl};
        return result;
    }

}
