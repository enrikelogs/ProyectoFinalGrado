package com.example.enrique.protectfinal.Model;

import android.provider.BaseColumns;

public class SongContract implements BaseColumns {
    public static final String TABLE_NAME ="song";

    public static final String SONG_ID = "id_song";
    public static final String ALBUM_ID = "id_album";
    public static final String TITLE = "title";
    public static final String SONG_GENERE = "songGenere";
}
