package com.example.enrique.protectfinal.Model;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Song implements Serializable {

    private int song_id;
    private int id_album;
    private int id_author;
    private String song_uri;
    private String title;
    private String genere;
    private int duration;
    private Bitmap albumImg;

    public Song(int song_id, int id_album, int id_author, String song_uri, String title, String genere, int duration, Bitmap albumImg){
        this.song_id = song_id;
        this.id_album = id_album;
        this.id_author = id_author;
        this.song_uri = song_uri;
        this.title = title;
        this.genere = genere;
        this.duration = duration;
        this.albumImg = albumImg;
    }

    public int getSong_id() {
        return song_id;
    }

    public void setSong_id(int song_id) {
        this.song_id = song_id;
    }

    public int getId_album() {
        return id_album;
    }

    public void setId_album(int id_album) {
        this.id_album = id_album;
    }

    public int getId_author() {
        return id_author;
    }

    public void setId_author(int id_author) {
        this.id_author = id_author;
    }

    public String getSong_uri() {
        return song_uri;
    }

    public void setSong_uri(String song_uri) {
        this.song_uri = song_uri;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Bitmap getAlbumImg() {
        return albumImg;
    }

    public void setAlbumImg(Bitmap albumImg) {
        this.albumImg = albumImg;
    }
}
