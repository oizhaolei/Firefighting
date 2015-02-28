package com.ruptech.firefighting.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;

import com.ruptech.firefighting.App;

public class ApkUpgrade {

    protected final String TAG = ApkUpgrade.class.getSimpleName();
    private final Context context;
    public RetrieveServerVersionTask mRetrieveServerVersionTask;

    public ApkUpgrade(Context activity) {
        context = activity;
    }

    public void doApkUpdate() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(App.properties.getProperty("server.url") + "/firefighting.apk"));
        browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(browserIntent);
    }

    public void doRetrieveServerVersion(ServerVersionListener listener) {
        if (mRetrieveServerVersionTask != null
                && mRetrieveServerVersionTask.getStatus() == AsyncTask.Status.RUNNING) {
            mRetrieveServerVersionTask.cancel(true);
        }

        mRetrieveServerVersionTask = new RetrieveServerVersionTask(listener);

        mRetrieveServerVersionTask.execute();
    }

    public interface ServerVersionListener {
        void onServerVersion(int ver);
    }

    public class RetrieveServerVersionTask extends AsyncTask<Void, Void, Integer> {

        private final ServerVersionListener listener;

        public RetrieveServerVersionTask(ServerVersionListener listener) {
            this.listener = listener;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            return App.getHttpServer().ver();
        }

        @Override
        protected void onPostExecute(final Integer ver) {
            listener.onServerVersion(ver);
        }
    }

}
