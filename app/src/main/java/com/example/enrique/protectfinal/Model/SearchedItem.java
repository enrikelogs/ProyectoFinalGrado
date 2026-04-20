package com.example.enrique.protectfinal.Model;

import android.graphics.Bitmap;

import java.io.Serializable;

public class SearchedItem implements Serializable {

    private String title;
    private int id;
    private int id2;
    private  String component;
    private Bitmap img;

    public SearchedItem(int id, String title, String component, Bitmap img){//Cuando busco en la barra de busqueda
        this.id = id;
        this.title = title;
        this.component = component;
        this.img = img;
    }

    public SearchedItem(int id, String title){//Cuando cargo lista de canciones de un album
        this.id = id;
        this.title = title;
    }

    public SearchedItem(int idArtist, int idAuthor, String title){//Cuando cargo lista de artistas de una cancion
        this.id = idArtist;
        this.id2 = idAuthor;
        this.title = title;
    }

    public SearchedItem(int id, String title, Bitmap img){//Cuando cargo lista de albumes de un artista
        this.id = id;
        this.title = title;
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public Bitmap getImg() {
        return img;
    }

    public String getComponent() {
        return component;
    }
}
