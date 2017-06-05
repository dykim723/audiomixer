package com.team.audiomixer.controller;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static java.lang.Boolean.TRUE;

/**
 * Created by dykim on 2017-05-28.
 */

public class DBManager {

    public enum ServerAccessKey {
        USERS("users"), LOGIN("login"), JOIN("join"), POSTING("posting"), MIX("mix");

        final private String value;

        private ServerAccessKey(String value) {
            this.value = value;
        }

        public String getServerAccessKey(){
            return this.value;
        }
    };

    private static String makeDBURL(ServerAccessKey to) {
        String temp = Configuration.DBURL;

        return temp.concat(to.getServerAccessKey());
    }

    public static Boolean excutePost(ServerAccessKey key, JSONObject jsonParam) {
        URL url;
        HttpURLConnection connection = null;
        ByteArrayOutputStream baos = null;


        try {
            url = new URL(makeDBURL(key));

            connection = (HttpURLConnection)url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST"); // hear you are telling that it is a POST request, which can be changed into "PUT", "GET", "DELETE" etc.
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8"); // here you are setting the `Content-Type` for the data you are sending which is `application/json`
            connection.connect();


            //Send request
            DataOutputStream wr = new DataOutputStream(
                    connection.getOutputStream ());
            wr.writeBytes(jsonParam.toString());
            wr.flush();
            wr.close ();

            InputStream is;
            int response = connection.getResponseCode();
            if (response >= 200 && response <=399){
                //return is = connection.getInputStream();
                is = connection.getInputStream();
                baos = new ByteArrayOutputStream();
                byte[] byteBuffer = new byte[1024];
                byte[] byteData = null;
                int nLength = 0;
                while((nLength = is.read(byteBuffer, 0, byteBuffer.length)) != -1) {
                    baos.write(byteBuffer, 0, nLength);
                }
                byteData = baos.toByteArray();

                String res = new String(byteData);


                JSONObject responseJSON = new JSONObject(res);

                Boolean result = (Boolean) responseJSON.get("status");
                String description = (String) responseJSON.get("description");

                Log.i("DBManager", "DATA response = " + res);

                if(result) {
                    return true;
                } else{
                    return false;
                }

            } else {
                //return is = connection.getErrorStream();
                return false;
            }


        } catch (Exception e) {

            e.printStackTrace();
            return false;

        } finally {

            if(connection != null) {
                connection.disconnect();
            }
        }
    }


    public static String excuteGet() throws IOException {
        URL url = null;
        BufferedReader reader = null;
        StringBuilder stringBuilder;

        try
        {
            // create the HttpURLConnection
            url = new URL(Configuration.DBURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // just want to do an HTTP GET here
            connection.setRequestMethod("GET");

            // uncomment this if you want to write output to this url
            //connection.setDoOutput(true);

            // give it 15 seconds to respond
            connection.setReadTimeout(15*1000);
            connection.connect();

            // read the output from the server
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            stringBuilder = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null)
            {
                stringBuilder.append(line + "\n");
            }
            return stringBuilder.toString();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw e;
        }
        finally
        {
            // close the reader; this can throw an exception too, so
            // wrap it in another try/catch block.
            if (reader != null)
            {
                try
                {
                    reader.close();
                }
                catch (IOException ioe)
                {
                    ioe.printStackTrace();
                }
            }
        }

    }
}
