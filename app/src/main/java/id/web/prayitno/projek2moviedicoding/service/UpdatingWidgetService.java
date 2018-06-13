package id.web.prayitno.projek2moviedicoding.service;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.widget.RemoteViews;

import id.web.prayitno.projek2moviedicoding.MovieAppWidget;
import id.web.prayitno.projek2moviedicoding.R;

public class UpdatingWidgetService extends JobService {
    @Override
    public boolean onStartJob(JobParameters params) {
        AppWidgetManager manager = AppWidgetManager.getInstance(this);

        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.movie_app_widget);
        ComponentName theWidget = new ComponentName(this, MovieAppWidget.class);

//        String lastUpdate = "Random "

        jobFinished(params, false);

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {

        return false;
    }
}
