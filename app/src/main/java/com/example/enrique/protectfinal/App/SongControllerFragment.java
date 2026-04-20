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
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.enrique.protectfinal.Model.SearchedItem;
import com.example.enrique.protectfinal.Model.Song;
import com.example.enrique.protectfinal.R;

import java.io.IOException;
import java.util.ArrayList;

import static com.example.enrique.protectfinal.BDD.Api.MUSIC_API_URL;

public class SongControllerFragment extends Fragment {

    private TextView tvCurrentTime, tvDuration;
    private ImageButton btnPlay, btnNext, btnPrev;
    private Boolean playing;
    private SeekBar seekBar;

    MediaPlayer mediaPlayer;
    private Runnable runnable;
    Song song;
    ArrayList<SearchedItem> arrArtist;
    String url = MUSIC_API_URL;
    int currentPosition;
    boolean firstPlay;

    public SongControllerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_song_controller, container, false);

        btnPlay = view.findViewById(R.id.btnPlay);
        btnNext = view.findViewById(R.id.btnNext);
        btnPrev = view.findViewById(R.id.btnPrev);
        seekBar = view.findViewById(R.id.seekBar);
        tvDuration = view.findViewById(R.id.tvDuration);
        tvCurrentTime = view.findViewById(R.id.tvCurrentTime);

        playing = false;
        firstPlay = true;
        if (getArguments() != null) {
            Log.i("PLAY", "Canción: " + ((Song) getArguments().getSerializable("songData")).getSong_uri());
            song = (Song) getArguments().getSerializable("songData");
            mediaPlayer = new MediaPlayer();
            btnPlay.setImageResource(R.drawable.play1);
            tvDuration.setText(formatTime(song.getDuration()));
            //loadSong(url.concat(song.getSong_uri()));
            //arrArtist = (ArrayList<SearchedItem>) getArguments().getSerializable("ArtistList");
            //int p = getArguments().getInt("prueba");
            //Log.i("PLAY", "PRUEBA: " + p);
        }else{
            Log.i("PLAY", "ARGUMENTOS VACIOS");
        }

        //prevSongs[25] = new String();


        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                mp.stop();
                mp.reset();
                Log.i("PLAY", "Oh vaya, Onerrorlistener");
                //playing = false;
                //firstPlay = false;
                return false;
            }
        });

        //Reinicio MediaPlayer cuando termine un audio o seleccione otro nuevo (Para que no se superpongan al reproducir)
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {//**Comprobar que le doy al boton play y reinicia la canción**
            //escribir la canción en el array prevSongs
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.stop();
                mp.reset();
                /*try {
                    mediaPlayer.setDataSource(url);
                    mediaPlayer.prepareAsync(); // might take long! (for buffering, etc)
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
                Log.i("PLAY", "Estoy en CompletionListener");
                playing = false;
                //firstPlay = true;
                nextSong();
                //play();
                //btnPlay.setText("PLAY");
            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("PLAY", String.valueOf(mediaPlayer) + " " + playing);

                if (mediaPlayer != null && !playing){
                    play();
                    playing = true;
                    //btnPlay.setImageResource(R.drawable.pause1);
                }else if(mediaPlayer != null && playing){
                    pause();
                    playing = false;
                    firstPlay = false;
                    btnPlay.setImageResource(R.drawable.play1);
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //if (mediaPlayer != null && !playing){

                //}else if(mediaPlayer != null && playing){
                    nextSong();
                //}
            }
        });

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //playProgress();

        return view;
        //return inflater.inflate(R.layout.fragment_song_controller, container, false);
    }

    public void loadSong(String url){
        //Paso las url de las canciones a reproducir y prepararlas para comenzar a reproducirlas
        Log.i("PLAY", "CARGANDO CANCIÓN...");
        try {
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepareAsync(); // might take long! (for buffering, etc)
            seekBar.setMax(song.getDuration()*1000);//mediaPlayer.getDuration()-En este caso coge el tiempo maximo que puede albergar un objeto MediaPlayer
            //tvDuration.setText(formatTime(song.getDuration()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //mediaPlayer.seekTo(mediaPlayer.getCurrentPosition());
        //btnPlay.setText("PLAY");
    }

    public void play(){//**no se si esta bien declararla final la variable position, pero si no da error**
        //Antes debré comprobar si la canción está en pausa o es nueva.
        //código cuando una canción se inicia por primera vez
        //código cuando reanudo la canción pausada.
        if (firstPlay){
            loadSong(url.concat(song.getSong_uri()));
            Log.i("PLAY", "Entro para comenzar a reproducir");
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    Log.i("PLAY", "playing = "+String.valueOf(playing));
                    Log.i("PLAY", "REPRODUCIENDO...");
                    //mp.seekTo(0);
                    btnPlay.setImageResource(R.drawable.pause1);
                    currentPosition = 0;
                    mp.start();
                    playProgress();
                }
            });
        }else{
            Log.i("PLAY", "REANUDANDO...");
            mediaPlayer.seekTo(mediaPlayer.getCurrentPosition());
            mediaPlayer.start();
            btnPlay.setImageResource(R.drawable.pause1);
        }
        playProgress();

    }

    public void playProgress(){
        //Avance de la barra de progreso y poder desplazar el puntero para que la canción continue
        //en el segundo en el que suelto el puntero (incluso si esta en pausa)
        Log.i("PLAY", "CARGADO BARRA DE PROGRESO");
        seekBar.setProgress(mediaPlayer.getCurrentPosition());

        if (mediaPlayer.isPlaying()){
            runnable = new Runnable() {
                @Override
                public void run() {
                    playProgress();
                }
            };
            seekBar.postDelayed(runnable, 1000);
        }

        //No se si es correcto que la seekBar esté aquí
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mediaPlayer.seekTo(progress);
                tvCurrentTime.setText(formatTime(progress/1000));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //Toast.makeText(getContext(), "Lo has tocadooo :O", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //Toast.makeText(getContext(), "Aaaah lo soltaste... ;D", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void pause(){
        //código cuando una canción se pausa. (deberá guardar la ultima posición, segundo, en que
        // se encuentra)
        Log.i("PLAY", "PAUSANDO");
        Log.i("PLAY", "playing = "+String.valueOf(playing));
        currentPosition = mediaPlayer.getCurrentPosition();
        mediaPlayer.pause();
        playProgress();
    }

    public void nextSong(){
        //Crear objeto cancion con un id aleatorio, recoger sus datos y pasar la url a loadSong().
        Log.i("PLAY", "ALGO");
        int num = getArguments().getInt("numRegisters");
        Log.i("DATOS", num +"");
        song = ((MainActivity) getActivity()).getDataSong(((int)Math.random()*num)+1);
        loadSong(url.concat(song.getSong_uri()));
    }

    public String formatTime(int time){
        int minutos = time/60;
        int segundos = time%60;
        if (segundos < 10){
            return minutos + ":0" + segundos;
        }
        return minutos + ":" + segundos;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
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
