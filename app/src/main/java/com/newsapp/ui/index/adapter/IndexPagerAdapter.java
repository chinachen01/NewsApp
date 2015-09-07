package com.newsapp.ui.index.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.newsapp.ui.index.fragment.IndexFragment;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class IndexPagerAdapter extends FragmentStatePagerAdapter {
    private List<String> mList = new ArrayList<>();

    public IndexPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setDataChange(List<String> list) {
        mList = list;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return IndexFragment.newInstance(mList.get(position));
    }


    @Override
    public int getCount() {
        return mList.size();
    }
}
