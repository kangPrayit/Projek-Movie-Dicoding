package id.web.prayitno.projek2moviedicoding.service;

import android.content.Intent;
import android.widget.RemoteViewsService;

import id.web.prayitno.projek2moviedicoding.Factory.StackRemoteViewsFactory;

public class StackWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}
