package id.web.prayitno.projekmoviemodule.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
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
import id.web.prayitno.projekmoviemodule.R;
import id.web.prayitno.projekmoviemodule.model.Movie;

public class CardViewMovieAdapter extends RecyclerView.Adapter<CardViewMovieAdapter.MyHolder> {

    private Context mContext;
    private Cursor listMovies;
    private Activity mActivity;

    public CardViewMovieAdapter(Context context){
        mContext = context;
    }

    public void setListMovies(Cursor listMovies){
        this.listMovies = listMovies;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cardview_movie, parent, false);
        return new MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        final Movie movie = getMovieItem(position);
        holder.tvJudul.setText(movie.getTitle());
        holder.tvDeskripsi.setText(movie.getOverview());
        holder.tvTanggalRilis.setText(movie.getReleaseDate());
        Glide.with(mContext)
                .load("http://image.tmdb.org/t/p/w185" + movie.getPosterPath())
                .into(holder.ivPosterFilm);
        holder.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Awesome Movie : " + movie.getTitle());
                sendIntent.setType("text/plain");
                mContext.startActivity(sendIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listMovies == null) return 0;
        return listMovies.getCount();
    }

    private Movie getMovieItem(int position){
        if (!listMovies.moveToPosition(position)){
            throw new IllegalStateException("Position Invalid");
        }
        return new Movie(listMovies);
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
