package id.web.prayitno.projek2moviedicoding.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
public class UpcomingFragment extends Fragment {

    public static final String TITLE = "Upcoming";

    @BindView(R.id.rv_upcoming_movie)
    RecyclerView rvUpcomingMovie;
    CardViewMovieAdapter mCardViewMovieAdapter;

    ArrayList<Movie> mMoviesUpcoming;

    public static UpcomingFragment newInstance() {
        return new UpcomingFragment();
    }

    public UpcomingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upcoming, container, false);
        ButterKnife.bind(this, view);

        mMoviesUpcoming = new ArrayList<>();
        mCardViewMovieAdapter = new CardViewMovieAdapter(getContext(), mMoviesUpcoming, getFragmentManager().beginTransaction());

        rvUpcomingMovie.setLayoutManager(new LinearLayoutManager(getContext()));
        rvUpcomingMovie.setAdapter(mCardViewMovieAdapter);

        ApiService apiService = ApiClient.getRetrofit().create(ApiService.class);
        Call<MovieResult> upcomingMovies = apiService.getUpcomingMovie(ApiClient.TMDB_API_KEY);
        upcomingMovies.enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {
                mMoviesUpcoming.clear();
                mMoviesUpcoming.addAll(response.body().getResults());
                mCardViewMovieAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<MovieResult> call, Throwable t) {

            }
        });
        return view;
    }

}
