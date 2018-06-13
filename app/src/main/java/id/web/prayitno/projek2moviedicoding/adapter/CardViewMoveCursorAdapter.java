package id.web.prayitno.projek2moviedicoding.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.web.prayitno.projek2moviedicoding.R;
import id.web.prayitno.projek2moviedicoding.fragment.DetailFilmFragment;
import id.web.prayitno.projek2moviedicoding.model.Movie;
import id.web.prayitno.projek2moviedicoding.network.ApiClient;

public class CardViewMoveCursorAdapter extends RecyclerView.Adapter<id.web.prayitno.projek2moviedicoding.adapter.CardViewMoveCursorAdapter.MyHolder> {

    private Context mContext;
    private FragmentTransaction mFragmentTransaction;
    private Cursor mFavMoviesCursor;
    private Cursor mFavMovieList;

    public CardViewMoveCursorAdapter(Context context, Cursor favMoviesCursor, FragmentTransaction fragmentTransaction) {
        mContext = context;
        mFavMoviesCursor = favMoviesCursor;
        mFragmentTransaction = fragmentTransaction;
    }

    @NonNull
    @Override
    public id.web.prayitno.projek2moviedicoding.adapter.CardViewMoveCursorAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cardview_movie, parent, false);
        return new id.web.prayitno.projek2moviedicoding.adapter.CardViewMoveCursorAdapter.MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        final Movie movie = getMovieCursorItem(position);
        holder.tvJudul.setText(movie.getTitle());
        holder.tvDeskripsi.setText(movie.getOverview());
        holder.tvTanggalRilis.setText(movie.getReleaseDate());
        Glide.with(mContext)
                .load(ApiClient.IMDB_POSTER_URL + movie.getPosterPath())
                .into(holder.ivPosterFilm);
        holder.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle mBundle = new Bundle();
                mBundle.putParcelable("Movie", movie);
                DetailFilmFragment detailFilmFragment = new DetailFilmFragment();
                detailFilmFragment.setArguments(mBundle);
                mFragmentTransaction.replace(R.id.fragment_container, detailFilmFragment);
                mFragmentTransaction.addToBackStack(null);
                mFragmentTransaction.commit();
            }
        });
        holder.btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Awsome Movie : " + movie.getTitle());
                sendIntent.setType("text/plain");
                mContext.startActivity(sendIntent);
            }
        });
    }

    private Movie getMovieCursorItem(int position) {
        if (!mFavMoviesCursor.moveToPosition(position)){
            throw new IllegalStateException("Position Invalid");
        }
        return new Movie(mFavMoviesCursor);
    }

    @Override
    public int getItemCount() {
        if (mFavMoviesCursor == null) return 0;
        return mFavMoviesCursor.getCount();
    }

    public void setFavMovieList(Cursor favMovieList) {
        mFavMoviesCursor = favMovieList;
    }

    public class MyHolder extends RecyclerView.ViewHolder {
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

        public MyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
