package com.theradioapp.activity;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.theradioapp.BackgroundSoundService;
import com.theradioapp.R;
import com.theradioapp.TinyDB;
import com.theradioapp.Utils.Utility;
import com.theradioapp.adapter.CustomListAdapter;
import com.theradioapp.constants.AppConstatnts;

import com.theradioapp.models.RecentSongs;
import com.theradioapp.models.iTunesMOdel;
import com.theradioapp.models.imageJSON;
import com.theradioapp.views.BottomSheetListView;
import com.theradioapp.views.TextViewCustom;
import com.theradioapp.ws.VolleyService;
import com.theradioapp.ws.interfaces.VolleyResponseListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Calendar;
import java.util.Date;

import static com.theradioapp.Utils.Utility.isMyServiceRunning;


public class MainActivity extends AppCompatActivity implements View.OnClickListener
        , MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener, VolleyResponseListener {

    private ImageView ivPlayPause;
    private ImageView ivSetting;
    private ImageView ivThumb;
    private ImageView ivMain;
    //    private MediaPlayer mediaPlayer;

    private Intent intent;
    LinearLayout layoutBottomSheet;
    BottomSheetBehavior sheetBehavior;
    private BottomSheetListView lvRecent;
    private ArrayList<RecentSongs> recentSongs = new ArrayList<>();
    private TextViewCustom tvJustHeard;
    private TextViewCustom tvNoRecent1;
    private TextViewCustom tvNoRecent2;
    private TextViewCustom tvNoNet;
    private RecentSongs recentSongsObj;
    private CustomListAdapter adapter;
    private TinyDB tinyDB;
    private Bundle b;
    private ProgressBar pb;
    private String FINAL_URL;
    private long timeLong1, timeLong2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        intent = new Intent(this, BackgroundSoundService.class);
        setupControls();
    }

    private void setupControls() {
        tinyDB = new TinyDB(MainActivity.this);
        b = getIntent().getExtras();
        FINAL_URL = AppConstatnts.ITUNES_1 + tinyDB.getString(AppConstatnts.CURRENT_IMAGE_ID)
                + AppConstatnts.ITUNES_2;
        pb = (ProgressBar) findViewById(R.id.pb);
        ivPlayPause = (ImageView) findViewById(R.id.ivPlayPause);
        ivSetting = (ImageView) findViewById(R.id.ivSetting);
        ivThumb = (ImageView) findViewById(R.id.ivThumb);
        ivMain = (ImageView) findViewById(R.id.ivMain);
        lvRecent = (BottomSheetListView) findViewById(R.id.lvRecent);
        tvJustHeard = (TextViewCustom) findViewById(R.id.tvJustHeard);
        tvNoRecent1 = (TextViewCustom) findViewById(R.id.tvNoRecent1);
        tvNoRecent2 = (TextViewCustom) findViewById(R.id.tvNoRecent2);
        tvNoNet = (TextViewCustom) findViewById(R.id.tvNoNet);
        layoutBottomSheet = findViewById(R.id.bottom_sheet);
        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);

        sheetBehavior.setPeekHeight(60);
        if (isMyServiceRunning(BackgroundSoundService.class, MainActivity.this)) {
            Glide.with(MainActivity.this)
                    .load(tinyDB.getString(AppConstatnts.CURRENT_IMAGE_URL))
                    .addListener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            ivMain.setImageResource(R.drawable.main_placeholder);
                            return true;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            ivMain.setImageDrawable(resource);
                            return true;
                        }
                    })
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(ivMain);
        }
