package com.example.enrique.protectfinal.Model;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.Date;
/*No necesito la clase author, porque ya hago la relación de lo que necesito en php con la querys*/

public class Artist implements Serializable {
    private int id_artist;
    private String artist_name;
    private Date date_artist;
    private Bitmap photo;

    public Artist(int id_artist, String artist_name, Date date_artist, Bitmap photo){
        this.id_artist = id_artist;
        this.artist_name = artist_name;
        this.date_artist = date_artist;
        this.photo = photo;
    }

    public int getId_artist() {
        return id_artist;
    }

    public String getArtist_name() {
        return artist_name;
    }

    public Date getDate_artist() {
        return date_artist;
    }

    public Bitmap getPhoto() {
        return photo;
    }
}
