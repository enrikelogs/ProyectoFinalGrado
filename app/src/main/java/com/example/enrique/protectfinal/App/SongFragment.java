package com.example.enrique.protectfinal.App;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.enrique.protectfinal.Model.SearchedItem;
import com.example.enrique.protectfinal.Model.Song;
import com.example.enrique.protectfinal.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.Inflater;

public class SongFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private TextView tvTitle, tvArtist;
    private ImageView ivAlbum;
    Song song;
    ArrayList<SearchedItem> arrArtist;

    public SongFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_song, container, false);

        tvTitle = view.findViewById(R.id.tvTitle);
        tvArtist = view.findViewById(R.id.tvArtist);
        ivAlbum = view.findViewById(R.id.ivAlbum);

        if (getArguments() != null) {
            Log.i("PLAY", "Canción: " + ((Song) getArguments().getSerializable("songData")).getSong_uri());
            song = (Song) getArguments().getSerializable("songData");
            arrArtist = (ArrayList<SearchedItem>) getArguments().getSerializable("ArtistList");
            //int p = getArguments().getInt("prueba");
            //Log.i("PLAY", "PRUEBA: " + p);
        }else{
            Log.i("PLAY", "ARGUMENTOS VACIOS");
        }
        loadSongData();

        return view;
    }

    public void loadSongData(){
        ivAlbum.setImageBitmap(song.getAlbumImg());
        tvTitle.setText(song.getTitle());
        for (int i = 0; i < arrArtist.size(); i++) {
            if (i == 0){
                tvArtist.append(arrArtist.get(i).getTitle());
            } else {
                tvArtist.append(", ");
                tvArtist.append(arrArtist.get(i).getTitle());
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            //throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
