package com.theradioapp.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.theradioapp.R;
import com.theradioapp.TinyDB;
import com.theradioapp.constants.AppConstatnts;
import com.theradioapp.views.TextViewCustom;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    private TextViewCustom tvReport, tvVisitSite, tvListeningTime;
    private TextViewCustom tvInstallDate;
    private TextViewCustom tvVersion;
    private Switch switchWifi;
    private TinyDB tinyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        tinyDB = new TinyDB(SettingActivity.this);
        setupControls();
    }

    private void setupControls() {
        getSupportActionBar().setTitle("Setting");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        tvReport = (TextViewCustom) findViewById(R.id.tvReport);
        tvVisitSite = (TextViewCustom) findViewById(R.id.tvVisitSite);
        tvListeningTime = (TextViewCustom) findViewById(R.id.tvListeningTime);
        tvInstallDate = (TextViewCustom) findViewById(R.id.tvInstallDate);
        tvVersion = (TextViewCustom) findViewById(R.id.tvVersion);
        switchWifi = (Switch) findViewById(R.id.switchWifi);
        initGlobal();
    }

    private void initGlobal() {
        tvListeningTime.setText(tinyDB.getString(AppConstatnts.TOTAL_FINAL_LISTENING_TIME));
        if (tinyDB.getBoolean(AppConstatnts.WIFI_STATUS)) {
            switchWifi.setChecked(true);
        } else {
            switchWifi.setChecked(false);
        }
        tvInstallDate.setText(tinyDB.getString(AppConstatnts.INSTALL_DATE));
        tvVersion.setText(tinyDB.getString(AppConstatnts.VERSION_INFO));
        tvVisitSite.setOnClickListener(this);
        tvReport.setOnClickListener(this);
        switchWifi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tinyDB.putBoolean(AppConstatnts.WIFI_STATUS, true);
//                    Log.e("TAG", "onCheckedChanged: WIFI ON");
                } else {
                    tinyDB.putBoolean(AppConstatnts.WIFI_STATUS, false);
//                    Log.e("TAG", "onCheckedChanged: WIFI OFF");
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.tvVisitSite:
//                http://www.tinselandtunes.com
                String url = "http://www.tinselandtunes.com ";
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
                break;
            case R.id.tvReport:
//                http://www.tinselandtunes.com

                intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
//                intent.setType("message/rfc822");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"support@tinselandtunes.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT, "Help with Android app");
                intent.putExtra(Intent.EXTRA_TEXT, "Please describe the problem:\n\n\n\n\n\n" +
                        "Make sure you include all of this information below:\n\n\n\nApp version:" + tvVersion.getText().toString()
                        + "\n\nInstall date:" + tvInstallDate.getText().toString() + "\n\nWifi only:Yes" + "\n\nAndroid Version : " + Build.VERSION_CODES.class.getFields()[android.os.Build.VERSION.SDK_INT].getName()
                        + "\n\nHardware Name:" + android.os.Build.MODEL);
                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    //TODO: Handle case where no email app is available
                    Toast.makeText(this, "No Mail Apps Found!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
