package com.newsapp.ui.picNews.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.newsapp.ui.picNews.fragment.PicNewsFragment;

/**
 *
 */
public class PicNewsPagerAdapter extends FragmentStatePagerAdapter {
    private String[] mUrls = null;
    public PicNewsPagerAdapter(FragmentManager fm, String[] urls) {
        super(fm);
        mUrls = urls;
    }

    @Override
    public Fragment getItem(int i) {
        return PicNewsFragment.newInstance(mUrls[i]);
    }

    @Override
    public int getCount() {
        return mUrls.length;
    }
}
