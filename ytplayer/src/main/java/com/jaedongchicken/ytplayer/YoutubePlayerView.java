package com.jaedongchicken.ytplayer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jaedongchicken.ytplayer.model.PlaybackQuality;
import com.jaedongchicken.ytplayer.model.YTParams;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

public class YoutubePlayerView extends WebView {

    private QualsonBridge bridge = new QualsonBridge();

    private YTParams params = new YTParams();

    private YouTubeListener youTubeListener;
    private String backgroundColor = "#000000";
    private final String customURL = "http://jaedong.net/youtube/";

    private Context context;

    private boolean isCustomDomain = false;
    private boolean isHandlerEnable = true;

    public YoutubePlayerView(Context context) {
        super(context);
        this.context = context;
        setWebViewClient(new MyWebViewClient((Activity) context));
    }

    public YoutubePlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setWebViewClient(new MyWebViewClient((Activity) context));
    }

    @SuppressLint("JavascriptInterface")
    public void initialize(String videoId, YouTubeListener youTubeListener) {
        WebSettings set = this.getSettings();
        set.setJavaScriptEnabled(true);
        set.setUseWideViewPort(true);
        set.setLoadWithOverviewMode(true);
        set.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        set.setCacheMode(WebSettings.LOAD_NO_CACHE);
        set.setPluginState(WebSettings.PluginState.ON);
        set.setPluginState(WebSettings.PluginState.ON_DEMAND);
        set.setAllowContentAccess(true);
        set.setAllowFileAccess(true);

        this.youTubeListener = youTubeListener;
        this.setLayerType(View.LAYER_TYPE_NONE, null);
        this.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        this.addJavascriptInterface(bridge, "QualsonInterface");
        this.setLongClickable(true);
        this.setWebChromeClient(new WebChromeClient());
        this.setWebViewClient(new QWebViewClient());
        this.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && BuildConfig.DEBUG) {
            setWebContentsDebuggingEnabled(true);
        }

        if (isCustomDomain) {
            this.loadUrl(videoId);
        } else {
            this.loadDataWithBaseURL("http://www.youtube.com", getVideoHTML(videoId), "text/html", "utf-8", null);
        }
    }

    public void initialize(String videoId, YTParams params, YouTubeListener youTubeListener) {
        if(params != null) {
            this.params = params;
        }
        initialize(videoId, youTubeListener);
    }

    public void initializeWithUrl(String videoUrl, YTParams params, YouTubeListener youTubeListener) {
        if(params != null) {
            this.params = params;
        }
        String videoId = videoUrl.substring(videoUrl.indexOf('=')+1);
        initialize(videoId, youTubeListener);
    }

    public void initializeWithCustomURL(String videoId, YTParams params, YouTubeListener youTubeListener) {
        if (params != null) {
            this.params = params;
        }
        isCustomDomain = true;
        String webCustomUrl = customURL.concat(videoId);
        if (videoId.startsWith("http") || videoId.startsWith("https")) {
            webCustomUrl = videoId;
        }
        initialize(webCustomUrl.concat(params.toString()), youTubeListener);
    }

    public void setWhiteBackgroundColor() {
        backgroundColor = "#ffffff";
    }

    public void setHandlerDisable() {
        isHandlerEnable = false;
    }

    public void setAutoPlayerHeight(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        this.getLayoutParams().height = (int) (displayMetrics.widthPixels * 0.5625);
    }

    public void setAutoPlayerHeight() {
        setAutoPlayerHeight(context);
    }

    /**
     * APP TO WEB
     */
    public void seekToMillis(double mil) {
        JLog.d("seekToMillis : ");
        this.loadUrl("javascript:onSeekTo(" + mil + ")");
    }

    public void pause() {
        JLog.d("pause");
        this.loadUrl("javascript:onVideoPause()");
    }

    public void play() {
        JLog.d("play");
        this.loadUrl("javascript:onVideoPlay()");
    }

    public void onLoadVideo(String videoId, float mil) {
        JLog.d("onLoadVideo : " + videoId + ", " + mil);
        this.loadUrl("javascript:loadVideo('" + videoId + "', " + mil + ")");
    }

    public void onCueVideo(String videoId) {
        JLog.d("onCueVideo : " + videoId);
        this.loadUrl("javascript:cueVideo('" + videoId + "')");
    }

    public void playFullscreen() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        this.getLayoutParams().height = (int) (displayMetrics.widthPixels * 0.5625);

        JLog.d("playFullscreen");
        this.loadUrl("javascript:playFullscreen(" + displayMetrics.widthPixels + ", " + displayMetrics.heightPixels + ")");
    }

    /**
     * WEB TO APP
     */
    private class QualsonBridge {

        @JavascriptInterface
        public void onReady(String arg) {
            JLog.d("onReady(" + arg + ")");
            if (youTubeListener != null) {
                youTubeListener.onReady();
            }
        }

        @JavascriptInterface
        public void onStateChange(String arg) {
            JLog.d("onStateChange(" + arg + ")");
            if (youTubeListener != null) {
                if ("UNSTARTED".equalsIgnoreCase(arg)) {
                    youTubeListener.onStateChange(STATE.UNSTARTED);
                } else if ("ENDED".equalsIgnoreCase(arg)) {
                    youTubeListener.onStateChange(STATE.ENDED);
                } else if ("PLAYING".equalsIgnoreCase(arg)) {
                    youTubeListener.onStateChange(STATE.PLAYING);
                } else if ("PAUSED".equalsIgnoreCase(arg)) {
                    youTubeListener.onStateChange(STATE.PAUSED);
                } else if ("BUFFERING".equalsIgnoreCase(arg)) {
                    youTubeListener.onStateChange(STATE.BUFFERING);
                } else if ("CUED".equalsIgnoreCase(arg)) {
                    youTubeListener.onStateChange(STATE.CUED);
                }
            }
        }

        @JavascriptInterface
        public void onPlaybackQualityChange(String arg) {
            JLog.d("onPlaybackQualityChange(" + arg + ")");
            if (youTubeListener != null) {
                youTubeListener.onPlaybackQualityChange(arg);
            }
        }

        @JavascriptInterface
        public void onPlaybackRateChange(String arg) {
            JLog.d("onPlaybackRateChange(" + arg + ")");
            if (youTubeListener != null) {
                youTubeListener.onPlaybackRateChange(arg);
            }
        }

        @JavascriptInterface
        public void onError(String arg) {
            JLog.e("onError(" + arg + ")");
            if (youTubeListener != null) {
                youTubeListener.onError(arg);
            }
        }

        @JavascriptInterface
        public void onApiChange(String arg) {
            JLog.d("onApiChange(" + arg + ")");
            if (youTubeListener != null) {
                youTubeListener.onApiChange(arg);
            }
        }

        @JavascriptInterface
        public void currentSeconds(String seconds) {
            if (youTubeListener != null) {
                // currentTime callback
                float second = Float.parseFloat(seconds);
                if (isHandlerEnable) {
                    Message msg = new Message();
                    msg.obj = second;
                    handler.sendMessage(msg);
                } else {
                    youTubeListener.onCurrentSecond(second);
                }
            }
        }

        @JavascriptInterface
        public void duration(String seconds) {
            JLog.d("duration -> " + seconds);
            if (youTubeListener != null) {
                youTubeListener.onDuration(Double.parseDouble(seconds));
            }
        }

        @JavascriptInterface
        public void logs(String arg) {
            JLog.d("logs(" + arg + ")");
            if (youTubeListener != null) {
                youTubeListener.logs(arg);
            }
        }

        private Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                float second = (float) msg.obj;
                if (youTubeListener != null) {
                    youTubeListener.onCurrentSecond(second);
                }
            }
        };
    }


    /**
     * NonLeakingWebView
     */
    private static Field sConfigCallback;

    static {
        try {
            sConfigCallback = Class.forName("android.webkit.BrowserFrame").getDeclaredField("sConfigCallback");
            sConfigCallback.setAccessible(true);
        } catch (Exception e) {
            // ignored
        }
    }

    public void onDestroy() {
        super.onDetachedFromWindow();
        // View is now detached, and about to be destroyed
        this.clearCache(true);
        this.clearHistory();
        try {
            if (sConfigCallback != null)
                sConfigCallback.set(null, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private class MyWebViewClient extends WebViewClient {
        protected WeakReference<Activity> activityRef;

        public MyWebViewClient(Activity activity) {
            this.activityRef = new WeakReference<>(activity);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            try {
                final Activity activity = activityRef.get();
                if (activity != null)
                    activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            } catch (RuntimeException ignored) {
                // ignore any url parsing exceptions
            }
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            JLog.d("onPageFinished()");
        }
    }

    public interface YouTubeListener {
        void onReady();

        void onStateChange(STATE state);

        void onPlaybackQualityChange(String arg);

        void onPlaybackRateChange(String arg);

        void onError(String arg);

        void onApiChange(String arg);

        void onCurrentSecond(double second);

        void onDuration(double duration);

        void logs(String log);
    }

    public enum STATE {
        UNSTARTED,
        ENDED,
        PLAYING,
        PAUSED,
        BUFFERING,
        CUED,
        NONE
    }

    private String getVideoHTML(String videoId) {
        try {
            InputStream in = getResources().openRawResource(R.raw.players);
            if (in != null) {
                InputStreamReader stream = new InputStreamReader(in, "utf-8");
                BufferedReader buffer = new BufferedReader(stream);
                String read;
                StringBuilder sb = new StringBuilder("");

                while ((read = buffer.readLine()) != null) {
                    sb.append(read + "\n");
                }

                in.close();

                String html = sb.toString().replace("[VIDEO_ID]", videoId).replace("[BG_COLOR]", backgroundColor);
                PlaybackQuality playbackQuality = params.getPlaybackQuality();
                html = html.replace("[AUTO_PLAY]", String.valueOf(params.getAutoplay()))
                        .replace("[AUTO_HIDE]", String.valueOf(params.getAutohide()))
                        .replace("[REL]", String.valueOf(params.getRel()))
                        .replace("[SHOW_INFO]", String.valueOf(params.getShowinfo()))
                        .replace("[ENABLE_JS_API]", String.valueOf(params.getEnablejsapi()))
                        .replace("[DISABLE_KB]", String.valueOf(params.getDisablekb()))
                        .replace("[CC_LANG_PREF]", String.valueOf(params.getCc_lang_pref()))
                        .replace("[CONTROLS]", String.valueOf(params.getControls()))
                        .replace("[AUDIO_VOLUME]", String.valueOf(params.getVolume()))
                        .replace("[PLAYBACK_QUALITY]", playbackQuality == null ? String.valueOf("default") : playbackQuality.name());
                return html;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
