package id.web.prayitno.projek2moviedicoding.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.web.prayitno.projek2moviedicoding.R;
import id.web.prayitno.projek2moviedicoding.adapter.CardViewMovieAdapter;
import id.web.prayitno.projek2moviedicoding.adapter.ListViewMovieAdapter;
import id.web.prayitno.projek2moviedicoding.model.Movie;
import id.web.prayitno.projek2moviedicoding.model.MovieResult;
import id.web.prayitno.projek2moviedicoding.network.ApiClient;
import id.web.prayitno.projek2moviedicoding.network.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NowPlayingFragment extends Fragment {

    public static final String TITLE = "Now Playing";

    @BindView(R.id.shimmer_container)
    ShimmerFrameLayout mShimmerFrameLayout;
    @BindView(R.id.rv_now_playing)
    RecyclerView rvNowPlaying;
    CardViewMovieAdapter cardViewMovieAdapter;


    ArrayList<Movie> moviesData;

    public static NowPlayingFragment newInstance() {
        return new NowPlayingFragment();
    }

    public NowPlayingFragment() {
        // Required empty public constructor
        
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_now_playing, container, false);
        ButterKnife.bind(this, view);

        moviesData = new ArrayList<>();

        showRecycleCard();

        ApiService apiService = ApiClient.getRetrofit().create(ApiService.class);

        Call<MovieResult> nowPlayingMovies = apiService.getNowPlayingMovie(ApiClient.TMDB_API_KEY);
        nowPlayingMovies.enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {
                Log.e("FILM", "Jumlah Movie: " + response.body().getResults().size());
                moviesData.clear();
                moviesData.addAll(response.body().getResults());
                cardViewMovieAdapter.notifyDataSetChanged();
                mShimmerFrameLayout.stopShimmer();
                mShimmerFrameLayout.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<MovieResult> call, Throwable t) {

            }
        });
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu,  menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_card:
//                Toast.makeText(getContext(), "CARD", Toast.LENGTH_LONG).show();
                showRecycleCard();
                break;
            case R.id.action_list:
//                Toast.makeText(getContext(), "LIST", Toast.LENGTH_LONG).show();
                showRecycleList();
                break;
            case R.id.action_localization:
                Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showRecycleCard() {
        rvNowPlaying.setLayoutManager(new LinearLayoutManager(getContext()));
        cardViewMovieAdapter = new CardViewMovieAdapter(getContext(), moviesData, getFragmentManager().beginTransaction());
        rvNowPlaying.setAdapter(cardViewMovieAdapter);
    }

    private void showRecycleList() {
        rvNowPlaying.setLayoutManager(new LinearLayoutManager(getContext()));
        ListViewMovieAdapter listViewMovieAdapter = new ListViewMovieAdapter(getContext(), moviesData, getFragmentManager().beginTransaction());
        rvNowPlaying.setAdapter(listViewMovieAdapter);
    }
}
