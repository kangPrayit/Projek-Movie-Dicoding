package id.web.prayitno.projekmoviemodule;

import android.database.Cursor;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.web.prayitno.projekmoviemodule.adapter.CardViewMovieAdapter;
import id.web.prayitno.projekmoviemodule.adapter.MovieCursorAdapter;
import id.web.prayitno.projekmoviemodule.model.Movie;

import static id.web.prayitno.projekmoviemodule.db.DatabaseContract.CONTENT_URI;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{
    @BindView(R.id.rv_fav_movie)
    RecyclerView rvFavMovie;
    @BindView(R.id.lv_favorite)
    ListView lvFavMovies;

    ArrayList<Movie> favMovies;
    CardViewMovieAdapter cardViewMovieAdapter;

    MovieCursorAdapter mMovieCursorAdapter;
    private int LOAD_FAV_ID = 110;


    private Cursor listMoviesCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // Load Data From Content Provider
        favMovies = new ArrayList<>();
//        getContentResolver().query()

        cardViewMovieAdapter = new CardViewMovieAdapter(getApplicationContext());
        rvFavMovie.setLayoutManager(new LinearLayoutManager(this));
        rvFavMovie.setAdapter(cardViewMovieAdapter);

//        mMovieCursorAdapter = new MovieCursorAdapter(this, null, true);
//        lvFavMovies.setAdapter(mMovieCursorAdapter);
//
//        getSupportLoaderManager().initLoader(LOAD_FAV_ID, null, this);

        new LoadFavMoviesAsync().execute();
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(
                this,
                CONTENT_URI,
                null,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        mMovieCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mMovieCursorAdapter.swapCursor(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getSupportLoaderManager().destroyLoader(LOAD_FAV_ID);
    }

    private class LoadFavMoviesAsync extends AsyncTask<Void, Void, Cursor>{

        @Override
        protected Cursor doInBackground(Void... voids) {
            return getContentResolver().query(
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
            listMoviesCursor = cursor;
            cardViewMovieAdapter.setListMovies(listMoviesCursor);
            cardViewMovieAdapter.notifyDataSetChanged();

            if (listMoviesCursor.getCount() == 0){
                Toast.makeText(MainActivity.this, "Tidak Ada data saat ini", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
