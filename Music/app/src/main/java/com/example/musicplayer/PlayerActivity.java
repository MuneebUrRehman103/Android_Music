package com.example.musicplayer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.util.Util;

import java.io.InputStream;
import java.net.URL;

public class PlayerActivity extends AppCompatActivity {

    public static SimpleExoPlayerView playerView;
    public  String link;
    public  TextView txt;
    public ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        VideoPlayer.ctx = this;
        txt = findViewById(R.id.textView2);
        img = findViewById(R.id.imageView);
        playerView =findViewById(R.id.player_view);


        String url = getIntent().getStringExtra("AUDIO");
        String cover = getIntent().getStringExtra("COVER");
        String title = getIntent().getStringExtra("TITLE");
        link=url;

        txt.setText(title);
       new DownLoadImageTask(img).execute(cover);
        VideoPlayer.initializePlayer(url);
    }
    @Override
    public  void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            VideoPlayer.initializePlayer(link);
        }
    }
   @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 || VideoPlayer.player == null)) {
            VideoPlayer.initializePlayer(link);
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            VideoPlayer.releasePlayer();
        }
    }
    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            VideoPlayer.releasePlayer();
        }
    }
    private class DownLoadImageTask extends AsyncTask<String,Void,Bitmap> {
        ImageView imageView;

        public DownLoadImageTask(ImageView imageView){
            this.imageView = imageView;
        }
        protected Bitmap doInBackground(String...urls){
            String urlOfImage = urls[0];
            Bitmap img = null;
            try{
                InputStream is = new URL(urlOfImage).openStream();
                img = BitmapFactory.decodeStream(is);
            }catch(Exception e){
                e.printStackTrace();
            }
            return img;
        }
        protected void onPostExecute(Bitmap result){
            imageView.setImageBitmap(result);
        }
    }

}
