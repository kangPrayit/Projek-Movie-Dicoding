package id.web.prayitno.projek2moviedicoding.network;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.ArrayList;

import id.web.prayitno.projek2moviedicoding.model.Movie;

public class MyAsyncTaskLoader extends AsyncTaskLoader<ArrayList<Movie>> {
    private String searchMovieTitle;
    private boolean mHasResult = false;
    private ArrayList<Movie> mData;

    public MyAsyncTaskLoader(final Context context, String searchMovieTitle) {
        super(context);
        onContentChanged();
        this.searchMovieTitle = searchMovieTitle;
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged()) {
            forceLoad();
        } else if (mHasResult) {
            deliverResult(mData);
        }
    }

    @Nullable
    @Override
    public ArrayList<Movie> loadInBackground() {
        return ApiClient.searchMovies(searchMovieTitle);
    }

    @Override
    public void deliverResult(@Nullable ArrayList<Movie> data) {
        Log.e("CariFilmFragment", "Jumlah cari Film : " + data.size());

        mData = data;
        mHasResult = true;
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if (mHasResult) {
            mData = null;
            mHasResult = false;
        }
    }
}
