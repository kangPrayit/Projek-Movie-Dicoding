package id.web.prayitno.projek2moviedicoding.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static id.web.prayitno.projek2moviedicoding.db.DatabaseContract.FavoriteColumns;
import static id.web.prayitno.projek2moviedicoding.db.DatabaseContract.TABLE_FAVORITE;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "movieapp";
    private static final int DATABASE_VERSION = 1;
    private static final String SQL_CREATE_TABLE_FAVORITE =
            String.format(
                    "CREATE TABLE %s" +
                    " (%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL)",
                    DatabaseContract.TABLE_FAVORITE,
                    FavoriteColumns._ID,
                    FavoriteColumns.ID_MOVIE,
                    FavoriteColumns.TITLE,
                    FavoriteColumns.OVERVIEW,
                    FavoriteColumns.RELEASE_DATE,
                    FavoriteColumns.VOTE_AVERAGE,
                    FavoriteColumns.VOTE_COUNT,
                    FavoriteColumns.POSTER,
                    FavoriteColumns.BACKDROP
            );

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_FAVORITE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITE);
        onCreate(db);
    }
}
