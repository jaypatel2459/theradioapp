package com.theradioapp;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.NetworkOnMainThreadException;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RemoteViews;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.AppWidgetTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.theradioapp.Utils.Utility;
import com.theradioapp.activity.SplashActivity;
import com.theradioapp.constants.AppConstatnts;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static android.content.ContentValues.TAG;

public class BackgroundSoundService extends Service {
    //creating a mediaplayer object
    public MediaPlayer player;
    private TinyDB tinyDB;
    public static NotificationManager notificationmanager;
    public static final String ANDROID_CHANNEL_ID = "com.theradioapp.ANDROID";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //getting systems default ringtone
        player = new MediaPlayer();
        tinyDB = new TinyDB(getApplicationContext());
        try {
            player.setDataSource(AppConstatnts.AUDIO_URL);
            player.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //setting loop play to true
        //this will make the ringtone continuously playing
        player.setLooping(true);
        startPlayer();


        //we have some options for service
        //start sticky means service will be explicity started and stopped
        return START_NOT_STICKY;
    }

    public void startPlayer() {
        //staring the player
        player.start();



        if (tinyDB.getString(AppConstatnts.CURRENT_IMAGE_URL).equals("")) {
            CustomNotification(tinyDB.getString(AppConstatnts.CURRENT_SONG_ARTIST),
                    tinyDB.getString(AppConstatnts.CURRENT_SONG_TITLE), null);
        } else {
            new GetBitmapFromURL().execute(tinyDB.getString(AppConstatnts.CURRENT_IMAGE_URL));
        }
    }

    public void CustomNotification(String artist, String title, Bitmap bitmap) {
        final RemoteViews remoteViews = new RemoteViews(getPackageName(),
                R.layout.view_expanded_notification);
        Intent intent = new Intent(this, SplashActivity.class);
        intent.putExtra(AppConstatnts.CURRENT_SONG_ARTIST_TEMP, tinyDB.getString(AppConstatnts.CURRENT_SONG_ARTIST));
        intent.putExtra(AppConstatnts.CURRENT_SONG_TITLE_TEMP, tinyDB.getString(AppConstatnts.CURRENT_SONG_TITLE));
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, ANDROID_CHANNEL_ID)
                .setSmallIcon(R.drawable.play)
                .setTicker(title)
                .setAutoCancel(true)
                .setOngoing(true)
                .setContentIntent(pIntent)
                .setContent(remoteViews);

        remoteViews.setOnClickPendingIntent(R.id.imagenotiright, pIntent);
        remoteViews.setTextViewText(R.id.title, title);
        remoteViews.setTextViewText(R.id.text, artist);

        if (bitmap == null) {
            remoteViews.setImageViewResource(R.id.imagenotileft, R.drawable.main_placeholder);
        } else {
            remoteViews.setImageViewBitmap(R.id.imagenotileft, bitmap);
        }

        notificationmanager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(ANDROID_CHANNEL_ID, getString(R.string.app_name),
                    NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
            notificationManager.notify(0, builder.build());
        } else {
            notificationmanager.notify(0, builder.build());
        }
    }

    public void stopPlayer() {
        player.stop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //stopping the player when service is destroyed
        player.stop();
        player.release();
    }


    @Override
    public void onTaskRemoved(Intent rootIntent) {
        if (notificationmanager != null) {
            notificationmanager.cancelAll();
        }
        super.onTaskRemoved(rootIntent);
    }

    @SuppressLint("StaticFieldLeak")
    private class GetBitmapFromURL extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {
            return Utility.getBitmapFromURL(params[0]);
        }

        @Override
        protected void onPostExecute(Bitmap result) {
//            Log.e(TAG, "onPostExecute: "+result );
            CustomNotification(tinyDB.getString(AppConstatnts.CURRENT_SONG_ARTIST),
                    tinyDB.getString(AppConstatnts.CURRENT_SONG_TITLE), result);
        }
    }
}