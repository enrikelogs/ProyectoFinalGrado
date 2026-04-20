package com.example.enrique.protectfinal.BDD;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import com.example.enrique.protectfinal.App.SearchFragment;
import com.example.enrique.protectfinal.Model.Album;
import com.example.enrique.protectfinal.Model.Artist;
import com.example.enrique.protectfinal.Model.SearchedItem;
import com.example.enrique.protectfinal.Model.Song;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import static com.example.enrique.protectfinal.BDD.Api.ALBUMIMG_API_URL;
import static com.example.enrique.protectfinal.BDD.Api.API_URL;
import static com.example.enrique.protectfinal.BDD.Api.ARTISTIMG_API_URL;
import static com.example.enrique.protectfinal.BDD.Api.CODE_GET_REQUEST;
import static com.example.enrique.protectfinal.BDD.Api.CODE_POST_REQUEST;
import static com.example.enrique.protectfinal.BDD.Api.GET_SONG;

public class MySQLHelper {

    public MySQLHelper(){

    }

    public Song getSongData(int song_id){//Siempre le pasare un id aunque haya varias canciones con el mismo titulo
        HashMap<String, String> params = new HashMap<>();
        params.put("idSong", String.valueOf(song_id));

        //PerformNetworkRequest request = new PerformNetworkRequest(GET_SONG, CODE_POST_REQUEST);//de prueba
        String apiUrl = API_URL+"getSong";
        PerformNetworkRequest request = new PerformNetworkRequest(apiUrl, params, CODE_POST_REQUEST);
        try {
            String result = String.valueOf(request.execute(apiUrl).get());//convertimos el resultado a string para poder formatearlo en json
            Log.i("DATOS", "JJJJ"+result);
            JSONObject jObject = new JSONObject(result);//Aquí parseo los datos recogidos en php a json
            //JSONArray object = jObject.getJSONArray("result");//Aquí obtengo los datos del JSONObject
            Log.i("DATOS", "cosa "+jObject.getInt("idSong"));

            Song song = new Song(
                    jObject.getInt("idSong"),
                    jObject.getInt("idAlbum"),
                    jObject.getInt("idAuthor"),
                    jObject.getString("uriSong"),
                    jObject.getString("title"),
                    jObject.getString("genereSong"),
                    jObject.getInt("duration"),
                    img(ALBUMIMG_API_URL+jObject.getString("albumImg"))
            );
            Log.i("DATOS", "URI:"+song.getSong_uri());
            return song;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Artist getArtistData(int id_artist){//Siempre le pasare un id aunque haya varias canciones con el mismo titulo
        HashMap<String, String> params = new HashMap<>();
        params.put("idArtist", String.valueOf(id_artist));
        //PerformNetworkRequest request = new PerformNetworkRequest(GET_SONG, CODE_POST_REQUEST);//de prueba
        String apiUrl = API_URL+"getArtist";
        PerformNetworkRequest request = new PerformNetworkRequest(apiUrl, params, CODE_POST_REQUEST);

        try {
            String result = String.valueOf(request.execute(apiUrl).get());//convertimos el resultado a string para poder formatearlo en json
            Log.i("DATOS", "AAAA"+result);
            JSONObject jObject = new JSONObject(result);//Aquí parseo los datos recogidos en php a json
            //JSONArray object = jObject.getJSONArray("result");//Aquí obtengo los datos del JSONObject
            Log.i("DATOS", jObject.getString("idArtist"));

            Artist artist = new Artist(
                    jObject.getInt("idArtist"),
                    jObject.getString("artistName"),
                    convertDate(jObject.getString("artistDate")),
                    img(ARTISTIMG_API_URL+jObject.getString("artistImg"))
            );
            Log.i("DATOS", "URI:"+artist.getArtist_name());
            return artist;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<SearchedItem> getArtistList(int id_author){
        ArrayList<SearchedItem> arrResults = new ArrayList();
        HashMap<String, String> params = new HashMap<>();
        params.put("idAuthor", String.valueOf(id_author));

        //PerformNetworkRequest request = new PerformNetworkRequest(GET_SONG, CODE_POST_REQUEST);//de prueba
        String apiUrl = API_URL+"getListArtist";
        PerformNetworkRequest request = new PerformNetworkRequest(apiUrl, params, CODE_POST_REQUEST);

        try {
            String result = String.valueOf(request.execute(apiUrl).get());//convertimos el resultado a string para poder formatearlo en json
            Log.i("DATOS", "ArtistList"+result);
            JSONArray object = new JSONArray(result);//Aquí obtengo los datos del JSONArray
            for (int i = 0; i < object.length(); i++) {
                SearchedItem item = new SearchedItem(
                        object.getJSONObject(i).getInt("idArtist"),
                        object.getJSONObject(i).getInt("idAuthor"),
                        object.getJSONObject(i).getString("artistName"));
                arrResults.add(item);//Items con el mismo nombre de la busqueda
                //Log.i("DATOS", "cosa "+object.getJSONObject(i).getInt("id"));
                Log.i("DATOS", "cosa "+arrResults.get(i).getTitle());
            }
            return arrResults;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<SearchedItem> getAlbumList(int id_artist){
        ArrayList<SearchedItem> arrResults = new ArrayList();
        HashMap<String, String> params = new HashMap<>();
        params.put("idArtist", String.valueOf(id_artist));

        //PerformNetworkRequest request = new PerformNetworkRequest(GET_SONG, CODE_POST_REQUEST);//de prueba
        String apiUrl = API_URL+"getAlbumList";
        PerformNetworkRequest request = new PerformNetworkRequest(apiUrl, params, CODE_POST_REQUEST);

        try {
            String result = String.valueOf(request.execute(apiUrl).get());//convertimos el resultado a string para poder formatearlo en json
            Log.i("DATOS", "AlbumList"+result);
            JSONArray object = new JSONArray(result);//Aquí obtengo los datos del JSONArray
            for (int i = 0; i < object.length(); i++) {
                //object.getJSONObject(i).getString("albumImg");
                SearchedItem item = new SearchedItem(
                        object.getJSONObject(i).getInt("idAlbum"),
                        object.getJSONObject(i).getString("albumName"),
                        img(ALBUMIMG_API_URL+object.getJSONObject(i).getString("albumImg"))
                );
                arrResults.add(item);//Items con el mismo nombre de la busqueda
                //Log.i("DATOS", "IMG URL "+arrResults.get(i).getImg());
                //Log.i("DATOS", "cosa "+object.getJSONObject(i).getInt("id"));
                Log.i("DATOS", "cosa "+arrResults.get(i).getTitle());
            }
            return arrResults;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Album getAlbumData(int id_album){//Siempre le pasare un id aunque haya varias canciones con el mismo titulo
        HashMap<String, String> params = new HashMap<>();
        params.put("idAlbum", String.valueOf(id_album));

        //PerformNetworkRequest request = new PerformNetworkRequest(GET_SONG, CODE_POST_REQUEST);//de prueba
        String apiUrl = API_URL+"getAlbum";
        PerformNetworkRequest request = new PerformNetworkRequest(apiUrl, params, CODE_POST_REQUEST);

        try {
            String result = String.valueOf(request.execute(apiUrl).get());//convertimos el resultado a string para poder formatearlo en json
            Log.i("DATOS", "BBBB"+result);
            JSONObject jObject = new JSONObject(result);//Aquí parseo los datos recogidos en php a json
            //JSONArray object = jObject.getJSONArray("result");//Aquí obtengo los datos del JSONObject
            Log.i("DATOS", "cosa "+jObject.getInt("idAlbum"));

            Album album = new Album(
                    jObject.getInt("idAlbum"),
                    jObject.getString("albumName"),
                    jObject.getString("albumGenere"),
                    convertDate(jObject.getString("release")),
                    img(ALBUMIMG_API_URL+jObject.getString("albumImg"))
            );
            Log.i("DATOS", "Album:"+album.getRelease());
            return album;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList getAlbumSongs(int idAlbum){
        ArrayList<SearchedItem> arrResults = new ArrayList();
        HashMap<String, String> params = new HashMap<>();
        params.put("idAlbum", String.valueOf(idAlbum));

        String apiUrl = API_URL+"getAlbumSongs";
        PerformNetworkRequest request = new PerformNetworkRequest(apiUrl, params, CODE_POST_REQUEST);

        try {
            String result = String.valueOf(request.execute(apiUrl).get());//convertimos el resultado a string para poder formatearlo en json
            Log.i("DATOS", "LLLL"+result);
            JSONArray object = new JSONArray(result);//Aquí obtengo los datos del JSONArray
            for (int i = 0; i < object.length(); i++) {
                SearchedItem item = new SearchedItem(
                        object.getJSONObject(i).getInt("id"),
                        object.getJSONObject(i).getString("title"));
                arrResults.add(item);//Items con el mismo nombre de la busqueda
                //Log.i("DATOS", "cosa "+object.getJSONObject(i).getInt("id"));
                Log.i("DATOS", "cosa "+arrResults.get(i).getTitle());
            }
            return arrResults;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList getSearchedItems(String txt){
        String imgUrl;
        ArrayList<SearchedItem> arrResults = new ArrayList();
        HashMap<String, String> params = new HashMap<>();
        params.put("str", txt);
        params.put("num", String.valueOf(0));//incrementa cada vez que le doy a acargar resultados

        String apiUrl = API_URL+"searching";
        PerformNetworkRequest request = new PerformNetworkRequest(apiUrl, params, CODE_POST_REQUEST);

        try {
            String result = String.valueOf(request.execute(apiUrl).get());//convertimos el resultado a string para poder formatearlo en json
            Log.i("DATOS", "SSSS"+result);
            JSONArray object = new JSONArray(result);//Aquí obtengo los datos del JSONArray
            for (int i = 0; i < object.length(); i++) {
                Log.i("DATOS", "componente "+object.getJSONObject(i).getString("we"));
                //imgUrl = object.getJSONObject(i).getString("we");
                if (object.getJSONObject(i).getString("we").length() == "artist".length()) {
                    imgUrl = ARTISTIMG_API_URL;
                } else {
                    imgUrl = ALBUMIMG_API_URL;
                }
                SearchedItem item = new SearchedItem(
                        object.getJSONObject(i).getInt("id"),
                        object.getJSONObject(i).getString("name"),
                        object.getJSONObject(i).getString("we"),
                        img(imgUrl+object.getJSONObject(i).getString("img"))
                );
                arrResults.add(item);//Items con el mismo nombre de la busqueda
                //Log.i("DATOS", "cosa "+object.getJSONObject(i).getInt("id"));
                Log.i("DATOS", "cosa "+imgUrl+object.getJSONObject(i).getString("img"));
            }
            return arrResults;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getNumRegisters(){
        String apiUrl = API_URL+"getNumRegisters";
        PerformNetworkRequest request = new PerformNetworkRequest(apiUrl, null, CODE_GET_REQUEST);

        try {
            String result = String.valueOf(request.execute(apiUrl).get());
            Log.i("DATOS", "numero"+result);
            JSONObject jObject = new JSONObject(result);
            Log.i("DATOS", "cosa "+jObject.getInt("numRegisters"));

            return jObject.getInt("numRegisters");
        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Date convertDate(String str){

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date = sdf.parse(str);
            Log.i("DATOS", "fecha:"+date);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Bitmap img(String imgUrl){
        //String apiUrl = "http://cam0n.tk/proyecto_final_dam/images/imgsAlbum/endlessmotion.jpeg";//+str;
        Bitmap bmap;
        PerformNetworkRequest request = new PerformNetworkRequest(imgUrl, null, 3);

        try {
            bmap = (Bitmap) request.execute(imgUrl).get();//convertimos el resultado a string para poder formatearlo en json
            Log.i("DATOS", "peticion"+bmap);
            //result = Base64.encodeToString(result.getBytes(), Base64.DEFAULT);
            //Log.i("DATOS", "resultEncode"+result);
            //byte [] encodeByte= Base64.decode(result,Base64.DEFAULT);
            //Log.i("DATOS", "encodeByte"+encodeByte);
            //bmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);

            //Log.i("DATOS", "ALBUM:"+BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length));
            return bmap;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
