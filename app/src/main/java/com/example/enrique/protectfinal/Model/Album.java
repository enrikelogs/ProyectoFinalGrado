package com.example.enrique.protectfinal.Model;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.Date;

public class Album implements Serializable {
    private int id_album;
    private String album_name;
    private String genere;
    private Date release;
    private Bitmap album_img;

    public Album(int id_album, String album_name, String genere, Date release, Bitmap album_img){
        this.id_album = id_album;
        this.album_name = album_name;
        this.genere = genere;
        this.release = release;
        this.album_img = album_img;
    }

    public int getId_album() {
        return id_album;
    }

    public String getAlbum_name() {
        return album_name;
    }

    public String getGenere() {
        return genere;
    }

    public Date getRelease() {
        return release;
    }

    public Bitmap getAlbum_img() {
        return album_img;
    }
}
