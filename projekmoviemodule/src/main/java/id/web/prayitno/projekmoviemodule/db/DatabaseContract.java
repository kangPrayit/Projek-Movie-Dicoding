package id.web.prayitno.projekmoviemodule.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    static String TABLE_FAVORITE = "favorite";

    public static final class FavoriteColumns implements BaseColumns {
        public static String ID_MOVIE = "id_movie";
        public static String TITLE = "title";
        public static String OVERVIEW = "overview";
        public static String RELEASE_DATE = "release_date";
        public static String VOTE_AVERAGE = "vote_average";
        public static String VOTE_COUNT = "vote_count";
        public static String POSTER = "poster";
        public static String BACKDROP = "backdrop";
    }

    public static final String AUTHORITY = "id.web.prayitno.projek2moviedicoding";
    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_FAVORITE)
            .build();
    public static String getColumnString(Cursor cursor, String columnName){
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName){
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static long getColumnLong(Cursor cursor, String columnName){
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }
}
