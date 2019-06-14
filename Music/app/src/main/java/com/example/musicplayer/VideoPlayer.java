package com.example.musicplayer;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MergingMediaSource;
import com.google.android.exoplayer2.source.SingleSampleMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;

public class VideoPlayer {


    public static final DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();
    public static final String TAG = "PlayerActivity";

    public static SimpleExoPlayer player;
    public static long playbackPosition;
    public static int currentWindow;
    public static boolean playWhenReady = true;
    public static  Context ctx;
    public  static  void initializePlayer(String link) {


        PlayerActivity.playerView.setPlayer(player);
        //   Log.i(TAG, "initializePlayer: Player is Running" +link);
        //1
        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        //2
        final ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        //3
        TrackSelection.Factory adaptiveTrackSelectionFactory = new AdaptiveTrackSelection.Factory(BANDWIDTH_METER);
        //4
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(ctx, Util.getUserAgent(ctx, "VideoPlayer"), bandwidthMeter);
        //5
        MediaSource mediaSource  = new ExtractorMediaSource(Uri.parse(link), dataSourceFactory, extractorsFactory, new Handler(), null);
        //6
        player = ExoPlayerFactory.newSimpleInstance(ctx, new DefaultTrackSelector(adaptiveTrackSelectionFactory));

        VideoPlayer.player.prepare(mediaSource, true, false);
        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentWindow, playbackPosition);

        Log.i(TAG, "initializePlayer: Player is Running");

    }

    public static void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }


}
