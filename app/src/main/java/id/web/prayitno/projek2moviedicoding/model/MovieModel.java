package id.web.prayitno.projek2moviedicoding.model;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import java.util.ArrayList;

import id.web.prayitno.projek2moviedicoding.network.ApiClient;

public class MovieModel extends ViewModel {
    private MutableLiveData<ArrayList<Movie>> moviesNowPlaying;
    private MutableLiveData<ArrayList<Movie>> moviesUpcoming;
    private MutableLiveData<ArrayList<Movie>> searchMovie;
    private MutableLiveData<ArrayList<Movie>> favMovies;

    public MutableLiveData<ArrayList<Movie>> getFavMovies() {
        if (favMovies == null){
            favMovies = new MutableLiveData<>();

        }
        return favMovies;
    }

    public MutableLiveData<ArrayList<Movie>> getSearchMovie(String title) {
        if (searchMovie == null){
            searchMovie = new MutableLiveData<>();
            new SearchMovieAsyncTask().execute(title);
        }
        return searchMovie;
    }

    public MutableLiveData<ArrayList<Movie>> getMoviesNowPlaying() {
        if (moviesNowPlaying == null){
            moviesNowPlaying = new MutableLiveData<>();
            new MoviesAsyncTaskMoviesNowPlaying().execute();
        }
        return moviesNowPlaying;
    }

    public MutableLiveData<ArrayList<Movie>> getMoviesUpcoming() {
        if (moviesUpcoming == null){
            moviesUpcoming = new MutableLiveData<>();
            new MoviesAsyncTaskMoviesUpcoming().execute();
        }
        return moviesUpcoming;
    }

    private class SearchMovieAsyncTask extends AsyncTask<String, Void, ArrayList<Movie>>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<Movie> doInBackground(String... strings) {
            String mMovieTitle =  strings[0];
            return ApiClient.searchMovies(mMovieTitle);
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            super.onPostExecute(movies);
            searchMovie.setValue(movies);
        }
    }

    private class MoviesAsyncTaskMoviesNowPlaying extends AsyncTask<Void, Void, ArrayList<Movie>>{

        @Override
        protected ArrayList<Movie> doInBackground(Void... voids) {
            return ApiClient.getNowPlayingMovies();
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> data) {
            super.onPostExecute(data);
            moviesNowPlaying.setValue(data);
        }
    }

    private class MoviesAsyncTaskMoviesUpcoming extends AsyncTask<Void, Void, ArrayList<Movie>>{

        @Override
        protected ArrayList<Movie> doInBackground(Void... voids) {
            return ApiClient.getUpcomingMovies();
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> data) {
            super.onPostExecute(data);
            moviesUpcoming.setValue(data);
        }
    }
}
