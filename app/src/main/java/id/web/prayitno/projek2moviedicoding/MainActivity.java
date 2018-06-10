package id.web.prayitno.projek2moviedicoding;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.web.prayitno.projek2moviedicoding.db.DatabaseHelper;
import id.web.prayitno.projek2moviedicoding.fragment.CariFilmFragment;
import id.web.prayitno.projek2moviedicoding.fragment.FavoriteFragment;
import id.web.prayitno.projek2moviedicoding.fragment.NowPlayingFragment;
import id.web.prayitno.projek2moviedicoding.fragment.UpcomingFragment;
import id.web.prayitno.projek2moviedicoding.network.ApiClient;
import id.web.prayitno.projek2moviedicoding.utils.AppPreference;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.bottom_navigation)
    BottomNavigationView mBottomNavigationView;

    private ActionBar toolbar;
    AppPreference mAppPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (ApiClient.TMDB_API_KEY.isEmpty()){
            Toast.makeText(
                    getApplicationContext(),
                    "Mohon isi TMDB API KEY",
                    Toast.LENGTH_LONG
            ).show();
        }

        toolbar = getSupportActionBar();

        mAppPreference = new AppPreference(MainActivity.this);
        if (mAppPreference.getFirstRun()) {
            // create database & table
            new DatabaseHelper(MainActivity.this);
            // set false to first run app
            mAppPreference.setFirstRun(false);
        }

        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        loadFragment(new NowPlayingFragment());

    }

    private BottomNavigationView.OnNavigationItemSelectedListener
            mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;

            switch (item.getItemId()) {
                case R.id.nav_upcoming:
                    toolbar.setTitle(R.string.toolbar_title_upcoming);
                    fragment = new UpcomingFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.nav_now_playing:
                    toolbar.setTitle(R.string.toolbar_title_upcoming);
                    fragment = new NowPlayingFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.nav_search:
                    toolbar.setTitle(R.string.toolbar_title_search);
                    fragment = new CariFilmFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.nav_fav:
                    toolbar.setTitle("My Favorite Movie");
                    fragment = new FavoriteFragment();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }

}
