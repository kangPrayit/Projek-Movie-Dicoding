package id.web.prayitno.projek2moviedicoding.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.web.prayitno.projek2moviedicoding.R;
import id.web.prayitno.projek2moviedicoding.adapter.CardViewMovieAdapter;
import id.web.prayitno.projek2moviedicoding.model.Movie;
import id.web.prayitno.projek2moviedicoding.network.MyAsyncTaskLoader;

/**
 * A simple {@link Fragment} subclass.
 */
public class CariFilmFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<ArrayList<Movie>> {

    public static final String TITLE = "Cari Film";

    @BindView(R.id.et_cari_film_ganteng)
    EditText etCariFilm;
    @BindView(R.id.btn_cari_film)
    Button btnCariFilm;
    @BindView(R.id.rv_hasil_cari_film)
    RecyclerView rvHasilCariFilm;
    @BindView(R.id.shimmer_container)
    ShimmerFrameLayout mShimmerContainer;

    ArrayList<Movie> mMoviesCariFilm;
    CardViewMovieAdapter mCardViewMovieAdapter;
    String kataKunci = "";

    public static final String SEARCH_MOVIE_KEYWORD = "keyword";


    public static CariFilmFragment newInstance() {
        return new CariFilmFragment();
    }

    public CariFilmFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cari_film, container, false);
        ButterKnife.bind(this, view);

        mShimmerContainer.setVisibility(View.GONE);

        mMoviesCariFilm = new ArrayList<>();
        mCardViewMovieAdapter = new CardViewMovieAdapter(getContext(), mMoviesCariFilm, getFragmentManager().beginTransaction());
        rvHasilCariFilm.setLayoutManager(new LinearLayoutManager(getContext()));
        rvHasilCariFilm.setAdapter(mCardViewMovieAdapter);

        btnCariFilm.setOnClickListener(myClickListener);
        if (getArguments() != null) {
            kataKunci = getArguments().getString("EMBUH");
        }
        if (kataKunci != null) {
            etCariFilm.setText(kataKunci);
            String kota = etCariFilm.getText().toString();
            Bundle bundle = new Bundle();
            bundle.putString(SEARCH_MOVIE_KEYWORD, kataKunci);

            getLoaderManager().initLoader(0, bundle, this);
        }

        return view;
    }

    private View.OnClickListener myClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String movieTitle = etCariFilm.getText().toString();
            if (movieTitle.isEmpty()) {
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putString(SEARCH_MOVIE_KEYWORD, movieTitle);

            getLoaderManager().restartLoader(0, bundle, CariFilmFragment.this);
        }
    };

    @NonNull
    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int id, @Nullable Bundle args) {
        String searchMovieTitle = "";
        if (args != null) {
            searchMovieTitle = args.getString(SEARCH_MOVIE_KEYWORD);
        }
        return new MyAsyncTaskLoader(getContext(), searchMovieTitle);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Movie>> loader, ArrayList<Movie> data) {
        if (data.size() == 0){
            Toast.makeText(getContext(), "Movie Title Not Found!", Toast.LENGTH_SHORT).show();
        } else {
            mMoviesCariFilm.clear();
            mMoviesCariFilm.addAll(data);
            mCardViewMovieAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Movie>> loader) {
        mMoviesCariFilm.clear();
        mCardViewMovieAdapter.notifyDataSetChanged();
    }

}
