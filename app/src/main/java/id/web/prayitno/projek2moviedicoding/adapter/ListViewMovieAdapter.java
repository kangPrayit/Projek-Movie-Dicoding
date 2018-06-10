package id.web.prayitno.projek2moviedicoding.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.web.prayitno.projek2moviedicoding.R;
import id.web.prayitno.projek2moviedicoding.fragment.DetailFilmFragment;
import id.web.prayitno.projek2moviedicoding.model.Movie;
import id.web.prayitno.projek2moviedicoding.network.ApiClient;

public class ListViewMovieAdapter extends RecyclerView.Adapter<ListViewMovieAdapter.MyHolder> {
    private Context mContext;
    private ArrayList<Movie> mMovie;
    private FragmentTransaction mFragmentTransaction;

    public ListViewMovieAdapter(Context context, ArrayList<Movie> movie, FragmentTransaction fragmentTransaction) {
        mContext = context;
        mMovie = movie;
        mFragmentTransaction = fragmentTransaction;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_listview_movie, parent, false);
        return new MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        final Movie movie = mMovie.get(position);
        holder.tvJudul.setText(movie.getTitle());
        holder.tvVote.setText("Average Vote : " + movie.getVoteAverage());
        Glide.with(mContext)
                .load(ApiClient.IMDB_POSTER_URL + movie.getPosterPath())
                .apply(new RequestOptions().override(200, 200))
                .transition(new DrawableTransitionOptions().crossFade())
                .into(holder.ivPosterFilm);
        holder.ivPosterFilm.setOnClickListener(new View.OnClickListener() {
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
    }

    @Override
    public int getItemCount() {
        return mMovie.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_poster_film)
        ImageView ivPosterFilm;
        @BindView(R.id.tv_judul)
        TextView tvJudul;
        @BindView(R.id.tv_vote)
        TextView tvVote;

        public MyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
