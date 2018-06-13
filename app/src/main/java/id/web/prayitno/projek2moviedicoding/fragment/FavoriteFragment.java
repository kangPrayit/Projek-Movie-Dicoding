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
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.web.prayitno.projek2moviedicoding.R;
import id.web.prayitno.projek2moviedicoding.adapter.CardViewMoveCursorAdapter;

import static id.web.prayitno.projek2moviedicoding.db.DatabaseContract.CONTENT_URI;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {
    @BindView(R.id.rv_fav_movie)
    RecyclerView rvFavMovie;

    Cursor favMoviesCursor;
    CardViewMoveCursorAdapter mCardViewMoveCursorAdapter;


    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        ButterKnife.bind(this, view);

        // Load Data From ContentProvider
        rvFavMovie.setLayoutManager(new LinearLayoutManager(getContext()));
        mCardViewMoveCursorAdapter = new CardViewMoveCursorAdapter(getContext(), favMoviesCursor, getFragmentManager().beginTransaction());
        rvFavMovie.setAdapter(mCardViewMoveCursorAdapter);

        new LoadFavMoviesAsync().execute();

        return view;
    }

    private class LoadFavMoviesAsync extends AsyncTask<Void, Void, Cursor>{

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

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            favMoviesCursor = cursor;
            mCardViewMoveCursorAdapter.setFavMovieList(favMoviesCursor);
            mCardViewMoveCursorAdapter.notifyDataSetChanged();
            if (favMoviesCursor.getCount() == 0){
                Toast.makeText(getContext(), "Tidak Ada Data Saat Ini", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
