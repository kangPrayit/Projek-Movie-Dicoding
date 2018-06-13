package id.web.prayitno.projek2moviedicoding.Factory;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import id.web.prayitno.projek2moviedicoding.MovieAppWidget;
import id.web.prayitno.projek2moviedicoding.R;
import id.web.prayitno.projek2moviedicoding.model.Movie;
import id.web.prayitno.projek2moviedicoding.network.ApiClient;

import static id.web.prayitno.projek2moviedicoding.db.DatabaseContract.CONTENT_URI;

public class StackRemoteViewsFactory
    implements RemoteViewsService.RemoteViewsFactory{

    private List<Bitmap> mWidgetItems = new ArrayList<>();
    private Context mContext;
    private int mAppWidgetId;
    private Cursor favMovieCursor = null;

    public StackRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        // take fav movie from database
        final long identityToken = Binder.clearCallingIdentity();
        // using content resolver
        favMovieCursor = mContext.getContentResolver().query(
                CONTENT_URI,
                null,
                null,
                null,
                null
        );

        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        Log.e("StackRemoteView", "Jumlah Fav Movie " + favMovieCursor.getCount());

        if (favMovieCursor == null){
            return 0;
        } else {
            return favMovieCursor.getCount();
        }
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
        final Movie movie = getMovieCursorItem(position);
        Bitmap bmp = null;
        try {
            bmp = Glide.with(mContext)
                    .asBitmap()
                    .load(ApiClient.IMDB_POSTER_URL + movie.getPosterPath())
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            Log.e("WidgetLoadError", "error: " + e.toString());
        }
        remoteViews.setImageViewBitmap(R.id.imageView, bmp);
        Log.e("StackRemoteView", "Data Gambar " + ApiClient.IMDB_POSTER_URL + movie.getPosterPath());

        Bundle extras = new Bundle();
        extras.putInt(MovieAppWidget.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        remoteViews.setOnClickFillInIntent(R.id.imageView, fillInIntent);

        return remoteViews;
    }

    private Movie getMovieCursorItem(int position) {
        if (!favMovieCursor.moveToPosition(position)){
            throw new IllegalStateException("Invalid Position");
        }
        return new Movie(favMovieCursor);
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    public void setFavMovieCursor(Cursor favMovieCursor) {
        this.favMovieCursor = favMovieCursor;
    }
}
