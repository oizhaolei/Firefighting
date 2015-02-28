package com.ruptech.firefighting;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.ruptech.firefighting.utils.ApkUpgrade;
import com.ruptech.firefighting.utils.PrefUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class SettingsActivity extends ActionBarActivity {

    @InjectView(R.id.activity_setting_push)
    TextView mPushTextView;
    @InjectView(R.id.activity_about_email_textview)
    TextView versionTextView;
    private ApkUpgrade apkUpgrade;
    private int serverVersion;

    @OnClick(R.id.activity_setting_logout_layout)
    public void doLogout() {
        App.logout();
        finish();
    }

    private void showVersion() {
        String ver = String.valueOf(App.getAppVersionCode());
        if (serverVersion > 0) {
            ver += " new version:";
            ver += serverVersion;
        }
        versionTextView.setText(ver);
    }

    @OnClick(R.id.activity_about_version_view)
    public void version(View v) {
        if (App.getAppVersionCode() < serverVersion) {
            try {
                apkUpgrade.doApkUpdate();
            } catch (Exception e) {
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.inject(this);

        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);
        String title = getString(R.string.action_settings);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        apkUpgrade = new ApkUpgrade(this);

        apkUpgrade.doRetrieveServerVersion(new ApkUpgrade.ServerVersionListener() {
            @Override
            public void onServerVersion(int ver) {
                serverVersion = ver;
                showVersion();
            }
        });

        showVersion();
        mPushTextView.setText(PrefUtils.readPushToken());
    }

}
