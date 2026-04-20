package com.example.enrique.protectfinal.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.enrique.protectfinal.R;

import java.util.ArrayList;

public class SearchedItemAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<SearchedItem> arrResults;

    public SearchedItemAdapter(Context context, ArrayList arrResults){
        this.context = context;
        this.arrResults = arrResults;
    }

    @Override
    public int getCount() {
        return arrResults.size();
    }

    @Override
    public Object getItem(int position) {
        return arrResults.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.layout_searcheditem, null);

        TextView tvTitle = view.findViewById(R.id.tvTitle);
        TextView tvComponent = view.findViewById(R.id.tvComponent);
        ImageView ivImg = view.findViewById(R.id.ivImg);

        ivImg.setImageBitmap(arrResults.get(position).getImg());
        tvTitle.setText(arrResults.get(position).getTitle());
        tvComponent.setText(arrResults.get(position).getComponent());
        return view;
    }
}
