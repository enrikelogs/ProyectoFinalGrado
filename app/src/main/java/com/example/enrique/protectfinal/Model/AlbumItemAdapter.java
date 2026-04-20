package com.example.enrique.protectfinal.Model;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.enrique.protectfinal.R;

import java.util.ArrayList;

public class AlbumItemAdapter extends BaseAdapter {
    Context context;
    ArrayList<SearchedItem> arrAlbums;

    public AlbumItemAdapter(Context context, ArrayList arrAlbums){
        this.context = context;
        this.arrAlbums = arrAlbums;
    }
    @Override
    public int getCount() {
        return arrAlbums.size();
    }

    @Override
    public Object getItem(int position) {
        return arrAlbums.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.layout_albumitem, null);
        ImageView ivImg = view.findViewById(R.id.ivAlbumItem);
        TextView tvTitleAlbum = view.findViewById(R.id.tvAlbumItemTitle);

        Log.i("PLAY", "Resultados:" + arrAlbums.get(position).getTitle());
        tvTitleAlbum.setText(arrAlbums.get(position).getTitle());
        ivImg.setImageBitmap(arrAlbums.get(position).getImg());

        return view;
    }
}
