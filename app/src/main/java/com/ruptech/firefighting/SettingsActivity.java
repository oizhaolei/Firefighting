package com.ruptech.firefighting;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsActivity extends ActionBarActivity {

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

    }
}