//        Log.e("TAG", "setupControls: " + checkDate());
        initGlobal();

    }

    @Override
    public void onResponse(Object response, String url, boolean isSuccess, VolleyError error) {
        if (url.equals(FINAL_URL)) {
            if (isSuccess && response instanceof iTunesMOdel) {

                if (((iTunesMOdel) response).getResults().size() > 0) {
                    tinyDB.putString(AppConstatnts.CURRENT_IMAGE_URL, ((iTunesMOdel) response).getResults().get(0).getArtworkUrl100());
                    Glide.with(MainActivity.this)
                            .load(tinyDB.getString(AppConstatnts.CURRENT_IMAGE_URL))
                            .addListener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    ivMain.setImageResource(R.drawable.main_placeholder);
                                    return true;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    ivMain.setImageDrawable(resource);
                                    return true;
                                }
                            })
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .into(ivMain);
                    adapter.notifyDataSetChanged();
                }
            } else {
                callItunesAPI();
            }
            timeLong1 = System.currentTimeMillis();
            Log.e("TAG", "onResponse: START TIME::::" + timeLong1);
            startService(intent);
        } else {
            switch (url) {
                case AppConstatnts.JSON_URL:
                    if (isSuccess && response instanceof imageJSON) {
//                        Log.e("TAG", "onResponse: " + ((imageJSON) response).getImage());
                        callItunesAPI();
                        tinyDB.putString(AppConstatnts.CURRENT_IMAGE_ID, ((imageJSON) response).getImage());
                        tinyDB.putString(AppConstatnts.CURRENT_SONG_ARTIST, ((imageJSON) response).getArtist());
                        tinyDB.putString(AppConstatnts.CURRENT_SONG_TITLE, ((imageJSON) response).getTitle());
                        recentSongsObj = new RecentSongs(((imageJSON) response).getArtist(), ((imageJSON) response).getTitle());
                        if (recentSongs.size() == 0) {
                            recentSongs.add(recentSongsObj);
                        } else {
                            for (int i = 0; i < recentSongs.size(); i++) {
                                if (recentSongs.get(i).getArtist().equals(((imageJSON) response).getArtist())) {
                                    recentSongs.remove(recentSongs.get(i));
                                    recentSongs.add(recentSongsObj);
                                }
                            }
                        }

                        if (recentSongs.size() >= 1) {
                            tvJustHeard.setVisibility(View.VISIBLE);
                            tvNoRecent1.setVisibility(View.GONE);
                            tvNoRecent2.setVisibility(View.GONE);
                        }
                        pb.setVisibility(View.GONE);
                        ivPlayPause.setImageResource(R.drawable.pause);
                        adapter.notifyDataSetChanged();
                    } else {
                        getImageID();
                    }
                    break;
            }
        }
    }

    private void callItunesAPI() {
        FINAL_URL = AppConstatnts.ITUNES_1 + tinyDB.getString(AppConstatnts.CURRENT_IMAGE_ID)
                + AppConstatnts.ITUNES_2;
        if (Utility.isConnected(MainActivity.this)) {
            VolleyService.GetMethod(FINAL_URL, iTunesMOdel.class, null, this);
        }
    }

    public void getImageID() {
        if (Utility.isConnected(MainActivity.this)) {
            VolleyService.GetMethod(AppConstatnts.JSON_URL, imageJSON.class, null, this);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initGlobal() {
        pb.setVisibility(View.GONE);
        pb.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.white),
                PorterDuff.Mode.MULTIPLY);
        if (b != null) {
            RecentSongs songs = new RecentSongs(b.getString(AppConstatnts.CURRENT_SONG_ARTIST_TEMP),
                    b.getString(AppConstatnts.CURRENT_SONG_TITLE_TEMP));
            recentSongs.add(songs);
            tvNoRecent1.setVisibility(View.GONE);
            tvNoRecent2.setVisibility(View.GONE);
            tvJustHeard.setVisibility(View.VISIBLE);
        } else {
            recentSongs.clear();
            tvNoRecent1.setVisibility(View.VISIBLE);
            tvNoRecent2.setVisibility(View.VISIBLE);
            tvJustHeard.setVisibility(View.GONE);
        }
        adapter = new CustomListAdapter(MainActivity.this, recentSongs);
        lvRecent.setAdapter(adapter);

        if (recentSongs.size() == 0) {
            tvJustHeard.setVisibility(View.GONE);
        }
        if (isMyServiceRunning(BackgroundSoundService.class, MainActivity.this)) {
            ivPlayPause.setImageResource(R.drawable.pause);
        }
        ivThumb.setOnClickListener(this);
        ivPlayPause.setOnClickListener(this);
        ivSetting.setOnClickListener(this);
        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {
                        ivThumb.setImageResource(R.drawable.ic_expand_more);
                    }
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                        ivThumb.setImageResource(R.drawable.ic_expand_less);
                    }
                    break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
