package com.example.enrique.protectfinal.App;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.enrique.protectfinal.BDD.MySQLHelper;
import com.example.enrique.protectfinal.Model.Album;
import com.example.enrique.protectfinal.Model.SearchedItem;
import com.example.enrique.protectfinal.Model.SongItemAdapter;
import com.example.enrique.protectfinal.R;

import java.util.ArrayList;
import java.util.Date;

public class AlbumFragment extends Fragment {

    private ImageView ivAlbum;
    private TextView tvTitle;
    private TextView tvRelease;
    private TextView tvArtistName;
    private ListView lvAlbumSongs;
    private ArrayList<SearchedItem> arrAlbumSongs;

    private OnFragmentInteractionListener mListener;

    public AlbumFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_album, container, false);

        tvTitle = view.findViewById(R.id.tvAlbumTitle);
        tvRelease = view.findViewById(R.id.tvRelease);
        tvArtistName = view.findViewById(R.id.tvAlbumFrom);
        lvAlbumSongs = view.findViewById(R.id.lvAlbumList);
        ivAlbum = view.findViewById(R.id.ivAlbum);

        Album album = (Album) getArguments().getSerializable("albumData");
        arrAlbumSongs = (ArrayList<SearchedItem>) getArguments().getSerializable("albumSongs");
        Log.i("PLAY", "BULU:" + arrAlbumSongs);
        ivAlbum.setImageBitmap(album.getAlbum_img());
        tvTitle.setText(album.getAlbum_name());
        tvRelease.setText(String.valueOf(album.getRelease()));

        SongItemAdapter songItemAdapter = new SongItemAdapter(this.getContext(), arrAlbumSongs);

        lvAlbumSongs.setAdapter(songItemAdapter);

        lvAlbumSongs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((MainActivity) getActivity()).loadFragment("songFragment", arrAlbumSongs.get(position).getId());
            }
        });

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
