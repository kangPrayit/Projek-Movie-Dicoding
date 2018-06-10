package id.web.prayitno.projek2moviedicoding.fragment;


import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.web.prayitno.projek2moviedicoding.R;
import id.web.prayitno.projek2moviedicoding.adapter.CardViewMovieAdapter;
import id.web.prayitno.projek2moviedicoding.db.MovieHelper;
import id.web.prayitno.projek2moviedicoding.model.Movie;

import static id.web.prayitno.projek2moviedicoding.db.DatabaseContract.CONTENT_URI;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {
    @BindView(R.id.rv_fav_movie)
    RecyclerView rvFavMovie;

    ArrayList<Movie> favMovies;
    CardViewMovieAdapter cardViewMovieAdapter;


    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        ButterKnife.bind(this, view);

        // Load Data From SQLite
        MovieHelper movieHelper = new MovieHelper(getContext());

        movieHelper.open();
        favMovies = movieHelper.getAllFavMovies();
        movieHelper.close();

        showRecycleCard();

        return view;
    }

    private void showRecycleCard() {
        rvFavMovie.setLayoutManager(new LinearLayoutManager(getContext()));
        cardViewMovieAdapter = new CardViewMovieAdapter(getContext(), favMovies, getFragmentManager().beginTransaction());
        rvFavMovie.setAdapter(cardViewMovieAdapter);
    }

    private class LoadMovieAsync extends AsyncTask<Void, Void, Cursor>{

        @Override
        protected Cursor doInBackground(Void... voids) {
            return getContext().getContentResolver().query(
                    CONTENT_URI,
                    null,
                    null,
                    null,
                    null
            );
        }
    }

}
