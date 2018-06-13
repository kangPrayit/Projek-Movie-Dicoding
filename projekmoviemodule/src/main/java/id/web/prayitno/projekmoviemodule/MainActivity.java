package id.web.prayitno.projekmoviemodule;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.web.prayitno.projekmoviemodule.adapter.MovieCursorAdapter;

import static id.web.prayitno.projekmoviemodule.db.DatabaseContract.CONTENT_URI;

public class MainActivity extends AppCompatActivity
    implements LoaderManager.LoaderCallbacks<Cursor>{
    @BindView(R.id.lv_favorite)
    ListView lvFavorite;

    private MovieCursorAdapter mMovieCursorAdapter;
    private int LOAD_FAV_ID = 110;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mMovieCursorAdapter = new MovieCursorAdapter(this, null, true);
        lvFavorite.setAdapter(mMovieCursorAdapter);

        getSupportLoaderManager().initLoader(LOAD_FAV_ID, null, this);

//        new LoadFavMoviesAsync().execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().initLoader(LOAD_FAV_ID, null, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getSupportLoaderManager().destroyLoader(LOAD_FAV_ID);
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
        Toast.makeText(this, "Jumlah Data : " + data.getCount(), Toast.LENGTH_SHORT).show();
        mMovieCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mMovieCursorAdapter.swapCursor(null);
    }

}
