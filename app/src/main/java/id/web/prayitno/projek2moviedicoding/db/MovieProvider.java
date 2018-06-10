package id.web.prayitno.projek2moviedicoding.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static id.web.prayitno.projek2moviedicoding.db.DatabaseContract.AUTHORITY;
import static id.web.prayitno.projek2moviedicoding.db.DatabaseContract.TABLE_FAVORITE;

public class MovieProvider extends ContentProvider {
    public static final int MOVIE = 1;
    public static final int MOVIE_ID_MOVIE = 2;
    public static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        // content://id.web.prayitno.projek2moviedicoding/movie
        sUriMatcher.addURI(AUTHORITY, TABLE_FAVORITE, MOVIE);
        sUriMatcher.addURI(
                AUTHORITY,
                TABLE_FAVORITE + "/#",
                MOVIE_ID_MOVIE
        );
    }
    private MovieHelper mMovieHelper;

    @Override
    public boolean onCreate() {
        mMovieHelper = new MovieHelper(getContext());
        mMovieHelper.open();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                cursor = mMovieHelper.queryProvider();
                break;
            case MOVIE_ID_MOVIE:
                cursor = mMovieHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }
        if (cursor != null) {
            cursor.setNotificationUri(getContext().getContentResolver(),uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
