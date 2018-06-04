package id.web.prayitno.projek2moviedicoding.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import id.web.prayitno.projek2moviedicoding.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CariFilmFragment extends Fragment {

    public static final String TITLE = "Cari Film";

    public static CariFilmFragment newInstance() {
        return new CariFilmFragment();
    }

    public CariFilmFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cari_film, container, false);
    }

}
