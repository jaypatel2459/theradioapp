package com.theradioapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;

import com.theradioapp.BackgroundSoundService;
import com.theradioapp.R;
import com.theradioapp.TinyDB;
import com.theradioapp.Utils.Utility;
import com.theradioapp.constants.AppConstatnts;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.content.ContentValues.TAG;

public class SplashActivity extends Activity {

    private TinyDB tinyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        tinyDB = new TinyDB(SplashActivity.this);
        initGlobal();
    }


    private void initGlobal() {
        if (!tinyDB.isKeyExist(AppConstatnts.WIFI_STATUS)) {
            tinyDB.putBoolean(AppConstatnts.WIFI_STATUS, false);
        }
        if (!tinyDB.isKeyExist(AppConstatnts.TOTAL_FINAL_LISTENING_TIME)) {
            tinyDB.putString(AppConstatnts.TOTAL_FINAL_LISTENING_TIME, "0D 0H 0M 0S");
        }
        final Bundle b = getIntent().getExtras();
//        if (b != null || Utility.isMyServiceRunning(BackgroundSoundService.class, SplashActivity.this)) {
//            Log.e(TAG, "initGlobal: " + b.getString(AppConstatnts.CURRENT_SONG_TITLE_TEMP));
//            Log.e(TAG, "initGlobal: " + b.getString(AppConstatnts.CURRENT_SONG_ARTIST_TEMP));
//            Log.e(TAG, "initGlobal:IMG::: " +tinyDB.getString(AppConstatnts.CURRENT_IMAGE_URL));
//        }
        if (!tinyDB.getBoolean(AppConstatnts.IS_INSTALLED)) {
            Date c = Calendar.getInstance().getTime();
            Log.e(TAG, "initGlobal: " + c);
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            tinyDB.putString(AppConstatnts.INSTALL_DATE, df.format(c));
            tinyDB.putBoolean(AppConstatnts.IS_INSTALLED, Boolean.TRUE);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tinyDB.putString(AppConstatnts.VERSION_INFO, getVersionInfo());
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                if (b != null && b.getString(AppConstatnts.CURRENT_SONG_ARTIST_TEMP) != null) {
                    i.putExtra(AppConstatnts.CURRENT_SONG_ARTIST_TEMP, b.getString(AppConstatnts.CURRENT_SONG_ARTIST_TEMP));
                    i.putExtra(AppConstatnts.CURRENT_SONG_TITLE_TEMP, b.getString(AppConstatnts.CURRENT_SONG_TITLE_TEMP));
                }
                startActivity(i);
                finish();
            }
        }, 1000);
    }

    private String getVersionInfo() {
        String versionName = "";
        int versionCode = -1;
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            versionName = packageInfo.versionName;
            versionCode = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }
}
