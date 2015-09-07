package com.newsapp.ui.vedio.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.focus.newsapp.R;
import com.newsapp.widgets.CustomTitle;
import com.newsapp.utils.Constans;
import com.newsapp.ui.vedio.adapter.VedioPagerAdapter;

/**
 *
 */
public class VedioActivity extends FragmentActivity implements OnCheckedChangeListener,ViewPager.OnPageChangeListener, View.OnClickListener{
    private String[] VedioType = {Constans.VedioTextType.REDIAN,Constans.VedioTextType.YULE,Constans.VedioTextType.GAOXIAO,Constans.VedioTextType.JINGPIN};
    private ViewPager mViewPager;
    private RadioGroup mRadioGroup;
    private CustomTitle mTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_vedio_layout);
        initView();
    }

    /**
     * 初始化View
     */
    private void initView() {
        mRadioGroup = (RadioGroup) findViewById(R.id.vedio_title_group);
        mRadioGroup.setOnCheckedChangeListener(this);
        mViewPager = (ViewPager) findViewById(R.id.vedio_view_pager);
        mViewPager.setOnPageChangeListener(this);
        VedioPagerAdapter adapter = new VedioPagerAdapter(getSupportFragmentManager(), VedioType);
        mViewPager.setAdapter(adapter);
        mTitle = (CustomTitle) findViewById(R.id.vedio_titile);
        mTitle.setOnClickListener(this);
        ((RadioButton) mRadioGroup.getChildAt(0)).toggle();//Ĭ��ѡ��Ϊ0
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        int count = group.getChildCount();
        for (int i = 0; i < count; i++) {
            if (checkedId == group.getChildAt(i).getId()) {
                mViewPager.setCurrentItem(i);
            }
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
            ((RadioButton) mRadioGroup.getChildAt(position)).toggle();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.custom_title_left_image:
                finish();
                break;
        }
    }
}
