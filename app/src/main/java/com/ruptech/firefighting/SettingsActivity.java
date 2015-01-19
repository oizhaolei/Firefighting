package com.ruptech.firefighting;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.ruptech.firefighting.utils.PrefUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class SettingsActivity extends ActionBarActivity {

    @InjectView(R.id.activity_setting_push)
    TextView mPushTextView;

    @OnClick(R.id.activity_setting_logout_layout)
    public void doLogout() {
        App.logout();
        finish();
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


        mPushTextView.setText(PrefUtils.readPushToken());
    }
}
