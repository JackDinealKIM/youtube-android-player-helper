package com.jaedongchicken.ytplayer.model;

/**
 * Created by JD on 2016-06-01.
 */
public class YTParams {
    private int autoplay = 0;
    private int autohide = 1;
    private int rel = 0;
    private int showinfo = 0;
    private int enablejsapi = 1;
    private int disablekb = 1;
    private String cc_lang_pref = "en";
    private int controls = 1;
    private int volume = 100;
    private PlaybackQuality playbackQuality;

    public int getAutoplay() {
        return autoplay;
    }

    public void setAutoplay(int autoplay) {
        this.autoplay = autoplay;
    }

    public int getAutohide() {
        return autohide;
    }

    public void setAutohide(int autohide) {
        this.autohide = autohide;
    }

    public int getRel() {
        return rel;
    }

    public void setRel(int rel) {
        this.rel = rel;
    }

    public int getShowinfo() {
        return showinfo;
    }

    public void setShowinfo(int showinfo) {
        this.showinfo = showinfo;
    }

    public int getEnablejsapi() {
        return enablejsapi;
    }

    public void setEnablejsapi(int enablejsapi) {
        this.enablejsapi = enablejsapi;
    }

    public int getDisablekb() {
        return disablekb;
    }

    public void setDisablekb(int disablekb) {
        this.disablekb = disablekb;
    }

    public String getCc_lang_pref() {
        return cc_lang_pref;
    }

    public void setCc_lang_pref(String cc_lang_pref) {
        this.cc_lang_pref = cc_lang_pref;
    }

    public int getControls() {
        return controls;
    }

    public void setControls(int controls) {
        this.controls = controls;
    }

    public int getVolume() {
        return volume;
    }

    public YTParams setVolume(int volume) {
        this.volume = volume;
        return this;
    }

    public PlaybackQuality getPlaybackQuality() {
        return playbackQuality;
    }

    public YTParams setPlaybackQuality(PlaybackQuality playbackQuality) {
        this.playbackQuality = playbackQuality;
        return this;
    }


    @Override
    public String toString() {
        return "?autoplay=" + autoplay +
                "&autohide=" + autohide +
                "&rel=" + rel +
                "&showinfo=" + showinfo +
                "&enablejsapi=" + enablejsapi +
                "&disablekb=" + disablekb +
                "&cc_lang_pref=" + cc_lang_pref +
                "&controls=" + controls +
                "&volume=" + volume +
                "&playbackQuality=" + playbackQuality.name();
    }
}
