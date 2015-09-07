package com.newsapp.ui.vedio.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.newsapp.ui.vedio.fragment.VedioFragment;

/**
 *
 */
public class VedioPagerAdapter extends FragmentStatePagerAdapter {
    private String[] mUrls = null;
    public VedioPagerAdapter(FragmentManager fm, String[] urls) {
        super(fm);
        mUrls = urls;
    }

    @Override
    public Fragment getItem(int i) {
        String url = "http://c.3g.163.com/nc/video/list/"+ mUrls[i] + "/n/" + "0-9.html";
        return VedioFragment.newInstance();
    }

    @Override
    public int getCount() {
        return mUrls.length;
    }
}
