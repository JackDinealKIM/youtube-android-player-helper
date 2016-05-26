package com.jaedongchicken.ytplayer_sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.jaedongchicken.ytplayer.YoutubePlayerView;

public class MainActivity extends AppCompatActivity {

    private TextView currentSec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentSec = (TextView) findViewById(R.id.currentSec);

        // get id from XML
        YoutubePlayerView youtubePlayerView = (YoutubePlayerView) findViewById(R.id.youtubePlayerView);
        // make auto height of youtube. if you want to use 'wrap_content'
        youtubePlayerView.setAutoPlayerHeight(this);
        // initialize YoutubePlayerCallBackListener and VideoID
        youtubePlayerView.initialize("YOUTUBE_ID", new YoutubePlayerView.YouTubeListener() {

            @Override
            public void onReady() {
                // when player is ready.
            }

            @Override
            public void onStateChange(YoutubePlayerView.STATE state) {
                /**
                 * YoutubePlayerView.STATE
                 *
                 * UNSTARTED, ENDED, PLAYING, PAUSED, BUFFERING, CUED, NONE
                 *
                 */
            }

            @Override
            public void onPlaybackQualityChange(String arg) {
            }

            @Override
            public void onPlaybackRateChange(String arg) {
            }

            @Override
            public void onError(String error) {            }

            @Override
            public void onApiChange(String arg) {

            }

            @Override
            public void onCurrentSecond(double second) {
                // currentTime callback
            }

            @Override
            public void onDuration(double duration) {
                // total duration
            }

            @Override
            public void logs(String log) {
                // javascript debug log. you don't need to use it.
            }
        });


        // psuse video
        youtubePlayerView.pause();
        // play video when it's ready
        youtubePlayerView.play();
    }
}
