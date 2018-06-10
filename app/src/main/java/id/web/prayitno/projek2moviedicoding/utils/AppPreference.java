package id.web.prayitno.projek2moviedicoding.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import id.web.prayitno.projek2moviedicoding.R;

public class AppPreference {
    SharedPreferences mSharedPreferences;
    Context mContext;

    public AppPreference(Context context){
        mContext = context;
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setFirstRun(Boolean input){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        String key = mContext.getResources().getString(R.string.app_first_run);
        editor.putBoolean(key, input);
        editor.apply();
    }

    public Boolean getFirstRun(){
        String key = mContext.getResources().getString(R.string.app_first_run);
        return mSharedPreferences.getBoolean(key, true);
    }
}
