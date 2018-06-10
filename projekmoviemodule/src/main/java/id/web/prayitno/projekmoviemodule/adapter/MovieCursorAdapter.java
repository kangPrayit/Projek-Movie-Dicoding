package id.web.prayitno.projekmoviemodule.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.web.prayitno.projekmoviemodule.R;

import static id.web.prayitno.projekmoviemodule.db.DatabaseContract.FavoriteColumns.BACKDROP;
import static id.web.prayitno.projekmoviemodule.db.DatabaseContract.FavoriteColumns.OVERVIEW;
import static id.web.prayitno.projekmoviemodule.db.DatabaseContract.FavoriteColumns.RELEASE_DATE;
import static id.web.prayitno.projekmoviemodule.db.DatabaseContract.FavoriteColumns.TITLE;
import static id.web.prayitno.projekmoviemodule.db.DatabaseContract.getColumnString;

public class MovieCursorAdapter extends CursorAdapter {
    @BindView(R.id.iv_poster_film)
    ImageView ivPosterFilm;
    @BindView(R.id.tv_judul)
    TextView tvJudul;
    @BindView(R.id.tv_deskripsi)
    TextView tvDeskripsi;
    @BindView(R.id.tv_tanggal_rilis)
    TextView tvTanggalRilis;
    @BindView(R.id.btn_detail)
    Button btnDetail;
    @BindView(R.id.btn_share)
    Button btnShare;

    public MovieCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cardview_movie, parent, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        if (cursor != null) {
            tvJudul.setText(getColumnString(cursor, TITLE));
            tvDeskripsi.setText(getColumnString(cursor, OVERVIEW));
            tvTanggalRilis.setText(getColumnString(cursor, RELEASE_DATE));
            Glide.with(context)
                    .load("http://image.tmdb.org/t/p/w185" + getColumnString(cursor, BACKDROP))
                    .into(ivPosterFilm);
        }
    }

    @Override
    public Cursor getCursor() {
        return super.getCursor();
    }
}
