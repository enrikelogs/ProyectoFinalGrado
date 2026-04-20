package com.example.enrique.protectfinal.App;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.enrique.protectfinal.Model.AlbumItemAdapter;
import com.example.enrique.protectfinal.Model.Artist;
import com.example.enrique.protectfinal.Model.SearchedItem;
import com.example.enrique.protectfinal.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ArtistFragment extends Fragment {

    ImageView ivArtist;
    TextView tvName;
    TextView tvYear;
    GridView gvAlbumList;

    ArrayList<SearchedItem> arrAlbumList;

    private OnFragmentInteractionListener mListener;

    public ArtistFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_artist, container, false);

        ivArtist = view.findViewById(R.id.ivArtist);
        gvAlbumList = view.findViewById(R.id.gvAlbumList);
        tvName = view.findViewById(R.id.tvName);
        tvYear = view.findViewById(R.id.tvYear);

        Artist artist = (Artist) getArguments().getSerializable("artistData");
        Log.i("PLAY", "artistFragment:" + artist.getArtist_name());
        arrAlbumList = (ArrayList<SearchedItem>) getArguments().getSerializable("albumList");
        Log.i("PLAY", "artistfragmentAlbums:" + arrAlbumList.get(0).getTitle());
        ivArtist.setImageBitmap(artist.getPhoto());
        tvName.setText(artist.getArtist_name());
        tvYear.setText(String.valueOf(artist.getDate_artist()));
//new SimpleDateFormat("yyyy/MM/dd").format(artist.getDate_artist())
        AlbumItemAdapter albumItemAdapter = new AlbumItemAdapter(this.getContext(), arrAlbumList);

        gvAlbumList.setAdapter(albumItemAdapter);

        gvAlbumList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((MainActivity) getActivity()).loadFragment("albumFragment", arrAlbumList.get(position).getId());
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
