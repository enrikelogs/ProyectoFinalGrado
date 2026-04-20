package com.example.enrique.protectfinal.BDD;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Base64InputStream;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class RequestHandler {

    public String sendPostRequest(String requestURL, HashMap<String, String> params){
        URL url;
        StringBuilder sb = new StringBuilder();
        try {
            url = new URL(requestURL);

            //Creamos y abrimos la conexión
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //Parámetros de conexón
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            ////MANDOAMOS LOS DATOS AL WS////
            //Creamos un stream de salida para pasar los datos que queramos a través de este stream
            OutputStream os = conn.getOutputStream();

            //Escribimos los parámetros en la petición
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(params));

            //Con flush escribimos los datos del writer en el stream de salida
            writer.flush();
            writer.close();
            os.close();

            ////RECOGEMOS LOS DATOS DE WS////
            //Obtenemos el código de respuesta del WS para confirmar que funciona correctamente
            int responseCode = conn.getResponseCode();
            //Si ha ido correctamente, recibiremos los datos correspondientes a la petición realizada al WS
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                //Obtengo la información del WS que almaceno en un BufferedReader a partir del stream de entrada.
                Log.i("CONEXION", "conexion y codigo 200");
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                sb = new StringBuilder();
                String response;
                //Vamos leyendo la respuesta y la vamos concatenando en nuestro builder.
                while ((response = br.readLine()) != null) {
                    sb.append(response);
                }

                Log.i("CONEXION", "Texto recibido: " + sb.toString());
            } else {
                Log.i("CONEXION", "conexion fallida" + responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    //Las peticiones con GET las utilizaré solo cuando extraiga datos del WS
    public String sendGetRequest(String requestURL) {
        URL url = null;
        int response = 0;
        try {
            Log.i("CONEXION", requestURL);
            url = new URL(requestURL);
            StringBuilder sb = new StringBuilder();
            String line = "";
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            response = conn.getResponseCode();
            Log.i("CONEXION", String.valueOf(response));
            if (response == HttpURLConnection.HTTP_OK){
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));//Obtengo la informacion del WS

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                Log.i("DATOS", sb.toString());
                return sb.toString();
            }else{
                return "Fallo en la conexión";
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        Log.i("DATOS", params.toString());
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first) {
                first = false;
            }else {
                result.append("&");
            }
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }

    public Bitmap downloadBitmap(String requestURL) {
        URL url = null;
        int response = 0;

        try {
            url = new URL(requestURL);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            response = urlConnection.getResponseCode();
            if (response == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = urlConnection.getInputStream();

                Log.i("PLAY", "BKÑJLJ" + url.getFile().getBytes());
                if (inputStream != null) {
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    Log.i("PLAY", "BULUMBU" + bitmap);
                    return bitmap;
                }
            }
            return  null;
        } catch (Exception e) {
            //urlConnection.disconnect();
            //Log.e("LoadImage class", "Descargando imagen desde url: " + url);
        }
        return null;
    }

    /*public String loadBitmap(String requestURL) {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(requestURL);
            conn = (HttpURLConnection) url.openConnection();
            int response = conn.getResponseCode();
            String line = "";

            if (response == HttpURLConnection.HTTP_OK) {
                String result = "";
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                InputStream in = conn.getInputStream();
                BitmapFactory.decodeStream(conn.getInputStream());
                while ((line = bufferedReader.readLine()) != null) {
                    result += Base64.encodeToString(line.getBytes(), Base64.DEFAULT);
                    //sb.append(line + "\n");
                }
                Log.i("DATOS", ""+BitmapFactory.decodeStream(conn.getInputStream()));
                return result;
            }else{
                return null;
            }
        } catch (Exception e) {
            conn.disconnect();
            Log.e("LoadImage class", "Descargando imagen desde url: " + requestURL);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return null;
    }*/
}
