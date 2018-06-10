package id.web.prayitno.projek2moviedicoding.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;

import id.web.prayitno.projek2moviedicoding.model.Movie;

import static id.web.prayitno.projek2moviedicoding.db.DatabaseContract.TABLE_FAVORITE;
import static id.web.prayitno.projek2moviedicoding.db.DatabaseContract.FavoriteColumns;

public class MovieHelper {
    private Context mContext;
    private DatabaseHelper mDatabaseHelper;

    private SQLiteDatabase mSQLiteDatabase;

    public MovieHelper(Context context){
        mContext = context;
    }

    public MovieHelper open() {
        mDatabaseHelper = new DatabaseHelper(mContext);
        mSQLiteDatabase = mDatabaseHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        mDatabaseHelper.close();
    }

    public ArrayList<Movie> getAllFavMovies(){
        Cursor cursor = mSQLiteDatabase.query(
                TABLE_FAVORITE,
                null,
                null,
                null,
                null,
                null,
                FavoriteColumns._ID + " ASC",
                null
        );
        cursor.moveToFirst();
        ArrayList<Movie> movieArrayList = new ArrayList<>();
        Movie movie;
        if (cursor.getCount() > 0){
            do {
                movie = new Movie();
                movie.setId(cursor.getInt(cursor.getColumnIndexOrThrow(FavoriteColumns.ID_MOVIE)));
                movie.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(FavoriteColumns.TITLE)));
                movie.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(FavoriteColumns.OVERVIEW)));
                movie.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(FavoriteColumns.POSTER)));
                movie.setBackdropPath(cursor.getString(cursor.getColumnIndexOrThrow(FavoriteColumns.BACKDROP)));
                movie.setReleaseDate(cursor.getString(cursor.getColumnIndexOrThrow(FavoriteColumns.RELEASE_DATE)));
                movie.setVoteAverage(cursor.getDouble(cursor.getColumnIndexOrThrow(FavoriteColumns.VOTE_AVERAGE)));
                movie.setVoteCount(cursor.getInt(cursor.getColumnIndexOrThrow(FavoriteColumns.VOTE_COUNT)));

                movieArrayList.add(movie);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();

        return movieArrayList;
    }

    public long insertFavMovie(Movie movie){
        ContentValues initialValues = new ContentValues();
        initialValues.put(FavoriteColumns.ID_MOVIE, movie.getId());
        initialValues.put(FavoriteColumns.TITLE, movie.getTitle());
        initialValues.put(FavoriteColumns.OVERVIEW, movie.getOverview());
        initialValues.put(FavoriteColumns.POSTER, movie.getPosterPath());
        initialValues.put(FavoriteColumns.BACKDROP, movie.getBackdropPath());
        initialValues.put(FavoriteColumns.RELEASE_DATE, movie.getReleaseDate());
        initialValues.put(FavoriteColumns.VOTE_AVERAGE, movie.getVoteAverage());
        initialValues.put(FavoriteColumns.VOTE_COUNT, movie.getVoteCount());
        return mSQLiteDatabase.insert(TABLE_FAVORITE, null, initialValues);
    }

    public int deleteFavMovie(int id){
        return mSQLiteDatabase.delete(
                TABLE_FAVORITE,
                FavoriteColumns.ID_MOVIE + " = '" + id + "'",
                null
        );
    }

    public boolean isFavorite(Integer id) {
        Cursor cursor = mSQLiteDatabase.query(
                TABLE_FAVORITE,
                null,
                FavoriteColumns.ID_MOVIE + " = '" + id + "'",
                null,
                null,
                null,
                null
        );
        if (cursor.getCount() == 0){
            return false;
        } else {
            return true;
        }
    }

    public Cursor queryByIdProvider(String id){
        return mSQLiteDatabase.query(
                TABLE_FAVORITE,
                null,
                FavoriteColumns.ID_MOVIE + " = ?",
                new String[]{id},
                null,
                null,
                null
        );
    }

    public Cursor queryProvider(){
        return mSQLiteDatabase.query(
                TABLE_FAVORITE,
                null,
                null,
                null,
                null,
                null,
                FavoriteColumns._ID + " DESC"
        );
    }

    public long insertProvide(Movie movie){
        ContentValues initialValues = new ContentValues();
        initialValues.put(FavoriteColumns.ID_MOVIE, movie.getId());
        initialValues.put(FavoriteColumns.TITLE, movie.getTitle());
        initialValues.put(FavoriteColumns.OVERVIEW, movie.getOverview());
        initialValues.put(FavoriteColumns.POSTER, movie.getPosterPath());
        initialValues.put(FavoriteColumns.BACKDROP, movie.getBackdropPath());
        initialValues.put(FavoriteColumns.RELEASE_DATE, movie.getReleaseDate());
        initialValues.put(FavoriteColumns.VOTE_AVERAGE, movie.getVoteAverage());
        initialValues.put(FavoriteColumns.VOTE_COUNT, movie.getVoteCount());
        return mSQLiteDatabase.insert(TABLE_FAVORITE, null, initialValues);
    }

    public int deleteProvider(String id){
        return mSQLiteDatabase.delete(
                TABLE_FAVORITE,
                FavoriteColumns.ID_MOVIE + " = '" + id + "'",
                null
        );
    }
}