//        playAudio();
    }

    private void startPlaying() {
        if (!isMyServiceRunning(BackgroundSoundService.class, MainActivity.this)) {
            pb.setVisibility(View.VISIBLE);
            getImageID();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivPlayPause:
                if (Utility.isConnected(MainActivity.this)) {
                    tvNoNet.setVisibility(View.GONE);
                    if (ivPlayPause.getDrawable().getConstantState() ==
                            ContextCompat.getDrawable(this, R.drawable.play).getConstantState()) {
                        if (tinyDB.getBoolean(AppConstatnts.WIFI_STATUS)) {
                            if (Utility.checkWifiConnection(MainActivity.this)) {
                                if (checkDate()) {
                                    startPlaying();
                                } else {
                                    tvNoNet.setText(R.string.date_validity_msg);
                                    tvNoNet.setVisibility(View.VISIBLE);
                                }
                            } else {
                                tvNoNet.setText(R.string.switch_to_wifi);
                                tvNoNet.setVisibility(View.VISIBLE);
                            }
                        } else {
                            if (checkDate()) {
                                startPlaying();
                            } else {
                                tvNoNet.setText(R.string.date_validity_msg);
                                tvNoNet.setVisibility(View.VISIBLE);
                            }
                        }
                    } else {
                        if (isMyServiceRunning(BackgroundSoundService.class, MainActivity.this)) {
                            if (BackgroundSoundService.notificationmanager != null) {
                                BackgroundSoundService.notificationmanager.cancelAll();
                            }
                            stopService(intent);
                            timeLong2 = System.currentTimeMillis();
//                            Log.e("TAG", "onResponse: END TIME::::" + timeLong2);

                            TinyDB.updateListeningTime((timeLong2 - timeLong1), tinyDB);
                            Glide.with(MainActivity.this)
                                    .load(R.drawable.main_placeholder)
                                    .transition(DrawableTransitionOptions.withCrossFade())
                                    .into(ivMain);
                            ivPlayPause.setImageResource(R.drawable.play);
                        }
                    }
                } else {
                    tvNoNet.setText(R.string.no_net_msg);
                    tvNoNet.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.ivSetting:
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.ivThumb:
                if (sheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
                break;
        }
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {

    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        ivPlayPause.setImageResource(R.drawable.play);
    }

    @Override
    protected void onDestroy() {
        // Unbound background audio service when activity is destroyed.
//        mediaPlayer.stop();
//        mediaPlayer.release();

        super.onDestroy();
    }


    private boolean checkDate() {
//        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Calendar c = Calendar.getInstance();
        Date currentDate = c.getTime();
        int year = currentDate.getYear();
        Date StartDate = new Date(year, Calendar.OCTOBER, 30);
        Date EndDate = new Date(year + 1, Calendar.JANUARY, 2);
//        String formattedDate = df.format(StartDate);
//        Log.e("TAG", "StartDate::::" + formattedDate);
//        formattedDate = df.format(EndDate);
//        Log.e("TAG", "EndDate::::" + formattedDate);
//        formattedDate = df.format(currentDate);
//        Log.e("TAG", "CurDate::::" + formattedDate);
        return StartDate.compareTo(currentDate) * currentDate.compareTo(EndDate) > 0;
    }


}
