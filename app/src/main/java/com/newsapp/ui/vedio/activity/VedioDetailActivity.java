package com.newsapp.ui.vedio.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Window;
import android.widget.Button;

import com.focus.newsapp.R;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

public class VedioDetailActivity extends Activity {
    private VideoView mVedioView;
    private int mWidth,mHeigth;
    private Button mPlayBtn,mPauseBtn;
    private String url = "http://flv5.bn.netease.com/tvmrepo/2015/9/G/7/EB1LAQFG7/HD/EB1LAQFG7.flv";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_vedio_detail_layout);
        init();
    }

    private void init() {
        if (!io.vov.vitamio.LibsChecker.checkVitamioLibs(this))
            return;
        mVedioView = (VideoView) findViewById(R.id.surface_view);
        mVedioView.setVideoPath(url);
        mVedioView.setVideoQuality(MediaPlayer.VIDEOQUALITY_HIGH);
        mVedioView.setMediaController(new MediaController(this));
    }
    class MyMediaContruller extends MediaController {

        public MyMediaContruller(Context context, AttributeSet attrs) {
            super(context, attrs);
        }
    }
}
