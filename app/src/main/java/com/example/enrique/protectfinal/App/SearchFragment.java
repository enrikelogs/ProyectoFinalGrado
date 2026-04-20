package com.example.enrique.protectfinal.App;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.enrique.protectfinal.Model.Album;
import com.example.enrique.protectfinal.Model.SearchedItem;
import com.example.enrique.protectfinal.Model.SearchedItemAdapter;
import com.example.enrique.protectfinal.R;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    private ListView lvResults;
    private Button btnLoad;
    private TextView tvNoResults;
    private ArrayList<SearchedItem> arrResults;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        lvResults = view.findViewById(R.id.lvResults);
        tvNoResults = view.findViewById(R.id.tvNoResults);

        if (getArguments() != null) {
            arrResults = (ArrayList<SearchedItem>) getArguments().getSerializable("searchedItem");
            Log.i("PLAY", "Resultados:" + arrResults);
            lvResults.setVisibility(View.VISIBLE);
            //btnLoad = view.findViewById(R.id.btnLoad);
            SearchedItemAdapter searchedItemAdapter = new SearchedItemAdapter(this.getContext(), arrResults);

            lvResults.setAdapter(searchedItemAdapter);

            lvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String nameFragment = new String();
                    //Bundle bundle = new Bundle();

                    switch (arrResults.get(position).getComponent()) {

                        case "song":
                            nameFragment = "songFragment";
                            break;
                        case "artist":
                            nameFragment = "artistFragment";
                            break;
                        case "album":
                            nameFragment = "albumFragment";
                            //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.principal, new AlbumFragment());
                            break;
                        default:
                            break;
                    }
                    ((MainActivity) getActivity()).loadFragment(nameFragment, arrResults.get(position).getId());
                }
            });
        }else{

            tvNoResults.setVisibility(View.VISIBLE);
            //tvNoResults.setText("No results");
        }
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
