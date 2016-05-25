package com.jaedongchicken.ytplayer_sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.jaedongchicken.ytplayer.JLog;
import com.jaedongchicken.ytplayer.YoutubePlayerView;

public class MainActivity extends AppCompatActivity {

    private TextView currentSec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentSec = (TextView) findViewById(R.id.currentSec);

        YoutubePlayerView youtubePlayerView = (YoutubePlayerView) findViewById(R.id.youtubePlayerView);
        youtubePlayerView.setAutoPlayerHeight(this);
        youtubePlayerView.initialize("efx5dBnZPQA", new YoutubePlayerView.YouTubeListener() {

            @Override
            public void onReady() {
                JLog.d("onReady()");
            }

            @Override
            public void onStateChange(YoutubePlayerView.STATE state) {
                switch (state) {
                    case UNSTARTED:
                        JLog.d("onStateChange(UNSTARTED)");
                        break;
                    case ENDED:
                        JLog.d("onStateChange(ENDED)");
                        break;
                    case PLAYING:
                        JLog.d("onStateChange(PLAYING)");
                        break;
                    case PAUSED:
                        JLog.d("onStateChange(PAUSED)");
                        break;
                    case BUFFERING:
                        JLog.d("onStateChange(BUFFERING)");
                        break;
                    case CUED:
                        JLog.d("onStateChange(CUED)");
                        break;
                }
            }

            @Override
            public void onPlaybackQualityChange(String arg) {
                JLog.d("onPlaybackQualityChange(" + arg + ")");
            }

            @Override
            public void onPlaybackRateChange(String arg) {
                JLog.d("onPlaybackRateChange(" + arg + ")");
            }

            @Override
            public void onError(String error) {
                JLog.e("onApiChange(" + error + ")");
            }

            @Override
            public void onApiChange(String arg) {
                JLog.d("onApiChange(" + arg + ")");
            }

            @Override
            public void onCurrentSecond(double second) {
                JLog.d("onCurrentSecond(" + second + ")");
                currentSec.setText(second + " sec.");
            }

            @Override
            public void onDuration(double duration) {
                JLog.d("onDuration(" + duration + ")");
            }

            @Override
            public void logs(String log) {
                JLog.d("logs(" + log + ")");
            }
        });
    }
}
