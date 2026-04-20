package com.example.enrique.protectfinal.App;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.enrique.protectfinal.BDD.MySQLHelper;
import com.example.enrique.protectfinal.Model.Album;
import com.example.enrique.protectfinal.Model.Artist;
import com.example.enrique.protectfinal.Model.SearchedItem;
import com.example.enrique.protectfinal.Model.Song;
import com.example.enrique.protectfinal.R;

import java.util.ArrayList;

import static com.example.enrique.protectfinal.BDD.Api.ALBUMIMG_API_URL;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private final MySQLHelper helper = new MySQLHelper();
    private final int numRegisters = helper.getNumRegisters();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //helper = new MySQLHelper();
        //Lo pongo para que en caso de no cargar ninguna canción,cargue una predefinida
        dataToFragmentSong(1);
        dataToFragmentController(1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        return true;
    }
    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        if (!newText.isEmpty()){
            dataToFragmentSearch(newText);
        }else {
            dataToFragmentSong(1);
            dataToFragmentController(1);
        }
        return false;
    }

    public Artist getDataArtist(int id){
        Artist artist = helper.getArtistData(id);
        Log.i("DATOS", artist.getArtist_name());
        return artist;
    }

    public void dataToFragmentArtist(int id){
        ArtistFragment artistFragment = new ArtistFragment();
        ArrayList<SearchedItem> arrAlbumList = helper.getAlbumList(id);
        Bundle bundle = new Bundle();
        Log.i("PLAY", "Album: "+arrAlbumList);
        bundle.putSerializable("artistData", getDataArtist(id));
        bundle.putSerializable("albumList", arrAlbumList);

        artistFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.principal, artistFragment).commit();
    }

    public Song getDataSong(int id){
        Song song = helper.getSongData(id);
        Log.i("DATOS", song.getSong_uri());
        return song;
    }

    public void dataToFragmentSong(int id){

        SongFragment songFragment = new SongFragment();
        Song song = helper.getSongData(id);
        ArrayList<SearchedItem> arrArtistList = helper.getArtistList(song.getId_author());
        Log.i("PLAY", "Artistas: "+arrArtistList);
        Bundle bundle = new Bundle();
        Log.i("PLAY", "Canción: "+song.getId_author());
        bundle.putSerializable("songData", song);
        bundle.putSerializable("ArtistList", arrArtistList);

        songFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.principal, songFragment).commit();
    }

    public void dataToFragmentController(int id){

        SongControllerFragment controller = new SongControllerFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable("songData", getDataSong(id));
        bundle.putInt("numRegisters", numRegisters);

        controller.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.controllerContainer, controller).commit();
    }

    public Album getDataAlbum(int id){
        Album album = helper.getAlbumData(id);
        //Log.i("DATOS", album.getAlbum_name());
        return album;
    }

    public void dataToFragmentAlbum(int id){
        AlbumFragment albumFragment = new AlbumFragment();
        ArrayList<SearchedItem> arrAlbumSongs = helper.getAlbumSongs(id);
        Bundle bundle = new Bundle();
        Log.i("PLAY", "Album: "+arrAlbumSongs);
        bundle.putSerializable("albumData", getDataAlbum(id));
        bundle.putSerializable("albumSongs", arrAlbumSongs);

        albumFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.principal, albumFragment).commit();
    }

    public void dataToFragmentSearch(String str){
        SearchFragment searchFragment = new SearchFragment();
        ArrayList<SearchedItem> arrItems = helper.getSearchedItems(str);
        if (arrItems == null){
            Toast.makeText(this, "No se encontraron resultdos", Toast.LENGTH_SHORT).show();
        }else {
            Log.i("PLAY", "Resultados:" + arrItems.get(0).getImg());
            Bundle bundle = new Bundle();
            //bundle.putSerializable("searchedItem", getDataSong(2));
            bundle.putSerializable("searchedItem", arrItems);
            searchFragment.setArguments(bundle);
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.principal, searchFragment).commit();
    }

    public void loadFragment(String nameFragment, int id){
        switch (nameFragment){
            case "songFragment":
                getDataSong(id);
                dataToFragmentController(id);
                dataToFragmentSong(id);
                break;
            case "artistFragment":
                getDataArtist(id);
                dataToFragmentArtist(id);
                break;
            case "albumFragment":
                getDataAlbum(id);
                dataToFragmentAlbum(id);
                break;
        }
    }
}
