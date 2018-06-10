package id.web.prayitno.projek2moviedicoding.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    // Todo isi TMDB_API_KEY
    public static final String TMDB_API_KEY = "ec0d0a4029f0f70dfb954357574a700b";
    public static final String BASE_URL = "https://api.themoviedb.org/";
    public static final String IMDB_POSTER_URL = "http://image.tmdb.org/t/p/w185";
    private static Retrofit sRetrofit;

    public static Retrofit getRetrofit(){
        if (sRetrofit == null){
            sRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return sRetrofit;
    }
}
