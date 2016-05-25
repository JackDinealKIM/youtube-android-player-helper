Youtube-Android-Player-Helper(YTPlayer)
=====
Helper library for Android developers looking to add YouTube video playback in their applications via the iframe player in WebView


Download
--------
use Gradle:

```gradle
repositories {
  mavenCentral() // jcenter() works as well because it pulls from Maven Central
}

dependencies {
  compile 'com.jaedongchicken:ytplayer:1.0.0'
}
```

Or Maven:

```xml
<dependency>
  <groupId>com.jaedongchicken</groupId>
  <artifactId>ytplayer</artifactId>
  <version>1.0.0</version>
  <type>pom</type>
</dependency>
```


How do I use YTPlayer?
-------------------
Simple use cases will look something like this:

```java
// For a simple view:
public class MainActivity extends AppCompatActivity {

    private TextView currentSec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentSec = (TextView) findViewById(R.id.currentSec);

        YoutubePlayerView youtubePlayerView = (YoutubePlayerView) findViewById(R.id.youtubePlayerView);
        youtubePlayerView.setAutoPlayerHeight(this);
        youtubePlayerView.initialize("YouTubeID", new YoutubePlayerView.YouTubeListener() {

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

```


Author
------
Jaedong Kim - @JackDinealKIM on GitHub

License
-------
##License 
```code
Copyright 2016 JD Kim

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.```

Disclaimer
---------
This is not an official Google product.

