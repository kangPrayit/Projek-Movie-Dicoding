package id.web.prayitno.projek2moviedicoding.network;

import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

import id.web.prayitno.projek2moviedicoding.model.Movie;
import id.web.prayitno.projek2moviedicoding.model.MovieResult;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    // Todo isi TMDB_API_KEY
    public static final String TMDB_API_KEY = "ec0d0a4029f0f70dfb954357574a700b";
    public static final String BASE_URL = "https://api.themoviedb.org/";
    public static final String IMDB_POSTER_URL = "http://image.tmdb.org/t/p/w185";
    private static Retrofit sRetrofit;
    private static ApiService mApiService;
//    private static ArrayList<Movie> results;

    public static Retrofit getRetrofit(){
        if (sRetrofit == null){
            sRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return sRetrofit;
    }

    public static ApiService getApiService(){
        if (mApiService == null){
            mApiService = getRetrofit().create(ApiService.class);
        }
        return mApiService;
    }

    public static ArrayList<Movie> getNowPlayingMovies() {
        ArrayList<Movie> results = new ArrayList<>();
        Call<MovieResult> nowPlayingMovies = getApiService().getNowPlayingMovie(TMDB_API_KEY);
        try {
            results = nowPlayingMovies.execute().body().getResults();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return results;
    }

    public static ArrayList<Movie> getUpcomingMovies() {
        ArrayList<Movie> results = new ArrayList<>();
        Call<MovieResult> upcomingMovie = getApiService().getUpcomingMovie(TMDB_API_KEY);
        Log.e("ApiClient", upcomingMovie.request().toString());
        try {
            results = upcomingMovie.execute().body().getResults();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return results;
    }

    public static ArrayList<Movie> searchMovies(String searchMovieTitle) {
        ArrayList<Movie> results = new ArrayList<>();
        Call<MovieResult> searchMovie = getApiService().searchMovie(TMDB_API_KEY, searchMovieTitle);
        Log.e("ApiClient", searchMovie.request().toString());
        try {
            results = searchMovie.execute().body().getResults();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return results;
    }

}
