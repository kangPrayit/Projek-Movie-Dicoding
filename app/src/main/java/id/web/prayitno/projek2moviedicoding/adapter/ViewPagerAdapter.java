package id.web.prayitno.projek2moviedicoding.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import id.web.prayitno.projek2moviedicoding.fragment.CariFilmFragment;
import id.web.prayitno.projek2moviedicoding.fragment.NowPlayingFragment;
import id.web.prayitno.projek2moviedicoding.fragment.UpcomingFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private static int TAB_COUNT = 3;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return NowPlayingFragment.newInstance();
            case 1:
                return UpcomingFragment.newInstance();
            case 2:
                return CariFilmFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return TAB_COUNT;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return NowPlayingFragment.TITLE;
            case 1:
                return UpcomingFragment.TITLE;
            case 2:
                return CariFilmFragment.TITLE;
        }
        return super.getPageTitle(position);
    }
}
