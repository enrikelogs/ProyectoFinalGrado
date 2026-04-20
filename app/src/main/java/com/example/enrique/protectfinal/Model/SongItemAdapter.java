package com.example.enrique.protectfinal.Model;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.enrique.protectfinal.R;

import java.util.ArrayList;

public class SongItemAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<SearchedItem> arrAlbumSongs;

    public SongItemAdapter(Context context, ArrayList arrAlbumSongs){
        this.context = context;
        this.arrAlbumSongs = arrAlbumSongs;
    }

    @Override
    public int getCount() {
        return arrAlbumSongs.size();
    }

    @Override
    public Object getItem(int position) {
        return arrAlbumSongs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.layout_songitem, null);
        //ImageView ivImg = view.findViewById(R.id.ivImg);
        TextView tvTitle = view.findViewById(R.id.tvSongTitle);
        //TextView tvComponent = view.findViewById(R.id.tvComponent);
        Log.i("PLAY", "BULU:" + arrAlbumSongs);
        //ivImg.setImageBitmap();
        tvTitle.setText(arrAlbumSongs.get(position).getTitle());
        //tvComponent.setText(arrAlbumSongs.get(position).getComponent());
        return view;
    }
}
