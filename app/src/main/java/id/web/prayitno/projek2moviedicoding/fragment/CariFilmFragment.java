package id.web.prayitno.projek2moviedicoding.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.web.prayitno.projek2moviedicoding.R;
import id.web.prayitno.projek2moviedicoding.adapter.CardViewMovieAdapter;
import id.web.prayitno.projek2moviedicoding.model.Movie;
import id.web.prayitno.projek2moviedicoding.model.MovieResult;
import id.web.prayitno.projek2moviedicoding.network.ApiClient;
import id.web.prayitno.projek2moviedicoding.network.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CariFilmFragment extends Fragment {

    public static final String TITLE = "Cari Film";

    @BindView(R.id.et_cari_film)
    EditText etCariFilm;
    @BindView(R.id.btn_cari_film)
    Button btnCariFilm;
    @BindView(R.id.rv_hasil_cari_film)
    RecyclerView rvHasilCariFilm;
    @BindView(R.id.shimmer_container)
    ShimmerFrameLayout mShimmerContainer;

    ArrayList<Movie> mMoviesCariFilm;
    CardViewMovieAdapter mCardViewMovieAdapter;

    public static CariFilmFragment newInstance() {
        return new CariFilmFragment();
    }

    public CariFilmFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cari_film, container, false);
        ButterKnife.bind(this, view);

        mShimmerContainer.setVisibility(View.GONE);

        mMoviesCariFilm = new ArrayList<>();
        mCardViewMovieAdapter = new CardViewMovieAdapter(getContext(), mMoviesCariFilm, getFragmentManager().beginTransaction());
        rvHasilCariFilm.setLayoutManager(new LinearLayoutManager(getContext()));
        rvHasilCariFilm.setAdapter(mCardViewMovieAdapter);

        btnCariFilm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiService apiService = ApiClient.getRetrofit().create(ApiService.class);
                Call<MovieResult> gg = apiService.searchMovie(
                        ApiClient.TMDB_API_KEY,
                        etCariFilm.getText().toString()
                );
                mShimmerContainer.startShimmer();
                mShimmerContainer.setVisibility(View.VISIBLE);
                gg.enqueue(new Callback<MovieResult>() {
                    @Override
                    public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {
                        Log.e("FILM", "Jumlah Film: " + response.body().getResults().size());
                        mMoviesCariFilm.clear();
                        mMoviesCariFilm.addAll(response.body().getResults());
                        mCardViewMovieAdapter.notifyDataSetChanged();
                        mShimmerContainer.stopShimmer();
                        mShimmerContainer.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<MovieResult> call, Throwable t) {

                    }
                });
            }
        });

        return view;
    }

}
