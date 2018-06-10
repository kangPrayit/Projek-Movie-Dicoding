package id.web.prayitno.projek2moviedicoding.network;

import id.web.prayitno.projek2moviedicoding.model.MovieResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("3/search/movie")
    Call<MovieResult> searchMovie(@Query("api_key") String apiKey, @Query("query") String query);

    @GET("3/movie/now_playing")
    Call<MovieResult> getNowPlayingMovie(@Query("api_key") String apiKey);

    @GET("3/movie/upcoming")
    Call<MovieResult> getUpcomingMovie(@Query("api_key") String apiKey);
}
