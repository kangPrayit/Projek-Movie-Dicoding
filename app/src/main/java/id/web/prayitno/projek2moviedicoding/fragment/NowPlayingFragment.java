package id.web.prayitno.projek2moviedicoding.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import id.web.prayitno.projek2moviedicoding.R;


public class NowPlayingFragment extends Fragment {

    public static final String TITLE = "Now Playing";

    public static NowPlayingFragment newInstance() {
        return new NowPlayingFragment();
    }

    public NowPlayingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_now_playing, container, false);
    }

}
