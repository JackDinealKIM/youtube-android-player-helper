package com.jaedongchicken.ytplayer_sample;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.jaedongchicken.ytplayer.JLog;
import com.jaedongchicken.ytplayer.YoutubePlayerView;
import com.jaedongchicken.ytplayer.model.PlaybackQuality;
import com.jaedongchicken.ytplayer.model.YTParams;

public class MainActivity extends AppCompatActivity {

    private TextView currentSec;
    private YoutubePlayerView youtubePlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currentSec = (TextView) findViewById(R.id.currentSec);

        // get id from XML
        youtubePlayerView = (YoutubePlayerView) findViewById(R.id.youtubePlayerView);

        // make auto height of youtube. if you want to use 'wrap_content'
        youtubePlayerView.setAutoPlayerHeight(this);

        // if you want to change white backgrond, #default is black.
        // youtubePlayerView.setWhiteBackgroundColor();

        // Control values : see more # https://developers.google.com/youtube/player_parameters?hl=en
        YTParams params = new YTParams();
        // params.setControls(0);
        // params.setAutoplay(1);
        params.setVolume(80);
        params.setPlaybackQuality(PlaybackQuality.small);

        // initialize YoutubePlayerCallBackListener with Params and VideoID
        // youtubePlayerView.initialize("WCchr07kLPE", params, new YoutubePlayerView.YouTubeListener())

        // initialize YoutubePlayerCallBackListener and VideoID
        youtubePlayerView.initialize("BzYnNdJhZQw", params, new YoutubePlayerView.YouTubeListener() {

            @Override
            public void onReady() {
                // when player is ready.
                JLog.i("onReady()");
            }

            @Override
            public void onStateChange(YoutubePlayerView.STATE state) {
                /**
                 * YoutubePlayerView.STATE
                 *
                 * UNSTARTED, ENDED, PLAYING, PAUSED, BUFFERING, CUED, NONE
                 *
                 */

                JLog.i("onStateChange(" + state + ")");
            }

            @Override
            public void onPlaybackQualityChange(String arg) {
                JLog.i("onPlaybackQualityChange(" + arg + ")");
            }

            @Override
            public void onPlaybackRateChange(String arg) {
                JLog.i("onPlaybackRateChange(" + arg + ")");
            }

            @Override
            public void onError(String arg) {
                JLog.e("onError(" + arg + ")");
            }

            @Override
            public void onApiChange(String arg) {
                JLog.i("onApiChange(" + arg + ")");
            }

            @Override
            public void onCurrentSecond(double second) {
                // currentTime callback
                Message msg = new Message();
                msg.obj = second;
                handler.sendMessage(msg);
            }

            @Override
            public void onDuration(double duration) {
                // total duration
                JLog.i("onDuration(" + duration + ")");
            }

            @Override
            public void logs(String log) {
                // javascript debug log. you don't need to use it.
                JLog.d(log);
            }
        });


        // psuse video
        // youtubePlayerView.pause();

        // play video when it's ready
        // youtubePlayerView.play();

        // cue video
        // youtubePlayerView.onCueVideo("WCchr07kLPE");

        // start with 20 secs
        // youtubePlayerView.onLoadVideo("WCchr07kLPE", 20);

        // seek to 20 secs
        // youtubePlayerView.seekToMillis(20);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // you can cast with float that I recommend.
            double sec = (double) msg.obj;
            currentSec.setText(String.valueOf(sec));
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // this is optional but you need.
        youtubePlayerView.destroy();
    }
}
