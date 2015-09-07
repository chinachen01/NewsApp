package com.newsapp.ui.index.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.focus.newsapp.R;
import com.newsapp.ui.picNews.activity.PicNewsActivity;
import com.newsapp.ui.vedio.activity.VedioDetailActivity;

/**
 *
 */
public class SlidingMenuFragment extends Fragment implements View.OnClickListener {
    private TextView mPicText, mVedioText, mWeatherText, mMapText, mMoreText;
    private OnClickSlidingMenuItem mOnClickSlingMenu;

    public interface OnClickSlidingMenuItem {
        void closeSlidingMenu();

        void clickSlidingMenuItem();
    }

    public static SlidingMenuFragment newInstance() {
        Bundle args = new Bundle();
        SlidingMenuFragment fragment = new SlidingMenuFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mOnClickSlingMenu = (OnClickSlidingMenuItem) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sliding_menu_fragment, container, false);
        mPicText = (TextView) view.findViewById(R.id.slidingmenu_pic_text);
        mPicText.setOnClickListener(this);
        mVedioText = (TextView) view.findViewById(R.id.slidingmenu_vedio_text);
        mVedioText.setOnClickListener(this);
        mWeatherText = (TextView) view.findViewById(R.id.slidingmenu_weather_text);
        mWeatherText.setOnClickListener(this);
        mMapText = (TextView) view.findViewById(R.id.slidingmenu_map_text);
        mMapText.setOnClickListener(this);
        mMoreText = (TextView) view.findViewById(R.id.slidingmenu_more_text);
        mMoreText.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.slidingmenu_pic_text:
                intent.setClass(getActivity(), PicNewsActivity.class);
                break;
            case R.id.slidingmenu_vedio_text:
                intent.setClass(getActivity(), VedioDetailActivity.class);
            default:
                mOnClickSlingMenu.clickSlidingMenuItem();
                break;
        }
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
        mOnClickSlingMenu.closeSlidingMenu();
    }
}
