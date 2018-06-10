package id.web.prayitno.projek2moviedicoding.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.web.prayitno.projek2moviedicoding.R;
import id.web.prayitno.projek2moviedicoding.db.MovieHelper;
import id.web.prayitno.projek2moviedicoding.model.Movie;
import id.web.prayitno.projek2moviedicoding.network.ApiClient;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFilmFragment extends Fragment {

    @BindView(R.id.tv_judul)
    TextView tvJudul;
    @BindView(R.id.tv_deskripsi)
    TextView tvDeskripsi;
    @BindView(R.id.tv_tanggal_rilis)
    TextView tvTanggalRilis;
    @BindView(R.id.tv_vote)
    TextView tvVote;
    @BindView(R.id.iv_poster_film)
    ImageView ivPosterFilm;
    @BindView(R.id.btn_fav_movie)
    MaterialFavoriteButton btnFavMovie;

    Movie mMovie;
    MovieHelper mMovieHelper;

    public DetailFilmFragment() {

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mMovieHelper.close();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_film, container, false);
        ButterKnife.bind(this, view);

        // get movie data from fragment transaction
        mMovie = getArguments().getParcelable("Movie");

        mMovieHelper = new MovieHelper(getContext());
        mMovieHelper.open();
        // check fav button condition from SQLite
        if (mMovieHelper.isFavorite(mMovie.getId())){
            btnFavMovie.setFavorite(true);
        } else {
            btnFavMovie.setFavorite(false);
        }

        tvJudul.setText(mMovie.getTitle());
        tvTanggalRilis.setText("Release Date : " + mMovie.getReleaseDate());
        tvDeskripsi.setText(mMovie.getOverview());
        tvVote.setText("Average Vote : " + mMovie.getVoteAverage()
                + " with " + mMovie.getVoteCount() + " Vote for it!");
        Glide.with(view)
                .load(ApiClient.IMDB_POSTER_URL + mMovie.getBackdropPath())
                .into(ivPosterFilm);

        btnFavMovie.setOnFavoriteChangeListener(new MaterialFavoriteButton.OnFavoriteChangeListener() {
            @Override
            public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                // insert movie into favorite table SQLite
                if (favorite){
                    mMovieHelper.insertFavMovie(mMovie);
                    Toast.makeText(getContext(),
                            mMovie.getTitle() + " added to Favorite!",
                            Toast.LENGTH_SHORT
                    ).show();
                } else {
                    mMovieHelper.deleteFavMovie(mMovie.getId());
                    Toast.makeText(getContext(),
                            mMovie.getTitle() + " delete from Favorite!",
                            Toast.LENGTH_SHORT
                    ).show();
                }

            }
        });

        return view;
    }

}
