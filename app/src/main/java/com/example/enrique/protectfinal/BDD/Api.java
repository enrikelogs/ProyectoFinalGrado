package com.example.enrique.protectfinal.BDD;

public class Api {
    public static final String ROOT_URL = "http://cam0n.tk/proyecto_final_dam/";
    //Rutas de las Api (URL de las canciones y URL de datos de las canciones)
    public static final String API_URL = ROOT_URL + "connection/Api.php?apicall=";
    public static final String MUSIC_API_URL = ROOT_URL + "music/";
    public static final String ALBUMIMG_API_URL = ROOT_URL + "images/imgsAlbum/";
    public static final String ARTISTIMG_API_URL = ROOT_URL + "images/imgsArtist/";

    public static final String GET_SONG = ROOT_URL + "proyecto_final_dam/connection/DbOperation.php";//prueba para ver si funciona

    //Codigos para acceder a un metodo o a otro
    public static final int CODE_GET_REQUEST = 0001;
    public static final int CODE_POST_REQUEST = 0002;
}
